package controller;

import model.*;
import model.movement.Direction;
import org.example.Main;
import view.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static controller.Constants.*;
import static controller.Utils.relativeLocation;

public class Update {
    public static int upsCount = 0, fpsCount = 0;
    private static Point2D mouse = new Point2D.Double();

    public static Point2D getMouse() {
        return mouse;
    }

    public static void setMouse(Point2D mouse) {
        Update.mouse = mouse;
    }

    private static String error = "";
    public static int ability = 0;
    public static int wave;
    public static boolean inBetweenWaves;
    private static Timer modelTimer, viewTimer, waveTimer, empowerTimer, errorTimer, abilityCoolDownTimer;
    private static long storeTime, storeTime2;
    private static boolean tripleShot, abilityCoolDown;
    private static int difficulty = 2;

    public static int getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(int difficulty) {
        Update.difficulty = difficulty;
    }

    public static int getSensitivity() {
        return sensitivity;
    }

    public static void setSensitivity(int sensitivity) {
        Update.sensitivity = sensitivity;
    }

    private static int sensitivity = 50;


    public static void updateView() {
        for (EntityView entityView : EntityView.entityViews) {
            entityView.setLocation(relativeLocation(Controller.findEntityLocation(entityView.getId()), GamePanelModel.getINSTANCE().getLocation()));
            if (entityView instanceof SquarantineView) {
                Enemy entityModel = (Enemy) Controller.findEntity(entityView.getId());
                ((SquarantineView) entityView).setActive(entityModel.isActive());
                ((SquarantineView) entityView).setHp(entityModel.getHp());
                Polygon shape = entityModel.getShape();
                int[] xPoints = new int[4];
                int[] yPoints = new int[4];
                for (int i = 0; i < 4; i++) {
                    Point2D point2D = relativeLocation(new Point2D.Double(shape.xpoints[i], shape.ypoints[i]), GamePanelModel.getINSTANCE().getLocation());
                    xPoints[i] = (int) point2D.getX();
                    yPoints[i] = (int) point2D.getY();
                }
                ((SquarantineView) entityView).setShape(new Polygon(xPoints, yPoints, 4));
            } else if (entityView instanceof TrigorathView) {
                Enemy entityModel = (Enemy) Controller.findEntity(entityView.getId());
                ((TrigorathView) entityView).setActive(entityModel.isActive());
                ((TrigorathView) entityView).setHp(entityModel.getHp());
                Polygon shape = entityModel.getShape();
                int[] xPoints = new int[3];
                int[] yPoints = new int[3];
                for (int i = 0; i < 3; i++) {
                    Point2D point2D = relativeLocation(new Point2D.Double(shape.xpoints[i], shape.ypoints[i]), GamePanelModel.getINSTANCE().getLocation());
                    xPoints[i] = (int) point2D.getX();
                    yPoints[i] = (int) point2D.getY();
                }
                ((TrigorathView) entityView).setShape(new Polygon(xPoints, yPoints, 3));
            } else if (entityView instanceof EpsilonView) {
                ArrayList<Point2D> verticeViews = new ArrayList<>();
                for (Point2D point2D : Epsilon.getINSTANCE().getVertices()) {
                    verticeViews.add(relativeLocation(point2D, GamePanelModel.getINSTANCE().getLocation()));
                }
                ((EpsilonView) entityView).setVertices(verticeViews);
            }
        }
        for (BulletView bulletView : BulletView.bulletViews) {
            if (bulletView.isActive()) {
                bulletView.setLocation(relativeLocation(Controller.findBulletLocation(bulletView.getId()), GamePanelModel.getINSTANCE().getLocation()));
            }
        }
        for (CollectibleView collectibleView : CollectibleView.collectibleViews) {
            if (collectibleView.isActive()) {
                collectibleView.setLocation(Utils.relativeLocation(Controller.findCollectibleLocation(collectibleView.getId()), GamePanelModel.getINSTANCE().getLocation()));
            }
        }
        GamePanel.getINSTANCE().setLocation((int) GamePanelModel.getINSTANCE().getLocation().getX(), (int) GamePanelModel.getINSTANCE().getLocation().getY());
        GamePanel.getINSTANCE().setSize(new Dimension((int) GamePanelModel.getINSTANCE().getSize().getWidth(), (int) GamePanelModel.getINSTANCE().getSize().getHeight()));
        GamePanel.getINSTANCE().setEpsilonXp(Epsilon.getINSTANCE().getXp());
        GamePanel.getINSTANCE().setEpsilonHp(Epsilon.getINSTANCE().getHp());

        GamePanel.getINSTANCE().repaint();
        fpsCount++;
    }

