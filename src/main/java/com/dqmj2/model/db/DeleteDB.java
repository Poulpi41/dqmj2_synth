package com.dqmj2.model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DeleteDB {
    public static void main(String[] args) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:h2:./src/main/resources/db/synthesis","sa","");
        c.createStatement().execute(DB_Sandbox.loadSql("/db/query/deleteAll.sql"));
        c.close();

    }
}
