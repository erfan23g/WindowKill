package model;

import model.movement.Direction;
import view.GamePanel;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.Constants.EPSILON_RADIUS;

public class Epsilon extends Entity {
    public static Epsilon INSTANCE;

    public static Epsilon getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new Epsilon(new Point2D.Double(100, 100));
        return INSTANCE;
    }

    public Epsilon(Point2D location) {
        super(location);
    }

    public void move(Direction direction, double speed) {
        if (direction.equals(Direction.UP)) {
            setLocation(new Point2D.Double(getLocation().getX(), getLocation().getY() - speed));
//            setY(getY() - speed);
        } else if (direction.equals(Direction.DOWN)) {
            setLocation(new Point2D.Double(getLocation().getX(), getLocation().getY() + speed));
//            setY(getY() + speed);
        } else if (direction.equals(Direction.RIGHT)) {
            setLocation(new Point2D.Double(getLocation().getX() + speed, getLocation().getY()));
//            setX(getX() + speed);
        } else if (direction.equals(Direction.LEFT)) {
            setLocation(new Point2D.Double(getLocation().getX() - speed, getLocation().getY()));
//            setX(getX() - speed);
        }
    }
}
