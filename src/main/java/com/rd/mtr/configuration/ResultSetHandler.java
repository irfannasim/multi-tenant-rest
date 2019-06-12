package com.rd.mtr.configuration;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultSetHandler {
    void handle(ResultSet rs) throws SQLException;
}
