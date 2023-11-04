package com.dqmj2.model.db;


import com.dqmj2.model.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.json.JSONArray;

import com.dqmj2.model.MonsterList;

public class DB_Sandbox {
    public static void insertAllMonster(Connection c,List<String> monsterNames){
        for (String string : monsterNames) {
            try {
                c
                .createStatement()
                .executeUpdate(
                    "INSERT INTO MONSTER (NAME) VALUES ('"+string+"')"
                );
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    private static String firstCoupleId(List<String> parentsName ,List<String> parentsLevel){
        return 
            "( SELECT ID_COUPLE "+
            "FROM MONSTER_COUPLE "+
            "WHERE NAME_MONSTER1 = '"+parentsName.get(0)+"' "+
            "AND NAME_MONSTER2 = '"+parentsName.get(1)+"' "+
            (!parentsLevel.get(0).equals("") ? "AND LEVEL_MONSTER1 = '"+parentsLevel.get(0)+"' " : "")+
            (!parentsLevel.get(1).equals("") ? "AND LEVEL_MONSTER2 = '"+parentsLevel.get(1)+"' " : "")+
            ")"
            ;
    }
    private static String secondCoupleId(List<String> parentsName ,List<String> parentsLevel){
        return 
            "( SELECT ID_COUPLE "+
            "FROM MONSTER_COUPLE "+
            "WHERE NAME_MONSTER1 = '"+parentsName.get(2)+"' "+
            "AND NAME_MONSTER2 = '"+parentsName.get(3)+"' "+
            (!parentsLevel.get(2).equals("") ? "AND LEVEL_MONSTER1 = '"+parentsLevel.get(2)+"' " : "")+
            (!parentsLevel.get(3).equals("") ? "AND LEVEL_MONSTER2 = '"+parentsLevel.get(3)+"' " : "")+
            ")"
            ;
    }
    private static String propId(String synthtype,String ranktype){
        return 
            "( SELECT ID_PROPERTY "+
            "FROM SYNTH_PROPERTY "+
            "WHERE SYNTH_TYPE = '"+synthtype+"' "+
            "AND RANK_TYPE = '"+ranktype+"' "+
            ")"
            ;
    }
    public static void insertOneSynthesis(Connection c,String son,String synthtype,String ranktype,int numberofparents,List<String> parentsName,List<String> parentsLevel) throws SQLException{
        
        PreparedStatement ps = c.prepareStatement(
            "insert into MONSTER_COUPLE(name_monster1,name_monster2,level_monster1,level_monster2)"+
            "values (?,?,?,?)"
        );
        ps.setString(1, parentsName.get(0));
        ps.setString(2, parentsName.get(1));
        ps.setString(3, parentsLevel.get(0));
        ps.setString(4, parentsLevel.get(1));

        try{
            ps.execute();
        }catch (SQLException e){
            ps.close();
        }
        if (numberofparents>2){
            PreparedStatement ps2 = c.prepareStatement(
                "insert into MONSTER_COUPLE(name_monster1,name_monster2,level_monster1,level_monster2)"+
                "values (?,?,?,?)"
            );
            ps2.setString(1, parentsName.get(2));
            ps2.setString(2, parentsName.get(3));
            ps2.setString(3, parentsLevel.get(2));
            ps2.setString(4, parentsLevel.get(3));
            try {
                ps2.execute();
            }catch(SQLException e){
                ps2.close();
            }
        }
        String id_fisrt, id_second, id_prop;
        id_fisrt = firstCoupleId(parentsName, parentsLevel);
        id_second = (numberofparents>2) ? secondCoupleId(parentsName, parentsLevel) : null;
        id_prop = propId(synthtype, ranktype);
        PreparedStatement ps3_synthesis = c.prepareStatement(
            "insert into SYNTHESIS(id_first_couple,id_second_couple,id_property,number_of_parents,son_name)"+
            "values ("+id_fisrt+","+id_second+","+id_prop+",?,?)"
        );
        ps3_synthesis.setInt(1, numberofparents);
        ps3_synthesis.setString(2, son);
        ps3_synthesis.execute();
    }
    public static String strFromInputStream(java.io.InputStream stream) {
        Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
        String res =  s.hasNext() ? s.next() : "";
        s.close();
        return res;
    }
    public static String loadSql(String path){
        return strFromInputStream(Utils.streamFrom(path));
    }
    public static void main(String[] args) throws SQLException {
        List<String> monsterNames= new MonsterList("/json/monster_list.json").getNames();
        JSONObject synthesis = new org.json.JSONObject(new JSONTokener(Utils.streamFrom("/json/parentSonDataBase.json")));
        Connection c = DriverManager.getConnection("jdbc:h2:./src/main/resources/db/synthesis", "sa", "");
        c.createStatement().execute(loadSql("/db/query/createTables.sql"));
        c.createStatement().execute(loadSql("/db/query/insertAllProperty.sql"));
        insertAllMonster(c, monsterNames);
        /* for each son */
        for (String son: monsterNames) {
            JSONArray synthesisList = synthesis.getJSONArray(son);
            /* for each synthesis */
            for (int i = 0; i < synthesisList.length(); i++) {
                JSONObject synthesisObject = (JSONObject) synthesisList.get(i);
                String synthtype = synthesisObject.getString("synthtype");
                String ranktype = synthesisObject.getString("ranktype");
                int numberofparents = synthesisObject.getInt("numberofparents");
                List<String> parentsNames = new ArrayList<String>();
                Pattern pattern = Pattern.compile("\\ lv\\.[0-9]{1,3}");
                List<String> parentsLevel = new ArrayList<String>();
                /* for each parent */
                for (Object parent : synthesisObject.getJSONArray("parents").toList()) {
                    String parentName = (String) parent;
                    Matcher matcher = pattern.matcher(parentName);
                    if (matcher.find()) {
                        parentsNames.add(parentName.substring(0,parentName.indexOf(" lv.")));
                        parentsLevel.add(parentName.substring(parentName.indexOf(" lv."), parentName.length()));
                    } else {
                        parentsNames.add(parentName);
                        parentsLevel.add("");
                    }
                }
                /* insert into database */
                insertOneSynthesis(c, son, synthtype, ranktype, numberofparents, parentsNames, parentsLevel);
            }
        }
        c.close();
    }
}
