package hu.alkfejl.dao;

import hu.alkfejl.model.Game;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.sql.*;

public class GamesDAO {

    private String connectionURL;

    private static final String SELECT_ALL_GAMES = "SELECT * FROM GAMES";
    private static final String INSERT_GAME = "INSERT INTO GAMES (player1, player2, score, date) VALUES (?, ?, ?, ?)";


    public GamesDAO(){
        try{
            Properties props = new Properties();
            props.load(getClass().getResourceAsStream("/application.properties"));
            connectionURL = props.getProperty("db.url");
            Class.forName("org.sqlite.JDBC");
        }
        catch (IOException | ClassNotFoundException e){
             e.printStackTrace();
        }
    }

    public List<Game> getSpecificGame(Game game){

        List<Game> games = new ArrayList<>();

        try(Connection c = DriverManager.getConnection(connectionURL)
        ) {
            String selectString = "SELECT * FROM GAMES WHERE";
            if (game.getPlayer1() != null)
                selectString += " player1 = '" + game.getPlayer1() + "'";
            if (game.getPlayer2() != null)
                if (!selectString.equals("SELECT * FROM GAMES WHERE"))
                    selectString += " AND player2 = '" + game.getPlayer2() + "'";
                else
                    selectString += " player2 = '" + game.getPlayer2() + "'";
            if (game.getScore() != null)
                if (!selectString.equals("SELECT * FROM GAMES WHERE"))
                    selectString += " AND score = '" + game.getScore() + "'";
                else
                    selectString += " score = '" + game.getScore() + "'";
            if (game.getDate() != null)
                if (!selectString.equals("SELECT * FROM GAMES WHERE"))
                    selectString += " AND date = '" + game.getDate().toString() + "'";
                else
                    selectString += " date = '" + game.getDate().toString() + "'";

            PreparedStatement statement = c.prepareStatement(selectString);

            ResultSet rs = statement.executeQuery();

            while (rs.next()){
                Game g = new Game();
                g.setPlayer1(rs.getString("player1"));
                g.setPlayer2(rs.getString("player2"));
                g.setScore(rs.getString("score"));
                g.setDate(Date.valueOf(rs.getString("date")).toLocalDate());

                games.add(g);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return games;
    }

    public List<Game> getAllGames(){

        List<Game> games = new ArrayList<>();

        try(Connection c = DriverManager.getConnection(connectionURL);
            Statement s = c.createStatement();
            ResultSet rs = s.executeQuery(SELECT_ALL_GAMES)
        ){

            while (rs.next()){
                Game g = new Game();
                g.setPlayer1(rs.getString("player1"));
                g.setPlayer2(rs.getString("player2"));
                g.setScore(rs.getString("score"));
                g.setDate(Date.valueOf(rs.getString("date")).toLocalDate());

                games.add(g);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }

        return games;

    }

    public void saveGame(Game game){
        try(Connection c = DriverManager.getConnection(connectionURL);
            PreparedStatement statement = c.prepareStatement(INSERT_GAME)
        ){

            statement.setString(1, game.getPlayer1());
            statement.setString(2, game.getPlayer2());
            statement.setString(3, game.getScore());
            statement.setString(4, game.getDate().toString());

            statement.executeUpdate();

        }
        catch (SQLException e){
            e.printStackTrace();
        }
    }


}
