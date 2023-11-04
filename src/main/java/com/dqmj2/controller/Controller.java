package com.dqmj2.controller;


import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

// import org.json.JSONArray;

import com.dqmj2.model.FamilyTree;
import com.dqmj2.model.Model;
import com.dqmj2.vue.MainPanel;
import com.dqmj2.vue.TemplateTreeSynthesis;

public class Controller {
    private class MyWindowAdapter extends java.awt.event.WindowAdapter{
        Model mod;
        public MyWindowAdapter(Model mod){
            this.mod = mod;
        }
        @Override
        public void windowClosing(java.awt.event.WindowEvent windowEvent) {
            mod.turnOffDataBase();
            super.windowClosing(windowEvent);
        }
    }
    private JFrame  frame;
    private Model   model;

    public Controller(){
        this.frame = new JFrame();
        this.model = new Model();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setTitle("DQM Joker 2 Synthesis");
        this.frame.addWindowListener(
            new MyWindowAdapter(this.model)
        );
    }
    private FamilyTree computeFamilyTree(int depth,String name){
        return model.getFamilyTreeFor(name, depth);
    }
    public void launchSearch(int depth,String name){
        FamilyTree tree = computeFamilyTree(depth,name);
        JPanel jp = new TemplateTreeSynthesis(tree,this);
        JScrollPane tmp = new JScrollPane(jp);
        tmp.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        tmp.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        tmp.getVerticalScrollBar().setUnitIncrement(16);
        tmp.getHorizontalScrollBar().setUnitIncrement(16);

        frame.setContentPane(tmp);
        frame.pack();
        frame.setVisible(true);
    }
    public void loadMainPanel(){
        String[] choices = model.getMonsterNames();
        MainPanel mp = new MainPanel(choices,this);
        frame.setContentPane(mp);
        frame.pack();
        frame.setVisible(true);
    }
}
