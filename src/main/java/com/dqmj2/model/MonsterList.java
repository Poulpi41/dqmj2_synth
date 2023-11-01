package com.dqmj2.model;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONTokener;

public class MonsterList extends JSONArray{    
    MonsterList(String path){
        this(Utils.streamFrom(path));
    }
    MonsterList(InputStream is){
        super(new JSONTokener(is));
    }
    public List<String> getNames(){
        List<String> names = new ArrayList<String>();
        for (Object s : this.toList()) {
            names.add((String)s);
        }
        return names;
    }
}
