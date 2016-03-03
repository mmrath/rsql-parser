package com.mmrath.rsql.jdbc;

import com.mmrath.rsql.ExpressionBuilder;

import java.util.ArrayList;
import java.util.List;

public class JdbcExpressionBuilder implements ExpressionBuilder<JdbcExpression> {
    private final String OR = " OR ";
    private final String AND = " AND ";
    private final String COMPARISION_TEMPLATE = "%s %s ?";
    private final String IS_NULL_TEMPLATE = "%s IS NULL";
    private final String IS_NOT_NULL_TEMPLATE = "%s IN NOT NULL";
    private final String LIKE_TEMPLATE = "%s LIKE ?";
    private final String NOT_LIKE_TEMPLATE = "%s NOT LIKE ?";
    private final String BETWEEN_TEMPLATE = "%s BETWEEN ? AND ?";
    private final String NOT_BETWEEN_TEMPLATE = "%s NOT BETWEEN ? AND ?";
    private final String IN_TEMPLATE = "%s IN";
    private final String NOT_IN_TEMPLATE = "%s NOT IN";
    private final String COMMA = ",";

    private final ColumnNameTransformer columnNameTransformer;

    public JdbcExpressionBuilder() {
        this(null);
    }

    public JdbcExpressionBuilder(ColumnNameTransformer columnNameTransformer) {
        this.columnNameTransformer = columnNameTransformer;
    }


    @Override
    public JdbcExpression or(JdbcExpression statement1, JdbcExpression statement2) {
        return andOr(statement1, statement2, OR);
    }

    @Override
    public JdbcExpression and(JdbcExpression statement1, JdbcExpression statement2) {
        return andOr(statement1, statement2, AND);
    }

    @Override
    public JdbcExpression parenthesize(JdbcExpression statement) {
        return new JdbcExpression(" ( " + statement.getWhereClause() + " ) ", statement.getParams());
    }

    private JdbcExpression andOr(JdbcExpression statement1, JdbcExpression statement2, String op) {
        if (statement1 == null) {
            return statement2;
        }
        if (statement2 == null) {
            return statement1;
        }

        String whereSql = statement1.getWhereClause() + op + statement2.getWhereClause();
        List<Object> params = new ArrayList<Object>();
        params.addAll(statement1.getParams());
        params.addAll(statement2.getParams());

        return new JdbcExpression(whereSql, params);
    }

    @Override
    public JdbcExpression equal(String columnCode, Object value) {
        return comparisionPredicate(columnCode, "=", value);
    }

    @Override
    public JdbcExpression notEqual(String columnCode, Object value) {
        return comparisionPredicate(columnCode, "!=", value);
    }

    @Override
    public JdbcExpression lessOrEqual(String columnCode, Object value) {
        return comparisionPredicate(columnCode, "<=", value);
    }

    @Override
    public JdbcExpression lessThan(String columnCode, Object value) {
        return comparisionPredicate(columnCode, "<", value);
    }

    @Override
    public JdbcExpression greaterOrEqual(String columnCode, Object value) {
        return comparisionPredicate(columnCode, ">=", value);
    }

    @Override
    public JdbcExpression greaterThan(String columnCode, Object value) {
        return comparisionPredicate(columnCode, ">", value);
    }

    @Override
    public JdbcExpression isNotNull(String column) {
        return new JdbcExpression(String.format(IS_NOT_NULL_TEMPLATE, getColumnName(column)));
    }

    @Override
    public JdbcExpression isNull(String column) {
        return new JdbcExpression(String.format(IS_NULL_TEMPLATE, getColumnName(column)));
    }

    @Override
    public JdbcExpression notLike(String column, Object value) {
        return new JdbcExpression(String.format(NOT_LIKE_TEMPLATE, getColumnName(column)), value);
    }

    @Override
    public JdbcExpression like(String column, Object value) {
        return new JdbcExpression(String.format(LIKE_TEMPLATE, getColumnName(column)), value);
    }

    @Override
    public JdbcExpression notBetween(String column, Object start, Object end) {
        return new JdbcExpression(String.format(NOT_BETWEEN_TEMPLATE, getColumnName(column)), start, end);
    }

    @Override
    public JdbcExpression between(String column, Object start, Object end) {
        return new JdbcExpression(String.format(BETWEEN_TEMPLATE, getColumnName(column)), start, end);
    }

    @Override
    public JdbcExpression notIn(String column, List<Object> values) {

        StringBuilder inClause = new StringBuilder(String.format(NOT_IN_TEMPLATE, getColumnName(column)));
        inClause.append(" (");
        inClause.append(repeat("?", COMMA, values.size()));
        inClause.append(")");

        return new JdbcExpression(inClause.toString(), values);
    }

    @Override
    public JdbcExpression in(String column, List<Object> values) {
        StringBuilder inClause = new StringBuilder(String.format(IN_TEMPLATE, getColumnName(column)));
        inClause.append(" (");
        inClause.append(repeat("?", COMMA, values.size()));
        inClause.append(")");

        return new JdbcExpression(inClause.toString(), values);
    }

    private JdbcExpression comparisionPredicate(String columnCode, String operator, Object value) {
        List<Object> params = new ArrayList<Object>();
        params.add(value);
        return new JdbcExpression(String.format(COMPARISION_TEMPLATE, getColumnName(columnCode), operator), params);
    }

    private String getColumnName(String columnCode) {
        if (columnNameTransformer != null) {
            return columnNameTransformer.transform(columnCode);
        }
        return columnCode;
    }

    private String repeat(String s, String separator, int count) {
        StringBuilder string = new StringBuilder((s.length() + separator.length()) * count);
        while (--count > 0) {
            string.append(s).append(separator);
        }
        return string.append(s).toString();
    }

}
