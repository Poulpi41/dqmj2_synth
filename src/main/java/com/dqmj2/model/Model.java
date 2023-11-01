package com.dqmj2.model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

public class Model {
    private JSONArray monsterNames;
    private JSONObject parentSonRelationship;
    private String[] forComboBox;
    public Model(){
        monsterNames = new JSONArray(new JSONTokener(Utils.streamFrom("/json/monster_list.json")));
        forComboBox = monsterNames.toList().toArray(new String[0]);
        parentSonRelationship = new JSONObject(new JSONTokener(Utils.streamFrom("/json/parentSonDataBase.json")));
    }
    public String[] getMonsterNames(){
        return forComboBox;
    }
    public JSONArray getListParentsFor(String name,int index){
        JSONArray tmp = parentSonRelationship.getJSONArray(name);
        if (tmp.length() < 1) {
            return new JSONArray();
        }
        if (index >= tmp.length()) {
            return new JSONArray();
        }
        JSONObject choices = tmp.getJSONObject(index);
        return choices.getJSONArray("parents");
    }
    public JSONArray getListParentsFor(String name){
        return getListParentsFor(name,0);
    }
}
