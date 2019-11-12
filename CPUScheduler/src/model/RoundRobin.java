package model;

//Written by: Danny Fayaud
/* All model sorting algorithms utilize the same basic structure.  First, an ArrayList is constructed with deep copies of the
Process array for easy sorting and burstTime decrementing without altering the "good" copies.  The product of each algorithm
is the jobQueue array: a large array with each element mapped to a single time unit that represents the entirety of the CPU
operation cycle. fillJobQueue() contains a unique algorithm for each class.    
*/

//RoundRobin created problems as the basic algorithm utilized by the other Schedulers didn't hold up
//Therefore RR is the only algorithm that doesn't handle dynamic arrival times
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
		
//A second arrayList is formed and Processes in the sorted first ArrayList are added when timer = arrivaltime()
		List<Process> localProcessesList = new ArrayList<Process>();
		sortByArrivalTime();
		
//while loop runs until the jobQueue is full... slight change as the index conditional can't be linked 
//to processes.length as the localList will grow well beyond that size 		
		while (timer != jobQueue.length && (index < localProcessesList.size() || index == 0)) {

//quantum timer conditional pops element from the front of the queue and pushes on the end, then increments to next element
			if (quantumTimer == quantum) {
				quantumTimer = 0;
				localProcessesList.add(localProcessesList.get(index++));
			}

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
//Fills jobQueue with 0 (IDLE) until timer hits a Process Arrival time.. an artifact from previous code since
//arrivaltimes are not supported, but good to keep in place in case it can be patched
			if (index >= localProcessesList.size() || localProcessesList.get(index).getArrivalTime() > timer ) {
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
				
//quantumTimer resets after moving to next element
				quantumTimer = 0;
			} else {

				this.jobQueue[timer] = localProcessesList.get(index).getProcessNumber();
				localProcessesList.get(index).setBurstTime(localProcessesList.get(index).getBurstTime() - 1);
				quantumTimer++;
				timer++;
			}
		}
		
//RR misbehaves... produces a good jobQueue, but problematic results for waiting/arrival times.
//patchwaitingTimes uses the jobQueue to calculate good times.		
		patchWaitingTimes();

	}

//Creates a deep copy ArrayList of the original Process array.	
	private void createProcessList() {
		this.processesList = new ArrayList<Process>();
		for (int i = 0; i < processes.length; i++) {
			processesList.add(new Process(processes[i]));
		}

	}

//method to parse good times from jobQueue
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

//lambda expressions for sorting ArrayLists
	private void sortByArrivalTime() {
		processesList.sort((Process a, Process b) -> a.getArrivalTime() - b.getArrivalTime());

	}

	public int[] getJobQueue() {
		return jobQueue;
	}

}
