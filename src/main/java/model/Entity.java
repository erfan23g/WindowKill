package model;

import controller.Controller;
import model.movement.Direction;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.UUID;

public abstract class Entity {
    public static ArrayList<Entity> entities = new ArrayList<>();
    private Point2D location;
    public String getId() {
        return id;
    }

    private final String id;

    public Entity(Point2D location) {
        this.location = location;
        id = UUID.randomUUID().toString();
        entities.add(this);
        Controller.createEntityView(id);
    }

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }
}
