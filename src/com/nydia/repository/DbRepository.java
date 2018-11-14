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

    public Response getResponse(String sqlStatement) {
        if (dbConnection != null) {
            sqlStatement = "EXPLAIN (FORMAT JSON) " + sqlStatement;
            try {
                PreparedStatement prepStatement = dbConnection
                        .prepareStatement(sqlStatement);
                //prepStatement.setString(1, username);

                Gson gson = new GsonBuilder().create();

                ResultSet result = prepStatement.executeQuery();
                while (result.next()) {
                    String results = result.getString(1);
                    //System.out.println(results);
                    Response[] r = gson.fromJson(results, Response[].class);

                    //System.out.println(r[0].getPlan().getNodeType());
                    return r[0];
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}