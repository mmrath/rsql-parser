package com.mmrath;


import com.mmrath.rsql.RsqlParser;

import java.math.BigDecimal;
import java.math.BigInteger;

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
        throw new IllegalArgumentException("Not implemented");
    }

    private RsqlStatement visitPatternMatchingPredicate(RsqlParser.PatternMatchingPredicateContext patternMatchingPredicateContext) {
        throw new IllegalArgumentException("Not implemented");
    }

    private RsqlStatement visitInPredicate(RsqlParser.InPredicateContext inPredicateContext) {
        throw new IllegalArgumentException("Not implemented");
    }

    private RsqlStatement visitBetweenPredicate(RsqlParser.BetweenPredicateContext betweenPredicateContext) {
        throw new IllegalArgumentException("Not implemented");
    }

    private RsqlStatement visitComparisionPredicate(RsqlParser.ComparisonPredicateContext comparisonPredicateContext) {
        if (comparisonPredicateContext.comparisionOperator().EQUAL() != null) {
            return RsqlStatement.equalPredicate(comparisonPredicateContext.left.Identifier().getText(),
                    getValue(comparisonPredicateContext.valueExpression()));
        } else if (comparisonPredicateContext.comparisionOperator().NOT_EQUAL() != null) {
            return RsqlStatement.notEqualPredicate(comparisonPredicateContext.left.Identifier().getText(),
                    getValue(comparisonPredicateContext.valueExpression()));
        } else if (comparisonPredicateContext.comparisionOperator().LEQ() != null) {
            return RsqlStatement.lessOrEqualPredicate(comparisonPredicateContext.left.Identifier().getText(),
                    getValue(comparisonPredicateContext.valueExpression()));
        } else if (comparisonPredicateContext.comparisionOperator().LTH() != null) {
            return RsqlStatement.lessThanPredicate(comparisonPredicateContext.left.Identifier().getText(),
                    getValue(comparisonPredicateContext.valueExpression()));
        } else if (comparisonPredicateContext.comparisionOperator().GEQ() != null) {
            return RsqlStatement.greaterOrEqualPredicate(comparisonPredicateContext.left.Identifier().getText(),
                    getValue(comparisonPredicateContext.valueExpression()));
        } else if (comparisonPredicateContext.comparisionOperator().GTH() != null) {
            return RsqlStatement.greeaterThanPredicate(comparisonPredicateContext.left.Identifier().getText(),
                    getValue(comparisonPredicateContext.valueExpression()));
        } else {
            throw new IllegalArgumentException("Comparision predicate not recognized:" + comparisonPredicateContext.getText());
        }


    }


    private Object getValue(RsqlParser.ValueExpressionContext valueExpressionContext) {
        Object value;

        if (valueExpressionContext.Character_String_Literal() != null) {
            String literal = valueExpressionContext.Character_String_Literal().getText();
            literal = literal.substring(1); // remove first quote
            literal = literal.substring(0, literal.length()-1);
            value = literal;
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


}
