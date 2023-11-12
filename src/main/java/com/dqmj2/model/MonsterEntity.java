package com.dqmj2.model;

public class MonsterEntity {
    String name;
    String level;
    public MonsterEntity(String name,String level){
        this.name = name;
        this.level = level;
    }

    public String getName(){
        return name;
    }
    @Override
    public String toString() {
        return ""+name+(level==null?"":" "+level);
    }
}
