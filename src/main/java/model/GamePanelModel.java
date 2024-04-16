package model;

import controller.Constants;
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
    private Dimension size;

    private boolean expandUp, expandDown, expandLeft, expandRight;
    private double upSpeed, downSpeed, leftSpeed, rightSpeed;

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

    public Dimension getSize() {
        return size;
    }

    public void setSize(Dimension size) {
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
    }

    public void setLocationToCenter() {
        setLocation(new Point2D.Double((GameFrame.getINSTANCE().getWidth() / 2 - getSize().getWidth() / 2), (GameFrame.getINSTANCE().getHeight() / 2 - getSize().getHeight() / 2)));
    }

    public void accelerate () {
        if (expandUp && upSpeed < EXPANSION_SPEED) {
            upSpeed += EXPANSION_ACCELERATION;
            upSpeed = Math.min(upSpeed, EXPANSION_SPEED);
        }
        if (expandDown && downSpeed < EXPANSION_SPEED) {
            downSpeed += EXPANSION_ACCELERATION;
            downSpeed = Math.min(downSpeed, EXPANSION_SPEED);
        }
        if (expandLeft && leftSpeed < EXPANSION_SPEED) {
            leftSpeed += EXPANSION_ACCELERATION;
            leftSpeed = Math.min(leftSpeed, EXPANSION_SPEED);
        }
        if (expandRight && rightSpeed < EXPANSION_SPEED) {
            rightSpeed += EXPANSION_ACCELERATION;
            rightSpeed = Math.min(rightSpeed, EXPANSION_SPEED);
        }
    }

    public void expand () {
        if (upSpeed > 0) {
            setLocation(new Point2D.Double(location.getX(), location.getY() - upSpeed));
            setSize(new Dimension((int) getSize().getWidth(), (int) (getSize().height + upSpeed)));
            if (upSpeed == EXPANSION_SPEED) {
                upSpeed = 0;
                expandUp = false;
            }
        }
        if (downSpeed > 0) {
            setSize(new Dimension((int) getSize().getWidth(), (int) (getSize().height + downSpeed)));
            setLocation(new Point2D.Double(location.getX(), location.getY() + downSpeed));
            if (downSpeed == EXPANSION_SPEED) {
                downSpeed = 0;
                expandDown = false;
            }
        }
        if (leftSpeed > 0) {
            setLocation(new Point2D.Double(location.getX() - leftSpeed, location.getY()));
            setSize(new Dimension((int) (getSize().getWidth() + leftSpeed), (getSize().height)));
            if (leftSpeed == EXPANSION_SPEED) {
                leftSpeed = 0;
                expandLeft = false;
            }
        }
        if (rightSpeed > 0) {
            setSize(new Dimension((int) (getSize().getWidth() + rightSpeed), (getSize().height)));
            setLocation(new Point2D.Double(location.getX() + rightSpeed, location.getY()));
            if (rightSpeed == EXPANSION_SPEED) {
                rightSpeed = 0;
                expandRight = false;
            }
        }
    }

}
