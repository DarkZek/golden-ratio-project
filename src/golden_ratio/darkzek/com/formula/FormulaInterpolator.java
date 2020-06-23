package golden_ratio.darkzek.com.formula;

import golden_ratio.darkzek.com.Helper;
import golden_ratio.darkzek.com.Settings;
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

    private Drawing drawing;
    private Timer timer;

    public FormulaInterpolator(Drawing drawing, Settings settings) {

        this.drawing = drawing;
        this.appliedSettings = settings;
        this.targetSettings = appliedSettings.do_clone();
        this.targetSettings.startColor = Color.GREEN;

        int fps = 60;

        timer = new Timer();

        timer.scheduleAtFixedRate(this, 0, 1000 / fps);
    }

    @Override
    public void run() {

        // Run in UI thread
        Platform.runLater(() -> {

            // This is ran every frame so if the fps is 30 a (1/30) animation speed will take 1 second to complete
            double animationSpeed = 1.0 / 30.0;

            // Because of the high cost of if statements it's actually faster to just perform this calculation regardless of it the field actually changed

            appliedSettings.rotationPerPoint        = lerp(appliedSettings.rotationPerPoint, targetSettings.rotationPerPoint, animationSpeed);
            //appliedSettings.points                  = lerpInt(appliedSettings.points, targetSettings.points, animationSpeed);
            appliedSettings.distancePerRotation     = lerp(appliedSettings.distancePerRotation, targetSettings.distancePerRotation, animationSpeed);
            appliedSettings.sizeIncreasePerPoint    = lerp(appliedSettings.sizeIncreasePerPoint, targetSettings.sizeIncreasePerPoint, animationSpeed);
            appliedSettings.defaultSize             = lerp(appliedSettings.defaultSize, targetSettings.defaultSize, animationSpeed);
            appliedSettings.startColor              = Helper.lerpColor(appliedSettings.startColor, targetSettings.startColor, animationSpeed);
            appliedSettings.endColor                = Helper.lerpColor(appliedSettings.endColor, targetSettings.endColor, animationSpeed);

            drawing.updateCanvas();

        });
    }

    public void drop() {
        timer.cancel();
        timer.purge();
    }
}
