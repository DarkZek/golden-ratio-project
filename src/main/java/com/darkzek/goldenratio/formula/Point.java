package com.darkzek.goldenratio.formula;

import javafx.scene.paint.Color;

/** A point on the graph */
public class Point {
    public double x = 0;
    public double y = 0;
    public double size = 10;
    public Color color = Color.BLACK;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y, double size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public Point(double x, double y, double size, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }
}
