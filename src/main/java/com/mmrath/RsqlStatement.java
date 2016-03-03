package com.mmrath;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class RsqlStatement {
    private static final String OR = " OR ";
    private static final String AND = " AND ";
    private static final String COMPARISION_TEMPLATE = "%s %s ?";

    private static final String IS_NULL_TEMPLATE = "%s IS NULL";
    private static final String IS_NOT_NULL_TEMPLATE = "%s IN NOT NULL";
    private static final String LIKE_TEMPLATE = "%s LIKE ?";
    private static final String NOT_LIKE_TEMPLATE = "%s NOT LIKE ?";

    private static final String BETWEEN_TEMPLATE = "%s BETWEEN ? AND ?";
    private static final String NOT_BETWEEN_TEMPLATE = "%s NOT BETWEEN ? AND ?";

    private static final String IN_TEMPLATE = "%s IN";
    private static final String NOT_IN_TEMPLATE = "%s NOT IN";

    private static final String COMMA = ",";


    private final String whereClause;
    private final List<Object> params;

    public RsqlStatement(String whereClause, List<Object> params) {
        this.whereClause = whereClause;
        this.params = params;
    }

    public RsqlStatement(String whereClause, Object... params) {
        this.whereClause = whereClause;
        if (params != null) {
            this.params = Arrays.asList(params);
        } else {
            this.params = Collections.emptyList();
        }

    }

    @Override
    public String toString() {
        return "RsqlStatement{" +
                "whereClause='" + whereClause + '\'' +
                ", params=" + params +
                '}';
    }


    public static RsqlStatement or(RsqlStatement statement1, RsqlStatement statement2) {
        return andOr(statement1, statement2, OR);
    }

    public static RsqlStatement and(RsqlStatement statement1, RsqlStatement statement2) {
        return andOr(statement1, statement2, AND);
    }

    public static RsqlStatement parenthesize(RsqlStatement statement) {
        return new RsqlStatement(" ( " + statement.whereClause + " ) ", statement.params);
    }

    private static RsqlStatement andOr(RsqlStatement statement1, RsqlStatement statement2, String op) {
        if (statement1 == null) {
            return statement2;
        }
        if (statement2 == null) {
            return statement1;
        }

        String whereSql = statement1.whereClause + op + statement2.whereClause;
        List<Object> params = new ArrayList<Object>();
        params.addAll(statement1.params);
        params.addAll(statement2.params);

        return new RsqlStatement(whereSql, params);
    }

    public static RsqlStatement equal(String columnCode, Object value) {
        return comparisionPredicate(columnCode, "=", value);
    }

    public static RsqlStatement notEqual(String columnCode, Object value) {
        return comparisionPredicate(columnCode, "!=", value);
    }

    public static RsqlStatement lessOrEqual(String columnCode, Object value) {
        return comparisionPredicate(columnCode, "<=", value);
    }

    public static RsqlStatement lessThan(String columnCode, Object value) {
        return comparisionPredicate(columnCode, "<", value);
    }

    public static RsqlStatement greaterOrEqual(String columnCode, Object value) {
        return comparisionPredicate(columnCode, ">=", value);
    }

    public static RsqlStatement greeaterThan(String columnCode, Object value) {
        return comparisionPredicate(columnCode, ">", value);
    }

    public static RsqlStatement isNotNull(String column) {
        return new RsqlStatement(String.format(IS_NOT_NULL_TEMPLATE, getColumnName(column)));
    }

    public static RsqlStatement isNull(String column) {
        return new RsqlStatement(String.format(IS_NULL_TEMPLATE, getColumnName(column)));
    }

    public static RsqlStatement notLike(String column, Object value) {
        return new RsqlStatement(String.format(NOT_LIKE_TEMPLATE, getColumnName(column)), value);
    }

    public static RsqlStatement like(String column, Object value) {
        return new RsqlStatement(String.format(LIKE_TEMPLATE, getColumnName(column)), value);
    }

    public static RsqlStatement notBetween(String column, Object start, Object end) {
        return new RsqlStatement(String.format(NOT_BETWEEN_TEMPLATE, getColumnName(column)), start, end);
    }

    public static RsqlStatement between(String column, Object start, Object end) {
        return new RsqlStatement(String.format(BETWEEN_TEMPLATE, getColumnName(column)), start, end);
    }

    public static RsqlStatement notIn(String column, List<Object> values) {

        StringBuilder inClause = new StringBuilder(String.format(NOT_IN_TEMPLATE, getColumnName(column)));
        inClause.append(" (");
        inClause.append(repeat("?", COMMA, values.size()));
        inClause.append(")");

        return new RsqlStatement(inClause.toString(), values);
    }

    public static RsqlStatement in(String column, List<Object> values) {
        StringBuilder inClause = new StringBuilder(String.format(IN_TEMPLATE, getColumnName(column)));
        inClause.append(" (");
        inClause.append(repeat("?", COMMA, values.size()));
        inClause.append(")");

        return new RsqlStatement(inClause.toString(), values);
    }

    private static RsqlStatement comparisionPredicate(String columnCode, String operator, Object value) {
        List<Object> params = new ArrayList<Object>();
        params.add(value);
        return new RsqlStatement(String.format(COMPARISION_TEMPLATE, getColumnName(columnCode), operator), params);
    }

    private static String getColumnName(String columnCode) {
        return columnCode;
    }

    private static String repeat(String s, String separator, int count) {
        StringBuilder string = new StringBuilder((s.length() + separator.length()) * count);
        while (--count > 0) {
            string.append(s).append(separator);
        }
        return string.append(s).toString();
    }

}
