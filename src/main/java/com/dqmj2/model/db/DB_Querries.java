package com.dqmj2.model.db;

import java.util.List;

public class DB_Querries {
    public static String insertEntry(
        String sonName,
        int numberOfParents,
        List<String> parentNames,
        String synthType,
        String rankType)
    {
        StringBuilder sb = new StringBuilder();
        sb.append("INSERT INTO ");
        sb.append("synths");
        sb.append(" (");
        sb.append("sonName,");
        sb.append("numberOfParents,");
        sb.append("parentName1,");
        sb.append("parentName2,");
        sb.append("parentName3,");
        sb.append("parentName4,");
        sb.append("synthType,");
        sb.append("rankType");
        sb.append(" )");
        sb.append(" VALUES (");
        sb.append("\'" + sonName + "\',");
        sb.append(numberOfParents + ",");
        sb.append("\'" + parentNames.get(0) + "\',");
        sb.append("\'" + parentNames.get(1) + "\',");
        if (numberOfParents > 2) {
            sb.append("\'" + parentNames.get(2) + "\',");
            sb.append("\'" + parentNames.get(3) + "\',");
        } else {
            sb.append("NULL,");
            sb.append("NULL,");
        }
        sb.append("\'" + synthType + "\',");
        sb.append("\'" + rankType + "\'");
        sb.append(");");
        return sb.toString();
    }
    public static String createTable(){
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ");
        sb.append("synths");
        sb.append(" (");
        sb.append("id INTEGER PRIMARY KEY AUTO_INCREMENT ,");
        sb.append("sonName TEXT NOT NULL,");
        sb.append("numberOfParents INTEGER NOT NULL,");
        sb.append("parentName1 TEXT NOT NULL,");
        sb.append("parentName2 TEXT NOT NULL,");
        sb.append("parentName3 TEXT,");
        sb.append("parentName4 TEXT,");
        sb.append("synthType TEXT NOT NULL,");
        sb.append("rankType TEXT NOT NULL");
        sb.append(");");
        return sb.toString();
    }
    public static String deleteTable(){
        StringBuilder sb = new StringBuilder();
        sb.append("DROP TABLE IF EXISTS ");
        sb.append("synths");
        sb.append(";");
        return sb.toString();
    }
    public static String selectAll(){
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM ");
        sb.append("synths");
        sb.append(";");
        return sb.toString();
    }
}
