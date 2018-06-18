package sample;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.*;

public class Controller {
    private DataBase dataBase;
    private Button addButton;
    private Button PDFButton;
    private ButtonCreationObserver buttonCreationObserver;
    private ButtonRemovalObserver buttonRemovalObserver;

    //called when .fxml file is loaded
    public void initialize(){
        buttonCreationObserver = ButtonCreationObserver.getInstance();
        buttonRemovalObserver = ButtonRemovalObserver.getInstance();
        addToObserver();
        dataBase = new DerbyDataBase();
        try {
            dataBase.createConnectionToDerby();
            //tutaj pobieramy dane z bazy, wypeniamy mapę przycisków, oraz je wyswietlamy na planszy
            loadEventsFromDatabase();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private GridPane gridPaneDay;
    private HashMap<Integer, Button> mapOfButtons = new HashMap<>();

    private void addToObserver() {
        this.buttonCreationObserver.addControllerToObserver(this);
        this.buttonRemovalObserver.addControllerToObserver(this);
    }

    public void removeButtonFromGrid(int idEvent) {
        Button buttonToRemove = mapOfButtons.get(idEvent);
        this.gridPaneDay.getChildren().remove(buttonToRemove);
    }

    public void removeButtonFromDataBase(int idEvent) {
        mapOfButtons.remove(idEvent);
        try {
            this.dataBase.deleteEvent(idEvent);
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void clickAddButton() {
        WindowToCreateEvent windowToCreateEvent = new WindowToCreateEvent();
        windowToCreateEvent.createUserInput();
        Optional<Event> result = windowToCreateEvent.getInputResult();
        
        result.ifPresent(res -> {
            if (!res.getMessage().isEmpty()) {
            	int key = 0;
                try {
                	key = dataBase.addEvent(result.get());
                    Event event = new Event(key, result.get().getId_user(), result.get().getDay(),
                            result.get().getHour(),result.get().getMin(), result.get().getMessage());
                    EventField eventField = new EventField(event);
                    Button eventButton = eventField.createButtonEvent();
                    gridPaneDay.add(eventButton, eventField.getDayId(), eventField.getHour());
                    mapOfButtons.put(key, eventButton);
                } catch(SQLException e) {
                    e.printStackTrace();
                }    
            }
        });
    }

    public void clickEventButton(int idEvent) {
            try {
				WindowToEditEvent windowToEditEvent = new WindowToEditEvent(dataBase.getEvent(idEvent));
                windowToEditEvent.createUserInput();
                Optional<Event> result = windowToEditEvent.getInputResult();
                result.ifPresent(res -> {
                    if (!res.getMessage().isEmpty()) {
                        try {
                            this.removeButtonFromGrid(idEvent);
                            Event event = new Event(idEvent, result.get().getId_user(), result.get().getDay(),
                                    result.get().getHour(),result.get().getMin(), result.get().getMessage());
                            dataBase.updateEvent(idEvent, event);
                            EventField eventField = new EventField(event);
                            Button eventButton = eventField.createButtonEvent();
                            gridPaneDay.add(eventButton, eventField.getDayId(), eventField.getHour());
                            mapOfButtons.put(idEvent, eventButton);
                        } catch(SQLException e) {
                            e.printStackTrace();
                        }
                    }
                });
			} catch (SQLException exe) {
                exe.printStackTrace();
			}
    }

    public void clickPDFButton() {
        //TODO generowanie do pdf
        System.out.println("kliknięto przycisk PDF");
    }
    
    public void loadEventsFromDatabase() throws SQLException {
    	List<Event> events = dataBase.getAllEvents();
    	
    	for(Event event: events) {
            EventField eventField = new EventField(event);
            Button eventButton = eventField.createButtonEvent();

            gridPaneDay.add(eventButton, eventField.getDayId(), eventField.getHour());
            mapOfButtons.put(eventField.getEventId(), eventButton);
    	}
    }


}
