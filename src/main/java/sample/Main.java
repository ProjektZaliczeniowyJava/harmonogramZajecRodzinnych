package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
import javafx.stage.Stage;

import java.awt.*;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader();
        loader.setController(new Controller());
        ScrollPane root = loader.load(getClass().getResource("sample.fxml"));
        root.getStylesheets().add(String.valueOf(getClass().getResource("/styles/style1.css")));
        primaryStage.setTitle("Harmonogram Zajeć Rodzinnych");
        primaryStage.setScene(new Scene(root, 850, 700));
        primaryStage.setResizable(false);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }
}
