package view;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Optional;

import controller.CalendarController;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import model.Event;

public class CalendarView extends Application{
	private BorderPane window;
	private VBox topBox, eventVBox;
	private Label calendarLabel, bottomLabel;
	private TextField eventTitle, eventSHR, eventSM, eventEHR, eventEM;
	private TextField eventDay, eventMonth, eventYear, eventLocation, eventNote;
	private CalendarController controller;
	private HBox viewOptions;
	private Button dayButton, weekButton, monthButton, addEvent;
	private ChoiceBox<String> cb;
	private HashMap<String, String> calendarColors;
	ArrayList<Integer> monthsW31 = new ArrayList<Integer>(Arrays.asList(0,2,4,6,7,9,11));

	@Override
	public void start(Stage primaryStage) { 
		controller = new CalendarController();
		window = new BorderPane();
		// Set calendarColors for various calendar Options
		calendarColors = new HashMap<String, String>();
		calendarColors.put("Personal", "-fx-background-color: #ff0000;");
		calendarColors.put("Work", "-fx-background-color: #00ffea;");
		calendarColors.put("Random", "-fx-background-color: #fffb00;");
		
		createTop();
		createBottom();
		addActionHandlers();
		
		window.setTop(topBox);
		// By Default, have the dayView pull up
		window.setCenter(dayView(new Integer(6), new Integer(4), new Integer(30)));
		window.setBottom(bottomLabel);
		
		
		Scene scene = new Scene(window, 500, 500);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Corbett & Vukasin - Calendar Application");
		primaryStage.show();
	}
	
