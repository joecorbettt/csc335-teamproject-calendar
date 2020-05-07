import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalTime;
import java.util.Date;

import org.junit.jupiter.api.Test;
import controller.CalendarController;
import view.CalendarView;
import model.Calendar;
import model.Event;

/**
 * This class contains test cases that helped show bugs in our program
 * 
 * 
 * @author Peter Vukasin and Joe Corbett
 *
 */
public class CalanderTest {

	@Test
	void testIsCorrect() {
		CalendarController controller = new CalendarController();
		Date date = new Date();
		LocalTime start = LocalTime.now();
		LocalTime end = LocalTime.now();
		Event event = new Event("tittle", date, start, end);
		event.setTitle("test");
		assertTrue(event.getTitle() == "test");
		event.setDate(date);
		assertTrue(event.getDate() == date);
		event.setStart(start);
		event.setEnd(end);
		assertTrue(event.getStart() == start);
		assertTrue(event.getEnd() == end);
		event.setNote("note test");
		assertTrue(event.getNote() == "note test");
		event.setLocation("test loc");
		assertTrue(event.getLocation() == "test loc");
		event.setCalendarTag("test cal");
		assertTrue(event.getCalendarTag() == "test cal");
		event.print();
		controller.addEvent(event);
		assertFalse(controller.containsDay(1, 1, 2020));
		controller.getMonth(1, 2020);
		controller.getWeek(1, 1, 2020);
		controller.getWeek(29, 1, 2020);
		controller.getWeek(32, 11, 2020);
		controller.getWeek(32, 2, 2020);
		controller.getWeek(32, 3, 2020);
		controller.getWeek(3, 3, 2020);
		controller.getWeek(3, 2, 2020);

		controller.getDay(1, 1, 2020);
		controller.getDay(29, 1, 2020);
		controller.getDay(32, 11, 2020);
		controller.getDay(32, 2, 2020);
		controller.getDay(32, 3, 2020);
		controller.getDay(3, 3, 2020);
		controller.getDay(3, 2, 2020);

		Calendar cal = new Calendar();
		cal.setMem(1);
		assertTrue(cal.getMem() == 1);
		cal.print();

	}

	// Corbet if you have time can you edit this testcase to have it hit
	// all the branches im having a tough time right now figuiring it out
	@Test
	void testIsCorrect2() {
		CalendarController controller = new CalendarController();
		Date date = new Date();
		LocalTime start = LocalTime.now();
		LocalTime end = LocalTime.now();
		Event event = new Event("tittle", date, start, end);
		event.setTitle("test");
		assertTrue(event.getTitle() == "test");
		event.setDate(date);
		assertTrue(event.getDate() == date);
		event.setStart(start);
		event.setEnd(end);
		assertTrue(event.getStart() == start);
		assertTrue(event.getEnd() == end);
		event.setNote("note test");
		assertTrue(event.getNote() == "note test");
		event.setLocation("test loc");
		assertTrue(event.getLocation() == "test loc");
		event.setCalendarTag("test cal");
		assertTrue(event.getCalendarTag() == "test cal");
		event.print();
		controller.addEvent(event);
		assertFalse(controller.containsDay(1, 1, 2020));
		controller.getMonth(1, 2020);
		controller.getWeek(1, 1, 2020);
		controller.getWeek(29, 1, 2020);
		controller.getWeek(32, 11, 2020);
		controller.getWeek(32, 2, 2020);
		controller.getWeek(32, 3, 2020);
		controller.getWeek(3, 3, 2020);
		controller.getWeek(3, 2, 2020);

		controller.getDay(1, 1, 2020);
		controller.getDay(29, 1, 2020);
		controller.getDay(32, 11, 2020);
		controller.getDay(32, 2, 2020);
		controller.getDay(32, 3, 2020);
		controller.getDay(3, 3, 2020);
		controller.getDay(3, 2, 2020);

		Calendar cal = new Calendar();
		cal.setMem(1);
		assertTrue(cal.getMem() == 1);
		cal.print();

	}

}
