package com.example.clone;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

import static com.example.clone.Friend.Update;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainFrame.fxml"));
            Parent root = loader.load();
            MainFrameController controller = loader.getController();
// Imposta il valore della ComboBox dopo il caricamento
            controller.FriendsBox.getItems().add("Aggiungi");
            controller.LabelUpdate(controller.DateLabel,"1/1/2020");

            primaryStage.setTitle("JavaFX App");
            primaryStage.setScene(new Scene(root, 800, 600));
            primaryStage.show();

            // **Gestiamo l'evento di chiusura**
            primaryStage.setOnCloseRequest(event -> {
                System.out.println("Chiusura rilevata, salvando dati...");
                controller.UpdateFile();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}