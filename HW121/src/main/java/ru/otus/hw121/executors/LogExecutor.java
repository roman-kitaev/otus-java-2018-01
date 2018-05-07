package ru.otus.hw121.executors;

import ru.otus.hw121.objectwriter.ResultHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LogExecutor {
    private final Connection connection;

    public LogExecutor(Connection connection) {
        this.connection = connection;
    }

    public void execQuery(String query, ResultHandler handler) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            handler.handle(result);
        }
    }

    public int execUpdate(String update) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(update);
            return stmt.getUpdateCount();
        }
    }

    Connection getConnection() {
        return connection;
    }
}
