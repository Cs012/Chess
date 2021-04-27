package hu.alkfejl.pieces;

import hu.alkfejl.PieceManager;
import hu.alkfejl.Square;
import javafx.scene.paint.Color;
import hu.alkfejl.Board.SquareState;

public class Pawn extends BasePiece {

    public Pawn(Color sidecolor, String path, PieceManager pm) {
        super(sidecolor, path, pm);
        if (color == Color.WHITE){
            this.movement = new int[]{0, -1, -1};
        }
        else {
            this.movement = new int[]{0, 1, 1};
        }
    }

    @Override
    public void checkMoves() {
        if(active) {
            int currentX = currentSquare.boardPos[0];
            int currentY = currentSquare.boardPos[1];

            //Left
            matchAvailableSquares(currentX - movement[2], currentY + movement[1], SquareState.Enemy);


            //Forward
            if (matchAvailableSquares(currentX, currentY + movement[1], SquareState.Unoccupied) && isFirstMove){
                matchAvailableSquares(currentX, currentY + 2 * movement[1], SquareState.Unoccupied);
            }


            //Right
            matchAvailableSquares(currentX + movement[2], currentY + movement[1], SquareState.Enemy);
        }
    }

    @Override
    public void move() {
        super.move();

        if (currentSquare.boardPos[1] == 1 || currentSquare.boardPos[1] == 8){
            currentSquare.board.promotedHere = currentSquare;
            currentSquare.board.controller.addPieceList(this.color);
        }

    }

    private boolean matchAvailableSquares(int xPos, int yPos, SquareState targetState){
        SquareState squareState = currentSquare.board.validateSquare(xPos, yPos, this);

        if (targetState == squareState){
            availableSquares.add((Square) currentSquare.board.getChildren().get(xPos*9+yPos));
            currentSquare.board.highLightedSquares.add((Square) currentSquare.board.getChildren().get(xPos*9+yPos));
            currentSquare.board.getChildren().get(xPos*9+yPos).setStyle("-fx-background-color: #" + currentSquare.board.highlightColor);
            return true;
        }
        return false;
    }
}
