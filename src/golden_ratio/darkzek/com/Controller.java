package golden_ratio.darkzek.com;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

import java.net.URL;

public class Controller {

    public Canvas canvas;

    private FormulaGenerator formulaGenerator;

    private Point[] points;

    public void initialize() {

        canvas.setWidth(500);
        canvas.setHeight(500);

        // Setup formula
        formulaGenerator = new FormulaGenerator();

        formulaGenerator.setCenterY(canvas.getHeight() / 2.0);
        formulaGenerator.setCenterX(canvas.getWidth() / 2.0);

        formulaGenerator.setSteps(40);
        formulaGenerator.setDistancePerRotation(5);
        formulaGenerator.setRotationPerStep(2);

        GraphicsContext context = canvas.getGraphicsContext2D();

        context.setFill(Color.RED);
        context.setStroke(Color.BLUE);
        context.setLineWidth(5);

        context.fillRect(-75, -75, 100, 100);

        System.out.println("REEE");

        points = formulaGenerator.calculatePoints();

        drawPoints(context);

    }

    public void drawPoints(GraphicsContext context) {
        for (Point point : points) {
            context.setFill(point.color);
            context.fillRoundRect(point.x, point.y, point.size, point.size, point.size, point.size);
        }
    }
}
