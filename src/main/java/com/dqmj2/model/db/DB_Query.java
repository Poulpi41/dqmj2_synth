package com.dqmj2.model.db;

public class DB_Query {
    public static String convertForImage(String s) {
        return s.replace("`", "'");
    }
}
