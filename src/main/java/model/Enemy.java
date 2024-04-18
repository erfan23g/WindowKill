package model;

import controller.Utils;
import model.collision.Collidable;
import view.SquarantineView;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import static controller.Constants.*;

public abstract class Enemy extends Entity implements Collidable {
    private Polygon shape;
    public Polygon getShape() {
        return shape;
    }

    public void setShape(Polygon shape) {
        this.shape = shape;
    }


    private double verticalSpeed, horizontalSpeed;
    private double angle = 0;
    private boolean isActive = true;
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

    public abstract void updateShape();

    public Enemy(Point2D location) {
        super(location);
        updateShape();
    }




    public void accelerate() {
        if (getImpactAngles().isEmpty()) {

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
        } else {
            ArrayList<HashMap<String, Double>> anglesToRemove = new ArrayList<>();
            for (HashMap<String, Double> impactAngle : getImpactAngles()) {
                if (impactAngle.get("angle") < 0 && verticalSpeed > -ENEMY_SPEED) {
                    verticalSpeed -= impactAngle.get("acceleration");
                    verticalSpeed = Math.max(-ENEMY_SPEED, verticalSpeed);
                } else if (impactAngle.get("angle") > 0 && verticalSpeed < ENEMY_SPEED) {
                    verticalSpeed += impactAngle.get("acceleration");
                    verticalSpeed = Math.min(ENEMY_SPEED, verticalSpeed);
                }
                if (impactAngle.get("angle") > -Math.PI / 2 && impactAngle.get("angle") < Math.PI / 2 & horizontalSpeed < ENEMY_SPEED) {
                    horizontalSpeed += impactAngle.get("acceleration");
                    horizontalSpeed = Math.min(ENEMY_SPEED, horizontalSpeed);
                } else if ((impactAngle.get("angle") < -Math.PI / 2 || impactAngle.get("angle") > Math.PI / 2) && horizontalSpeed > -ENEMY_SPEED) {
                    horizontalSpeed -= impactAngle.get("acceleration");
                    horizontalSpeed = Math.max(-ENEMY_SPEED, horizontalSpeed);
                }
                Double count = impactAngle.get("count") - 1;
                impactAngle.put("count", count);
                if (impactAngle.get("count") < 1) {
                    anglesToRemove.add(impactAngle);
                }
            }
            getImpactAngles().removeAll(anglesToRemove);
        }
    }
//    public void accelerate(double angle, double acceleration) {
//        if (angle < 0) {
//            verticalSpeed -= acceleration;
////            verticalSpeed = Math.max(-ENEMY_SPEED, verticalSpeed);
//        } else if (angle > 0) {
//            verticalSpeed += acceleration;
////            verticalSpeed = Math.min(ENEMY_SPEED, verticalSpeed);
//        }
//        if (angle > -Math.PI / 2 && angle < Math.PI / 2) {
//            horizontalSpeed += acceleration;
////            horizontalSpeed = Math.min(ENEMY_SPEED, horizontalSpeed);
//        } else if ((angle < -Math.PI / 2 || angle > Math.PI / 2)) {
//            horizontalSpeed -= acceleration;
////            horizontalSpeed = Math.max(-ENEMY_SPEED, horizontalSpeed);
//        }
//    }

    public void move() {
        double dx = Math.abs(Math.cos(angle)) * horizontalSpeed, dy = Math.abs(Math.sin(angle)) * verticalSpeed;
        setLocation(new Point2D.Double(getLocation().getX() + dx, getLocation().getY() + dy));
    }

    public void updateAngle(Point2D point) {
        angle = Math.atan2(point.getY() - getLocation().getY(), point.getX() - getLocation().getX());
    }

    @Override
    public void damage(int reduction) {
        setHp(getHp() - reduction);
        if (getHp() <= 0) setActive(false);
    }
    public Point2D[] getVertices () {
        Point2D[] vertices = new Point2D[getShape().npoints];
        for (int i = 0; i < getShape().npoints; i++) {
            vertices[i] = new Point2D.Double(getShape().xpoints[i], getShape().ypoints[i]);
        }
        return vertices;
    }
}
