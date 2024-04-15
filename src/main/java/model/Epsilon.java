package model;

import model.collision.Collidable;
import model.movement.Direction;
import view.GamePanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import javax.swing.Timer;

import static controller.Constants.*;

public class Epsilon extends Entity {
    public static Epsilon INSTANCE;
    double upSpeed, downSpeed, leftSpeed, rightSpeed;

    public static Epsilon getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new Epsilon(EPSILON_STARTING_LOCATION);
        return INSTANCE;
    }

    public Epsilon(Point2D location) {
        super(location);
    }

    public void accelerate (Direction direction, boolean isPositive) {
        if (isPositive) {
            if (direction.equals(Direction.UP) && upSpeed < EPSILON_SPEED) {
                upSpeed += EPSILON_ACCELERATION;
                upSpeed = Math.min(EPSILON_SPEED, upSpeed);
            } else if (direction.equals(Direction.DOWN) && downSpeed < EPSILON_SPEED) {
                downSpeed += EPSILON_ACCELERATION;
                downSpeed = Math.min(EPSILON_SPEED, downSpeed);
            } else if (direction.equals(Direction.RIGHT) && rightSpeed < EPSILON_SPEED) {
                rightSpeed += EPSILON_ACCELERATION;
                rightSpeed = Math.min(EPSILON_SPEED, rightSpeed);
            } else if (direction.equals(Direction.LEFT) && leftSpeed < EPSILON_SPEED) {
                leftSpeed += EPSILON_ACCELERATION;
                leftSpeed = Math.min(EPSILON_SPEED, leftSpeed);
            }
        } else {
            if (direction.equals(Direction.UP) && upSpeed > 0) {
                upSpeed -= EPSILON_ACCELERATION;
                upSpeed = Math.max(0, upSpeed);
            } else if (direction.equals(Direction.DOWN) && downSpeed > 0) {
                downSpeed -= EPSILON_ACCELERATION;
                downSpeed = Math.max(0, downSpeed);
            } else if (direction.equals(Direction.RIGHT) && rightSpeed > 0) {
                rightSpeed -= EPSILON_ACCELERATION;
                rightSpeed = Math.max(0, rightSpeed);
            } else if (direction.equals(Direction.LEFT) && leftSpeed > 0) {
                leftSpeed -= EPSILON_ACCELERATION;
                leftSpeed = Math.max(0, leftSpeed);
            }
        }
    }

    public void move() {
        setLocation(new Point2D.Double(getLocation().getX(), getLocation().getY() - upSpeed));
        setLocation(new Point2D.Double(getLocation().getX(), getLocation().getY() + downSpeed));
        setLocation(new Point2D.Double(getLocation().getX() + rightSpeed, getLocation().getY()));
        setLocation(new Point2D.Double(getLocation().getX() - leftSpeed, getLocation().getY()));
    }
}
