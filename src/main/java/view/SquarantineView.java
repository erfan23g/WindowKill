package view;

import java.awt.*;

import static controller.Constants.ENEMY_SIDE_LENGTH;

public class SquarantineView extends EntityView {

    private Polygon shape = new Polygon();

    public Polygon getShape() {
        return shape;
    }

    public void setShape(Polygon shape) {
        this.shape = shape;
    }

    public SquarantineView(String id) {
        super(id);
    }
}
