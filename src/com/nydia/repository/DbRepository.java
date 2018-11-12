package com.nydia.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.nydia.model.Response;
import com.nydia.util.DbUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DbRepository {
    private Connection dbConnection;

    public DbRepository() {
        dbConnection = DbUtil.getConnection();
    }

    public String getPassword(String username) {
        if (dbConnection != null) {
            try {
                PreparedStatement prepStatement = dbConnection
                        .prepareStatement("EXPLAIN (FORMAT JSON) SELECT publicationType, COUNT(*) AS count\n" +
                                "FROM pubschema_complete.Publication\n" +
                                "WHERE year >= 2000 AND year <= 2017\n" +
                                "GROUP BY publicationType;\n");
                //prepStatement.setString(1, username);

                Gson gson = new GsonBuilder().create();

                ResultSet result = prepStatement.executeQuery();
                while (result.next()) {
                    String results = result.getString(1);
                    System.out.println(results);
                    Response[] r = gson.fromJson(results, Response[].class);

                    System.out.println(r[0].getPlan().getNodeType());
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return "errorWhatHappened";
    }

}