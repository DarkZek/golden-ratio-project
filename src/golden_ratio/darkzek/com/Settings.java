package golden_ratio.darkzek.com;

import golden_ratio.darkzek.com.formula.Expression;
import golden_ratio.darkzek.com.formula.RotationType;
import javafx.scene.paint.Color;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * This is the settings class that is used by the formula class to store most of the formula
 * parameters It also features loading and saving function to make it persistant between restarts
 */
public class Settings implements Cloneable {
    public Expression rotationPerPoint = new Expression(0.115);
    public double distancePerRotation = 2.249234114419136;
    public int points = 200;
    public Color startColor = Color.web("0x000080ff");
    public Color endColor = Color.BLACK;
    public RotationType rotationType = RotationType.Radians;
    public double defaultSize = 6;
    public double sizeIncreasePerPoint = 0.05;
    public boolean smoothAnimation = true;

    /**
     * Creates a new settings instance and tries to load the saved settings on disk
     *
     * @return New settings instance
     */
    public static Settings load() {

        Settings settings = new Settings();

        File file = new File("./settings.yml");

        if (!file.exists()) {
            return settings;
        }

        Path path = Path.of("./settings.yml");

        try {
            Files.lines(path)
                    .forEach(
                            line -> {
                                String[] lines = line.split(": ");

                                // If there's not two theres an error because its formatted like
                                // `key: value`
                                if (lines.length != 2) {
                                    return;
                                }

                                String value = lines[1];

                                switch (lines[0]) {
                                    case "rotation_per_point":
                                        {
                                            settings.rotationPerPoint.setExpression(value);
                                            break;
                                        }
                                    case "distance_per_rotation":
                                        {
                                            settings.distancePerRotation =
                                                    Double.parseDouble(value);
                                            break;
                                        }
                                    case "points":
                                        {
                                            settings.points = Integer.parseInt(value);
                                            break;
                                        }
                                    case "start_color":
                                        {
                                            settings.startColor = Color.web(value);
                                            break;
                                        }
                                    case "end_color":
                                        {
                                            settings.endColor = Color.web(value);
                                            break;
                                        }
                                    case "rotation_type":
                                        {
                                            settings.rotationType = RotationType.valueOf(value);
                                            break;
                                        }
                                    case "default_size":
                                        {
                                            settings.defaultSize = Double.parseDouble(value);
                                            break;
                                        }
                                    case "size_increase_per_point":
                                        {
                                            settings.sizeIncreasePerPoint =
                                                    Double.parseDouble(value);
                                            break;
                                        }
                                }
                            });

        } catch (Exception e) {
            return new Settings();
        }

        return settings;
    }

    /** Tries to save the settings to disk */
    public void save() {
        String content = "# Settings configuration for Golden Ratio Viewer" + "\n";
        content += "rotation_per_point: " + rotationPerPoint.getExpression() + "\n";
        content += "distance_per_rotation: " + distancePerRotation + "\n";
        content += "points: " + points + "\n";
        content += "start_color: " + startColor + "\n";
        content += "end_color: " + endColor + "\n";
        content += "rotation_type: " + rotationType + "\n";
        content += "default_size: " + defaultSize + "\n";
        content += "size_increase_per_point: " + sizeIncreasePerPoint + "\n";

        try {
            File file = new File("./settings.yml");

            // Create if it doesn't exist
            file.createNewFile();

            FileWriter fileWriter = new FileWriter("./settings.yml");
            fileWriter.write(content);
            fileWriter.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Clones the current settings
     *
     * @return Returns a new copy of the settings
     */
    public Settings do_clone() {
        try {
            Settings s = (Settings) this.clone();
            s.rotationPerPoint = new Expression(this.rotationPerPoint.getExpression());
            return s;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /** Clears the current settings and restores to factory default. */
    public void clear() {
        rotationPerPoint.setValue(0.115);
        distancePerRotation = 2.249234114419136;
        points = 200;
        startColor = Color.web("0x000080ff");
        endColor = Color.BLACK;
        rotationType = RotationType.Radians;
        defaultSize = 6;
        sizeIncreasePerPoint = 0.05;
    }
}