    public static boolean isTripleShot() {
        return tripleShot;
    }

    public static void updateModel() {
//        shrink();
        if (GameFrame.w) {
            Epsilon.getINSTANCE().accelerate(Direction.UP, true);
        } else {
            Epsilon.getINSTANCE().accelerate(Direction.UP, false);
        }
        if (GameFrame.s) {
            Epsilon.getINSTANCE().accelerate(Direction.DOWN, true);
        } else {
            Epsilon.getINSTANCE().accelerate(Direction.DOWN, false);
        }
        if (GameFrame.a) {
            Epsilon.getINSTANCE().accelerate(Direction.LEFT, true);
        } else {
            Epsilon.getINSTANCE().accelerate(Direction.LEFT, false);
        }
        if (GameFrame.d) {
            Epsilon.getINSTANCE().accelerate(Direction.RIGHT, true);
        } else {
            Epsilon.getINSTANCE().accelerate(Direction.RIGHT, false);
        }
        Epsilon.getINSTANCE().move();
        for (Collectible collectible : Collectible.collectibles) {
            if (collectible.isActive() && Epsilon.getINSTANCE().collisionPoint(collectible) != null) {
                Epsilon.getINSTANCE().eat(collectible);
            }
        }
//        if (Epsilon.getINSTANCE().collisionPoint(GamePanelModel.getINSTANCE()) != null) {
//            impact(Epsilon.getINSTANCE().collisionPoint(GamePanelModel.getINSTANCE()));
//        }
        for (Entity entity : Entity.entities) {
            if (entity instanceof Enemy && ((Enemy) entity).isActive()) {
                ((Enemy) entity).updateAngle(Epsilon.getINSTANCE().getLocation());
                if (entity instanceof Squarantine) ((Squarantine) entity).changeDash();
                ((Enemy) entity).accelerate();
                ((Enemy) entity).move();
                Point2D epsilonCollisionPoint = Epsilon.getINSTANCE().collisionPoint(entity);
                if (epsilonCollisionPoint != null) {
//                    ((Enemy) entity).rotate(true);
                    if (entity instanceof Squarantine) ((Squarantine) entity).setDash(false);
                    impact(Epsilon.getINSTANCE().collisionPoint(entity), false);
//                    if (!(entity).isCoolDown()) {
//                        for (Point2D point2D : ((Enemy) entity).getVertices()) {
//                            if (point2D.equals(Epsilon.getINSTANCE().collisionPoint(entity))) {
//                                Epsilon.getINSTANCE().damage(((Enemy) entity).getPower());
//                                (entity).setCoolDown(true);
//                                (entity).startCoolDownTimer();
//                            }
//                        }
//                    }
                    if (Epsilon.getINSTANCE().containsPoint(epsilonCollisionPoint) && !entity.getVertices().contains(epsilonCollisionPoint) && !Epsilon.getINSTANCE().isCoolDown()) {
                        entity.damage(10);
                        Epsilon.getINSTANCE().setCoolDown(true);
                        Epsilon.getINSTANCE().startCoolDownTimer();
                    } else if (!Epsilon.getINSTANCE().containsPoint(epsilonCollisionPoint) && entity.getVertices().contains(epsilonCollisionPoint) && !entity.isCoolDown()) {
                        Epsilon.getINSTANCE().damage(((Enemy) entity).getPower());
                        entity.setCoolDown(true);
                        entity.startCoolDownTimer();
                    }
                }
                for (Entity entity2 : Entity.entities) {
                    if (entity2 instanceof Enemy && !entity2.getId().equals(entity.getId()) && ((Enemy) entity2).isActive() && entity.collisionPoint(entity2) != null) {
//                        ((Enemy) entity).rotate(true);
//                        ((Enemy) entity2).rotate(true);
                        if (entity instanceof Squarantine) ((Squarantine) entity).setDash(false);
                        impact(entity.collisionPoint(entity2), false);
                    }
                }
                ((Enemy) entity).updateShape();
//                ((Enemy) entity).rotatePolygon(((Enemy) entity).getShape());
//                ((Enemy) entity).accelerateRotation();
            }
        }
        for (Bullet bullet : Bullet.bullets) {
            if (bullet.isActive()) {
                bullet.accelerate();
                bullet.move();
                for (Entity entity : Entity.entities) {
                    if (entity instanceof Enemy && ((Enemy) entity).isActive()) {
                        if (bullet.collisionPoint(entity) != null) {
                            bullet.setActive(false);
                            Controller.findBulletView(bullet.getId()).setActive(false);
                            entity.damage(ability == 1 ? (5 + (Epsilon.getINSTANCE().getAbilityCount() * 2)) : 5);
                            impact(bullet.collisionPoint(entity), false);
                        }
                    }
                }
                if (bullet.collisionPoint(GamePanelModel.getINSTANCE()) != null) {
                    bullet.setActive(false);
                    impact(bullet.collisionPoint(GamePanelModel.getINSTANCE()), false);
                    Controller.findBulletView(bullet.getId()).setActive(false);
                    if (bullet.collisionPoint(GamePanelModel.getINSTANCE()).getY() == 0) {
                        GamePanelModel.getINSTANCE().setExpandUp(true);
                    } else if (bullet.collisionPoint(GamePanelModel.getINSTANCE()).getY() == GamePanelModel.getINSTANCE().getSize().getHeight()) {
                        GamePanelModel.getINSTANCE().setExpandDown(true);
                    } else if (bullet.collisionPoint(GamePanelModel.getINSTANCE()).getX() == 0) {
                        GamePanelModel.getINSTANCE().setExpandLeft(true);
                    } else if (bullet.collisionPoint(GamePanelModel.getINSTANCE()).getX() == GamePanelModel.getINSTANCE().getSize().getWidth()) {
                        GamePanelModel.getINSTANCE().setExpandRight(true);
                    }
                }
            }
        }
        GamePanelModel.getINSTANCE().accelerate();
        GamePanelModel.getINSTANCE().expand();
        if (!inBetweenWaves && !Enemy.enemiesLeft()) {
            wave += 1;
            inBetweenWaves = true;
            waveTimer.restart();
        }
        if (!inBetweenWaves) {
            GamePanelModel.getINSTANCE().shrink();
        }
        upsCount++;
    }


