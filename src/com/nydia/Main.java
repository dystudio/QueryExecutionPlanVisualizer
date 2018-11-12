package com.nydia;

import com.nydia.repository.DbRepository;
import com.nydia.util.DbUtil;

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;


public class Main {

    public static void main(String[] argv) {

        DbUtil.getConnection();
        DbRepository dbRepository = new DbRepository();
        dbRepository.getPassword("test");
    }

}
