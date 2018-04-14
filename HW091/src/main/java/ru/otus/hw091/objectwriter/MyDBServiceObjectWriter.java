package ru.otus.hw091.objectwriter;

import ru.otus.hw091.base.DataSet;
import ru.otus.hw091.base.MyDBService;
import ru.otus.hw091.base.UserDataSet;
import ru.otus.hw091.executors.DataSetExecutor;
import ru.otus.hw091.executors.LogExecutor;
import ru.otus.hw091.executors.TExecutor;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by rel on 12.04.2018.
 */
public class MyDBServiceObjectWriter implements MyDBService {
    private final Connection connection;
    private static final String CREATE_TABLE_USER = "create table if not exists user (id bigint auto_increment, name varchar(256), age int(3), primary key (id))";
    private static final String INSERT_USER = "insert into user (name, age) values ('%s', %s)";
    private static final String DELETE_USER = "drop table user";
    private static final String SELECT_USER = "select name from user where id=%s";
    private static final String GET_ALL_NAMES = "select name from user";
    private static final String GET_ALL_USERS = "select * from user";
    private static final String SELECT_DATASET = "select name, age from user where id=%s";
    public MyDBServiceObjectWriter() {
        connection = ru.otus.hw091.objectwriter.ConnectionHelper.getConnection();
    }

    @Override
    public String getMetaData() {
        try {
            return "Connected to: " + getConnection().getMetaData().getURL() + "\n" +
                    "DB name: " + getConnection().getMetaData().getDatabaseProductName() + "\n" +
                    "DB version: " + getConnection().getMetaData().getDatabaseProductVersion() + "\n" +
                    "Driver: " + getConnection().getMetaData().getDriverName();
        } catch (SQLException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    @Override
    public void prepareTables() throws SQLException {
        LogExecutor exec = new LogExecutor(getConnection());
        exec.execUpdate(CREATE_TABLE_USER);
        System.out.println("Table created");
    }

    @Override
    public void addUsers(String... names) throws SQLException {
        LogExecutor exec = new LogExecutor(getConnection());
        Random rand = new Random(47);
        for (String name : names) {
            int rows = exec.execUpdate(String.format(INSERT_USER, name, (rand.nextInt(30) + 10)));
            System.out.println("User added. Rows changed: " + rows);
        }
    }

    @Override
    public String getUserName(int id) throws SQLException {
        TExecutor execT = new TExecutor(getConnection());

        return execT.execQuery(String.format(SELECT_USER, id), result -> {
            result.next();
            return result.getString("name");
        });
    }


    @Override
    public List<String> getAllNames() throws SQLException {
        TExecutor execT = new TExecutor(getConnection());

        return execT.execQuery(GET_ALL_NAMES, new TResultHandler<List<String>>() {
            @Override
            public List<String> handle(ResultSet resultSet) throws SQLException {
                List<String> list = new ArrayList<String>();
                while(resultSet.next()) {
                    list.add(resultSet.getString("name"));
                }
                return list;
            }
        });
    }

    @Override
    public List<UserDataSet> getAllUsers() throws SQLException {
        TExecutor execT = new TExecutor(getConnection());

        return execT.execQuery(GET_ALL_USERS, new TResultHandler<List<UserDataSet>>() {
            @Override
            public List<UserDataSet> handle(ResultSet resultSet) throws SQLException {
                List<UserDataSet> list = new ArrayList<UserDataSet>();
                while(resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String name = resultSet.getString("name");
                    int age = resultSet.getInt("age");
                    list.add(new UserDataSet(id, name, age));
                }
                return list;
            }
        });
    }

    @Override
    public void deleteTables() throws SQLException {
        LogExecutor exec = new LogExecutor(getConnection());
        exec.execUpdate(DELETE_USER);
        System.out.println("Table dropped");
    }

    @Override
    public void close() throws Exception {
        connection.close();
        System.out.println("Connection closed. Bye!");
    }

    protected Connection getConnection() {
        return connection;
    }

    @Override
    public <T extends DataSet> void save(T user) throws SQLException {
        try {
            Field fieldName = user.getClass().getDeclaredField("name");
            fieldName.setAccessible(true);
            String name = (String) fieldName.get(user);

            Field fieldAge = user.getClass().getDeclaredField("age");
            fieldAge.setAccessible(true);
            int age = (int) fieldAge.get(user);

            DataSetExecutor exec = new DataSetExecutor(getConnection());

            int rows = exec.execUpdate(String.format(INSERT_USER, name, age));
            System.out.println("User added. Rows changed: " + rows);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            System.out.println("Reflection exception");
        }
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        //Let`s suppose that our clazz has constructor (int id, String name, int age):
        DataSetExecutor exec = new DataSetExecutor(getConnection());
        return exec.execQuery(String.format(SELECT_DATASET, id), new TResultHandler<T>() {
            @Override
            public T handle(ResultSet result) throws SQLException {
                T obj = null;
                result.next();
                String name = result.getString("name");
                int age = result.getInt("age");
                try {
                    Class[] params = {int.class, String.class, int.class};
                    obj = clazz.getConstructor(params).newInstance((int)id, name, age);
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    System.out.println("Reflection eception");
                }
                return obj;
            }
        });
    }
}
