package model;

import controller.Controller;
import model.collision.Collidable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.UUID;

public class Collectible implements Collidable {
    public static ArrayList<Collectible> collectibles = new ArrayList<>();
    private final Color color;
    private final Point2D location;
    private int xp;
    private final String id;
    private boolean isActive;

    public String getId() {
        return id;
    }


    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Collectible(Color color, Point2D location, int xp) {
        this.color = color;
        this.location = location;
        this.xp = xp;
        this.id = UUID.randomUUID().toString();
        isActive = true;
        collectibles.add(this);
        Controller.createCollectibleView(id);
        new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setActive(false);
                Controller.deactivateCollectibleView(id);
            }
        }).start();
    }

    public Color getColor() {
        return color;
    }


    public Point2D getLocation() {
        return location;
    }


    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }
}
