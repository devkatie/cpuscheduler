package model;

import java.util.ArrayList;
import java.util.List;

public class FirstComeFirstServe {

	private int timeframe;
	private int[] jobQueue;
	private Process[] processes;
	private List<Process> processesList;

	public FirstComeFirstServe(int timeframe, Process[] processes) {
		super();
		this.timeframe = timeframe;
		this.processes = processes;
		createProcessList();
		fillJobQueue();

	}

	private void fillJobQueue() {
		int timer = 0;
		int index = 0;
		this.jobQueue = new int[timeframe];
		sortByArrivalTime();
		while (timer != jobQueue.length && index < processes.length) {
			if (processesList.get(index).getArrivalTime() > timer) {
				jobQueue[timer] = 99;
				timer++;
			} else if (processesList.get(index).getBurstTime() == 0) {
				processes[processesList.get(index).getProcessNumber() - 1].setTurnaroundTime(timer);
				processes[processesList.get(index).getProcessNumber() - 1].setWaitingTime(
						timer - processes[processesList.get(index).getProcessNumber() - 1].getArrivalTime()
								- processes[processesList.get(index).getProcessNumber() - 1].getBurstTime());
				index++;

			} else {

				this.jobQueue[timer] = processesList.get(index).getProcessNumber();
				processesList.get(index).setBurstTime(processesList.get(index).getBurstTime() - 1);
				timer++;
			}
		}

	}

	public int[] getJobQueue() {
		return jobQueue;

	}

	private void sortByArrivalTime() {
		processesList.sort((Process a, Process b) -> a.getArrivalTime() - b.getArrivalTime());

	}

	public void createProcessList() {
		this.processesList = new ArrayList<Process>();
		for (int i = 0; i < processes.length; i++) {
			processesList.add(new Process(processes[i]));
		}
	}

}
