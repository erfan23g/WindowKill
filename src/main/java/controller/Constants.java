package controller;

import model.GamePanelModel;
import view.GameFrame;
import view.GamePanel;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.concurrent.TimeUnit;

public class Constants {
    public static final Dimension GAME_FRAME_SIZE = Toolkit.getDefaultToolkit().getScreenSize();
    public static final Dimension INITIAL_PANEL_SIZE = new Dimension((int) (GAME_FRAME_SIZE.getWidth() / 2), (int) (GAME_FRAME_SIZE.getHeight() / 2));
    public static final Dimension MINIMUM_PANEL_SIZE = new Dimension((int) (GAME_FRAME_SIZE.getWidth() / 4), (int) (GAME_FRAME_SIZE.getHeight() / 4));
    public static final Point2D PAGE_CENTER = new Point2D.Double((GameFrame.getINSTANCE().getWidth() / 2 - GamePanelModel.getINSTANCE().getSize().getWidth() / 2), (GameFrame.getINSTANCE().getHeight() / 2 - GamePanelModel.getINSTANCE().getSize().getHeight() / 2));
    public static final int FPS = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0].getDisplayMode().getRefreshRate();
    public static final double FRAME_UPDATE_TIME = (double) TimeUnit.SECONDS.toMillis(1) / FPS;
    public static final int UPS = 200;
    public static final double MODEL_UPDATE_TIME = (double) TimeUnit.SECONDS.toMillis(1) / UPS;
    public static final double INITIAL_WIDTH_REDUCTION_PER_UPDATE = 1.2;
    public static final double WIDTH_REDUCTION_PER_UPDATE = 0.3;
    public static final double INITIAL_HEIGHT_REDUCTION_PER_UPDATE = 0.8;
    public static final double HEIGHT_REDUCTION_PER_UPDATE = 0.2;
    public static final double EPSILON_SPEED = 1;
    public static final double BULLET_SPEED = 4;
    public static final double EPSILON_RADIUS = 20;
    public static final double BULLET_RADIUS = 2;


}
