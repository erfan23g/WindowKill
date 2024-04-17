package model;

import view.TrigorathView;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.Constants.ENEMY_SIDE_LENGTH;

public class Trigorath extends Enemy{
    public Trigorath(Point2D location) {
        super(location);
        setHp(15);
    }
    public Polygon getShape () {
        return super.getShape();
    }
    public void updateShape () {
        double height = Math.sqrt(3) * ENEMY_SIDE_LENGTH / 2;
        int[] xPoints = {(int) getLocation().getX(),
                (int) (getLocation().getX() - ENEMY_SIDE_LENGTH / 2),
                (int) (getLocation().getX() + ENEMY_SIDE_LENGTH / 2)};
        int[] yPoints = {(int) (getLocation().getY() - height),
                (int) (getLocation().getY() + height / 2),
                (int) (getLocation().getY() + height / 2)};
        setShape(new Polygon(xPoints, yPoints, 3));
    }
}
