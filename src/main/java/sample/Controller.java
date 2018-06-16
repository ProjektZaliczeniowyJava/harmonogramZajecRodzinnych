package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import java.util.*;

public class Controller {
	private DataBase dataBase;
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

        result.ifPresent(pair -> {
            if(!pair.getMessage().isEmpty()) {
                EventField eventField = new EventField(result.get());
                Button eventButton = eventField.createButtonEvent();
                gridPaneDay.add(eventButton, eventField.getDayId(), eventField.getHour());
                mapOfButtons.put(eventField.getEventId(), eventButton);

            }
        });

        //tylko dla testow  jak dodasz wydarzenie dla osoba 1 , a drugie dla innej osoby z minutami 10 to pierwsze znika :)
        result.ifPresent(pair-> {if(pair.getMin() == 10) {
            removeFromGridPane(gridPaneDay, mapOfButtons.get(2));
        }});
    }

}
