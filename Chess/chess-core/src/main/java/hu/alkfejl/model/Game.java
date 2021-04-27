package hu.alkfejl.model;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

import java.time.LocalDate;

public class Game {


    private SimpleStringProperty player1 = new SimpleStringProperty(this, "player1");
    private SimpleStringProperty player2 = new SimpleStringProperty(this, "player2");
    private SimpleStringProperty score = new SimpleStringProperty(this, "score");
    private ObjectProperty<LocalDate> date = new SimpleObjectProperty<>(this, "date");

    public String getPlayer1() {
        return player1.get();
    }

    public SimpleStringProperty player1Property() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1.set(player1);
    }

    public String getPlayer2() {
        return player2.get();
    }

    public SimpleStringProperty player2Property() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2.set(player2);
    }

    public String getScore() {
        return score.get();
    }

    public SimpleStringProperty scoreProperty() {
        return score;
    }

    public void setScore(String score) {
        this.score.set(score);
    }

    public LocalDate getDate() {
        return date.get();
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }
}
