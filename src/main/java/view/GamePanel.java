package view;


import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import static controller.Constants.INITIAL_PANEL_SIZE;

public final class GamePanel extends JPanel {


    private static GamePanel INSTANCE;
    private final Random rng = new Random();

    private GamePanel() {
        setBorder(BorderFactory.createLineBorder(Color.green, 3));
        setBackground(Color.black);
        setSize(INITIAL_PANEL_SIZE);
        setLocationToCenter(GameFrame.getINSTANCE());

        GameFrame.getINSTANCE().add(this);

//        Collidable.collidables.add(this);
    }


    public void setLocationToCenter(GameFrame glassFrame) {
        setLocation(glassFrame.getWidth() / 2 - getWidth() / 2, glassFrame.getHeight() / 2 - getHeight() / 2);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (EntityView entityView : EntityView.entityViews){
            if (entityView instanceof EpsilonView) {
                g.setColor(Color.red);
                g.drawOval((int) entityView.getLocation().getX(), (int) entityView.getLocation().getY(), (int) ((EpsilonView) entityView).radius, (int) ((EpsilonView) entityView).radius);
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
