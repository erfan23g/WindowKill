package model;

import view.GameFrame;
import view.GamePanel;

import java.awt.*;
import java.awt.geom.Point2D;

import static controller.Constants.INITIAL_PANEL_SIZE;
import static controller.Constants.PAGE_CENTER;

public class GamePanelModel {
    private Point2D location;
    private Dimension size;

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
}
