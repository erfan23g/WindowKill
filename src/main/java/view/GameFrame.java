package view;

import javax.swing.*;
import java.awt.*;

import static controller.Constants.GAME_FRAME_SIZE;


public class GameFrame extends JFrame {
    private static GameFrame INSTANCE;

    private GameFrame() throws HeadlessException {
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setSize(GAME_FRAME_SIZE);
        setLocationRelativeTo(null);
        setVisible(true);
        setLayout(null);
    }

    public static GameFrame getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new GameFrame();
        return INSTANCE;
    }
}
