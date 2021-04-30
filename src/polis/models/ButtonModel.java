package polis.models;

import javafx.scene.paint.Color;
import polis.gamegrid.GameGrid;
import polis.simulation.Region;
import polis.ui.UiButton;

/**
 * De buttonModel wordt gebruikt om bij te houden welke knop ingedrukt is, en roept dan ook de functies
 * die aangeroepen moeten worden in de andere models.
 */

public class ButtonModel {

    private final GameGrid gameGrid;
    private int currentPressed;
    private GridModel gridModel;
    private UiButton[] buttons;
    private Region region;

    public ButtonModel(GameGrid map) {
        this.gameGrid = map;
        currentPressed = 1;
    }

    public int getCurrentPressed() {
        return currentPressed;
    }

    /**
     * Zet de currentPressed op de juiste button en roep de methoden aan van de regio en gamegrid als het nodig is.
     * @param currentPressed
     */

    public void setCurrentPressed(int currentPressed) {
        this.buttons[this.currentPressed - 1].setSelected(false);
        this.currentPressed = currentPressed;
        this.buttons[this.currentPressed - 1].setSelected(true);

        if (-1 < currentPressed && currentPressed < 5) {
            gameGrid.setPolyColor(Color.TRANSPARENT);
        } else if (currentPressed == 5) {
            gameGrid.setPolyColor(Color.RED);
        } else if (currentPressed == 6) {
            gameGrid.setPolyColor(Color.WHITE);
        } else if (currentPressed == 7) {
            region.setRunning();
        }

        gridModel.stateChanged(this.currentPressed);
    }

    public void setGridModel(GridModel gridModel) {
        this.gridModel = gridModel;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    public void addButtons(UiButton... buttons) {
        this.buttons = buttons;
    }
}
