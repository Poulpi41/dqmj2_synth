package com.dqmj2.model.db.maintenance;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;

public class MainDB {
    public static void main(String[] args) throws SQLException {
        URL url = MainDB.class.getResource("/db/compact/synthesis.mv.db");
        String path = url.getPath();
        path = path.substring(path.indexOf("/"), path.indexOf(".mv.db"));
        System.out.println(path);
        Connection conn = DriverManager.getConnection("jdbc:h2:zip:"+path, "sa", "");
        String req = DB_dataBaseCreation.loadSql("/db/query/typicalRequest2.sql");
        Instant timestamp = Instant.now();
        conn.createStatement().executeQuery(req);
        System.out.println("miliSecond elapsed:" + Duration.between(timestamp, Instant.now()).getNano()/1000000f);
        conn.close();
    }
}
