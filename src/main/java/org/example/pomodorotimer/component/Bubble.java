package org.example.pomodorotimer.component;

import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Arrays;
import java.util.stream.Collectors;

// TODO: implement static factory method
public class Bubble {

    private StackPane container;
    private Circle bubble;
    private String bubbleColor;
    private Label label;
    private ScaleTransition st;

    private Bubble(StackPane container, Circle bubble, Label label) {
        this.container = container;
        this.bubble = bubble;
        this.bubbleColor = this.getCSSFill(this.bubble.getStyle()); // so I can revert to old color when bubble is unclicked
        this.label = label;
        this.st = new ScaleTransition(Duration.millis(250), this.container);

        // set event handlers
        this.container.setOnMouseEntered(this::onMouseEnterGrow);
        this.container.setOnMouseExited(this::onMouseLeaveShrink);
        this.bubble.setOnMousePressed(this::onClickedHold);
        this.bubble.setOnMouseReleased(this::onClickedRelease);
    }

    public static Bubble initialize(StackPane container, Circle bubble, Label label) {
        return new Bubble(container, bubble, label);
    }

    private void onMouseEnterGrow(MouseEvent event) {
        st.setFromX(1);
        st.setFromY(1);
        st.setToX(1.1);
        st.setToY(1.1);
        st.play();
    }

    private void onMouseLeaveShrink(MouseEvent event) {
        st.setFromX(1.1);
        st.setFromY(1.1);
        st.setToX(1);
        st.setToY(1);
        st.play();
    }

    private void onClickedHold(MouseEvent event){
        Circle circle = (Circle) event.getSource();
        circle.setFill(Color.web("#4981b5"));
    }

    private void onClickedRelease(MouseEvent event) {
        Circle circle = (Circle) event.getSource();
        circle.setFill(Color.web(this.bubbleColor));
    }

    // Need to filter out fill CSS prop from .getStyle() which returns all CSS props
    private String getCSSFill(String style) {
        return Arrays.stream(style.split(";"))
                .filter(s -> s.startsWith("-fx-fill"))
                .collect(Collectors.joining())
                .split(":")[1].trim();
    }

}
