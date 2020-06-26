package golden_ratio.darkzek.com;

import javafx.scene.paint.Color;

public class Helper {
    public static Color lerpColor(Color color1, Color color2, double i) {
        double r, g, b, a = 0.0;

        r = (color1.getRed() * i) + (color2.getRed() * ( 1 - i));
        g = (color1.getGreen() * i) + (color2.getGreen() * ( 1 - i));
        b = (color1.getBlue() * i) + (color2.getBlue() * ( 1 - i));
        a = (color1.getOpacity() * i) + (color2.getOpacity() * ( 1 - i));

        return new Color(r, g, b, a);
    }

    public static double clampToPositive(double n) {
        if (n < 0) {
            return 0;
        } else {
            return n;
        }
    }

    public static double lerp(double a, double b, double f, double min_change)
    {
        if (Math.abs(a - b) < min_change) { return b; }
        return a + f * (b - a);
    }

    public static int lerpInt(int a, int b, double f)
    {
        return ((int) Math.ceil(a + f)) * (b - a);
    }
}
