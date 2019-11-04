package model;

public class ProcessBag {

	private Process[] processes;
	private int timeframe;
	private double averageWaitingTime;
	private double averageTurnaroundTime;
	private int quantum;
	private int[] jobQueue;
	
	public ProcessBag(int timeframe, int[]burstTimes, int[]arrivalTimes) {
		super();
		this.timeframe = timeframe;
		this.processes = new Process[burstTimes.length];
		createProcesses(burstTimes, arrivalTimes);
		this.jobQueue = new FirstComeFirstServe(this.timeframe, this.processes).getJobQueue();
		calculateAverages();
	}
	

	public ProcessBag(int timeframe, int[]burstTimes, int[]arrivalTimes, boolean isSJF) {
		super();
		this.timeframe = timeframe;
		this.processes = new Process[burstTimes.length];
		createProcesses(burstTimes, arrivalTimes);
		this.jobQueue = new ShortestJobFirst(timeframe, processes).getJobQueue();
		calculateAverages();
	
	}
	
	public ProcessBag(int timeframe, int[]burstTimes, int[]arrivalTimes, int[]priorityLevels) {
		super();
		this.timeframe = timeframe;
		this.processes = new Process[burstTimes.length];
		createProcesses(burstTimes, arrivalTimes);
		addPriorityLevels(priorityLevels);
		this.jobQueue = new Priority(timeframe, processes).getJobQueue();
		calculateAverages();
		
	}

	public ProcessBag(int timeframe, int[]burstTimes, int[]arrivalTimes, int[]priorityLevels, int quantum) {
		super();
		this.timeframe = timeframe;
		this.processes = new Process[burstTimes.length];
		createProcesses(burstTimes, arrivalTimes);
		addPriorityLevels(priorityLevels);
		this.quantum = quantum;
		this.jobQueue = new RoundRobin(timeframe, processes, quantum).getJobQueue();
		calculateAverages();
	}
	
	private void createProcesses(int[] burstTimes, int[] arrivalTimes) {
		for(int i = 0; i < burstTimes.length; i++) {
			this.processes[i] = new Process(burstTimes[i], arrivalTimes[i]);
			this.processes[i].setProcessNumber(i + 1);
		}

	}
	
	private void addPriorityLevels(int[]priorityLevels) {
		for(int i = 0; i < priorityLevels.length; i++) {
			this.processes[i].setPriorityLevel(priorityLevels[i]);			
		}

	}
	
	private void calculateAverages() {
		int waitingTimeSum = 0;
		int turnaroundTimeSum = 0;
		double total = 0;
		for(int i = 0; i < this.processes.length; i++) {
			waitingTimeSum += this.processes[i].getWaitingTime();
			turnaroundTimeSum += this.processes[i].getTurnaroundTime();
			if(this.processes[i].getTurnaroundTime() != 0) {
				total++;
			}
		}
		if(total==0) {
			total = 1;
		}
		this.averageWaitingTime = waitingTimeSum / total;
		this.averageTurnaroundTime = turnaroundTimeSum / total;
		
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
	
	
}
