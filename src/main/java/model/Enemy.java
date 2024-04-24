package model;

import controller.Utils;
import model.collision.Collidable;
import view.SquarantineView;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import static controller.Constants.*;

public abstract class Enemy extends Entity implements Collidable {
    private Polygon shape;
    protected int power;

    public int getPower() {
        return power;
    }

    public Polygon getShape() {
        return shape;
    }

    public void setShape(Polygon shape) {
        this.shape = shape;
    }


    private double verticalSpeed, horizontalSpeed;
    private double angle = 0;
    private boolean isActive = true;
    private double rotationSpeed;
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
    public void move() {
        double dx = Math.abs(Math.cos(angle)) * horizontalSpeed, dy = Math.abs(Math.sin(angle)) * verticalSpeed;
        setLocation(new Point2D.Double(getLocation().getX() + dx, getLocation().getY() + dy));
//        for (int i = 0; i < shape.npoints; i++) {
//            shape.xpoints[i] += (int) dx;
//            shape.ypoints[i] += (int) dy;
//        }
    }

    public void updateAngle(Point2D point) {
        angle = Math.atan2(point.getY() - getLocation().getY(), point.getX() - getLocation().getX());
    }

    @Override
    public void damage(int reduction) {
        setHp(getHp() - reduction);
        if (getHp() <= 0) die();
    }
    public Point2D[] getVertices () {
        Point2D[] vertices = new Point2D[getShape().npoints];
        for (int i = 0; i < getShape().npoints; i++) {
            vertices[i] = new Point2D.Double(getShape().xpoints[i], getShape().ypoints[i]);
        }
        return vertices;
    }
    public abstract void die ();

    private final ArrayList<HashMap<String, Double>> rotationAngles = new ArrayList<>();

    public void accelerateRotation() {
        ArrayList<HashMap<String, Double>> anglesToRemove = new ArrayList<>();
        for (HashMap<String, Double> rotationAngle : rotationAngles) {
            if (rotationAngle.get("accelerating") == 1.0) {
                rotationAngle.put("speed", Math.min(ENEMY_ROTATION_SPEED, rotationAngle.get("speed") + ENEMY_ROTATION_ACCELERATION));
                if (rotationAngle.get("speed") >= ENEMY_ROTATION_SPEED) {
                    anglesToRemove.add(rotationAngle);
                }
            }
//            else {
//                rotationAngle.put("speed", Math.max(0, rotationAngle.get("speed") - ENEMY_ROTATION_ACCELERATION));
//            }
            double newAngle = rotationAngle.get("angle") + rotationAngle.get("direction") * rotationAngle.get("speed");
            rotationAngle.put("angle", newAngle);
            rotationSpeed += newAngle;
            rotationSpeed = Math.min(ENEMY_ROTATION_SPEED, rotationSpeed);
//            if (rotationAngle.get("speed") <= 0) {
//                anglesToRemove.add(rotationAngle);
//            }
        }
        rotationAngles.removeAll(anglesToRemove);
    }
    public void rotatePolygon(Polygon p) {
        System.out.println(rotationSpeed);
//        System.out.println(rotationAngles.size());
        AffineTransform at = AffineTransform.getRotateInstance(Math.toRadians(rotationSpeed), getLocation().getX(), getLocation().getY());
        for (int i = 0; i < p.npoints; i++) {
            double[] pt = {p.xpoints[i], p.ypoints[i]};
            at.transform(pt, 0, pt, 0, 1);
            p.xpoints[i] = (int) pt[0];
            p.ypoints[i] = (int) pt[1];
        }
//        System.out.println(rotationAngles.size());
    }
    public void rotate(boolean isClockwise) {
        HashMap<String, Double> rotationAngle = new HashMap<>();
        rotationAngle.put("speed", 0.0);
        rotationAngle.put("angle", 0.0);
        rotationAngle.put("direction", (double) (isClockwise ? 1 : -1));
        rotationAngle.put("accelerating", 1.0);
        rotationAngles.add(rotationAngle);
    }
}
