package golden_ratio.darkzek.com;

import javafx.scene.paint.Color;

public class Point {
    double x = 0;
    double y = 0;
    double size = 10;
    Color color = Color.BLACK;

    public Point (double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point (double x, double y, double size) {
        this.x = x;
        this.y = y;
        this.size = size;
    }

    public Point (double x, double y, double size, Color color) {
        this.x = x;
        this.y = y;
        this.size = size;
        this.color = color;
    }
}
