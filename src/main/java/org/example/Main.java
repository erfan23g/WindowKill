package org.example;

import controller.Update;
import model.*;
import view.GameFrame;
import view.GamePanel;

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
//            if (System.getProperty("os.name").toLowerCase().contains("mac")) {
//                try {
//                    String appName = "java"; // Modify this to match your Java app's name
//                    String script = "tell application \"System Events\"\n"
//                            + "set allProcesses to every application process where visible is true and name is not \"" + appName + "\"\n"
//                            + "repeat with proc in allProcesses\n"
//                            + "set proc's visible to false\n"
//                            + "end repeat\n"
//                            + "end tell";
//
//                    String[] cmd = { "osascript", "-e", script };
//                    Runtime.getRuntime().exec(cmd);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            } else if (System.getProperty("os.name").toLowerCase().contains("windows")) {
//                try {
//                    String[] command = {
//                            "powershell.exe",
//                            "Get-Process | Where-Object {$_.MainWindowTitle -ne \"\" -and $_.ProcessName -notlike \"*java*\"} | ForEach-Object { $_.MainWindowHandle | ForEach-Object { Add-Type -AssemblyName System.Windows.Forms; [System.Windows.Forms.SendKeys]::SendWait(\"% n\") }}"
//                    };
//                    Runtime.getRuntime().exec(command);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
            GameFrame.getINSTANCE();
            GamePanel.getINSTANCE();
            GamePanelModel.getINSTANCE();
            new Squarantine(new Point2D.Double(500, 500));
            new Squarantine(new Point2D.Double(600, 500));
//            new Squarantine(new Point2D.Double(700, 500));
            new Trigorath(new Point2D.Double(800, 600));
            new Trigorath(new Point2D.Double(800, 700));
//            new Trigorath(new Point2D.Double(800, 800));
            new Update();
            new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
//                    System.out.println("UPS: " + Update.upsCount + " | FPS: " + Update.fpsCount);
                    Update.upsCount = 0;
                    Update.fpsCount = 0;
                }
            }).start();
        });
    }
}
