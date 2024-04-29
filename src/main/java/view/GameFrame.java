package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import static controller.Constants.GAME_FRAME_SIZE;
import static controller.KeyCodes.*;


public class GameFrame extends JFrame implements KeyListener {
    private static GameFrame INSTANCE;
    public static boolean w, s, a, d, info;


    private GameFrame() throws HeadlessException {
        setUndecorated(true);
        setBackground(new Color(0, 0, 0, 0));
        setSize(new Dimension((int) GAME_FRAME_SIZE.getWidth(), (int) GAME_FRAME_SIZE.getHeight()));
        setFocusable(true);

        this.addKeyListener(this);
        setLocationRelativeTo(null);

        setVisible(true);
        setLayout(null);

    }

    public static GameFrame getINSTANCE() {
        if (INSTANCE == null) INSTANCE = new GameFrame();
        return INSTANCE;
    }

    public boolean isW() {
        return w;
    }

    public void setW(boolean w) {
        this.w = w;
    }

    public boolean isS() {
        return s;
    }

    public void setS(boolean s) {
        this.s = s;
    }

    public boolean isA() {
        return a;
    }

    public void setA(boolean a) {
        this.a = a;
    }

    public boolean isD() {
        return d;
    }

    public void setD(boolean d) {
        this.d = d;
    }

    public void keyTyped(KeyEvent e){

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == MOVE_UP) {
            System.out.println("hi");
            w = true;
        }
        if (e.getKeyCode() == MOVE_DOWN) {
            s = true;
        }
        if (e.getKeyCode() == MOVE_LEFT) {
            a = true;
        }
        if (e.getKeyCode() == MOVE_RIGHT) {
            d = true;
        }
        if (e.getKeyCode() == SHOW_INFO) {
            info = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == MOVE_UP) {
            w = false;
        }
        if (e.getKeyCode() == MOVE_DOWN) {
            s = false;
        }
        if (e.getKeyCode() == MOVE_LEFT) {
            a = false;
        }
        if (e.getKeyCode() == MOVE_RIGHT) {
            d = false;
        }
        if (e.getKeyCode() == SHOW_INFO) {
            info = false;
        }
    }

}
