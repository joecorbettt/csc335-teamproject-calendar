package model;
import java.time.LocalTime;
import java.util.Date;

/**
 * This class represents a standard calendar event. It includes a title, 
 * start date, start time, and end time. Additionally, it includes get
 * and set methods for each object field. This makes it easy to edit the 
 * Event object after it has already been instantiated. 
 * 
 * Lastly, there is a print() method - which provides a textual representation
 * of the Event object itself. 
 * 
 * One key point to note is that time is represented in 24-Hour format.
 * 
 * @author Joe Corbett & Peter Vukasin
 */
public class Event {
	String note, title, location;
	LocalTime start;
	LocalTime end;
	Date date;
	
	/**
	 * Parameterized Constructor for Event Object.
	 * 
	 * The location and note field do not need to be explicitly defined, 
	 * meaning they are instantiated with empty strings. If the user wants to
	 * utilize these fields, they can use the setter methods.
	 * 
	 * @param title - String representing title of Event 
	 * @param date - Date object representing the start date of Event
	 * @param start - LocalTime object representing start time of Event
	 * @param end - LocalTime object representing end time of Event
	 */
	public Event(String title, Date date, LocalTime start, LocalTime end) {
		this.title = title;
		this.date = date;
		this.start = start;
		this.end = end;
		this.note = "";
		this.location = "";	
	}
	
	
	/**
	 * Setter for Title Field
	 * 
	 * @param title - String representing title of Event 
	 */
	public void setTitle(String title) {
		this.title = title.trim();
	}
	
	
	/**
	 * Getter for Title Field
	 * 
	 * @return - String representing title of Event 
	 */
	public String getTitle() {
		return this.title;
	}
	
	
	/**
	 * Setter for Date Field 
	 * 
	 * @param date - Date object representing the start date of Event
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	/**
	 * Getter for Date Field 
	 * 
	 * @return - Date object representing the start date of Event 
	 */
	public Date getDate() {
		return this.date;
	}
	
	
	/**
	 * Setter for Start Field
	 *  
	 * @param start - LocalTime object representing start time of Event
	 */
	public void setStart(LocalTime start) {
		this.start = start;
	}
	
	
	/**
	 * Getter for Start Field 
	 * 
	 * @return - LocalTime object representing start time of Event
	 */
	public LocalTime getStart() {
		return this.start;
	}
	
	
	/**
	 * Setter for End Field 
	 * 
	 * @param end - LocalTime object representing end time of Event
	 */
	public void setEnd(LocalTime end) {
		this.end = end;
	}
	
	
	/**
	 * Getter for End Field 
	 * 
	 * @return - LocalTime object representing end time of Event
	 */
	public LocalTime getEnd() {
		return this.end;
	}
	
	
	/**
	 * Setter for Note Field 
	 * 
	 * @param note - String representing note field for Event 
	 */
	public void setNote(String note) {
		this.note = note.trim();
	}
	
	
	/**
	 * Getter for Note Field 
	 * 
	 * @return - String representing note field for Event 
	 */
	public String getNote() {
		return this.note;
	}
	
	
	/**
	 * Setter for Location Field 
	 * 
	 * @param location - String representing location field for Event 
	 */
	public void setLocation(String location) {
		this.location = location.trim();
	}
	
	
	/**
	 * Getter for Location Field 
	 * 
	 * @return - String representing location field for Event 
	 */
	public String getLocation() {
		return this.location;
	}
	
	/**
	 * Prints the Event Object 
	 */
	public void print() {
		System.out.printf("Title: %s\n", this.title);
		System.out.printf("Date: %d-%d-%d\n", 
				this.date.getMonth(), 
				this.date.getDate(), 
				this.date.getYear());
		System.out.printf("Start Time: %d:%d %s\n", 
				this.start.getHour() > 12 ? this.start.getHour() - 12 : this.start.getHour(),
				this.start.getMinute(),
				this.start.getHour() > 11 ? "PM" : "AM");
		System.out.printf("End Time: %d:%d %s\n", 
				this.end.getHour() > 12 ? this.end.getHour() - 12 : this.end.getHour(),
				this.end.getMinute(),
				this.end.getHour() > 11 ? "PM" : "AM");
	}
}
