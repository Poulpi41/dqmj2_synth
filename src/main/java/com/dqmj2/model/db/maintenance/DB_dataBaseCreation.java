package com.dqmj2.model.db.maintenance;


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


public class DB_dataBaseCreation {
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
    public static String strFromInputStream(java.io.InputStream stream) {
        Scanner s = new java.util.Scanner(stream).useDelimiter("\\A");
        String res =  s.hasNext() ? s.next() : "";
        s.close();
        return res;
    }
    public static String loadSql(String path){
        return strFromInputStream(Utils.streamFrom(path));
    }
    private static List<String> computeAllNamesFrom(String path){
        List<String> res = new ArrayList<String>();
        JSONArray monsterList = new JSONArray(new JSONTokener(Utils.streamFrom(path)));
        for (Object monster : monsterList.toList()) {
            res.add((String) monster);
        }
        return res;
    }
    public static void insertNullSynthMonster(Connection c){
        try {
            c.createStatement().executeUpdate("INSERT INTO SYNTH_MONSTER (parent_name,lvl) VALUES (null,null)");
        } catch (SQLException e) {}
    }
    public static String selectSynthPropQuery(String synthType,String rank){
        return "( SELECT prop_val FROM SYNTH_PROP WHERE synth_type = '"+synthType+"' and rank = '"+rank+"' )";
    }
    public static void insertParentList(Connection c,List<String> p,List<String> lv) throws SQLException{
        PreparedStatement ps = c.prepareStatement(
            "INSERT INTO parent_list (p1,lv1,p2,lv2,p3,lv3,p4,lv4) VALUES (?,?,?,?,?,?,?,?)"
        );
        ps.setString(1, p.get(0));
        ps.setString(2, lv.get(0));
        ps.setString(3, p.get(1));
        ps.setString(4, lv.get(1));
        ps.setString(5, p.get(2));
        ps.setString(6, lv.get(2));
        ps.setString(7, p.get(3));
        ps.setString(8, lv.get(3));
        ps.executeUpdate();
    }
    public static String selectParentListQuery(List<String> p,List<String> lv){
        StringBuilder res = new StringBuilder();
        res.append("( SELECT id_pl FROM parent_list WHERE ");
        for (int i = 0; i < 3; i++) {
            String currentFather = p.get(i);
            String currentFatherLevel = lv.get(i);
            if (currentFather != null) {
                res.append("p"+(i+1)+" = '"+currentFather+"' and ");
            }
            else{
                res.append("p"+(i+1)+" is null and ");
            }
            if (currentFatherLevel != null) {
                res.append("lv"+(i+1)+" = '"+currentFatherLevel+"' and ");
            }
            else{
                res.append("lv"+(i+1)+" is null and ");
            }
        }
        if (p.get(3) != null) {
            res.append("p4 = '"+p.get(3)+"' and ");
        }
        else{
            res.append("p4 is null and ");
        }
        if (lv.get(3) != null) {
            res.append("lv4 = '"+lv.get(3)+"' )");
        }
        else{
            res.append("lv4 is null )");
        }
        return res.toString();
    }
    public static void insertSynth(Connection c,String son,String synthtype,String ranktype){
        try {
            c.createStatement().executeUpdate(
                "INSERT INTO SYNTH (son_name,id_pr) VALUES ('"+son+"',"+selectSynthPropQuery(synthtype, ranktype)+" )"
            );
        } catch (SQLException e) {}
    }
    public static String selectSynthQuery(Connection c, String son,String synthtype,String ranktype){
        return "( SELECT id_s FROM SYNTH WHERE son_name = '"+son+"' and id_pr = "+selectSynthPropQuery(synthtype, ranktype)+" )";

    }
    public static void insertJoinTableEntry(Connection c,String son,String synthtype,String ranktype,List<String> p,List<String> lv){
        try {
            String s1 = selectSynthQuery(c, son, synthtype, ranktype),s2 = selectParentListQuery(p, lv);
            c.createStatement().executeUpdate(
                "INSERT INTO pl_s(id_s,id_pl) VALUES ("+s1+","+s2+")"
            );
        } catch (SQLException e) {}
    }
    public static void insertEntry(
        Connection c,String son,List<String> p,
        List<String> lv,String synthtype,
        String ranktype,int numberofparents) 
    {
        for (int i = 0; i < numberofparents; i++) {
            try{
                PreparedStatement ps = c.prepareStatement(
                    "INSERT INTO synth_monster (parent_name,lvl) VALUES (?,?)"
                );
                ps.setString(1, p.get(i));
                ps.setString(2, lv.get(i));
                ps.executeUpdate();
            }
            catch(Exception e){}
        }
        try {
            insertParentList(c, p, lv);
        } catch (SQLException e) {}
        insertSynth(c, son, synthtype, ranktype);
        insertJoinTableEntry(c, son, synthtype, ranktype, p, lv);
    }
    public static void createDB(String path) throws SQLException {
        List<String> monsterNames= computeAllNamesFrom("/json/monster_list.json");
        JSONObject synthesis = new org.json.JSONObject(new JSONTokener(Utils.streamFrom("/json/parentSonDataBase.json")));
        Connection c = DriverManager.getConnection("jdbc:h2:"+path, "sa", "");
        c.createStatement().execute(loadSql("/db/query/createTables.sql"));
        c.createStatement().execute(loadSql("/db/query/insertAllProp.sql"));
        insertAllMonster(c, monsterNames);
        insertNullSynthMonster(c);
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
                        parentsLevel.add(null);
                    }
                }
                if (numberofparents<4){
                    for (int j = numberofparents - parentsNames.size(); j < numberofparents; j++) {
                        parentsNames.add(null);
                        parentsLevel.add(null);
                    }
                }
                /* insert into database */
                insertEntry(c, son, parentsNames, parentsLevel, synthtype, ranktype, numberofparents);
            }
        }
        // c.createStatement().execute(loadSql("/db/query/makeIndex.sql"));
        c.close();
    }
    public static void main(String[] args) throws SQLException {
        createDB("~/Documents/game/DQMJ2_synthesis/synth_tree/db/synthesis");
    }
}
