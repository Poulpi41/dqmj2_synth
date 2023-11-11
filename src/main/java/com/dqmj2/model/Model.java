package com.dqmj2.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.dqmj2.model.db.DB_Utils;

public class Model {
    Connection conn;
    String[] choices;

    public Model(){
        turnOnDataBase();
        choices = DB_Utils.getMonsterNames(conn);
    }

    public String[] getMonsterNames() {
        return choices;
    }
    public void turnOnDataBase() {
        conn = DB_Utils.getConnection("./db/synthesis");
        //conn = DB_Utils.getConnectionDebug();
    }
    public void turnOffDataBase() {
        try { conn.close(); } catch (SQLException e) {}
    }
    public List<TableInfos> getSynthesisFor(String name) {
        return DB_Utils.getSynthesisFor(conn,name);
    }
    public TableInfos getFirstSynthesisFor(String name) {
        return DB_Utils.getFirstSynthesisFor(conn,name);
    }
    private FamilyTree recFamilyTreeFor(String name,String level,int depth) {
        if (depth <= 0) {
            return new FamilyTree(new MonsterEntity(name, level));
        }
        TableInfos ti = getFirstSynthesisFor(name);
        if (ti == null) {
            return new FamilyTree(new MonsterEntity(name, level));
        }
        FamilyTree res = new FamilyTree(new MonsterEntity(name, level));
        for (int i = 0; i < ti.number_of_parents; i++) {
            res.add(recFamilyTreeFor(ti.father_names.get(i),ti.father_levels.get(i),depth-1));
        }
        return res;
    }
    public FamilyTree getFamilyTreeFor(String name,int depth) {
        return recFamilyTreeFor(name,"",depth);
    }
    public List<TableInfos> getNfirstSynthesisFor(String name,int n) {
        return DB_Utils.getSynthesisFor(conn,name,n);
    }
    public List<TableInfos> get100FirstSynthesis(String name) {
        return getNfirstSynthesisFor(name,100);
    }
}
