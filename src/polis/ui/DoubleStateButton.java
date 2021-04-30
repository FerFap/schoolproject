package polis.ui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import polis.models.ButtonModel;

/**
 * Abstracte representatie voor een button dat van foto moet veranderen (play button).
 */
public class DoubleStateButton extends UiButton {

    private final ImageView firstPicture;
    private final ImageView secondPicture;
    private boolean pressed = false;

    public DoubleStateButton(String picture1, String picture2, ButtonModel buttonModel, int value) {
        this.buttonModel = buttonModel;
        firstPicture = new ImageView(new Image(picture1));
        secondPicture = new ImageView(new Image(picture2));
        setGraphic(firstPicture);
        setOnMouseClicked(e ->
                buttonPressed()
        );
        setFocusTraversable(false);
        this.value = value;
    }

    public void buttonPressed() {
        if (pressed) {
            setGraphic(firstPicture);
        } else {
            setGraphic(secondPicture);
        }
        pressed = !pressed;
        buttonModel.setCurrentPressed(this.value);
    }
}
