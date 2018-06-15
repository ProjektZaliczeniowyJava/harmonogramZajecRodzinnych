package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class Controller {
    @FXML
    private Button addButton;
    public void clickAddButton(ActionEvent actionEvent) {

        TextInputDialog dialog = new TextInputDialog("");
        dialog.setTitle("Dodaj wydarzenie ");
        dialog.setContentText("Podaj dzien");


        Optional<String> result = dialog.showAndWait();



        result.ifPresent(name -> System.out.println("wydarzenia: " + name));

    }


}
