package com.dqmj2.vue;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.dqmj2.controller.Controller;
import com.dqmj2.model.TableInfos;
import com.dqmj2.model.Utils;

public class SynthListPanel extends JPanel{
    Controller controller;
    private class GoBackListener implements ActionListener{
        Controller controller;
        public GoBackListener(Controller controller){
            this.controller = controller;
        }
        public void actionPerformed(ActionEvent e){
            controller.loadMainPanel();
        }
    }
    
    public SynthListPanel(String name ,List<TableInfos> synths,Controller controller){
        super();
        this.controller = controller;
        this.setLayout(new GridLayout(1,2));
        JPanel synthsPanel = new JPanel();
        synthsPanel.setLayout(new GridLayout(synths.size()+1,1));
        JPanel header = new JPanel();
        header.setLayout(new GridLayout(1,7));

        Font font = new Font("Arial", Font.CENTER_BASELINE, Utils.FONT_SIZE);
        JLabel[] headers = new JLabel[]{
            new JLabel("resulting monster"),
            new JLabel("first parent"),
            new JLabel("second parent"),
            new JLabel("third parent"),
            new JLabel("fourth parent"),
            new JLabel("synthesis type"),
            new JLabel("rank type")
        };
        for(JLabel h : headers){
            h.setFont(font);
            header.add(h);
        }
        synthsPanel.add(header);
        for(TableInfos synth : synths){
            JPanel line = new JPanel();
            line.setLayout(new GridLayout(1,7));
            JLabel monster = new JLabel();
            monster.setFont(font);
            monster.setText(name);
            monster.setIcon(Utils.getIcon(name));

            line.add(monster);
            for (int i = 0; i < synth.number_of_parents; i++) {
                JLabel father = new JLabel();
                father.setText(synth.father_names.get(i) + synth.father_levels.get(i));
                father.setIcon(Utils.getIcon(synth.father_names.get(i)));
                father.setFont(font);
                line.add(father);
            }
            JLabel synth_type = new JLabel(synth.synth_type), rank_type = new JLabel(synth.rank_type);
            synth_type.setFont(font);
            rank_type.setFont(font);
            line.add(synth_type);
            line.add(rank_type);
            synthsPanel.add(line);
        }
        JButton b = new JButton("back");
        b.setFont(font);
        b.addActionListener(new GoBackListener(controller));
        this.add(synthsPanel);
        this.add(b);
    }

}
