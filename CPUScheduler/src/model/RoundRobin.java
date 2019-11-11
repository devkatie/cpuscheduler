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
		int timer = 0;
		int index = 0;
		int quantumTimer = 0;
		this.jobQueue = new int[timeframe];
		List<Process> localProcessesList = new ArrayList<Process>();
		sortByArrivalTime();
		while (timer != jobQueue.length && (index < localProcessesList.size() || index == 0)) {

			if (quantumTimer == quantum) {
				quantumTimer = 0;
				localProcessesList.add(localProcessesList.get(index++));
			}

			if (localProcessesList.isEmpty()) {
				localProcessesList.add(processesList.get(index));
	
			}
			for (int i = 0; i < processes.length; i++) {
				if (timer >= processesList.get(i).getArrivalTime()
						&& !localProcessesList.contains(processesList.get(i))) {
					localProcessesList.add(processesList.get(i));


				}

			}

			if (index >= localProcessesList.size() || localProcessesList.get(index).getArrivalTime() > timer ) {
				jobQueue[timer] = 99;
				timer++;
			} else if (localProcessesList.get(index).getBurstTime() == 0) {

				processes[localProcessesList.get(index).getProcessNumber() - 1].setWaitingTime(
						timer - processes[localProcessesList.get(index).getProcessNumber() - 1].getArrivalTime()
								- processes[localProcessesList.get(index).getProcessNumber() - 1].getBurstTime());
				processes[localProcessesList.get(index).getProcessNumber() - 1].setTurnaroundTime(
						processes[localProcessesList.get(index).getProcessNumber() - 1].getWaitingTime()
								+ processes[localProcessesList.get(index).getProcessNumber() - 1].getBurstTime());
				index++;
				quantumTimer = 0;
			} else {

				this.jobQueue[timer] = localProcessesList.get(index).getProcessNumber();
				localProcessesList.get(index).setBurstTime(localProcessesList.get(index).getBurstTime() - 1);
				quantumTimer++;
				timer++;
			}
		}
		patchWaitingTimes();

	}

	private void createProcessList() {
		this.processesList = new ArrayList<Process>();
		for (int i = 0; i < processes.length; i++) {
			processesList.add(new Process(processes[i]));
		}

	}

	public void patchWaitingTimes() {
		for (int i = 0; i < processes.length; i++) {
			for (int j = jobQueue.length - 1; j >= 0; j--) {
				if (jobQueue[j] == i + 1) {
					processes[i].setTurnaroundTime(j + 1);
					break;
				}
			}
			processes[i].setWaitingTime(
					processes[i].getTurnaroundTime() - processes[i].getArrivalTime() - processes[i].getBurstTime());
		}

	}


	private void sortByArrivalTime() {
		processesList.sort((Process a, Process b) -> a.getArrivalTime() - b.getArrivalTime());

	}

	public int[] getJobQueue() {
		return jobQueue;
	}

}
