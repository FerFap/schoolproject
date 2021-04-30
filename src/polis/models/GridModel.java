package polis.models;

import polis.gamegrid.GameGrid;
import polis.sprites.*;
import polis.ui.Stats;

import java.util.Map;

import static polis.Config.NUM_CELLS;

/**
 * De gridmodel houdt de staat van de map bij en voert daar operaties op.
 */

public class GridModel {

    private final Sprite[][] sprites;
    private final RoadModel roadModel;
    private final SelectionModel selectionModel;
    private final Map<Integer, Factory> map = Map.of(
            1, Residence::new,
            2, Industry::new,
            3, Commerce::new,
            4, Road::new
    );
    private GameGrid gridGame;
    private ButtonModel buttonModel;
    private Sprite currentPhoto;
    private int startx = -1, starty = -1, endx = -1, endy = -1;
    private boolean select = true, something, bull, dragged;
    private Stats stats;

    public GridModel(Sprite[][] sprite) {
        this.sprites = sprite;
        this.dragged = false;
        this.roadModel = new RoadModel(this, this.sprites);
        this.selectionModel = new SelectionModel(this, this.sprites);
    }

    public static boolean validCoords(int x, int y) {
        return (-1 < x && x < NUM_CELLS && -1 < y && y < NUM_CELLS);
    }

    public void setButtonModel(ButtonModel buttonModel) {
        this.buttonModel = buttonModel;
    }

    public void setGrid(GameGrid grid) {
        this.gridGame = grid;
        selectionModel.setGameGrid(grid);
        roadModel.setGameGrid(grid);
        roadModel.setUnremovableRoad();
    }

    public void setStartDrag() {
        startx = gridGame.getR();
        starty = gridGame.getK();
        dragged = true;
    }

    public void setEndDrag() {
        endx = gridGame.getR();
        endy = gridGame.getK();
        dragged = false;
        if (currentPhoto instanceof Road) {
            roadModel.draw(this.startx, this.endx, this.starty, this.endy, 1);
        }
    }

    /**
     * Deze methode wordt gebruikt om 1x1, 2x2 en drags te tekenen met blauwe polygonen als template
     * bij het plaatsen van sprites.
     */

    public void drawSelect() {
        if (currentPhoto != null) {
            if ((currentPhoto.getSize() == 1) && dragged) {
                selectionModel.draw(startx, gridGame.getR(), starty, gridGame.getK(), 1);
            } else {
                int x = gridGame.getR() - (gridGame.getR() + currentPhoto.getSize() - 1) / NUM_CELLS;
                int y = gridGame.getK() - (gridGame.getK() + currentPhoto.getSize() - 1) / NUM_CELLS;
                selectionModel.draw(x, x, y, y, currentPhoto.getSize());
            }
        }
    }

    /**
     * Methode die opgeroepen wordt wanneer een button ingedrukt wordt.
     * @param button
     */
    public void stateChanged(int button) {
        resetValues();
        if (button > -1 && button < 5) {
            currentPhoto = map.get(button).newSprite();
            something = true;
        } else if (button == 5) {
            bull = true;
            select = false;
        }
    }

    /**
     * De logica om het plaatsen van 1x1, 2x2 en dragged sprites
     */
    public void mouseClicked() {
        stats.resetFocus();
        if (something && currentPhoto.getSize() == 1) {
            if (!dragged) {
                roadModel.draw(gridGame.getR(), gridGame.getR(), gridGame.getK(), gridGame.getK(), 1);
            } else {
                setEndDrag();
            }
        } else if (something && currentPhoto.getSize() == 2) {
            int x = gridGame.getR() - (gridGame.getR() + 1) / NUM_CELLS;
            int y = gridGame.getK() - (gridGame.getK() + 1) / NUM_CELLS;
            helper(x, y);
        } else if (sprites[gridGame.getR()][gridGame.getK()] != null && select) {
            stats.setFocused(sprites[gridGame.getR()][gridGame.getK()], gridGame.getR(), gridGame.getK());

        } else if (sprites[gridGame.getR()][gridGame.getK()] != null && bull && (sprites[gridGame.getR()][gridGame.getK()].isRemovable())) {
            if (sprites[gridGame.getR()][gridGame.getK()] instanceof Road) {
                roadModel.checkOut(gridGame.getR(), gridGame.getK());
                roadModel.update(gridGame.getR(), gridGame.getK());
            }
            gridGame.getChildren().remove(sprites[gridGame.getR()][gridGame.getK()]);
            removeSprite(sprites[gridGame.getR()][gridGame.getK()], gridGame.getR(), gridGame.getK());
        }
        drawSelect();
    }

    private void helper(int x, int y) {
        if (sprites[x][y] == null && sprites[x + 1][y] == null && sprites[x][y + 1] == null && sprites[x + 1][y + 1] == null) {
            sprites[x][y] = currentPhoto;
            sprites[x + 1][y] = currentPhoto;
            sprites[x][y + 1] = currentPhoto;
            sprites[x + 1][y + 1] = currentPhoto;
            Point point = GameGrid.convertRowCol(x, y);
            currentPhoto.setCoords(point.getX(), point.getY());
            currentPhoto.setViewOrder(-x - y - 5.0);
            gridGame.getChildren().add(currentPhoto);
            currentPhoto = map.get(buttonModel.getCurrentPressed()).newSprite();
        }
    }

    /**
     * Verwijdert een sprite door in een vierkant rond het punt dat gedelete moet worden te kijken.
     * @param sprite
     * @param x
     * @param y
     */

    private void removeSprite(Sprite sprite, int x, int y) {
        for (int i = x - sprite.getSize() + 1; i <= x + sprite.getSize() - 1; i++) {
            for (int j = y - sprite.getSize() + 1; j <= y + sprite.getSize() - 1; j++) {
                if (validCoords(i, j) && sprites[i][j] != null && sprites[i][j].equals(sprite)) {
                    sprites[i][j] = null;
                }
            }
        }
        if (sprite instanceof Residence) {
            ((Residence) sprite).deleteResidents();
        }
    }

    private void resetValues() {
        this.startx = -1;
        this.starty = -1;
        this.endx = -1;
        this.endy = -1;
        this.select = true;
        this.bull = false;
        this.something = false;
        this.dragged = false;
        currentPhoto = null;
        selectionModel.removeAllSelections();
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }
}
