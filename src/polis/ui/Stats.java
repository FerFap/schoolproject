package polis.ui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import polis.sprites.Commerce;
import polis.sprites.Industry;
import polis.sprites.Residence;
import polis.sprites.Sprite;

import java.util.HashSet;
import java.util.Set;

import static polis.Config.*;

/**
 * De statistieken van de simulatie.
 */

public class Stats extends Pane {

    private final Label label = new Label();
    private final Label title;
    private final Sprite[][] sprites;
    private boolean focused = false;
    private Sprite focus;
    private int focusR;
    private int focusK;

    public Stats(Sprite[][] sprites) {
        this.sprites = sprites;
        setPrefWidth(STATS_WIDTH);
        setPrefHeight(STATS_HEIGHT);
        this.title = new Label();
        this.title.setTranslateX(25);
        this.title.setTranslateY(10);
        this.title.setText("Statistieken");
        this.title.setFont(new Font(30));
        this.label.setFont(new Font(20));
        this.label.layoutXProperty().bind(widthProperty().subtract(this.label.widthProperty()).divide(2));
        this.label.layoutYProperty().bind(heightProperty().subtract(this.label.heightProperty()).divide(2));
        getChildren().addAll(this.title, this.label);
        setPickOnBounds(false);
        setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        setMouseTransparent(true);
        setStyle("-fx-border-color: black");
    }

    public void setText(String string) {
        this.label.setText(string);
    }

    public void setTitle(String string) {
        this.title.setText(string);
    }

    public void setFocused(Sprite sprite, int r, int k) {
        this.focus = sprite;
        this.focusK = k;
        this.focusR = r;
        focused = true;
    }

    public void resetFocus() {
        focused = false;
    }

    public void update() {
        if (!focused) {
            updateStats();
        } else {
            updateIndividualStats();
        }
    }

    private void updateIndividualStats() {
        setText(focus.getInfo());
        setTitle(focus.getTitle(focusR, focusK));
    }

    /**
     * Update de stats door ze eerst te berekenen.
     */

    private void updateStats() {
        double workers = 0;
        double jobs = 0;
        double residents = 0;
        double residentSpace = 0;
        double goods = 0;
        double goodsStorage = 0;
        double customersAvailable = 0;
        double customers = 0;

        Set<Sprite> visited = new HashSet<>();

        for (int i = 0; i < NUM_CELLS; i++) {
            for (int j = 0; j < NUM_CELLS; j++) {
                if (sprites[i][j] instanceof Residence && !visited.contains(sprites[i][j])) {
                    Residence residence = (Residence) sprites[i][j];
                    residents += residence.getNumResidents();
                    residentSpace += residence.getCapacity();
                    residence.checkForInactive();
                    visited.add(residence);
                }
                if (sprites[i][j] instanceof Commerce && !visited.contains(sprites[i][j])) {
                    Commerce commerce = (Commerce) sprites[i][j];
                    goods += commerce.getGoods();
                    goodsStorage += commerce.getGoodsCapacity();
                    jobs += commerce.getJobCapacity();
                    customersAvailable += commerce.getClients();
                    customers += commerce.getClientCapacity();
                    workers += commerce.getNumWorkers();
                    commerce.checkForInactive();
                    visited.add(commerce);
                }

                if (sprites[i][j] instanceof Industry && !visited.contains(sprites[i][j])) {
                    Industry industry = (Industry) sprites[i][j];
                    workers += industry.getNumWorkers();
                    jobs += industry.getJobCapacity();
                    industry.checkForInactive();
                    visited.add(industry);
                }
            }
        }
        setText(String.format("Jobs: %.1f / %.1f\nResidents: %.1f / %.1f\nClients: %.1f / %.1f\nGoods: %.1f / %.1f", workers, jobs, residents, residentSpace, customersAvailable, customers, goods, goodsStorage));
        setTitle("Statistics");
    }

}
