package com.darkzek.goldenratio;

import javafx.scene.paint.Color;

/** Simple static helper functions can be used anywhere they're needed */
public class Helper {
    /**
     * Lerps between color1 and color2 by i amount
     *
     * @param color1 First colour to be interpolated
     * @param color2 Second colour to be interpolated
     * @param i Amount to be interpolated by
     * @return The interpolated color
     */
    public static Color lerpColor(Color color1, Color color2, double i) {
        double r, g, b, a;

        r = (color1.getRed() * i) + (color2.getRed() * (1 - i));
        g = (color1.getGreen() * i) + (color2.getGreen() * (1 - i));
        b = (color1.getBlue() * i) + (color2.getBlue() * (1 - i));
        a = (color1.getOpacity() * i) + (color2.getOpacity() * (1 - i));

        return new Color(r, g, b, a);
    }

    /**
     * If n is under 0, return 0; Otherwise return n
     *
     * @param n
     * @return Output
     */
    public static double clampToPositive(double n) {
        if (n < 0) {
            return 0;
        } else {
            return n;
        }
    }

    /**
     * Clamps a double between two other values
     *
     * @param n The value to be clamped
     * @param floor The minimum value n may be
     * @param ceil The maximum value n may be
     * @return n between floor and ceil
     */
    public static double clamp(double n, double floor, double ceil) {
        if (n < floor) {
            return floor;
        } else return Math.min(n, ceil);
    }

    /**
     * Clamps an int between two other values
     *
     * @param n The value to be clamped
     * @param floor The minimum value n may be
     * @param ceil The maximum value n may be
     * @return n between floor and ceil
     */
    public static int clampInt(int n, int floor, int ceil) {
        if (n < floor) {
            return floor;
        } else return Math.min(n, ceil);
    }

    /**
     * Interpolates between two numbers, with a minimum change amount that if it doesnt exceed, we
     * can just return b.
     *
     * @param a The first number
     * @param b The second number
     * @param f The amount to interpolate between them
     * @param min_change The minimum amount of change to interpolate by
     * @return The interpolated value
     */
    public static double lerp(double a, double b, double f, double min_change) {
        if (Math.abs(a - b) < min_change) {
            return b;
        }
        return a + f * (b - a);
    }

    /** Rounds a double to decimalPlaces places
     * @param input Input number
     * @param decimalPlaces Amount of decimal places
     * @return The output value
     */
    public static double round(double input, int decimalPlaces) {
        return Math.round(input * Math.pow(10, decimalPlaces)) / Math.pow(10, decimalPlaces);
    }
}
