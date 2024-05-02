package controller;

import model.GamePanelModel;
import view.GameFrame;
import view.GamePanel;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.concurrent.TimeUnit;

public class Constants {
    public static final MyDimension GAME_FRAME_SIZE = new MyDimension(Toolkit.getDefaultToolkit().getScreenSize().getWidth(), Toolkit.getDefaultToolkit().getScreenSize().getHeight());
    public static final Point2D EPSILON_STARTING_LOCATION = new Point2D.Double(Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2, Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
    public static final MyDimension INITIAL_PANEL_SIZE = new MyDimension((2 * GAME_FRAME_SIZE.getWidth() / 3),(2 * GAME_FRAME_SIZE.getHeight() / 3));
    public static final MyDimension MINIMUM_PANEL_SIZE = new MyDimension((GAME_FRAME_SIZE.getWidth() / 3), (GAME_FRAME_SIZE.getHeight() / 3));
    public static final Point2D PAGE_CENTER = new Point2D.Double((GameFrame.getINSTANCE().getWidth() / 2 - GamePanelModel.getINSTANCE().getSize().getWidth() / 2), (GameFrame.getINSTANCE().getHeight() / 2 - GamePanelModel.getINSTANCE().getSize().getHeight() / 2));
    public static final int FPS = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getRefreshRate();
    public static final double FRAME_UPDATE_TIME = (double) TimeUnit.SECONDS.toMillis(1) / FPS;
    public static final int UPS = 200;
    public static final double MODEL_UPDATE_TIME = (double) TimeUnit.SECONDS.toMillis(1) / UPS;
    public static final double EPSILON_RADIUS = 20;
    public static final double BULLET_RADIUS = 2;
    public static final double COLLECTIBLE_RADIUS = 4;
    public static final double IMPACT_RADIUS = 60;
    public static final double ENEMY_SIDE_LENGTH = 30;
    public static final double EPSILON_SPEED = (double) 200 / UPS;
    public static final double EPSILON_ACCELERATION = (double) 1 / UPS;
    public static final double BULLET_SPEED = (double) 800 / UPS;
    public static final double BULLET_ACCELERATION = (double) 4 / UPS;
    public static final double EXPANSION_SPEED = (double) 500 / UPS;
    public static final double EXPANSION_ACCELERATION = (double) 10 / UPS;
    public static final double SHRINKAGE_SPEED = (double) 40 / UPS;
    public static final double SHRINKAGE_ACCELERATION = (double) SHRINKAGE_SPEED / 100;
    public static final double INITIAL_SHRINKAGE_SPEED = (double) 100 / UPS;
    public static final double INITIAL_SHRINKAGE_ACCELERATION = (double) INITIAL_SHRINKAGE_SPEED / 100;
    public static final double ENEMY_SPEED = (double) 100 / UPS;
    public static final double FAR_SPEED = (double) 200 / UPS;
    public static final double DASH_SPEED = (double) 500 / UPS;
    public static final double ENEMY_ACCELERATION = (double) 5 / UPS;
    public static final double DASH_ACCELERATION = (double) 50 / UPS;
    public static final double ENEMY_ROTATION_SPEED = (double) 4500 / UPS;
    public static final double ENEMY_ROTATION_ACCELERATION = (double) 200 / UPS;
    public static final int COLLECTIBLE_XP = 5;

    public static final int ENEMIES_PER_WAVE = 4;

}
