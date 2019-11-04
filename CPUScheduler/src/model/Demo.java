package model;

import java.util.Arrays;

public class Demo {

	public static void main(String[] args) {

		int timeframe = 200;
		int[] burstTimes = { 10, 19, 50, 20, 10 };
		int[] arrivalTimes = {0,0,0,0,0};

		ProcessBag bag = new ProcessBag(timeframe, burstTimes, arrivalTimes);
		System.out.println(Arrays.toString(bag.getJobQueue()));

		for (int i = 0; i < bag.getProcesses().length; i++) {
			System.out.println(bag.getProcesses()[i].getProcessNumber() + "   " + bag.getProcesses()[i].getWaitingTime() + "    "
					+ bag.getProcesses()[i].getTurnaroundTime());
		}
		System.out.println(bag.getAverageWaitingTime() + "    " + bag.getAverageTurnaroundTime());

	}

}
