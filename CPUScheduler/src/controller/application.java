package controller;

import javafx.application.Application;
import javafx.stage.Stage;
import view.View;

public class application extends Application{

	public static void main(String[] args) {
		launch(args);
	
		
		

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		View view = new View();
		primaryStage.setScene(view.getScene());
		primaryStage.show();
	}

}
