package model.collision;

import controller.Utils;
import model.Bullet;
import model.Enemy;
import model.Epsilon;
import model.GamePanelModel;
import controller.Utils.*;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import static controller.Constants.BULLET_RADIUS;

public interface Collidable {
    ArrayList<Collidable> collidables=new ArrayList<>();

    default Point2D collisionPoint (Collidable collidable) {
        if (this instanceof Bullet && collidable instanceof GamePanelModel) {
            Line2D up, down, left, right;
//            up = new Line2D.Double(((GamePanelModel) collidable).getLocation(), new Point2D.Double (((GamePanelModel) collidable).getLocation().getX() + ((GamePanelModel) collidable).getSize().getWidth(), ((GamePanelModel) collidable).getLocation().getY()));
            up = new Line2D.Double(new Point2D.Double(0, 0), new Point2D.Double (((GamePanelModel) collidable).getSize().getWidth(), 0));
//            down = new Line2D.Double((new Point2D.Double(((GamePanelModel) collidable).getLocation().getX(), ((GamePanelModel) collidable).getLocation().getY() + ((GamePanelModel) collidable).getSize().getHeight())), new Point2D.Double (((GamePanelModel) collidable).getLocation().getX() + ((GamePanelModel) collidable).getSize().getWidth(), ((GamePanelModel) collidable).getLocation().getY() + ((GamePanelModel) collidable).getSize().getHeight()));
            down = new Line2D.Double(new Point2D.Double(0, ((GamePanelModel) collidable).getSize().getHeight()), new Point2D.Double (((GamePanelModel) collidable).getSize().getWidth(), ((GamePanelModel) collidable).getSize().getHeight()));
//            left = new Line2D.Double(((GamePanelModel) collidable).getLocation(), new Point2D.Double (((GamePanelModel) collidable).getLocation().getX(), ((GamePanelModel) collidable).getLocation().getY() + ((GamePanelModel) collidable).getSize().getHeight()));
            left = new Line2D.Double(new Point2D.Double(0, 0), new Point2D.Double (0, ((GamePanelModel) collidable).getSize().getHeight()));
//            right = new Line2D.Double((new Point2D.Double(((GamePanelModel) collidable).getLocation().getX() + ((GamePanelModel) collidable).getSize().getWidth(), ((GamePanelModel) collidable).getLocation().getY())), new Point2D.Double (((GamePanelModel) collidable).getLocation().getX() + ((GamePanelModel) collidable).getSize().getWidth(), ((GamePanelModel) collidable).getLocation().getY() + ((GamePanelModel) collidable).getSize().getHeight()));
            right = new Line2D.Double(new Point2D.Double(((GamePanelModel) collidable).getSize().getWidth(), 0), new Point2D.Double (((GamePanelModel) collidable).getSize().getWidth(), ((GamePanelModel) collidable).getSize().getHeight()));
            double upDistance = up.ptLineDist(Utils.relativeLocation(((Bullet) this).getLocation(), GamePanelModel.getINSTANCE().getLocation()));
            double downDistance = down.ptLineDist(Utils.relativeLocation(((Bullet) this).getLocation(), GamePanelModel.getINSTANCE().getLocation()));
            double leftDistance = left.ptLineDist(Utils.relativeLocation(((Bullet) this).getLocation(), GamePanelModel.getINSTANCE().getLocation()));
            double rightDistance = right.ptLineDist(Utils.relativeLocation(((Bullet) this).getLocation(), GamePanelModel.getINSTANCE().getLocation()));
            double radius = BULLET_RADIUS;
            if (upDistance <= radius) {
                return new Point2D.Double(((Bullet) this).getLocation().getX(), 0);
            } else if (downDistance <= radius) {
                return new Point2D.Double(((Bullet) this).getLocation().getX(), ((GamePanelModel) collidable).getSize().getHeight());
            } else if (leftDistance <= radius) {
                return new Point2D.Double(0, ((Bullet) this).getLocation().getY());
            } else if (rightDistance <= radius) {
                return new Point2D.Double(((GamePanelModel) collidable).getSize().getWidth(), ((Bullet) this).getLocation().getY());
            } else {
                return null;
            }
        } else if (this instanceof Bullet && collidable instanceof Enemy) {
            Polygon shape = ((Enemy) collidable).getShape();
            if (shape.contains(((Bullet) this).getLocation())) {
                return ((Bullet) this).getLocation();
            }
            return null;
        }
        return null;
    }
}
