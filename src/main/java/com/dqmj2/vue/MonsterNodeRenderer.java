package com.dqmj2.vue;

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

import com.dqmj2.model.MonsterEntity;
import com.dqmj2.model.Utils;

public class MonsterNodeRenderer implements TreeCellRenderer{
    
    // private static String removeLvFrom(String son){
    //     String[] parts = son.split(" ");
    //     // all but last
    //     String[] parts2 = new String[parts.length-1];
    //     for (int i = 0; i < parts2.length; i++) {
    //         parts2[i] = parts[i];
    //     }
    //     return String.join(" ",parts2);
    // }
    // private static ImageIcon computeIcon(String son){
    //     ImageIcon icon = null;
    //     InputStream firstTry = Utils.streamFrom("/imagesx2/"+son+".png");
    //     if (firstTry != null){
    //         try{
    //             icon = new ImageIcon(ImageIO.read(firstTry));
    //         }catch(Exception e){}
    //     }
    //     else {
    //         InputStream secondTry = Utils.streamFrom("/imagesx2/"+removeLvFrom(son)+".png");
    //         if (secondTry != null){
    //             try {
    //                 icon = new ImageIcon(ImageIO.read(secondTry));
    //             } catch (IOException e) {}
    //         }
    //         else {
    //             System.err.println("Error: could not find image for "+son);
    //         }
    //     }
    //     return icon;
    // }

    /**
     * get the icon for the given name
     * @param name the name of the icon
     * @return the icon for the given name
     */
    private static ImageIcon getIcon(String name){
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
    @Override
    public Component getTreeCellRendererComponent(
            JTree arg0,
            Object value, 
            boolean selected,
            boolean expanded,
            boolean leaf,
            int row,
            boolean hasFocus
            )
    {
        JLabel label = new JLabel();
        Object userObject = ((DefaultMutableTreeNode) value).getUserObject();

        if (userObject instanceof MonsterEntity) {
            MonsterEntity monster = (MonsterEntity) userObject;
            label.setIcon(getIcon(monster.getName()));
            label.setText(monster.toString());
        }

        // if (userObject instanceof String) {
        //     label.setIcon(computeIcon((String)(userObject)));
        //     label.setText((String) userObject);
        // }
        Font font = new Font("Arial", Font.CENTER_BASELINE, Utils.FONT_SIZE);
        label.setFont(font);
        return label;
    }
    
}
