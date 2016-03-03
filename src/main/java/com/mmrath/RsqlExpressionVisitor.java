package com.mmrath;


import com.mmrath.rsql.RsqlParser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class RsqlExpressionVisitor {


    public RsqlStatement visit(final RsqlParser.ExpressionContext context) {
        if (context.booleanValueExpression() != null) {
            return visitBooleanValueExpression(context.booleanValueExpression());
        }
        return null;
    }

    private RsqlStatement visitBooleanValueExpression(RsqlParser.BooleanValueExpressionContext booleanValueExpressionContext) {

        if (booleanValueExpressionContext.orPredicate() != null) {
            return visitOrPredicate(booleanValueExpressionContext.orPredicate());
        } else {
            throw new IllegalArgumentException("Or predicate expected");
        }


    }

    private RsqlStatement visitOrPredicate(RsqlParser.OrPredicateContext orPredicateContext) {
        RsqlStatement andStmt = null;
        if (orPredicateContext.andPredicate() != null) {
            andStmt = visitAndStmt(orPredicateContext.andPredicate());
        }

        RsqlStatement orStmt = null;
        if (orPredicateContext.orPredicate() != null && !orPredicateContext.orPredicate().isEmpty()) {
            for (RsqlParser.OrPredicateContext orExp : orPredicateContext.orPredicate()) {
                orStmt = RsqlStatement.or(orStmt, visitOrPredicate(orExp));
            }
        }
        return RsqlStatement.or(andStmt, orStmt);
    }

    private RsqlStatement visitAndStmt(RsqlParser.AndPredicateContext andPredicateContext) {
        RsqlStatement primaryBooleanStmt = null;

        if (andPredicateContext.booleanPrimary() != null) {
            primaryBooleanStmt = visitPrimaryBooleanStmt(andPredicateContext.booleanPrimary());
        }
        RsqlStatement andStmt = null;
        if (andPredicateContext.andPredicate() != null && !andPredicateContext.andPredicate().isEmpty()) {
            for (RsqlParser.AndPredicateContext and : andPredicateContext.andPredicate()) {
                andStmt = RsqlStatement.and(andStmt, visitAndStmt(and));
            }
        }
        return RsqlStatement.and(primaryBooleanStmt, andStmt);

    }

    private RsqlStatement visitPrimaryBooleanStmt(RsqlParser.BooleanPrimaryContext booleanPrimaryContext) {
        if (booleanPrimaryContext.predicate() != null) {
            return visitPredicate(booleanPrimaryContext.predicate());
        } else if (booleanPrimaryContext.booleanPredicand() != null) {
            return visitBooleanPredicand(booleanPrimaryContext.booleanPredicand());
        } else {
            return null;
        }
    }

    private RsqlStatement visitBooleanPredicand(RsqlParser.BooleanPredicandContext booleanPredicandContext) {
        if (booleanPredicandContext.parenthesizedBooleanValueExpression() != null &&
                booleanPredicandContext.parenthesizedBooleanValueExpression().booleanValueExpression() != null) {
            return RsqlStatement.parenthesize(visitBooleanValueExpression(
                    booleanPredicandContext.parenthesizedBooleanValueExpression().booleanValueExpression()));

        }
        return null;
    }

    private RsqlStatement visitPredicate(RsqlParser.PredicateContext predicate) {
        if (predicate.comparisonPredicate() != null) {
            return visitComparisionPredicate(predicate.comparisonPredicate());
        } else if (predicate.betweenPredicate() != null) {
            return visitBetweenPredicate(predicate.betweenPredicate());
        } else if (predicate.inPredicate() != null) {
            return visitInPredicate(predicate.inPredicate());
        } else if (predicate.patternMatchingPredicate() != null) {
            return visitPatternMatchingPredicate(predicate.patternMatchingPredicate());
        } else if (predicate.nullPredicate() != null) {
            return visitNullPredicate(predicate.nullPredicate());
        } else {
            throw new IllegalArgumentException("Unknown predicate type in:" + predicate.getText());
        }
    }

    private RsqlStatement visitNullPredicate(RsqlParser.NullPredicateContext nullPredicateContext) {
        String column = nullPredicateContext.columnName().Identifier().getText();
        if(nullPredicateContext.NOT() != null){
            return RsqlStatement.isNotNull(column);
        }else {
            return RsqlStatement.isNull(column);
        }
    }

    private RsqlStatement visitPatternMatchingPredicate(RsqlParser.PatternMatchingPredicateContext patternMatchingPredicateContext) {
        String column = patternMatchingPredicateContext.columnName().Identifier().getText();
        Object value = valueFromStringLiteral(patternMatchingPredicateContext.Character_String_Literal().getText());
        if(patternMatchingPredicateContext.patternMatcher().NOT()!=null){
            return RsqlStatement.notLike(column, value);
        }else{
            return RsqlStatement.like(column, value);
        }
    }

    private RsqlStatement visitInPredicate(RsqlParser.InPredicateContext inPredicateContext) {
        String column = inPredicateContext.columnName().Identifier().getText();
        List<Object> values = getValues(inPredicateContext.inPredicateValue().inValueList().valueExpression());
        if(inPredicateContext.NOT()!=null){
            return RsqlStatement.notIn(column,values);
        }else{
            return RsqlStatement.in(column,values);
        }
    }

    private RsqlStatement visitBetweenPredicate(RsqlParser.BetweenPredicateContext betweenPredicateContext) {
        String column = betweenPredicateContext.columnName().Identifier().getText();
        Object start = getValue(betweenPredicateContext.betweenBegin().valueExpression());
        Object end= getValue(betweenPredicateContext.betweenEnd().valueExpression());

        if(betweenPredicateContext.NOT()!=null){
            return RsqlStatement.notBetween(column,start,end);
        }else{
            return RsqlStatement.between(column,start,end);
        }
    }

    private RsqlStatement visitComparisionPredicate(RsqlParser.ComparisonPredicateContext comparisonPredicateContext) {
        if (comparisonPredicateContext.comparisionOperator().EQUAL() != null) {
            return RsqlStatement.equal(comparisonPredicateContext.left.Identifier().getText(),
                    getValue(comparisonPredicateContext.valueExpression()));
        } else if (comparisonPredicateContext.comparisionOperator().NOT_EQUAL() != null) {
            return RsqlStatement.notEqual(comparisonPredicateContext.left.Identifier().getText(),
                    getValue(comparisonPredicateContext.valueExpression()));
        } else if (comparisonPredicateContext.comparisionOperator().LEQ() != null) {
            return RsqlStatement.lessOrEqual(comparisonPredicateContext.left.Identifier().getText(),
                    getValue(comparisonPredicateContext.valueExpression()));
        } else if (comparisonPredicateContext.comparisionOperator().LTH() != null) {
            return RsqlStatement.lessThan(comparisonPredicateContext.left.Identifier().getText(),
                    getValue(comparisonPredicateContext.valueExpression()));
        } else if (comparisonPredicateContext.comparisionOperator().GEQ() != null) {
            return RsqlStatement.greaterOrEqual(comparisonPredicateContext.left.Identifier().getText(),
                    getValue(comparisonPredicateContext.valueExpression()));
        } else if (comparisonPredicateContext.comparisionOperator().GTH() != null) {
            return RsqlStatement.greeaterThan(comparisonPredicateContext.left.Identifier().getText(),
                    getValue(comparisonPredicateContext.valueExpression()));
        } else {
            throw new IllegalArgumentException("Comparision predicate not recognized:" + comparisonPredicateContext.getText());
        }


    }



    private List<Object> getValues(List<RsqlParser.ValueExpressionContext> valueExpressionContexts){
        List<Object> params = new ArrayList<Object>();

        for(RsqlParser.ValueExpressionContext context:valueExpressionContexts){
            params.add(getValue(context));
        }
        return params;
    }
    private Object getValue(RsqlParser.ValueExpressionContext valueExpressionContext) {
        Object value;

        if (valueExpressionContext.Character_String_Literal() != null) {
            String literal = valueExpressionContext.Character_String_Literal().getText();
            return valueFromStringLiteral(literal);
        } else if (valueExpressionContext.numericValueExpression() != null) {
            RsqlParser.NumericValueExpressionContext numericValueExpressionContext = valueExpressionContext.numericValueExpression();
            if (numericValueExpressionContext.numericPrimary().NUMBER() != null) {
                value = new BigInteger(numericValueExpressionContext.getText());
            } else {
                value = new BigDecimal(numericValueExpressionContext.getText());
            }
        } else if (valueExpressionContext.Identifier() != null) {
            value = valueExpressionContext.Identifier().getText();
        } else {
            throw new IllegalArgumentException("Value is illegal:" + valueExpressionContext.getText());
        }
        return value;
    }

    private String valueFromStringLiteral(String literal){
        literal = literal.substring(1); // remove first quote
        literal = literal.substring(0, literal.length()-1); // remove last quote
        return literal;
    }


}
