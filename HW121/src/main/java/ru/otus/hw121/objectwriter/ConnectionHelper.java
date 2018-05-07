package ru.otus.hw121.objectwriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

class ConnectionHelper {

    static Connection getConnection() {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());

            String url = "jdbc:mysql://" +       //db type
                    "localhost:" +               //host name
                    "3306/" +                    //port
                    "mydbtest?" +              //db name
                    "user=root&" +              //login
                    "password=root&" +          //password
                    "useJDBCCompliantTimezoneShift=true&" +
                    "useLegacyDatetimeCode=false&" +
                    "serverTimezone=UTC&" +
                    "useSSL=false";              //do not use Secure Sockets Layer


            return DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}