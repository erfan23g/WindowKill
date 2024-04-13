package view;

import model.Entity;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.UUID;

import static model.Entity.entities;

public abstract class EntityView {
    public static ArrayList<EntityView> entityViews = new ArrayList<>();

    private Point2D location = new Point2D.Double(0, 0);

    public String getId() {
        return id;
    }

    private final String id;

    public EntityView(String id) {
        this.id = id;
        entityViews.add(this);
    }

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public abstract void paint(Graphics g);
}
