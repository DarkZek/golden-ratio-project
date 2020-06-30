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

    public Drawing(Canvas canvas, Settings settings) {
        this.canvas = canvas;
        context = canvas.getGraphicsContext2D();

        this.setupFormula(settings);
    }

    /**
     * To be replaced with a drawing class soon
     * @param context The context on which to draw
     */

    public void drawPoints(GraphicsContext context) {

        context.clearRect(0, 0, canvas.getWidth() - 1, canvas.getHeight() - 1);

        for (Point point : points) {
            context.setFill(point.color);
            context.fillRoundRect(point.x - (point.size / 2), point.y - (point.size / 2), point.size, point.size, point.size, point.size);
        }
    }

    /**
     * Sets up the formula with some defaults
     */

    private void setupFormula(Settings settings) {
        formulaGenerator = new FormulaGenerator();

        formulaGenerator.setCenterY(canvas.getHeight() / 2.0);
        formulaGenerator.setCenterX(canvas.getWidth() / 2.0);

        formulaGenerator.setSettings(settings);
    }

    /**
     * Updates the canvas with the newest applied settings and recalculates points with those settings. Then it draws those new points to the screen
     */

    public void updateCanvas() {

        points = formulaGenerator.calculatePoints();

        drawPoints(context);
    }

    public void resized(double width, double height) {
        formulaGenerator.setCenterY(height / 2.0);
        formulaGenerator.setCenterX(width / 2.0);

        canvas.setWidth(width);
        canvas.setHeight(height);

        updateCanvas();
    }
}
