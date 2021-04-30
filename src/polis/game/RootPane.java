package polis.game;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import polis.gamegrid.GameGrid;
import polis.models.ButtonModel;
import polis.models.GridModel;
import polis.simulation.Region;
import polis.simulation.Simulation;
import polis.sprites.Sprite;
import polis.ui.Ui;
import prog2.util.Viewport;

import static polis.Config.*;

/**
 * In deze klasse worden alles aangemaakt voor het project dat nodig is voor het project.
 */

public class RootPane extends StackPane {

    Ui ui;
    GameGrid map;

    public RootPane() {

        Sprite[][] sprites = new Sprite[NUM_CELLS][NUM_CELLS];
        GridModel gridModel = new GridModel(sprites);
        this.map = new GameGrid(gridModel);
        ButtonModel buttonModel = new ButtonModel(this.map);
        this.ui = new Ui(buttonModel, sprites);
        Pane mapping = new Viewport(this.map, ZOOM);

        Simulation simulation = new Simulation(this.map, sprites);
        Region region = new Region(simulation);
        simulation.setRegion(region);
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(SIMULATION_SPEED), e -> {
            region.sendImmigrant();
            this.ui.getStats().update();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.playFromStart();

        gridModel.setStats(this.ui.getStats());
        buttonModel.setRegion(region);
        gridModel.setGrid(map);
        gridModel.setButtonModel(buttonModel);
        buttonModel.setGridModel(gridModel);
        getChildren().addAll(mapping, this.ui);
        turnOffPickOnBoundsFor(this.ui);
        setOnKeyPressed(this::catchKeyAndMouseEvents);
        setStyle(COLOR_BACKGROUND);
    }

    /**
     * Vangt key en mouse events en geeft ze door aan de map en ui.
     * @param keyEvent
     */

    private void catchKeyAndMouseEvents(KeyEvent keyEvent) {

        ui.checkForValidKeyEvents(keyEvent);
        map.requestFocus();
    }

    /**
     * Komt van stackoverflow -> het voert setPickOnBounds(false) voor alle children van mijn Ui.
     * https://stackoverflow.com/a/16879652
     * @param n
     */

    private void turnOffPickOnBoundsFor(Node n) {

        n.setPickOnBounds(false);
        if (n instanceof Parent) {
            for (Node c : ((Parent) n).getChildrenUnmodifiable()) {
                turnOffPickOnBoundsFor(c);
            }
        }
    }
}
