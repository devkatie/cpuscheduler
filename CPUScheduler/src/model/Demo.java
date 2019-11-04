package model;

import java.util.Arrays;

public class Demo {

	public static void main(String[] args) {

		int timeframe = 200;
		int[] burstTimes = {10, 20, 30, 40, 50};
		int[] arrivalTimes = {0,0,0,0,0};
		int[] priorityLevels = {40, 0, 50, 10, 0};

		ProcessBag bag = new ProcessBag(timeframe, burstTimes, arrivalTimes, priorityLevels);
		System.out.println(Arrays.toString(bag.getJobQueue()));

		for (int i = 0; i < bag.getProcesses().length; i++) {
			System.out.println(bag.getProcesses()[i].getProcessNumber() + "   " + bag.getProcesses()[i].getWaitingTime() + "    "
					+ bag.getProcesses()[i].getTurnaroundTime());
		}
		System.out.println(bag.getAverageWaitingTime() + "    " + bag.getAverageTurnaroundTime());

	}

}
