package view;

import model.Bullet;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import static controller.Constants.BULLET_RADIUS;

public class BulletView {
    public static ArrayList<BulletView> bulletViews = new ArrayList<>();
    private boolean isActive;
    private final String id;
    private Point2D location = new Point2D.Double(0, 0);

    public String getId() {
        return id;
    }

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public BulletView(String id) {
        this.id = id;
        this.isActive = true;
        bulletViews.add(this);
    }
    public void draw (Graphics g) {
        g.setColor(Color.red);
        g.drawOval((int) (getLocation().getX() - BULLET_RADIUS), (int) (getLocation().getY() - BULLET_RADIUS), (int) ((BULLET_RADIUS * 2)), (int) (BULLET_RADIUS * 2));
    }
}
