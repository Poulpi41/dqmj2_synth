package com.dqmj2.model.db;

public class DB_Query {

    /**
     * create the query to be send to the database to retrieve number_of_results results
     * @param name name of the monster to search the synthesis for 
     * @param number_of_results number of results to retrieve , if 0 then retrieve all results
     * @return the query to be send to the database as a string
     */
    public static String getResFor(String name,int number_of_results) {
        StringBuilder sb = new StringBuilder();
        /*
            select son_name as son,p1,p2,p3,p4,lv1,lv2,lv3,lv4,synth_type,rank
            from synth
            join SYNTH_PROP on SYNTH_PROP.prop_val = synth.id_pr
            join pl_s on pl_s.id_s = synth.id_s
            join PARENT_LIST on PARENT_LIST.ID_PL = pl_s.id_pl
            where son_name = 'abyss diver'
            order by prop_val desc
        */
        if (number_of_results<=0)
            sb.append("select son_name,");
        else
            sb.append("select top "+number_of_results+" son_name,");
        sb.append("p1,p2,p3,p4,synth_type,rank,lv1,lv2,lv3,lv4\n");
        sb.append("from synth\n");
        sb.append("join SYNTH_PROP on SYNTH_PROP.prop_val = synth.id_pr\n");
        sb.append("join pl_s on pl_s.id_s = synth.id_s\n");
        sb.append("join PARENT_LIST on PARENT_LIST.ID_PL = pl_s.id_pl\n");
        sb.append("where son_name = '"+name+"'\n");
        sb.append("order by prop_val desc");
        return  sb.toString();
    }

    /**
     * create the query to be send to the database to retrieve all the synthesis for a monster
     * @param name name of the monster to search the synthesis for 
     * @return the query to be send to the database as a string
     */
    public static String getResFor(String name) {
        return getResFor(name,0);
    }

    /**
     * create the query to be send to the database to retrieve the names of all the monsters
     * @return the query to be send to the database as a string
     */
    public static String selectAllName(){
        return "SELECT * FROM MONSTER";
    }
}
