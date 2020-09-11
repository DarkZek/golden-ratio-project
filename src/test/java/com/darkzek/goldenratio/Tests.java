package com.darkzek.goldenratio;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Tests {

    @org.junit.jupiter.api.Test
    void lerpColor() {
        Color colour = Helper.lerpColor(Color.web("#ff0000"), Color.web("#00ff00"), 0.6);
        Color expected = Color.web("#996600");

        assertEquals(Helper.round(expected.getRed(), 4), Helper.round(colour.getRed(), 4));
        assertEquals(Helper.round(expected.getGreen(), 4), Helper.round(colour.getGreen(), 4));
        assertEquals(Helper.round(expected.getBlue(), 4), Helper.round(colour.getBlue(), 4));
        assertEquals(Helper.round(expected.getOpacity(), 4), Helper.round(colour.getOpacity(), 4));
    }

    @org.junit.jupiter.api.Test
    void clampToPositive() {
        assertEquals(Helper.clampToPositive(-1), 0);
        assertEquals(Helper.clampToPositive(104), 104);
    }

    @org.junit.jupiter.api.Test
    void clamp() {
        assertEquals(Helper.clamp(-5, -4, 4), -4);
        assertEquals(Helper.clamp(5, 4, 10), 5);
        assertEquals(Helper.clamp(1.4, 1, 1.3), 1.3);
    }

    @org.junit.jupiter.api.Test
    void clampInt() {
        assertEquals(Helper.clampInt(-5, -4, 4), -4);
        assertEquals(Helper.clampInt(5, 4, 10), 5);
        assertEquals(Helper.clampInt(4, 1, 2), 2);
    }

    @org.junit.jupiter.api.Test
    void lerp() {
        assertEquals(Helper.lerp(5.5, 10.45, 0.5, 0), 7.975);
        assertEquals(Helper.lerp(-4, 9, 0.5, 0), 2.5);
        assertEquals(Helper.lerp(0, 10, 0.75, 0), 7.5);
    }

    @Test
    void round() {
        double in = 3.14159265359;

        double round = Helper.round(in, 2);

        assertEquals(3.14, round);

        round = Helper.round(in, 5);

        assertEquals(3.14159, round);
    }
}