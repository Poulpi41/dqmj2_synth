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
        if (number_of_results<=0)
            sb.append("select son_name,");
        else
            sb.append("select top "+number_of_results+" son_name,");
                sb.append("mc1.name_monster1 as parent1,");
                sb.append("mc1.name_monster2 as parent2,");
                sb.append("c2.name_monster1 as parent3,");
                sb.append("c2.name_monster2 as parent4,");
                sb.append("synth_property.synth_type,");
                sb.append("synth_property.rank_type,");
                sb.append("mc1.level_monster1 as level1,");
                sb.append("mc1.level_monster2 as level2,");
                sb.append("c2.level_monster1 as level3,");
                sb.append("c2.level_monster2 as level4,");
                sb.append("synthesis.number_of_parents\n");
        sb.append("from synthesis\n");
        sb.append("join monster_couple mc1 on mc1.id_couple = synthesis.ID_FIRST_COUPLE\n");
        sb.append("join synth_property on synthesis.id_property=synth_property.id_property\n");
        sb.append("left outer join monster_couple c2 on c2.id_couple = synthesis.ID_SECOND_COUPLE\n");
        sb.append("where son_name = '"+name+"'\n");
        //sb.append("order by (synth_property.prop_value) desc \n");
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
