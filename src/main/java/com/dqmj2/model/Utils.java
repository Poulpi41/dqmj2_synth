package com.dqmj2.model;

import java.io.InputStream;

import com.dqmj2.Main;

public class Utils {
    public static final int FONT_SIZE = 50;
    public static InputStream streamFrom(String path){
        Class<Main> classObj = Main.class;
        InputStream is = classObj.getResourceAsStream(path);
        if (is == null) {
            System.out.println("File not found!");
            return null;
        }
        return is;
    }
}
