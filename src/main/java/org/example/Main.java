package org.example;

import controller.KeyCodes;
import controller.Update;
import model.*;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame.getINSTANCE();
            StartingPanel.getINSTANCE();
            SkillTreePanel.getINSTANCE();
            SettingsPanel.getINSTANCE();
            KeyCodes.loadKeyCodes();

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
