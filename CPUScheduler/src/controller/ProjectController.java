package controller;

import model.ProcessBag;
import view.View;

public class ProjectController {
	private ProcessBag processbag;
	private SubController1 subController1;
	private SubController2 subController2;
	private SubController3 subController3;
	private View view;
	
	
	public ProjectController(ProcessBag processBag, KatiesScene katiesScene) {
		super();
		this.subController1 = new SubController1(processBag, katiesScene);
		this.subController2 = new SubController2(processBag, katiesScene);
		this.subController3 = new SubController3(processBag, katiesScene);

	}
	
}
