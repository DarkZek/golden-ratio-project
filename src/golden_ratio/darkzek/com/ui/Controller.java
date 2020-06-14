package golden_ratio.darkzek.com.ui;

import golden_ratio.darkzek.com.Settings;
import golden_ratio.darkzek.com.formula.FormulaGenerator;
import golden_ratio.darkzek.com.formula.Point;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import java.util.Timer;
import java.util.TimerTask;

public class Controller {

    public Canvas canvas;

    private FormulaGenerator formulaGenerator;

    private Settings settings;

    private Point[] points;

    /**
     * Initializes the controller, it's main goal is to setup the formula, load the settings, setup the canvas and then display the first render of the points onto the canvas.
     */

    public void initialize() {

        canvas.setWidth(500);
        canvas.setHeight(500);

        settings = Settings.load();

        // For testing purposes
        settings.save();

        // Sets up the formula with some decent defaults
        setupFormula();

        updateCanvas();

        // For the demo video
//        Timer timer = new Timer();
//
//        TimerTask task = new TimerTask()
//        {
//            public void run()
//            {
//                settings.rotationPerPoint += 0.002;
//                updateCanvas();
//            }
//
//        };
//
//        timer.scheduleAtFixedRate(task,5000, 50);
    }

    public void updateCanvas() {
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

        formulaGenerator.setSettings(settings);
    }

    /**
     * To be replaced with a drawing class soon
     * @param context The context on which to draw
     */

    public void drawPoints(GraphicsContext context) {

        context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (Point point : points) {
            context.setFill(point.color);
            context.fillRoundRect(point.x, point.y, point.size, point.size, point.size, point.size);
        }
    }
}
