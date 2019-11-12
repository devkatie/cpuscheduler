package model;

//Written by: Danny Fayaud
/* All model sorting algorithms utilize the same basic structure.  First, an ArrayList is constructed with deep copies of the
Process array for easy sorting and burstTime decrementing without altering the "good" copies.  The product of each algorithm
is the jobQueue array: a large array with each element mapped to a single time unit that represents the entirety of the CPU
operation cycle. fillJobQueue() contains a unique algorithm for each class.    
*/

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

	public void fillJobQueue() {
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
				
			}
			
//if multiple Processes are added to the localList at timer = 0, it will need to be sorted immediately.
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

//lambda expression to sort ArrayList by bursttime	
	private void sortByBurstTime(List<Process> localProcessesList) {
		localProcessesList.sort((Process a, Process b) -> a.getBurstTime() - b.getBurstTime());

	}

//lambda expression to sort ArrayList by arrivaltime		
	private void sortByArrivalTime() {
		processesList.sort((Process a, Process b) -> a.getArrivalTime() - b.getArrivalTime());

	}

	public int[] getJobQueue() {
		return jobQueue;
	}

//Creates a deep copy ArrayList of the original Process array.		
	public void createProcessList() {
		this.processesList = new ArrayList<Process>();
		for (int i = 0; i < processes.length; i++) {
			processesList.add(new Process(processes[i]));
		}
	}
}
