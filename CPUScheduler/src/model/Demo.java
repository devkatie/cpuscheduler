package model;

import java.util.Arrays;

import javafx.application.Application;
import javafx.stage.Stage;

public class Demo extends Application{

	public static void main(String[] args) {
		launch(args);
		

	}

	@Override
	public void start(Stage arg0) throws Exception {
		int timeframe = 200;
		int[] burstTimes = {10, 10, 10, 10, 10};
		int[] arrivalTimes = {0,0,0,0,0};
		int[] priorityLevels = {1,2,3,4,5,6,7,8,9,1};

		ProcessBag bag = new ProcessBag(timeframe, burstTimes, arrivalTimes);
		
		System.out.println(Arrays.toString(bag.getJobQueue()));
		System.out.println(new GanttStringBuilder(bag).getGanttString());
		new GanttSceneBuilder(bag).showGanttScene();
		
		
		for (int i = 0; i < bag.getProcesses().length; i++) {
			System.out.println(bag.getProcesses()[i].getProcessNumber() + "   " + bag.getProcesses()[i].getWaitingTime() + "    "
					+ bag.getProcesses()[i].getTurnaroundTime());
		}
		System.out.println(bag.getAverageWaitingTime() + "    " + bag.getAverageTurnaroundTime());
	}

}
