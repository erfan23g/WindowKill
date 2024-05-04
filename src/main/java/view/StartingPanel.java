package view;

import controller.Update;
import model.Epsilon;
import model.GamePanelModel;
import org.example.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import static controller.Constants.INITIAL_PANEL_SIZE;

public class StartingPanel extends JPanel implements ActionListener {
    private JButton newGameButton, skillTreeButton, tutorialButton, settingsButton, exitButton;
    private int xp;

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    private static StartingPanel INSTANCE;
    public static StartingPanel getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new StartingPanel();
        return INSTANCE;
    }
    public StartingPanel() {
        setDoubleBuffered(true);
        File file = new File("src/main/java/data/xp.txt");
        try {
            Scanner scanner = new Scanner(file);
            xp = Integer.parseInt(scanner.next());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        newGameButton = new JButton("New Game");
        skillTreeButton = new JButton("Skill Tree");
        tutorialButton = new JButton("Tutorial");
        settingsButton = new JButton("Settings");
        exitButton = new JButton("Exit");
        newGameButton.setBounds(60, 40, 130, 60);
        skillTreeButton.setBounds(60, 120, 130, 60);
        tutorialButton.setBounds(60, 200, 130, 60);
        settingsButton.setBounds(60, 280, 130, 60);
        exitButton.setBounds(60, 360, 130, 60);
        newGameButton.addActionListener(this);
        skillTreeButton.addActionListener(this);
        tutorialButton.addActionListener(this);
        settingsButton.addActionListener(this);
        exitButton.addActionListener(this);

        add(newGameButton);
        add(skillTreeButton);
        add(tutorialButton);
        add(settingsButton);
        add(exitButton);
        GameFrame.getINSTANCE().add(this);
    }

    @Override
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == newGameButton) {
//            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
//                try {
//                    String appName = "java"; // Modify this to match your Java app's name
//                    String script = "tell application \"System Events\"\n"
//                            + "set allProcesses to every application process where visible is true and name is not \"" + appName + "\"\n"
//                            + "repeat with proc in allProcesses\n"
//                            + "set proc's visible to false\n"
//                            + "end repeat\n"
//                            + "end tell";
//
//                    String[] cmd = { "osascript", "-e", script };
//                    Runtime.getRuntime().exec(cmd);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            } else if (System.getProperty("os.name").toLowerCase().contains("windows")) {
//                try {
//                    String[] command = {
//                            "powershell.exe",
//                            "Get-Process | Where-Object {$_.MainWindowTitle -ne \"\" -and $_.ProcessName -notlike \"*java*\"} | ForEach-Object { $_.MainWindowHandle | ForEach-Object { Add-Type -AssemblyName System.Windows.Forms; [System.Windows.Forms.SendKeys]::SendWait(\"% n\") }}"
//                    };
//                    Runtime.getRuntime().exec(command);
//                } catch (IOException ex) {
//                    ex.printStackTrace();
//                }
//            }
            dispose();
            GamePanel.getINSTANCE();
            GamePanelModel.getINSTANCE();
            StorePanel.getINSTANCE();
            Update.start();
        } else if (e.getSource() == skillTreeButton) {
            this.setVisible(false);
            SkillTreePanel.getINSTANCE().setVisible(true);
            GameFrame.getINSTANCE().repaint();
        } else if (e.getSource() == tutorialButton) {

        } else if (e.getSource() == settingsButton) {
            SettingsPanel.isOpen = true;
            setVisible(false);
            SettingsPanel.getINSTANCE().setVisible(true);
            GameFrame.getINSTANCE().repaint();
        } else if (e.getSource() == exitButton) {
            GameFrame.getINSTANCE().dispose();
        }
    }
    public static void dispose() {
        GameFrame.getINSTANCE().remove(INSTANCE);
        INSTANCE = null;
        GameFrame.getINSTANCE().repaint();
    }
}