    public static void impact(Point2D point, boolean isBanish) {
        if (!isBanish) {
            for (Entity entity : Entity.entities) {
                if (point.distance(entity.getLocation()) < IMPACT_RADIUS) {
                    double angle = Math.atan2(point.getY() - entity.getLocation().getY(), point.getX() - entity.getLocation().getX());
                    double angle2 = (angle > 0) ? angle - Math.PI : angle + Math.PI;
                    HashMap<String, Double> map = new HashMap<>();
                    map.put("angle", angle2);
                    map.put("count", 50.0);
                    if (entity instanceof Epsilon) {
                        map.put("acceleration", (IMPACT_RADIUS - point.distance(entity.getLocation())) * 0.001);
                    } else {
                        map.put("acceleration", (IMPACT_RADIUS - point.distance(entity.getLocation())) * 0.01);
                    }
                    entity.getImpactAngles().add(map);
                }
            }
        } else {
            for (Entity entity : Entity.entities) {
                if (!(entity instanceof Epsilon) && point.distance(entity.getLocation()) < BANISH_RADIUS) {
                    double angle = Math.atan2(point.getY() - entity.getLocation().getY(), point.getX() - entity.getLocation().getX());
                    double angle2 = (angle > 0) ? angle - Math.PI : angle + Math.PI;
                    HashMap<String, Double> map = new HashMap<>();
                    map.put("angle", angle2);
                    map.put("count", 200.0);
                    map.put("acceleration", (BANISH_RADIUS - point.distance(entity.getLocation())) * 0.1);
                    entity.getImpactAngles().add(map);
                }
            }
        }
    }

