package hu.alkfejl.pieces;

import hu.alkfejl.DisplaySquare;
import hu.alkfejl.PieceManager;
import hu.alkfejl.Square;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import hu.alkfejl.Board.SquareState;

import java.util.ArrayList;
import java.util.List;

public class BasePiece{

    public Color color;
    public boolean active;
    public boolean isFirstMove = true;
    protected ImageView image;
    protected PieceManager pM;
    protected Square currentSquare;
    protected Square targetSquare;
    protected int[] movement;
    protected List<Square> availableSquares = new ArrayList<>();

    public BasePiece(Color sidecolor, String path, PieceManager pm)  {

        this.image = setImage(path);

        this.pM = pm;
        this.color = sidecolor;
    }

    public void place(Square sq){

        sq.getChildren().add(this.image);
        currentSquare = sq;
        currentSquare.currentPiece = this;
    }

    public void place(DisplaySquare dp){
        dp.getChildren().add(this.image);
        currentSquare = null;
        dp.currentPiece = this;
    }

    public void checkMoves(){
        if(active){
            getAvailableSquares(1, 0, movement[0]);
            getAvailableSquares(-1, 0, movement[0]);

            getAvailableSquares(0, 1, movement[1]);
            getAvailableSquares(0, -1, movement[1]);

            getAvailableSquares(1, 1, movement[2]);
            getAvailableSquares(-1, 1, movement[2]);

            getAvailableSquares(-1, -1, movement[2]);
            getAvailableSquares(1, -1, movement[2]);
        }
    }

    public void move(){

        if (targetSquare.currentPiece != null){
            targetSquare.currentPiece.kill();
        }

        if (currentSquare != null){
            currentSquare.currentPiece = null;
        }

        currentSquare = targetSquare;
        currentSquare.currentPiece = this;
        place(currentSquare);
        targetSquare = null;

        currentSquare.board.deleteHighlightedSquares();
        availableSquares.clear();

        isFirstMove = false;

        for (Button b : currentSquare.board.controller.drawButtons){
            b.setDisable(false);
        }
        currentSquare.board.controller.drawRequest = false;

        pM.switchSides(this.color);
    }

    protected void getAvailableSquares(int xDir, int yDir, int moves){
        int currentX = currentSquare.boardPos[0];
        int currentY = currentSquare.boardPos[1];

        SquareState squareState;

        for (int i = 1; i <= moves; i++){
            currentX += xDir;
            currentY += yDir;
            squareState = currentSquare.board.validateSquare(currentX, currentY, this);

            if(squareState == SquareState.Enemy || squareState == SquareState.Unoccupied){
                availableSquares.add((Square) currentSquare.board.getChildren().get(currentX*9+currentY));
                currentSquare.board.highLightedSquares.add((Square) currentSquare.board.getChildren().get(currentX*9+currentY));
                currentSquare.board.getChildren().get(currentX*9+currentY).setStyle("-fx-background-color: #" + currentSquare.board.highlightColor);
            }

            if(squareState != SquareState.Unoccupied){
                break;
            }

        }

    }

    protected void kill(){
        if (color == Color.BLACK){
            pM.blackPieces.remove(this);
        }else {
            pM.whitePieces.remove(this);
        }
        currentSquare.currentPiece = null;
        currentSquare.getChildren().remove(1);
        currentSquare.board.deleteHighlightedSquares();
    }

    private ImageView setImage(String path){
        ImageView imV = new ImageView();
        imV.setImage(new Image("File:src\\main\\resources\\hu\\alkfejl\\Pieces\\" + path));
        imV.setFitHeight(50);
        imV.setFitWidth(50);
        return imV;
    }



    public PieceManager getpM() {
        return pM;
    }

    public void setpM(PieceManager pM) {
        this.pM = pM;
    }

    public Square getCurrentSquare() {
        return currentSquare;
    }

    public void setCurrentSquare(Square currentSquare) {
        this.currentSquare = currentSquare;
    }

    public Square getTargetSquare() {
        return targetSquare;
    }

    public void setTargetSquare(Square targetSquare) {
        this.targetSquare = targetSquare;
    }

    public List<Square> getAvailableSquares() {
        return availableSquares;
    }

    public void setAvailableSquares(List<Square> availableSquares) {
        this.availableSquares = availableSquares;
    }
}
