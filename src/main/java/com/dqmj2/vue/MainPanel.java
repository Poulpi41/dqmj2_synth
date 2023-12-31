package com.dqmj2.vue;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import com.dqmj2.controller.Controller;
import com.dqmj2.model.Utils;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPanel extends JPanel{
    private class PrintSynthListener implements ActionListener{
        Controller controller;
        JComboBox<String> comboBox;
        PrintSynthListener(Controller controller,JComboBox<String> comboBox){
            this.controller = controller;
            this.comboBox = comboBox;
        }
        @Override
        public void actionPerformed(ActionEvent arg0) {
            controller.print100SynthsFor((String)comboBox.getSelectedItem());
        }

    }
    private class SearchLanuncher implements ActionListener {
        private Controller c;
        private JComboBox<String> nameChoice;
        private JSpinner depthChoice;
        public SearchLanuncher(Controller c,JComboBox<String> name,JSpinner depth){
            this.c = c;
            this.depthChoice = depth;
            this.nameChoice = name;
        }
        @Override
        public void actionPerformed(ActionEvent arg0) {
            int depth = (Integer)depthChoice.getValue();
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
        JSpinner spinner = new JSpinner();
        SpinnerNumberModel spinmod;
        Integer current = 2;
        Integer min = 1;
        Integer max = 128;
        Integer step = 1;
        spinmod = new SpinnerNumberModel(current,min,max,step);
        spinner.setModel(spinmod);
        spinner.setFont(font);
        JLabel label2 = new JLabel("choose the depth of the search:");
        label2.setFont(font);

        
        JButton button = new JButton("launch research");
        button.setFont(font);
        SearchLanuncher sl = new SearchLanuncher(control,comboBox,spinner);
        button.addActionListener(sl);

        JPanel line1 = new JPanel();
        line1.setLayout(new java.awt.FlowLayout());

        line1.add(label);
        line1.add(comboBox);
        line1.add(label2);
        line1.add(spinner);
        line1.add(button);


        JPanel line2 = new JPanel();
        line2.setLayout(new java.awt.FlowLayout());
        JButton button2 = new JButton("print 100 first synths for this monster");
        button2.setFont(font);
        PrintSynthListener psl = new PrintSynthListener(control,comboBox);
        button2.addActionListener(psl);
        line2.add(button2);

        this.setLayout(new GridLayout(2,1));
        this.add(line1);
        this.add(line2);
    }
}
