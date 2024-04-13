package model;

import controller.Controller;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import static controller.Constants.BULLET_SPEED;

public class Bullet {
    public static ArrayList<Bullet> bullets = new ArrayList<>();
    private Point2D location;
    private final String id;
    private double angle;

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
        bullets.add(this);
        Controller.createBulletView(id);
    }
    public void move() {
        double dx = Math.cos(angle) * BULLET_SPEED, dy = Math.sin(angle) * BULLET_SPEED;
        setLocation(new Point2D.Double(location.getX() + dx, location.getY() + dy));
    }
}
