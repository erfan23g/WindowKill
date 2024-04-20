package model;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.Constants.COLLECTIBLE_XP;
import static controller.Constants.ENEMY_SIDE_LENGTH;

public class Squarantine extends Enemy{


    public Squarantine(Point2D location) {
        super(location);
        setHp(10);
    }

    @Override
    public void die() {
        setActive(false);
        new Collectible(Color.green, getLocation(), COLLECTIBLE_XP);
    }

    public Polygon getShape () {
        return super.getShape();
    }
    public void updateShape() {
        int[] xPoints = {(int) (getLocation().getX() - ENEMY_SIDE_LENGTH / 2),
                (int) (getLocation().getX() + ENEMY_SIDE_LENGTH / 2),
                (int) (getLocation().getX() + ENEMY_SIDE_LENGTH / 2),
                (int) (getLocation().getX() - ENEMY_SIDE_LENGTH / 2)};
        int[] yPoints = {(int) (getLocation().getY() - ENEMY_SIDE_LENGTH / 2),
                (int) (getLocation().getY() - ENEMY_SIDE_LENGTH / 2),
                (int) (getLocation().getY() + ENEMY_SIDE_LENGTH / 2),
                (int) (getLocation().getY() + ENEMY_SIDE_LENGTH / 2)};
        setShape(new Polygon(xPoints, yPoints, 4));
    }
}
