package golden_ratio.darkzek.com.ui;

import com.sun.media.jfxmediaimpl.platform.Platform;
import golden_ratio.darkzek.com.Settings;
import golden_ratio.darkzek.com.formula.FormulaGenerator;
import golden_ratio.darkzek.com.formula.FormulaInterpolator;
import golden_ratio.darkzek.com.formula.Point;
import javafx.scene.canvas.Canvas;

public class Controller {

    public Canvas canvas;

    private FormulaInterpolator interpolator;

    private Settings settings;

    private Drawing drawing;

    /**
     * Initializes the controller, it's main goal is to setup the formula, load the settings, setup the canvas and then display the first render of the points onto the canvas.
     */

    public void initialize() {

        canvas.setWidth(500);
        canvas.setHeight(500);

        // Setting up the services
        settings = Settings.load();
        drawing = new Drawing(canvas, settings);
        interpolator = new FormulaInterpolator(drawing, settings);

        // For testing purposes
        settings.save();

        drawing.updateCanvas();
    }

    public void stop() {
        // Stop the interpolator thread
        interpolator.drop();
    }
}
