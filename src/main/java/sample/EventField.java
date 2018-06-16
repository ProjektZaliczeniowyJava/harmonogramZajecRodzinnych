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

    public  Button createButtonEvent() {
        Button button = new Button(this.event.getMessage());
        //button.setId(Integer.toString(this.event.getId()));
        button.setOnAction((e)-> {
           new WindowToEditEvent().createUserInput();
        });

        return button;
    }

    public int getDayId() {
        return dayIdMap.get(this.event.getDay());
    }

    public int getHour() {
        return this.event.getHour();
    }

    public int getEventId() {
        return this.event.getId();
    }

}