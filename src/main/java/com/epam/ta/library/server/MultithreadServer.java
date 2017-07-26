package com.epam.ta.library.server;

import java.util.List;

import org.apache.log4j.Logger;

import com.epam.ta.library.controller.Controller;

public class MultithreadServer {

	private static MultithreadServer instance;

	private final static Logger log = Logger.getLogger(Controller.class);

	private MultithreadServer() {
	}
	
	public static synchronized MultithreadServer getInstance() {
		if (null == instance) {
			instance = new MultithreadServer();
		}
		return instance;
	}
	

	public List<String> executeUserCommands(String [] userCommandMap) {
		RequestDispatcher requestDispatcher = null;

			requestDispatcher = new RequestDispatcher(userCommandMap);
			Thread thread = new Thread(requestDispatcher);

			thread.start();
			try {
				thread.join();
			} catch (InterruptedException e) {
				log.error(e);
			}
		return requestDispatcher.getResponceList();
	}

}
