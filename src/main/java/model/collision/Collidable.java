package model.collision;

import controller.Utils;
import model.*;
import controller.Utils.*;

import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import static controller.Constants.*;

public interface Collidable {
    ArrayList<Collidable> collidables=new ArrayList<>();

    default Point2D collisionPoint (Collidable collidable) {
        if (this instanceof Bullet && collidable instanceof GamePanelModel) {
            Line2D up, down, left, right;
            up = new Line2D.Double(new Point2D.Double(0, 0), new Point2D.Double (((GamePanelModel) collidable).getSize().getWidth(), 0));
            down = new Line2D.Double(new Point2D.Double(0, ((GamePanelModel) collidable).getSize().getHeight()), new Point2D.Double (((GamePanelModel) collidable).getSize().getWidth(), ((GamePanelModel) collidable).getSize().getHeight()));
            left = new Line2D.Double(new Point2D.Double(0, 0), new Point2D.Double (0, ((GamePanelModel) collidable).getSize().getHeight()));
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
        } else if (this instanceof Epsilon && collidable instanceof GamePanelModel) {
            Line2D up, down, left, right;
            up = new Line2D.Double(new Point2D.Double(0, 0), new Point2D.Double (((GamePanelModel) collidable).getSize().getWidth(), 0));
            down = new Line2D.Double(new Point2D.Double(0, ((GamePanelModel) collidable).getSize().getHeight()), new Point2D.Double (((GamePanelModel) collidable).getSize().getWidth(), ((GamePanelModel) collidable).getSize().getHeight()));
            left = new Line2D.Double(new Point2D.Double(0, 0), new Point2D.Double (0, ((GamePanelModel) collidable).getSize().getHeight()));
            right = new Line2D.Double(new Point2D.Double(((GamePanelModel) collidable).getSize().getWidth(), 0), new Point2D.Double (((GamePanelModel) collidable).getSize().getWidth(), ((GamePanelModel) collidable).getSize().getHeight()));
            double upDistance = up.ptLineDist(Utils.relativeLocation(((Epsilon) this).getLocation(), GamePanelModel.getINSTANCE().getLocation()));
            double downDistance = down.ptLineDist(Utils.relativeLocation(((Epsilon) this).getLocation(), GamePanelModel.getINSTANCE().getLocation()));
            double leftDistance = left.ptLineDist(Utils.relativeLocation(((Epsilon) this).getLocation(), GamePanelModel.getINSTANCE().getLocation()));
            double rightDistance = right.ptLineDist(Utils.relativeLocation(((Epsilon) this).getLocation(), GamePanelModel.getINSTANCE().getLocation()));
            double radius = EPSILON_RADIUS;
            if (upDistance <= radius) {
                return new Point2D.Double(((Epsilon) this).getLocation().getX(), ((Epsilon) this).getLocation().getY() - radius);
            } else if (downDistance <= radius) {
                return new Point2D.Double(((Epsilon) this).getLocation().getX(), ((Epsilon) this).getLocation().getY() + radius);
            } else if (leftDistance <= radius) {
                return new Point2D.Double(((Epsilon) this).getLocation().getX() - radius, ((Epsilon) this).getLocation().getY());
            } else if (rightDistance <= radius) {
                return new Point2D.Double(((Epsilon) this).getLocation().getX() + radius, ((Epsilon) this).getLocation().getY());
            } else {
                return null;
            }
        } else if (this instanceof Bullet && collidable instanceof Enemy) {
            Polygon shape = ((Enemy) collidable).getShape();
            Point2D closest = closestPointOnPolygon(((Bullet) this).getLocation(), shape);
            if (closest.distance(((Bullet) this).getLocation()) <= BULLET_RADIUS) {
                return closest;
            }
            return null;
        } else if (this instanceof Epsilon && collidable instanceof Enemy) {
            Polygon shape = ((Enemy) collidable).getShape();
            Point2D closest = closestPointOnPolygon(((Epsilon) this).getLocation(), shape);
            if (closest.distance(((Epsilon) this).getLocation()) <= EPSILON_RADIUS) {
                return closest;
            }
            return null;
        } else if (this instanceof Enemy && collidable instanceof Enemy) {
            Polygon shape = ((Enemy) this).getShape();
            ArrayList<Point2D> vertices = ((Enemy) collidable).getVertices();
            for (Point2D point : vertices) {
                if (shape.contains(point)) return point;
            }
            return null;
        } else if (this instanceof Epsilon && collidable instanceof Collectible) {
            double distance = ((Epsilon) this).getLocation().distance(((Collectible) collidable).getLocation());
            if (distance <= EPSILON_RADIUS + COLLECTIBLE_RADIUS) {
                double x = (((Epsilon) this).getLocation().getX() + ((Collectible) collidable).getLocation().getX()) / 2;
                double y = (((Epsilon) this).getLocation().getY() + ((Collectible) collidable).getLocation().getY()) / 2;
                return new Point2D.Double(x, y);
            }
            return null;
        }
        return null;
    }
    default Point2D closestPointOnPolygon(Point2D point, Polygon polygon) {
        ArrayList<Point2D> vertices = new ArrayList<>();
        for (int i = 0; i < polygon.npoints; i++) {
            vertices.add(new Point2D.Double(polygon.xpoints[i], polygon.ypoints[i]));
        }
        double minDistance = Double.MAX_VALUE;
        Point2D closest = null;
        for (int i = 0; i < vertices.size(); i++) {
            Point2D temp = getClosestPointOnSegment(vertices.get(i), vertices.get((i + 1) % vertices.size()), point);
            double distance = temp.distance(point);
            if (distance < minDistance) {
                minDistance = distance;
                closest = temp;
            }
        }
        return closest;
    }

    default Point2D getClosestPointOnSegment(Point2D head1, Point2D head2, Point2D point) {
        double u = ((point.getX() - head1.getX()) * (head2.getX() - head1.getX()) + (point.getY() - head1.getY()) * (head2.getY() - head1.getY())) / head2.distanceSq(head1);
        if (u > 1.0) return (Point2D) head2.clone();
        else if (u <= 0.0) return (Point2D) head1.clone();
        else
            return new Point2D.Double(head2.getX() * u + head1.getX() * (1.0 - u) + 0.5, head2.getY() * u + head1.getY() * (1.0 - u) + 0.5);
    }
}
