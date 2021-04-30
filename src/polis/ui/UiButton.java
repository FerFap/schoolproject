package polis.ui;

import javafx.scene.control.ToggleButton;
import polis.models.ButtonModel;

/**
 * Abstract representatie van een button
 */
public abstract class UiButton extends ToggleButton {

    protected int value;
    protected ButtonModel buttonModel;

    public abstract void buttonPressed();

}
