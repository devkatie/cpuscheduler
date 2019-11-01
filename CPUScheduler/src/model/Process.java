package model;

public class Process {
		
	private int burstTime;
	private int arrivalTime;
	private int priorityLevel;
	private int waitingTime;
	private int turnaroundTime;

	
	public Process(int burstTime, int arrivalTime) {
		super();
		this.burstTime = burstTime;
		this.arrivalTime = arrivalTime;
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
}
