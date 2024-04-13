package controller;

import java.awt.geom.Point2D;

public class Utils {
    public static Point2D relativeLocation(Point2D point, Point2D anchor){
        return new Point2D.Double(point.getX()-anchor.getX(),point.getY()-anchor.getY());
    }
}
