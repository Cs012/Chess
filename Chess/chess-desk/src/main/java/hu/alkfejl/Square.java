package hu.alkfejl;

import hu.alkfejl.pieces.BasePiece;
import hu.alkfejl.pieces.King;
import hu.alkfejl.pieces.Rook;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.util.List;

public class Square extends GridPane {
    public int[] boardPos;
    public Board board;
    public BasePiece currentPiece = null;
    public String baseColor;

    public Square(int[] pos, Board b){
        this.boardPos = pos;
        this.board = b;
        this.addEventFilter(MouseEvent.MOUSE_PRESSED, squareClick);
    }

    private boolean canCastle(Rook rook){

        if (rook.getCurrentSquare().boardPos[0] < 4){
            for (int i = 1; i <= 3; i++){
                if (board.validateSquare(boardPos[0]-i, boardPos[1], currentPiece) != Board.SquareState.Unoccupied){
                    return false;
                }
            }
        }
        else{
            for (int i = 1; i <= 2; i++){
                if (board.validateSquare(boardPos[0]+i, boardPos[1], currentPiece) != Board.SquareState.Unoccupied){
                    return false;
                }
            }
        }

        return true;
    }


    private Rook getRookForCastle(boolean isThisKingSquare){
        List<BasePiece> pieces;
        if (board.selectedPiece.color == Color.WHITE){
            pieces = board.selectedPiece.getpM().whitePieces;
        }
        else{
            pieces = board.selectedPiece.getpM().blackPieces;
        }

        for (BasePiece p : pieces){
            if (p.getClass() == Rook.class){
                if ((((Rook) p).castleSquareKing == board.getChildren().get(isThisKingSquare ? ((boardPos[0]+2)*9+boardPos[1]) : (boardPos[0])*9+boardPos[1])
                        || ((Rook) p).castleSquareKing == board.getChildren().get(isThisKingSquare ? ((boardPos[0]-2)*9+boardPos[1]) : (boardPos[0])*9+boardPos[1])
                        && canCastle(((Rook) p)))){

                    return (Rook) p;
                }
            }
        }
        return null;
    }

    EventHandler<MouseEvent> squareClick = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent mouseEvent) {
            //this square
            Square thS = (Square) mouseEvent.getSource();

            //something selected
            if(board.selectedPiece != null){
                //selected moves
                if (board.selectedPiece.getAvailableSquares().contains(thS)){
                    board.selectedPiece.setTargetSquare(thS);
                    board.selectedPiece.move();
                    return;
                }

                //selected is king that can castle
                if (board.selectedPiece.getClass() == King.class && ((King) board.selectedPiece).castleSquares.contains(thS)){
                    ((King)board.selectedPiece).castle(getRookForCastle(false));
                }
            }

            //nothing selected
            else {
                //it has a piece
                if (thS.currentPiece != null){
                    //it is on the active side
                    if (thS.currentPiece.active){

                        //it is a king
                        if (thS.currentPiece.getClass() == King.class){
                            thS.setStyle("-fx-background-color: #" + board.highlightColor);
                            board.highLightedSquares.add(thS);
                            board.selectedPiece = thS.currentPiece;
                            ((King) currentPiece).checkMoves(getRookForCastle(true));
                        }

                        thS.setStyle("-fx-background-color: #" + board.highlightColor);
                        board.highLightedSquares.add(thS);
                        board.selectedPiece = thS.currentPiece;
                        currentPiece.checkMoves();
                        return;
                    }
                }
            }
            board.deleteHighlightedSquares();
        }
    };
}
