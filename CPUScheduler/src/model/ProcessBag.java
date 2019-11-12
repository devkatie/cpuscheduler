package model;

// Written by: Danny Fayaud
// The ProcessBag is the class that is passed to View. It contains all of the necessary data for displaying algorithm output.

public class ProcessBag {

	private Process[] processes;
	private int timeframe;
	private double averageWaitingTime;
	private double averageTurnaroundTime;
	private int[]arrivalTimes;
	private int[]burstTimes;
	private int quantum;
	private int[] jobQueue;
	private boolean isRoundRobin;
	
// Constructors utilize polymorphism and will calculate algorithms based on what elements are passed upon declaration.  

	
// FirstComeFirstServe Constructor	
	public ProcessBag(int timeframe, int[] burstTimes, int[] arrivalTimes) {
		super();
		this.timeframe = timeframe;
		this.arrivalTimes = arrivalTimes;
		this.burstTimes = burstTimes;
		this.processes = new Process[burstTimes.length];
		createProcesses(burstTimes, arrivalTimes);
		this.jobQueue = new FirstComeFirstServe(this.timeframe, this.processes).getJobQueue();
		calculateAverages();
		this.isRoundRobin = false;
	}

// Shortest Jobs Constructor is shared by SJF and SRTF.  True = SJF, false = SRTF	
	public ProcessBag(int timeframe, int[] burstTimes, int[] arrivalTimes, boolean isSJF) {
		super();
		this.timeframe = timeframe;
		this.processes = new Process[burstTimes.length];
		this.arrivalTimes = arrivalTimes;
		this.burstTimes = burstTimes;
		createProcesses(burstTimes, arrivalTimes);
		if (isSJF) {
			this.jobQueue = new ShortestJobFirst(timeframe, processes).getJobQueue();
		} else {
			this.jobQueue = new ShortestRemainingTimeFirst(timeframe, processes).getJobQueue();
		}
		calculateAverages();
		this.isRoundRobin = false;

	}
	
//Priority Constructor
	public ProcessBag(int timeframe, int[] burstTimes, int[] arrivalTimes, int[] priorityLevels) {
		super();
		this.timeframe = timeframe;
		this.processes = new Process[burstTimes.length];
		this.arrivalTimes = arrivalTimes;
		this.burstTimes = burstTimes;
		createProcesses(burstTimes, arrivalTimes);
		addPriorityLevels(priorityLevels);
		this.jobQueue = new Priority(timeframe, processes).getJobQueue();
		calculateAverages();
		this.isRoundRobin = false;

	}

//RoundRobin Constructor	
	public ProcessBag(int timeframe, int[] burstTimes, int[] arrivalTimes, int quantum) {
		super();
		this.timeframe = timeframe;
		this.processes = new Process[burstTimes.length];
		this.arrivalTimes = setArrivalTimesToZero(arrivalTimes);
		this.burstTimes = burstTimes;
		createProcesses(burstTimes, arrivalTimes);
		this.quantum = quantum;
		this.jobQueue = new RoundRobin(timeframe, processes, quantum).getJobQueue();
		calculateAverages();
		this.isRoundRobin = true;
	}

	
// RoundRobin fails when supplied with arrivalTimes other than zero.  This method sets times to zero.  	
	private int[] setArrivalTimesToZero(int[] arrivalTimes) {
		for(int i = 0; i < arrivalTimes.length; i++) {
			arrivalTimes[i] = 0;
		}
		return arrivalTimes;
	}

	
//Process creation based on supplied arrays	
	private void createProcesses(int[] burstTimes, int[] arrivalTimes) {
		for (int i = 0; i < burstTimes.length; i++) {
			this.processes[i] = new Process(burstTimes[i], arrivalTimes[i]);
			this.processes[i].setProcessNumber(i + 1);
		}

	}

//Priority Levels added for Priority algorithm	
	private void addPriorityLevels(int[] priorityLevels) {
		for (int i = 0; i < priorityLevels.length; i++) {
			this.processes[i].setPriorityLevel(10 - priorityLevels[i]);
		}

	}

//Calculates averages and stores resulting double rounded to one decimal place	
	private void calculateAverages() {
		int waitingTimeSum = 0;
		int turnaroundTimeSum = 0;
		double total = 0;
		for (int i = 0; i < this.processes.length; i++) {
			waitingTimeSum += this.processes[i].getWaitingTime();
			turnaroundTimeSum += this.processes[i].getTurnaroundTime();
			if (this.processes[i].getTurnaroundTime() != 0) {
				total++;
			}
		}
		if (total == 0) {
			total = 1;
		}
		this.averageWaitingTime = (Math.round(waitingTimeSum / total) * 10) /10.0;
		this.averageTurnaroundTime = (Math.round(turnaroundTimeSum / total)* 10) / 10.0;

	}
	
	public int getProcessWaitingTime(int processNumber) {
		return this.processes[processNumber - 1].getWaitingTime();
	}
	
	public int getProcessTurnaroundTime(int processNumber) {
		return this.processes[processNumber - 1].getTurnaroundTime();
	}

	public Process[] getProcesses() {
		return processes;
	}

	public void setProcesses(Process[] processes) {
		this.processes = processes;
	}

	public double getAverageWaitingTime() {
		return averageWaitingTime;
	}

	public void setAverageWaitingTime(int averageWaitingTime) {
		this.averageWaitingTime = averageWaitingTime;
	}

	public double getAverageTurnaroundTime() {
		return averageTurnaroundTime;
	}

	public void setAverageTurnaroundTime(int averageTurnaroundTime) {
		this.averageTurnaroundTime = averageTurnaroundTime;
	}

	public int getQuantum() {
		return quantum;
	}

	public void setQuantum(int quantum) {
		this.quantum = quantum;
	}

	public int getTimeframe() {
		return timeframe;
	}

	public void setTimeframe(int timeframe) {
		this.timeframe = timeframe;
	}

	public int[] getJobQueue() {
		return jobQueue;
	}

	public void setJobQueue(int[] jobQueue) {
		this.jobQueue = jobQueue;
	}

	public int[] getArrivalTimes() {
		return arrivalTimes;
	}

	public void setArrivalTimes(int[] arrivalTimes) {
		this.arrivalTimes = arrivalTimes;
	}

	public int[] getBurstTimes() {
		return burstTimes;
	}

	public void setBurstTimes(int[] burstTimes) {
		this.burstTimes = burstTimes;
	}

	public void setAverageWaitingTime(double averageWaitingTime) {
		this.averageWaitingTime = averageWaitingTime;
	}

	public void setAverageTurnaroundTime(double averageTurnaroundTime) {
		this.averageTurnaroundTime = averageTurnaroundTime;
	}
	
	public boolean isRoundRobin() {
		return isRoundRobin;
	}

}
