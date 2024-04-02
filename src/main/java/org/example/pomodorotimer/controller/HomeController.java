package org.example.pomodorotimer.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import org.example.pomodorotimer.component.Bubble;
import org.example.pomodorotimer.fx.SceneLoader;
import org.example.pomodorotimer.fx.Scenes;


public class HomeController {
    @FXML private StackPane homeBackground;
    @FXML private AnchorPane bubblesContainer;
    @FXML private Label fiveMinCircleLabel;
    @FXML private Label twoFiveMinCircleLabel;
    @FXML private Label fiveFiveMinCircleLabel;
    @FXML private Circle fiveMinCircle;
    @FXML private Circle twoFiveMinCircle;
    @FXML private Circle fiveFiveMinCircle;
    @FXML private StackPane fiveMinContainer;
    @FXML private StackPane twoFiveMinContainer;
    @FXML private StackPane fiveFiveMinContainer;

    private Bubble fiveMinBubble;
    private Bubble twoFiveMinBubble;
    private Bubble fiveFiveMinBubble;

    private final int FIVE_MIN_SECONDS = 60 * 5;
    private final int TWO_FIVE_MIN_SECONDS = 60 * 25;
    private final int FIVE_FIVE_MIN_SECONDS = 60 * 55;



    @FXML
    public void initialize() {
        fiveMinBubble = Bubble.initialize(fiveMinContainer, fiveMinCircle, fiveMinCircleLabel);
        twoFiveMinBubble = Bubble.initialize(twoFiveMinContainer, twoFiveMinCircle, twoFiveMinCircleLabel);
        fiveFiveMinBubble = Bubble.initialize(fiveFiveMinContainer, fiveFiveMinCircle, fiveFiveMinCircleLabel);

        fiveMinContainer.setOnMouseClicked(e -> SceneLoader.changeScene(Scenes.TIMER, e, new TimerController(FIVE_MIN_SECONDS)));
        twoFiveMinContainer.setOnMouseClicked(e -> SceneLoader.changeScene(Scenes.TIMER, e, new TimerController(TWO_FIVE_MIN_SECONDS)));
        fiveFiveMinContainer.setOnMouseClicked(e -> SceneLoader.changeScene(Scenes.TIMER, e, new TimerController(FIVE_FIVE_MIN_SECONDS)));
    }
}
