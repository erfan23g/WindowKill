package view;

import java.awt.*;

import static controller.Constants.EPSILON_RADIUS;

public class EpsilonView extends EntityView{
    double radius = EPSILON_RADIUS;
    public EpsilonView(String id) {
        super(id);
    }

    @Override
    public void paint(Graphics g) {
        g.setColor(Color.red);
        g.drawOval((int) getLocation().getX(), (int) getLocation().getY(), (int) radius, (int) radius);
        g.fillOval((int) getLocation().getX(), (int) getLocation().getY(), (int) radius, (int) radius);
    }
}
