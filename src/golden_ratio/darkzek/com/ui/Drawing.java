package golden_ratio.darkzek.com.ui;

import golden_ratio.darkzek.com.Settings;
import golden_ratio.darkzek.com.formula.FormulaGenerator;
import golden_ratio.darkzek.com.formula.Point;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Drawing {

    private Canvas canvas;
    private GraphicsContext context;

    private FormulaGenerator formulaGenerator;

    private Point[] points;

    /**
     * Creates a new drawing object
     *
     * @param canvas The canvas to draw on
     * @param settings The settings to draw
     */
    public Drawing(Canvas canvas, Settings settings) {
        this.canvas = canvas;
        context = canvas.getGraphicsContext2D();

        this.setupFormula(settings);
    }

    /**
     * To be replaced with a drawing class soon
     *
     * @param context The context on which to draw
     */
    public void drawPoints(GraphicsContext context) {

        context.clearRect(0, 0, canvas.getWidth() - 1, canvas.getHeight() - 1);

        for (Point point : points) {
            context.setFill(point.color);
            context.fillRoundRect(
                    point.x - (point.size / 2),
                    point.y - (point.size / 2),
                    point.size,
                    point.size,
                    point.size,
                    point.size);
        }
    }

    /**
     * Sets up the formula with the inputted settings
     *
     * @param settings The settings that the points generator should use
     */
    private void setupFormula(Settings settings) {
        formulaGenerator = new FormulaGenerator();

        formulaGenerator.setCenter(canvas.getWidth() / 2.0, canvas.getHeight() / 2.0);

        formulaGenerator.setSettings(settings);
    }

    /**
     * Updates the canvas with the newest applied settings and recalculates points with those
     * settings. Then it draws those new points to the screen
     */
    public void updateCanvas() {
        points = formulaGenerator.calculatePoints();

        drawPoints(context);
    }

    /** Detects resize events and updates the other classes accordingly. */
    public void resized(double width, double height) {
        formulaGenerator.setCenter(width / 2.0, height / 2.0);

        canvas.setWidth(width);
        canvas.setHeight(height);

        updateCanvas();
    }
}
