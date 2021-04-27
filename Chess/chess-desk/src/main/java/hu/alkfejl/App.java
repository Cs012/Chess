package hu.alkfejl;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(FXMLLoader.load(getClass().getResource("main.fxml")), 800, 700);
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml){
        try{
            scene.setRoot(FXMLLoader.load((App.class.getResource(fxml))));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    static void setRoot(Pane fxml) {
        scene.setRoot(fxml);
    }

    public static void main(String[] args) {
        launch();
    }

}