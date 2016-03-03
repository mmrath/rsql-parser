package com.mmrath;

import com.mmrath.rsql.RsqlLexer;
import com.mmrath.rsql.RsqlParser;
import com.mmrath.rsql.RsqlParserBaseListener;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.TerminalNode;


public class App {

    public static void main(String[] args) {
        //calc();
        rsql();

        System.out.println("************");
        //sql();

    }

    public static void rsql() {
        RsqlLexer lexer = new RsqlLexer(new ANTLRInputStream("ab > 'DE' and (c = d or d > 4.3) and e>4 or " +
                "rere in (20,30,40,50) and defer between 2 and 3 or rerer not in ('432','234324') "));
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        RsqlParser rsqlParser = new RsqlParser(tokenStream);
        RsqlParser.ExpressionContext tree = rsqlParser.expression();
        RsqlStatement rsqlStatement = new RsqlExpressionVisitor().visit(tree);
        System.out.println(rsqlStatement);
        //ParseTreeWalker walker = new ParseTreeWalker();
        //walker.walk(new RsqlParserListenerImpl(), tree);
    }

    static class RsqlParserListenerImpl extends RsqlParserBaseListener {

        @Override
        public void enterExpression(RsqlParser.ExpressionContext ctx) {
            log("enterExpression", ctx);
        }

        @Override
        public void exitExpression(RsqlParser.ExpressionContext ctx) {
            log("exitExpression", ctx);
        }

        @Override
        public void enterBooleanValueExpression(RsqlParser.BooleanValueExpressionContext ctx) {
            log("enterBooleanValueExpression", ctx);
        }

        @Override
        public void exitBooleanValueExpression(RsqlParser.BooleanValueExpressionContext ctx) {
            log("exitBooleanValueExpression", ctx);
        }

        @Override
        public void enterOrPredicate(RsqlParser.OrPredicateContext ctx) {
            log("enterOrPredicate", ctx);
        }

        @Override
        public void exitOrPredicate(RsqlParser.OrPredicateContext ctx) {
            log("exitOrPredicate", ctx);
        }

        @Override
        public void enterAndPredicate(RsqlParser.AndPredicateContext ctx) {
            log("enterAndPredicate", ctx);
        }

        @Override
        public void exitAndPredicate(RsqlParser.AndPredicateContext ctx) {
            log("exitAndPredicate", ctx);
        }

        @Override
        public void enterBooleanPrimary(RsqlParser.BooleanPrimaryContext ctx) {
            log("enterBooleanPrimary", ctx);
        }

        @Override
        public void exitBooleanPrimary(RsqlParser.BooleanPrimaryContext ctx) {
            log("exitBooleanPrimary", ctx);
        }

        @Override
        public void enterBooleanPredicand(RsqlParser.BooleanPredicandContext ctx) {
            log("enterBooleanPredicand", ctx);
        }

        @Override
        public void exitBooleanPredicand(RsqlParser.BooleanPredicandContext ctx) {
            log("exitBooleanPredicand", ctx);
        }

        @Override
        public void enterParenthesizedBooleanValueExpression(RsqlParser.ParenthesizedBooleanValueExpressionContext ctx) {
            log("enterParenthesizedBooleanValueExpression", ctx);
        }

        @Override
        public void exitParenthesizedBooleanValueExpression(RsqlParser.ParenthesizedBooleanValueExpressionContext ctx) {
            log("exitParenthesizedBooleanValueExpression", ctx);
        }

        @Override
        public void enterPredicate(RsqlParser.PredicateContext ctx) {
            log("enterPredicate", ctx);
        }

        @Override
        public void exitPredicate(RsqlParser.PredicateContext ctx) {
            log("exitPredicate", ctx);
        }

        @Override
        public void enterComparisonPredicate(RsqlParser.ComparisonPredicateContext ctx) {
            log("enterComparisonPredicate", ctx);
        }

        @Override
        public void exitComparisonPredicate(RsqlParser.ComparisonPredicateContext ctx) {
            log("exitComparisonPredicate", ctx);
        }

        @Override
        public void enterComparisionOperator(RsqlParser.ComparisionOperatorContext ctx) {
            log("enterComparisionOperator", ctx);
        }

        @Override
        public void exitComparisionOperator(RsqlParser.ComparisionOperatorContext ctx) {
            log("exitComparisionOperator", ctx);
        }

