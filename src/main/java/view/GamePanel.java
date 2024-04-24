package view;


import controller.Constants;
import controller.Controller;
import controller.Update;
import model.GamePanelModel;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

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

    private GamePanel() {
//        setBorder(BorderFactory.createLineBorder(Color.green, 3));
        setBackground(Color.black);
        setSize(new Dimension((int) INITIAL_PANEL_SIZE.getWidth(), (int) INITIAL_PANEL_SIZE.getHeight()));
        setLocationToCenter();
//        setFocusable(true);
//        requestFocus();
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
        GameFrame.getINSTANCE().add(this);
    }


    public void setLocationToCenter() {
        setLocation((int) (GameFrame.getINSTANCE().getWidth() / 2 - GamePanelModel.getINSTANCE().getSize().getWidth() / 2), (int) (GameFrame.getINSTANCE().getHeight() / 2 - GamePanelModel.getINSTANCE().getSize().getHeight() / 2));
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (EntityView entityView : EntityView.entityViews) {
            if (entityView instanceof EpsilonView) {
                g.setColor(Color.red);
                g.drawOval((int) (entityView.getLocation().getX() - EPSILON_RADIUS),
                        (int) (entityView.getLocation().getY() - EPSILON_RADIUS),
                        (int) (EPSILON_RADIUS * 2),
                        (int) (EPSILON_RADIUS * 2));
            } else if (entityView instanceof SquarantineView && ((SquarantineView) entityView).isActive()) {
                g.setColor(Color.green);
                g.fillPolygon(((SquarantineView) entityView).getShape());
                Font font = new Font("Calibri", Font.PLAIN, 12);
                g.setFont(font);
                FontMetrics fm = g.getFontMetrics();
                double stringWidth = SwingUtilities.computeStringWidth(fm, ((SquarantineView) entityView).getHp() + "");
                int centerX = (int) (entityView.getLocation().getX() - stringWidth / 2);
                int centerY = (int) (entityView.getLocation().getY() + fm.getHeight() / 4);
                g.setColor(Color.black);
                g.drawString(((SquarantineView) entityView).getHp() + "", centerX, centerY);
            } else if (entityView instanceof TrigorathView && ((TrigorathView) entityView).isActive()) {
                g.setColor(Color.yellow);
                g.fillPolygon(((TrigorathView) entityView).getShape());
                Font font = new Font("Calibri", Font.PLAIN, 12);
                g.setFont(font);
                FontMetrics fm = g.getFontMetrics();
                double stringWidth = SwingUtilities.computeStringWidth(fm, ((TrigorathView) entityView).getHp() + "");
                int centerX = (int) (entityView.getLocation().getX() - stringWidth / 2);
                int centerY = (int) (entityView.getLocation().getY() + fm.getHeight() / 4);
                g.setColor(Color.black);
                g.drawString(((TrigorathView) entityView).getHp() + "", centerX, centerY);
            }
        }
        for (BulletView bulletView : BulletView.bulletViews) {
            if (bulletView.isActive()) {
                g.setColor(Color.red);
                g.drawOval((int) (bulletView.getLocation().getX() - BULLET_RADIUS), (int) (bulletView.getLocation().getY() - BULLET_RADIUS), (int) ((BULLET_RADIUS * 2)), (int) (BULLET_RADIUS * 2));
            }
        }
        for (CollectibleView collectibleView : CollectibleView.collectibleViews) {
            if (collectibleView.isActive()) {
                g.setColor(collectibleView.getColor());
                g.fillOval((int) (collectibleView.getLocation().getX() - COLLECTIBLE_RADIUS), (int) (collectibleView.getLocation().getY() - COLLECTIBLE_RADIUS), (int) ((COLLECTIBLE_RADIUS * 2)), (int) (COLLECTIBLE_RADIUS * 2));
            }
        }
        if (GameFrame.info) {
            g.setColor(Color.cyan);
            Font font = new Font("Times New Roman", Font.PLAIN, 20);
            g.setFont(font);
            FontMetrics fm = g.getFontMetrics();
            double stringWidth = SwingUtilities.computeStringWidth(fm, "XP: " + epsilonXp);
            g.drawString("HP: " + epsilonHp + "/100", 8, 20);
            g.drawString("XP: " + epsilonXp, (int) (getSize().getWidth() - 8 - stringWidth), 20);
        }
    }

    public static GamePanel getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new GamePanel();
        return INSTANCE;
    }

//    @Override
//    public boolean isCircular() {
//        return false;
//    }
//
//    @Override
//    public double getRadius() {
//        return 0;
//    }
//
//    @Override
//    public Point2D getAnchor() {
//        return null;
//    }
//
//    @Override
//    public ArrayList<Point2D> getVertices() {
//        return new ArrayList<>(List.of(new Point2D.Double(getX(),getY()),new Point2D.Double(getX()+getWidth(),getY()),
//                new Point2D.Double(getX()+getWidth(),getY()+getHeight()),new Point2D.Double(getX(),getY()+getHeight())));
//    }

}
