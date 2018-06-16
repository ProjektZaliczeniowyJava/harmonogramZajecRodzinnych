package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


class EventField {
    private Event event;
    private static final Map<String, Integer> dayIdMap = new HashMap<>();
    static {
        dayIdMap.put("PONIEDZIAŁEK", 0);
        dayIdMap.put("WTOREK", 1);
        dayIdMap.put("ŚRODA", 2);
        dayIdMap.put("CZWARTEK", 3);
        dayIdMap.put("PIĄTEK", 4);
        dayIdMap.put("SOBOTA", 5);
        dayIdMap.put("NIEDZIELA", 6);
    }

    public EventField(Event event) {
        this.event = event;
    }

    private Button createButtonEvent() {
        return new Button(this.event.getMessage());
    }

    private int getDayId(String day) {
        return dayIdMap.get(day);
    }

    public void addToGridPane(GridPane gridPane) {
        gridPane.add(createButtonEvent(), getDayId(this.event.getDay()), this.event.getHour());
    }
}

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
