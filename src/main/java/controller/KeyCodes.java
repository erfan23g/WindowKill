package controller;

import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class KeyCodes {
    public static void loadKeyCodes() {
        File file = new File("src/main/java/data/keycodes.txt");
        try {
            Scanner scanner = new Scanner(file);
            MOVE_UP = Integer.parseInt(scanner.nextLine());
            MOVE_DOWN = Integer.parseInt(scanner.nextLine());
            MOVE_RIGHT = Integer.parseInt(scanner.nextLine());
            MOVE_LEFT = Integer.parseInt(scanner.nextLine());
            SHOW_INFO = Integer.parseInt(scanner.nextLine());
            STORE = Integer.parseInt(scanner.nextLine());
            ABILITY = Integer.parseInt(scanner.nextLine());
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static void saveKeyCodes() {
        File file = new File("src/main/java/data/keycodes.txt");
        try {
            FileWriter fileWriter = new FileWriter(file, false);
            fileWriter.append(MOVE_UP + "\n"
            + MOVE_DOWN + "\n"
            + MOVE_RIGHT + "\n"
            + MOVE_LEFT + "\n"
            + SHOW_INFO + "\n"
            + STORE + "\n"
            + ABILITY);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static int MOVE_UP = KeyEvent.VK_W;
    public static int MOVE_DOWN = KeyEvent.VK_S;
    public static int MOVE_RIGHT = KeyEvent.VK_D;
    public static int MOVE_LEFT = KeyEvent.VK_A;
    public static int SHOW_INFO = KeyEvent.VK_I;
    public static int STORE = KeyEvent.VK_B;
    public static int ABILITY = KeyEvent.VK_F;
}
