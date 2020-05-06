package controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import model.Calendar;
import model.Event;

public class CalendarController {
	Calendar model;
	ArrayList<Integer> monthsW31 = new ArrayList<Integer>(Arrays.asList(0,2,4,6,7,9,11));
	
	public CalendarController(Calendar model) {
		this.model = model;
	}
	
	public CalendarController() {
		this(new Calendar());
	}
	
	/**
	 * Add an event to the Map data structure, which holds all the events. This 
	 * method specifically organizes the event into a specific year and month 
	 * category of the data structure. 
	 * 
	 * @param event - Event object to add to the events Map
	 */
	public void addEvent(Event event) {
		Integer year = new Integer(event.getDate().getYear());
		Integer month = new Integer(event.getDate().getMonth());
		if (containsYear(year)) {
			if (containsMonth(month, year)) {
				model.getEvents().get(year).get(month).add(event);
			} else {
				model.getEvents().get(year).put(month, new ArrayList<Event>(Arrays.asList(event)));
			}
		} else {
			HashMap<Integer, ArrayList<Event>> monthMap = new HashMap<Integer, ArrayList<Event>>();
			model.getEvents().put(year, monthMap);
			model.getEvents().get(year).put(month, new ArrayList<Event>(Arrays.asList(event)));
		}
	}
	
	/**
	 * Identifies if event(s) have been planned for this specific day
	 * 
	 * @param day - an Integer representing day
	 * @param month - an Integer representing month
	 * @param year - an Integer representing year
	 * @return boolean
	 */
	public boolean containsDay(Integer day, Integer month, Integer year) {
		if (containsMonth(month, year)) {
			for (Event temp: getMonth(month, year)) {
				if (temp.getDate().compareTo(new Date(year, month, day)) == 0) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Identifies if event(s) have been planned for this specific month. 
	 * 
	 * Due to Java's utilization of short circuit evaluation, the second half of the 
	 * boolean should never return, when trying to get a K,V pair. 
	 * 
	 * @param year - an Integer representing year
	 * @param month - an Integer representing month 
	 * @return boolean 
	 */
	public boolean containsMonth(Integer month, Integer year) {
		return containsYear(year) && model.getEvents().get(year).containsKey(month);
	}
	
	/**
	 * Identifies if event(s) have been planned for this specific year. 
	 * 
	 * @param year - an Integer representing year
	 * @return boolean
	 */
	public boolean containsYear(Integer year) {
		return model.getEvents().containsKey(year);
	}
	
	/**
	 * Return an ArrayList of events occurring within that specified month.
	 * 
	 * @param month - an Integer representing month
	 * @param year - an Integer representing year
	 * @return ArrayList<Event> of all events occurring that month
	 */
	public ArrayList<Event> getMonth(Integer month, Integer year) {
		if (containsMonth(month, year)) {
			return model.getEvents().get(year).get(month);
		}
		return new ArrayList<Event>();
	}
	
	/**
	 * Return an ArrayList of events occurring within that specified week.
	 * 
	 * @param day - an Integer representing day
	 * @param month - an Integer representing month
	 * @param year - an Integer representing year
	 * @return ArrayList<Event> of all events occurring that week
	 */
	public ArrayList<Event> getWeek(Integer day, Integer month, Integer year) {
		ArrayList<Event> toReturn = new ArrayList<Event>();
		for (int i = 0; i < 7; i++) { 
			// Wrap February 
			if (month.equals(new Integer(1)) && day > 28) {
				month = new Integer(2);
				day = 1;
			}
			// Wrap January, March, May, July, August, October, December
			if (monthsW31.contains(month) && day > 31) {
				// Handle specifically December
				if (month.equals(11)) {
					year++;
					month = 0;
					day = 1;
				} else {
					month += 1;
					day = 1;
				}
			}
			// Wrap Rest of Months
			if (day > 30) {
				month += 1;
				day = 1;
			}
			for (Event temp: getDay(day, month, year)) {
				toReturn.add(temp);
			}
			day++;
		}
		return toReturn;
	}
	
	/**
	 * Return an ArrayList of events occurring within that specified day.
	 * 
	 * @param day -  an Integer representing year
	 * @param month - an Integer representing year
	 * @param year -  an Integer representing year
	 * @return ArrayList<Event> of events occurring that day
	 */
	public ArrayList<Event> getDay(Integer day, Integer month, Integer year) {
		ArrayList<Event> toReturn = new ArrayList<Event>();
		// Wrap February 
		if (month.equals(new Integer(1)) && day > 28) {
			month = new Integer(2);
			day = 1;
		}
		// Wrap January, March, May, July, August, October, December
		if (monthsW31.contains(month) && day > 31) {
			// Handle specifically December
			if (month.equals(11)) {
				year++;
				month = 0;
				day = 1;
			} else {
				month += 1;
				day = 1;
			}
		}
		// Wrap Rest of Months
		if (day > 30) {
			month += 1;
			day = 1;
		}
		// Get Day After Checking Bounds
		if (containsDay(day, month, year)) {
			for (Event temp: getMonth(month, year)) {
				if (temp.getDate().compareTo(new Date(year, month, day)) == 0) {
					toReturn.add(temp);
				}
			}
		}
		return toReturn;
	}
}
