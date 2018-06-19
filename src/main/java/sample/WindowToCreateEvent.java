package sample;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.*;


public class WindowToCreateEvent {
    private Optional<Event> result;
    private List<User> listOfUsers;
    private Map<String, Integer> users;
    private ArrayList<String> dayNames = new ArrayList<> (Arrays.asList
            ("PONIEDZIAŁEK","WTOREK", "ŚRODA", "CZWARTEK", "PIĄTEK", "SOBOTA", "NIEDZIELA"));
    private ArrayList<String> hours = new ArrayList<>(Arrays.asList
            ("00", "01", "02", "03", "04","05", "06", "07", "08", "09", "10",
                    "11", "12","13", "14","15", "16","17", "18", "19", "20", "21", "22", "23"));
    private ArrayList<String> minutes = new ArrayList<>(Arrays.asList
            ("00", "01", "02", "03", "04","05", "06", "07", "08", "09", "10", "11", "12","13", "14","15", "16","17", "18", "19", "20",
                    "21", "22", "23", "24", "25", "26", "27", "28", "29", "30", "31", "32","33", "34","35", "36","37", "38", "39", "40",
                    "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52","53", "54","55", "56","57", "58", "59"));

    public WindowToCreateEvent(List<User> listOfUsers) {
        this.result = null;
        this.listOfUsers = listOfUsers;
        this.users = new HashMap<>();
        this.createMapOfUsers();
    }

    private void createMapOfUsers() {
        this.listOfUsers.forEach(e->this.users.put(e.getName(), e.getId()));
    }

    private int getUserId(String name) {
        return this.users.get(name);
    }

    public void createUserInput() {
        Dialog<Event> dialog = new Dialog<>();
        dialog.setTitle("Dodaj wydarzenie ");
        Label person = new Label("Osoba");
        Label day = new Label("Dzien");
        Label hour = new Label("Godzina");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);


        ObservableList<String> personList =
                FXCollections.observableArrayList(this.users.keySet());
        ComboBox<String> personOption = new ComboBox<>(personList);
        personOption.getSelectionModel().selectFirst();

        ObservableList<String> dayList =
                FXCollections.observableArrayList(dayNames);
        ComboBox<String> dayOption = new ComboBox<>(dayList);
        dayOption.getSelectionModel().selectFirst();

        ObservableList<String> hourList =
                FXCollections.observableArrayList(hours);
        ComboBox<String> hourOption = new ComboBox<>(hourList);
        hourOption.getSelectionModel().selectFirst();

        ObservableList<String> minuteList =
                FXCollections.observableArrayList(minutes);
        ComboBox<String> minuteOption = new ComboBox<>(minuteList);
        minuteOption.getSelectionModel().selectFirst();


        TextField eventInformation = new TextField();
        eventInformation.setPromptText("Treść wydarzenia");

        GridPane gridpane = new GridPane();
        gridpane.setPadding(new Insets(10));
        gridpane.setHgap(5);
        gridpane.setVgap(5);


        gridpane.add(eventInformation, 0,0);
        gridpane.add(person, 1,0 );
        gridpane.add(personOption, 2,0 );
        gridpane.add(day, 0,1 );
        gridpane.add(dayOption, 1,1 );
        gridpane.add(hour, 0,2 );
        gridpane.add(hourOption, 1,2 );
        gridpane.add(minuteOption, 2,2 );

        dialogPane.setContent(gridpane);
        Platform.runLater(() -> eventInformation.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Event(this.getUserId(personOption.getValue()),
                        dayOption.getValue(), Integer.parseInt(hourOption.getValue()), Integer.parseInt(minuteOption.getValue()),eventInformation.getText());

            }
            return null;
        });

        this.result = dialog.showAndWait();
    }

    public Optional<Event> getInputResult() {
        return this.result;
    }
}
