package model;

import controller.Controller;
import controller.Utils;
import model.collision.Collidable;
import model.movement.Direction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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


    private final Timer coolDownTimer;
    private boolean coolDown;
    public boolean isCoolDown() {
        return coolDown;
    }

    public void setCoolDown(boolean coolDown) {
        this.coolDown = coolDown;
    }

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
        coolDownTimer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setCoolDown(false);
                coolDownTimer.stop();
            }
        });
        Controller.createEntityView(id);
    }

    public abstract ArrayList<Point2D> getVertices ();

    public void startCoolDownTimer() {
        coolDownTimer.start();
    }
    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }
    public void damage(int reduction) {
        Utils.playSound("src/main/java/soundeffects/damage.wav");
    };
}
