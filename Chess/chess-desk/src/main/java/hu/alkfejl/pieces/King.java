package hu.alkfejl.pieces;

import hu.alkfejl.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class King extends BasePiece {

    public List<Square> castleSquares = new ArrayList<>();

    public King(Color sidecolor, String path, PieceManager pm) {
        super(sidecolor, path, pm);
        this.movement = new int[]{1, 1, 1};
    }

    public void castle(Rook rook){
            this.targetSquare = rook.castleSquareKing;
            this.move();
            rook.targetSquare = rook.castleSquareRook;
            rook.move();
    }

    public void checkMoves(Rook rook) {
        if (active){
            super.checkMoves();

            int currentX = currentSquare.boardPos[0];
            int currentY = currentSquare.boardPos[1];


            if (currentSquare.board.validateSquare(currentX + 1, currentY, this) == Board.SquareState.Unoccupied
                    && rook != null && rook.isFirstMove && isFirstMove)
                matchAvailableSquares(currentX + 2, currentY, Board.SquareState.Unoccupied);
            if (currentSquare.board.validateSquare(currentX - 1, currentY, this) == Board.SquareState.Unoccupied
                    && rook != null && rook.isFirstMove &&isFirstMove)
                matchAvailableSquares(currentX - 2, currentY, Board.SquareState.Unoccupied);
        }

    }

    private void matchAvailableSquares(int xPos, int yPos, Board.SquareState targetState){
        Board.SquareState squareState = currentSquare.board.validateSquare(xPos, yPos, this);

        if (targetState == squareState){
            castleSquares.add((Square) currentSquare.board.getChildren().get(xPos*9+yPos));
            currentSquare.board.highLightedSquares.add((Square) currentSquare.board.getChildren().get(xPos*9+yPos));
            currentSquare.board.getChildren().get(xPos*9+yPos).setStyle("-fx-background-color: #" + currentSquare.board.highlightColor);
        }
    }

    @Override
    protected void kill() {
        super.kill();

        Controller.winnerName = (color == Color.WHITE ? Controller.player1Name : Controller.player2Name) +  " won!";
        App.setRoot("end.fxml");
    }
}
