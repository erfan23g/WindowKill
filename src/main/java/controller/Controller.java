package controller;

import model.*;
import view.*;

import java.awt.geom.Point2D;

public class Controller {
    public static void createEntityView(String id) {
        if (findEntity(id) != null && findEntity(id) instanceof Epsilon) {
            new EpsilonView(id);
        } else if (findEntity(id) != null && findEntity(id) instanceof Squarantine) {
            new SquarantineView(id);
        } else if (findEntity(id) != null && findEntity(id) instanceof Trigorath) {
            new TrigorathView(id);
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
    public static void createCollectibleView (String id) {
        Collectible collectible = findCollectible(id);
        new CollectibleView(id, collectible.getColor());
    }
    public static Collectible findCollectible (String id) {
        for (Collectible collectible : Collectible.collectibles) {
            if (collectible.getId().equals(id)) {
                return collectible;
            }
        }
        return null;
    }
    public static Point2D findCollectibleLocation(String id) {
        Collectible collectible = findCollectible(id);
        assert collectible != null;
        return collectible.getLocation();
    }
    public static void deactivateCollectibleView (String id) {
        for (CollectibleView collectibleView : CollectibleView.collectibleViews) {
            if (collectibleView.getId().equals(id)) collectibleView.setActive(false);
        }
    }
}
