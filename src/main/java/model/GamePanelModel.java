package model;

import controller.Constants;
import controller.MyDimension;
import controller.Update;
import model.collision.Collidable;
import model.movement.Direction;
import view.GameFrame;
import view.GamePanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Dimension2D;
import java.awt.geom.Point2D;
import javax.swing.Timer;

import static controller.Constants.*;

public class GamePanelModel implements Collidable {

    private Point2D location;
    private MyDimension size;
    private boolean beginning = true;

    private boolean expandUp, expandDown, expandLeft, expandRight;
    private double upSpeed, downSpeed, leftSpeed, rightSpeed;
    private double horizontalSpeed, verticalSpeed;

    public boolean isExpandUp() {
        return expandUp;
    }

    public void setExpandUp(boolean expandUp) {
        this.expandUp = expandUp;
    }

    public boolean isExpandDown() {
        return expandDown;
    }

    public void setExpandDown(boolean expandDown) {
        this.expandDown = expandDown;
    }

    public boolean isExpandLeft() {
        return expandLeft;
    }

    public void setExpandLeft(boolean expandLeft) {
        this.expandLeft = expandLeft;
    }

    public boolean isExpandRight() {
        return expandRight;
    }

    public void setExpandRight(boolean expandRight) {
        this.expandRight = expandRight;
    }

    public Point2D getLocation() {
        return location;
    }

    public void setLocation(Point2D location) {
        this.location = location;
    }

    public MyDimension getSize() {
        return size;
    }

    public void setSize(MyDimension size) {
        this.size = size;
    }

    private static GamePanelModel INSTANCE;

