package view;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import static controller.Constants.EPSILON_RADIUS;

public class EpsilonView extends EntityView {

    public EpsilonView(String id) {
        super(id);
        setRadius(EPSILON_RADIUS, EPSILON_RADIUS);
    }

    public ArrayList<Point2D> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Point2D> vertices) {
        this.vertices = vertices;
    }

    private ArrayList<Point2D> vertices = new ArrayList<>();


    @Override
    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.drawOval((int) (getLocation().getX() - radius1),
                (int) (getLocation().getY() - radius2),
                (int) (radius1 * 2),
                (int) (radius2 * 2));
        g.setColor(Color.white);
        for (Point2D point : vertices) {
            g.fillOval((int) point.getX() - 2, (int) point.getY() - 2, 4, 4);
        }
    }

}
