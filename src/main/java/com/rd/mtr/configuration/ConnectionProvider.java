package com.rd.mtr.configuration;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface ConnectionProvider {
    Connection getConnection() throws SQLException, IOException;
}
