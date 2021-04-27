package hu.alkfejl.pieces;

import hu.alkfejl.PieceManager;
import javafx.scene.paint.Color;

public class Queen extends BasePiece {
    public Queen(Color sidecolor, String path, PieceManager pm) {
        super(sidecolor, path, pm);
        this.movement = new int[]{7, 7, 7};
    }
}
