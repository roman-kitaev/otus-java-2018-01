package ru.otus.hw101.base;

import ru.otus.hw101.base.dataSets.UserDataSet;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by rel on 24.04.2018.
 */
public interface DBService {
    void save(UserDataSet dataSet);

    UserDataSet read(long id);

    UserDataSet readByName(String name);

    List<UserDataSet> getAllUsers();

    String getMetaData();

    void prepareTables();

    void addUsers(String... names);

    String getUserName(int id);

    List<String> getAllNames();

    void deleteTables();

    void shutdown();
}
