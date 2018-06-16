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
        System.out.println("aa");

        /*dataBase.createUserTable();
        dataBase.addRecordToUserTable(1, "kasia");
        dataBase.addRecordToUserTable(4, "karolina");*/

        //dataBase.createEventTable();
        
        //dataBase.addRecordToEventTable(1, 2, "Poniedzia³ek", 4, 15, "wyniesc smieci");
        //dataBase.addRecordToEventTable(2, 2, "Wtorek", 7, 20, "pozmywac naczynia");
        
		System.out.println(dataBase.getAllUsers());
		System.out.println(dataBase.getAllEvents());
//        dataBase.backUpDatabase();
//        dataBase.restoreDatabase();

		FXMLLoader loader = new FXMLLoader();
        loader.setController(new Controller());
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Harmonogram ZajeÄ‡ Rodzinnych");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
        //test pusha brancha
        //ostatni test
    }


    public static void main(String[] args) {
        launch(args);
    }
}
