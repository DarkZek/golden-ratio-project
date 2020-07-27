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
 * This is the settings class that is used by the formula class to store most of the formula parameters
 * It also features loading and saving function to make it persistant between restarts
 */

public class Settings implements Cloneable {
    public Expression rotationPerPoint =  new Expression(1);
    public double distancePerRotation = 1;
    public int points = 100;
    public Color startColor = Color.BLACK;
    public Color endColor = Color.BLACK;
    public RotationType rotationType = RotationType.Degrees;
    public double defaultSize = 10;
    public double sizeIncreasePerPoint = -0.02;
    public boolean smoothAnimation = true;
    public boolean joinClosePoints = false;

    /**
     * Creates a new settings instance and tries to load the saved settings on disk
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
            Files.lines(path).forEach(line -> {
               String[] lines = line.split(": ");

               // If there's not two theres an error because its formatted like `key: value`
               if (lines.length != 2) {
                   return;
               }

               String value = lines[1];

               switch (lines[0]) {
                   case "rotation_per_point": {
                       settings.rotationPerPoint.setExpression(value);
                       break;
                   }
                   case "distance_per_rotation": {
                       settings.distancePerRotation = Double.parseDouble(value);
                       break;
                   }
                   case "points": {
                       settings.points = Integer.parseInt(value);
                       break;
                   }
                   case "start_color": {
                       settings.startColor = Color.web(value);
                       break;
                   }
                   case "end_color": {
                       settings.endColor = Color.web(value);
                       break;
                   }
                   case "rotation_type": {
                       settings.rotationType = RotationType.valueOf(value);
                       break;
                   }
                   case "default_size": {
                       settings.defaultSize = Double.parseDouble(value);
                       break;
                   }
                   case "size_increase_per_point": {
                       settings.sizeIncreasePerPoint = Double.parseDouble(value);
                       break;
                   }
                   case "smooth_animation": {
                       settings.smoothAnimation = Boolean.getBoolean(value);
                       break;
                   }
                   case "joinClosePoints": {
                       settings.joinClosePoints = Boolean.getBoolean(value);
                       break;
                   }
               }
            });

        } catch (Exception e) {
            return new Settings();
        }

        return settings;
    }

    /**
     * Tries to save the settings to disk
     */

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
        content += "smooth_animation: " + smoothAnimation + "\n";
        content += "join_close_points: " + joinClosePoints + "\n";

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
     * @return Returns a new copy of the settings
     */
    public Settings do_clone() {
        try {
            Settings s = (Settings) this.clone();
            s.rotationPerPoint = new Expression(this.rotationPerPoint.getValue());
            return s;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Clears the current settings and restores to factory default.
     */
    public void clear() {
        rotationPerPoint.setValue(1);
        distancePerRotation = 1;
        points = 100;
        startColor = Color.BLACK;
        endColor = Color.BLACK;
        rotationType = RotationType.Degrees;
        defaultSize = 10;
        sizeIncreasePerPoint = -0.02;
        smoothAnimation = true;
        joinClosePoints = false;
    }
}

