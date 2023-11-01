package com.dqmj2.controller;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import org.json.JSONArray;

import com.dqmj2.model.FamilyTree;
import com.dqmj2.model.Model;
import com.dqmj2.vue.MainPanel;
import com.dqmj2.vue.TemplateTreeSynthesis;

public class Controller {
    JFrame  frame;
    JPanel  currentPanel;
    Model   model;
    public Controller(){
        this.frame = new JFrame();
        this.model = new Model();
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    private FamilyTree computeFamilyTree(int depth,String name){
        FamilyTree tree = new FamilyTree(name);
        JSONArray parents = new JSONArray();
        try{
            parents = model.getListParentsFor(name);
        }
        catch(Exception e){}
        if(depth == 0){
            return tree;
        }
        for (Object object : parents) {
            String str = (String)object;
            tree.add(computeFamilyTree(depth-1, str));
        }
        return tree;
    }
    public void launchSearch(int depth,String name){
        FamilyTree tree = computeFamilyTree(depth,name);
        currentPanel = new TemplateTreeSynthesis(tree,this);
        frame.setContentPane(currentPanel);
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
