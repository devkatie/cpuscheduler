package model;

//Written by: Danny Fayaud
/* All model sorting algorithms utilize the same basic structure.  First, an ArrayList is constructed with deep copies of the
Process array for easy sorting and burstTime decrementing without altering the "good" copies.  The product of each algorithm
is the jobQueue array: a large array with each element mapped to a single time unit that represents the entirety of the CPU
operation cycle. fillJobQueue() contains a unique algorithm for each class.    
*/

//ShortestRemainingTimeFirst is basically SJF with preemption

import java.util.ArrayList;
import java.util.List;

public class ShortestRemainingTimeFirst {
	private int timeframe;
	private int[] jobQueue;
	private Process[] processes;
	private List<Process> processesList;

	public ShortestRemainingTimeFirst(int timeframe, Process[] processes) {
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

//A second arrayList is formed and Processes in the sorted first ArrayList are added when timer = arrivaltime() 
		List<Process> localProcessesList = new ArrayList<Process>();
		sortByArrivalTime();
		
//while loop runs until the jobQueue is full
		while (timer != jobQueue.length && index < processes.length) {

//Fills jobQueue with 0 (IDLE) until timer hits a Process Arrival time				
			if (localProcessesList.isEmpty()) {
				localProcessesList.add(processesList.get(index));
			}
			
//adds processes to localList when time is right
			for (int i = 0; i < processes.length; i++) {
				if (timer >= processesList.get(i).getArrivalTime()
						&& !localProcessesList.contains(processesList.get(i))) {
					localProcessesList.add(processesList.get(i));
					
				}
// KEY DIFFERENCE between SJF and SRTF: by sortingByBurstTime immediately after a Process is added to the localList, SJF becomes preeemptive.
			sortByBurstTime(localProcessesList);
			}
			if(timer == 0) {
				sortByBurstTime(localProcessesList);
			}
			

			if (index >= localProcessesList.size() || localProcessesList.get(index).getArrivalTime() > timer) {
				jobQueue[timer] = 0;
				timer++;
				
//Should a Process finish (Burst time decremented to 0) arrival and burst times are recorded and we move to the next
//element in the Array List.  Times are referenced by the ArrayList, but stored in the Process array.					
			} else if (localProcessesList.get(index).getBurstTime() == 0) {
				processes[localProcessesList.get(index).getProcessNumber() - 1].setWaitingTime(
						timer - processes[localProcessesList.get(index).getProcessNumber() - 1].getArrivalTime()
								- processes[localProcessesList.get(index).getProcessNumber() - 1].getBurstTime());
				processes[localProcessesList.get(index).getProcessNumber() - 1].setTurnaroundTime(
						processes[localProcessesList.get(index).getProcessNumber() - 1].getWaitingTime()
								+ processes[localProcessesList.get(index).getProcessNumber() - 1].getBurstTime());
				index++;
				sortByBurstTime(localProcessesList);
			} else {

//jobQueue is assigned process number until burstTime is depleted.
				this.jobQueue[timer] = localProcessesList.get(index).getProcessNumber();
				localProcessesList.get(index).setBurstTime(localProcessesList.get(index).getBurstTime() - 1);

				timer++;
			}
		}

	}

	
//lambda expressions to sort ArrayLists by sort and Arrival time	
	private void sortByBurstTime(List<Process> localProcessesList) {
		localProcessesList.sort((Process a, Process b) -> a.getBurstTime() - b.getBurstTime());

	}

	private void sortByArrivalTime() {
		processesList.sort((Process a, Process b) -> a.getArrivalTime() - b.getArrivalTime());

	}

	public int[] getJobQueue() {
		return jobQueue;
	}

//Creates a deep copy ArrayList of the original Process array.		
	private void createProcessList() {
		this.processesList = new ArrayList<Process>();
		for (int i = 0; i < processes.length; i++) {
			processesList.add(new Process(processes[i]));
		}

	}

}
