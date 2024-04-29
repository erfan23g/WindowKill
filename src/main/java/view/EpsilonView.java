package view;

import java.awt.*;

import static controller.Constants.EPSILON_RADIUS;

public class EpsilonView extends EntityView {

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
    }

}
