package view;

import javax.swing.*;
import java.awt.*;


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

    @Override
    public void draw(Graphics g) {
        if (isActive) {
            g.setColor(Color.green);
            g.fillPolygon(getShape());
            Font font = new Font("Calibri", Font.PLAIN, 8);
            g.setFont(font);
            FontMetrics fm = g.getFontMetrics();
            double stringWidth = SwingUtilities.computeStringWidth(fm, (getHp() + ""));
            int centerX = (int) (getLocation().getX() - stringWidth / 2);
            int centerY = (int) (getLocation().getY() + fm.getHeight() / 4);
            g.setColor(Color.black);
            g.drawString(getHp() + "", centerX, centerY);
        }
    }
}
