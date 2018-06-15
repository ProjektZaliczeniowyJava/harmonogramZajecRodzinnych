package sample;
import java.sql.*;

public class DataBase {
    Connection conn;

    public void connectionToDerby() throws SQLException {
        // -------------------------------------------
        // URL format is
        // jdbc:derby:<local directory to save data>
        // -------------------------------------------
        String dbUrl = "jdbc:derby:src/main/resources/sample/dataBase;create=true";
        conn = DriverManager.getConnection(dbUrl);
    }

    public void normalDbUsage() throws SQLException {
        Statement stmt = conn.createStatement();


        // create table
        stmt.executeUpdate("Create table users (id int primary key, name varchar(30))");

        // insert 2 rows
        stmt.executeUpdate("insert into users values (1,'tom')");
        stmt.executeUpdate("insert into users values (2,'peter')");

        // query
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");

        // print out query result
        while (rs.next()) {
            System.out.printf("%d\t%s\n", rs.getInt("id"), rs.getString("name"));
        }

        // drop table
       // stmt.executeUpdate("Drop Table users");

    }

    public void addRecordToDatabase() throws SQLException {
        Statement stmt = conn.createStatement();
        stmt.executeUpdate("INSERT INTO users values (4, 'karolina')");


    }

    public void showDatabase() throws SQLException {
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM users");

        // print out query result
        while (rs.next()) {
            System.out.printf("%d\t%s\n", rs.getInt("id"), rs.getString("name"));
        }
    }

    public void backUpDatabase()throws SQLException {
        String backUpDirectory ="mybackups/"+ "kopia";
        CallableStatement cs = conn.prepareCall("CALL SYSCS_UTIL.SYSCS_BACKUP_DATABASE(?)");
        cs.setString(1, backUpDirectory);
        cs.execute();
        cs.close();
        System.out.println("backed up database to "+backUpDirectory);
    }

    public void restoreDatabase() throws SQLException {
        String dbUrl = "jdbc:derby:src/main/resources/sample/dataBase;restoreFrom=mybackups/kopia/dataBase";
        String nsURL1 = "jdbc:derby:src/main/resources/sample/dataBase;shutdown=true";
        try {
            DriverManager.getConnection(nsURL1);
        } catch (SQLException e) {
            ;
        }
        conn = DriverManager.getConnection(dbUrl);
    }
}
