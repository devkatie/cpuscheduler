package view;

import java.util.Random;

import javafx.*;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
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

public class View extends Application {

	Scene PRIMARY_SCENE;
	
//	public static void main(String[] args) {
//		launch(args);
//	}

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
		
		Button randomBurstButton = new Button("Randomize Burst Times");
		Button calculate = new Button("Calculate");
		
		HBox processBox = new HBox(processLabel, processCombos, randomBurstButton, calculate);
		processBox.setSpacing(10);
		processBox.setAlignment(Pos.TOP_LEFT);
		processBox.setPadding(new Insets(10, 10, 10, 10));
		
		// setting up algorithm combobox & its features
		Label algoLabel = new Label("Algorithm to Test: ");
		ComboBox<String> algoCombos = new ComboBox<String>();
		algoCombos.setPromptText("--Select--");
		algoCombos.getItems().add("RR: Round Robin");
		algoCombos.getItems().add("SJF: Shortest Job First");
		algoCombos.getItems().add("FCFS: First Come First Serve");
		algoCombos.getItems().add("SRTF: Shortest Remaining Time First");
		HBox algoBox = new HBox(algoLabel, algoCombos);
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
		HBox displayTitles = new HBox();
		displayTitles.getChildren().addAll(processLabel2, burstTimeLabel, waitTimeLabel, totalTimeLabel);
		displayTitles.setSpacing(100);
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
		HBox process1 = new HBox();
		process1.getChildren().addAll(p1, burst1, wait1, total1);
		process1.setSpacing(115);
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
		HBox process2 = new HBox();
		process2.getChildren().addAll(p2, burst2, wait2, total2);
		process2.setSpacing(115);
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
		HBox process3 = new HBox();
		process3.getChildren().addAll(p3, burst3, wait3, total3);
		process3.setSpacing(115);
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
		HBox process4 = new HBox();
		process4.getChildren().addAll(p4, burst4, wait4, total4);
		process4.setSpacing(115);
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
		HBox process5 = new HBox();
		process5.getChildren().addAll(p5, burst5, wait5, total5);
		process5.setSpacing(115);
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
		HBox process6 = new HBox();
		process6.getChildren().addAll(p6, burst6, wait6, total6);
		process6.setSpacing(115);
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
		HBox process7 = new HBox();
		process7.getChildren().addAll(p7, burst7, wait7, total7);
		process7.setSpacing(115);
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
		HBox process8 = new HBox();
		process8.getChildren().addAll(p8, burst8, wait8, total8);
		process8.setSpacing(115);
		process8.setAlignment(Pos.BASELINE_CENTER);
		
		//
		// ACTION EVENTS
		//
		
		//
		// calculates the cpu scheduler chosen
		calculate.setOnAction(e -> {
			// write shit here for controller interaction (?)
		});
		
