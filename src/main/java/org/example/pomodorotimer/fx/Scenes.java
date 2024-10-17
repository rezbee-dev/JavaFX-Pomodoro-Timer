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
//        System.out.println(getClass().getResource(this.file));
//        return getClass().getResource(this.file);
//        System.out.println("Scenes.java classloader path: " + this.getClass().getClassLoader().getResource("").getPath());
        System.out.println("fxml path: " + Scenes.class.getResource(this.file));
        return Scenes.class.getResource(this.file);
    }

    public String getScene() {
        return this.scene;
    }
}
