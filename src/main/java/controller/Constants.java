package controller;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Constants {
    public static final Dimension GAME_FRAME_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final Dimension INITIAL_PANEL_SIZE = new Dimension((int) (GAME_FRAME_SIZE.getWidth() / 2), (int) (GAME_FRAME_SIZE.getHeight() / 2));
    public static final Dimension MINIMUM_PANEL_SIZE = new Dimension((int) (GAME_FRAME_SIZE.getWidth() / 4), (int) (GAME_FRAME_SIZE.getHeight() / 4));
    public static final int FPS = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getRefreshRate();
    public static final double FRAME_UPDATE_TIME = (double) TimeUnit.SECONDS.toMillis(1) / FPS;
    public static final int UPS = 200;
    public static final double MODEL_UPDATE_TIME = (double) TimeUnit.SECONDS.toMillis(1) / UPS;
    public static final int INITIAL_WIDTH_REDUCTION_PER_UPDATE = 9;
    public static final int WIDTH_REDUCTION_PER_UPDATE = 3;
    public static final int INITIAL_HEIGHT_REDUCTION_PER_UPDATE = 6;
    public static final int HEIGHT_REDUCTION_PER_UPDATE = 2;
    public static final double EPSILON_SPEED = 1;
    public static final double BULLET_SPEED = 4;
    public static final double EPSILON_RADIUS = 20;
    public static final double BULLET_RADIUS = 2;


}
