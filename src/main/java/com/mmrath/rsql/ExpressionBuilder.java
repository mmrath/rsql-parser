package com.mmrath.rsql;

import java.util.List;

/**
 * Created by murali on 3/03/2016.
 */
public interface ExpressionBuilder<T> {
    T or(T expression1, T expression2);

    T and(T expression1, T expression2);

    T parenthesize(T expression);

    T equal(String column, Object value);

    T notEqual(String column, Object value);

    T lessOrEqual(String column, Object value);

    T lessThan(String column, Object value);

    T greaterOrEqual(String column, Object value);

    T greaterThan(String column, Object value);

    T isNotNull(String column);

    T isNull(String column);

    T notLike(String column, Object value);

    T like(String column, Object value);

    T notBetween(String column, Object start, Object end);

    T between(String column, Object start, Object end);

    T notIn(String column, List<Object> values);

    T in(String column, List<Object> values);
}
