package golden_ratio.darkzek.com.formula;

import golden_ratio.darkzek.com.Helper;
import golden_ratio.darkzek.com.Settings;
import golden_ratio.darkzek.com.ui.Controller;
import golden_ratio.darkzek.com.ui.Drawing;
import javafx.application.Platform;

import java.util.Timer;
import java.util.TimerTask;

import static golden_ratio.darkzek.com.Helper.lerp;

/**
 * The formula interpolator is the seperate thread that animates the graph. It takes the current settings of the graph, and slowly morphs them into the target settings the user requested.
 * This does things like makes the points animate towards where they should be when changing sliders, as well as animating colour changes and zooming in and out.
 * Because this is on a seperate thread we need to be careful to shut it down when the window is closed.
 */
public class FormulaInterpolator extends TimerTask {
    public Settings appliedSettings;
    public Settings targetSettings;

    // Tells the interpolator if we should do a full rerender.
    public boolean updateAll;

    // Tells the interpolator if we are animating currently.
    public boolean interpolating = false;

    private final Drawing drawing;
    private final Timer timer;
    private final Controller controller;

    public int FPS = 60;

    // FPS counting variables
    public int frames = 0;
    public long startTime = 0;

    // Doesn't schedule new frame to be drawn until old one is done
    public boolean drewFrame = false;
    public boolean scrollingInterpolation = false;

    /**
     * Initialises the formula controller and starts process
     * @param controller The view controller
     * @param drawing The drawing
     * @param settings The default settings
     */
    public FormulaInterpolator(Controller controller, Drawing drawing, Settings settings) {

        this.controller = controller;
        this.drawing = drawing;
        this.appliedSettings = settings;
        this.targetSettings = appliedSettings.do_clone();

        timer = new Timer();

        startTime = System.nanoTime();
        timer.scheduleAtFixedRate(this, 0, 1000 / FPS);
    }

    /**
     * Starts running the interpolator thread
     */
    @Override
    public void run() {

        if (drewFrame) {
            return;
        }

        drewFrame = true;

        // Run in UI thread
        Platform.runLater(() -> {

            if (startTime < System.nanoTime() - 1_000_000_000.0 && System.getenv("SHOW-FPS") != null) {
                double difference = (System.nanoTime() - startTime) * 0.000000001;
                System.out.println("FPS: " + (frames / difference));
                startTime = System.nanoTime();
                frames = 0;
            }
            frames++;

            // This is ran every frame so if the fps is 30 a (1/30) animation speed will take 1 second to complete
            double animationSpeed = 1.0 / FPS;

            // Because of the high cost of if statements it's actually faster to just perform this calculation regardless of it the field actually changed
            // Some have speed multipliers to speed up certain animations
            if (appliedSettings.rotationPerPoint.getValue() != targetSettings.rotationPerPoint.getValue() || updateAll) {

                controller.rotation_per_step_field.setText(targetSettings.rotationPerPoint.getExpression());
                appliedSettings.rotationPerPoint.setValue(lerp(appliedSettings.rotationPerPoint.getValue(), targetSettings.rotationPerPoint.getValue(), animationSpeed * 10, 0.0000001));

            } else {
                interpolating = false;

                if (appliedSettings.rotationPerPoint.getValue() != targetSettings.rotationPerPoint.getValue()) {
                    appliedSettings.rotationPerPoint.setExpression(targetSettings.rotationPerPoint.getExpression());
                }
            }

            if (appliedSettings.distancePerRotation != targetSettings.distancePerRotation || updateAll) {
                appliedSettings.distancePerRotation = lerp(appliedSettings.distancePerRotation, targetSettings.distancePerRotation, animationSpeed, 0.001);
            }
            if (appliedSettings.sizeIncreasePerPoint != targetSettings.sizeIncreasePerPoint || updateAll) {
                appliedSettings.sizeIncreasePerPoint = lerp(appliedSettings.sizeIncreasePerPoint, targetSettings.sizeIncreasePerPoint, animationSpeed, 0.001);
            }
            if (appliedSettings.defaultSize != targetSettings.defaultSize || updateAll) {
                appliedSettings.defaultSize = lerp(appliedSettings.defaultSize, targetSettings.defaultSize, animationSpeed * 100, 0.001);
            }
            if (appliedSettings.startColor != targetSettings.startColor || updateAll) {
                appliedSettings.startColor = Helper.lerpColor(appliedSettings.startColor, targetSettings.startColor, animationSpeed);
            }
            if (appliedSettings.endColor != targetSettings.endColor || updateAll) {
                appliedSettings.endColor = Helper.lerpColor(appliedSettings.endColor, targetSettings.endColor, animationSpeed);
            }

            drawing.updateCanvas();
            updateAll = false;
            drewFrame = false;

        });
    }

    /**
     * Drop and stops thread
     */
    public void drop() {
        timer.cancel();
        timer.purge();

        // Save settings
        this.targetSettings.save();
    }
}
