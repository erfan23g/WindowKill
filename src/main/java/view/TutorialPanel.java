package view;

import controller.Update;
import model.Epsilon;
import model.GamePanelModel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static controller.Constants.INITIAL_PANEL_SIZE;

public class TutorialPanel extends JPanel implements ActionListener {
    private static TutorialPanel INSTANCE;
    private final String tutorial = "Welcome to WindowKil.\nIn this game you control\na circular character called Epsilon\nand you have to fight the evil squares and triangles\nwho try to trap you in a frame\nand get over your entire computer.\nThese enemies attack you in 3 waves.\nYou can move with the WASD keys.\nYou can open the store with\nthe B key on your keyboard\nand activate the ability that you have already\nbought in the skill tree with the F key.\nHolding the J key lets you see the game information.\nAll of these keys can be changed according\nto your will in the settings.\n\nMade by Erfan Ghorbani";
    public static TutorialPanel getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new TutorialPanel();
        return INSTANCE;
    }
    private final JButton backButton;
    public TutorialPanel() {
        setDoubleBuffered(true);
        setSize(new Dimension((int) INITIAL_PANEL_SIZE.getWidth(), (int) INITIAL_PANEL_SIZE.getHeight()));
        setLocation((int) (GameFrame.getINSTANCE().getWidth() / 2 - GamePanelModel.getINSTANCE().getSize().getWidth() / 2), (int) (GameFrame.getINSTANCE().getHeight() / 2 - GamePanelModel.getINSTANCE().getSize().getHeight() / 2));
        setLayout(null);
//
//        JLabel label = new JLabel(tutorial);
//        label.setForeground(Color.cyan);
//        label.setVerticalTextPosition(JLabel.TOP);
//        label.setHorizontalTextPosition(JLabel.LEFT);
//        label.setBounds(60, 80, 160, 300);
//        add(label);


        backButton = new JButton("Back");
        backButton.setBounds(60, 460, 160, 80);
        backButton.addActionListener(this);
        add(backButton);
        setVisible(false);
        GameFrame.getINSTANCE().add(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == backButton) {
            this.setVisible(false);
            StartingPanel.getINSTANCE().setVisible(true);
            GameFrame.getINSTANCE().repaint();
        }
        repaint();
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("src/main/java/pictures/amogus.jpg").getImage(), 0, 0, getWidth(), getHeight(), null);
        g.setColor(Color.lightGray);
        String[] lines = tutorial.split("\n");
        Font font = new Font("Times New Roman", Font.PLAIN, 17);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int y = 30;
        for (String line : lines) {
            g.drawString(line, 60, y);
            y += fm.getHeight() + 1;
        }
    }
}
