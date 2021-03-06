package sample.utilities;

public class Event {
	private int id;
	private int id_user;
	private String day;
	private int hour;
	private int minute;
	private String message;
	
	public Event(int id, int id_user, String day, int hour, int min, String message) {
		this.id = id;
		this.id_user = id_user;
		this.day = day;
		this.hour = hour;
		this.minute = min;
		this.message = message;
	}
	
	public Event(int id_user, String day, int hour, int min, String message) {
		this.id = 0;
		this.id_user = id_user;
		this.day = day;
		this.minute = min;
		this.hour = hour;
		this.minute = min;
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

	public int getHour() {
		return hour;
	}

	public void setHour(int hour) {
		this.hour = hour;
	}

	public int getMin() {
		return minute;
	}

	public void setMin(int min) {
		this.minute = min;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public String toString() {
		return String.format("Event [%s %s %s %s %s %s]", this.id, this.id_user, this.day, this.hour, this.minute, this.message);
	}
}
