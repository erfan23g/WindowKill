package view;

import controller.Update;
import model.GamePanelModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import static controller.Constants.INITIAL_PANEL_SIZE;

public class SkillTreePanel extends JPanel implements ActionListener {
    private static SkillTreePanel INSTANCE;
    private ArrayList<String> skills = new ArrayList<>();
    public static boolean isOpen = false;
    private int xp;
    public static SkillTreePanel getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new SkillTreePanel();
        return INSTANCE;
    }
    private final JButton aresButton, acesoButton, proteusButton, backButton;
    public SkillTreePanel() {
        setDoubleBuffered(true);
        File file = new File("src/main/java/data/xp.txt");
        try {
            Scanner scanner = new Scanner(file);
            xp = Integer.parseInt(scanner.next());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        file = new File("src/main/java/data/skills.txt");
        try {
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) skills.add(scanner.nextLine());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        setSize(new Dimension((int) INITIAL_PANEL_SIZE.getWidth(), (int) INITIAL_PANEL_SIZE.getHeight()));
        setLocation((int) (GameFrame.getINSTANCE().getWidth() / 2 - GamePanelModel.getINSTANCE().getSize().getWidth() / 2), (int) (GameFrame.getINSTANCE().getHeight() / 2 - GamePanelModel.getINSTANCE().getSize().getHeight() / 2));
        setLayout(null);
        aresButton = new JButton(skills.contains("Ares") ? "Writ of Ares" : "Writ of Ares\uD83D\uDD12");
        acesoButton = new JButton(skills.contains("Aceso") ? "Writ of Aceso" : "Writ of Aceso\uD83D\uDD12");
        proteusButton = new JButton(skills.contains("Proteus") ? "Writ of Proteus" : "Writ of Proteus\uD83D\uDD12");
        backButton = new JButton("Back");
        aresButton.setBounds(60, 40, 160, 80);
        acesoButton.setBounds(60, 180, 160, 80);
        proteusButton.setBounds(60, 320, 160, 80);
        backButton.setBounds(60, 460, 160, 80);
        aresButton.addActionListener(this);
        acesoButton.addActionListener(this);
        proteusButton.addActionListener(this);
        backButton.addActionListener(this);
        add(aresButton);
        add(acesoButton);
        add(proteusButton);
        add(backButton);
        setVisible(false);
        GameFrame.getINSTANCE().add(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == aresButton) {
            if (skills.contains("Ares")) {
                Update.ability = 1;
                //TODO message
            } else {

            }
        } else if (e.getSource() == acesoButton) {
            if (skills.contains("Aceso")) {
                Update.ability = 2;
                //TODO message
            } else {

            }
        } else if (e.getSource() == proteusButton) {
            if (skills.contains("Proteus")) {
                Update.ability = 3;
                //TODO message
            } else {

            }
        } else if (e.getSource() == backButton) {
            this.setVisible(false);
            StartingPanel.getINSTANCE().setVisible(true);
            GameFrame.getINSTANCE().repaint();
        }
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("src/main/java/pictures/amogus.jpg").getImage(), 0, 0, getWidth(), getHeight(), null);
        g.setColor(Color.MAGENTA);
        Font font = new Font("Times New Roman", Font.PLAIN, 20);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        double stringWidth = SwingUtilities.computeStringWidth(fm, "XP: " + xp);
        g.drawString("XP: " + xp, (int) ((getWidth() - stringWidth) / 2), 35);
    }
    public static void dispose() {
        GameFrame.getINSTANCE().remove(INSTANCE);
        INSTANCE = null;
        GameFrame.getINSTANCE().repaint();
    }
    public static boolean isNull() {return INSTANCE == null;}
}
