package controller;

import model.Bullet;
import model.Entity;
import model.Epsilon;
import model.GamePanelModel;
import model.movement.Direction;
import view.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import static controller.Constants.*;
import static controller.Utils.relativeLocation;

public class Update {
    private boolean beginning;

    public Update() {
        beginning = true;
        new Timer((int) FRAME_UPDATE_TIME, e -> updateView()) {{
            setCoalesce(true);
        }}.start();
        new Timer((int) MODEL_UPDATE_TIME, e -> updateModel()) {{
            setCoalesce(true);
        }}.start();
    }

    public void updateView() {
        // TODO fix shrink
//        shrink();

        for (EntityView entityView : EpsilonView.entityViews) {
            entityView.setLocation(Controller.findEntityLocation(entityView.getId()));
        }
        for (BulletView bulletView : BulletView.bulletViews) {
            bulletView.setLocation(Controller.findBulletLocation(bulletView.getId()));
        }
        GamePanel.getINSTANCE().setLocation((int) GamePanelModel.getINSTANCE().getLocation().getX(), (int) GamePanelModel.getINSTANCE().getLocation().getY());
        GamePanel.getINSTANCE().setSize(GamePanelModel.getINSTANCE().getSize());

        GamePanel.getINSTANCE().repaint();
    }

    public void updateModel() {
//        System.out.println(GameFrame.getINSTANCE());
        if (GameFrame.w) {
            Epsilon.getINSTANCE().move(Direction.UP, EPSILON_SPEED);
        }
        if (GameFrame.s) {
            Epsilon.getINSTANCE().move(Direction.DOWN, EPSILON_SPEED);
        }
        if (GameFrame.a) {
            Epsilon.getINSTANCE().move(Direction.LEFT, EPSILON_SPEED);
        }
        if (GameFrame.d) {
            Epsilon.getINSTANCE().move(Direction.RIGHT, EPSILON_SPEED);
        }
        for (Bullet bullet : Bullet.bullets){
            bullet.move();
        }
    }

    private void shrink() {
        if (beginning) {
            if (GamePanel.getINSTANCE().getSize().getWidth() > MINIMUM_PANEL_SIZE.getWidth()) {
                GamePanel.getINSTANCE().setSize(GamePanel.getINSTANCE().getWidth() - INITIAL_WIDTH_REDUCTION_PER_UPDATE, GamePanel.getINSTANCE().getHeight());
            }
            if (GamePanel.getINSTANCE().getSize().getHeight() > MINIMUM_PANEL_SIZE.getHeight()) {
                GamePanel.getINSTANCE().setSize(GamePanel.getINSTANCE().getWidth(), GamePanel.getINSTANCE().getHeight() - INITIAL_HEIGHT_REDUCTION_PER_UPDATE);
            }
            GamePanel.getINSTANCE().setLocationToCenter();
            if (GamePanel.getINSTANCE().getSize().getWidth() <= MINIMUM_PANEL_SIZE.getWidth() && GamePanel.getINSTANCE().getSize().getHeight() <= MINIMUM_PANEL_SIZE.getHeight()) {
                beginning = false;
            }
        } else {
            if (GamePanel.getINSTANCE().getSize().getWidth() > MINIMUM_PANEL_SIZE.getWidth()) {
                GamePanel.getINSTANCE().setSize(GamePanel.getINSTANCE().getWidth() - WIDTH_REDUCTION_PER_UPDATE, GamePanel.getINSTANCE().getHeight());
            }
            if (GamePanel.getINSTANCE().getSize().getHeight() > MINIMUM_PANEL_SIZE.getHeight()) {
                GamePanel.getINSTANCE().setSize(GamePanel.getINSTANCE().getWidth(), GamePanel.getINSTANCE().getHeight() - HEIGHT_REDUCTION_PER_UPDATE);
            }
        }
    }

}
