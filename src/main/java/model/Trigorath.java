package model;

import controller.Update;
import view.TrigorathView;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;

import static controller.Constants.*;
import static controller.Constants.ENEMY_ACCELERATION;

public class Trigorath extends Enemy{
    public Trigorath(Point2D location) {
        super(location);
        setHp(15);
        power = 10;
    }

    @Override
    public void die() {
        super.die();
        setActive(false);
        new Collectible(Color.yellow, getLocation(), COLLECTIBLE_XP);
        new Collectible(Color.yellow, getVertices().get(0), COLLECTIBLE_XP);
    }

    public Polygon getShape () {
        return super.getShape();
    }
    public void updateShape () {
        double height = Math.sqrt(3) * (ENEMY_SIDE_LENGTH * ((double) 2 / Update.getDifficulty())) / 2;
        int[] xPoints = {(int) getLocation().getX(),
                (int) (getLocation().getX() - (ENEMY_SIDE_LENGTH * ((double) 2 / Update.getDifficulty())) / 2),
                (int) (getLocation().getX() + (ENEMY_SIDE_LENGTH * ((double) 2 / Update.getDifficulty())) / 2)};
        int[] yPoints = {(int) (getLocation().getY() - height),
                (int) (getLocation().getY() + height / 2),
                (int) (getLocation().getY() + height / 2)};
        setShape(new Polygon(xPoints, yPoints, 3));
    }
    public boolean isFar () {
        return getLocation().distance(Epsilon.getINSTANCE().getLocation()) > 250;
    }
    public void accelerate() {
        double speed = isFar() ? FAR_SPEED * ((double) Update.getDifficulty() / 2) : ENEMY_SPEED * ((double) Update.getDifficulty() / 2);
//        double acceleration = isFar() ? DASH_ACCELERATION : ENEMY_ACCELERATION;
        double acceleration = ENEMY_ACCELERATION * ((double) Update.getDifficulty() / 2);
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
        verticalSpeed = Math.max(-speed, verticalSpeed);
        verticalSpeed = Math.min(speed, verticalSpeed);
        horizontalSpeed = Math.max(-speed, horizontalSpeed);
        horizontalSpeed = Math.min(speed, horizontalSpeed);
    }
    public void revive (Point2D location) {
        setLocation(location);
        updateShape();
        setCoolDown(false);
        setHp(15);
        setActive(true);
    }
}
