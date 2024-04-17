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
        for (EntityView entityView : EntityView.entityViews) {
            // TODO spin enemies
            if (entityView instanceof EpsilonView) {
                g.setColor(Color.red);
                g.drawOval((int) (entityView.getLocation().getX() - ((EpsilonView) entityView).radius),
                        (int) (entityView.getLocation().getY() - ((EpsilonView) entityView).radius),
                        (int) (((EpsilonView) entityView).radius * 2),
                        (int) (((EpsilonView) entityView).radius) * 2);
            } else if (entityView instanceof SquarantineView) {
                g.setColor(Color.green);
//                g.fillRect((int) (entityView.getLocation().getX() - ((SquarantineView) entityView).sideLength / 2),
//                        (int) (entityView.getLocation().getY() - ((SquarantineView) entityView).sideLength / 2),
//                        (int) ((SquarantineView) entityView).sideLength,
//                        (int) ((SquarantineView) entityView).sideLength);
                g.fillPolygon(((SquarantineView) entityView).getShape());

            } else if (entityView instanceof TrigorathView) {
                g.setColor(Color.yellow);
//                double height = Math.sqrt(3) * ((TrigorathView) entityView).sideLength / 2;
//                int[] xPoints = {(int) entityView.getLocation().getX(),
//                        (int) (entityView.getLocation().getX() - ((TrigorathView) entityView).sideLength / 2),
//                        (int) (entityView.getLocation().getX() + ((TrigorathView) entityView).sideLength / 2)};
//                int[] yPoints = {(int) (entityView.getLocation().getY() - height),
//                        (int) (entityView.getLocation().getY() + height / 2),
//                        (int) (entityView.getLocation().getY() + height / 2)};
//                g.fillPolygon(xPoints, yPoints, 3);
                g.fillPolygon(((TrigorathView) entityView).getShape());
            }
        }
        for (BulletView bulletView : BulletView.bulletViews) {
            if (bulletView.isActive()) {
                g.setColor(Color.red);
                g.drawOval((int) (bulletView.getLocation().getX() - BULLET_RADIUS), (int) (bulletView.getLocation().getY() - BULLET_RADIUS), (int) ((BULLET_RADIUS * 2)), (int) (BULLET_RADIUS * 2));
            }
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
