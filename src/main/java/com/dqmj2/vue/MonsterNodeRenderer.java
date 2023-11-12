package com.dqmj2.vue;

import java.awt.Component;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;

import com.dqmj2.model.MonsterEntity;
import com.dqmj2.model.Utils;

public class MonsterNodeRenderer implements TreeCellRenderer{
    @Override
    public Component getTreeCellRendererComponent(
        JTree arg0,
        Object value, 
        boolean selected,
        boolean expanded,
        boolean leaf,
        int row,
        boolean hasFocus
    ){
        JLabel label = new JLabel();
        Object userObject = ((DefaultMutableTreeNode) value).getUserObject();

        if (userObject instanceof MonsterEntity) {
            MonsterEntity monster = (MonsterEntity) userObject;
            label.setIcon(Utils.getIcon(monster.getName()));
            label.setText(monster.toString());
        }
        Font font = new Font("Arial", Font.CENTER_BASELINE, Utils.FONT_SIZE);
        label.setFont(font);
        return label;
    }
    
}
