package com.darkzek.goldenratio.ui;

import com.darkzek.goldenratio.Settings;
import com.darkzek.goldenratio.formula.Expression;
import com.darkzek.goldenratio.formula.FormulaInterpolator;
import com.darkzek.goldenratio.formula.RotationType;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.*;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

import java.io.File;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

import static com.darkzek.goldenratio.Helper.clamp;
import static com.darkzek.goldenratio.Helper.clampInt;

//import javafx.embed.swing.SwingFXUtils;
//import javafx.swing.SwingFXUtils;

public class Controller {

    public Canvas canvas;
    public AnchorPane canvas_pane;
    public VBox settings_list;
    public ScrollPane settings_panel;

    public Slider rotation_per_step_slider;
    public TextField rotation_per_step_field;

    public Slider distance_per_rotation_slider;

    public TextField points_field;

    public ColorPicker start_color_picker;
    public ColorPicker end_color_picker;

    public ToggleGroup measurement_setting;
    public RadioButton measurement_radians_setting;
    public RadioButton measurement_degrees_setting;

    public Slider point_size_slider;
    public Slider point_size_increase_slider;

    public Button export_button;
    public Button reset_button;

    private FormulaInterpolator interpolator;

    private Settings settings;

    private Drawing drawing;

    /**
     * Initializes the controller, it's main goal is to setup the formula, load the settings, setup
     * the canvas and then display the first render of the points onto the canvas.
     */
    public void initialize() {

        canvas.setWidth(500);
        canvas.setHeight(500);

        // Setting up the services
        settings = Settings.load();
        drawing = new Drawing(canvas, settings);
        interpolator = new FormulaInterpolator(this, drawing, settings);

        drawing.updateCanvas();

        ChangeListener<Number> stageSizeListener =
                (observable, oldValue, newValue) ->
                        drawing.resized(canvas_pane.getWidth(), canvas_pane.getHeight());

        canvas_pane.widthProperty().addListener(stageSizeListener);
        canvas_pane.heightProperty().addListener(stageSizeListener);

        setupListeners();
    }

