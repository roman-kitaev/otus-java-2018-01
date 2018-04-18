package ru.otus.hw091.objectwriter;

import ru.otus.hw091.base.DataSet;
import ru.otus.hw091.base.MyDBService;
import ru.otus.hw091.base.UserDataSet;
import ru.otus.hw091.executors.DataSetExecutor;
import ru.otus.hw091.executors.LogExecutor;
import ru.otus.hw091.executors.TExecutor;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

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
    //private Map<String, List<Field>> scheme = new HashMap<>();
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
    private <T extends DataSet> void prepareTables(Class<T> clazz) throws SQLException{
        StringBuilder query = new StringBuilder();
        query.append("create table if not exists ");
        query.append(clazz.getSimpleName().toLowerCase());
        query.append(" (id bigint auto_increment, ");
        for(Field field : clazz.getDeclaredFields()) {
            query.append(field.getName());
            switch (field.getType().getSimpleName()) {
                case "String" :
                    query.append(" varchar(256)");
                    break;
                case "int" :
                    query.append(" int(5)");
                    break;
                //...etc.
            }
            query.append(" ,");
        }
        query.append(" primary key (id));");
        LogExecutor exec = new LogExecutor(getConnection());
        exec.execUpdate(query.toString());
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
                    list.add(new UserDataSet(/* constructor */)); //there are no constructors any more(
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
        prepareTables(user.getClass());

        StringBuilder query = new StringBuilder();
        query.append("insert into " + user.getClass().getSimpleName().toLowerCase() + " (");
        for(Field field : user.getClass().getDeclaredFields()) {
            query.append(field.getName() + " ,");
        }
        query.delete(query.length() - 2, query.length());
        query.append(") values (");
        try {
            for(Field field : user.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object value = field.get(user);
                switch (value.getClass().getSimpleName()) {
                    case "String":
                        query.append("'" + value + "'");
                        break;
                    case "Integer":
                        query.append(value);
                        break;
                    //etc.
                }
                query.append(", ");
            }
        } catch (IllegalAccessException e) {
        }
        query.delete(query.length() - 2, query.length());
        query.append(");");
        DataSetExecutor exec = new DataSetExecutor(getConnection());
        int rows = exec.execUpdate(query.toString());
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        StringBuilder query = new StringBuilder();
        query.append("select ");

        for(Field field : clazz.getDeclaredFields()) {
            query.append(field.getName() + " ,");
        }
        query.delete(query.length() - 2, query.length());
        query.append(" from " + clazz.getSimpleName().toLowerCase() + " where id=" + id + ";" );
        DataSetExecutor exec = new DataSetExecutor(getConnection());
        return exec.execQuery(query.toString(), new TResultHandler<T>() {
            @Override
            public T handle(ResultSet result) throws SQLException {
                T obj = null;
                result.next();
                try {
                    obj = clazz.getConstructor().newInstance();
                    Method setId = clazz.getDeclaredMethod("setId", long.class);
                    obj.setId(id);
                    for(Field field : clazz.getDeclaredFields()) {
                        String fieldName = field.getName();
                        String methodName = "set" + capitalizeFirstLetter(fieldName);
                        Method method = clazz.getDeclaredMethod(methodName, field.getType());
                        switch (field.getType().getSimpleName()) {
                            case "String" :
                                method.invoke(obj, result.getString(fieldName));
                                break;
                            case "int" :
                                method.invoke(obj, result.getInt(fieldName));
                                break;
                            //...etc.
                        }
                    }
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
                    System.out.println("Reflection exception");
                }
                return obj;
            }
        });
    }
    private static String capitalizeFirstLetter(String str) {
        Character firstLetter = str.charAt(0);
        firstLetter = Character.toUpperCase(firstLetter);
        return firstLetter + str.substring(1, str.length());
    }
}
