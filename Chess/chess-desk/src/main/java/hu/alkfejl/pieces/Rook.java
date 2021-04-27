package hu.alkfejl.pieces;

import hu.alkfejl.PieceManager;
import hu.alkfejl.Square;
import javafx.scene.paint.Color;


public class Rook extends BasePiece {

    public Square castleSquareRook;
    public Square castleSquareKing;

    public Rook(Color sidecolor, String path, PieceManager pm) {
        super(sidecolor, path, pm);
        this.movement = new int[]{7, 7, 0};
    }

    @Override
    public void place(Square sq) {
        super.place(sq);

        if (sq.boardPos[0] > 4){
            this.castleSquareKing = getSquare(-1);
            this.castleSquareRook = getSquare(-2);
        }
        else{
            this.castleSquareKing = getSquare(2);
            this.castleSquareRook = getSquare(3);
        }

    }

    private Square getSquare(int offset){
        int pos = (currentSquare.boardPos[0]+offset) * 9 + currentSquare.boardPos[1];
        return (Square) currentSquare.board.getChildren().get(pos);
    }

}
