package model;

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

	public Process(Process process) {
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

	@Override
	public String toString() {
		return "Process [burstTime=" + burstTime + ", arrivalTime=" + arrivalTime + ", priorityLevel=" + priorityLevel
				+ ", waitingTime=" + waitingTime + ", turnaroundTime=" + turnaroundTime + ", processNumber="
				+ processNumber + "]";
	}
	
}
