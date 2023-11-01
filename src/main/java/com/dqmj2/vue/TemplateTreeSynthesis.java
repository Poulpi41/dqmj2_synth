package com.dqmj2.vue;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTree;

import com.dqmj2.controller.Controller;
import com.dqmj2.model.FamilyTree;

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
        JTree jtree = new JTree(tree);
        JButton button = new JButton("Back");
        button.addActionListener(new GoBackListener(c));
        this.add(jtree);
        this.add(button);
    }
}
