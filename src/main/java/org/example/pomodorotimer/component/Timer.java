package org.example.pomodorotimer.component;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.time.temporal.ChronoUnit;

// src: https://stackoverflow.com/a/67582559
public class Timer {
    private Label timer;
    private int cycles;
    private double containerHeight;
    private Rectangle backgroundSand;
    private ObjectProperty<java.time.Duration> time;
    private DoubleProperty height;

    private Timer(Label timer, int time, Rectangle backgroundSand, double containerHeight) {
        this.timer = timer;
        this.time = new SimpleObjectProperty<>(java.time.Duration.ofSeconds(time));
        this.cycles = (int) this.time.get().getSeconds();
        this.height = new SimpleDoubleProperty();
        this.backgroundSand = backgroundSand;
        this.containerHeight = containerHeight;
        this.setCountdown();
    }

    public static Timer init(Label timer, int time, Rectangle backgroundSand, double containerHeight){
        backgroundSand.setHeight(0);
        return new Timer(timer, time, backgroundSand, containerHeight);
    }

    private void setCountdown() {
        this.timer.textProperty().bind(this.timeBinding());
        this.backgroundSand.heightProperty().bind(this.height);

        Timeline countdown = new Timeline(new KeyFrame(Duration.seconds(1), this::duringCountdownChanges));
        countdown.setCycleCount(this.cycles);
        countdown.play();
    }

    private StringBinding timeBinding() {
        return Bindings.createStringBinding(() ->
                        String.format("%02d:%02d:%02d",
                                this.time.get().toHours(),
                                this.time.get().toMinutesPart(),
                                this.time.get().toSecondsPart()), this.time);
    }

    private void duringCountdownChanges(ActionEvent event){
        java.time.Duration timeDecrement = this.time.get().minus(1, ChronoUnit.SECONDS);
        this.time.setValue(timeDecrement);
        double heightIncrement = this.height.get() + (this.containerHeight/this.cycles);
        System.out.println("height increment:" + heightIncrement);
        this.height.setValue(heightIncrement);
    }
}
