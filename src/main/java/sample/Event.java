package sample;

public class Event {
	private int id;
	private int id_user;
	private String day;
	private String hour;
	private String message;
	
	public Event(int id, int id_user, String day, String hour, String message) {
		this.id = id;
		this.id_user = id_user;
		this.day = day;
		this.hour = hour;
		this.message = message;
	}
	
	public Event(int id_user, String day, String hour, String message) {
		this.id = 0;
		this.id_user = id_user;
		this.day = day;
		this.hour = hour;
		this.message = message;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getId_user() {
		return id_user;
	}

	public void setId_user(int id_user) {
		this.id_user = id_user;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
