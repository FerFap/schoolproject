package polis;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import polis.game.RootPane;

import static polis.Config.HEIGHT;
import static polis.Config.WIDTH;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(new RootPane(), WIDTH, HEIGHT);
        stage.setScene(scene);
        stage.setTitle("Ferhat Akbulut - Polis - 2021 © Universiteit Gent");
        stage.show();
    }
}
