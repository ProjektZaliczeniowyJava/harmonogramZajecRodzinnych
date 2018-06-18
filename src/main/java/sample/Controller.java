package sample;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import javax.imageio.ImageIO;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.print.PrinterJob;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;

public class Controller {
	private DataBase dataBase;
	private Button addButton;
	private Button PDFButton;
	private ButtonObserver buttonObserver;

	// called when .fxml file is loaded
	public void initialize() {
		buttonObserver = ButtonObserver.getInstance();
		addToObserver();
		dataBase = new DerbyDataBase();
		try {
			dataBase.createConnectionToDerby();
			// tutaj pobieramy dane z bazy, wypeniamy mapę przycisków, oraz je wyswietlamy
			// na planszy
			loadEventsFromDatabase();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@FXML
	private GridPane gridPaneDay;
	@FXML
	private AnchorPane root;
	private HashMap<Integer, Button> mapOfButtons = new HashMap<>();

	public void addToObserver() {
		this.buttonObserver.addControllerToObserver(this);
	}

	private void removeFromGridPane(GridPane gridPane, Button button) {
		// podajemy id button i bierzemy button z map, usuwamy z map
		// zabrac to stad potem
		gridPane.getChildren().remove(button);
	}

	public void clickAddButton() {
		WindowToCreateEvent windowToCreateEvent = new WindowToCreateEvent();
		windowToCreateEvent.createUserInput();
		Optional<Event> result = windowToCreateEvent.getInputResult();

		result.ifPresent(pair -> {
			if (!pair.getMessage().isEmpty()) {
				int key = 0;
				try {
					// Tu masz key czyli id_wydarzenia
					key = dataBase.addEvent(result.get());
				} catch (SQLException e) {

				}

				Event event = new Event(key, result.get().getId_user(), result.get().getDay(), result.get().getHour(),
						result.get().getMin(), result.get().getMessage());
				EventField eventField = new EventField(event);
				Button eventButton = eventField.createButtonEvent();
				gridPaneDay.add(eventButton, eventField.getDayId(), eventField.getHour());
				// zmieniałem na key chyba o to chodziło
				mapOfButtons.put(key, eventButton);
			}
		});

		// tylko dla testow jak dodasz wydarzenie dla osoba 1 , a drugie dla innej osoby
		// z minutami 10 to pierwsze znika :)
		result.ifPresent(pair -> {
			if (pair.getMin() == 10) {
				removeFromGridPane(gridPaneDay, mapOfButtons.get(2));
			}
		});
	}

	public void clickEventButton(int idEvent) {
		// TODO dodac aktualizacje danych w bazie na podstawie nowego event, stare
		// usuwamy?
		// na sztywno wydarzenie do edytowania, powinno odczytywac z bazy danych do
		// listy i potem z listy czytamy wydarzenie

		WindowToEditEvent windowToEditEvent = null;
		try {
			windowToEditEvent = new WindowToEditEvent(dataBase.getEvent(idEvent));
		} catch (SQLException exe) {

		}

		windowToEditEvent.createUserInput();
		Optional<Event> result = windowToEditEvent.getInputResult();

		result.ifPresent(pair -> {
			if (!pair.getMessage().isEmpty()) {

				try {
					dataBase.updateEvent(idEvent, result.get());
				} catch (SQLException exe) {
					exe.printStackTrace();
				}

				Event event = new Event(idEvent, result.get().getId_user(), result.get().getDay(),
						result.get().getHour(), result.get().getMin(), result.get().getMessage());
				EventField eventField = new EventField(event);
				Button eventButton = eventField.createButtonEvent();

				gridPaneDay.add(eventButton, eventField.getDayId(), eventField.getHour());
				mapOfButtons.put(idEvent, eventButton);
			}
		});
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

	public void loadEventsFromDatabase() throws SQLException {
		List<Event> events = dataBase.getAllEvents();

		for (Event event : events) {
			EventField eventField = new EventField(event);
			Button eventButton = eventField.createButtonEvent();

			gridPaneDay.add(eventButton, eventField.getDayId(), eventField.getHour());
			mapOfButtons.put(eventField.getEventId(), eventButton);
		}
	}

}
