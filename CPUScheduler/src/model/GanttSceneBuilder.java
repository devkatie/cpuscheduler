package model;

import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GanttSceneBuilder {
	
	private String ganttString;
	private Stage stage;
	private ProcessBag processBag;
	private static VBox vBox;
	private TextArea textArea;
	
	public GanttSceneBuilder(ProcessBag processBag) {
		super();
		this.processBag = processBag;
		this.vBox = new VBox();
		this.textArea = new TextArea();
		textArea.setMinSize(400, 400);
		textArea.setText(new GanttStringBuilder(processBag).getGanttString());
		vBox.getChildren().add(textArea);
		
		
	}
	
	public static void showGanttScene() {
		Scene scene = new Scene(vBox, 600, 600);
		Stage stage = new Stage();
		stage.setScene(scene);
		stage.show();
	}

	
}
