package model;

public class GanttStringBuilder {

	private ProcessBag processBag;
	private String ganttString;
	private int[] jobQueue;
	private int nElems;
	private final String AVERAGES = "Avg:\t\t";
	private final String ARROW = " -> ";
	private final String HEADER = "Process\t\tWait Time\tTurnAroundTime\n---------------------------------------------\n";
	private final String GANTT_CHART_HEADER = "\n\nGantt Chart\n-------------\n";
	private final String ROUND_ROBIN_ERROR = "Unfortunately, Round Robin does not support dynamic arrival times.\nAll arrival times are set to zero.\n\n";

	public GanttStringBuilder(ProcessBag processBag) {
		super();
		this.processBag = processBag;
		this.jobQueue = processBag.getJobQueue();
		this.nElems = processBag.getProcesses().length;
		buildGanttString();
	}

	public String getGanttString() {
		return ganttString;
	}

	private void buildGanttString() {
		ganttString = "";
		
		if(processBag.isRoundRobin()) {
			ganttString += ROUND_ROBIN_ERROR;
		}
		
		ganttString += HEADER;
		for (int i = 0; i < nElems; i++) {
			ganttString += "P" + processBag.getProcesses()[i].getProcessNumber() + "\t\t\t"
					+ processBag.getProcessWaitingTime(i + 1) +"       \t\t" + processBag.getProcessTurnaroundTime(i + 1) + "\n";
		}
		
		ganttString += AVERAGES + "\t" + processBag.getAverageWaitingTime() + "\t\t\t" + processBag.getAverageTurnaroundTime();
		ganttString += GANTT_CHART_HEADER;
		
		for(int i = 1; i < jobQueue.length; i++) {
			if(jobQueue[i] != jobQueue[i-1]) {
				if(jobQueue[i - 1] == 0) {
					ganttString += "IDLE" + ARROW;
				}else {
				ganttString += "P" + jobQueue[i - 1] + ARROW;
				}
			}
			if(i == jobQueue.length - 1) {
				if(jobQueue[i] == 0) {
					ganttString += "IDLE";
				}else {
				ganttString += "P" + jobQueue[i];
				}
			}
			
		}
		

	}

}
