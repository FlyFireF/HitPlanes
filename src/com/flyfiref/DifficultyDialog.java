package com.flyfiref;
import com.flyfiref.utils.GameUtils;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class DifficultyDialog extends JFrame{
    int difficulty;

    public DifficultyDialog(final Main gw){
        JPanel PanelSouth=new JPanel();
        JPanel PanelNorth=new JPanel();
        setResizable(false);
        JButton debug = new JButton("测试(Y)");
        JButton easy = new JButton("简单(E)");
        JButton medium = new JButton("中等(M)");
        JButton hard = new JButton("困难(H)");
        JButton hardcore = new JButton("极限(X)");
        JButton infinite = new JButton("无尽(I)");
        JCheckBox cb = new JCheckBox("无敌模式");
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        debug.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty=0;
                GameUtils.bgm.stop();
                GameUtils.playBGM(7);
                start(gw);
            }
        });
        InputMap inputmap0= debug.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputmap0.put(KeyStroke.getKeyStroke("Y"), "debug");
        ActionMap actionmap0= debug.getActionMap();
        actionmap0.put("debug",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty=0;
                GameUtils.bgm.stop();
                GameUtils.playBGM(7);
                start(gw);
            }
        });
        easy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty=1;
                GameUtils.bgm.stop();
                GameUtils.playBGM(1);
                start(gw);
            }
        });
        InputMap inputmap1= easy.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputmap1.put(KeyStroke.getKeyStroke("E"), "easy");
        ActionMap actionmap1= easy.getActionMap();
        actionmap1.put("easy",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty=1;
                GameUtils.bgm.stop();
                GameUtils.playBGM(1);
                start(gw);
            }
        });
        medium.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty=2;
                GameUtils.bgm.stop();
                GameUtils.playBGM(2);
                start(gw);
            }
        });
        InputMap inputmap2= medium.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputmap2.put(KeyStroke.getKeyStroke("M"), "medium");
        ActionMap actionmap2= medium.getActionMap();
        actionmap2.put("medium",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty=2;
                GameUtils.bgm.stop();
                GameUtils.playBGM(2);
                start(gw);
            }
        });
        hard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty=3;
                GameUtils.bgm.stop();
                GameUtils.playBGM(3);
                start(gw);
            }
        });
        InputMap inputmap3= hard.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputmap3.put(KeyStroke.getKeyStroke("H"), "hard");
        ActionMap actionmap3= hard.getActionMap();
        actionmap3.put("hard",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty=3;
                GameUtils.bgm.stop();
                GameUtils.playBGM(3);
                start(gw);
            }
        });
        hardcore.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty=4;
                GameUtils.bgm.stop();
                GameUtils.playBGM(4);
                start(gw);
            }
        });
        InputMap inputmap4= hardcore.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputmap4.put(KeyStroke.getKeyStroke("X"), "hardcore");
        ActionMap actionmap4= hardcore.getActionMap();
        actionmap4.put("hardcore",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty=4;
                GameUtils.bgm.stop();
                GameUtils.playBGM(4);
                start(gw);
            }
        });
        infinite.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty=5;
                GameUtils.bgm.stop();
                GameUtils.playBGM(1);
                start(gw);
            }
        });
        InputMap inputmap5= infinite.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        inputmap5.put(KeyStroke.getKeyStroke("I"), "infinite");
        ActionMap actionmap5= infinite.getActionMap();
        actionmap5.put("infinite",new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                difficulty=5;
                GameUtils.bgm.stop();
                GameUtils.playBGM(1);
                start(gw);
            }
        });

        PanelSouth.add(debug);
        PanelSouth.add(easy);
        PanelSouth.add(medium);
        PanelSouth.add(hard);
        PanelSouth.add(hardcore);
        PanelSouth.add(infinite);
        PanelSouth.add(cb);

        cb.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange()== ItemEvent.SELECTED) Main.undead=true;
                else if(e.getStateChange()== ItemEvent.DESELECTED) Main.undead=false;
            }
        });

        // 设置GridBagConstraints以实现在NORTH居中显示
        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.weighty = 1;
        c.anchor = GridBagConstraints.CENTER;
        PanelNorth.add(new JLabel("请选择难度"), c);

        add(PanelNorth, BorderLayout.NORTH);
        add(PanelSouth, BorderLayout.CENTER);

        setVisible(true);
        setLocationRelativeTo(null);
        setSize(300,150);
        setTitle("难度");
    }
    private void start(Main gw){
        Main.difficulty=difficulty;
        Main.state=1;
        gw.setEnabled(true);
        dispose();
    }

}
