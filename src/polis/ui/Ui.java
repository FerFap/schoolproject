package polis.ui;

import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import polis.models.ButtonModel;
import polis.sprites.Sprite;

import java.util.Map;

/**
 * Abstracte representatie van de ui.
 */
public class Ui extends AnchorPane {

    private final Map<String, UiButton> map;
    private final Stats stats;

    public Ui(ButtonModel buttonModel, Sprite[][] sprites) {
        HBox hbox1 = new HBox();
        hbox1.setSpacing(10);

        HBox hbox2 = new HBox();
        hbox2.setSpacing(10);

        VBox vbox = new VBox();
        vbox.setSpacing(10);

        Label label = new FPSCounter();
        UiButton residence = new SingleStateButton("polis/buttons/residence.png", buttonModel, 1);
        UiButton industry = new SingleStateButton("polis/buttons/industry.png", buttonModel, 2);
        UiButton commerce = new SingleStateButton("polis/buttons/commerce.png", buttonModel, 3);
        UiButton road = new SingleStateButton("polis/buttons/road.png", buttonModel, 4);
        UiButton buldozer = new SingleStateButton("polis/buttons/bulldozer.png", buttonModel, 5);
        UiButton selection = new SingleStateButton("polis/buttons/selection.png", buttonModel, 6);
        UiButton playPause = new DoubleStateButton("polis/buttons/play.png", "polis/buttons/pause.png", buttonModel, 7);
        buttonModel.addButtons(residence, industry, commerce, road, buldozer, selection, playPause);
        map = Map.of("R", residence,
                "I", industry,
                "C", commerce,
                "S", road,
                "B", buldozer,
                "ESCAPE", selection,
                "SPACE", playPause
        );

        hbox1.getChildren().addAll(residence, industry, commerce);
        hbox2.getChildren().addAll(road, buldozer);
        vbox.getChildren().addAll(hbox1, hbox2, selection, label);

        this.stats = new Stats(sprites);
        setTopAnchor(vbox, 10.0);
        setLeftAnchor(vbox, 10.0);
        setBottomAnchor(playPause, 10.0);
        setLeftAnchor(playPause, 10.0);
        setBottomAnchor(stats, 10.0);
        setRightAnchor(stats, 10.0);
        getChildren().addAll(vbox, playPause, stats);
    }

    public Stats getStats() {
        return stats;
    }

    public void checkForValidKeyEvents(KeyEvent keyEvent) {
        if (map.containsKey(keyEvent.getCode().toString())) {
            map.get(keyEvent.getCode().toString()).buttonPressed();
        }
    }
}
