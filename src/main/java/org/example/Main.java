package org.example;

import controller.Update;
import model.Entity;
import model.Epsilon;
import view.GameFrame;
import view.GamePanel;

import javax.swing.*;
import java.awt.geom.Point2D;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame.getINSTANCE();
            GamePanel.getINSTANCE();
            Epsilon.getINSTANCE();
            new Update();
        });
    }
}
