package golden_ratio.darkzek.com;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

public class Controller {

    public Canvas canvas;

    private FormulaGenerator formulaGenerator;

    private Point[] points;

    /**
     * Initializes the controller, it's main goal is to setup the formula, load the settings, setup the canvas and then display the first render of the points onto the canvas.
     */

    public void initialize() {

        canvas.setWidth(500);
        canvas.setHeight(500);

        // Sets up the formula with some decent defaults
        // TODO: Get this to load from settings
        setupFormula();

        GraphicsContext context = canvas.getGraphicsContext2D();

        points = formulaGenerator.calculatePoints();

        drawPoints(context);

    }

    /**
     * Sets up the formula with some defaults
     */

    private void setupFormula() {
        formulaGenerator = new FormulaGenerator();

        formulaGenerator.setCenterY(canvas.getHeight() / 2.0);
        formulaGenerator.setCenterX(canvas.getWidth() / 2.0);

        formulaGenerator.setSteps(40);
        formulaGenerator.setDistancePerRotation(5);
        formulaGenerator.setRotationPerStep(2);
    }

    /**
     * To be replaced with a drawing class soon
     * @param context The context on which to draw
     */

    public void drawPoints(GraphicsContext context) {
        for (Point point : points) {
            context.setFill(point.color);
            context.fillRoundRect(point.x, point.y, point.size, point.size, point.size, point.size);
        }
    }
}
