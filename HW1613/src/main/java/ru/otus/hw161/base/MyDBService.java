package ru.otus.hw161.base;

/**
 * Created by rel on 12.04.2018.
 */

import ru.otus.hw161.app.DataSet;
import ru.otus.hw161.app.HitMiss;
import ru.otus.hw161.app.UserDataSet;

import java.sql.SQLException;
import java.util.List;

public interface MyDBService extends AutoCloseable {
    HitMiss getCacheStatus();

    String getMetaData();

    void prepareTables() throws SQLException;

    void addUsers(String... names) throws SQLException;

    String getUserName(int id) throws SQLException;

    List<String> getAllNames() throws SQLException;

    List<UserDataSet> getAllUsers() throws SQLException;

    void deleteTables() throws SQLException;

    <T extends DataSet> void addScheme(Class<T> clazz) throws SQLException;

    <T extends DataSet> void addSchemes(Class<T>... clazzes) throws SQLException;

    <T extends DataSet> void save(T user) throws SQLException;

    <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException;
}
