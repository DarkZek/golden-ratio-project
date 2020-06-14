package golden_ratio.darkzek.com.formula;

import golden_ratio.darkzek.com.Helper;
import golden_ratio.darkzek.com.Settings;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Formula Generator is a Builder class that takes in variables and then outputs a list of Points.
 * It also interacts with the FormulaInterpolator to get interpolated between for smooth effects.
 */

public class FormulaGenerator {

    private Settings settings;

    private double centerX = 0.0;
    private double centerY = 0.0;

    public FormulaGenerator() {}

    /**
     * Calculate points takes in all of the variables that have been set in the Formula Generator and calculates a list of points using the formula.
     * @return
     */

    public Point[] calculatePoints() {

        ArrayList<Point> points = new ArrayList<Point>();

        double currentAngle = 0;
        double currentSize = settings.defaultSize;

        for (int i = 0; i < settings.points; i++) {

            double distanceFromCenter = i * settings.distancePerRotation;

            double pointX = (Math.cos(currentAngle) * distanceFromCenter) + centerX;
            double pointY = (Math.sin(currentAngle) * distanceFromCenter) + centerY;

            Color color = Helper.lerpColor(settings.startColor, settings.endColor, ((double) i ) / settings.points);

            points.add(new Point(pointX, pointY, currentSize, color));

            // Add current angle but reset at 360
            currentAngle += settings.rotationPerPoint;
            currentAngle %= 360;

            currentSize += settings.sizeIncreasePerPoint;
        }

        Point[] pointsOut = new Point[points.size()];
        pointsOut = points.toArray(pointsOut);


        return pointsOut;
    }

    public double getCenterX() {
        return centerX;
    }

    public void setCenterX(double centerX) {
        this.centerX = centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public void setCenterY(double centerY) {
        this.centerY = centerY;
    }

    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}