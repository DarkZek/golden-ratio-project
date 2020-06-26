package golden_ratio.darkzek.com.ui;

import golden_ratio.darkzek.com.Settings;
import golden_ratio.darkzek.com.formula.FormulaInterpolator;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static golden_ratio.darkzek.com.Helper.clampToPositive;

public class Controller {

    public Canvas canvas;
    public AnchorPane canvas_pane;
    public VBox settings_list;
    public ScrollPane settings_panel;

    public Slider rotation_per_step_slider;
    public TextField rotation_per_step_field;

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
        interpolator = new FormulaInterpolator(this, drawing, settings);

        // For testing purposes
        settings.save();

        drawing.updateCanvas();

        ChangeListener<Number> stageSizeListener = (observable, oldValue, newValue) ->
                drawing.resized(canvas_pane.getWidth(), canvas_pane.getHeight());

        canvas_pane.widthProperty().addListener(stageSizeListener);
        canvas_pane.heightProperty().addListener(stageSizeListener);

        canvas.setOnScroll(scrollEvent -> {

            double change = scrollEvent.getDeltaY() * 0.001;

            this.interpolator.targetSettings.distancePerRotation = clampToPositive(change + this.interpolator.targetSettings.distancePerRotation);

        });

        // Setup settings panel

        rotation_per_step_slider.adjustValue(settings.rotationPerPoint);

        rotation_per_step_slider.valueProperty().addListener((observableValue, number, t1) -> {
            if (!this.interpolator.interpolating) {
                this.interpolator.targetSettings.rotationPerPoint = number.floatValue();
                System.out.println("Test");
            }
        });

        rotation_per_step_field.setText(settings.rotationPerPoint + "");
    }

    public void onEnter(ActionEvent e) throws ParseException {
        double value = NumberFormat.getInstance(Locale.ENGLISH).parse(rotation_per_step_field.getText()).doubleValue();
        interpolator.appliedSettings.rotationPerPoint = value;
        interpolator.targetSettings.rotationPerPoint = value;
        interpolator.updateAll = true;
        interpolator.interpolating = true;
        rotation_per_step_slider.adjustValue(value);

    }

    public void stop() {
        // Stop the interpolator thread
        interpolator.drop();
    }
}
