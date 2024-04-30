package model;

import controller.Controller;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import static controller.Constants.*;
import static controller.Constants.ENEMY_SPEED;

public class Squarantine extends Enemy{

    boolean dash;


    public Squarantine(Point2D location) {
        super(location);
        setHp(10);
        power = 6;
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


    public void setDash(boolean dash) {
        this.dash = dash;
    }

    public void changeDash () {
        int rnd = (int) (Math.random() * 10000);
        if (rnd == 10) {
            setDash(true);
        }
    }
    public void accelerate() {
        double speed = dash ? DASH_SPEED : ENEMY_SPEED;
        double acceleration = dash ? DASH_ACCELERATION : ENEMY_ACCELERATION;
        if (getImpactAngles().isEmpty()) {

            if (angle < 0 && verticalSpeed > -speed) {
                verticalSpeed -= acceleration;
                verticalSpeed = Math.max(-speed, verticalSpeed);
            } else if (angle > 0 && verticalSpeed < speed) {
                verticalSpeed += speed;
                verticalSpeed = Math.min(speed, verticalSpeed);
            }
            if (angle > -Math.PI / 2 && angle < Math.PI / 2 & horizontalSpeed < speed) {
                horizontalSpeed += speed;
                horizontalSpeed = Math.min(speed, horizontalSpeed);
            } else if ((angle < -Math.PI / 2 || angle > Math.PI / 2) && horizontalSpeed > -speed) {
                horizontalSpeed -= speed;
                horizontalSpeed = Math.max(-speed, horizontalSpeed);
            }
        } else {
            ArrayList<HashMap<String, Double>> anglesToRemove = new ArrayList<>();
            for (HashMap<String, Double> impactAngle : getImpactAngles()) {
                if (impactAngle.get("angle") < 0 && verticalSpeed > -speed) {
                    verticalSpeed -= impactAngle.get("acceleration");
                    verticalSpeed = Math.max(-speed, verticalSpeed);
                } else if (impactAngle.get("angle") > 0 && verticalSpeed < speed) {
                    verticalSpeed += impactAngle.get("acceleration");
                    verticalSpeed = Math.min(speed, verticalSpeed);
                }
                if (impactAngle.get("angle") > -Math.PI / 2 && impactAngle.get("angle") < Math.PI / 2 & horizontalSpeed < speed) {
                    horizontalSpeed += impactAngle.get("acceleration");
                    horizontalSpeed = Math.min(speed, horizontalSpeed);
                } else if ((impactAngle.get("angle") < -Math.PI / 2 || impactAngle.get("angle") > Math.PI / 2) && horizontalSpeed > -speed) {
                    horizontalSpeed -= impactAngle.get("acceleration");
                    horizontalSpeed = Math.max(-speed, horizontalSpeed);
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
    public void revive (Point2D location) {
        setLocation(location);
        updateShape();
        setCoolDown(false);
        setDash(false);
        setHp(10);
        setActive(true);
    }
}
