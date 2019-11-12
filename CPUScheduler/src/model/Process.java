package model;

// Written by: Danny Fayaud
// The Process class is the basic unit upon which the rest of the project is built.  
// Unique elements include a deep copy constructor for copying Processes without altering master copies.

/* PROJECT ASSUMPTIONS: 1) CPUScheduler works with a maximum Ready Queue of eight processes.
 * 						2) The timeframe for processes to execute is limited to 200 units.  Processes can be added that would not complete in
 * 							this timeframe; however, their waiting and turnaround times will be both be set to zero, and those values
 * 							would not be used when calculating average  waiting and turnaround time.        
 */


public class Process {

	private int burstTime;
	private int arrivalTime;
	private int priorityLevel;
	private int waitingTime;
	private int turnaroundTime;
	private int processNumber;

	public Process(int burstTime, int arrivalTime) {
		super();
		this.burstTime = burstTime;
		this.arrivalTime = arrivalTime;
	}

	public Process(Process process) {  //Deep copy constructor
		super();
		this.processNumber = process.getProcessNumber();
		this.arrivalTime = process.getArrivalTime();
		this.burstTime = process.getBurstTime();
		this.priorityLevel = process.getPriorityLevel();
		this.waitingTime = process.getWaitingTime();
		this.turnaroundTime = process.getTurnaroundTime();
	}

	public Process() {
		super();
	}
	

	public int getBurstTime() {
		return burstTime;
	}

	public void setBurstTime(int burstTime) {
		this.burstTime = burstTime;
	}

	public int getWaitingTime() {
		return waitingTime;
	}

	public void setWaitingTime(int waitingTime) {
		this.waitingTime = waitingTime;
	}

	public int getTurnaroundTime() {
		return turnaroundTime;
	}

	public void setTurnaroundTime(int turnaroundTime) {
		this.turnaroundTime = turnaroundTime;
	}

	public int getArrivalTime() {
		return arrivalTime;
	}

	public void setArrivalTime(int arrivalTime) {
		this.arrivalTime = arrivalTime;
	}

	public int getPriorityLevel() {
		return priorityLevel;
	}

	public void setPriorityLevel(int priorityLevel) {
		this.priorityLevel = priorityLevel;
	}
	
	public int getProcessNumber() {
		return processNumber;
	}

	public void setProcessNumber(int processNumber) {
		this.processNumber = processNumber;
	}

	
}