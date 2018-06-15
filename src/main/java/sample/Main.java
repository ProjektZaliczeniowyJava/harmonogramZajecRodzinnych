package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        DataBase dataBase = new DataBase();
        dataBase.connectionToDerby();
        dataBase.createUserTable();
        dataBase.addRecordToUserTable(1, "kasia");
        dataBase.addRecordToUserTable(4, "karolina");
        dataBase.showDatabase();
        dataBase.backUpDatabase();
        dataBase.addRecordToUserTable(3, "Krzysiek");
        dataBase.showDatabase();
        dataBase.restoreDatabase();
        dataBase.showDatabase();

        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Harmonogram Zajeć Rodzinnych");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        //test pusha brancha
        //ostatni test
    }


    public static void main(String[] args) {
        launch(args);
    }
}
