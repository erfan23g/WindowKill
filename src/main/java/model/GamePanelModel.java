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

    double speed = 0, speed2 = 0;

    private boolean expandUp, expandDown, expandLeft, expandRight;

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

    public void expand(Direction direction) {
        if (direction.equals(Direction.UP)) expandUp = true;
        if (direction.equals(Direction.DOWN)) expandDown = true;
        if (direction.equals(Direction.LEFT)) expandLeft = true;
        if (direction.equals(Direction.RIGHT)) expandRight = true;
        speed = 0;
        speed2 = 0;
        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (direction.equals(Direction.UP) && expandUp) {
                    if (speed2 < EXPANSION_SPEED2) speed2 += 20D / UPS;
                    else expandUp = false;
                    setSize(new Dimension((int) getSize().getWidth(), (int) (getSize().height + speed2)));
                    setLocation(new Point2D.Double(location.getX(), location.getY() + speed2));
                } else if (direction.equals(Direction.DOWN) && expandDown) {
                    if (speed < EXPANSION_SPEED) speed += 10D / UPS;
                    else expandDown = false;
                    setSize(new Dimension((int) (getSize().getWidth()), (int) (getSize().height + speed)));
                } else if (direction.equals(Direction.LEFT) && expandLeft) {
                    if (speed2 < EXPANSION_SPEED2) speed2 += 20D / UPS;
                    else expandLeft = false;
                    setLocation(new Point2D.Double(location.getX() - speed2, location.getY()));
                } else if (direction.equals(Direction.RIGHT) && expandRight) {
                    if (speed < EXPANSION_SPEED) speed += 10D / UPS;
                    else expandRight = false;
                    setSize(new Dimension((int) (getSize().getWidth() + speed), (getSize().height)));
                }
            }
        });
        timer.start();
        expand2(direction);
    }
    public void expand2 (Direction direction) {
        if (direction.equals(Direction.UP)) expandUp = true;
        if (direction.equals(Direction.DOWN)) expandDown = true;
        if (direction.equals(Direction.LEFT)) expandLeft = true;
        if (direction.equals(Direction.RIGHT)) expandRight = true;
        speed = 0;
        speed2 = 0;
        Timer timer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (direction.equals(Direction.UP) && expandUp) {
                    if (speed < EXPANSION_SPEED2) speed += 10D / UPS;
                    else expandUp = false;
                    setSize(new Dimension((int) getSize().getWidth(), (int) (getSize().height - speed2)));
                } else if (direction.equals(Direction.DOWN) && expandDown) {
                    if (speed2 < EXPANSION_SPEED) speed2 += 20D / UPS;
                    else expandDown = false;
                    setLocation(new Point2D.Double(location.getX(), location.getY() + speed2));
                } else if (direction.equals(Direction.LEFT) && expandLeft) {
                    if (speed < EXPANSION_SPEED2) speed += 10D / UPS;
                    else expandLeft = false;
                    setSize(new Dimension((int) (getSize().getWidth() + speed), (getSize().height)));
                } else if (direction.equals(Direction.RIGHT) && expandRight) {
                    if (speed2 < EXPANSION_SPEED) speed2 += 20D / UPS;
                    else expandRight = false;
                    setLocation(new Point2D.Double(location.getX() + speed2, location.getY()));
                }
            }
        });
        timer.start();
        System.out.println(GamePanelModel.getINSTANCE().getSize().getWidth() + " " + GamePanelModel.getINSTANCE().getSize().getHeight());
    }
}
