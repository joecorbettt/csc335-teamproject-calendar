package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

/**
 * This class represents the data structure that holds all the Calendar events.
 * This is accomplished through a 2D Map and an ArrayList. 
 * 
 * The first Map represents: Integer - YEAR; HashMap(Integer,Event) The second
 * Map represents: Integer - DATE; ArrayList(Event)
 * 
 * Essentially, the first key should represent year and the second should
 * represent the month. This makes using the Calendar class extremely simple and
 * organized.
 * 
 * One key point to note is that Months are 0-indexed and Days are not.
 * 
 * Another side note. javadoc was giving us errors when we tried useing the
 * greater than less than signs for Hashmaps so they were changed to parenthesis
 * @author Joe Corbett and Peter Vukasin
 *
 */
public class Calendar implements Serializable {
	Map<Integer, HashMap<Integer, ArrayList<Event>>> events;

	/**
	 * Constructor for Calendar Object.
	 * 
	 * This method simply instantiates the Map field, events.
	 */

	private int mem = 0;

	public Calendar() {
		this.events = new HashMap<Integer, HashMap<Integer, ArrayList<Event>>>();
	}

	/**
	 * Getter for Events field
	 * 
	 * @return events : returns a hashmap of a hashmap of an arraylist
	 */
	public Map<Integer, HashMap<Integer, ArrayList<Event>>> getEvents() {
		return this.events;

	}

	/**
	 * used mainly for testing purposes
	 * 
	 * @param num - number to set mem too
	 *            
	 */
	public void setMem(int num) {
		this.mem = num;
	}

	/**
	 * used mainly for testing purposes
	 * 
	 * @return num - what num mem is currently set too
	 *            
	 */
	public int getMem() {
		return mem;
	}

	/**
	 * Prints the Calendar Object
	 */
	public void print() {
		for (Integer year : events.keySet()) {
			for (Integer month : events.get(year).keySet()) {
				for (Event temp : events.get(year).get(month)) {
					temp.print();
					System.out.println();
				}
			}
		}
	}
}
