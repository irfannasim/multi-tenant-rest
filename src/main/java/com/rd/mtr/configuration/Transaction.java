package com.rd.mtr.configuration;

import java.sql.Connection;
import java.sql.SQLException;

public class Transaction implements AutoCloseable {

    private Connection connection;
    private boolean committed;

    public Transaction(Connection connection) throws SQLException {
        this.connection = connection;
    }

    public void commit() throws SQLException {
        connection.commit();
        committed = true;
    }

    @Override
    public void close() throws SQLException {
        if (!committed) {
            connection.rollback();
        }
    }

    public boolean isCommitted() {
        return committed;
    }
}