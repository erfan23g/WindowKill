package controller;

import view.GameFrame;
import view.GamePanel;

import javax.swing.*;

import java.util.ArrayList;

import static controller.Constants.*;

public class Update {
    private boolean beginning;
    public Update() {
        beginning = true;
        new Timer((int) FRAME_UPDATE_TIME, e -> updateView()){{setCoalesce(true);}}.start();
        new Timer((int) MODEL_UPDATE_TIME, e -> updateModel()){{setCoalesce(true);}}.start();
    }
    public void updateView(){
        if (beginning) {
            if (GamePanel.getINSTANCE().getSize().getWidth() > MINIMUM_PANEL_SIZE.getWidth()){
                GamePanel.getINSTANCE().setSize(GamePanel.getINSTANCE().getWidth() - INITIAL_WIDTH_REDUCTION_PER_UPDATE, GamePanel.getINSTANCE().getHeight());
            }
            if (GamePanel.getINSTANCE().getSize().getHeight() > MINIMUM_PANEL_SIZE.getHeight()){
                GamePanel.getINSTANCE().setSize(GamePanel.getINSTANCE().getWidth(), GamePanel.getINSTANCE().getHeight() - INITIAL_HEIGHT_REDUCTION_PER_UPDATE);
            }
            GamePanel.getINSTANCE().setLocationToCenter(GameFrame.getINSTANCE());
            if (GamePanel.getINSTANCE().getSize().getWidth() <= MINIMUM_PANEL_SIZE.getWidth() && GamePanel.getINSTANCE().getSize().getHeight() <= MINIMUM_PANEL_SIZE.getHeight()){
                beginning = false;
            }
        } else {
            if (GamePanel.getINSTANCE().getSize().getWidth() > MINIMUM_PANEL_SIZE.getWidth()){
                GamePanel.getINSTANCE().setSize(GamePanel.getINSTANCE().getWidth() - WIDTH_REDUCTION_PER_UPDATE, GamePanel.getINSTANCE().getHeight());
            }
            if (GamePanel.getINSTANCE().getSize().getHeight() > MINIMUM_PANEL_SIZE.getHeight()){
                GamePanel.getINSTANCE().setSize(GamePanel.getINSTANCE().getWidth(), GamePanel.getINSTANCE().getHeight() - HEIGHT_REDUCTION_PER_UPDATE);
            }
        }
    }
    public void updateModel(){

    }
}
