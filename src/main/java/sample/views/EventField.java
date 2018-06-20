package sample.views;

import javafx.scene.control.Button;
import sample.observers.ButtonCreationObserver;
import sample.observers.ButtonRemovalObserver;
import sample.utilities.Event;

import java.util.HashMap;
import java.util.Map;

public class EventField {
    private Event event;
    private ButtonCreationObserver buttonCreationObserver = ButtonCreationObserver.getInstance();
    private ButtonRemovalObserver buttonRemovalObserver = ButtonRemovalObserver.getInstance();
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
        button.setOnAction((e)-> {
            this.notifyCreationObserver(this.getEventId());
        });
        return button;
    }

    private void notifyCreationObserver(int idEvent) {
        this.buttonCreationObserver.update(idEvent);
    }

    public void notifyRemovalObserver(int idEvent) {
        this.buttonRemovalObserver.update(idEvent);
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