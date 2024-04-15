package model;

import controller.Controller;
import model.collision.Collidable;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import static controller.Constants.BULLET_ACCELERATION;
import static controller.Constants.BULLET_SPEED;

public class Bullet implements Collidable {
    public static ArrayList<Bullet> bullets = new ArrayList<>();
    private Point2D location;
    private double speed = 0;
    private final String id;
    private double angle;

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    private boolean isActive;

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public Bullet (Point2D location, double angle) {
        this.location = location;
        this.angle = angle;
        this.id = UUID.randomUUID().toString();
        this.isActive = true;
        bullets.add(this);
        Controller.createBulletView(id);
    }
    public void accelerate () {
        if (speed < BULLET_SPEED) {
            speed += BULLET_ACCELERATION;
            speed = Math.min(BULLET_SPEED, speed);
        }
    }
    public void move() {
        double dx = Math.cos(angle) * speed, dy = Math.sin(angle) * speed;
        setLocation(new Point2D.Double(location.getX() + dx, location.getY() + dy));
    }
}
