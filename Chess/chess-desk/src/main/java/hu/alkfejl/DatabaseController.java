package hu.alkfejl;

import hu.alkfejl.dao.GamesDAO;
import hu.alkfejl.model.Game;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class DatabaseController {

    private final GamesDAO gamesDAO = new GamesDAO();


    //tableview
    @FXML
    private TableView<Game> gamesView;
    @FXML
    private TableColumn<Object, Object> whitePlayerCol;
    @FXML
    private TableColumn<Object, Object> blackPlayerCol;
    @FXML
    private TableColumn<Object, Object> scoreCol;
    @FXML
    private TableColumn<Object, Object> dateCol;

    //search
    @FXML
    private TextField player1;
    @FXML
    private TextField player2;
    @FXML
    private ToggleGroup winner;
    @FXML
    private DatePicker date;


    public void initialize(){
        whitePlayerCol.setCellValueFactory(new PropertyValueFactory<>("player2"));
        blackPlayerCol.setCellValueFactory(new PropertyValueFactory<>("player1"));
        scoreCol.setCellValueFactory(new PropertyValueFactory<>("score"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));

        for(Game g : gamesDAO.getAllGames()){
            gamesView.getItems().add(g);
        }
    }

    @FXML
    private void switchToMenu(){
        App.setRoot("main.fxml");
    }

    @FXML
    private void searchGame() {
        gamesView.getItems().clear();

        Game game = new Game();

        if (!player1.getText().isBlank())
            game.setPlayer1(player1.getText());
        if (!player2.getText().isBlank())
            game.setPlayer2(player2.getText());
        if (winner.getSelectedToggle() != null)
            if (((RadioButton) winner.getSelectedToggle()).getText().equals("White"))
                game.setScore("1-0");
            else if (((RadioButton) winner.getSelectedToggle()).getText().equals("Black"))
                game.setScore("0-1");
            else
                game.setScore("0-0");

        if (date.getValue() != null)
            game.setDate(date.getValue());

        List<Game> games;

        if (game.getDate() == null && game.getScore() == null && game.getPlayer1() == null && game.getPlayer2() == null)
            games = gamesDAO.getAllGames();
        else
            games = gamesDAO.getSpecificGame(game);

        for(Game g : games){
            gamesView.getItems().add(g);
        }
    }

    @FXML
    private void clearConditions() {
        player1.setText("");
        player2.setText("");
        if (winner.getSelectedToggle() != null)
            winner.getSelectedToggle().setSelected(false);
        if (date.getValue() != null)
            date.setValue(null);
    }
}
