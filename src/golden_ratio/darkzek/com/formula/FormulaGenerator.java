package golden_ratio.darkzek.com.formula;

import golden_ratio.darkzek.com.Helper;
import golden_ratio.darkzek.com.Settings;
import javafx.scene.paint.Color;

import java.util.ArrayList;

/**
 * Formula Generator is a class that takes in a set of Settings and then outputs a list of Points.
 * It also gets interacted by the FormulaInterpolator which interpolates between these applied settings and some target settings to achieve smooth transitions.
 */
public class FormulaGenerator {

    private Settings settings;

    private double centerX = 0.0;
    private double centerY = 0.0;

    public FormulaGenerator() {}

    /**
     * Calculate points takes in all of the variables that have been set in the Formula Generator and calculates a list of points using the formula.
     * @return A list of points calculated by the Settings variable
     */

    public Point[] calculatePoints() {

        ArrayList<Point> points = new ArrayList();

        double currentAngle = 0;
        double currentSize = settings.defaultSize;

        for (int i = 0; i < settings.points; i++) {

            double distanceFromCenter = i * settings.distancePerRotation;

            double pointX = (Math.cos(currentAngle) * distanceFromCenter) + centerX;
            double pointY = (Math.sin(currentAngle) * distanceFromCenter) + centerY;

            pointY = (centerY * 2) - pointY;

            Color color = Helper.lerpColor(settings.startColor, settings.endColor, ((double) i ) / settings.points);

            points.add(new Point(pointX, pointY, currentSize, color));

            // Add current angle but reset at 360
            if (settings.rotationType == RotationType.Radians) {
                currentAngle += (Math.PI * 2) * settings.rotationPerPoint.getValue();
            } else {
                // Convert degrees to radians
                double radians = (Math.PI * 2) * (settings.rotationPerPoint.getValue() * (Math.PI / 180));
                currentAngle += radians;
            }
            currentAngle %= Math.PI * 2;

            currentSize += settings.sizeIncreasePerPoint;
        }

        Point[] pointsOut = new Point[points.size()];
        pointsOut = points.toArray(pointsOut);

        return pointsOut;
    }

    /**
     * Sets the position of the center of the canvas
     * @param centerX The X position of the center
     * @param centerY The y position of the center.
     */
    public void setCenter(double centerX, double centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
    }

    /**
     * Simple setter for the settings we should use
     * @param settings
     */
    public void setSettings(Settings settings) {
        this.settings = settings;
    }
}
