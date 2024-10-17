package org.example.pomodorotimer.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import org.example.pomodorotimer.component.Timer;

import java.net.URL;
import java.util.ResourceBundle;

public class TimerController implements Initializable {
    @FXML private Label timerLabel;
    @FXML private Rectangle sandRectangle;
    @FXML private StackPane timerContainer;
    @FXML private VBox timerButtonsContainer;
    @FXML private Label timerResumeLabel;
    @FXML private Label timerBackLabel;
    private Timer timer;
    private int time;

    public TimerController(int time){
        this.time = time;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timer = Timer.init(timerLabel, this.time, sandRectangle, this.timerContainer.getPrefHeight(), timerButtonsContainer, timerResumeLabel, timerBackLabel);
    }
}
