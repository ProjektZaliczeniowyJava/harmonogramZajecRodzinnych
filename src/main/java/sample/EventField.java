package sample;

import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import java.util.HashMap;
import java.util.Map;

public class EventField {
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