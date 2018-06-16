package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.*;

public class Controller {
    @FXML
    private Button addButton;

    @FXML
    private GridPane gridPaneDay;
    private HashMap<Integer, Button> mapOfButtons = new HashMap<>();

    public void removeFromGridPane(GridPane gridPane, Button button) {
//        podajemy id button i bierzemy button z map, usuwamy z map
        // zabrac to stad potem
         gridPane.getChildren().remove(button);
    }
    public void clickAddButton(ActionEvent actionEvent) {
        WindowToCreateEvent windowToCreateEvent = new WindowToCreateEvent();
        windowToCreateEvent.createUserInput();
        Optional<Event> result = windowToCreateEvent.getInputResult();
//        result.ifPresent(pair -> {
//            System.out.println("ID:" + pair.getId() + "\nID_USER:" + pair.getId_user()+
//                    "\nDAY:"+pair.getDay()+"\nHOUR:"+pair.getHour()+"\nMINUTES:"+pair.getMin()+"\nMESSAGE:"+pair.getMessage());
//        });
        new EventField(result.get()).addToGridPaneAndButtonList(gridPaneDay, mapOfButtons);

        //tylko dla testow  jak dodasz wydarzenie dla osoba 1 , a drugie dla innej osoby z minutami 10 to pierwsze znika :)
        result.ifPresent(pair-> {if(pair.getMin() == 10) {
            removeFromGridPane(gridPaneDay, mapOfButtons.get(2));
        };});
    }


}
