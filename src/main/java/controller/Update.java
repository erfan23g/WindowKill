package controller;

import model.Entity;
import model.Epsilon;
import model.movement.Direction;
import view.EntityView;
import view.EpsilonView;
import view.GameFrame;
import view.GamePanel;

import javax.swing.*;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
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
//        shrink();
        for (EntityView entityView : EpsilonView.entityViews) {
            entityView.setLocation(Controller.findEntityLocation(entityView.getId()));
        }
        GamePanel.getINSTANCE().repaint();
    }

    public void updateModel() {
        if (GameFrame.getINSTANCE().isW()) {
            Epsilon.getINSTANCE().move(Direction.UP, EPSILON_SPEED);
        }
        if (GameFrame.getINSTANCE().isS()) {
            Epsilon.getINSTANCE().move(Direction.DOWN, EPSILON_SPEED);
        }
        if (GameFrame.getINSTANCE().isA()) {
            Epsilon.getINSTANCE().move(Direction.LEFT, EPSILON_SPEED);
        }
        if (GameFrame.getINSTANCE().isD()) {
            Epsilon.getINSTANCE().move(Direction.RIGHT, EPSILON_SPEED);
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
            GamePanel.getINSTANCE().setLocationToCenter(GameFrame.getINSTANCE());
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
