package view;

import java.time.LocalDate;
import java.time.LocalTime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import controller.CalendarController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Calendar;
import model.Event;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * This class represents the view that lets the user interact with the calendar.
 * It works by accessing the controller and using it as the glue between the
 * view and the model where all the data is stored.
 * 
 * Our "Wow" Factors include the use of color coding and highlighitng the day
 * that is currently is. Furthermore whenever you open the app it opens to the
 * current date.
 * 
 * @author Peter Vukasin and Joe Corbett
 *
 */

public class CalendarView extends Application {

	private BorderPane window;
	private VBox topBox, eventVBox;
	private Label calendarLabel, bottomLabel;
	private TextField eventTitle, eventSHR, eventSM, eventEHR, eventEM;
	private TextField eventDay, eventMonth, eventYear, eventLocation, eventNote;
	private CalendarController controller;
	private HBox viewOptions, viewCalendar;
	private Button moreCal, clearAll, previous, next, dayButton, weekButton, monthButton, addEvent, createCal;
	private ChoiceBox<String> cb;
	private HashMap<String, String> calendarColors;
	private int type = 0;

	ArrayList<Integer> monthsW31 = new ArrayList<Integer>(Arrays.asList(0, 2, 4, 6, 7, 9, 11));
	//
	private static Calendar calendar1 = null;
	private static String filename = "test.txt";
	private int calendarNum = 1;
	private LocalDate dateFormat;
	private static String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
	private String[] arrDate = date.split("-");
	private int currentYear = Integer.valueOf(arrDate[2]);
	private int currentMonth = Integer.valueOf(arrDate[1]);
	private int currentDay = Integer.valueOf(arrDate[0]);
	private int monthTemp = currentMonth - 1, yearTemp = currentYear, dayTemp = currentDay;
	ArrayList<Integer> calanders = new ArrayList<Integer>(Arrays.asList(0));
	private int whichCalander = 0;

	@Override

	/**
	 * The start method is what is called from main (after main does some checking
	 * for saved data) and will create the appropriate calander.
	 * 
	 * 
	 */

