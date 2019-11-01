package model;

public class FirstComeFirstServe {
	
	private int timeframe; 
	private int[] jobQueue;
	private Process[] processes;

	public FirstComeFirstServe(int timeframe, Process[] processes) {
		super();
		this.timeframe = timeframe;
		this.processes = processes;
		fillJobQueue();
		
	}

	private void fillJobQueue() {
		sortByArrivalTime();
		for(int i = 0; i < this.jobQueue.length; i++) {
			
		}
		
	}

	public int[] getJobQueue() {
		// TODO Auto-generated method stub
		return null;
	}

	private void sortByArrivalTime() {
		// TODO Auto-generated method stub
		
	}
}
