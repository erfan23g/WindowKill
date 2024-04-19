package controller;

import java.awt.geom.Dimension2D;

public class MyDimension extends Dimension2D {
    public MyDimension () {

    }
    public MyDimension (double width, double height) {
        setSize(width, height);
    }
    private double width = 0, height = 0;
    @Override
    public double getWidth() {
        return width;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public void setSize(double width, double height) {
        this.width = width;
        this.height = height;
    }
}