    /** Sets up listeners so that the app will respond to button clicks and keyboard input. */
    public void setupListeners() {
        canvas.setOnScroll(
                scrollEvent -> {
                    double change = scrollEvent.getDeltaY() * 0.001;

                    this.interpolator.targetSettings.distancePerRotation =
                            clamp(change + this.interpolator.targetSettings.distancePerRotation, 0.8, 6.2);

                    distance_per_rotation_slider.setValue(
                            this.interpolator.targetSettings.distancePerRotation);
                    this.interpolator.scrollingInterpolation = true;
                });

        // Setup settings panel

        rotation_per_step_slider.adjustValue(settings.rotationPerPoint.getValue());
        rotation_per_step_slider
                .valueProperty()
                .addListener(
                        (observableValue, oldValue, newValue) -> {
                            if (!this.interpolator.interpolating) {
                                this.interpolator.targetSettings.rotationPerPoint.setValue(
                                        newValue.doubleValue());
                            }
                        });

        rotation_per_step_field.setText(settings.rotationPerPoint.getExpression() + "");
        rotation_per_step_field.setOnAction(
                actionEvent -> {
                    Expression rotationPerPoint = interpolator.targetSettings.rotationPerPoint;
                    rotationPerPoint.setExpression(rotation_per_step_field.getText());

                    // Tell the interpolator to render the changes made above
                    interpolator.interpolating = true;

                    // Red outline if invalid
                    if (rotationPerPoint.isInvalid()) {
                        rotation_per_step_field.setBorder(
                                new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, new CornerRadii(5.0), BorderWidths.DEFAULT)));
                    } else {
                        rotation_per_step_field.setBorder(
                                new Border(new BorderStroke(Color.web("#323232"), BorderStrokeStyle.SOLID, new CornerRadii(5.0), BorderWidths.DEFAULT)));
                    }

                    // Update the slider to reflect the new value
                    rotation_per_step_slider.adjustValue(rotationPerPoint.getValue());
                });

        distance_per_rotation_slider.setValue(this.interpolator.targetSettings.distancePerRotation);
        distance_per_rotation_slider
                .valueProperty()
                .addListener(
                        (observableValue, oldValue, newValue) -> {
                            if (this.interpolator.scrollingInterpolation) {
                                this.interpolator.scrollingInterpolation = false;
                                return;
                            }

                            this.interpolator.targetSettings.distancePerRotation =
                                    newValue.doubleValue();
                        });

        points_field.setText(interpolator.appliedSettings.points + "");
        points_field.setOnAction(
                actionEvent -> {
                    try {
                        int value =
                                NumberFormat.getInstance(Locale.ENGLISH)
                                        .parse(points_field.getText())
                                        .intValue();

                        value = clampInt(value, 0, 10000);

                        interpolator.appliedSettings.points = value;
                        interpolator.targetSettings.points = value;
                        interpolator.updateAll = true;
                        points_field.setText(value + "");
                    } catch (ParseException _e) {
                        // There was an error parsing
                        points_field.setText(interpolator.targetSettings.points + "");
                    }
                });

        start_color_picker.getCustomColors().add(this.interpolator.appliedSettings.startColor);
        start_color_picker.setValue(this.interpolator.appliedSettings.startColor);
        start_color_picker
                .valueProperty()
                .addListener(
                        (observableValue, t1, color) ->
                                interpolator.targetSettings.startColor = color);
        start_color_picker.setOnAction(
                actionEvent ->
                        interpolator.targetSettings.startColor = start_color_picker.getValue());

        end_color_picker.getCustomColors().add(this.interpolator.appliedSettings.endColor);
        end_color_picker.setValue(this.interpolator.appliedSettings.endColor);
        end_color_picker
                .valueProperty()
                .addListener(
                        (observableValue, t1, color) ->
                                interpolator.targetSettings.endColor = color);
        end_color_picker.setOnAction(
                actionEvent -> interpolator.targetSettings.endColor = end_color_picker.getValue());

        measurement_setting
                .selectedToggleProperty()
                .addListener(
                        (observableValue, oldToggle, newToggle) -> {
                            String option = ((RadioButton) newToggle).getText();

                            interpolator.updateAll = true;

                            if (option.equals("Radians")) {
                                interpolator.appliedSettings.rotationType = RotationType.Radians;
                                interpolator.targetSettings.rotationType = RotationType.Radians;
                                rotation_per_step_slider.setMax(Math.PI * 2);
                            } else {
                                interpolator.appliedSettings.rotationType = RotationType.Degrees;
                                interpolator.targetSettings.rotationType = RotationType.Degrees;
                                rotation_per_step_slider.setMax(360.0);
                            }
                        });

        if (interpolator.appliedSettings.rotationType == RotationType.Radians) {
            measurement_radians_setting.setSelected(true);
            rotation_per_step_slider.setMax(Math.PI * 2);
        } else {
            measurement_degrees_setting.setSelected(true);
            rotation_per_step_slider.setMax(360.0);
        }

        point_size_slider.setValue(interpolator.targetSettings.defaultSize);
        point_size_slider
                .valueProperty()
                .addListener(
                        (observableValue, oldValue, newValue) -> {
                            interpolator.targetSettings.defaultSize = newValue.doubleValue();
                            interpolator.updateAll = true;
                        });

        point_size_increase_slider.setValue(interpolator.targetSettings.sizeIncreasePerPoint);
        point_size_increase_slider
                .valueProperty()
                .addListener(
                        (observableValue, oldValue, newValue) -> {
                            interpolator.targetSettings.sizeIncreasePerPoint =
                                    newValue.doubleValue();
                            interpolator.updateAll = true;
                        });

        export_button.setOnAction(
                actionEvent -> {
                    FileChooser fc = new FileChooser();
                    fc.setInitialDirectory(new File("./"));

                    fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("PNG", "*.png"));
                    fc.setTitle("Save Map");
                    fc.setInitialFileName("Drawing.png");

                    File file =
                            fc.showSaveDialog(
                                    ((Node) actionEvent.getSource()).getScene().getWindow());

                    if (file != null) {
                        WritableImage wi =
                                new WritableImage(
                                        (int) canvas.getWidth(), (int) canvas.getHeight());
//                        try {
//                            SnapshotParameters sp = new SnapshotParameters();
//                            sp.setFill(Color.TRANSPARENT);
//
//                            ImageIO.write(
//                                    SwingFXUtils.fromFXImage(canvas.snapshot(sp, wi), null),
//                                    "png",
//                                    file);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
                    }
                });

        reset_button.setOnAction(
                actionEvent -> {
                    settings.clear();
                    interpolator.appliedSettings.clear();
                    interpolator.targetSettings.clear();

                    start_color_picker.setValue(interpolator.appliedSettings.startColor);
                    end_color_picker.setValue(interpolator.appliedSettings.endColor);

                    if (interpolator.appliedSettings.rotationType == RotationType.Radians) {
                        measurement_degrees_setting.setSelected(false);
                        measurement_radians_setting.setSelected(true);
                    } else {
                        measurement_degrees_setting.setSelected(true);
                        measurement_radians_setting.setSelected(false);
                    }

                    points_field.setText(interpolator.appliedSettings.points + "");
                    point_size_slider.setValue(interpolator.appliedSettings.defaultSize);
                    point_size_increase_slider.setValue(interpolator.appliedSettings.sizeIncreasePerPoint);

                    interpolator.updateAll = true;
                });
    }

    /** Stops the interpolation thread */
    public void stop() {
        // Stop the interpolator thread
        interpolator.drop();
    }
}
