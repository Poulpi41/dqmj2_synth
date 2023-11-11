package com.dqmj2.model;

import java.util.List;


public class TableInfos {
    
    public String son_name;
    public List<String> father_names;
    public List<String> father_levels;
    public int number_of_parents;
    public String synth_type;
    public String rank_type;
    /**
     * instantiate a TableInfos object
     * @constructor for the TableInfos class
     * @param son_name name of the monster that the synthesis has been searched for
     * @param father_names the name of the fathers for this synthesis
     * @param father_levels the level of the fathers for this synthesis
     * @param number_of_parents the number of parents for this synthesis
     * @param synth_type the type of the synthesis
     * @param rank_type the rank constaint of the synthesis
     */
    public TableInfos(String son_name, List<String> father_names, String synth_type, String rank_type, List<String> father_levels) {
        this.son_name = son_name;
        this.father_names = father_names;
        this.father_levels = father_levels;
        this.synth_type = synth_type;
        this.rank_type = rank_type;
        this.number_of_parents = 0;
        for (String string : father_names) {
            if (string != null) {
                this.number_of_parents++;
            }
        }
    }

}
