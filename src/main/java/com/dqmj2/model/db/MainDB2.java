package com.dqmj2.model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.*;

public class MainDB2 {
    public static void main(String[] args) throws SQLException {
        // URL url = MainDB.class.getResource("/db/synthesis.mv.db");
        // String path = url.getPath();
        // path = path.substring(path.indexOf("/"), path.indexOf(".mv.db"));
        // System.out.println(path);
        // Connection conn = DriverManager.getConnection("jdbc:h2:zip:"+path, "sa", "");
        Connection c = DriverManager.getConnection("jdbc:h2:./src/main/resources/db/synthesis", "sa", "");
        String req = DB_Sandbox.loadSql("/db/query/typicalRequest2.sql");
        Instant timestamp = Instant.now();
        c.createStatement().executeQuery(req);
        System.out.println("miliSecond elapsed:" + Duration.between(timestamp, Instant.now()).getNano()/1000000f);

        c.close();
    }
}
