package view;

import java.awt.*;

import static controller.Constants.ENEMY_SIDE_LENGTH;

public class SquarantineView extends EntityView {
    private boolean isActive = true;
    private int hp = 10;

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

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
