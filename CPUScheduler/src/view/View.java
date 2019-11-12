package view;

import java.util.Arrays;
import java.util.Random;

import javax.swing.JOptionPane;

import javafx.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.GanttSceneBuilder;
import model.GanttStringBuilder;
import model.Process;
import model.ProcessBag;

public class View extends Application {

	Scene PRIMARY_SCENE;
	private ProcessBag processBag;
	StackPane chart1 = new StackPane();

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		primaryStage.setTitle("CPU Scheduler");
		//
		// link to app icon will load if we have internet access
		// this makes it cuter when we export into executable jar :D
		//
		primaryStage.getIcons().add(new Image("https://cdn1.iconfinder.com/data/icons/chips-and-cpu/512/module-memory-cpu-chip-chipset-512.png"));
		// 
		// if no internet access -> uncomment this & paste path to img
		// primaryStage.getIcons().add(new Image("/path/to/javaicon.png"));
		//
		
		// setting up the greeting/title box
		VBox titleBox = new VBox();
		titleBox.setAlignment(Pos.CENTER);
		Text titleText = new Text("CPU Scheduler Simulator");
		titleText.setFont(Font.font("Impact", 32));
		Text creditText = new Text("by Dan Fayaud & Katie Porter :)");
		creditText.setFont(Font.font("Tahoma", FontPosture.ITALIC, 14));
		titleBox.getChildren().addAll(titleText, creditText);
		
		// initializing stackpanes so they can be changed up later
		// and pushed into gantt chart as needed
		StackPane chart1 = null;
		StackPane chart2 = null;
		StackPane chart3 = null;
		StackPane chart4 = null;
		StackPane chart5 = null;
		StackPane chart6 = null;
		StackPane chart7 = null;
		StackPane chart8 = null;
		Text avgWaitTime = new Text("0");;
		Text avgTurnaroundTime = avgTurnaroundTime = new Text("0");;

		// setting up the processBox & its features
		Label processLabel = new Label("Number of Processes: ");
		ComboBox<String> processCombos = new ComboBox<String>();
		processCombos.setPromptText("--Select--");
		processCombos.getItems().add("1 Process");
		processCombos.getItems().add("2 Processes");
		processCombos.getItems().add("3 Processes");
		processCombos.getItems().add("4 Processes");
		processCombos.getItems().add("5 Processes");
		processCombos.getItems().add("6 Processes");
		processCombos.getItems().add("7 Processes"); 
		processCombos.getItems().add("8 Processes");
		
		Button randomBurstButton = new Button("Randomize All Times");
		Button calculate = new Button("Calculate");
		
		HBox processBox = new HBox(processLabel, processCombos, randomBurstButton, calculate);
		processBox.setSpacing(10);
		processBox.setAlignment(Pos.TOP_LEFT);
		processBox.setPadding(new Insets(10, 10, 10, 10));
		
		TextField quantumField = new TextField();
		quantumField.setPromptText("Quantum");
		quantumField.setMaxWidth(100);
		quantumField.setVisible(false);
		
		// setting up algorithm combobox & its features***************************************************
		Label algoLabel = new Label("Algorithm to Test: ");
		ComboBox<String> algoCombos = new ComboBox<String>();
		algoCombos.setPromptText("--Select--");
		algoCombos.getItems().add("PRI: Priority");
		algoCombos.getItems().add("RR: Round Robin");
		algoCombos.getItems().add("SJF: Shortest Job First");
		algoCombos.getItems().add("FCFS: First Come First Serve");
		algoCombos.getItems().add("SRTF: Shortest Remaining Time First");
		HBox algoBox = new HBox(algoLabel, algoCombos, quantumField);
		algoBox.setSpacing(10);
		algoBox.setAlignment(Pos.TOP_LEFT);
		algoBox.setPadding(new Insets(10, 10, 10, 10));		

		
		// combining selections & algocombobox
		VBox selections = new VBox(titleBox, new Separator(), algoBox, processBox, new Separator());
		selections.setAlignment(Pos.TOP_LEFT);
		selections.setSpacing(5);
		selections.setPadding(new Insets(10, 10, 10, 10));
		
		// Process burst time display
		Label processLabel2 = new Label("Current Process:");
		Label burstTimeLabel = new Label("Burst Time:");
		Label waitTimeLabel = new Label("Wait Time:");
		Label totalTimeLabel = new Label("Total Time:");
		Label priorityLabel = new Label("Priority:");
		Label arrivalLabel = new Label("Arrival:");
		HBox displayTitles = new HBox();
		displayTitles.getChildren().addAll(processLabel2, burstTimeLabel, waitTimeLabel, totalTimeLabel,
				arrivalLabel, priorityLabel);
		displayTitles.setSpacing(30);
		displayTitles.setAlignment(Pos.BASELINE_CENTER);
		
