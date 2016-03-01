package com.mmrath;

import java.util.ArrayList;
import java.util.List;

public class RsqlStatement {
    private static final String OR = " OR ";
    private static final String AND = " AND ";
    private static final String COMPARISION_TEMPLATE = "%s %s ?";

    private final String whereClause;
    private final List<Object> params;

    public RsqlStatement(String whereClause, List<Object> params) {
        this.whereClause = whereClause;
        this.params = params;
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

    public static RsqlStatement equalPredicate(String columnCode, Object value) {
        return comparisionPredicate(columnCode, "=", value);
    }

    public static RsqlStatement notEqualPredicate(String columnCode, Object value) {
        return comparisionPredicate(columnCode, "!=", value);
    }

    public static RsqlStatement lessOrEqualPredicate(String columnCode, Object value) {
        return comparisionPredicate(columnCode, "<=", value);
    }

    public static RsqlStatement lessThanPredicate(String columnCode, Object value) {
        return comparisionPredicate(columnCode, "<", value);
    }

    public static RsqlStatement greaterOrEqualPredicate(String columnCode, Object value) {
        return comparisionPredicate(columnCode, ">=", value);
    }

    public static RsqlStatement greeaterThanPredicate(String columnCode, Object value) {
        return comparisionPredicate(columnCode, ">", value);
    }

    private static RsqlStatement comparisionPredicate(String columnCode, String operator, Object value) {
        List<Object> params = new ArrayList<Object>();
        params.add(value);
        return new RsqlStatement(String.format(COMPARISION_TEMPLATE, getColumnName(columnCode), operator), params);
    }

    private static String getColumnName(String columnCode) {
        return columnCode;
    }

}
