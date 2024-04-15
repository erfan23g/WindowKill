package controller;

import model.Bullet;
import model.Entity;
import model.Epsilon;
import model.GamePanelModel;
import view.BulletView;
import view.EntityView;
import view.EpsilonView;

import java.awt.geom.Point2D;

public class Controller {
    public static void createEntityView(String id) {
        if (findEntity(id) != null && findEntity(id) instanceof Epsilon) {
            new EpsilonView(id);
        }
    }

    public static Entity findEntity(String id) {
        for (Entity entity : Entity.entities) {
            if (entity.getId().equals(id)) {
                return entity;
            }
        }
        return null;
    }

    public static Point2D findEntityLocation(String id) {
        Entity entity = findEntity(id);
        assert entity != null;
        return entity.getLocation();
    }

    public static void createBulletView(String id) {
        new BulletView(id);
    }

    public static Bullet findBullet(String id) {
        for (Bullet bullet : Bullet.bullets) {
            if (bullet.getId().equals(id)) {
                return bullet;
            }
        }
        return null;
    }
    public static BulletView findBulletView(String id) {
        for (BulletView bulletView : BulletView.bulletViews) {
            if (bulletView.getId().equals(id)) {
                return bulletView;
            }
        }
        return null;
    }
    public static Point2D findBulletLocation (String id) {
        Bullet bullet = findBullet(id);
        assert bullet != null;
        return bullet.getLocation();
    }
    public static void fireBullet(Point2D point){
        double angle = Math.atan2(point.getY() - Utils.relativeLocation(Epsilon.getINSTANCE().getLocation(), GamePanelModel.getINSTANCE().getLocation()).getY(), point.getX() - Utils.relativeLocation(Epsilon.getINSTANCE().getLocation(), GamePanelModel.getINSTANCE().getLocation()).getX());
        new Bullet(Epsilon.getINSTANCE().getLocation(), angle);
    }
}
