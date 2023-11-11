package com.dqmj2.vue;

import java.awt.Component;
import java.awt.Font;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
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
            label.setIcon(Utils.getIcon(monster.getName()));
            label.setText(monster.toString());
        }

        // if (userObject instanceof String) {
        //     label.setIcon(computeIcon((String)(userObject)));
        //     label.setText((String) userObject);
        // }
        Font font = new Font("Arial", Font.CENTER_BASELINE, Utils.FONT_SIZE);
        label.setFont(font);
        JButton button = new JButton("change");
        button.setFont(font);
        JLayeredPane both = new JLayeredPane();
        both.setLayout(new java.awt.FlowLayout());
        both.add(label);
        both.add(button);
        return both;
    }
    
}
