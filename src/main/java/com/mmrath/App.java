package com.mmrath;

import com.mmrath.rsql.RsqlExpressionVisitor;
import com.mmrath.rsql.RsqlLexer;
import com.mmrath.rsql.RsqlParser;
import com.mmrath.rsql.RsqlParserBaseListener;
import com.mmrath.rsql.jdbc.JdbcExpression;
import com.mmrath.rsql.jdbc.JdbcExpressionBuilder;
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
        JdbcExpression rsqlStatement = new RsqlExpressionVisitor<JdbcExpression>(new JdbcExpressionBuilder()).visit(tree);
        System.out.println(rsqlStatement);
    }
}