		// first row of processes - "p1"
		// this will be set up and mimicked for each other process
		// other processes will only be set to visible depending on
		// how many prcoesses were chosen by the user in processCombos
		Label p1 = new Label("Process 1");
		TextField burst1 = new TextField();
		burst1.setPromptText("burst time");
		burst1.setPrefWidth(75);
		burst1.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    burst1.setText(oldValue);
                }
            }
        });
		Label wait1 = new Label("1");
		Label total1 = new Label("1");
		TextField priority1 = new TextField();
		priority1.setPromptText("priority");
		priority1.setPrefWidth(50);
		priority1.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,1}?")) {
                    priority1.setText(oldValue);
                }
            }
		});
		TextField arrival1 = new TextField();
		arrival1.setPromptText("time(ms)");
		arrival1.setPrefWidth(50);
		arrival1.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,3}?")) {
                    arrival1.setText(oldValue);
                }
            }
		});
		HBox process1 = new HBox();
		process1.getChildren().addAll(p1, burst1, wait1, total1, arrival1, priority1);
		process1.setSpacing(50);
		process1.setAlignment(Pos.BASELINE_CENTER);
		
		Label p2 = new Label("Process 2");
		TextField burst2 = new TextField();
		burst2.setPromptText("burst time");
		burst2.setPrefWidth(75);
		burst2.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    burst2.setText(oldValue);
                }
            }
        });
		Label wait2 = new Label("2");
		Label total2 = new Label("2");
		TextField priority2 = new TextField();
		priority2.setPromptText("priority");
		priority2.setPrefWidth(50);
		priority2.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,1}?")) {
                    priority2.setText(oldValue);
                }
            }
		});
		TextField arrival2 = new TextField();
		arrival2.setPromptText("time(ms)");
		arrival2.setPrefWidth(50);
		arrival2.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,3}?")) {
                    arrival2.setText(oldValue);
                }
            }
		});
		HBox process2 = new HBox();
		process2.getChildren().addAll(p2, burst2, wait2, total2, arrival2, priority2);
		process2.setSpacing(50);
		process2.setAlignment(Pos.BASELINE_CENTER);
		
		Label p3 = new Label("Process 3");
		TextField burst3 = new TextField();
		burst3.setPromptText("burst time");
		burst3.setPrefWidth(75);
		burst3.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    burst3.setText(oldValue);
                }
            }
        });
		Label wait3 = new Label("3");
		Label total3 = new Label("3");
		TextField priority3 = new TextField();
		priority3.setPromptText("priority");
		priority3.setPrefWidth(50);
		priority3.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,1}?")) {
                    priority3.setText(oldValue);
                }
            }
		});
		TextField arrival3 = new TextField();
		arrival3.setPromptText("time(ms)");
		arrival3.setPrefWidth(50);
		arrival3.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,3}?")) {
                    arrival3.setText(oldValue);
                }
            }
		});
		HBox process3 = new HBox();
		process3.getChildren().addAll(p3, burst3, wait3, total3, arrival3, priority3);
		process3.setSpacing(50);
		process3.setAlignment(Pos.BASELINE_CENTER);
		
		Label p4 = new Label("Process 4");
		TextField burst4 = new TextField();
		burst4.setPromptText("burst time");
		burst4.setPrefWidth(75);
		burst4.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    burst4.setText(oldValue);
                }
            }
        });
		Label wait4 = new Label("4");
		Label total4 = new Label("4");
		TextField priority4 = new TextField();
		priority4.setPromptText("priority");
		priority4.setPrefWidth(50);
		priority4.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,1}?")) {
                    priority4.setText(oldValue);
                }
            }
		});
		TextField arrival4 = new TextField();
		arrival4.setPromptText("time(ms)");
		arrival4.setPrefWidth(50);
		arrival4.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,3}?")) {
                    arrival4.setText(oldValue);
                }
            }
		});
		HBox process4 = new HBox();
		process4.getChildren().addAll(p4, burst4, wait4, total4, arrival4, priority4);
		process4.setSpacing(50);
		process4.setAlignment(Pos.BASELINE_CENTER);
		
		Label p5 = new Label("Process 5");
		TextField burst5 = new TextField();
		burst5.setPromptText("burst time");
		burst5.setPrefWidth(75);
		burst5.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    burst5.setText(oldValue);
                }
            }
        });
		Label wait5 = new Label("5");
		Label total5 = new Label("5");
		TextField priority5 = new TextField();
		priority5.setPromptText("priority");
		priority5.setPrefWidth(50);
		priority5.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,1}?")) {
                    priority5.setText(oldValue);
                }
            }
		});
		TextField arrival5 = new TextField();
		arrival5.setPromptText("time(ms)");
		arrival5.setPrefWidth(50);
		arrival5.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,3}?")) {
                    arrival5.setText(oldValue);
                }
            }
		});
		HBox process5 = new HBox();
		process5.getChildren().addAll(p5, burst5, wait5, total5, arrival5, priority5);
		process5.setSpacing(50);
		process5.setAlignment(Pos.BASELINE_CENTER);
		
		Label p6 = new Label("Process 6");
		TextField burst6 = new TextField();
		burst6.setPromptText("burst time");
		burst6.setPrefWidth(75);
		burst6.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    burst6.setText(oldValue);
                }
            }
        });
		Label wait6 = new Label("6");
		Label total6 = new Label("6");
		TextField priority6 = new TextField();
		priority6.setPromptText("priority");
		priority6.setPrefWidth(50);
		priority6.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,1}?")) {
                    priority6.setText(oldValue);
                }
            }
		});
		TextField arrival6 = new TextField();
		arrival6.setPromptText("time(ms)");
		arrival6.setPrefWidth(50);
		arrival6.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,3}?")) {
                    arrival6.setText(oldValue);
                }
            }
		});
		HBox process6 = new HBox();
		process6.getChildren().addAll(p6, burst6, wait6, total6, arrival6, priority6);
		process6.setSpacing(50);
		process6.setAlignment(Pos.BASELINE_CENTER);
		
		Label p7 = new Label("Process 7");
		TextField burst7 = new TextField();
		burst7.setPromptText("burst time");
		burst7.setPrefWidth(75);
		burst7.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    burst7.setText(oldValue);
                }
            }
        });
		Label wait7 = new Label("7");
		Label total7 = new Label("7");
		TextField priority7 = new TextField();
		priority7.setPromptText("priority");
		priority7.setPrefWidth(50);
		priority7.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,1}?")) {
                    priority7.setText(oldValue);
                }
            }
		});
		TextField arrival7 = new TextField();
		arrival7.setPromptText("time(ms)");
		arrival7.setPrefWidth(50);
		arrival7.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,3}?")) {
                    arrival7.setText(oldValue);
                }
            }
		});
		HBox process7 = new HBox();
		process7.getChildren().addAll(p7, burst7, wait7, total7, arrival7, priority7);
		process7.setSpacing(50);
		process7.setAlignment(Pos.BASELINE_CENTER);
		Label p8 = new Label("Process 8");
		TextField burst8 = new TextField();
		burst8.setPromptText("burst time");
		burst8.setPrefWidth(75);
		burst8.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,7}([\\.]\\d{0,4})?")) {
                    burst8.setText(oldValue);
                }
            }
        });
		Label wait8 = new Label("8");
		Label total8 = new Label("8");
		TextField priority8 = new TextField();
		priority8.setPromptText("priority");
		priority8.setPrefWidth(50);
		priority8.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,1}?")) {
                    priority8.setText(oldValue);
                }
            }
		});
		TextField arrival8 = new TextField();
		arrival8.setPromptText("time(ms)");
		arrival8.setPrefWidth(50);
		arrival8.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.matches("\\d{0,3}?")) {
                    arrival8.setText(oldValue);
                }
            }
		});
		HBox process8 = new HBox();
		process8.getChildren().addAll(p8, burst8, wait8, total8, arrival8, priority8);
		process8.setSpacing(50);
		process8.setAlignment(Pos.BASELINE_CENTER);
		
		//
		// ACTION EVENTS
		//
		
		//
		// calculates the cpu scheduler chosen
		// depending on which algorithm is chosen by
		// the user, the calculate button will use switch cases
		// to determine witch algorithm to use. then, based off
		// of the input values for each field, calculations will be done
		Alert alert = new Alert(AlertType.INFORMATION);
		calculate.setOnAction(e -> {
			TextField[] burstTextFields = new TextField[8];
			TextField[] arrivalTextFields = new TextField[8];
			TextField[] priorityTextFields = new TextField[8];
			
			burstTextFields[0] = burst1;
			burstTextFields[1] = burst2;
			burstTextFields[2] = burst3;
			burstTextFields[3] = burst4;
			burstTextFields[4] = burst5;
			burstTextFields[5] = burst6;
			burstTextFields[6] = burst7;
			burstTextFields[7] = burst8;
			
			arrivalTextFields[0] = arrival1;
			arrivalTextFields[1] = arrival2;
			arrivalTextFields[2] = arrival3;
			arrivalTextFields[3] = arrival4;
			arrivalTextFields[4] = arrival5;
			arrivalTextFields[5] = arrival6;
			arrivalTextFields[6] = arrival7;
			arrivalTextFields[7] = arrival8;
			
			priorityTextFields[0] = priority1;
			priorityTextFields[1] = priority2;
			priorityTextFields[2] = priority3;
			priorityTextFields[3] = priority4;
			priorityTextFields[4] = priority5;
			priorityTextFields[5] = priority6;
			priorityTextFields[6] = priority7;
			priorityTextFields[7] = priority8;
			
			int nElems = 0;
			int algorithm = 0;
			
			
			switch(algoCombos.getValue()) {
			case "PRI: Priority":
				algorithm = 3;
				switch(processCombos.getValue()) {
				case "1 Process":
					nElems = 1;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					// Priority Process for 1 process
					break;
				case "2 Processes":
					// Priority Process for 2 processes
					nElems = 2;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "3 Processes":
					// Priority for 3 processes
					nElems = 3;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "4 Processes":
					// Priority for 4 processes
					nElems = 4;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "5 Processes":
					// Priority for 5 processes
					nElems = 5;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "6 Processes":
					// Priority for 6 processes
					nElems = 6;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "7 Processes":
					// Priority for 7 processes
					nElems = 7;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "8 Processes":
					// Priority for 8 processes
					nElems = 8;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				}
				break;
			case "RR: Round Robin":
				algorithm = 4;
				switch(processCombos.getValue()) {
				case "1 Process":
					// RR for 1 process
					nElems = 1;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "2 Processes":
					// RR for 2 processes
					nElems = 2;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "3 Processes":
					// RR for 3 processes
					nElems = 3;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "4 Processes":
					// RR for 4 processes
					nElems = 4;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "5 Processes":
					// RR for 5 processes
					nElems = 5;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "6 Processes":
					// RR for 6 processes
					nElems = 6;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "7 Processes":
					// RR for 7 processes
					nElems = 7;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "8 Processes":
					// RR for 8 processes
					nElems = 8;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				}
				break;
			case "SJF: Shortest Job First":
				algorithm = 1;
				switch(processCombos.getValue()) {
				case "1 Process":
					// SJF for 1 process
					nElems = 1;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "2 Processes":
					// SJF for 2 processes
					nElems = 2;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "3 Processes":
					// SJF for 3 processes
					nElems = 3;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "4 Processes":
					// SJF for 4 processes
					nElems = 4;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "5 Processes":
					// SJF for 5 processes
					nElems = 5;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "6 Processes":
					// SJF for 6 processes
					nElems = 6;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "7 Processes":
					// SJF for 7 processes
					nElems = 7;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "8 Processes":
					// SJF for 8 processes
					nElems = 8;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				}
				break;
			case "FCFS: First Come First Serve":
				algorithm = 0;
				switch(processCombos.getValue()) {
				case "1 Process":
					// FCFS for 1 process
					nElems = 1;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "2 Processes":
					// FCFS for 2 processes
					nElems = 2;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "3 Processes":
					// FCFS for 3 processes
					nElems = 3;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "4 Processes":
					// FCFS for 4 processes
					nElems = 4;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "5 Processes":
					// FCFS for 5 processes
					nElems = 5;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "6 Processes":
					// FCFS for 6 processes
					nElems = 6;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "7 Processes":
					// FCFS for 7 processes
					nElems = 7;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "8 Processes":
					// FCFS for 8 processes
					nElems = 8;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				}
				break;
			case "SRTF: Shortest Remaining Time First":
				algorithm = 2;
				switch(processCombos.getValue()) {
				case "1 Process":
					// SRTF for 1 process
					nElems = 1;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "2 Processes":
					// SRTF for 2 processes
					nElems = 2;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "3 Processes":
					// SRTF for 3 processes
					nElems = 3;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "4 Processes":
					// SRTF for 4 processes
					nElems = 4;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "5 Processes":
					// SRTF for 5 processes
					nElems = 5;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "6 Processes":
					// SRTF for 6 processes
					nElems = 6;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "7 Processes":
					// SRTF for 7 processes
					nElems = 7;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				case "8 Processes":
					// SRTF for 8 processes
					nElems = 8;
//					avgWaitTime.setText(processBag.getAverageWaitingTime() + "");
//					avgTurnaroundTime.setText(processBag.getAverageTurnaroundTime() + "");
					break;
				}
				break;
			}
			int arrivalTimes[];
			int burstTimes[];
			int priorityLevels[];
			switch(algorithm) {
			case 0 : 
				arrivalTimes = parseArray(arrivalTextFields, nElems);
				burstTimes = parseArray(burstTextFields, nElems);
				this.processBag = new ProcessBag(200, burstTimes, arrivalTimes);
				
//				System.out.println("FCFS");
				break;
			case 1:
				arrivalTimes = parseArray(arrivalTextFields, nElems);
				burstTimes = parseArray(burstTextFields, nElems);
				this.processBag = new ProcessBag(200, burstTimes, arrivalTimes, true);
//				System.out.println("SJF");
				break;
			case 2:
				arrivalTimes = parseArray(arrivalTextFields, nElems);
				burstTimes = parseArray(burstTextFields, nElems);
				this.processBag = new ProcessBag(200, burstTimes, arrivalTimes, false);
//				System.out.println("SRTF");
				break;
			case 3:
				arrivalTimes = parseArray(arrivalTextFields, nElems);
				burstTimes = parseArray(burstTextFields, nElems);
				priorityLevels = parseArray(priorityTextFields, nElems);
				this.processBag = new ProcessBag(200, burstTimes, arrivalTimes, priorityLevels);
//				System.out.println("PRIORITY");
				break;
			case 4:
				int quantum = Integer.parseInt(quantumField.getText());
				arrivalTimes = parseArray(arrivalTextFields, nElems);
				burstTimes = parseArray(burstTextFields, nElems);
				this.processBag = new ProcessBag(200, burstTimes, arrivalTimes, quantum);
//				System.out.println("RR");
				break;
			
			}
			
			String ganttString = new GanttStringBuilder(processBag).getGanttString();
//			testing out that this implementation ( ^ ) works
//			System.out.println(ganttString); 
			String[] ganttArray = ganttString.split(" -> ");
			String[] ganttChartArray = new String[200];
			int num = 0;
			for(int i = 0; i < ganttArray.length; i++) {
				if(!(ganttArray[i].equals("IDLE"))) {
					ganttChartArray[num] = ganttArray[i];
					num++;
				}
			}
			String gantt = "";
			for (int i = 0; i < ganttChartArray.length; i++) {
				if(ganttChartArray[i] != null) {
					if (gantt.equals("")) {
						gantt += ganttChartArray[i];
					} else {
						gantt += " -> " + ganttChartArray[i];
					}
				}
			}
			/*
			the following 2 lines will be
			displaying the gantt chart in an alert box
			this will do for now since I'm not sure how to
			dynamically display stack panes :( - Katie
			*/
			alert.setContentText(gantt);
			alert.show();
			
			avgWaitTime.setText(Double.toString(processBag.getAverageWaitingTime()));
			avgTurnaroundTime.setText(Double.toString(processBag.getAverageTurnaroundTime()));
			
			
		});
		//
		// generates random burst times
		randomBurstButton.setOnAction(e -> {
			// ? Random random = new Random();
			// generate random int between 1 and 100
			// (int)(Math.random()*((100 - 1) + 1)) + 1
			burst1.setText(new Random().nextInt(25 - 1) + 1 +"");
			burst2.setText(new Random().nextInt(25 - 1) + 1+ "");
			burst3.setText(new Random().nextInt(25 - 1) + 1+ "");
			burst4.setText(new Random().nextInt(25 - 1) + 1+ "");
			burst5.setText(new Random().nextInt(25 - 1) + 1+ "");
			burst6.setText(new Random().nextInt(25 - 1) + 1+ "");
			burst7.setText(new Random().nextInt(25 - 1) + 1+ "");
			burst8.setText(new Random().nextInt(25 - 1) + 1+ "");
			priority1.setText(new Random().nextInt(9 - 1) + 1+ "");
			priority2.setText(new Random().nextInt(9 - 1) + 1+ "");
			priority3.setText(new Random().nextInt(9 - 1) + 1+ "");
			priority4.setText(new Random().nextInt(9 - 1) + 1+ "");
			priority5.setText(new Random().nextInt(9 - 1) + 1+ "");
			priority6.setText(new Random().nextInt(9 - 1) + 1+ "");
			priority7.setText(new Random().nextInt(9 - 1) + 1+ "");
			priority8.setText(new Random().nextInt(9 - 1) + 1+ "");
			arrival1.setText(new Random().nextInt(9 - 1) + 1+ "");
			arrival2.setText(new Random().nextInt(9 - 1) + 1+ "");
			arrival3.setText(new Random().nextInt(9 - 1) + 1+ "");
			arrival4.setText(new Random().nextInt(9 - 1) + 1+ "");
			arrival5.setText(new Random().nextInt(9 - 1) + 1+ "");
			arrival6.setText(new Random().nextInt(9 - 1) + 1+ "");
			arrival7.setText(new Random().nextInt(9 - 1) + 1+ "");
			arrival8.setText(new Random().nextInt(9 - 1) + 1+ "");
		
		});
		//
		// default = priority will be invisible
		
		// PRI -
		// switch cases to "unlock" priority
		/*
		algoCombos.getItems().add("SRTF: Shortest Remaining Time First");
		 */
		algoCombos.setOnAction(e -> {
			switch(algoCombos.getValue()) {
			case "SRTF: Shortest Remaining Time First":
				quantumField.setVisible(false);
				priorityLabel.setVisible(false);
				priority1.setVisible(false);
				burst1.clear();
				arrival1.clear();
				priority1.clear();
				priority2.setVisible(false);
				burst2.clear();
				arrival2.clear();
				priority2.clear();
				priority3.setVisible(false);
				burst3.clear();
				arrival3.clear();
				priority3.clear();
				priority4.setVisible(false);
				burst4.clear();
				arrival4.clear();
				priority4.clear();
				priority5.setVisible(false);
				burst5.clear();
				arrival5.clear();
				priority5.clear();
				priority6.setVisible(false);
				burst6.clear();
				arrival6.clear();
				priority6.clear();
				priority7.setVisible(false);
				burst7.clear();
				arrival7.clear();
				priority7.clear();
				priority8.setVisible(false);
				burst8.clear();
				arrival8.clear();
				priority8.clear();
				break;
			case "FCFS: First Come First Serve":
				quantumField.setVisible(false);
				priorityLabel.setVisible(false);
				priority1.setVisible(false);
				burst1.clear();
				arrival1.clear();
				priority1.clear();
				priority2.setVisible(false);
				burst2.clear();
				arrival2.clear();
				priority2.clear();
				priority3.setVisible(false);
				burst3.clear();
				arrival3.clear();
				priority3.clear();
				priority4.setVisible(false);
				burst4.clear();
				arrival4.clear();
				priority4.clear();
				priority5.setVisible(false);
				burst5.clear();
				arrival5.clear();
				priority5.clear();
				priority6.setVisible(false);
				burst6.clear();
				arrival6.clear();
				priority6.clear();
				priority7.setVisible(false);
				burst7.clear();
				arrival7.clear();
				priority7.clear();
				priority8.setVisible(false);
				burst8.clear();
				arrival8.clear();
				priority8.clear();
				break;
			case "SJF: Shortest Job First":
				quantumField.setVisible(false);
				priorityLabel.setVisible(false);
				priority1.setVisible(false);
				burst1.clear();
				arrival1.clear();
				priority1.clear();
				priority2.setVisible(false);
				burst2.clear();
				arrival2.clear();
				priority2.clear();
				priority3.setVisible(false);
				burst3.clear();
				arrival3.clear();
				priority3.clear();
				priority4.setVisible(false);
				burst4.clear();
				arrival4.clear();
				priority4.clear();
				priority5.setVisible(false);
				burst5.clear();
				arrival5.clear();
				priority5.clear();
				priority6.setVisible(false);
				burst6.clear();
				arrival6.clear();
				priority6.clear();
				priority7.setVisible(false);
				burst7.clear();
				arrival7.clear();
				priority7.clear();
				priority8.setVisible(false);
				burst8.clear();
				arrival8.clear();
				priority8.clear();
				break;
			case "PRI: Priority":
				quantumField.setVisible(false);
				priorityLabel.setVisible(true);
				priority1.setVisible(true);
				burst1.clear();
				arrival1.clear();
				priority1.clear();
				priority2.setVisible(true);
				burst2.clear();
				arrival2.clear();
				priority2.clear();
				priority3.setVisible(true);
				burst3.clear();
				arrival3.clear();
				priority3.clear();
				priority4.setVisible(true);
				burst4.clear();
				arrival4.clear();
				priority4.clear();
				priority5.setVisible(true);
				burst5.clear();
				arrival5.clear();
				priority5.clear();
				priority6.setVisible(true);
				burst6.clear();
				arrival6.clear();
				priority6.clear();
				priority7.setVisible(true);
				burst7.clear();
				arrival7.clear();
				priority7.clear();
				priority8.setVisible(true);
				burst8.clear();
				arrival8.clear();
				priority8.clear();
				break;
			case "RR: Round Robin":
				quantumField.setVisible(true);
				priorityLabel.setVisible(false);
				priority1.setVisible(false);
				burst1.clear();
				arrival1.clear();
				priority1.clear();
				priority2.setVisible(false);
				burst2.clear();
				arrival2.clear();
				priority2.clear();
				priority3.setVisible(false);
				burst3.clear();
				arrival3.clear();
				priority3.clear();
				priority4.setVisible(false);
				burst4.clear();
				arrival4.clear();
				priority4.clear();
				priority5.setVisible(false);
				burst5.clear();
				arrival5.clear();
				priority5.clear();
				priority6.setVisible(false);
				burst6.clear();
				arrival6.clear();
				priority6.clear();
				priority7.setVisible(false);
				burst7.clear();
				arrival7.clear();
				priority7.clear();
				priority8.setVisible(false);
				burst8.clear();
				arrival8.clear();
				priority8.clear();
				break;
			default:
				priorityLabel.setVisible(false);
				priority1.setVisible(false);
				priority2.setVisible(false);
				priority3.setVisible(false);
				priority4.setVisible(false);
				priority5.setVisible(false);
				priority6.setVisible(false);
				priority7.setVisible(false);
				priority8.setVisible(false);
			}
		});
		
		//
		// default setting will be invisible
		process1.setVisible(false);
		process2.setVisible(false);
		process3.setVisible(false);
		process4.setVisible(false);
		process5.setVisible(false);
		process6.setVisible(false);
		process7.setVisible(false);
		process8.setVisible(false);
		
		//
		// switch cases to "unlock" processes & clear data from textfields
		processCombos.setOnAction(e -> {
			switch(processCombos.getValue()) {
			case "1 Process":
				process1.setVisible(true);
				burst1.clear();
				arrival1.clear();
				priority1.clear();
				process2.setVisible(false);
				process3.setVisible(false);
				process4.setVisible(false);
				process5.setVisible(false);
				process6.setVisible(false);
				process7.setVisible(false);
				process8.setVisible(false);
				break;
			case "2 Processes":
				process1.setVisible(true);
				burst1.clear();
				arrival1.clear();
				priority1.clear();
				process2.setVisible(true);
				burst2.clear();
				arrival2.clear();
				priority2.clear();
				process3.setVisible(false);
				process4.setVisible(false);
				process5.setVisible(false);
				process6.setVisible(false);
				process7.setVisible(false);
				process8.setVisible(false);
				break;
			case "3 Processes":
				process1.setVisible(true);
				burst1.clear();
				arrival1.clear();
				priority1.clear();
				process2.setVisible(true);
				burst2.clear();
				arrival2.clear();
				priority2.clear();
				process3.setVisible(true);
				burst3.clear();
				arrival3.clear();
				priority3.clear();
				process4.setVisible(false);
				process5.setVisible(false);
				process6.setVisible(false);
				process7.setVisible(false);
				process8.setVisible(false);
				break;
			case "4 Processes":
				process1.setVisible(true);
				burst1.clear();
				arrival1.clear();
				priority1.clear();
				process2.setVisible(true);
				burst2.clear();
				arrival2.clear();
				priority2.clear();
				process3.setVisible(true);
				burst3.clear();
				arrival3.clear();
				priority3.clear();
				process4.setVisible(true);
				burst4.clear();
				arrival4.clear();
				priority4.clear();
				process5.setVisible(false);
				process6.setVisible(false);
				process7.setVisible(false);
				process8.setVisible(false);
				break;
			case "5 Processes":
				process1.setVisible(true);
				burst1.clear();
				arrival1.clear();
				priority1.clear();
				process2.setVisible(true);
				burst2.clear();
				arrival2.clear();
				priority2.clear();
				process3.setVisible(true);
				burst3.clear();
				arrival3.clear();
				priority3.clear();
				process4.setVisible(true);
				burst4.clear();
				arrival4.clear();
				priority4.clear();
				process5.setVisible(true);
				burst5.clear();
				arrival5.clear();
				priority5.clear();
				process6.setVisible(false);
				process7.setVisible(false);
				process8.setVisible(false);
				break;
			case "6 Processes":
				process1.setVisible(true);
				burst1.clear();
				arrival1.clear();
				priority1.clear();
				process2.setVisible(true);
				burst2.clear();
				arrival2.clear();
				priority2.clear();
				process3.setVisible(true);
				burst3.clear();
				arrival3.clear();
				priority3.clear();
				process4.setVisible(true);
				burst4.clear();
				arrival4.clear();
				priority4.clear();
				process5.setVisible(true);
				burst5.clear();
				arrival5.clear();
				priority5.clear();
				process6.setVisible(true);
				burst6.clear();
				arrival6.clear();
				priority6.clear();
				process7.setVisible(false);
				process8.setVisible(false);
				break;
			case "7 Processes":
				process1.setVisible(true);
				burst1.clear();
				arrival1.clear();
				priority1.clear();
				process2.setVisible(true);
				burst2.clear();
				arrival2.clear();
				priority2.clear();
				process3.setVisible(true);
				burst3.clear();
				arrival3.clear();
				priority3.clear();
				process4.setVisible(true);
				burst4.clear();
				arrival4.clear();
				priority4.clear();
				process5.setVisible(true);
				burst5.clear();
				arrival5.clear();
				priority5.clear();
				process6.setVisible(true);
				burst6.clear();
				arrival6.clear();
				priority6.clear();
				process7.setVisible(true);
				burst7.clear();
				arrival7.clear();
				priority7.clear();
				process8.setVisible(false);
				break;
			case "8 Processes":
				process1.setVisible(true);
				burst1.clear();
				arrival1.clear();
				priority1.clear();
				process2.setVisible(true);
				burst2.clear();
				arrival2.clear();
				priority2.clear();
				process3.setVisible(true);
				burst3.clear();
				arrival3.clear();
				priority3.clear();
				process4.setVisible(true);
				burst4.clear();
				arrival4.clear();
				priority4.clear();
				process5.setVisible(true);
				burst5.clear();
				arrival5.clear();
				priority5.clear();
				process6.setVisible(true);
				burst6.clear();
				arrival6.clear();
				priority6.clear();
				process7.setVisible(true);
				burst7.clear();
				arrival7.clear();
				priority7.clear();
				process8.setVisible(true);
				burst8.clear();
				arrival8.clear();
				priority8.clear();
		
				break;
			default:
				process1.setVisible(false);
				process2.setVisible(false);
				process3.setVisible(false);
				process4.setVisible(false);
				process5.setVisible(false);
				process6.setVisible(false);
				process7.setVisible(false);
				process8.setVisible(false);
				break;
			}
		});
		
		
		//
		//
		// displaying the averages 
		//
		
		Label avgWaitLabel = new Label("Average Wait Time: ");
		Label avgTurnaroundLabel = new Label("Average Turnaround Time: ");
		
		// RESET THESE WITH THE LAMBDA EXPRESSION FROM THE
		// CALCULATE BUTTON ACTION IF THERE ARE GETTERS/SETTERS
		// TO DO SO! :D
		HBox avg1 = new HBox();
		avg1.getChildren().addAll(avgWaitLabel, avgWaitTime);
		avg1.setAlignment(Pos.CENTER);
		avg1.setPadding(new Insets(10, 10, 10, 10));
		HBox avg2 = new HBox();
		avg2.getChildren().addAll(avgTurnaroundLabel, avgTurnaroundTime);
		avg2.setAlignment(Pos.CENTER);
		avg2.setPadding(new Insets(10, 10, 10, 10));
		VBox averageBox = new VBox();
		averageBox.getChildren().addAll(avg1, avg2);
		averageBox.setPadding(new Insets(5, 5, 5, 5));
				
		VBox processDisplayBox = new VBox();
		processDisplayBox.setSpacing(5);
		processDisplayBox.getChildren().addAll(displayTitles, process1, process2, process3,
				process4, process5, process6, process7, process8, new Separator());
		
		VBox mainVBox = new VBox();
		mainVBox.getChildren().addAll(selections, processDisplayBox, averageBox, new Separator());
		
		// finalization
		PRIMARY_SCENE = new Scene(mainVBox, 700, 600);
		primaryStage.setScene(PRIMARY_SCENE);
		primaryStage.show();
	}
	
	public Scene getScene() {
		return PRIMARY_SCENE;
	}
	
	public int[] parseArray(TextField[] textFields, int nElems) {
		int [] times = new int [nElems];
		int count = 0;
		for(int i = 0; i < nElems; i++) {
			if(textFields[i].isVisible()  && !textFields[i].equals("")) {
				times[i] = Integer.parseInt(textFields[i].getText());
			}else {
				break;
			}	
			
		}
		return times;
		
	}

	private int[] trimArray(int[] array, int count) {
		int[] trimmedArray = new int[count];
		for(int i = 0; i < count; i++) {
			trimmedArray[i] = array[i];
		}
		return trimmedArray;
		
	}
	
}
