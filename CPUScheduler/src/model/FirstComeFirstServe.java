package model;

//Written by: Danny Fayaud
/* All sorting algorithms utilize the same basic structure.  First, an ArrayList is constructed with deep copies of the
Process array for easy sorting and burstTime decrementing without altering the "good" copies.  The product of each algorithm
is the jobQueue array: a large array with each element mapped to a single time unit that represents the entirety of the CPU
operation cycle. fillJobQueue() contains a unique algorithm for each class.    
*/

//FirstComeFirstServe is uniquely simple and is the only sorting algorithm that doesn't utilize a second local ArrayList in its code.


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

//while loop runs until the jobQueue is full		
		
		while (timer != jobQueue.length && index < processes.length) {
			
//Fills jobQueue with 0 (IDLE) until timer hits a Process Arrival time		
			if (processesList.get(index).getArrivalTime() > timer) {
				jobQueue[timer] = 0;
				timer++;
				
//Should a Process finish (Burst time decremented to 0) arrival and burst times are recorded and we move to the next
//element in the Array List.  Times are referenced by the ArrayList, but stored in the Process array.
			} else if (processesList.get(index).getBurstTime() == 0) {

				processes[processesList.get(index).getProcessNumber() - 1].setWaitingTime(
						timer - processes[processesList.get(index).getProcessNumber() - 1].getArrivalTime()
								- processes[processesList.get(index).getProcessNumber() - 1].getBurstTime());
				processes[processesList.get(index).getProcessNumber() - 1]
						.setTurnaroundTime(processes[processesList.get(index).getProcessNumber() - 1].getWaitingTime()
								+ processes[processesList.get(index).getProcessNumber() - 1].getBurstTime());
				index++;

			} else {
				
//jobQueue is assigned process number until burstTime is depleted.
				this.jobQueue[timer] = processesList.get(index).getProcessNumber();
				processesList.get(index).setBurstTime(processesList.get(index).getBurstTime() - 1);
				timer++;
			}
		}

	}

	public int[] getJobQueue() {
		return jobQueue;

	}

//lambda expression for sorting ArrayList by arrivalTime
	private void sortByArrivalTime() {
		processesList.sort((Process a, Process b) -> a.getArrivalTime() - b.getArrivalTime());

	}

//Creates a deep copy ArrayList of the original Process array.	
	public void createProcessList() {
		this.processesList = new ArrayList<Process>();
		for (int i = 0; i < processes.length; i++) {
			processesList.add(new Process(processes[i]));
		}
	}

}