	public void start(Stage primaryStage) {
		if (calendar1 == null) {
			controller = new CalendarController();
		} else {
			controller = new CalendarController(calendar1);
		}

		window = new BorderPane();
		// Set calendarColors for various calendar Options
		calendarColors = new HashMap<String, String>();
		calendarColors.put("Personal", "-fx-background-color: #ff0000;");
		calendarColors.put("Work", "-fx-background-color: #00ffea;");
		calendarColors.put("Random", "-fx-background-color: #fffb00;");
		calendarColors.put("School", "-fx-background-color: #E9967A;");
		calendarColors.put("Hobbies", "-fx-background-color: #FFDAB9;");
		calendarColors.put("Birthdays", "-fx-background-color: #778899;");
		calendarColors.put("Secret", "-fx-background-color: #808000;");

		createTop();
		createBottom();
		addActionHandlers();

		window.setTop(topBox);
		// By Default, have the monthView pull up
		window.setCenter(createMonthBox(monthTemp, yearTemp));
		window.setBottom(bottomLabel);
		BackgroundFill bf = new BackgroundFill(Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY);

		Background background = new Background(bf);
		window.setBackground(background);

		updateView();

		Scene scene = new Scene(window, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Corbett & Vukasin - Calendar Application");
		primaryStage.show();
	}

	/**
	 * The addActionHandlers method is used to add in all of the code used in each
	 * of our buttons and text fields. These buttons include moreCal, clearAll,
	 * previous, next, dayButton, weekButton, monthButton, addEvent, and createCal.
	 * 
	 * 
	 */
	public void addActionHandlers() {
		moreCal.setOnAction((event) -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Calander Selection");
			alert.setHeaderText("Select the calander you want to use");
			// Set up HBox and ChoiceBoxes

			ComboBox comboBox = new ComboBox(FXCollections.observableArrayList(calanders));

			EventHandler<ActionEvent> event1 = new EventHandler<ActionEvent>() {
				public void handle(ActionEvent e) {
					whichCalander = (int) comboBox.getValue();
				}
			};

			// Set on action
			comboBox.setOnAction(event1);

			HBox calHBox = new HBox();
			VBox calVBox = new VBox();

			Button button = new Button("Select Calander");

			button.setOnAction(action -> {
				calanders.add(calanders.size());
			});
			ButtonType done = new ButtonType("Done");
			ButtonType cancel = new ButtonType("Cancel");
			calHBox.getChildren().add(comboBox);
			calHBox.getChildren().add(button);
			alert.getDialogPane().setContent(calHBox);
			alert.getButtonTypes().setAll(done, cancel);
			Optional<ButtonType> result = alert.showAndWait();
		});

		dayButton.setOnAction((event) -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Day Selection");
			alert.setHeaderText("Select Month, Day, Year");
			// Set up HBox and ChoiceBoxes
			HBox dayHBox = new HBox();
			ChoiceBox<Integer> months = new ChoiceBox<Integer>();
			months.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
			ChoiceBox<Integer> days = new ChoiceBox<Integer>();
			days.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
					24, 25, 26, 27, 28, 29, 30, 31);
			ChoiceBox<Integer> years = new ChoiceBox<Integer>();
			years.getItems().addAll(2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030);
			dayHBox.getChildren().add(new Label(" Month:  "));
			dayHBox.getChildren().add(months);
			dayHBox.getChildren().add(new Label(" Day: "));
			dayHBox.getChildren().add(days);
			dayHBox.getChildren().add(new Label(" Year: "));
			dayHBox.getChildren().add(years);
			alert.getDialogPane().setContent(dayHBox);
			ButtonType done = new ButtonType("Done");
			ButtonType cancel = new ButtonType("Cancel");
			alert.getButtonTypes().setAll(done, cancel);
			Optional<ButtonType> result = alert.showAndWait();
			// Set DayView
			if (result.get() == done) {
				type = 1;
				monthTemp = months.getValue() - 1;
				dayTemp = days.getValue();
				yearTemp = years.getValue();
				window.setCenter(dayView(days.getValue(), months.getValue() - 1, years.getValue() - 1900));
				updateView();
			}
		});

		weekButton.setOnAction((event) -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Week Selection");
			alert.setHeaderText("Select Month, Day, Year");
			// Set up HBox and ChoiceBoxes
			HBox dayHBox = new HBox();
			ChoiceBox<Integer> months = new ChoiceBox<Integer>();
			months.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
			ChoiceBox<Integer> days = new ChoiceBox<Integer>();
			days.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23,
					24, 25, 26, 27, 28, 29, 30, 31);
			ChoiceBox<Integer> years = new ChoiceBox<Integer>();
			years.getItems().addAll(2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030);
			dayHBox.getChildren().add(new Label(" Month:  "));
			dayHBox.getChildren().add(months);
			dayHBox.getChildren().add(new Label(" Day: "));
			dayHBox.getChildren().add(days);
			dayHBox.getChildren().add(new Label(" Year: "));
			dayHBox.getChildren().add(years);
			alert.getDialogPane().setContent(dayHBox);
			ButtonType done = new ButtonType("Done");
			ButtonType cancel = new ButtonType("Cancel");
			alert.getButtonTypes().setAll(done, cancel);
			Optional<ButtonType> result = alert.showAndWait();
			// Set DayView
			if (result.get() == done) {
				type = 2;
				monthTemp = months.getValue() - 1;
				dayTemp = days.getValue();
				yearTemp = years.getValue();
				window.setCenter(createWeekBox(days.getValue(), months.getValue() - 1, years.getValue()));
				updateView();
			}
		});

		monthButton.setOnAction((event) -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Month Selection");
			alert.setHeaderText("Select Month, Year");
			// Set up HBox and ChoiceBoxes
			HBox dayHBox = new HBox();
			ChoiceBox<Integer> months = new ChoiceBox<Integer>();
			months.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12);
			ChoiceBox<Integer> years = new ChoiceBox<Integer>();
			years.getItems().addAll(2020, 2021, 2022, 2023, 2024, 2025, 2026, 2027, 2028, 2029, 2030);
			dayHBox.getChildren().add(new Label(" Month:  "));
			dayHBox.getChildren().add(months);
			dayHBox.getChildren().add(new Label(" Year: "));
			dayHBox.getChildren().add(years);
			alert.getDialogPane().setContent(dayHBox);
			ButtonType done = new ButtonType("Done");
			ButtonType cancel = new ButtonType("Cancel");
			alert.getButtonTypes().setAll(done, cancel);
			Optional<ButtonType> result = alert.showAndWait();
			// Set DayView
			if (result.get() == done) {
				type = 0;
				monthTemp = months.getValue();
				yearTemp = years.getValue();
				window.setCenter(createMonthBox(months.getValue() - 1, years.getValue()));
				updateView();
			}
		});

		addEvent.setOnAction((event) -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("New Event");
			alert.setHeaderText("Create your event below:");
			alert.getDialogPane().setContent(createEventBox());
			ButtonType done = new ButtonType("Done");
			ButtonType cancel = new ButtonType("Cancel");
			alert.getButtonTypes().setAll(done, cancel);
			Optional<ButtonType> result = alert.showAndWait();
			// Create Event and Add to Calendar
			if (result.get() == done) {
				LocalTime start = LocalTime.of(Integer.parseInt(eventSHR.getText().trim()),
						Integer.parseInt(eventSM.getText().trim()));
				LocalTime end = LocalTime.of(Integer.parseInt(eventEHR.getText().trim()),
						Integer.parseInt(eventEM.getText().trim()));
				Date date = new Date(Integer.parseInt(eventYear.getText().trim()) - 1900,
						Integer.parseInt(eventMonth.getText().trim()) - 1, Integer.parseInt(eventDay.getText().trim()));
				Event newEvent = new Event(eventTitle.getText().trim(), date, start, end);
				if (!eventLocation.getText().equals("Location") && !eventLocation.getText().isEmpty()) {
					newEvent.setLocation(eventLocation.getText().trim());
				}
				if (!eventNote.getText().equals("Notes") && !eventNote.getText().isEmpty()) {
					newEvent.setNote(eventNote.getText().trim());
				}
				controller.addEvent(newEvent);
				newEvent.setCalendarTag(cb.getValue());
				System.out.println("");
				// vuke
				try {
					FileOutputStream file = new FileOutputStream(filename);
					ObjectOutputStream out = new ObjectOutputStream(file);
					calendar1 = controller.getCal();
					out.writeObject(calendar1);
					out.close();
					file.close();
					// System.out.println("test1");
					// controller.getCal(calendarNum).print();

				} catch (IOException ex) {
					System.out.println("IOException is caught 1" + ex);
				}
				updateView();
			}
		});

		previous.setOnAction((event) -> {
			if (type == 0) {
				if (monthTemp != 0) {
					monthTemp--;
					window.setCenter(createMonthBox(monthTemp, yearTemp));

				} else {

					window.setCenter(createMonthBox(monthTemp, yearTemp));
				}
			} else if (type == 1) {
				if (dayTemp != 1) {
					dayTemp--;
					window.setCenter(dayView(dayTemp, monthTemp, yearTemp - 1900));
				} else {
					window.setCenter(dayView(dayTemp, monthTemp, yearTemp - 1900));
				}

			} else if (type == 2) {
				if (dayTemp > 7) {
					dayTemp -= 7;
					window.setCenter(createWeekBox(dayTemp, monthTemp, yearTemp));
				} else {
					dayTemp = 1;
					window.setCenter(createWeekBox(dayTemp, monthTemp, yearTemp));
				}

			}
			updateView();
		});
		clearAll.setOnAction((event) -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Clear All Events");
			alert.setHeaderText("Are you sure you want to clear all your saved events?");
			ButtonType done = new ButtonType("Yes, delete them!");
			ButtonType cancel = new ButtonType("No I want my events!");
			alert.getButtonTypes().setAll(done, cancel);
			Optional<ButtonType> result = alert.showAndWait();
			// Create Event and Add to Calendar
			if (result.get() == done) {
				try {
					new FileOutputStream(filename).close();
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				controller = new CalendarController();
				updateView();
			}
		});

		next.setOnAction((event) -> {
			if (type == 0) {
				if (monthTemp != 11) {
					monthTemp++;
					window.setCenter(createMonthBox(monthTemp, yearTemp));
				} else {

					window.setCenter(createMonthBox(monthTemp, yearTemp));
				}

			} else if (type == 1) {
				if (dayTemp != 31) {
					dayTemp++;
					window.setCenter(dayView(dayTemp, monthTemp, yearTemp - 1900));
				} else {
					window.setCenter(dayView(dayTemp, monthTemp, yearTemp - 1900));
				}

			} else if (type == 2) {
				if (dayTemp < 27) {
					dayTemp += 7;
					window.setCenter(createWeekBox(dayTemp, monthTemp, yearTemp));
				} else {
					window.setCenter(createWeekBox(dayTemp, monthTemp, yearTemp));
				}

			}

			updateView();
		});

	}

	/**
	 * The createWeekBox method creates an Hbox that we can put directly in the
	 * center of the calander for when we need to switch between views
	 * 
	 * @param days
	 *            - day user wants to move to
	 * @param months
	 *            - month user wants to move to
	 * @param years
	 *            - year user wants to move to
	 * @return weekBox - an Hbox in the week-view format
	 */
	public HBox createWeekBox(int days, int months, int years) {
		HBox weekBox = new HBox();
		Integer day = days;
		Integer month = months;
		Integer year = years;
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
			weekBox.getChildren().add(dayView(day, month, year - 1900));
			day++;
		}
		weekBox.setAlignment(Pos.CENTER);
		return weekBox;
	}

	/**
	 * The createMonthBox method does the exact same thing as the weekBox but in
	 * month format, however it is slightly longer due to necessary formatting
	 * 
	 * 
	 * @param months
	 *            - month user wants to move to
	 * @param years
	 *            - year user wants to move to
	 * @return monthBox - an Hbox in the week-view format
	 */
	public VBox createMonthBox(int months, int years) {
		VBox monthBox = new VBox();
		Integer day = 1;
		Integer month = months;
		Integer year = years;
		for (int j = 0; j < 4; j++) {
			HBox weekBox = new HBox();
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
				weekBox.getChildren().add(dayView(day, month, year - 1900));
				day++;
			}
			weekBox.setAlignment(Pos.CENTER);
			ObservableList<Node> weekList = monthBox.getChildren();
			if (weekList.size() > 0) {
				HBox prevWeekBox = (HBox) weekList.get(0);
				weekBox.setMinHeight(prevWeekBox.getHeight());
			}
			monthBox.getChildren().add(weekBox);
		}
		if (!month.equals(new Integer(1)) && !monthsW31.contains(month)) {
			HBox weekBox = new HBox();
			weekBox.getChildren().add(dayView(day, month, year - 1900));
			weekBox.getChildren().add(dayView(day + 1, month, year - 1900));

			weekBox.setAlignment(Pos.CENTER);
			// weekBox.setPadding(new Insets(0, 30, 0, 0));

			ObservableList<Node> weekList = monthBox.getChildren();
			if (weekList.size() > 0) {
				HBox prevWeekBox = (HBox) weekList.get(0);
				weekBox.setMinHeight(prevWeekBox.getWidth());
			}
			monthBox.getChildren().add(weekBox);
		}
		// Add to 31st Day if applicable
		if (monthsW31.contains(month)) {
			HBox weekBox = new HBox();
			weekBox.getChildren().add(dayView(day, month, year - 1900));
			weekBox.getChildren().add(dayView(day + 1, month, year - 1900));
			weekBox.getChildren().add(dayView(day + 2, month, year - 1900));
			weekBox.setAlignment(Pos.CENTER);
			// weekBox.setPadding(new Insets(0, 30, 0, 0));
			ObservableList<Node> weekList = monthBox.getChildren();
			if (weekList.size() > 0) {
				HBox prevWeekBox = (HBox) weekList.get(0);
				weekBox.setMinHeight(prevWeekBox.getWidth());
			}
			monthBox.getChildren().add(weekBox);
		}
		monthBox.setAlignment(Pos.CENTER);
		return monthBox;
	}

	/**
	 * Create a Dialog Box, utilized for adding an event to the Calendar.
	 * 
	 * @return VBox - serves as the content for the Dialog box
	 */
	public VBox createEventBox() {
		eventVBox = new VBox();
		eventTitle = new TextField("Event Title");
		eventTitle.setAlignment(Pos.CENTER);
		eventTitle.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				eventTitle.clear();
			}
		});
		eventSHR = new TextField("Start Hour (0-23)");
		eventSHR.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				eventSHR.clear();
			}
		});
		eventSM = new TextField("Start Minutes (0-59)");
		eventSM.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				eventSM.clear();
			}
		});
		eventEHR = new TextField("End Hour (0-23)");
		eventEHR.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				eventEHR.clear();
			}
		});
		eventEM = new TextField("End Minutes (0-59)");
		eventEM.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				eventEM.clear();
			}
		});
		eventMonth = new TextField("Month");
		eventMonth.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				eventMonth.clear();
			}
		});
		eventDay = new TextField("Day");
		eventDay.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				eventDay.clear();
			}
		});
		eventYear = new TextField("Year");
		eventYear.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				eventYear.clear();
			}
		});
		eventLocation = new TextField("Location");
		eventLocation.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				eventLocation.clear();
			}
		});
		eventNote = new TextField("Notes");
		eventNote.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				eventNote.clear();
			}
		});
		// Format Date HBox
		HBox dateHBox = new HBox();
		dateHBox.getChildren().add(eventMonth);
		dateHBox.getChildren().add(eventDay);
		dateHBox.getChildren().add(eventYear);
		dateHBox.setAlignment(Pos.CENTER);
		// Format Time HBoxs
		HBox startHBox = new HBox();
		startHBox.getChildren().add(eventSHR);
		startHBox.getChildren().add(eventSM);
		startHBox.setAlignment(Pos.CENTER);
		HBox endHBox = new HBox();
		endHBox.getChildren().add(eventEHR);
		endHBox.getChildren().add(eventEM);
		endHBox.setAlignment(Pos.CENTER);
		// Format ChoiceBox
		HBox choiceHBox = new HBox();
		choiceHBox.setAlignment(Pos.CENTER);
		cb = new ChoiceBox<String>();
		cb.getItems().addAll("Personal", "Work", "Random", "School", "Hobbies", "Birthdays", "Secret");
		cb.setValue("Personal");
		Label choiceLabel = new Label("Select a Calendar Tag:");
		choiceLabel.setPadding(new Insets(5, 5, 5, 5));
		choiceHBox.getChildren().add(choiceLabel);
		choiceHBox.getChildren().add(cb);
		// Add nodes to VBox
		eventVBox.getChildren().add(eventTitle);
		eventVBox.getChildren().add(dateHBox);
		eventVBox.getChildren().add(startHBox);
		eventVBox.getChildren().add(endHBox);
		eventVBox.getChildren().add(eventLocation);
		eventVBox.getChildren().add(eventNote);
		eventVBox.getChildren().add(choiceHBox);
		return eventVBox;
	}

	/**
	 * Method to update the view in live-time. It has been very useful and makes the
	 * calendar run a lot smoother
	 */

	public void updateView() {
		calendarLabel.setText(
				Integer.toString(monthTemp + 1) + "/" + Integer.toString(dayTemp) + "/" + Integer.toString(yearTemp));
		if (type == 1) {
			window.setCenter(dayView(dayTemp, monthTemp, yearTemp - 1900));
		} else if (type == 2) {
			window.setCenter(createWeekBox(dayTemp, monthTemp, yearTemp));
		} else if (type == 0) {
			window.setCenter(createMonthBox(monthTemp, yearTemp));
		}

	}

	/**
	 * Create the top of the GridPane, for the Scene
	 */
	public void createTop() {
		// Format calendarLabel
		viewCalendar = new HBox();
		viewCalendar.setPadding(new Insets(5, 5, 5, 5));
		viewCalendar.setPrefWidth(130);
		viewCalendar.setAlignment(Pos.CENTER);
		calendarLabel = new Label(
				Integer.toString(monthTemp) + "/" + Integer.toString(dayTemp) + "/" + Integer.toString(yearTemp));
		calendarLabel.setFont(new Font("Verdana", 40));
		// Format topBox, which holds calendarLabel and viewOptions
		topBox = new VBox();
		topBox.setAlignment(Pos.CENTER);
		topBox.setPadding(new Insets(5, 5, 5, 5));
		// Format viewOptions
		viewOptions = new HBox();
		viewOptions.setPadding(new Insets(5, 5, 5, 5));
		viewOptions.setPrefWidth(130);
		viewOptions.setAlignment(Pos.CENTER);
		// Format Buttons
		previous = new Button("Previous");
		previous.setMaxSize(130, 30);
		previous.setPadding(new Insets(10, 10, 10, 10));
		previous.setFont(new Font("Verdana", 15));
		next = new Button("Next");
		next.setMaxSize(130, 30);
		next.setPadding(new Insets(10, 10, 10, 10));
		next.setFont(new Font("Verdana", 15));
		dayButton = new Button("Day View");
		dayButton.setMaxSize(130, 30);
		dayButton.setPadding(new Insets(10, 10, 10, 10));
		dayButton.setFont(new Font("Verdana", 15));
		weekButton = new Button("Week View");
		weekButton.setMaxSize(130, 30);
		weekButton.setPadding(new Insets(10, 10, 10, 10));
		weekButton.setFont(new Font("Verdana", 15));
		monthButton = new Button("Month View");
		monthButton.setMaxSize(130, 30);
		monthButton.setPadding(new Insets(10, 10, 10, 10));
		monthButton.setFont(new Font("Verdana", 15));
		addEvent = new Button("Add Event");
		addEvent.setMaxSize(130, 30);
		addEvent.setPadding(new Insets(10, 10, 10, 10));
		addEvent.setFont(new Font("Verdana", 15));

		// Add all Nodes to Parent
		viewOptions.getChildren().add(dayButton);
		viewOptions.getChildren().add(weekButton);
		viewOptions.getChildren().add(monthButton);
		viewOptions.getChildren().add(addEvent);
		// clear button
		clearAll = new Button("Clear All Events");
		clearAll.setMaxSize(170, 30);
		clearAll.setPadding(new Insets(10, 10, 10, 10));
		clearAll.setFont(new Font("Verdana", 15));
		clearAll.setStyle("-fx-background-color: Red");

		// MESSED UP HERE @CORBETT I couldnt figuire this part out so it can be
		// commented out. you wanna give it a go?
		// also the clearAll works just not making the new calander
		createCal = new Button("Add Calander");
		createCal.setMaxSize(130, 30);
		createCal.setPadding(new Insets(10, 10, 10, 10));
		createCal.setFont(new Font("Verdana", 15));
		moreCal = new Button("Choose Calander");
		moreCal.setMaxSize(130, 30);
		moreCal.setPadding(new Insets(10, 10, 10, 10));
		moreCal.setFont(new Font("Verdana", 15));

		HBox hboxT = new HBox(clearAll);
		hboxT.setPadding(new Insets(5, 5, 5, 5));
		hboxT.setPrefWidth(130);
		hboxT.setAlignment(Pos.CENTER);

		// calander nodes
		viewCalendar.getChildren().add(previous);
		viewCalendar.getChildren().add(calendarLabel);
		viewCalendar.getChildren().add(next);
		topBox.getChildren().add(viewCalendar);
		topBox.getChildren().add(viewOptions);
		topBox.getChildren().add(hboxT);
	}

	/**
	 * Create the bottom of the GridPane, for the Scene
	 */
	public void createBottom() {
		bottomLabel = new Label();
		bottomLabel.setFont(new Font("Verdana", 12));
		bottomLabel.setPadding(new Insets(5, 5, 5, 5));
	}

	/**
	 * Create a singular day in the Calendar, using a ScrollPane to wrap around a
	 * VBox(). Each specific Event is given an EventHandler to help the user see
	 * that Event, if clicked on.
	 * 
	 * @param day
	 *            - an Integer representing day
	 * @param month
	 *            - an Integer representing month
	 * @param year
	 *            - an Integer representing year
	 * @return dayScroller - aka the scrolling pane for the day view
	 */
	public ScrollPane dayView(Integer day, Integer month, Integer year) {
		ScrollPane dayScroller = new ScrollPane();
		dayScroller.setPadding(new Insets(5, 5, 5, 5));

		if (day.equals(new Integer(currentDay)) && month.equals(new Integer(currentMonth - 1))
				&& year.equals(new Integer(currentYear - 1900))) {
			dayScroller.setStyle("-fx-background: #008B8B;");
		} else {
			dayScroller.setStyle("-fx-background: #c2c4c4;");
		}

		dayScroller.setMinHeight(100);
		// Format dayView VBox
		VBox dayView = new VBox();
		dayView.setAlignment(Pos.CENTER);
		// Format dayLabel
		Label dayLabel = new Label(Integer.toString(day));
		dayLabel.setFont(new Font("Verdana", 15));
		dayLabel.setPadding(new Insets(5, 5, 5, 5));
		dayLabel.setMinWidth(50);
		dayLabel.setMaxHeight(30);
		dayLabel.setMinHeight(30);
		dayLabel.setAlignment(Pos.CENTER);
		dayLabel.setBorder(
				new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
		dayView.getChildren().add(dayLabel);
		// Format VBox();
		for (Event tempEvent : controller.getDay(day, month, year)) {
			// Format tempButton
			Button tempButton = new Button(tempEvent.getTitle());
			tempButton.setFont(new Font("Verdana", 12));
			tempButton.setPadding(new Insets(5, 5, 5, 5));
			tempButton.setAlignment(Pos.CENTER);
			tempButton.setStyle(calendarColors.get(tempEvent.getCalendarTag()));
			tempButton.setMinWidth(25);
			dayView.getChildren().add(tempButton);
			// Add action for tempButton to display below if clicked
			tempButton.setOnAction((event) -> {
				String labelString = "";
				labelString += tempEvent.getTitle() + "\nStart Time - " + tempEvent.getStart() + "\nEnd Time - "
						+ tempEvent.getEnd() + "\nLocation - " + tempEvent.getLocation() + "\nNote:"
						+ tempEvent.getNote();
				bottomLabel.setText(labelString);
			});
		}
		dayScroller.setContent(dayView);

		return dayScroller;
	}

	/**
	 * This main method is actually quite important for the serialization process.
	 * It first checks to see if the calendar is empty and if it is, a new fresh one
	 * is created. If it is not however it used the old data that was stored
	 * 
	 * @param args - args from main app none in this case
	 */

	public static void main(String[] args) {

		calendar1 = null;
		try {
			FileInputStream file = new FileInputStream(filename);
			ObjectInputStream in = new ObjectInputStream(file);
			calendar1 = (Calendar) in.readObject();
			in.close();
			file.close();
			// System.out.println("test1");
			// calendar1.print();

		} catch (IOException ex) {
			System.out.println("IOException is caught");
		} catch (ClassNotFoundException ex) {
			System.out.println("ClassNotFoundException is caught");
		}

		launch(args);
	}
}