	public void addActionHandlers() {
		dayButton.setOnAction((event) -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Day Selection");
			alert.setHeaderText("Select Month, Day, Year");
			// Set up HBox and ChoiceBoxes
			HBox dayHBox = new HBox();
			ChoiceBox<Integer> months = new ChoiceBox<Integer>();
			months.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12);
			ChoiceBox<Integer> days = new ChoiceBox<Integer>();
			days.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,
					15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31);
			ChoiceBox<Integer> years = new ChoiceBox<Integer>();
			years.getItems().addAll(2020,2021,2022,2023,2024,2025,2026,2027,2028,2029,2030);
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
				window.setCenter(dayView(days.getValue(), months.getValue() - 1, years.getValue()- 1900));
			}
		});
		
		weekButton.setOnAction((event) -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Week Selection");
			alert.setHeaderText("Select Month, Day, Year");
			// Set up HBox and ChoiceBoxes
			HBox dayHBox = new HBox();
			ChoiceBox<Integer> months = new ChoiceBox<Integer>();
			months.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12);
			ChoiceBox<Integer> days = new ChoiceBox<Integer>();
			days.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12,13,14,
					15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31);
			ChoiceBox<Integer> years = new ChoiceBox<Integer>();
			years.getItems().addAll(2020,2021,2022,2023,2024,2025,2026,2027,2028,2029,2030);
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
				HBox weekBox = new HBox();
				Integer day = days.getValue();
				Integer month = months.getValue() - 1;
				Integer year = years.getValue();
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
				window.setCenter(weekBox);
			}
		});
		
		monthButton.setOnAction((event) -> {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Month Selection");
			alert.setHeaderText("Select Month, Year");
			// Set up HBox and ChoiceBoxes
			HBox dayHBox = new HBox();
			ChoiceBox<Integer> months = new ChoiceBox<Integer>();
			months.getItems().addAll(1,2,3,4,5,6,7,8,9,10,11,12);
			ChoiceBox<Integer> years = new ChoiceBox<Integer>();
			years.getItems().addAll(2020,2021,2022,2023,2024,2025,2026,2027,2028,2029,2030);
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
				VBox monthBox = new VBox();
				Integer day = 1;
				Integer month = months.getValue() - 1;
				Integer year = years.getValue();
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
				monthBox.setAlignment(Pos.CENTER);
				window.setCenter(monthBox);
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
						Integer.parseInt(eventMonth.getText().trim()) - 1, 
						Integer.parseInt(eventDay.getText().trim()));
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
			}
		});
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
		cb.getItems().addAll("Personal", "Work", "Random");
		cb.setValue("Personal");
		Label choiceLabel = new Label("Select a Calendar Tag:");
		choiceLabel.setPadding(new Insets(5,5,5,5));
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
	 * Create the top of the GridPane, for the Scene
	 */
	public void createTop() {
		// Format calendarLabel
		calendarLabel = new Label("CALENDAR");
		calendarLabel.setFont(new Font("Verdana", 40));
		// Format topBox, which holds calendarLabel and viewOptions
		topBox = new VBox();
		topBox.setAlignment(Pos.CENTER);
		topBox.setPadding(new Insets(5,5,5,5));
		// Format viewOptions
		viewOptions = new HBox();	
		viewOptions.setPadding(new Insets(5,5,5,5));
		viewOptions.setPrefWidth(130);
		viewOptions.setAlignment(Pos.CENTER);
		// Format Buttons 
		dayButton = new Button("Day View");
		dayButton.setMaxSize(130, 30);
		dayButton.setPadding(new Insets(10,10,10,10));
		dayButton.setFont(new Font("Verdana", 15));
		weekButton = new Button("Week View");
		weekButton.setMaxSize(130, 30);
		weekButton.setPadding(new Insets(10,10,10,10));
		weekButton.setFont(new Font("Verdana", 15));
		monthButton = new Button("Month View");
		monthButton.setMaxSize(130, 30);
		monthButton.setPadding(new Insets(10,10,10,10));
		monthButton.setFont(new Font("Verdana", 15));
		addEvent = new Button("Add Event");
		addEvent.setMaxSize(130, 30);
		addEvent.setPadding(new Insets(10,10,10,10));
		addEvent.setFont(new Font("Verdana", 15));
		// Add all Nodes to Parent
		viewOptions.getChildren().add(dayButton);
		viewOptions.getChildren().add(weekButton);
		viewOptions.getChildren().add(monthButton);
		viewOptions.getChildren().add(addEvent);
		topBox.getChildren().add(calendarLabel);
		topBox.getChildren().add(viewOptions);
	}
	
	/**
	 * Create the bottom of the GridPane, for the Scene
	 */
	public void createBottom() {
		bottomLabel = new Label();
		bottomLabel.setFont(new Font("Verdana", 12));
		bottomLabel.setPadding(new Insets(5,5,5,5));
	}
	
	/**
	 * Create a singular day in the Calendar, using a ScrollPane
	 * to wrap around a VBox(). Each specific Event is given an
	 * EventHandler to help the user see that Event, if clicked on.
	 * 
	 * @param day - an Integer representing day
	 * @param month - an Integer representing month
	 * @param year - an Integer representing year
	 */
	public ScrollPane dayView(Integer day, Integer month, Integer year) {
		ScrollPane dayScroller = new ScrollPane();
		dayScroller.setPadding(new Insets(5, 5, 5, 5));
		dayScroller.setStyle("-fx-background: #c2c4c4;");
		dayScroller.setMinHeight(100);
		// Format dayView VBox
		VBox dayView = new VBox();
		dayView.setAlignment(Pos.CENTER);
		// Format dayLabel
		Label dayLabel = new Label(Integer.toString(day));
		dayLabel.setFont(new Font("Verdana", 15));
		dayLabel.setPadding(new Insets(5,5,5,5));
		dayLabel.setMinWidth(50);
		dayLabel.setMaxHeight(30);
		dayLabel.setMinHeight(30);
		dayLabel.setAlignment(Pos.CENTER);
		dayLabel.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, null, new BorderWidths(2))));
		dayView.getChildren().add(dayLabel);
		// Format VBox();
		for (Event tempEvent: controller.getDay(day, month, year)) {
			// Format tempButton
			Button tempButton = new Button(tempEvent.getTitle());
			tempButton.setFont(new Font("Verdana", 12));
			tempButton.setPadding(new Insets(5,5,5,5));
			tempButton.setAlignment(Pos.CENTER);
			tempButton.setStyle(calendarColors.get(tempEvent.getCalendarTag()));
			tempButton.setMinWidth(25);
			dayView.getChildren().add(tempButton);
			// Add action for tempButton to display below if clicked
			tempButton.setOnAction((event) -> {
				String labelString = "";
				labelString += tempEvent.getTitle() + "\nStart Time - " +
							   tempEvent.getStart() + "\nEnd Time - " + 
							   tempEvent.getEnd() + "\nLocation - " + 
							   tempEvent.getLocation() + "\nNote:" + 
							   tempEvent.getNote();
				bottomLabel.setText(labelString);
			});
		}
		dayScroller.setContent(dayView);
		return dayScroller;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
