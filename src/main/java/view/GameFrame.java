package view;

import controller.Update;
import model.GamePanelModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.Year;

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

        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                Update.setMouse(e.getPoint());
            }
        });
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
        if (Update.inGame) {

            if (e.getKeyCode() == MOVE_UP) {
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
            System.out.println(SHOW_INFO);
            System.out.println(e.getKeyCode());
            if (e.getKeyCode() == SHOW_INFO) {
                info = true;
            }
            if (e.getKeyCode() == STORE) {
                if (!StorePanel.isNull()){
                    if (StorePanel.isOpen){
                        Update.closeStore();
                    } else {
                        if (Update.isTripleShot()) {
                            Update.changeError("Cannot use store while \"O’ Athena، Empower\" is enabled");
                        } else {
                            Update.openStore();
                        }
                    }
                }
            }
            if (e.getKeyCode() == ABILITY) {
                Update.activateAbility();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (Update.inGame) {
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

}
