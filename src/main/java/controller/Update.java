package controller;

import model.*;
import model.movement.Direction;
import view.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static controller.Constants.*;
import static controller.Utils.relativeLocation;

public class Update {
    public static int upsCount = 0, fpsCount = 0;

    public Update() {
        new Timer((int) FRAME_UPDATE_TIME, e -> updateView()) {{
            setCoalesce(true);
        }}.start();
        new Timer((int) MODEL_UPDATE_TIME, e -> updateModel()) {{
            setCoalesce(true);
        }}.start();
    }

    public void updateView() {
        for (EntityView entityView : EntityView.entityViews) {
            entityView.setLocation(relativeLocation(Controller.findEntityLocation(entityView.getId()), GamePanelModel.getINSTANCE().getLocation()));
            if (entityView instanceof SquarantineView && ((SquarantineView) entityView).isActive()) {
                Enemy entityModel = (Enemy) Controller.findEntity(entityView.getId());
                if (!entityModel.isActive()) {
                    ((SquarantineView) entityView).setActive(false);
                    continue;
                }
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
            } else if (entityView instanceof TrigorathView && ((TrigorathView) entityView).isActive()) {
                Enemy entityModel = (Enemy) Controller.findEntity(entityView.getId());
                if (!entityModel.isActive()) {
                    ((TrigorathView) entityView).setActive(false);
                    continue;
                }
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

    public void updateModel() {
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
                ((Enemy) entity).accelerate();
                ((Enemy) entity).move();
                if (Epsilon.getINSTANCE().collisionPoint(entity) != null) {
//                    ((Enemy) entity).rotate(true);
                    impact(Epsilon.getINSTANCE().collisionPoint(entity));
                    for (Point2D point2D : ((Enemy) entity).getVertices()) {
                        if (point2D.equals(Epsilon.getINSTANCE().collisionPoint(entity))) {
                            Epsilon.getINSTANCE().damage(((Enemy) entity).getPower());
                        }
                    }
                }
                for (Entity entity2 : Entity.entities) {
                    if (entity2 instanceof Enemy && !entity2.getId().equals(entity.getId()) && ((Enemy) entity2).isActive() && entity.collisionPoint(entity2) != null) {
//                        ((Enemy) entity).rotate(true);
//                        ((Enemy) entity2).rotate(true);
                        impact(entity.collisionPoint(entity2));
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
                            entity.damage(5);
                            impact(bullet.collisionPoint(entity));
                        }
                    }
                }
                if (bullet.collisionPoint(GamePanelModel.getINSTANCE()) != null) {
                    bullet.setActive(false);
                    impact(bullet.collisionPoint(GamePanelModel.getINSTANCE()));
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
        GamePanelModel.getINSTANCE().shrink();
        upsCount++;
    }


    public void impact(Point2D point) {
        for (Entity entity : Entity.entities) {
//            System.out.println(entity.getLocation());
//            System.out.println(point.distance(entity.getLocation()));
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
    }
}
