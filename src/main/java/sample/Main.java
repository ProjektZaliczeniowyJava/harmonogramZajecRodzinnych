package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        DataBase dataBase = new DerbyDataBase();
        dataBase.createConnectionToDerby();
        /*dataBase.createUserTable();
        dataBase.addRecordToUserTable(1, "kasia");
        dataBase.addRecordToUserTable(4, "karolina");*/
        dataBase.showUserTable();

        /*dataBase.createEventTable();
        dataBase.addRecordToEventTable(1, 2, 3, 4, "wyniesc smieci");
        dataBase.addRecordToEventTable(2, 2, 6, 7, "pozmywac naczynia");*/
        dataBase.showEventTable();
//        dataBase.backUpDatabase();
//        dataBase.restoreDatabase();

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