    public static void spawnEnemies() {
        for (int i = 0; i < ENEMIES_PER_WAVE * ((double) Update.getDifficulty() / 2) * wave / 2; i++) {
            Point2D sqPoint;
            do {
                sqPoint = new Point2D.Double(Math.random() * GameFrame.getINSTANCE().getWidth(), Math.random() * GameFrame.getINSTANCE().getHeight());
            } while (sqPoint.getX() > GamePanelModel.getINSTANCE().getLocation().getX() && sqPoint.getX() < GamePanelModel.getINSTANCE().getLocation().getX() + GamePanelModel.getINSTANCE().getSize().getWidth() && sqPoint.getY() > GamePanelModel.getINSTANCE().getLocation().getY() && sqPoint.getY() < GamePanelModel.getINSTANCE().getLocation().getY() + GamePanelModel.getINSTANCE().getSize().getHeight());
            Point2D trPoint;
            do {
                trPoint = new Point2D.Double(Math.random() * GameFrame.getINSTANCE().getWidth(), Math.random() * GameFrame.getINSTANCE().getHeight());
            } while (trPoint.getX() > GamePanelModel.getINSTANCE().getLocation().getX() && trPoint.getX() < GamePanelModel.getINSTANCE().getLocation().getX() + GamePanelModel.getINSTANCE().getSize().getWidth() && trPoint.getY() > GamePanelModel.getINSTANCE().getLocation().getY() && trPoint.getY() < GamePanelModel.getINSTANCE().getLocation().getY() + GamePanelModel.getINSTANCE().getSize().getHeight());
            Enemy.spawn(sqPoint, true);
            Enemy.spawn(trPoint, false);
        }
    }

