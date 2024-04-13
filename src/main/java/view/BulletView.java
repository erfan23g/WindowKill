package view;

import model.Bullet;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class BulletView {
    public static ArrayList<BulletView> bulletViews = new ArrayList<>();
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

    public BulletView(String id) {
        this.id = id;
        bulletViews.add(this);
    }
}
