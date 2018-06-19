package sample;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;

public class Controller {
    private DataBase dataBase;
    private Button addButton;
    private Button PDFButton;
    private ButtonCreationObserver buttonCreationObserver;
    private ButtonRemovalObserver buttonRemovalObserver;

    public ArrayList<RowConstraints> rowsconstraints = new ArrayList<>();
    public ArrayList<VBox> vboxes = new ArrayList<>();

    @FXML
    private RowConstraints row0, row1, row2, row3, row4, row5, row6, row7, row8, row9, row10, row11, row12, row13, row14;
    @FXML
    private VBox vbox0, vbox1, vbox2, vbox3, vbox4, vbox5, vbox6, vbox7, vbox8, vbox9, vbox10, vbox11, vbox12, vbox13, vbox14;

    public ChoiceBox choicebox;

    //called when .fxml file is loaded
    public void initialize(){

        for(int i = 0; i < 7; i++) {
            for (int j = 0; j < 15; j++) {
                Pane pane = new Pane();
                Label label = new Label();
                //label.setText(i + " " + j);
                //pane.getChildren().add(label);
                gridPaneDay.add(pane, i, j);
            }
        }

        loadNodesToLists();

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
    @FXML
    private GridPane gridPaneDay;
    private HashMap<Integer, Button> mapOfButtons = new HashMap<>();

    @FXML
    private AnchorPane root;


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
        WindowToCreateEvent windowToCreateEvent = null;
        try {
            windowToCreateEvent = new WindowToCreateEvent(this.dataBase.getAllUsers());
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
                        Pane node_result = null;
                        ObservableList<Node> childrens = gridPaneDay.getChildren();
                        System.out.println(eventField.getDayId() + " " + eventField.getHour());
                        int counter = 0;

                        node_result = (Pane) gridPaneDay.getChildren().get(eventField.getDayId() * 15 + eventField.getHour() + 1);
                        System.out.println(node_result);

                        ObservableList<Node> childrensOfResult = node_result.getChildren();
                        for(Node childs : childrensOfResult) {
                            counter++;
                        }

                        if(counter == 0) {
                            eventButton.setLayoutY(0);
                        } else {
                            eventButton.setLayoutY(counter * 25);
                        }

                        eventButton.setMaxHeight(20);
                        if (rowsconstraints.get(eventField.getHour()).getMinHeight() < (1+counter)*25) {
                            rowsconstraints.get(eventField.getHour()).setMinHeight((1+counter)*25);
                            vboxes.get(eventField.getHour()).setMinHeight((1+counter)*25);
                        }
                        node_result.getChildren().add(eventButton);
                        mapOfButtons.put(key, eventButton);
                    } catch(SQLException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void loadNodesToLists() {
        rowsconstraints.add(row0);  vboxes.add(vbox0);
        rowsconstraints.add(row1);  vboxes.add(vbox1);
        rowsconstraints.add(row2);  vboxes.add(vbox2);
        rowsconstraints.add(row3);  vboxes.add(vbox3);
        rowsconstraints.add(row4);  vboxes.add(vbox4);
        rowsconstraints.add(row5);  vboxes.add(vbox5);
        rowsconstraints.add(row6);  vboxes.add(vbox6);
        rowsconstraints.add(row7);  vboxes.add(vbox7);
        rowsconstraints.add(row8);  vboxes.add(vbox8);
        rowsconstraints.add(row9);  vboxes.add(vbox9);
        rowsconstraints.add(row10);  vboxes.add(vbox10);
        rowsconstraints.add(row11);  vboxes.add(vbox11);
        rowsconstraints.add(row12);  vboxes.add(vbox12);
        rowsconstraints.add(row13);  vboxes.add(vbox13);
        rowsconstraints.add(row14);  vboxes.add(vbox14);
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

    public void loadEventsFromDatabase() throws SQLException {
        List<Event> events = dataBase.getAllEvents();

        for(Event event: events) {
            EventField eventField = new EventField(event);
            Button eventButton = eventField.createButtonEvent();
            Pane node_result = null;
            ObservableList<Node> childrens = gridPaneDay.getChildren();
            System.out.println(eventField.getDayId() + " " + eventField.getHour());
            int counter = 0;

            node_result = (Pane) gridPaneDay.getChildren().get(eventField.getDayId() * 15 + eventField.getHour() + 1);
            System.out.println(node_result);

            ObservableList<Node> childrensOfResult = node_result.getChildren();
            for(Node childs : childrensOfResult) {
                counter++;
            }

            if(counter == 0) {
                eventButton.setLayoutY(0);
            } else {
                eventButton.setLayoutY(counter * 25);
            }

            eventButton.setMaxHeight(20);
            if (rowsconstraints.get(eventField.getHour()).getMinHeight() < (1+counter)*25) {
                rowsconstraints.get(eventField.getHour()).setMinHeight((1+counter)*25);
                vboxes.get(eventField.getHour()).setMinHeight((1+counter)*25);
            }

            node_result.getChildren().add(eventButton);
            mapOfButtons.put(eventField.getEventId(), eventButton);
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
        //sdfsdfsdfsdfsdf
        result.ifPresent(res -> {
            if (!res.getName().isEmpty()) {
                try {
                    dataBase.addUser(result.get());
                    System.out.println(dataBase.getAllUsers());
                } catch(SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }



}