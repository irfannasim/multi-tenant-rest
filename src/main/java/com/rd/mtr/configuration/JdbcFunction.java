package com.rd.mtr.configuration;

import java.io.IOException;
import java.sql.SQLException;

@FunctionalInterface
public interface JdbcFunction<T, R> {
    R apply(T t) throws SQLException, IOException;
}
