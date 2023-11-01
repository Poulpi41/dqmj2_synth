package com.dqmj2.vue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.text.Utilities;

import com.dqmj2.controller.Controller;
import com.dqmj2.model.Utils;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel{
    private class SearchLanuncher implements ActionListener {
        private Controller c;
        private JComboBox<String> nameChoice;
        private JComboBox<Integer> depthChoice;
        public SearchLanuncher(Controller c,JComboBox<String> name,JComboBox<Integer> depth){
            this.c = c;
            this.depthChoice = depth;
            this.nameChoice = name;
        }
        @Override
        public void actionPerformed(ActionEvent arg0) {
            int depth = (Integer)depthChoice.getSelectedItem();
            String name = (String)nameChoice.getSelectedItem();
            c.launchSearch(depth,name);
        }
        
        
    }
    public MainPanel(String[] choices,Controller control){
        super();
        
        Font font = new Font("Arial", Font.CENTER_BASELINE, Utils.FONT_SIZE);
        JLabel label = new JLabel("choose a monster:");
        label.setFont(font);
        JComboBox<String> comboBox = new JComboBox<String>(choices);
        comboBox.setFont(font);
        JComboBox<Integer> comboBox2 = new JComboBox<Integer>(new Integer[]{
            1,2,3,4,5,6,7,8,9,10,
            11,12,13,14,15,16,17,18,19,20,
            21,22,23,24,25,26,27,28,29,30,
            31,32
        });
        comboBox2.setFont(font);
        JButton button = new JButton("launch research");
        button.setFont(font);
        SearchLanuncher sl = new SearchLanuncher(control,comboBox,comboBox2);
        button.addActionListener(sl);

        this.add(label);
        this.add(comboBox);
        this.add(comboBox2);
        this.add(button);

    }
}
