/*William Stearns
8-17-25
CSD-420 Module 1
This program selects and displays four random cards from a deck with refresh button*/

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Collections;

public class Cards extends Application {
    private ArrayList<Image> cards; // Store all 52 cards
    private HBox hBox;

    @Override
    public void start(Stage primaryStage) {
        // Load all card images from the "cards" subdirectory
        cards = new ArrayList<>();
        for (int i = 1; i <= 52; i++) {
            Image image = new Image(getClass().getResource("/cards/" + i + ".png").toExternalForm());
            cards.add(image);
        }

        // Button to refresh cards
        Button refreshButton = new Button("Refresh");
        refreshButton.setOnAction(e -> displayRandomCards()); // Lambda expression

        hBox = new HBox(10);
        hBox.setAlignment(Pos.CENTER);

        BorderPane pane = new BorderPane();
        pane.setCenter(hBox);

        HBox buttonBox = new HBox(refreshButton);
        buttonBox.setAlignment(Pos.CENTER);
        pane.setBottom(buttonBox);

        displayRandomCards(); // Display initial 4 cards

        Scene scene = new Scene(pane, 400, 400);
        primaryStage.setTitle("Random Cards");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Display 4 random cards
    private void displayRandomCards() {
        hBox.getChildren().clear();

        // Shuffle the cards and take the first 4
        Collections.shuffle(cards);
        for (int i = 0; i < 4; i++) {
            ImageView viewCard = new ImageView(cards.get(i));
            viewCard.setFitWidth(80);
            viewCard.setPreserveRatio(true);
            hBox.getChildren().add(viewCard);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
