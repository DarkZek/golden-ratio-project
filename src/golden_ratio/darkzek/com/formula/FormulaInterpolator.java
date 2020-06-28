package golden_ratio.darkzek.com.formula;

import golden_ratio.darkzek.com.Helper;
import golden_ratio.darkzek.com.Settings;
import golden_ratio.darkzek.com.ui.Controller;
import golden_ratio.darkzek.com.ui.Drawing;
import javafx.application.Platform;
import javafx.scene.paint.Color;

import java.util.Timer;
import java.util.TimerTask;

import static golden_ratio.darkzek.com.Helper.lerp;
import static golden_ratio.darkzek.com.Helper.lerpInt;

public class FormulaInterpolator extends TimerTask {
    public Settings appliedSettings;
    public Settings targetSettings;
    public boolean updateAll;
    public boolean interpolating = false;

    private final Drawing drawing;
    private final Timer timer;
    private final Controller controller;

    public int FPS = 60;

    public int frames = 0;
    public long startTime = 0;

    // Doesn't schedule new frame until old one is done
    public boolean drewFrame = false;

    public FormulaInterpolator(Controller controller, Drawing drawing, Settings settings) {

        this.controller = controller;
        this.drawing = drawing;
        this.appliedSettings = settings;
        this.targetSettings = appliedSettings.do_clone();
        this.targetSettings.startColor = Color.GREEN;

        timer = new Timer();

        startTime = System.nanoTime();
        timer.scheduleAtFixedRate(this, 0, 1000 / FPS);
    }

    @Override
    public void run() {

        if (drewFrame) {
            return;
        }

        drewFrame = true;

        // Run in UI thread
        Platform.runLater(() -> {

            if (startTime < System.nanoTime() - 1000000000.0 && System.getenv("SHOW-FPS") != null) {
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
            if (appliedSettings.rotationPerPoint != targetSettings.rotationPerPoint || updateAll) {
                appliedSettings.rotationPerPoint = lerp(appliedSettings.rotationPerPoint, targetSettings.rotationPerPoint, animationSpeed * 10, 0.0000001);
                controller.rotation_per_step_field.setText(appliedSettings.rotationPerPoint + "");
            } else {
                interpolating = false;
            }

            if (appliedSettings.distancePerRotation != targetSettings.distancePerRotation || updateAll) {
                appliedSettings.distancePerRotation = lerp(appliedSettings.distancePerRotation, targetSettings.distancePerRotation, animationSpeed, 0.001);
            }
            if (appliedSettings.sizeIncreasePerPoint != targetSettings.sizeIncreasePerPoint || updateAll) {
                appliedSettings.sizeIncreasePerPoint = lerp(appliedSettings.sizeIncreasePerPoint, targetSettings.sizeIncreasePerPoint, animationSpeed, 0.001);
            }
            if (appliedSettings.defaultSize != targetSettings.defaultSize || updateAll) {
                appliedSettings.defaultSize = lerp(appliedSettings.defaultSize, targetSettings.defaultSize, animationSpeed, 0.001);
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

    public void drop() {
        timer.cancel();
        timer.purge();
    }
}
