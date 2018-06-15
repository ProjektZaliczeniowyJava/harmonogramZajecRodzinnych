package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Pair;

import java.util.Optional;


public class Controller {
    @FXML
    private Button addButton;
    public void clickAddButton(ActionEvent actionEvent) {
        DialogWithUser dialogWithUser = new DialogWithUser();
        dialogWithUser.createUserInput();
        Optional<Pair<String, String>> result = dialogWithUser.getInputResult();
        result.ifPresent(pair -> {
            System.out.println("Wydarzenie:" + pair.getKey() + ", Godzina:" + pair.getValue());
        });
    }


}
