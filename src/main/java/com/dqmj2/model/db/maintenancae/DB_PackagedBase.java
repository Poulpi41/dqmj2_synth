package com.dqmj2.model.db.maintenancae;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.h2.tools.Backup;

public class DB_PackagedBase {
    public static void main(String[] args) throws SQLException {
        Connection c = DriverManager.getConnection("jdbc:h2:./src/main/resources/db/synthesis", "sa", "");
        //shutdown defrag; the base
        c.createStatement().execute("SHUTDOWN DEFRAG");
        c.close();
        Backup backup = new Backup();
        backup.runTool("-file","./src/main/resources/db/synthesis.zip", "-dir", "./src/main/resources/db");
    }
}
