package sample;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import java.util.HashMap;
import java.util.Map;

public class EventField {
    //TODO id uzytkownika skojarzone z kolorem przycisku
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
        Button button = new Button(this.event.getMessage());
        //button.setId(Integer.toString(this.event.getId()));
        button.setOnAction((e)-> {
           new WindowToEditEvent().createUserInput();
        });

        return button;
    }

    private int getDayId(String day) {
        return dayIdMap.get(day);
    }

    public void addToGridPaneAndButtonList(GridPane gridPane, HashMap<Integer, Button> mapOfButtons) {
        Button button = this.createButtonEvent();
        gridPane.add(button, getDayId(this.event.getDay()), this.event.getHour());
        mapOfButtons.put(this.event.getId(), button);
    }


}