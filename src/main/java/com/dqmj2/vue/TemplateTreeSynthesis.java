package com.dqmj2.vue;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTree;

import com.dqmj2.controller.Controller;
import com.dqmj2.model.FamilyTree;
import com.dqmj2.model.Utils;

public class TemplateTreeSynthesis extends JPanel{
    
    private class GoBackListener implements ActionListener{
        Controller controller;
        public GoBackListener(Controller controller){
            this.controller = controller;
        }
        public void actionPerformed(ActionEvent e){
            controller.loadMainPanel();
        }
    }
    public TemplateTreeSynthesis(FamilyTree tree,Controller c){
        super();
        Font font = new Font("Arial", Font.CENTER_BASELINE, Utils.FONT_SIZE);
        JTree jtree = new JTree(tree);
        jtree.setFont(font);
        jtree.setCellRenderer(new MonsterNodeRenderer());
        // for (int i = 0; i < jtree.getRowCount(); i++) {
        //     jtree.expandRow(i);
        // }
        JButton button = new JButton("Back");
        button.setFont(font);
        button.addActionListener(new GoBackListener(c));

        this.add(jtree);
        this.add(button);
    }
}
