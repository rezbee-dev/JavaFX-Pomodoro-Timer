package org.example.pomodorotimer;

import javafx.application.Application;
import javafx.stage.Stage;
import org.example.pomodorotimer.fx.SceneLoader;
import org.example.pomodorotimer.fx.Scenes;


public class Main extends Application {
    @Override
    public void start(Stage stage) {
        SceneLoader.changeScene(Scenes.HOME, stage);
    }

    public static void main(String[] args) {
        launch();
    }
}
