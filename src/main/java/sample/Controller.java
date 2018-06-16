package sample;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.*;

public class Controller {
    private DataBase dataBase = new DerbyDataBase();
    private Button addButton;
    private Observer observer = Observer.getInstance();

    @FXML
    private GridPane gridPaneDay;
    private HashMap<Integer, Button> mapOfButtons = new HashMap<>();


    public void addToObserver() {
        this.observer.addControllerToObserver(this);
    }

//    public void addDataBase(DataBase db) {
//        this.dataBase = db;
//    }

    private void removeFromGridPane(GridPane gridPane, Button button) {
//        podajemy id button i bierzemy button z map, usuwamy z map
        // zabrac to stad potem
        gridPane.getChildren().remove(button);
    }

    public void clickAddButton() {
        WindowToCreateEvent windowToCreateEvent = new WindowToCreateEvent();
        windowToCreateEvent.createUserInput();
        Optional<Event> result = windowToCreateEvent.getInputResult();

        result.ifPresent(pair -> {
            if (!pair.getMessage().isEmpty()) {
                EventField eventField = new EventField(result.get());
                Button eventButton = eventField.createButtonEvent();
                gridPaneDay.add(eventButton, eventField.getDayId(), eventField.getHour());
                mapOfButtons.put(eventField.getEventId(), eventButton);

            }
        });
// WYSKAKUJE NULLPOINTER przy dodawaniu do bazy !!!!
//        result.ifPresent(pair -> {
//            try {
//                dataBase.addEvent(result.get());
//            } catch(SQLException e) {
//                    ;
//                }
//            });


        //tylko dla testow  jak dodasz wydarzenie dla osoba 1 , a drugie dla innej osoby z minutami 10 to pierwsze znika :)
        result.ifPresent(pair -> {
            if (pair.getMin() == 10) {
                removeFromGridPane(gridPaneDay, mapOfButtons.get(2));
            }
        });
    }

    public void clickEventButton(int idEvent) {
//        TODO dodac aktualizacje danych w bazie na podstawie nowego event, stare usuwamy?
//        try {
//            List<Event> events = dataBase.getAllEvents();
//            WindowToEditEvent windowToEditEvent = new WindowToEditEvent(events.get(idEvent));
        // na sztywno wydarzenie do edytowania, powinno odczytywac z bazy danych do listy i potem z listy czytamy wydarzenie
            Event event = new Event(1,2, "NIEDZIELA", 8, 20, "ZMYWANIE NACZYN");
            WindowToEditEvent windowToEditEvent = new WindowToEditEvent(event);
            windowToEditEvent.createUserInput();
            Optional<Event> result = windowToEditEvent.getInputResult();

            result.ifPresent(pair -> {
                if (!pair.getMessage().isEmpty()) {
                    EventField eventField = new EventField(result.get());
                    Button eventButton = eventField.createButtonEvent();

                    gridPaneDay.add(eventButton, eventField.getDayId(), eventField.getHour());
                    mapOfButtons.put(eventField.getEventId(), eventButton);
                }
            });
//        } catch (SQLException e) {
//            ;
//        }


    }
}
