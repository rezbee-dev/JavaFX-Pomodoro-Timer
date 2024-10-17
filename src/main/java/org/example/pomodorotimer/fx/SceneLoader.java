package org.example.pomodorotimer.fx;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneLoader {

    public static void changeScene(Scenes scene, Stage stage){
        Parent root = getRoot(scene);
        stage.setTitle(scene.getScene());
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Change scene from event (ex: click)
    public static void changeScene(Scenes scene, Event actionEvent) {
        Stage stage = (Stage) ( (Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = getRoot(scene);
        stage.setTitle(scene.getScene());
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Change scene from event & pass data to controller
    public static void changeScene(Scenes scene, Event actionEvent, Initializable controller) {
        Stage stage = (Stage) ( (Node) actionEvent.getSource()).getScene().getWindow();
        Parent root = getRoot(scene, controller);
        stage.setTitle(scene.getScene());
        stage.setScene(new Scene(root));
        stage.show();
    }

    // Loads Stage
    private static Parent getRoot(Scenes scene) {
        try {
            return FXMLLoader.load(scene.getPath());
        } catch (Exception err) {
            Platform.exit();
            return null;
        }
    }

    // Loads stage w/ controller
    private static Parent getRoot(Scenes scene, Initializable controller) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(scene.getPath());
            loader.setController(controller);
            return loader.load();
        } catch (IOException err) {
            throw new RuntimeException(err);
        }
    }
}
