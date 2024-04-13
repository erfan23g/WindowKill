package org.example;

import controller.Update;
import view.GameFrame;
import view.GamePanel;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameFrame.getINSTANCE();
            GamePanel.getINSTANCE();
            new Update();
        });
    }
}
