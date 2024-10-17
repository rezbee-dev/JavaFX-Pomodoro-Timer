package org.example.pomodorotimer.component;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.*;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import org.example.pomodorotimer.fx.SceneLoader;
import org.example.pomodorotimer.fx.Scenes;

import java.time.temporal.ChronoUnit;

// src: https://stackoverflow.com/a/67582559
public class Timer {
    private Label pomodoroTimeLabel;
    private int pomodoroTimeInCycles;
    private Timeline pomodoroTimeAnimation;
    private ObjectProperty<java.time.Duration> pomodoroTimeProp;

    // sand -> rectangle that grows downwards to represent timer ticking down
    private Rectangle sand;
    private double sandMaxHeight;
    private DoubleProperty sandCurrentHeightProp = new SimpleDoubleProperty(0);

    // timer controls
    private VBox timerButtonsContainer;
    private Label timerResumeLabel;
    private Label timerBackLabel;

    private BooleanProperty areTimerControlsVisible = new SimpleBooleanProperty(false);

    private Timer(Label pomodoroTimeLabel, int pomodoroTime, Rectangle sand, double sandMaxHeight, VBox timerButtonsContainer, Label timerResumeLabel, Label timerBackLabel) {
        this.sand = sand;
        this.sandMaxHeight = sandMaxHeight;

        this.pomodoroTimeLabel = pomodoroTimeLabel;
        this.pomodoroTimeLabel.setOnMouseClicked(this::setupOnClickTimer);
        this.pomodoroTimeLabel.setOnMouseEntered(this::setupLabelDarken);
        this.pomodoroTimeLabel.setOnMouseExited(this::setupLabelLighten);
        this.pomodoroTimeProp = new SimpleObjectProperty<>(java.time.Duration.ofSeconds(pomodoroTime));
        this.pomodoroTimeInCycles = (int) this.pomodoroTimeProp.get().getSeconds();
        this.pomodoroTimeAnimation = new Timeline(new KeyFrame(Duration.seconds(1), this::setOnPomodoroTimeCycle));

        this.setupPomodoroTimeAnimation();

        this.timerButtonsContainer = timerButtonsContainer;
        this.timerButtonsContainer.visibleProperty().bind(this.areTimerControlsVisible);
        this.timerResumeLabel = timerResumeLabel;
        this.timerResumeLabel.setOnMouseClicked(this::setupOnClickTimer);
        this.timerResumeLabel.setOnMouseEntered(this::setupLabelDarken);
        this.timerResumeLabel.setOnMouseExited(this::setupLabelLighten);
        this.timerBackLabel = timerBackLabel;
        this.timerBackLabel.setOnMouseClicked(e -> SceneLoader.changeScene(Scenes.HOME, e));
        this.timerBackLabel.setOnMouseEntered(this::setupLabelDarken);
        this.timerBackLabel.setOnMouseExited(this::setupLabelLighten);
    }

    private void setupOnClickTimer(MouseEvent e) {
        this.areTimerControlsVisible.setValue(!this.areTimerControlsVisible.get());

        if(this.areTimerControlsVisible.get()) {
            this.pomodoroTimeAnimation.pause();
        } else {
            this.pomodoroTimeAnimation.play();
        }
    }

    private void setupLabelDarken(MouseEvent e) {
        Label label = (Label) e.getSource();
        label.setTextFill(Color.web("#4a3757"));
    }

    private void setupLabelLighten(MouseEvent e) {
        Label label = (Label) e.getSource();
        label.setTextFill(Color.WHITE);
    }

    private void setupPomodoroTimeAnimation() {
        this.pomodoroTimeLabel.textProperty().bind(this.setupPomodoroTimeLabelBinding());
        this.sand.heightProperty().bind(this.sandCurrentHeightProp);
        this.pomodoroTimeAnimation.setCycleCount(this.pomodoroTimeInCycles);
        this.pomodoroTimeAnimation.play();
    }

    // Extracted to its own method for readability
    private StringBinding setupPomodoroTimeLabelBinding() {
        return Bindings.createStringBinding(() ->
                        String.format("%02d:%02d:%02d",
                                this.pomodoroTimeProp.get().toHours(),
                                this.pomodoroTimeProp.get().toMinutesPart(),
                                this.pomodoroTimeProp.get().toSecondsPart()), this.pomodoroTimeProp);
    }

    // Events that occur during each cycle of the pomodoroTimeAnimation
    private void setOnPomodoroTimeCycle(ActionEvent event){
        this.pomodoroTimeProp.setValue(this.calculateTimeIncrements());
        this.sandCurrentHeightProp.setValue(this.calculateHeightIncrements());
    }

    private java.time.Duration calculateTimeIncrements(){
        return this.pomodoroTimeProp.get().minus(1, ChronoUnit.SECONDS);
    }

    private double calculateHeightIncrements() {
        double heightIncrement = this.sandCurrentHeightProp.get() + (this.sandMaxHeight /this.pomodoroTimeInCycles);
//        System.out.println("height increment:" + heightIncrement); // debug
        return heightIncrement;
    }

    // Factory method for setting up Timer (animation logic) and JavaFX controls (Label, Rectangle, etc)
    public static Timer init(Label timer, int time, Rectangle backgroundSand, double containerHeight, VBox timerButtonsContainer, Label timerResumeLabel, Label timerBackLabel){
        return new Timer(timer, time, backgroundSand, containerHeight, timerButtonsContainer, timerResumeLabel, timerBackLabel);
    }
}
