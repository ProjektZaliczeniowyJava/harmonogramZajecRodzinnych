package sample;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public interface DataBase {
    void createConnectionToDerby() throws SQLException;

    void createUserTable() throws SQLException;

    void createEventTable() throws SQLException;

    void addRecordToEventTable(int id, int id_user, String day, int hour, int minute, String message) throws SQLException;

    void addRecordToUserTable(int id, String name) throws SQLException;

    void showUserTable() throws SQLException;

    void showEventTable() throws SQLException;

    void backUpDatabase()throws SQLException;

    void restoreDatabase() throws SQLException;
    
    List<User> getAllUsers() throws SQLException;

    List<Event> getAllEvents() throws SQLException;
    
    Event convertRowToEvent(ResultSet myRs) throws SQLException;
    
    User convertRowToUser(ResultSet myRs) throws SQLException;
}
