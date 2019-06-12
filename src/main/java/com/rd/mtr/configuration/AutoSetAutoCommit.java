package com.rd.mtr.configuration;

import java.sql.Connection;
import java.sql.SQLException;

public class AutoSetAutoCommit implements AutoCloseable {

    private Connection connection;
    private boolean originalAutoCommit;

    public AutoSetAutoCommit(Connection connection, boolean autoCommit) throws SQLException {
        this.connection = connection;
        originalAutoCommit = connection.getAutoCommit();
        connection.setAutoCommit(autoCommit);
    }

    @Override
    public void close() throws SQLException {
        connection.setAutoCommit(originalAutoCommit);
    }

}