package hu.alkfejl;

import hu.alkfejl.dao.GamesDAO;
import hu.alkfejl.model.Game;
import hu.alkfejl.pieces.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Controller {

    private final VBox Base = new VBox();
    private final Board chessBoard = new Board(this);
    private final PieceManager pM = new PieceManager(chessBoard);

    public static String winnerName;
    public static String player1Name;
    public static String player2Name;

    public boolean drawRequest = false;
    public List<Button> drawButtons = new ArrayList<>();


    @FXML
    private TextField player_1;
    @FXML
    private TextField player_2;
    @FXML
    private Label winner;


    @FXML
    private void switchToGame() {
        setupGame();

        App.setRoot(Base);
    }

    @FXML
    public void listGames(){
        App.setRoot("games.fxml");
    }

    @FXML
    private void switchToMenu(){
        App.setRoot("main.fxml");
    }

    @FXML
    public void initialize() {
        //end game
        if (winner != null){
            winner.setText(winnerName);

            Game game = new Game();
            game.setPlayer1(player1Name.equals("Black") ? "anonym" : player1Name);
            game.setPlayer2(player2Name.equals("White") ? "anonym" : player2Name);
            if (winnerName.startsWith(player2Name))
                game.setScore("1-0");
            else if (winnerName.startsWith(player1Name))
                game.setScore("0-1");
            else
                game.setScore("0-0");

            game.setDate(LocalDate.now());

            GamesDAO dao = new GamesDAO();
            dao.saveGame(game);
        }
    }

    private void setupGame(){

        HBox p1Container = setupPlayerContainer(1);
        HBox p2Container = setupPlayerContainer(2);

        Base.getChildren().addAll(p1Container, chessBoard, p2Container);
        Base.setSpacing(20);
        Base.setAlignment(Pos.CENTER);
    }

    private HBox setupPieceContainer(Color color){
        HBox toRet = new HBox();

        String[] pieceOrder = {"KN", "B",  "R",  "Q" };

        List<DisplaySquare> list = new ArrayList<>();

        for (String s : pieceOrder) {

            DisplaySquare dp = new DisplaySquare();
            dp.setPrefSize(50, 50);
            dp.setBorder(new Border(new BorderStroke(Color.valueOf("#3841ff"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(1))));
            dp.setStyle("-fx-background-color: #66ccff");
            dp.addEventFilter(MouseEvent.MOUSE_PRESSED, changePromoted);

            BasePiece p;
            switch (s) {
                case "R":
                    p = new Rook(color,  (color==Color.WHITE? "white" : "black") + "_rook.png", pM);
                    break;
                case "KN":
                    p = new Knight(color,  (color==Color.WHITE? "white" : "black") + "_knight.png", pM);
                    break;
                case "B":
                    p = new Bishop(color,  (color==Color.WHITE? "white" : "black") + "_bishop.png", pM);
                    break;
                case "K":
                    p = new King(color,  (color==Color.WHITE? "white" : "black") + "_king.png", pM);
                    break;
                case "Q":
                    p = new Queen(color,  (color==Color.WHITE? "white" : "black") + "_queen.png", pM);
                    break;
                default:
                    p = new Pawn(color,  (color==Color.WHITE? "white" : "black") + "_pawn.png", pM);
                    break;
            }
            if (color==Color.WHITE){
                pM.whitePieces.add(p);
            }
            else {
                pM.blackPieces.add(p);
            }
            p.place(dp);

            list.add(dp);
        }

        //setup hbox
        toRet.getChildren().addAll(list);
        toRet.setAlignment(Pos.CENTER);

        return toRet;
    }

    private HBox setupPlayerContainer(int p){
        HBox toRet = new HBox();

        Label name = new Label();
        Button res = new Button("Feladás");
        Button dr = new Button("Döntetlen");

        //setup buttons
        res.setOnAction(resign);
        res.setFocusTraversable(false);
        dr.setOnAction(draw);
        dr.setFocusTraversable(false);
        drawButtons.add(dr);

        //setup label
        name.setPrefHeight(20);
        name.prefWidth(80);
        name.setFont(new Font("System Bold", 20));

        //setup hbox
        toRet.getChildren().addAll(name, res, dr);
        toRet.setAlignment(Pos.CENTER);
        toRet.setSpacing(20);

        //button id
        res.setId( p==1 ? "p1" : "p2");

        //set label text and store value
        name.setText(getPlayerName(p));

        return toRet;
    }

    private String getPlayerName(int p){
        String string;
        if (p == 1){
            if(player_1.getText().isBlank())
                string = "Black";
            else
                string =  player_1.getText();
            player1Name = string;
        }
        else {
            if(player_2.getText().isBlank())
                string =  "White";
            else
                string =  player_2.getText();
            player2Name = string;
        }
        return string;
    }

    public void addPieceList(Color color){
        boolean isPlaced = false;
        List<Node> children = new ArrayList<>(Base.getChildren());
        Base.getChildren().clear();
        int index = color == Color.WHITE ? 1 : 2;

        for (int i = 0; i <= children.size(); i++){
            if (i == index){
                Base.getChildren().add(setupPieceContainer(color));
                isPlaced = true;
            }
            else {
                Base.getChildren().add(children.get(isPlaced ? i-1 : i));
            }
        }
    }

    EventHandler<ActionEvent> resign = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {
            winnerName = (((Button) actionEvent.getSource()).getId().equals("p1") ? player2Name : player1Name) + " won!";
            App.setRoot("end.fxml");
        }
    };

    EventHandler<ActionEvent> draw = new EventHandler<>() {
        @Override
        public void handle(ActionEvent actionEvent) {

            ((Button) actionEvent.getSource()).setDisable(true);

            if (!drawRequest) {
                drawRequest = true;
            } else {
                winnerName = "Draw!";
                App.setRoot("end.fxml");
            }
        }
    };

    EventHandler<MouseEvent> changePromoted = mouseEvent -> {

        ((DisplaySquare) mouseEvent.getSource()).currentPiece.setTargetSquare(chessBoard.promotedHere);
        ((DisplaySquare) mouseEvent.getSource()).currentPiece.move();


        for (Node n : Base.getChildren()){
            if (n.getClass() == HBox.class){
                for (Node o : ((HBox) n).getChildren()){
                    if (o.getClass() == DisplaySquare.class){
                        Base.getChildren().remove(n);
                        return;
                    }
                }
            }
        }


    };
}
