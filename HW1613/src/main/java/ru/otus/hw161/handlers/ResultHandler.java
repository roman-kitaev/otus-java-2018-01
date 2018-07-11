package ru.otus.hw161.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface ResultHandler {
    void handle(ResultSet result) throws SQLException;
}
