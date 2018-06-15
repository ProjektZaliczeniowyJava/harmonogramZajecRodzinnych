package sample;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import java.util.Optional;

public class DialogWithUser {
    private Optional<Pair<String, String>> result;

    public DialogWithUser() {
        this.result = Optional.empty();
    }

    public void createUserInput() {
        Dialog<Pair<String, String>> dialog = new Dialog<>();
        dialog.setTitle("Dodaj wydarzenie ");

        ButtonType loginButtonType = new ButtonType("OK", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(20, 150, 10, 10));

        TextField eventInformation = new TextField();
        eventInformation.setPromptText("Wydarzenie");
        TextField eventHour = new TextField();
        eventHour.setPromptText("Godzina");

        gridPane.add(new Label("Dodaj treść wydarzenia "), 0, 0);
        gridPane.add(eventInformation, 0, 1);
        gridPane.add(new Label("Dodaj godzine:"), 0, 2);
        gridPane.add(eventHour, 0, 3);

        dialog.getDialogPane().setContent(gridPane);
        Platform.runLater(() -> eventInformation.requestFocus());

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return new Pair<>(eventInformation.getText(), eventHour.getText());
            }
            return null;
        });
        this.result = dialog.showAndWait();
    }

    public Optional<Pair<String, String>> getInputResult() {
        //TODO dodać obsługe kiedy null
        return this.result;
    }
}
