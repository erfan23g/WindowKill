package view;

import controller.KeyCodes;
import controller.Update;
import model.Epsilon;
import model.GamePanelModel;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.*;
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
    private final JButton movementButton, infoButton, storeButton, abilityButton;
    private String activeButton;

    public static SettingsPanel getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new SettingsPanel();
        return INSTANCE;
    }

    public SettingsPanel() {
        setDoubleBuffered(true);
        setSize(new Dimension((int) INITIAL_PANEL_SIZE.getWidth(), (int) INITIAL_PANEL_SIZE.getHeight()));
        setLocation((int) (GameFrame.getINSTANCE().getWidth() / 2 - GamePanelModel.getINSTANCE().getSize().getWidth() / 2), (int) (GameFrame.getINSTANCE().getHeight() / 2 - GamePanelModel.getINSTANCE().getSize().getHeight() / 2));
        setLayout(null);
//        addKeyListener(this);
        Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
            @Override
            public void eventDispatched(AWTEvent event) {
                if (event instanceof KeyEvent) {
                    KeyEvent keyEvent = (KeyEvent) event;
                    if (keyEvent.getID() == KeyEvent.KEY_PRESSED) {
                        if (isOpen) {
                            System.out.println("hi");
                            changeKeys(keyEvent.getKeyCode());
                        }
                    }
                }
            }
        }, AWTEvent.KEY_EVENT_MASK);

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

                movementButton.setForeground(Color.black);
                infoButton.setForeground(Color.black);
                storeButton.setForeground(Color.black);
                abilityButton.setForeground(Color.black);
                activeButton = "";
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


        movementButton = new JButton("Change the movement keys");
        infoButton = new JButton("Change information key");
        storeButton = new JButton("Change the store key");
        abilityButton = new JButton("Change the ability key");
        movementButton.setBounds(getWidth() - 220, 40, 160, 80);
        infoButton.setBounds(getWidth() - 220, 180, 160, 80);
        storeButton.setBounds(getWidth() - 220, 320, 160, 80);
        abilityButton.setBounds(getWidth() - 220, 460, 160, 80);
        movementButton.setFont(new Font("Calibri", Font.PLAIN, 10));
        infoButton.setFont(new Font("Calibri", Font.PLAIN, 10));
        storeButton.setFont(new Font("Calibri", Font.PLAIN, 10));
        abilityButton.setFont(new Font("Calibri", Font.PLAIN, 10));
        movementButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                int response = JOptionPane.showConfirmDialog(null, "Are you sure you want to buy this skill for 500 XP", "Writ of Aceso", JOptionPane.YES_NO_OPTION);
                String[] options = {"WASD", "Arrow keys"};
                int response2 = JOptionPane.showOptionDialog(null, "Do you work with WASD or arrow keys?", "Movement keys", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, JOptionPane.YES_OPTION);
                if (response2 == 0) {
                    KeyCodes.MOVE_UP = KeyEvent.VK_W;
                    KeyCodes.MOVE_DOWN = KeyEvent.VK_S;
                    KeyCodes.MOVE_LEFT = KeyEvent.VK_A;
                    KeyCodes.MOVE_RIGHT = KeyEvent.VK_D;
                } else if (response2 == 1) {
                    KeyCodes.MOVE_UP = KeyEvent.VK_UP;
                    KeyCodes.MOVE_DOWN = KeyEvent.VK_DOWN;
                    KeyCodes.MOVE_LEFT = KeyEvent.VK_LEFT;
                    KeyCodes.MOVE_RIGHT = KeyEvent.VK_RIGHT;
                }
            }
        });
        infoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeButton = "info";
                movementButton.setForeground(Color.black);
                storeButton.setForeground(Color.black);
                abilityButton.setForeground(Color.black);
                infoButton.setForeground(Color.red);
            }
        });
        storeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeButton = "store";
                movementButton.setForeground(Color.black);
                infoButton.setForeground(Color.black);
                abilityButton.setForeground(Color.black);
                storeButton.setForeground(Color.red);
            }
        });
        abilityButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                activeButton = "ability";
                movementButton.setForeground(Color.black);
                infoButton.setForeground(Color.black);
                storeButton.setForeground(Color.black);
                abilityButton.setForeground(Color.red);
            }
        });
        add(movementButton);
        add(infoButton);
        add(storeButton);
        add(abilityButton);


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

    public void changeKeys(int keyCode) {
        if (keyCode != KeyEvent.VK_ESCAPE) {
            if (KeyCodes.MOVE_UP == keyCode ||
                    KeyCodes.MOVE_DOWN == keyCode ||
                    KeyCodes.MOVE_LEFT == keyCode ||
                    KeyCodes.MOVE_RIGHT == keyCode ||
                    KeyCodes.ABILITY == keyCode ||
                    KeyCodes.SHOW_INFO == keyCode ||
                    KeyCodes.STORE == keyCode) {
                JOptionPane.showMessageDialog(null, "This key is occupied", "Task failed", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
            switch (activeButton) {
                case "info":
                    KeyCodes.SHOW_INFO = keyCode;
                    break;
                case "store":
                    KeyCodes.STORE = keyCode;
                    break;
                case "ability":
                    KeyCodes.ABILITY = keyCode;
                    break;
            }
        }
        movementButton.setForeground(Color.black);
        infoButton.setForeground(Color.black);
        storeButton.setForeground(Color.black);
        abilityButton.setForeground(Color.black);
        activeButton = "";
        KeyCodes.saveKeyCodes();
    }

}
