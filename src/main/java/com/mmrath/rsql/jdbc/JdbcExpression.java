package com.mmrath.rsql.jdbc;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class JdbcExpression {
    private final String whereClause;
    private final List<Object> params;

    public JdbcExpression(String whereClause, List<Object> params) {
        this.whereClause = whereClause;
        this.params = Collections.unmodifiableList(params);
    }

    public JdbcExpression(String whereClause, Object... params) {
        this(whereClause, params == null ? Collections.emptyList() : Arrays.asList(params));
    }

    @Override
    public String toString() {
        return "JdbcExpression{" +
                "whereClause='" + whereClause + '\'' +
                ", params=" + params +
                '}';
    }

    public String getWhereClause() {
        return whereClause;
    }

    public List<Object> getParams() {
        return params;
    }
}
