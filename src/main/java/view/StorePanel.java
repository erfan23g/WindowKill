package view;

import controller.Update;
import model.GamePanelModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static controller.Constants.INITIAL_PANEL_SIZE;

public class StorePanel extends JPanel implements ActionListener {
    private static StorePanel INSTANCE;
    public static boolean isOpen = false;
    public static StorePanel getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new StorePanel();
        return INSTANCE;
    }
    private final JButton banishButton, empowerButton, healButton, backButton;
    public StorePanel() {
        setDoubleBuffered(true);
//        setBackground(Color.black);
        setSize(new Dimension((int) INITIAL_PANEL_SIZE.getWidth(), (int) INITIAL_PANEL_SIZE.getHeight()));
        setLocation((int) (GameFrame.getINSTANCE().getWidth() / 2 - GamePanelModel.getINSTANCE().getSize().getWidth() / 2), (int) (GameFrame.getINSTANCE().getHeight() / 2 - GamePanelModel.getINSTANCE().getSize().getHeight() / 2));
        setLayout(null);
//        newGameButton = new JButton("New Game") {
//            @Override
//            protected void paintComponent(Graphics g) {
//
//                Graphics2D g2 = (Graphics2D) g;
////                g2.setPaint(new GradientPaint(0, 0, Color.BLUE, getWidth(), getHeight(), Color.LIGHT_GRAY));
//                g2.drawRect(0, 0, getWidth(), getHeight());
//                super.paintComponent(g);
//            }
//        };
//        newGameButton.setBackground(new Color(0, 0, 0, 0));
        banishButton = new JButton("O'Hephaestus، Banish");
        empowerButton = new JButton("O’ Athena، Empower");
        healButton = new JButton("O' Apollo Heal");
        backButton = new JButton("Back to main menu");
        banishButton.setBounds(60, 40, 160, 80);
        empowerButton.setBounds(60, 180, 160, 80);
        healButton.setBounds(60, 320, 160, 80);
        backButton.setBounds(60, 460, 160, 80);
        banishButton.addActionListener(this);
        empowerButton.addActionListener(this);
        healButton.addActionListener(this);
        backButton.addActionListener(this);
        add(banishButton);
        add(empowerButton);
        add(healButton);
        add(backButton);
        setVisible(false);
        GameFrame.getINSTANCE().add(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == banishButton) Update.banish();
        if (e.getSource() == empowerButton) Update.empower();
        if (e.getSource() == healButton) Update.heal();
        if (e.getSource() == healButton){
            Update.closeStore();
            Update.gameOver(true);
        }
    }
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(new ImageIcon("src/main/java/pictures/amogus.jpg").getImage(), 0, 0, getWidth(), getHeight(), null);
    }
    public static void dispose() {
        GameFrame.getINSTANCE().remove(INSTANCE);
        INSTANCE = null;
        GameFrame.getINSTANCE().repaint();
    }
    public static boolean isNull() {return INSTANCE == null;}
}
