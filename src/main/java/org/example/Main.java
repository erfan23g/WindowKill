package org.example;

import controller.Update;
import model.Entity;
import model.Epsilon;
import model.GamePanelModel;
import view.GameFrame;
import view.GamePanel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame.getINSTANCE();
            GamePanel.getINSTANCE();
            GamePanelModel.getINSTANCE();
            Epsilon.getINSTANCE();
            new Update();
            new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("UPS: " + Update.upsCount + " | FPS: " + Update.fpsCount);
                    Update.upsCount = 0;
                    Update.fpsCount = 0;
                }
            }).start();
        });
    }
}
