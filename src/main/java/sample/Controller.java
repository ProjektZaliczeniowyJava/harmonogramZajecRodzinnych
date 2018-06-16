package sample;

import java.sql.SQLException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;


public class Controller {
	
	private DataBase dataBase;
    private Button addButton;
	
    public Controller(DataBase db) {
		this.dataBase = db;
	}
    
    public Controller() {
		this.dataBase =null;
	}
    
	@FXML
    public void clickAddButton(ActionEvent actionEvent) {
        DialogWithUser dialogWithUser = new DialogWithUser();
        dialogWithUser.createUserInput();
        Optional<Event> result = dialogWithUser.getInputResult();
        result.ifPresent(pair -> {
            System.out.println("ID:" + pair.getId() + "\nID_USER:" + pair.getId_user()+
                    "\nDAY:"+pair.getDay()+"\nHOUR:"+pair.getHour()+"\nMINUTES:"+pair.getMin()+"\nMESSAGE:"+pair.getMessage());
            
            Event event = new Event(pair.getId(), pair.getId_user(), pair.getDay(), pair.getHour(), pair.getMin(), pair.getMessage());
           
            try {
				dataBase.addEvent(event);
			} catch (SQLException e) {
				//e.printStackTrace();
			}
        });
    }


}