		//
		// generates random burst times
		randomBurstButton.setOnAction(e -> {
			// ? Random random = new Random();
			// generate random int between 1 and 100
			// (int)(Math.random()*((100 - 1) + 1)) + 1
			burst1.setText(((int)(Math.random()*((25 - 1) + 1)) + 1) + "");
			burst2.setText(((int)(Math.random()*((25 - 1) + 1)) + 1) + "");
			burst3.setText(((int)(Math.random()*((25 - 1) + 1)) + 1) + "");
			burst4.setText(((int)(Math.random()*((25 - 1) + 1)) + 1) + "");
			burst5.setText(((int)(Math.random()*((25 - 1) + 1)) + 1) + "");
			burst6.setText(((int)(Math.random()*((25 - 1) + 1)) + 1) + "");
			burst7.setText(((int)(Math.random()*((25 - 1) + 1)) + 1) + "");
			burst8.setText(((int)(Math.random()*((25 - 1) + 1)) + 1) + "");
			
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
				process2.setVisible(true);
				burst2.clear();
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
				process2.setVisible(true);
				burst2.clear();
				process3.setVisible(true);
				burst3.clear();
				process4.setVisible(false);
				process5.setVisible(false);
				process6.setVisible(false);
				process7.setVisible(false);
				process8.setVisible(false);
				break;
			case "4 Processes":
				process1.setVisible(true);
				burst1.clear();
				process2.setVisible(true);
				burst2.clear();
				process3.setVisible(true);
				burst3.clear();
				process4.setVisible(true);
				burst4.clear();
				process5.setVisible(false);
				process6.setVisible(false);
				process7.setVisible(false);
				process8.setVisible(false);
				break;
			case "5 Processes":
				process1.setVisible(true);
				burst1.clear();
				process2.setVisible(true);
				burst2.clear();
				process3.setVisible(true);
				burst3.clear();
				process4.setVisible(true);
				burst4.clear();
				process5.setVisible(true);
				burst5.clear();
				process6.setVisible(false);
				process7.setVisible(false);
				process8.setVisible(false);
				break;
			case "6 Processes":
				process1.setVisible(true);
				burst1.clear();
				process2.setVisible(true);
				burst2.clear();
				process3.setVisible(true);
				burst3.clear();
				process4.setVisible(true);
				burst4.clear();
				process5.setVisible(true);
				burst5.clear();
				process6.setVisible(true);
				burst6.clear();
				process7.setVisible(false);
				process8.setVisible(false);
				break;
			case "7 Processes":
				process1.setVisible(true);
				burst1.clear();
				process2.setVisible(true);
				burst2.clear();
				process3.setVisible(true);
				burst3.clear();
				process4.setVisible(true);
				burst4.clear();
				process5.setVisible(true);
				burst5.clear();
				process6.setVisible(true);
				burst6.clear();
				process7.setVisible(true);
				burst7.clear();
				process8.setVisible(false);
				break;
			case "8 Processes":
				process1.setVisible(true);
				burst1.clear();
				process2.setVisible(true);
				burst2.clear();
				process3.setVisible(true);
				burst3.clear();
				process4.setVisible(true);
				burst4.clear();
				process5.setVisible(true);
				burst5.clear();
				process6.setVisible(true);
				burst6.clear();
				process7.setVisible(true);
				burst7.clear();
				process8.setVisible(true);
				burst8.clear();
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
				
		VBox processDisplayBox = new VBox();
		processDisplayBox.setSpacing(5);
		processDisplayBox.getChildren().addAll(displayTitles, process1, process2, process3,
				process4, process5, process6, process7, process8, new Separator());
		
		Text ganttTitle = new Text("Gantt Chart");
		ganttTitle.setFont(Font.font("Tahoma", 32));
		HBox ganttHBox = new HBox();
		ganttHBox.getChildren().add(ganttTitle);
		ganttHBox.setAlignment(Pos.CENTER);
		
		//
		// Visualization of Gantt Chart
		HBox ganttChart = new HBox();
		
		StackPane chart1 = new StackPane();
		Text chart1text = new Text("P1");
		chart1text.setFill(Color.LIGHTSLATEGRAY);
		Rectangle slot1 = new Rectangle(50, 50);
		slot1.setStroke(Color.DARKRED);
		slot1.setStrokeWidth(3);
		chart1.getChildren().addAll(slot1, chart1text);

		StackPane chart2 = new StackPane();
		Text chart2text = new Text("P2");
		chart2text.setFill(Color.LIGHTSLATEGRAY);
		Rectangle slot2 = new Rectangle(50, 50);
		slot2.setStroke(Color.DARKORANGE);
		slot2.setStrokeWidth(3);
		chart2.getChildren().addAll(slot2, chart2text);
		
		StackPane chart3 = new StackPane();
		Text chart3text = new Text("P3");
		chart3text.setFill(Color.LIGHTSLATEGRAY);
		Rectangle slot3 = new Rectangle(50, 50);
		slot3.setStroke(Color.YELLOW);
		slot3.setStrokeWidth(3);
		chart3.getChildren().addAll(slot3, chart3text);
		
		StackPane chart4 = new StackPane();
		Text chart4text = new Text("P4");
		chart4text.setFill(Color.LIGHTSLATEGRAY);
		Rectangle slot4 = new Rectangle(50, 50);
		slot4.setStroke(Color.LAWNGREEN);
		slot4.setStrokeWidth(3);
		chart4.getChildren().addAll(slot4, chart4text);
		
		StackPane chart5 = new StackPane();
		Text chart5text = new Text("P5");
		chart5text.setFill(Color.LIGHTSLATEGRAY);
		Rectangle slot5 = new Rectangle(50, 50);
		slot5.setStroke(Color.ROYALBLUE);
		slot5.setStrokeWidth(3);
		chart5.getChildren().addAll(slot5, chart5text);
		
		StackPane chart6 = new StackPane();
		Text chart6text = new Text("P6");
		chart6text.setFill(Color.LIGHTSLATEGRAY);
		Rectangle slot6 = new Rectangle(50, 50);
		slot6.setStroke(Color.SLATEBLUE);
		slot6.setStrokeWidth(3);
		chart6.getChildren().addAll(slot6, chart6text);
		
		StackPane chart7 = new StackPane();
		Text chart7text = new Text("P7");
		chart7text.setFill(Color.LIGHTSLATEGRAY);
		Rectangle slot7 = new Rectangle(50, 50);
		slot7.setStroke(Color.BLUEVIOLET);
		slot7.setStrokeWidth(3);
		chart7.getChildren().addAll(slot7, chart7text);
		
		StackPane chart8 = new StackPane();
		Text chart8text = new Text("P8");
		chart8text.setFill(Color.LIGHTSLATEGRAY);
		Rectangle slot8 = new Rectangle(50, 50);
		slot8.setStroke(Color.MEDIUMVIOLETRED);
		slot8.setStrokeWidth(3);
		chart8.getChildren().addAll(slot8, chart8text);
		
		ganttChart.getChildren().addAll(chart1, chart2, chart3, chart4, chart5, chart6, chart7, chart8);
		ganttChart.setSpacing(3);
		ganttChart.setAlignment(Pos.CENTER);
		
		VBox mainVBox = new VBox();
		mainVBox.getChildren().addAll(selections, processDisplayBox, ganttHBox, ganttChart);
		
		// finalization
		PRIMARY_SCENE = new Scene(mainVBox, 700, 600);
		primaryStage.setScene(PRIMARY_SCENE);
		primaryStage.show();
	}
	
	public Scene getScene() {
		return PRIMARY_SCENE;
	}
	
}
