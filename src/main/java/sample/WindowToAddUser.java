package sample;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.Optional;

public class WindowToAddUser {
    private Optional<User> result;

    public WindowToAddUser() { this.result = null; }

    public void createUserInput() {
        Dialog<User> dialog = new Dialog<>();
        dialog.setTitle("Dodaj członka rodziny ");
        Label user = new Label("Imię członka rodziny");
        Label color = new Label("Wybierz kolor");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        ((Button) dialog.getDialogPane().lookupButton(ButtonType.OK)).setText("DODAJ CZŁONKA RODZINY");
        ((Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL)).setText("ZAMKNIJ OKNO");

        TextField userName = new TextField();
        userName.setPromptText("Imię członka rodziny");

        ColorPicker colorPicker = new ColorPicker();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(5);
        gridPane.setHgap(5);

        gridPane.add(user, 0, 0);
        gridPane.add(userName, 1, 0);
        gridPane.add(color,2, 0);
        gridPane.add(colorPicker, 3, 0);

        dialogPane.setContent(gridPane);
        Platform.runLater(() -> userName.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return new User(userName.getText(), colorPicker.getValue());
            }
            return null;
        });

        this.result = dialog.showAndWait();
    }

    public Optional<User> getInputResult() {
        return this.result;
    }
}
