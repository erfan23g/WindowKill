package controller;

import model.*;
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
    public static int upsCount = 0, fpsCount = 0;
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
        for (EntityView entityView : EntityView.entityViews) {
            entityView.setLocation(relativeLocation(Controller.findEntityLocation(entityView.getId()), GamePanelModel.getINSTANCE().getLocation()));
        }
        for (BulletView bulletView : BulletView.bulletViews) {
            if (bulletView.isActive()) {
                bulletView.setLocation(relativeLocation(Controller.findBulletLocation(bulletView.getId()), GamePanelModel.getINSTANCE().getLocation()));
            }
        }
        GamePanel.getINSTANCE().setLocation((int) GamePanelModel.getINSTANCE().getLocation().getX(), (int) GamePanelModel.getINSTANCE().getLocation().getY());
        GamePanel.getINSTANCE().setSize(GamePanelModel.getINSTANCE().getSize());

        GamePanel.getINSTANCE().repaint();
        fpsCount++;
    }

    public void updateModel() {
//        shrink();
        if (GameFrame.w) {
            Epsilon.getINSTANCE().accelerate(Direction.UP, true);
        } else {
            Epsilon.getINSTANCE().accelerate(Direction.UP, false);
        }
        if (GameFrame.s) {
            Epsilon.getINSTANCE().accelerate(Direction.DOWN, true);
        } else {
            Epsilon.getINSTANCE().accelerate(Direction.DOWN, false);
        }
        if (GameFrame.a) {
            Epsilon.getINSTANCE().accelerate(Direction.LEFT, true);
        } else {
            Epsilon.getINSTANCE().accelerate(Direction.LEFT, false);
        }
        if (GameFrame.d) {
            Epsilon.getINSTANCE().accelerate(Direction.RIGHT, true);
        } else {
            Epsilon.getINSTANCE().accelerate(Direction.RIGHT, false);
        }
        Epsilon.getINSTANCE().move();
        for (Entity entity : Entity.entities) {
            if (entity instanceof Enemy) {
                ((Enemy) entity).updateAngle(Epsilon.getINSTANCE().getLocation());
                ((Enemy) entity).accelerate();
                ((Enemy) entity).move();
//                System.out.println("angle: " + ((Enemy) entity).getAngle() + "| location: " + entity.getLocation());
            }
        }
        for (Bullet bullet : Bullet.bullets){
            if (bullet.isActive()) {
                bullet.accelerate();
                bullet.move();
                if (bullet.collisionPoint(GamePanelModel.getINSTANCE()) != null) {
                    bullet.setActive(false);
                    Controller.findBulletView(bullet.getId()).setActive(false);
                    // TODO expand
                    if (bullet.collisionPoint(GamePanelModel.getINSTANCE()).getY() == 0) {
                        GamePanelModel.getINSTANCE().setExpandUp(true);
                    }  else if (bullet.collisionPoint(GamePanelModel.getINSTANCE()).getY() == GamePanelModel.getINSTANCE().getSize().getHeight()) {
                        GamePanelModel.getINSTANCE().setExpandDown(true);
                    } else if (bullet.collisionPoint(GamePanelModel.getINSTANCE()).getX() == 0) {
                        GamePanelModel.getINSTANCE().setExpandLeft(true);
                    } else if (bullet.collisionPoint(GamePanelModel.getINSTANCE()).getX() == GamePanelModel.getINSTANCE().getSize().getWidth()) {
                        GamePanelModel.getINSTANCE().setExpandRight(true);
                    }
                }
            }
        }
        GamePanelModel.getINSTANCE().accelerate();
        GamePanelModel.getINSTANCE().expand();
        upsCount++;
    }

    private void shrink() {
        if (beginning) {
            if (GamePanelModel.getINSTANCE().getSize().getWidth() > MINIMUM_PANEL_SIZE.getWidth()) {
                GamePanelModel.getINSTANCE().setSize(new Dimension((int) (GamePanelModel.getINSTANCE().getSize().getWidth() - INITIAL_WIDTH_REDUCTION_PER_UPDATE), (int) GamePanelModel.getINSTANCE().getSize().getHeight()));
            }
            if (GamePanelModel.getINSTANCE().getSize().getHeight() > MINIMUM_PANEL_SIZE.getHeight()) {
                GamePanelModel.getINSTANCE().setSize(new Dimension((int) (GamePanelModel.getINSTANCE().getSize().getWidth()), (int) (GamePanelModel.getINSTANCE().getSize().getHeight() - INITIAL_HEIGHT_REDUCTION_PER_UPDATE)));
            }
            GamePanelModel.getINSTANCE().setLocationToCenter();
            if (GamePanelModel.getINSTANCE().getSize().getWidth() <= MINIMUM_PANEL_SIZE.getWidth() && GamePanelModel.getINSTANCE().getSize().getHeight() <= MINIMUM_PANEL_SIZE.getHeight()) {
                beginning = false;
            }
        } else {
            if (GamePanelModel.getINSTANCE().getSize().getWidth() > MINIMUM_PANEL_SIZE.getWidth()) {
                GamePanelModel.getINSTANCE().setSize(new Dimension((int) (GamePanelModel.getINSTANCE().getSize().getWidth() - WIDTH_REDUCTION_PER_UPDATE), (int) GamePanelModel.getINSTANCE().getSize().getHeight()));
            }
            if (GamePanelModel.getINSTANCE().getSize().getHeight() > MINIMUM_PANEL_SIZE.getHeight()) {
                GamePanelModel.getINSTANCE().setSize(new Dimension((int) (GamePanelModel.getINSTANCE().getSize().getWidth()), (int) (GamePanelModel.getINSTANCE().getSize().getHeight() - HEIGHT_REDUCTION_PER_UPDATE)));
            }
        }
    }

}
