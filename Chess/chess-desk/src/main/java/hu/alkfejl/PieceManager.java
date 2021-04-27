package hu.alkfejl;

import hu.alkfejl.pieces.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class PieceManager {

    public List<BasePiece> whitePieces;
    public List<BasePiece> blackPieces;
    private final String[] pieceOrder =
    {
            "P",  "P", "P", "P", "P", "P", "P", "P",
            "R", "KN", "B", "Q", "K", "B", "KN", "R"
    };

    public PieceManager(Board board){
        whitePieces = createPieces(Color.WHITE);
        blackPieces = createPieces(Color.BLACK);

        placePieces(Color.WHITE, whitePieces, board);
        placePieces(Color.BLACK, blackPieces, board);

        activateSide(whitePieces, true);
        activateSide(blackPieces, false);
    }

    private void activateSide(List<BasePiece> p, boolean a){
        for (BasePiece bp: p) {
            bp.active = a;
        }
    }

    public void switchSides(Color color){
        if (color == Color.WHITE){
            activateSide(blackPieces, true);
            activateSide(whitePieces, false);
        }
        else {
            activateSide(blackPieces, false);
            activateSide(whitePieces, true);
        }
    }

    //setup board
    private List<BasePiece> createPieces(Color color){
        List<BasePiece> toRet = new ArrayList<>();
        for (String s : pieceOrder) {
            BasePiece p;
            switch (s) {
                case "R":
                    p = new Rook(color,  (color==Color.WHITE? "white" : "black") + "_rook.png", this);
                    break;
                case "KN":
                    p = new Knight(color,  (color==Color.WHITE? "white" : "black") + "_knight.png", this);
                    break;
                case "B":
                    p = new Bishop(color,  (color==Color.WHITE? "white" : "black") + "_bishop.png", this);
                    break;
                case "K":
                    p = new King(color,  (color==Color.WHITE? "white" : "black") + "_king.png", this);
                    break;
                case "Q":
                    p = new Queen(color,  (color==Color.WHITE? "white" : "black") + "_queen.png", this);
                    break;
                default:
                    p = new Pawn(color,  (color==Color.WHITE? "white" : "black") + "_pawn.png", this);
                    break;
            }
            toRet.add(p);
        }
        return toRet;
    }

    private void placePieces(Color color, List<BasePiece> pieces, Board board){
        for(int i = 2; i <= 9; i++)
        {
            if (color == Color.WHITE){
                pieces.get(i-2).place((Square) board.getChildren().get((i*9)-2)); //pawn
                pieces.get(i+8-2).place((Square) board.getChildren().get((i*9)-1)); //other
            }
            else{
                pieces.get(i-2).place((Square) board.getChildren().get((i*9)-7)); //pawn
                pieces.get(i+8-2).place((Square) board.getChildren().get((i*9)-8)); //other
            }
        }
    }
}
