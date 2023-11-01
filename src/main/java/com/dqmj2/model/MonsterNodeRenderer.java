package com.dqmj2.model;

import java.awt.Component;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

public class MonsterNodeRenderer implements TreeCellRenderer{
    
    private static String removeLvFrom(String son){
        String[] parts = son.split(" ");
        // all but last
        String[] parts2 = new String[parts.length-1];
        for (int i = 0; i < parts2.length; i++) {
            parts2[i] = parts[i];
        }
        return String.join(" ",parts2);
    }
    private static ImageIcon computeIcon(String son){
        ImageIcon icon = null;
        // try {
        //     icon = new ImageIcon("src/main/resources/images/"+son+".webp");
        // }
        // catch (Exception e){
        //     try {
        //         icon = new ImageIcon("src/main/resources/images/"+removeLvFrom(son)+".webp");
        //     }
        //     catch (Exception e2){
        //         System.err.println("Error: could not find image for "+son);
        //     }
        // }
        InputStream firstTry = Utils.streamFrom("/images/"+son+".png");
        if (firstTry != null){
            try{
                icon = new ImageIcon(ImageIO.read(firstTry));
            }catch(Exception e){}
        }
        else {
            InputStream secondTry = Utils.streamFrom("/images/"+removeLvFrom(son)+".png");
            if (secondTry != null){
                try {
                    icon = new ImageIcon(ImageIO.read(secondTry));
                } catch (IOException e) {}
            }
            else {
                System.err.println("Error: could not find image for "+son);
            }
        }
        return icon;
    }
    @Override
    public Component getTreeCellRendererComponent(
            JTree arg0, Object value, 
            boolean selected, boolean expanded, boolean leaf,
            int row, boolean hasFocus) {
        JLabel label = new JLabel();
        Object userObject = ((DefaultMutableTreeNode) value).getUserObject();

        if (userObject instanceof String) {
            //InputStream imgStream = getClass().getResourceAsStream("/images/" + (String)(userObject) + ".webp");
            label.setIcon(computeIcon((String)(userObject)));
            label.setText((String) userObject);
        }
        Font font = new Font("Arial", Font.CENTER_BASELINE, Utils.FONT_SIZE);
        label.setFont(font);
        return label;
    }
    
}
