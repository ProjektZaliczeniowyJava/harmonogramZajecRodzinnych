package sample;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Optional;

public class DialogWithUser {
    //TODO z osob, dni i godzin zrobić najlepiej Map<String, Integer>  i wtedy bedziemy przyporzadkowywac do kluczy
    // do Event dodać klase abstracyjna ze zmienna static ktora bede liczyla wydarzenia,
    // zeby automatycznie indeksowac i tworzyc odpowiedni numer event


    private Optional<Event> result;

    public DialogWithUser() {
        this.result = null;
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
                FXCollections.observableArrayList("1", "2", "3");
        ComboBox<String> personOption = new ComboBox<>(personList);
        personOption.getSelectionModel().selectFirst();

        ObservableList<String> dayList =
                FXCollections.observableArrayList("1", "2", "3");
        ComboBox<String> dayOption = new ComboBox<>(dayList);
        dayOption.getSelectionModel().selectFirst();

        ObservableList<String> hourList =
                FXCollections.observableArrayList("1", "2", "3");
        ComboBox<String> hourOption = new ComboBox<>(personList);
        hourOption.getSelectionModel().selectFirst();

        ObservableList<String> minuteList =
                FXCollections.observableArrayList("1", "2", "3");
        ComboBox<String> minuteOption = new ComboBox<>(personList);
        hourOption.getSelectionModel().selectFirst();
        
        TextField eventInformation = new TextField();
        eventInformation.setPromptText("Treść wydarzenia");

        dialogPane.setContent(new VBox(8, person, personOption, day, dayOption, hour, hourOption, eventInformation));
        Platform.runLater(() -> eventInformation.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new Event(1, Integer.parseInt(personOption.getValue()),
                        dayOption.getValue(), Integer.parseInt(hourOption.getValue()), Integer.parseInt(minuteOption.getValue()), eventInformation.getText());
            }
            return null;
        });
        this.result = dialog.showAndWait();
    }

    public Optional<Event> getInputResult() {
        //TODO dodać obsługe kiedy null
        return this.result;
    }
}
