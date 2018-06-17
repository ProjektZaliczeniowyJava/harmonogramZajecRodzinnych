package sample;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DerbyDataBase implements DataBase {
    private Connection connection;
    private static int backupIndex = 0;
    private String eventNameTable = "Events";
    private String userNameTable = "Users";

    public void createConnectionToDerby() throws SQLException {
        String dbUrl = "jdbc:derby:src/main/resources/sample/dataBase;create=true";
        connection = DriverManager.getConnection(dbUrl);
		DatabaseMetaData dbm = connection.getMetaData();
		ResultSet tables = dbm.getTables(null, null, eventNameTable.toUpperCase(), null);
		if (tables.next()) {
			// Table exists
			System.out.println("tabele już istnieją");
		}
		else {
			// Table does not exist
			System.out.println("tabele jeszcze nie istnieją");
			createEventTable();
		}
		tables = dbm.getTables(null, null, userNameTable.toUpperCase(), null);
		if (tables.next()) {}//user table exists
		else {
			//user table does not exist
			createUserTable();
		}
    }

    public void createUserTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("Create table " + this.userNameTable + " (id int primary key, name varchar(30))");
    }

    public void createEventTable() throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("Create table " + this.eventNameTable +
                " (id int primary key, id_user int, day varchar(20), hhour int, mminute int, message varchar(30))");
    }


    public void addRecordToEventTable(int id, int id_user, String day, int hour, int min, String message) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO "+ this.eventNameTable + " values ("
                + id + "," +  id_user + ",'" + day + "'," + hour + ", " + min + ", '" + message + "' )");
    }

    public void addRecordToUserTable(int id, String name) throws SQLException {
        Statement stmt = connection.createStatement();
        stmt.executeUpdate("INSERT INTO "+ this.userNameTable + " values (" + id + ",'" + name + "')");
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

	@Override
	public List<Event> getAllEvents() throws SQLException {
		List<Event> list = new ArrayList<>();
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = connection.createStatement();
			myRs = myStmt.executeQuery("select * from events order by id");

			while (myRs.next()) {
				Event tempEvent = convertRowToEvent(myRs);
				list.add(tempEvent);
			}

			return list;
		} finally {
			DBUtils.close(myStmt, myRs);
		}
	}
	
	public Event convertRowToEvent(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("id");
		int id_user = myRs.getInt("id_user");
		String day = myRs.getString("day");
		int hour = myRs.getInt("hhour");
		int minute = myRs.getInt("mminute");
		String message = myRs.getString("message");

		Event tempEvent = new Event(id, id_user, day, hour, minute, message);

		return tempEvent;
	}

	public List<User> getAllUsers() throws SQLException {
		List<User> list = new ArrayList<>();
		Statement myStmt = null;
		ResultSet myRs = null;

		try {
			myStmt = connection.createStatement();
			myRs = myStmt.executeQuery("select * from users order by id");

			while (myRs.next()) {
				User tempUser = convertRowToUser(myRs);
				list.add(tempUser);
			}

			return list;
		} finally {
			DBUtils.close(myStmt, myRs);
		}
	}
	
	public User convertRowToUser(ResultSet myRs) throws SQLException {

		int id = myRs.getInt("id");
		String name = myRs.getString("name");

		User tempUser = new User( id, name);

		return tempUser;
	}
	
	public void deleteEvent(int id) throws SQLException{
		PreparedStatement myStmt = null;
		
		try {
			myStmt = connection.prepareStatement(
					"delete from events where id =?");

			myStmt.setInt(1, id);
			myStmt.executeUpdate();
		}
		finally {
			DBUtils.close(myStmt);
		}
				
	}
	
	public void addEvent(Event event) throws SQLException {
		PreparedStatement myStmt = null;

		try {
			myStmt = connection.prepareStatement(
					"INSERT INTO events values (?, ?, ?, ?, ?, ?)",   
					Statement.RETURN_GENERATED_KEYS);

			myStmt.setInt(1, event.getId());
			myStmt.setInt(2, event.getId_user());
			myStmt.setString(3, event.getDay());
			myStmt.setInt(4, event.getHour());
			myStmt.setInt(5, event.getMin());
			myStmt.setString(6, event.getMessage());
	
			myStmt.executeUpdate();

		} finally {
			DBUtils.close(myStmt);
		}
	}
	
	public void updateEvent(Event event) throws SQLException {
		PreparedStatement myStmt = null;

		try {
			myStmt = connection.prepareStatement(
					"update events set id=?, id_user=?, day=?, hhour=?, mminute=?, message=?");

			myStmt.setInt(1, event.getId());
			myStmt.setInt(2, event.getId_user());
			myStmt.setString(3, event.getDay());
			myStmt.setInt(4, event.getHour());
			myStmt.setInt(5, event.getMin());
			myStmt.setString(6, event.getMessage());
			myStmt.execute("SET NAMES 'utf8'");

			myStmt.executeUpdate();
			
		} finally {
			DBUtils.close(myStmt);
		}

	}
}
