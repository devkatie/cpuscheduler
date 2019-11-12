package model;

public class ProcessBag {

	private Process[] processes;
	private int timeframe;
	private double averageWaitingTime;
	private double averageTurnaroundTime;
	private int[]arrivalTimes;
	private int[]burstTimes;
	private int quantum;
	private int[] jobQueue;

	public ProcessBag(int timeframe, int[] burstTimes, int[] arrivalTimes) {
		super();
		this.timeframe = timeframe;
		this.arrivalTimes = arrivalTimes;
		this.burstTimes = burstTimes;
		this.processes = new Process[burstTimes.length];
		createProcesses(burstTimes, arrivalTimes);
		this.jobQueue = new FirstComeFirstServe(this.timeframe, this.processes).getJobQueue();
		calculateAverages();
	}

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

	}

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

	}

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
	}

	private int[] setArrivalTimesToZero(int[] arrivalTimes) {
		for(int i = 0; i < arrivalTimes.length; i++) {
			arrivalTimes[i] = 0;
		}
		return arrivalTimes;
	}

	private void createProcesses(int[] burstTimes, int[] arrivalTimes) {
		for (int i = 0; i < burstTimes.length; i++) {
			this.processes[i] = new Process(burstTimes[i], arrivalTimes[i]);
			this.processes[i].setProcessNumber(i + 1);
		}

	}

	private void addPriorityLevels(int[] priorityLevels) {
		for (int i = 0; i < priorityLevels.length; i++) {
			this.processes[i].setPriorityLevel(10 - priorityLevels[i]);
		}

	}

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
		this.averageWaitingTime = waitingTimeSum / total;
		this.averageTurnaroundTime = turnaroundTimeSum / total;

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
	
	

}
