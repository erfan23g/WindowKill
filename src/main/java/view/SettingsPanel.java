package view;

import controller.Update;
import model.Epsilon;
import model.GamePanelModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static controller.Constants.INITIAL_PANEL_SIZE;

public class SettingsPanel extends JPanel {
    private static SettingsPanel INSTANCE;
    public static boolean isOpen = false;
    private final JSlider difficultySlider, sensitivitySlider, volumeSlider;
    private final JButton backButton;
    public static SettingsPanel getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new SettingsPanel();
        return INSTANCE;
    }
    public SettingsPanel() {
        setDoubleBuffered(true);
        setSize(new Dimension((int) INITIAL_PANEL_SIZE.getWidth(), (int) INITIAL_PANEL_SIZE.getHeight()));
        setLocation((int) (GameFrame.getINSTANCE().getWidth() / 2 - GamePanelModel.getINSTANCE().getSize().getWidth() / 2), (int) (GameFrame.getINSTANCE().getHeight() / 2 - GamePanelModel.getINSTANCE().getSize().getHeight() / 2));
        setLayout(null);
        // Create the slider
        difficultySlider = new JSlider(JSlider.HORIZONTAL, 1, 3, 2);
        difficultySlider.setMajorTickSpacing(1);
        difficultySlider.setMinorTickSpacing(0);
        difficultySlider.setPaintTicks(true);
        difficultySlider.setPaintLabels(true);
        difficultySlider.setSnapToTicks(true);
        difficultySlider.setBounds(60, 80, 160, 40);
        difficultySlider.setBackground(Color.white);
        difficultySlider.setForeground(Color.white);

        // Add a listener to update the label when the slider value changes
        difficultySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = difficultySlider.getValue();
                Update.setDifficulty(value);
            }
        });



        // sensitivitySlider
        sensitivitySlider = new JSlider(JSlider.HORIZONTAL, 1, 100, 50);
        sensitivitySlider.setMajorTickSpacing(20);
        sensitivitySlider.setMinorTickSpacing(5);
        sensitivitySlider.setPaintTicks(true);
//        sensitivitySlider.setPaintLabels(true);
        sensitivitySlider.setBounds(60, 200, 160, 40);
        sensitivitySlider.setBackground(Color.white);
        sensitivitySlider.setForeground(Color.white);

        // Add a listener to update the label when the slider value changes
        sensitivitySlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = sensitivitySlider.getValue();
                Update.setSensitivity(value);
            }
        });


        // volumeSlider
        volumeSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, 50);
        volumeSlider.setMajorTickSpacing(20);
        volumeSlider.setMinorTickSpacing(5);
        volumeSlider.setPaintTicks(true);
//        sensitivitySlider.setPaintLabels(true);
        volumeSlider.setBounds(60, 320, 160, 40);
        volumeSlider.setBackground(Color.white);
        volumeSlider.setForeground(Color.white);

        // Add a listener to update the label when the slider value changes
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                int value = volumeSlider.getValue();
            }
        });

        // backButton
        backButton = new JButton("Back");
        backButton.setBounds(60, 390, 160, 80);
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                StartingPanel.getINSTANCE().setVisible(true);
                isOpen = false;
                GameFrame.getINSTANCE().repaint();
            }
        });


        // Add the components to the frame
        add(difficultySlider);
        add(sensitivitySlider);
        add(volumeSlider);
        add(backButton);
        setVisible(false);
        GameFrame.getINSTANCE().add(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("src/main/java/pictures/amogus.jpg").getImage(), 0, 0, getWidth(), getHeight(), null);
        g.setColor(Color.MAGENTA);
        Font font = new Font("Times New Roman", Font.PLAIN, 20);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        g.drawString("Difficulty", 70, 60);
        g.drawString("Key sensitivity", 70, 180);
        g.drawString("Volume", 70, 300);
    }
    public static void dispose() {
        GameFrame.getINSTANCE().remove(INSTANCE);
        INSTANCE = null;
        GameFrame.getINSTANCE().repaint();
    }
    public static boolean isNull() {return INSTANCE == null;}
}
