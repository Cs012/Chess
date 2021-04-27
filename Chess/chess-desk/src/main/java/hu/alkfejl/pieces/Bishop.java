package hu.alkfejl.pieces;

import hu.alkfejl.PieceManager;
import javafx.scene.paint.Color;

public class Bishop extends BasePiece {
    public Bishop(Color sidecolor, String path, PieceManager pm) {
        super(sidecolor, path, pm);
        this.movement = new int[]{0, 0, 7};
    }
}
