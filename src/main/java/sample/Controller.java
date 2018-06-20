package sample;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;

public class Controller {
    private DataBase dataBase;
    private Button addButton;
    private Button PDFButton;
    private ButtonCreationObserver buttonCreationObserver;
    private ButtonRemovalObserver buttonRemovalObserver;
    public ChoiceBox choicebox;
    private HashMap<Integer, Button> mapOfButtons = new HashMap<>();
    private int userAmount = 0;
    private File tempStyleClass;
    @FXML
    private GridPane gridPaneDay;

    @FXML
    private AnchorPane root;

    @FXML
    private GridPane userLabel;

    public void initialize(){
        buttonCreationObserver = ButtonCreationObserver.getInstance();
        buttonRemovalObserver = ButtonRemovalObserver.getInstance();
        addToObserver();
        dataBase = new DerbyDataBase();
        try {
            dataBase.createConnectionToDerby();
            this.tempStyleClass = File.createTempFile("userInfo", ".css");
            tempStyleClass.deleteOnExit();
            loadEventsFromDatabase();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }

        choicebox.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number number2) {
                Scene scene = choicebox.getScene();
                String css = String.valueOf(getClass().getResource("../styles/" + choicebox.getItems().get((Integer) number2)));
                root.getStylesheets().clear();
                root.getStylesheets().add(css);
            }
        });
    }

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
        try {
            WindowToCreateEvent windowToCreateEvent = new WindowToCreateEvent(this.dataBase.getAllUsers());
            windowToCreateEvent.createUserInput();
            Optional<Event> result = windowToCreateEvent.getInputResult();

            result.ifPresent(res -> {
                if (!res.getMessage().isEmpty()) {
                    int key;
                    try {
                        key = dataBase.addEvent(result.get());
                        Event event = new Event(key, result.get().getId_user(), result.get().getDay(),
                                result.get().getHour(), result.get().getMin(), result.get().getMessage());
                        EventField eventField = new EventField(event);
                        User user = dataBase.getUser(key);
                        String color = user.getColorNumber();
                        Button eventButton = eventField.createButtonEvent();
                        eventButton.setStyle(" -fx-text-fill: black; -fx-background-color: "+color);
                        gridPaneDay.add(eventButton, eventField.getDayId(), eventField.getHour());
                        mapOfButtons.put(key, eventButton);
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }

    public void clickEventButton(int idEvent) {
        try {
            WindowToEditEvent windowToEditEvent = new WindowToEditEvent(dataBase.getEvent(idEvent), this.dataBase.getAllUsers());
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
                        int key = event.getId_user();
                        User user = dataBase.getUser(key);
                        String color = user.getColorNumber();
                        eventButton.setStyle(" -fx-text-fill: black; -fx-background-color: "+color);
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

    private void loadEventsFromDatabase() throws SQLException {
        List<Event> events = dataBase.getAllEvents();
        List<User> users = dataBase.getAllUsers();
        for(Event event: events) {
            EventField eventField = new EventField(event);
            Button eventButton = eventField.createButtonEvent();
            int key = event.getId_user();
            User user = dataBase.getUser(key);
            String color = user.getColorNumber();
            eventButton.setStyle(" -fx-text-fill: black; -fx-background-color: "+color);
            gridPaneDay.add(eventButton, eventField.getDayId(), eventField.getHour());
            mapOfButtons.put(eventField.getEventId(), eventButton);
        }
        try {

            try (PrintWriter printWriter = new PrintWriter(tempStyleClass)) {
                for (User user : users) {
                    Button button = new Button(user.getName());
                    String color = user.getColorNumber();
                    printWriter.println(".temp-style"+userAmount+" { -fx-text-fill: black; }");
                    printWriter.println(".temp-style"+userAmount+" { -fx-background-color: "+color+" ; }");
                    button.getStylesheets().add(tempStyleClass.toURI().toString());
                    button.getStyleClass().add("temp-style"+userAmount);
                    userLabel.add(button, 0, userAmount++);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clickPDFButton() {

        // Tutaj zapisuje do png
        WritableImage image = root.snapshot(new SnapshotParameters(), null);
        File file = new File(".\\Charts.pdf");
        try {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
        } catch (IOException e) {
        }

        // Tutaj drukuje
        PrinterJob job = PrinterJob.createPrinterJob();
        if (job != null) {
            job.showPrintDialog(gridPaneDay.getScene().getWindow());
            job.printPage(root);
            job.endJob();
        }

    }

    public void clickAddPersonButton() {
        WindowToAddUser windowToAddUser = new WindowToAddUser();
        windowToAddUser.createUserInput();
        Optional<User> result = windowToAddUser.getInputResult();
        result.ifPresent(res -> {
            if (!res.getName().isEmpty()) {
                try {
                    dataBase.addUser(result.get());
                    System.out.println(dataBase.getAllUsers());
                    Button button = new Button(result.get().getName());
                    String color = result.get().getColorNumber();
                    button.setStyle(" -fx-text-fill: black; -fx-background-color: "+color);
                    userLabel.add(button, 0, userAmount++);
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }



}