package model;

import java.util.ArrayList;
import java.util.List;

public class ShortestJobFirst {

	private int timeframe;
	private int[] jobQueue;
	private Process[] processes;
	private List<Process> processesList;
	
	public ShortestJobFirst(int timeframe, Process[] processes) {
		this.timeframe = timeframe;
		this.processes = processes;
		createProcessList();
		fillJobQueue();
	}

	public int[] getJobQueue() {
		return null;
	}
	
	public void createProcessList() {
		this.processesList = new ArrayList<Process>();
		for (int i = 0; i < processes.length; i++) {
			processesList.add(new Process(processes[i]));
		}
	}
}
