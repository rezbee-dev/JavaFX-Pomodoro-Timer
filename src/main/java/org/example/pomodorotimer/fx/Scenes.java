package org.example.pomodorotimer.fx;

import java.net.URL;

public enum Scenes {
    HOME("../home.fxml", "Home"),
    TIMER("../timer.fxml", "Timer");

    private final String file;
    private final String scene;

    Scenes(String file, String scene) {
        this.file = file;
        this.scene = scene;
    }

    public URL getPath() {
        return getClass().getResource(this.file);
    }

    public String getScene() {
        return this.scene;
    }
}
