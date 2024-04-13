package controller;

import model.Entity;
import model.Epsilon;
import view.EntityView;
import view.EpsilonView;

import java.awt.geom.Point2D;

public class Controller {
    public static void createEntityView(String id) {
        if (findEntity(id) != null && findEntity(id) instanceof Epsilon) {
            new EpsilonView(id);
        }
    }
    public static Entity findEntity (String id) {
        for (Entity entity : Entity.entities) {
            if (entity.getId().equals(id)) {
                return entity;
            }
        }
        return null;
    }
    public static Point2D findEntityLocation (String id) {
        Entity entity = findEntity(id);
        assert entity != null;
        return entity.getLocation();
    }
}
