package com.nydia.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DbUtil {
    private static Connection dbConnection = null;

    public static Connection getConnection() {
        if (dbConnection != null) {
            return dbConnection;
        } else {
            try {
                InputStream inputStream = DbUtil.class.getClassLoader()
                        .getResourceAsStream("com/nydia/util/db.properties");
                Properties properties = new Properties();
                properties.load(inputStream);

                String dbDriver = properties.getProperty("dbDriver");
                String connectionUrl = properties
                        .getProperty("connectionUrl");
                String username = properties.getProperty("username");
                String password = properties.getProperty("password");

                Class.forName(dbDriver).newInstance();
                dbConnection = DriverManager.getConnection(connectionUrl,
                        username, password);
                System.out.println("Connected to the database server successfully.");
            } catch (SQLException e) {
                System.out.println("Cannot connect to database!");
                e.printStackTrace();
            } catch (Exception e) {
                //System.out.println("Cannot connect to database!");
                e.printStackTrace();
            }
            return dbConnection;
        }
    }
}