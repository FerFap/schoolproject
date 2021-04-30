package polis.ui;

import javafx.animation.AnimationTimer;
import javafx.scene.control.Label;

// Deze implementatie komt volledig van stackoverflow
// https://stackoverflow.com/a/28287949

public class FPSCounter extends Label {

    private final long[] frameTimes = new long[100];
    private int frameTimeIndex = 0;
    private boolean arrayFilled = false;

    public FPSCounter() {
        AnimationTimer frameRateMeter = new AnimationTimer() {
            @Override
            public void handle(long now) {
                long oldFrameTime = frameTimes[frameTimeIndex];
                frameTimes[frameTimeIndex] = now;
                frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length;
                if (frameTimeIndex == 0) {
                    arrayFilled = true;
                }
                if (arrayFilled) {
                    long elapsedNanos = now - oldFrameTime;
                    long elapsedNanosPerFrame = elapsedNanos / frameTimes.length;
                    double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame;
                    setText(String.format("%.0f FPS", frameRate));
                }
            }
        };
        frameRateMeter.start();
        setMouseTransparent(true);
    }

}
