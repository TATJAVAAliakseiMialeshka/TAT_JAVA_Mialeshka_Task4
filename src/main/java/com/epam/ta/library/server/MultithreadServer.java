package com.epam.ta.library.server;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

	public List<String> executeUserCommands(String[] userCommandMap) {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		Future<List<String>> controllerResult = executor.submit(new RequestDispatcher(userCommandMap));
		List<String> resultList = null;
		try {
			resultList = controllerResult.get();

		} catch (InterruptedException e) {
			log.error("Thread " + Thread.currentThread().getName() + " was interrupted.");
		} catch (ExecutionException e) {
			log.error("Exception during executions user commands on thread + " + Thread.currentThread().getName());
		}
		return resultList;
	}

}
