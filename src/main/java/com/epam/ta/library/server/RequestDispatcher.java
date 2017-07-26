package com.epam.ta.library.server;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.epam.ta.library.controller.Controller;

public class RequestDispatcher implements Callable<List<String>> {

	private String [] userCommands;

	private Controller controller;

	private static final String WRONG_ARG_FORMAT = "Requested operation failed due to wrong arguments format.";

	public List<String> getResponceList() {
		return responceList;
	}

	private List<String> responceList = new ArrayList<>();

	public RequestDispatcher(String [] userCommandMap) {
		super();
		this.userCommands = userCommandMap;
	}


	@Override
	public List<String> call() throws Exception {
		controller = Controller.getInstance();
		if (null != userCommands) {
			for (String userCommand : userCommands) {
				String responce = controller.executeTask(userCommand);
				responceList.add(responce);
			}
		} else {
			responceList.add(WRONG_ARG_FORMAT);
		}
		return responceList;
	}

}
