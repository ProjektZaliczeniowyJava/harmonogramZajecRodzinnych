package sample;
import java.sql.*;

public class DataBase {
    Connection connection;
    private String eventNameTable = "Events";
    private String userNameTable = "Users";

    public void connectionToDerby() throws SQLException {
        // -------------------------------------------
        // URL format is
        // jdbc:derby:<local directory to save data>
        // -------------------------------------------
        String dbUrl = "jdbc:derby:src/main/resources/sample/dataBase;create=true";
        connection = DriverManager.getConnection(dbUrl);
    }

    public void createUserTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("Create table " + this.userNameTable + " (id int primary key, name varchar(30))");


    }


    public void addRecordToUserTable(int id, String name) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO "+ this.userNameTable + " values (" + id + ",'" + name + "')");


    }

    public void showDatabase() throws SQLException {
        Statement stmt = connection.createStatement();
        ResultSet result = stmt.executeQuery("SELECT * FROM users");

        while (result.next()) {
            System.out.printf("%d\t%s\n", result.getInt("id"), result.getString("name"));
        }
    }

    public void backUpDatabase()throws SQLException {
        String backUpDirectory ="mybackups/"+ "kopia";
        CallableStatement cs = connection.prepareCall("CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)");
        cs.setString(1, backUpDirectory);
        cs.execute();
        cs.close();
        System.out.println("backed up database to "+backUpDirectory);
    }

    public void restoreDatabase() throws SQLException {
        String dbUrl = "jdbc:derby:src/main/resources/sample/dataBase;restoreFrom=mybackups/kopia/dataBase";
        String shutdownURL = "jdbc:derby:src/main/resources/sample/dataBase;shutdown=true";
        try {
            DriverManager.getConnection(shutdownURL);
        } catch (SQLException e) {
//            e.printStackTrace();
        }
        connection = DriverManager.getConnection(dbUrl);
    }
}
