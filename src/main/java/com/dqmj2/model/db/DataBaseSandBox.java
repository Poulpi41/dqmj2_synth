package com.dqmj2.model.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;
import org.json.JSONArray;
import org.json.JSONTokener;

import com.dqmj2.model.Utils;

public class DataBaseSandBox {
    private static String escapeSipmleQuote(String s) {
        return s.replace("'", "`");
    }
    public static void main(String[] a)
            throws Exception {
        
        JSONObject obj = new JSONObject(new JSONTokener(Utils.streamFrom("/json/parentSonDataBase.json")));
        JSONArray arr = new JSONArray(new JSONTokener(Utils.streamFrom("/json/monster_list.json")));
        Connection conn = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        for (int i = 0; i < arr.length(); i++) {
            String monsterName = arr.getString(i);
            JSONArray synthesisList = obj.getJSONArray(monsterName);
            for (int j = 0; j < synthesisList.length(); j++) {
                JSONObject synthesis = synthesisList.getJSONObject(j);
                int numberOfParents = synthesis.getInt("numberofparents");
                List<Object> tmp = synthesis.getJSONArray("parents").toList();
                List<String> parentNames = new ArrayList<String>();
                for (Object o : tmp) {
                    parentNames.add(escapeSipmleQuote((String) o));
                }
                
                String synthType = synthesis.getString("synthtype");
                String rankType = synthesis.getString("ranktype");
                String query = DB_Querries.insertEntry(escapeSipmleQuote(monsterName), numberOfParents, parentNames, synthType, rankType);
                conn.createStatement().execute(query);
            }
        }
        // ResultSet set = conn.createStatement().executeQuery("SELECT * FROM synths");
        conn.close();
    }
}
