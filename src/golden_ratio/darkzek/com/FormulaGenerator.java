package golden_ratio.darkzek.com;

import java.util.ArrayList;

/**
 * Formula Generator is a Builder class that takes in variables and then outputs a list of Points.
 * It also interacts with the FormulaInterpolator to get interpolated between for smooth effects.
 */

public class FormulaGenerator {

    private double distancePerRotation = 0.0;
    private double rotationPerStep = 0.0;
    private int steps = 0;

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

        for (int i = 0; i < steps; i++) {

            double distanceFromCenter = i * distancePerRotation;

            double pointX = (Math.cos(currentAngle) * distanceFromCenter) + centerX;
            double pointY = (Math.sin(currentAngle) * distanceFromCenter) + centerY;

            points.add(new Point(pointX, pointY));

            // Add current angle but reset at 360
            currentAngle += rotationPerStep;
            currentAngle %= 360;
        }

        Point[] pointsOut = new Point[points.size()];
        pointsOut = points.toArray(pointsOut);


        return pointsOut;
    }

    public double getDistancePerRotation() {
        return distancePerRotation;
    }

    public void setDistancePerRotation(double distancePerRotation) {
        this.distancePerRotation = distancePerRotation;
    }

    public double getRotationPerStep() {
        return rotationPerStep;
    }

    public void setRotationPerStep(double rotationPerStep) {
        this.rotationPerStep = rotationPerStep;
    }

    public int getSteps() {
        return steps;
    }

    public void setSteps(int steps) {
        this.steps = steps;
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
}
