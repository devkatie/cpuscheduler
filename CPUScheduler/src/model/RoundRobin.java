package model;

import java.util.ArrayList;
import java.util.List;

public class RoundRobin {
	private int timeframe;
	private int[] jobQueue;
	private Process[] processes;
	private List<Process> processesList;
	private int quantum;

	public RoundRobin(int timeframe, Process[] processes, int quantum) {
		this.timeframe = timeframe;
		this.processes = processes;
		this.quantum = quantum;
		createProcessList();
		fillJobQueue();
	}

	private void fillJobQueue() {
		
	
	}
	

	private void createProcessList() {
		this.processesList = new ArrayList<Process>();
		for (int i = 0; i < processes.length; i++) {
			processesList.add(new Process(processes[i]));
		}

	}

	private void sortByPriority(List<Process> localProcessesList) {
		localProcessesList.sort((Process a, Process b) -> a.getPriorityLevel() - b.getPriorityLevel());

	}

	private void sortByBurstTime(List<Process> localProcessesList) {
		localProcessesList.sort((Process a, Process b) -> a.getBurstTime() - b.getBurstTime());

	}

	private void sortByArrivalTime() {
		processesList.sort((Process a, Process b) -> a.getArrivalTime() - b.getArrivalTime());

	}

	public int[] getJobQueue() {
		return jobQueue;
	}

}
