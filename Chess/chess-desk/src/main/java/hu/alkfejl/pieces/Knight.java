package hu.alkfejl.pieces;

import hu.alkfejl.Board;
import hu.alkfejl.PieceManager;
import hu.alkfejl.Square;
import javafx.scene.paint.Color;

public class Knight extends BasePiece {
    public Knight(Color sidecolor, String path, PieceManager pm) {
        super(sidecolor, path, pm);
    }

    @Override
    public void checkMoves() {
        if(active) {
            int currentX = currentSquare.boardPos[0];
            int currentY = currentSquare.boardPos[1];

            getAvailableSquares(currentX - 2, currentY - 1, 1);// -1 -1
            getAvailableSquares(currentX - 2, currentY + 1, 1);// -1 1

            getAvailableSquares(currentX + 2, currentY - 1, 1);// 3 -1
            getAvailableSquares(currentX + 2, currentY + 1, 1);// 3 1

            getAvailableSquares(currentX - 1, currentY - 2, 1);// 0 -2
            getAvailableSquares(currentX - 1, currentY + 2, 1);// 0 2

            getAvailableSquares(currentX + 1, currentY - 2, 1);// 2 -2
            getAvailableSquares(currentX + 1, currentY + 2, 1);// 2 2
        }
    }

    @Override
    protected void getAvailableSquares(int xDir, int yDir, int moves) {
        Board.SquareState squareState = currentSquare.board.validateSquare(xDir, yDir, this);

        if(squareState == Board.SquareState.Enemy || squareState == Board.SquareState.Unoccupied){
            availableSquares.add((Square) currentSquare.board.getChildren().get(xDir*9+yDir));
            currentSquare.board.highLightedSquares.add((Square) currentSquare.board.getChildren().get(xDir*9+yDir));
            currentSquare.board.getChildren().get(xDir*9+yDir).setStyle("-fx-background-color: #" + currentSquare.board.highlightColor);
        }
    }
}
