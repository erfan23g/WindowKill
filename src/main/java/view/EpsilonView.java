package view;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import static controller.Constants.EPSILON_RADIUS;

public class EpsilonView extends EntityView {
    public ArrayList<Point2D> getVertices() {
        return vertices;
    }

    public void setVertices(ArrayList<Point2D> vertices) {
        this.vertices = vertices;
    }

    private ArrayList<Point2D> vertices = new ArrayList<>();

    public EpsilonView(String id) {
        super(id);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(Color.red);
        g.drawOval((int) (getLocation().getX() - EPSILON_RADIUS),
                (int) (getLocation().getY() - EPSILON_RADIUS),
                (int) (EPSILON_RADIUS * 2),
                (int) (EPSILON_RADIUS * 2));
        g.setColor(Color.white);
        for (Point2D point : vertices) {
            g.fillOval((int) point.getX() - 2, (int) point.getY() - 2, 4, 4);
        }
    }

}
