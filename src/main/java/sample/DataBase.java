package sample;
import java.sql.*;

public class DataBase {
    Connection connection;
    private static int backupIndex = 0;
    private String eventNameTable = "Events";
    private String userNameTable = "Users";

    public void createConnectionToDerby() throws SQLException {
        String dbUrl = "jdbc:derby:src/main/resources/sample/dataBase;create=true";
        connection = DriverManager.getConnection(dbUrl);
    }

    public void createUserTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("Create table " + this.userNameTable + " (id int primary key, name varchar(30))");
    }

    public void createEventTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("Create table " + this.eventNameTable +
                " (id int primary key, id_user int, id_day int, id_hour int, message varchar(30))");
    }

    public void addRecordToEventTable(int id, int id_user, int id_day, int id_hour, String message) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO "+ this.eventNameTable + " values ("
                + id + "," +  id_user + "," + id_day + "," + id_hour + ", '" + message + "' )");
    }

    public void addRecordToUserTable(int id, String name) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO "+ this.userNameTable + " values (" + id + ",'" + name + "')");
    }

    public void showUserTable() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM "+this.userNameTable);
        while (result.next()) {
            System.out.printf("%d\t%s\n", result.getInt("id"), result.getString("name"));
        }
    }

    public void showEventTable() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM "+this.eventNameTable);
        while (result.next()) {
            System.out.printf("%d\t%d\t%d\t%d\t%s\n", result.getInt("id"),
                    result.getInt("id_user"),result.getInt("id_day"),result.getInt("id_hour"),result.getString("message"));
        }
    }

    public void backUpDatabase()throws SQLException {
        String backUpDirectory ="mybackups/";
        CallableStatement cs = connection.prepareCall("CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)");
        cs.setString(1, backUpDirectory);
        cs.execute();
        cs.close();
    }

    public void restoreDatabase() throws SQLException {
        String dbUrl = "jdbc:derby:src/main/resources/sample/dataBase;restoreFrom=mybackups/dataBase";
        String shutdownURL = "jdbc:derby:src/main/resources/sample/dataBase;shutdown=true";
        try {
            DriverManager.getConnection(shutdownURL);
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        connection = DriverManager.getConnection(dbUrl);
    }
}
