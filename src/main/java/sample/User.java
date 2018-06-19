package sample;

import javafx.scene.paint.Color;

public class User {
	private int id;
	private String name;
	private Color color;
	
	public User(int id, String name) {
		this.id = id;
		this.name = name;
		this.color = Color.WHITE;
	}
	
	public User(String name) {
		this.id = 0;
		this.name = name;
		this.color = Color.WHITE;
	}

	public User(String name, Color color) {
		this.id = 0;
		this.name = name;
		this.color = color;
	}
	public User(int id, String name, Color color) {
		this.id = id;
		this.name = name;
		this.color = color;
	}

	public String getColorNumber() {
		return  "#" + Integer.toHexString(color.hashCode());
	}
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setColor(Color color) { this.color = color; }

	public String getColor() { return color.toString(); }

	public String toString() {
		return String.format("User [%s %s]", this.id, this.name);
	}
}
