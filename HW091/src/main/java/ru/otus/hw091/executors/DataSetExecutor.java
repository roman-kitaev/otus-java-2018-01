package ru.otus.hw091.executors;

/**
 * Created by rel on 14.04.2018.
 */
import ru.otus.hw091.base.DataSet;
import ru.otus.hw091.objectwriter.TResultHandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DataSetExecutor extends LogExecutor {
    private final Connection connection;

    public DataSetExecutor(Connection connection) {
        super(connection);
        this.connection = connection;
    }

    public int execUpdate(String update) throws SQLException {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(update);
            return stmt.getUpdateCount();
        }
    }

    public <T extends DataSet> T execQuery(String query, TResultHandler<T> handler) throws SQLException {
        try(Statement stmt = connection.createStatement()) {
            stmt.execute(query);
            ResultSet result = stmt.getResultSet();
            return handler.handle(result);
        }
    }
}