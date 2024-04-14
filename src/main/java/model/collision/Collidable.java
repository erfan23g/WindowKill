package model.collision;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public interface Collidable {
    ArrayList<Collidable> collidables=new ArrayList<>();
    default boolean collidesWith (Collidable collidable) {
        return collisionPoint(collidable) != null;
    }
    default Point2D collisionPoint (Collidable collidable) {
        return null;
    }
}
