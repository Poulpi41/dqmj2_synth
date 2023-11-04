package com.dqmj2.model.db.maintenancae;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.*;

import com.dqmj2.model.db.DB_Query;



public class MainDB2 {
    public static int getSize(ResultSet rs) throws SQLException{
        if (rs == null)
            return 0;
        rs.last();
        return rs.getRow();
    }
    public static void main(String[] args) throws SQLException {
        // URL url = MainDB.class.getResource("/db/synthesis.mv.db");
        // String path = url.getPath();
        // path = path.substring(path.indexOf("/"), path.indexOf(".mv.db"));
        // System.out.println(path);
        // Connection conn = DriverManager.getConnection("jdbc:h2:zip:"+path, "sa", "");
        Connection c = DriverManager.getConnection("jdbc:h2:./src/main/resources/db/compact/synthesis", "sa", "");
        // String req = DB_Query.getResFor("abyss diver");
        String req = DB_Query.selectAllName();
        Instant timestamp = Instant.now();
        Statement st = c.createStatement();
        ResultSet rs = st.executeQuery(req);
        System.out.println("miliSecond elapsed:" + Duration.between(timestamp, Instant.now()).getNano()/1000000f);
        System.out.println("result fetched: "+getSize(rs));
        c.close();
    }
}
