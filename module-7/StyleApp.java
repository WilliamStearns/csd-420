/* William Stearns
   9-28-25
   CSD-420 Module 7
   This program displays JavaFX circles with a css stylesheet
*/

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class StyleApp extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Use a Pane for manual layout to match the image
        Pane pane = createContentPane();

        // Create a scene and link the stylesheet
        Scene scene = new Scene(pane, 450, 200);
        scene.getStylesheets().add(getClass().getResource("mystyle.css").toExternalForm());

        primaryStage.setTitle("Style App"); // Set the stage title from the image
        primaryStage.setScene(scene); // Place the scene in the stage
        primaryStage.show(); // Display the stage
    }

    public Pane createContentPane() {
        Pane pane = new Pane();
        pane.setPadding(new Insets(10, 10, 10, 10));

        // Create a rectangle and apply a style class
        Rectangle rectangle = new Rectangle(20, 20, 100, 160);
        rectangle.getStyleClass().add("bordered-rect");

        // Create four circles
        double radius = 40;
        double yPosition = 100;

        // Position the first circle in the center of the rectangle
        Circle circle1 = new Circle(70, yPosition, radius);
        // Reposition the other circles to maintain even spacing
        Circle circle2 = new Circle(180, yPosition, radius);
        Circle circle3 = new Circle(270, yPosition, radius);
        Circle circle4 = new Circle(360, yPosition, radius);

        // Apply the .plaincircle style class to all circles
        circle1.getStyleClass().add("plaincircle");
        circle2.getStyleClass().add("plaincircle");
        circle3.getStyleClass().add("plaincircle");
        circle4.getStyleClass().add("plaincircle");

        // Apply specific IDs to the third and forth circles
        // The CSS will override the fill color and stroke for these circles
        circle3.setId("redcircle");
        circle4.setId("greencircle");

        // Add shapes to the pane
        pane.getChildren().addAll(rectangle, circle1, circle2, circle3, circle4);

        return pane;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
