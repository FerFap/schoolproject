package polis.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import polis.models.ButtonModel;

/**
 * Eigen implementatie van een button voor de ui
 */
public class SingleStateButton extends UiButton {


    public SingleStateButton(String picture, ButtonModel buttonModel, int value) {
        this.buttonModel = buttonModel;
        setGraphic(new ImageView(new Image(picture)));
        setFocusTraversable(false);
        setOnMouseClicked(e ->
                buttonPressed()
        );
        this.value = value;
    }

    public void buttonPressed() {
        buttonModel.setCurrentPressed(this.value);
    }
}
