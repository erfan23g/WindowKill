package model;

import controller.Controller;
import controller.Update;
import model.collision.Collidable;
import model.movement.Direction;
import view.GamePanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.*;

import static controller.Constants.*;

public class Epsilon extends Entity {
    private static Epsilon INSTANCE;
    private double upSpeed, downSpeed, leftSpeed, rightSpeed;
    private int xp = 10000;
    private int abilityCount;
    private final Timer acesoTimer = new Timer(1000, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            setHp(Math.min(getHp() + abilityCount, 100));
        }
    });
    public ArrayList<Point2D> getVertices () {
        if (Update.ability != 3) {
            return new ArrayList<>();
        } else {
            ArrayList<Point2D> vertices = new ArrayList<>();
            double angle = Math.atan2(Update.getMouse().getY() - getLocation().getY(), Update.getMouse().getX() - getLocation().getX());
            double angle2 = (2 * Math.PI) / abilityCount;
            for (int i = 0; i < abilityCount; i++) {
                vertices.add(new Point2D.Double(getLocation().getX() + (Math.cos(angle) * EPSILON_RADIUS), getLocation().getY() + (Math.sin(angle) * EPSILON_RADIUS)));
                angle += angle2;
            }
            return vertices;
        }
    }

    public boolean containsPoint(Point2D point) {
        for (Point2D point2D : getVertices()) {
            double angle = Math.atan2(point.getY() - getLocation().getY(), point.getX() - getLocation().getX());
            double angle2D = Math.atan2(point2D.getY() - getLocation().getY(), point2D.getX() - getLocation().getX());
            if (angle >= angle2D - 0.1 && angle <= angle2D + 0.1) return true;
        }
        return false;
    }

    public Timer getAcesoTimer() {
        return acesoTimer;
    }

    public int getAbilityCount() {
        return abilityCount;
    }

    public void setAbilityCount(int abilityCount) {
        this.abilityCount = abilityCount;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public static Epsilon getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new Epsilon(EPSILON_STARTING_LOCATION);
        return INSTANCE;
    }

    public Epsilon(Point2D location) {
        super(location);
        setHp(100);

    }

    @Override
    public void damage(int reduction) {
        setHp(getHp() - reduction);
        if (getHp() <= 0) {
            Update.gameOver(false);
        }
    }

    public void accelerate (Direction direction, boolean isPositive) {
        if (getImpactAngles().isEmpty()) {
            if (isPositive) {
                if (direction.equals(Direction.UP) && upSpeed < EPSILON_SPEED) {
                    upSpeed += EPSILON_ACCELERATION * ((double) Update.getSensitivity() / 50);
                    upSpeed = Math.min(EPSILON_SPEED, upSpeed);
                } else if (direction.equals(Direction.DOWN) && downSpeed < EPSILON_SPEED) {
                    downSpeed += EPSILON_ACCELERATION * ((double) Update.getSensitivity() / 50);
                    downSpeed = Math.min(EPSILON_SPEED, downSpeed);
                } else if (direction.equals(Direction.RIGHT) && rightSpeed < EPSILON_SPEED) {
                    rightSpeed += EPSILON_ACCELERATION * ((double) Update.getSensitivity() / 50);
                    rightSpeed = Math.min(EPSILON_SPEED, rightSpeed);
                } else if (direction.equals(Direction.LEFT) && leftSpeed < EPSILON_SPEED) {
                    leftSpeed += EPSILON_ACCELERATION * ((double) Update.getSensitivity() / 50);
                    leftSpeed = Math.min(EPSILON_SPEED, leftSpeed);
                }
            } else {
                if (direction.equals(Direction.UP) && upSpeed > 0) {
                    upSpeed -= EPSILON_ACCELERATION * ((double) Update.getSensitivity() / 50);
                    upSpeed = Math.max(0, upSpeed);
                } else if (direction.equals(Direction.DOWN) && downSpeed > 0) {
                    downSpeed -= EPSILON_ACCELERATION * ((double) Update.getSensitivity() / 50);
                    downSpeed = Math.max(0, downSpeed);
                } else if (direction.equals(Direction.RIGHT) && rightSpeed > 0) {
                    rightSpeed -= EPSILON_ACCELERATION * ((double) Update.getSensitivity() / 50);
                    rightSpeed = Math.max(0, rightSpeed);
                } else if (direction.equals(Direction.LEFT) && leftSpeed > 0) {
                    leftSpeed -= EPSILON_ACCELERATION * ((double) Update.getSensitivity() / 50);
                    leftSpeed = Math.max(0, leftSpeed);
                }
            }
        } else {
            ArrayList<HashMap<String, Double>> anglesToRemove = new ArrayList<>();
            for (HashMap<String, Double> impactAngle : getImpactAngles()) {
                if (impactAngle.get("angle") < 0 && upSpeed < EPSILON_SPEED) {
                    upSpeed += impactAngle.get("acceleration");
                    upSpeed = Math.max(EPSILON_SPEED, upSpeed);
                } else if (impactAngle.get("angle") > 0 && downSpeed < EPSILON_SPEED) {
                    downSpeed += impactAngle.get("acceleration");
                    downSpeed = Math.min(EPSILON_SPEED, downSpeed);
                }
                if (impactAngle.get("angle") > -Math.PI / 2 && impactAngle.get("angle") < Math.PI / 2 & rightSpeed < EPSILON_SPEED) {
                    rightSpeed += impactAngle.get("acceleration");
                    rightSpeed = Math.min(EPSILON_SPEED, rightSpeed);
                } else if ((impactAngle.get("angle") < -Math.PI / 2 || impactAngle.get("angle") > Math.PI / 2) && leftSpeed < EPSILON_SPEED) {
                    leftSpeed += impactAngle.get("acceleration");
                    leftSpeed = Math.max(EPSILON_SPEED, leftSpeed);
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
        double x = getLocation().getX() + rightSpeed - leftSpeed;
        double y = getLocation().getY() + downSpeed - upSpeed;
        x = Math.max(GamePanelModel.getINSTANCE().getLocation().getX() + EPSILON_RADIUS, x);
        x = Math.min(GamePanelModel.getINSTANCE().getLocation().getX() + GamePanelModel.getINSTANCE().getSize().getWidth() - EPSILON_RADIUS, x);
        y = Math.max(GamePanelModel.getINSTANCE().getLocation().getY() + EPSILON_RADIUS, y);
        y = Math.min(GamePanelModel.getINSTANCE().getLocation().getY() + GamePanelModel.getINSTANCE().getSize().getHeight() - EPSILON_RADIUS, y);
        setLocation(new Point2D.Double(x, y));
    }
    public void eat (Collectible collectible) {
        setXp(getXp() + collectible.getXp());
        collectible.setActive(false);
        Controller.deactivateCollectibleView(collectible.getId());
    }

    public static void dispose() {
        INSTANCE = null;
    }
}
