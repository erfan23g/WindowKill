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
    private final Random rng = new Random();

    private GamePanel() {
        setBorder(BorderFactory.createLineBorder(Color.green, 3));
        setBackground(Color.black);
        setSize(INITIAL_PANEL_SIZE);
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
        for (EntityView entityView : EntityView.entityViews){
            if (entityView instanceof EpsilonView) {
                g.setColor(Color.red);
                g.drawOval((int) (entityView.getLocation().getX() - ((EpsilonView) entityView).radius), (int) (entityView.getLocation().getY() - ((EpsilonView) entityView).radius), (int) (((EpsilonView) entityView).radius * 2), (int) (((EpsilonView) entityView).radius) * 2);
            }
        }
        for (BulletView bulletView : BulletView.bulletViews) {
            g.setColor(Color.red);
            g.drawOval((int) (bulletView.getLocation().getX() - BULLET_RADIUS), (int) (bulletView.getLocation().getY() - BULLET_RADIUS), (int) ((BULLET_RADIUS * 2)), (int) (BULLET_RADIUS * 2));
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
