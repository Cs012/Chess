package hu.alkfejl;

import hu.alkfejl.pieces.BasePiece;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Board extends GridPane {

    public List<Square> highLightedSquares = new ArrayList<>();
    public BasePiece selectedPiece;
    public final String highlightColor = "E22222";
    public Square promotedHere;
    public Controller controller;


    public enum SquareState
    {
        Friendly,
        Enemy,
        Unoccupied,
        OutOfBounds,
    }

    public Board(Controller controller) {
        createSquares(9,9);
        colorSquares(9,9);

        this.setAlignment(Pos.CENTER);
        this.controller = controller;
    }

    public SquareState validateSquare(int xPos, int yPos, BasePiece piece){
        int pos = xPos*9+yPos;
        if (xPos < 1 || xPos > 8 || yPos < 1 || yPos > 8 || pos%9 == 0){
            return SquareState.OutOfBounds;
        }


        Square target = (Square) this.getChildren().get(pos);
        if (target.currentPiece != null){
            if(piece.color == target.currentPiece.color){
                return SquareState.Friendly;
            }
            else{
                return SquareState.Enemy;
            }
        }
        return SquareState.Unoccupied;
    }

    public void deleteHighlightedSquares(){
        for (Square sq : highLightedSquares) {
            sq.setStyle("-fx-background-color: " + sq.baseColor);
        }
        highLightedSquares.clear();
        selectedPiece = null;
    }


    //create board

    private void createSquares(int iTo, int jTo){

        for(int i=0;i<iTo;i++){
            for(int j=0;j<jTo;j++){

                Square square = new Square(new int[]{i, j}, this);
                Label label = new Label();

                if(i != 0 && j != 0){
                    square.setPrefSize(50, 50);
                    square.setId(i + "_" + j);
                    square.setBorder(new Border(new BorderStroke(Color.valueOf("#3c823c"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
                    //label.setText(square.getId());
                }
                else if(i == 0 && j != 0){
                    square.setPrefSize(10, 50);
                    switch (j){
                        case 1:
                            label.setText("1");
                            break;
                        case 2:
                            label.setText("2");
                            break;
                        case 3:
                            label.setText("3");
                            break;
                        case 4:
                            label.setText("4");
                            break;
                        case 5:
                            label.setText("5");
                            break;
                        case 6:
                            label.setText("6");
                            break;
                        case 7:
                            label.setText("7");
                            break;
                        case 8:
                            label.setText("8");
                            break;
                    }
                }
                else if(i != 0){
                    square.setPrefSize(50, 10);
                    switch (i){
                        case 1:
                            label.setText("A");
                            break;
                        case 2:
                            label.setText("B");
                            break;
                        case 3:
                            label.setText("C");
                            break;
                        case 4:
                            label.setText("D");
                            break;
                        case 5:
                            label.setText("E");
                            break;
                        case 6:
                            label.setText("F");
                            break;
                        case 7:
                            label.setText("G");
                            break;
                        case 8:
                            label.setText("H");
                            break;
                    }
                }

                square.getChildren().add(label);
                this.add(square, i, j);
            }
        }

    }

    private void colorSquares(int iTo, int jTo){
        for (int i = 0; i < iTo; i+=2)
        {
            for (int j = 0; j < jTo; j++)
            {
                if(i==0 || j==0)
                    continue;
                int offset = (j % 2 != 0) ? 1 : 0;
                Square actual = (Square) this.getChildren().toArray()[j * jTo + offset+i-1];
                actual.setStyle("-fx-background-color: #3c823c");
                actual.baseColor = "#3c823c";
            }
        }
        for (int i = 0; i < iTo; i+=2)
        {
            for (int j = 0; j < jTo; j++)
            {
                if(i==0 || j==0)
                    continue;
                int offset = (j % 2 != 0) ? 0 : 1;
                Square actual = (Square) this.getChildren().toArray()[j * jTo + offset+i-1];
                actual.setStyle("-fx-background-color: #ffffc8");
                actual.baseColor = "#ffffc8";
            }
        }
    }

}
