package com.dqmj2.model.db;

import java.net.URL;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.dqmj2.model.db.maintenancae.MainDB;
import com.dqmj2.model.TableInfos;

public class DB_Utils {
    /**
     * get a connection to the database
     * @param filePath path to the database file
     * @return a connection to the database null if connection failed
     */
    public static Connection getConnection(String filePath) {
        URL url = MainDB.class.getResource(filePath);
        String path = url.getPath();
        path = path.substring(path.indexOf("/"), path.indexOf(".mv.db"));
        System.out.println(path);
        try {
            Connection conn = DriverManager.getConnection("jdbc:h2:zip:"+path, "sa", "");
            return conn;
        } catch (SQLException e) {
            System.err.println("Error: could not connect to the database");
            return null;
        }
    }
    public static Connection getConnectionDebug() {
        try {
            return DriverManager.getConnection("jdbc:h2:./src/main/resources/db/compact/synthesis", "sa", "");
        } catch (SQLException e) {
            System.err.println("Error: could not connect to the database");
            return null;
        }
    }

    /**
     * get the names of all the monsters in the database
     * @param conn the connection to the database
     * @return the names of all the monsters in the database, null if the query failed
     */
    public static String[] getMonsterNames(Connection conn) {
        try {
            ResultSet rs = conn.createStatement().executeQuery(DB_Query.selectAllName());
            ArrayList<String> res = new ArrayList<String>();
            while (rs.next()) {
                res.add(rs.getString(1));
            }
            return res.toArray(new String[0]);
        } catch (SQLException e) {
            System.err.println("Error: could not get monster names");
            return null;
        }
    }

    /**
     * get the synthesis for a monster
     * @param conn the connection to the database
     * @param name the name of the monster to search the synthesis for
     * @return the synthesis for a monster as a list, null if the query failed
     */
    public static List<TableInfos> getSynthesisFor(Connection conn,String name){
        return getSynthesisFor(conn, name, 0);
    }
    /**
     * get the synthesis for a monster
     * @param conn the connection to the database
     * @param name the name of the monster to search the synthesis for
     * @param number_of_results number of results to retrieve , if 0 then retrieve all results
     * @return the synthesis for a monster as a list, null if the query failed
     */
    public static List<TableInfos> getSynthesisFor(Connection conn,String name, int number_of_results) {
        try {
            ResultSet rs = conn.createStatement().executeQuery(DB_Query.getResFor(name,number_of_results));
            List<TableInfos> res = new ArrayList<TableInfos>();
            while (rs.next()) {
                List<String> fathersName = new ArrayList<String>(), fathersLevel = new ArrayList<String>();
                for (int j = 2; j < 6; j++) {
                    fathersName.add(rs.getString(j));
                }
                for (int j = 8; j < 12; j++) {
                    fathersLevel.add(rs.getString(j));
                }
                res.add(new TableInfos(
                    rs.getString(1), 
                    fathersName,
                    rs.getString(6), 
                    rs.getString(7),
                    fathersLevel, 
                    rs.getInt(12)
                ));
            }
            return res;
        } catch (SQLException e) {
            return null;
        }
    }
    public static TableInfos getFirstSynthesisFor(Connection conn,String name) {
        List<TableInfos> tmp = getSynthesisFor(conn,name,1);
        if (tmp == null || tmp.size() == 0)
            return null;
        return tmp.get(0);
    }
}
