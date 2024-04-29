package view;

import model.Collectible;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import static controller.Constants.COLLECTIBLE_RADIUS;

public class CollectibleView {
    public static ArrayList<CollectibleView> collectibleViews = new ArrayList<>();
    private final Color color;

    public void setLocation(Point2D location) {
        this.location = location;
    }

    private Point2D location = new Point2D.Double();
    private final String id;
    private boolean isActive;

    public Color getColor() {
        return color;
    }


    public Point2D getLocation() {
        return location;
    }


    public String getId() {
        return id;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public CollectibleView (String id, Color color) {
        this.id = id;
        this.color = color;
        isActive = true;
        collectibleViews.add(this);
    }
    public void draw (Graphics g) {
        g.setColor(getColor());
        g.fillOval((int) (getLocation().getX() - COLLECTIBLE_RADIUS), (int) (getLocation().getY() - COLLECTIBLE_RADIUS), (int) ((COLLECTIBLE_RADIUS * 2)), (int) (COLLECTIBLE_RADIUS * 2));
    }
}
