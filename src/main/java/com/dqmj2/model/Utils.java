package com.dqmj2.model;

import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import com.dqmj2.Main;

public class Utils {
    /**
     * get the icon for the given name
     * @param name the name of the icon
     * @return the icon for the given name
     */
    public static ImageIcon getIcon(String name){
        ImageIcon icon = null;
        InputStream stream = Utils.streamFrom("/imagesx2/"+Utils.convertForImage(name)+".png");
        if (stream != null){
            try {
                icon = new ImageIcon(ImageIO.read(stream));
            } catch (IOException e) {}
        }
        else {
            System.err.println("Error: could not find image for "+name);
        }
        return icon;
    }
    /**
     * the font size used in the gui
     */
    public static final int FONT_SIZE = 30;
    /**
     * used because of issue with sql statement and simple quote
     * @param s the string to replace the ` with ' into
     * @return the string with the ` replaced by '
     */
    public static String convertForImage(String s) {
        return s!=null ? s.replace("`", "'") : null;
    }
    /**
     * used to load file from classpath as an inputstream
     * @param path the path to the file in the classpath ex: /db/compact/synthesis.mv.db
     * @return the inputstream of the file
     */
    public static InputStream streamFrom(String path){
        Class<Main> classObj = Main.class;
        InputStream is = classObj.getResourceAsStream(path);
        if (is == null) {
            System.out.println("File not found! for path: "+path);
            return null;
        }
        return is;
    }
}