        @Override
        public void enterBetweenPredicate(RsqlParser.BetweenPredicateContext ctx) {
            log("enterBetweenPredicate", ctx);
        }

        @Override
        public void exitBetweenPredicate(RsqlParser.BetweenPredicateContext ctx) {
            log("exitBetweenPredicate", ctx);
        }

        @Override
        public void enterInPredicate(RsqlParser.InPredicateContext ctx) {
            log("enterInPredicate", ctx);
        }

        @Override
        public void exitInPredicate(RsqlParser.InPredicateContext ctx) {
            log("exitInPredicate", ctx);
        }

        @Override
        public void enterInPredicateValue(RsqlParser.InPredicateValueContext ctx) {
            log("enterInPredicateValue", ctx);
        }

        @Override
        public void exitInPredicateValue(RsqlParser.InPredicateValueContext ctx) {
            log("exitInPredicateValue", ctx);
        }

        @Override
        public void enterInValueList(RsqlParser.InValueListContext ctx) {
            log("enterInValueList", ctx);
        }

        @Override
        public void exitInValueList(RsqlParser.InValueListContext ctx) {
            log("exitInValueList", ctx);
        }

        @Override
        public void enterPatternMatchingPredicate(RsqlParser.PatternMatchingPredicateContext ctx) {
            log("enterPatternMatchingPredicate", ctx);
        }

        @Override
        public void exitPatternMatchingPredicate(RsqlParser.PatternMatchingPredicateContext ctx) {
            log("exitPatternMatchingPredicate", ctx);
        }

        @Override
        public void enterPatternMatcher(RsqlParser.PatternMatcherContext ctx) {
            log("enterPatternMatcher", ctx);
        }

        @Override
        public void exitPatternMatcher(RsqlParser.PatternMatcherContext ctx) {
            log("exitPatternMatcher", ctx);
        }

        @Override
        public void enterNullPredicate(RsqlParser.NullPredicateContext ctx) {
            log("enterNullPredicate", ctx);
        }

        @Override
        public void exitNullPredicate(RsqlParser.NullPredicateContext ctx) {
            log("exitNullPredicate", ctx);
        }

        @Override
        public void enterValueExpression(RsqlParser.ValueExpressionContext ctx) {
            log("enterValueExpression", ctx);
        }

        @Override
        public void exitValueExpression(RsqlParser.ValueExpressionContext ctx) {
            log("exitValueExpression", ctx);
        }

        @Override
        public void enterNumericValueExpression(RsqlParser.NumericValueExpressionContext ctx) {
            log("enterNumericValueExpression", ctx);
        }

        @Override
        public void exitNumericValueExpression(RsqlParser.NumericValueExpressionContext ctx) {
            log("exitNumericValueExpression", ctx);
        }

        @Override
        public void enterNumericPrimary(RsqlParser.NumericPrimaryContext ctx) {
            log("enterNumericPrimary", ctx);
        }

        @Override
        public void exitNumericPrimary(RsqlParser.NumericPrimaryContext ctx) {
            log("exitNumericPrimary", ctx);
        }

        @Override
        public void enterSign(RsqlParser.SignContext ctx) {
            log("enterSign", ctx);
        }

        @Override
        public void exitSign(RsqlParser.SignContext ctx) {
            log("exitSign", ctx);
        }

        @Override
        public void enterColumnName(RsqlParser.ColumnNameContext ctx) {
            log("enterColumnName", ctx);
        }

        @Override
        public void exitColumnName(RsqlParser.ColumnNameContext ctx) {
            log("exitColumnName", ctx);
        }

        @Override
        public void enterEveryRule(ParserRuleContext ctx) {
            log("enterEveryRule", ctx);
        }

        @Override
        public void exitEveryRule(ParserRuleContext ctx) {
            log("exitEveryRule", ctx);
        }

        @Override
        public void visitTerminal(TerminalNode node) {
            System.out.println("visitTerminal:" + node.getText());
        }

        @Override
        public void visitErrorNode(ErrorNode node) {
            System.out.println("visitErrorNode:" + node.getText());
        }

        private void log(String exitExpression, RuleContext ctx) {
            System.out.println(exitExpression + ":" + ctx.getText());
        }

    }

    private static void log(String exitExpression, RuleContext ctx) {
        System.out.println(exitExpression + ":" + ctx.getText());
    }
}
