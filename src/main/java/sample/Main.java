package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{

        DataBase dataBase = new DerbyDataBase();
        dataBase.createConnectionToDerby();
        dataBase.createUserTable();
        dataBase.addRecordToUserTable(1, "kasia");
        dataBase.addRecordToUserTable(4, "karolina");
        dataBase.showUserTable();

        //Tworzenie tabeli event jeszcze raz
        dataBase.createEventTable();
        dataBase.addRecordToEventTable(1, 2, "Poniedzialek", 4, 15, "wyniesc smieci");
        dataBase.addRecordToEventTable(2, 2, "Wtorek", 7, 20, "pozmywac naczynia");
        //dataBase.showEventTable();
//        dataBase.backUpDatabase();
//        dataBase.restoreDatabase();

		System.out.println(dataBase.getAllUsers());
		System.out.println(dataBase.getAllEvents());
		
		
        FXMLLoader loader = new FXMLLoader();
        loader.setController(new Controller());
        Pane root = loader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Harmonogram Zajeć Rodzinnych");
        primaryStage.setScene(new Scene(root, 800, 420));
        primaryStage.setResizable(false);
        primaryStage.show();


        //test pusha brancha
        //ostatni test
    }


    public static void main(String[] args) {
        launch(args);
    }
}
