package golden_ratio.darkzek.com;

import javafx.scene.paint.Color;

public class Helper {
    public static Color lerpColor(Color color1, Color color2, double i) {
        double r, g, b, a = 0;

        r = (color1.getRed() * i) + (color2.getRed() * ( 1 - i));
        g = (color1.getGreen() * i) + (color2.getGreen() * ( 1 - i));
        b = (color1.getBlue() * i) + (color2.getBlue() * ( 1 - i));
        a = (color1.getOpacity() * i) + (color2.getOpacity() * ( 1 - i));

//        r = Math.min(color1.getRed(), color2.getRed()) + (Math.abs(color1.getRed() - color2.getRed()) * i);
//        g = Math.min(color1.getGreen(), color2.getGreen()) + (Math.abs(color1.getGreen() - color2.getGreen()) * i);
//        b = Math.min(color1.getBlue(), color2.getBlue()) + (Math.abs(color1.getBlue() - color2.getBlue()) * i);
//        a = Math.min(color1.getOpacity(), color2.getOpacity()) + (Math.abs(color1.getOpacity() - color2.getOpacity()) * i);

        return new Color(r, g, b, a);
    }
}
