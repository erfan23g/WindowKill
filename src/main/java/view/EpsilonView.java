package view;

import java.awt.*;

import static controller.Constants.EPSILON_RADIUS;

public class EpsilonView extends EntityView{
    double radius = EPSILON_RADIUS;
    public EpsilonView(String id) {
        super(id);
    }

}
