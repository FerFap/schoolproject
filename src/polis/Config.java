package polis;

import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

/**
 * Alle constanten om het veranderen van de simulatie gemakkelijk te maken.
 */
public final class Config {
    public static final int CELL_SIZE = 64; // Is constant
    public static final int NUM_CELLS = 32; // Mag veranderen
    public static final int SIZE = 1; // Mag veranderen
    public static final Paint COLOR_GRID = Paint.valueOf("rgb(204, 249, 170)"); // Mag veranderen
    public static final String COLOR_BACKGROUND = "-fx-background-color: #ADD8E6"; // Mag veranderen
    public static final Paint COLOR_SELECT_BLUE = Paint.valueOf("rgba(0, 127, 255, 0.5)"); // Mag veranderen
    public static final Paint COLOR_SELECT_RED = Paint.valueOf("rgba(255, 0, 0, 0.5)"); // Mag veranderen
    public static final int WIDTH = 1280; // Mag veranderen
    public static final int HEIGHT = 720; // Mag veranderen
    public static final double ZOOM = 0.5; // Mag veranderen
    public static final int STATS_WIDTH = WIDTH / 3;
    public static final int STATS_HEIGHT = HEIGHT / 3;
    public static final double SIMULATION_SPEED = 0.0625;
    public static final Color IMMIGRANT_COLOR = Color.GREY;
    public static final Color JOB_SEEKER_COLOR = Color.PERU;
    public static final Color SHOPPER_COLOR = Color.LIGHTBLUE;
    public static final Color GOODS_COLOR = Color.YELLOW;
    public static final Color COLOR_STATIONARY = Color.TRANSPARENT;
}