    public static GamePanelModel getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new GamePanelModel();
        return INSTANCE;
    }

    public GamePanelModel() {
        size = INITIAL_PANEL_SIZE;
        setLocationToCenter();
//        Update.start();
    }

    public void setLocationToCenter() {
        setLocation(new Point2D.Double((GameFrame.getINSTANCE().getWidth() / 2 - getSize().getWidth() / 2), (GameFrame.getINSTANCE().getHeight() / 2 - getSize().getHeight() / 2)));
    }

    public void accelerate () {
        if (expandUp && upSpeed < EXPANSION_SPEED * ((double) 2 / Update.getDifficulty())) {
            upSpeed += EXPANSION_ACCELERATION;
            upSpeed = Math.min(upSpeed, EXPANSION_SPEED * ((double) 2 / Update.getDifficulty()));
        }
        if (expandDown && downSpeed < EXPANSION_SPEED * ((double) 2 / Update.getDifficulty())) {
            downSpeed += EXPANSION_ACCELERATION;
            downSpeed = Math.min(downSpeed, EXPANSION_SPEED * ((double) 2 / Update.getDifficulty()));
        }
        if (expandLeft && leftSpeed < EXPANSION_SPEED * ((double) 2 / Update.getDifficulty())) {
            leftSpeed += EXPANSION_ACCELERATION;
            leftSpeed = Math.min(leftSpeed, EXPANSION_SPEED * ((double) 2 / Update.getDifficulty()));
        }
        if (expandRight && rightSpeed < EXPANSION_SPEED * ((double) 2 / Update.getDifficulty())) {
            rightSpeed += EXPANSION_ACCELERATION;
            rightSpeed = Math.min(rightSpeed, EXPANSION_SPEED * ((double) 2 / Update.getDifficulty()));
        }
        if (beginning) {
            if (getSize().getWidth() > MINIMUM_PANEL_SIZE.getWidth() && horizontalSpeed < INITIAL_SHRINKAGE_SPEED) {
                horizontalSpeed += INITIAL_SHRINKAGE_ACCELERATION;
                horizontalSpeed = Math.min(INITIAL_SHRINKAGE_SPEED, horizontalSpeed);
            }
            if (getSize().getHeight() > MINIMUM_PANEL_SIZE.getHeight() && verticalSpeed < INITIAL_SHRINKAGE_SPEED) {
                verticalSpeed += INITIAL_SHRINKAGE_ACCELERATION;
                verticalSpeed = Math.min(INITIAL_SHRINKAGE_SPEED, verticalSpeed);
            }
        } else {
            if (getSize().getWidth() > MINIMUM_PANEL_SIZE.getWidth() && horizontalSpeed < SHRINKAGE_SPEED * ((double) Update.getDifficulty() / 2)) {
                horizontalSpeed += SHRINKAGE_ACCELERATION;
                horizontalSpeed = Math.min(SHRINKAGE_SPEED * ((double) Update.getDifficulty() / 2), horizontalSpeed);
            }
            if (getSize().getHeight() > MINIMUM_PANEL_SIZE.getHeight() && verticalSpeed < SHRINKAGE_SPEED * ((double) Update.getDifficulty() / 2)) {
                verticalSpeed += SHRINKAGE_ACCELERATION;
                verticalSpeed = Math.min(SHRINKAGE_SPEED * ((double) Update.getDifficulty() / 2), verticalSpeed);
            }
        }
    }

    public void expand () {
        if (upSpeed > 0) {
            setLocation(new Point2D.Double(location.getX(), location.getY() - upSpeed));
            setSize(new MyDimension(getSize().getWidth(), getSize().getHeight() + upSpeed));
            if (upSpeed == EXPANSION_SPEED * ((double) 2 / Update.getDifficulty())) {
                upSpeed = 0;
                expandUp = false;
            }
        }
        if (downSpeed > 0) {
            setSize(new MyDimension(getSize().getWidth(), getSize().getHeight() + downSpeed));
            setLocation(new Point2D.Double(location.getX(), location.getY() + downSpeed));
            if (downSpeed == EXPANSION_SPEED * ((double) 2 / Update.getDifficulty())) {
                downSpeed = 0;
                expandDown = false;
            }
        }
        if (leftSpeed > 0) {
            setLocation(new Point2D.Double(location.getX() - leftSpeed, location.getY()));
            setSize(new MyDimension(getSize().getWidth() + leftSpeed, getSize().getHeight()));
            if (leftSpeed == EXPANSION_SPEED * ((double) 2 / Update.getDifficulty())) {
                leftSpeed = 0;
                expandLeft = false;
            }
        }
        if (rightSpeed > 0) {
            setSize(new MyDimension(getSize().getWidth() + rightSpeed, getSize().getHeight()));
            setLocation(new Point2D.Double(location.getX() + rightSpeed, location.getY()));
            if (rightSpeed == EXPANSION_SPEED * ((double) 2 / Update.getDifficulty())) {
                rightSpeed = 0;
                expandRight = false;
            }
        }
//        double x = Math.max(0, getLocation().getX()), y = Math.max(0, getLocation().getY());
//        x = Math.min(GAME_FRAME_SIZE.getWidth() - getSize().getWidth(), x);
//        y = Math.min(GAME_FRAME_SIZE.getHeight() - getSize().getHeight(), y);
//        setLocation(new Point2D.Double(x, y));
    }
    public void shrink () {
        double x = getLocation().getX();
        double y = getLocation().getY();
        double width = getSize().getWidth() - horizontalSpeed;
        double height = getSize().getHeight() - verticalSpeed;
        width = Math.max(MINIMUM_PANEL_SIZE.getWidth(), width);
        height = Math.max(MINIMUM_PANEL_SIZE.getHeight(), height);
        if (width < getSize().getWidth()) {
            x += horizontalSpeed / 2;
        }
        if (height < getSize().getHeight()) {
            y += verticalSpeed / 2;
        }
        setSize(new MyDimension(width, height));
        setLocation(new Point2D.Double(x, y));
        if (width == MINIMUM_PANEL_SIZE.getWidth()) horizontalSpeed = 0;
        if (height == MINIMUM_PANEL_SIZE.getHeight()) verticalSpeed = 0;
        if (width == MINIMUM_PANEL_SIZE.getWidth() && height == MINIMUM_PANEL_SIZE.getHeight()) beginning = false;
//        double x2 = Math.max(0, getLocation().getX()), y2 = Math.max(0, getLocation().getY());
//        x = Math.min(GAME_FRAME_SIZE.getWidth() - getSize().getWidth(), x2);
//        y = Math.min(GAME_FRAME_SIZE.getHeight() - getSize().getHeight(), y2);
//        setLocation(new Point2D.Double(x2, y2));
    }
    public static void dispose() {
        INSTANCE = null;
    }
}
