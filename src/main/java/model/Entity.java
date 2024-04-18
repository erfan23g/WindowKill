package model;

import controller.Controller;
import model.collision.Collidable;
import model.movement.Direction;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public abstract class Entity implements Collidable {
    public static ArrayList<Entity> entities = new ArrayList<>();
    private final ArrayList<HashMap<String, Double>> impactAngles = new ArrayList<>();

    public ArrayList<HashMap<String, Double>> getImpactAngles() {
        return impactAngles;
    }
    private Point2D location;
    private int hp;

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

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
    public abstract void damage(int reduction);
}
