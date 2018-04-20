package ru.otus.hw091.objectwriter;

import ru.otus.hw091.base.DataSet;
import ru.otus.hw091.base.MyDBService;
import ru.otus.hw091.base.UserDataSet;
import ru.otus.hw091.executors.DataSetExecutor;
import ru.otus.hw091.executors.LogExecutor;

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
    private Map<String, Field[]> scheme = new HashMap<>();
    public MyDBServiceObjectWriter() {
        connection = ru.otus.hw091.objectwriter.ConnectionHelper.getConnection();
    }

    @Override
    public <T extends DataSet> void addScheme(Class<T> clazz) throws SQLException{
        scheme.put(clazz.getSimpleName().toLowerCase(), clazz.getDeclaredFields());
        prepareTables(clazz);
        System.out.println("Scheme <<" + clazz.getSimpleName().toLowerCase() + ">> was added!");
    }

    @Override
    public <T extends DataSet> void addSchemes(Class<T>... clazzes) throws SQLException{
        for(Class<T> clazz : clazzes) {
            addScheme(clazz);
        }
    }

    @Override
    public <T extends DataSet> void save(T user) throws SQLException {
        DataSetExecutor exec = new DataSetExecutor(getConnection());
        int rows = exec.execUpdate(prepareSave(user));
        System.out.println("An instance of <<" + user.getClass().getSimpleName().toLowerCase() + ">> was saved and " + rows + " row(s) was updated!");
    }

    @Override
    public <T extends DataSet> T load(long id, Class<T> clazz) throws SQLException {
        DataSetExecutor exec = new DataSetExecutor(getConnection());
        return exec.execQuery(prepareLoad(id, clazz), new TResultHandler<T>() {
            @Override
            public T handle(ResultSet result) throws SQLException {
                T obj = null;
                result.next();
                try {
                    obj = clazz.getConstructor().newInstance();
                    obj.setId(id);
                    for(Field field : scheme.get(clazz.getSimpleName().toLowerCase())) {
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
                if(obj != null) System.out.println("An instance of <<" + clazz.getSimpleName().toLowerCase() + ">> was loaded!");
                return obj;
            }
        });
    }
    private static String capitalizeFirstLetter(String str) {
        Character firstLetter = str.charAt(0);
        firstLetter = Character.toUpperCase(firstLetter);
        return firstLetter + str.substring(1, str.length());
    }

    private <T extends DataSet> void prepareTables(Class<T> clazz) throws SQLException{
        String tableName = clazz.getSimpleName().toLowerCase();
        StringBuilder query = new StringBuilder();
        query.append("create table if not exists ");
        query.append(tableName);
        query.append(" (id bigint auto_increment, ");
        for(Field field : scheme.get(tableName)) {
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

    private <T extends DataSet> String prepareSave(T user) throws SQLException{
        String tableName = user.getClass().getSimpleName().toLowerCase();

        if(!scheme.containsKey(tableName)) {
            System.out.println("I have not seen <<" + tableName + ">>  before.");
            addScheme(user.getClass());
        }

        StringBuilder query = new StringBuilder();
        query.append("insert into " + tableName + " (");
        for(Field field : scheme.get(tableName)) {
            query.append(field.getName() + " ,");
        }
        query.delete(query.length() - 2, query.length());
        query.append(") values (");
        try {
            for(Field field : scheme.get(tableName)) {
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

        return query.toString();
    }

    public <T extends DataSet> String prepareLoad(long id, Class<T> clazz) throws SQLException {
        String tableName = clazz.getSimpleName().toLowerCase();

        if(!scheme.containsKey(tableName)) {
            System.out.println("I have not seen <<" + tableName + ">>  before.");
            addScheme(clazz);
        }

        StringBuilder query = new StringBuilder();
        query.append("select ");

        for(Field field : scheme.get(tableName)) {
            query.append(field.getName() + " ,");
        }
        query.delete(query.length() - 2, query.length());
        query.append(" from " + tableName + " where id=" + id + ";" );

        return query.toString();
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
    public String getMetaData() {/*old implementation*/
        throw new UnsupportedOperationException();}

    @Override
    public void prepareTables() throws SQLException {/*old implementation*/
        throw new UnsupportedOperationException();}

    @Override
    public void addUsers(String... names) throws SQLException {/*old implementation*/
        throw new UnsupportedOperationException();}

    @Override
    public String getUserName(int id) throws SQLException {/*old implementation*/
        throw new UnsupportedOperationException();}


    @Override
    public List<String> getAllNames() throws SQLException {/*old implementation*/
        throw new UnsupportedOperationException();}

    @Override
    public List<UserDataSet> getAllUsers() throws SQLException {/*old implementation*/
        throw new UnsupportedOperationException();}

    @Override
    public void deleteTables() throws SQLException {/*old implementation*/
        throw new UnsupportedOperationException();}


}
