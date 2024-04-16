package model;

import controller.Utils;
import model.collision.Collidable;

import java.awt.geom.Point2D;

import static controller.Constants.*;

public class Enemy extends Entity implements Collidable {
    private double speed = 0;
    private double upSpeed, downSpeed, leftSpeed, rightSpeed;
    private double verticalSpeed, horizontalSpeed;
    private double angle = 0;
    private boolean isActive;

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Enemy(Point2D location) {
        super(location);
    }

    public void accelerate () {
//        if (speed < ENEMY_SPEED) {
//            speed += ENEMY_ACCELERATION;
//            speed = Math.min(ENEMY_SPEED, speed);
//        }
//        if (upSpeed < ENEMY_SPEED) {
//            upSpeed += ENEMY_ACCELERATION;
//            upSpeed = Math.min(ENEMY_SPEED, upSpeed);
//        }
//        if (downSpeed < ENEMY_SPEED) {
//            downSpeed += ENEMY_ACCELERATION;
//            downSpeed = Math.min(ENEMY_SPEED, downSpeed);
//        }
//        if (leftSpeed < ENEMY_SPEED) {
//            leftSpeed += ENEMY_ACCELERATION;
//            leftSpeed = Math.min(ENEMY_SPEED, leftSpeed);
//        }
//        if (rightSpeed < ENEMY_SPEED) {
//            rightSpeed += ENEMY_ACCELERATION;
//            rightSpeed = Math.min(ENEMY_SPEED, rightSpeed);
//        }
        if (angle < 0 && verticalSpeed > -ENEMY_SPEED) {
            verticalSpeed -= ENEMY_ACCELERATION;
            verticalSpeed = Math.max(-ENEMY_SPEED, verticalSpeed);
        } else if (angle > 0 && verticalSpeed < ENEMY_SPEED) {
            verticalSpeed += ENEMY_ACCELERATION;
            verticalSpeed = Math.min(ENEMY_SPEED, verticalSpeed);
        }
        if (angle > -Math.PI / 2 && angle < Math.PI / 2 & horizontalSpeed < ENEMY_SPEED) {
            horizontalSpeed += ENEMY_ACCELERATION;
            horizontalSpeed = Math.min(ENEMY_SPEED, horizontalSpeed);
        } else if ((angle < -Math.PI / 2 || angle > Math.PI / 2) && horizontalSpeed > -ENEMY_SPEED) {
            horizontalSpeed -= ENEMY_ACCELERATION;
            horizontalSpeed = Math.max(-ENEMY_SPEED, horizontalSpeed);
        }
    }
    public void move() {
        double dx = Math.abs(Math.cos(angle)) * horizontalSpeed, dy = Math.abs(Math.sin(angle)) * verticalSpeed;
        setLocation(new Point2D.Double(getLocation().getX() + dx, getLocation().getY() + dy));
    }
    public void updateAngle (Point2D point) {
        angle = Math.atan2(point.getY() - getLocation().getY(), point.getX() - getLocation().getX());
    }
}
