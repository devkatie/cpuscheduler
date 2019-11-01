package model;

public class ShortestRemainingTimeFirst extends Scheduler {

	public ShortestRemainingTimeFirst(int timeframe, Process[] processes) {
		super(timeframe, processes);
		this.timeframe = timeframe;
	}

}
