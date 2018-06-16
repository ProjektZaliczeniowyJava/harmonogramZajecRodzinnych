package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class Controller {
    @FXML
    private Button addButton;

    @FXML
    private GridPane gridPaneDay;

    public void clickAddButton(ActionEvent actionEvent) {
        DialogWithUser dialogWithUser = new DialogWithUser();
        dialogWithUser.createUserInput();
        Optional<Event> result = dialogWithUser.getInputResult();
//        result.ifPresent(pair -> {
//            System.out.println("ID:" + pair.getId() + "\nID_USER:" + pair.getId_user()+
//                    "\nDAY:"+pair.getDay()+"\nHOUR:"+pair.getHour()+"\nMINUTES:"+pair.getMin()+"\nMESSAGE:"+pair.getMessage());
//        });

        new EventField(result.get()).addToGridPane(gridPaneDay);

    }


}
