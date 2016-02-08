package main.java.com.mmrath;

import com.mmrath.rsql.*;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

/**
 * Created by murali on 6/02/2016.
 */
public class App {

    public static void main(String[] args) {
        //calc();
        rsql();

        System.out.println("************");
        //sql();

    }

    public static void rsql() {
        RsqlLexer lexer = new RsqlLexer(new ANTLRInputStream("ab LIKE 'DE' and c = d or d > 4.3"));
        CommonTokenStream tokenStream = new CommonTokenStream(lexer);
        RsqlParser rsqlParser = new RsqlParser(tokenStream);
        ParserRuleContext tree = rsqlParser.expression();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(new RsqlParserListenerImpl(), tree);
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
        public void enterBetweenPredicate_part_2(RsqlParser.BetweenPredicate_part_2Context ctx) {
            log("enterBetweenPredicate_part_2", ctx);
        }

        @Override
        public void exitBetweenPredicate_part_2(RsqlParser.BetweenPredicate_part_2Context ctx) {
            log("exitBetweenPredicate_part_2", ctx);
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
        public void enterInPredicate_value(RsqlParser.InPredicate_valueContext ctx) {
            log("enterInPredicate_value", ctx);
        }

        @Override
        public void exitInPredicate_value(RsqlParser.InPredicate_valueContext ctx) {
            log("exitInPredicate_value", ctx);
        }

        @Override
        public void enterIn_value_list(RsqlParser.In_value_listContext ctx) {
            log("enterIn_value_list", ctx);
        }

        @Override
        public void exitIn_value_list(RsqlParser.In_value_listContext ctx) {
            log("exitIn_value_list", ctx);
        }

        @Override
        public void enterPatternMatchingPredicate(RsqlParser.PatternMatchingPredicateContext ctx) {
            log("enterPattern_matchingPredicate", ctx);
        }

        @Override
        public void exitPatternMatchingPredicate(RsqlParser.PatternMatchingPredicateContext ctx) {
            log("exitPattern_matchingPredicate", ctx);
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
        public void enterNumeric_primary(RsqlParser.Numeric_primaryContext ctx) {
            log("enterNumeric_primary", ctx);
        }

        @Override
        public void exitNumeric_primary(RsqlParser.Numeric_primaryContext ctx) {
            log("exitNumeric_primary", ctx);
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
            System.out.println("visitTerminal:"+node.getText());
        }

        @Override
        public void visitErrorNode(ErrorNode node) {
            System.out.println("visitErrorNode:"+node.getText());
        }

        private void log(String exitExpression, RuleContext ctx) {
            System.out.println(exitExpression + ":" + ctx.getText());
        }

    }
    private static void log(String exitExpression, RuleContext ctx) {
        System.out.println(exitExpression + ":" + ctx.getText());
    }
}
