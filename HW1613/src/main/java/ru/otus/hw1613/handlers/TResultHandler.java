package ru.otus.hw1613.handlers;

import java.sql.ResultSet;
import java.sql.SQLException;

@FunctionalInterface
public interface TResultHandler<T> {
    T handle(ResultSet resultSet) throws SQLException;
}