    public static void gameOver(boolean isBack) {
        File file = new File("src/main/java/data/xp.txt");
        try {
            Scanner scanner = new Scanner(file);
            int xp = Integer.parseInt(scanner.next());
            FileWriter fw = new FileWriter(file, false);
            int newXp = xp + Epsilon.getINSTANCE().getXp();
            fw.append(newXp + "");
            fw.flush();
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (!isBack) {
            JOptionPane.showMessageDialog(null, "Collected XP: " + Epsilon.getINSTANCE().getXp(), "Game Over!", JOptionPane.INFORMATION_MESSAGE);
        }
        Epsilon.dispose();
        GamePanelModel.dispose();
        GamePanel.dispose();
        Bullet.bullets = new ArrayList<>();
        BulletView.bulletViews = new ArrayList<>();
        Collectible.collectibles = new ArrayList<>();
        CollectibleView.collectibleViews = new ArrayList<>();
        Entity.entities = new ArrayList<>();
        EpsilonView.entityViews = new ArrayList<>();
        GameFrame.w = false;
        GameFrame.s = false;
        GameFrame.a = false;
        GameFrame.d = false;
        GameFrame.info = false;
        modelTimer.stop();
        viewTimer.stop();
        waveTimer.stop();
        empowerTimer.stop();
        errorTimer.stop();
        abilityCoolDownTimer.stop();
        upsCount = 0;
        fpsCount = 0;
        storeTime = 0;
        storeTime2 = 0;
        tripleShot = false;
        abilityCoolDown = false;
        StartingPanel.getINSTANCE();
        GameFrame.getINSTANCE().repaint();
    }


    public static String getError() {
        return error;
    }

    public static void start() {
        wave = 1;
        inBetweenWaves = true;
        viewTimer = new Timer((int) FRAME_UPDATE_TIME, e -> updateView()) {{
            setCoalesce(true);
        }};
        modelTimer = new Timer((int) MODEL_UPDATE_TIME, e -> updateModel()) {{
            setCoalesce(true);
        }};

        waveTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (inBetweenWaves) {
                    inBetweenWaves = false;
                    spawnEnemies();
                    waveTimer.stop();
                }
            }
        });
        empowerTimer = new Timer(10000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tripleShot = false;
                empowerTimer.stop();
            }
        });
        errorTimer = new Timer(3000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                error = "";
            }
        });
        abilityCoolDownTimer = new Timer(3, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                abilityCoolDown = false;
                abilityCoolDownTimer.stop();
            }
        });
        modelTimer.start();
        viewTimer.start();
        waveTimer.start();
    }

    public static void openStore() {
        StorePanel.isOpen = true;
        modelTimer.stop();
        viewTimer.stop();
        waveTimer.stop();
        empowerTimer.stop();
        errorTimer.stop();
        error = "";
        GamePanel.getINSTANCE().setVisible(false);
        StorePanel.getINSTANCE().setVisible(true);
        GameFrame.getINSTANCE().repaint();
        storeTime = System.currentTimeMillis();
    }

    public static long getStoreTime2() {
        return storeTime2;
    }

    public static void closeStore() {
        StorePanel.isOpen = false;
        modelTimer.start();
        viewTimer.start();
        if (inBetweenWaves) waveTimer.start();
        if (tripleShot) empowerTimer.stop();
        StorePanel.getINSTANCE().setVisible(false);
        GamePanel.getINSTANCE().setVisible(true);
        GameFrame.getINSTANCE().repaint();
        storeTime2 += (System.currentTimeMillis() - storeTime);
        storeTime = 0;
    }

    public static void banish() {
        closeStore();
        if (Epsilon.getINSTANCE().getXp() >= 100) {
            impact(Epsilon.getINSTANCE().getLocation(), true);
            Epsilon.getINSTANCE().setXp(Epsilon.getINSTANCE().getXp() - 100);
        } else {
            changeError("You need 100 XP for that");
        }
    }

    public static void empower() {
        closeStore();
        tripleShot = true;
        empowerTimer.start();
        if (Epsilon.getINSTANCE().getXp() >= 75) {
            Epsilon.getINSTANCE().setXp(Epsilon.getINSTANCE().getXp() - 75);
        } else {
            changeError("You need 75 XP for that");
        }
    }

    public static void heal() {
        closeStore();
        if (Epsilon.getINSTANCE().getXp() >= 50) {
            Epsilon.getINSTANCE().setHp(Math.min(100, Epsilon.getINSTANCE().getHp() + 10));
            Epsilon.getINSTANCE().setXp(Epsilon.getINSTANCE().getXp() - 50);
        } else {
            changeError("You need 50 XP for that");
        }
    }

    public static void activateAbility() {
        if (!abilityCoolDown && Epsilon.getINSTANCE().getXp() >= 100) {
            Epsilon.getINSTANCE().setAbilityCount(Epsilon.getINSTANCE().getAbilityCount() + 1);
            if (ability == 1) {
                Epsilon.getINSTANCE().setXp(Epsilon.getINSTANCE().getXp() - 100);
                abilityCoolDown = true;
                abilityCoolDownTimer.start();
            } else if (ability == 2) {
                if (Epsilon.getINSTANCE().getAbilityCount() == 1) {
                    Epsilon.getINSTANCE().getAcesoTimer().start();
                }
                Epsilon.getINSTANCE().setXp(Epsilon.getINSTANCE().getXp() - 100);
                abilityCoolDown = true;
                abilityCoolDownTimer.start();
            } else if (ability == 3) {
                Epsilon.getINSTANCE().setXp(Epsilon.getINSTANCE().getXp() - 100);
                abilityCoolDown = true;
                abilityCoolDownTimer.start();
            } else {
                changeError("You do not have any skill enabled");
            }
        } else if (abilityCoolDown && Epsilon.getINSTANCE().getXp() >= 100) {
            changeError("You have to wait 5 full minutes before using your skills again");
        } else {
            changeError("You need 100 XP for that");
        }
    }

    public static void changeError(String text) {
        error = text;
        errorTimer.restart();
    }

}
