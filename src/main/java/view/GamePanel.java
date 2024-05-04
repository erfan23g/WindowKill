package view;


import controller.Constants;
import controller.Controller;
import controller.Update;
import controller.Utils;
import model.GamePanelModel;

import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.time.Year;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import static controller.Constants.*;

public final class GamePanel extends JPanel {


    private static GamePanel INSTANCE;
    private int epsilonXp;

    public int getEpsilonHp() {
        return epsilonHp;
    }

    public void setEpsilonHp(int epsilonHp) {
        this.epsilonHp = epsilonHp;
    }

    private int epsilonHp;

    public int getEpsilonXp() {
        return epsilonXp;
    }

    public void setEpsilonXp(int epsilonXp) {
        this.epsilonXp = epsilonXp;
    }

    private final long startTime;


    private GamePanel() {
        setDoubleBuffered(true);
        setBackground(Color.black);
        setSize(new Dimension((int) INITIAL_PANEL_SIZE.getWidth(), (int) INITIAL_PANEL_SIZE.getHeight()));
        setLocationToCenter();
        setLayout(null);
        startTime = System.currentTimeMillis();
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Controller.fireBullet(e.getPoint());
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Update.setMouse(SwingUtilities.convertPoint(GamePanel.getINSTANCE(), e.getPoint(), GameFrame.getINSTANCE()));
            }
        });
        GameFrame.getINSTANCE().add(this);
    }


    public void setLocationToCenter() {
        setLocation((int) (GameFrame.getINSTANCE().getWidth() / 2 - GamePanelModel.getINSTANCE().getSize().getWidth() / 2), (int) (GameFrame.getINSTANCE().getHeight() / 2 - GamePanelModel.getINSTANCE().getSize().getHeight() / 2));
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (BulletView bulletView : BulletView.bulletViews) {
            if (bulletView.isActive()) {
                bulletView.draw(g);
            }
        }
        for (CollectibleView collectibleView : CollectibleView.collectibleViews) {
            if (collectibleView.isActive()) {
                collectibleView.draw(g);
            }
        }
        for (EntityView entityView : EntityView.entityViews) {
            entityView.draw(g);
        }
        g.setColor(Color.MAGENTA);
        Font font = new Font("Times New Roman", Font.PLAIN, 20);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        if (GameFrame.info) {



            epsilonHp = Math.max(0, epsilonHp);
            double stringWidth = SwingUtilities.computeStringWidth(fm, "HP: " + epsilonHp + "/100");
            g.drawString("HP: " + epsilonHp + "/100", 8, 20);
            double stringWidth2 = SwingUtilities.computeStringWidth(fm, "XP: " + epsilonXp);
            g.drawString("XP: " + epsilonXp, (int) (getSize().getWidth() - 8 - stringWidth2), 20);
            double stringWidth3 = SwingUtilities.computeStringWidth(fm, getElapsedTime());
            g.drawString(getElapsedTime(), 8, (int) (getSize().getHeight() - 12));
            double stringWidth4 = SwingUtilities.computeStringWidth(fm, "Wave: " + Update.wave + "/3");
            if (!Update.inBetweenWaves) {
                g.drawString("Wave: " + Update.wave + "/3", (int) (getSize().getWidth() - 8 - stringWidth4), (int) (getSize().getHeight() - 12));
            }
//            hpBar.setVisible(true);
        }
        if (!Update.getError().isEmpty()) {
            double stringWidth5 = SwingUtilities.computeStringWidth(fm, Update.getError());
            g.drawString(Update.getError(), (int) (getSize().getWidth() - stringWidth5) / 2, 35);
        }
    }

    public static GamePanel getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new GamePanel();
        return INSTANCE;
    }
    private String getElapsedTime () {
        long milliseconds = System.currentTimeMillis() - startTime - Update.getStoreTime2();

        long hours = TimeUnit.MILLISECONDS.toHours(milliseconds);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds) % 60;
        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds) % 60;

        // Format the string to "HH:MM:SS"
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);

    }

    public static void dispose() {
        GameFrame.getINSTANCE().remove(INSTANCE);
        INSTANCE.setVisible(false);
        INSTANCE = null;
    }

}
