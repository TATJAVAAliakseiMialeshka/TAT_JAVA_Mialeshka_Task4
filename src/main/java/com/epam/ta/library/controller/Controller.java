package com.epam.ta.library.controller;

import org.apache.log4j.Logger;

import com.epam.ta.library.controller.command.Command;
import com.epam.ta.library.controller.command.CommandProvider;
import com.epam.ta.library.server.ParamExtractor;
import com.epam.ta.library.service.util.ServiceUtil;

public class Controller {

	private Controller() {
	}

	private static Controller instance;
	private final CommandProvider provider = new CommandProvider();

	private final static Logger log = Logger.getLogger(Controller.class);
	private static final String WRONG_ARG_FORMAT = "Requested operation failed due to wrong arguments format.";

	public static Controller getInstance() {
		if (null == instance) {
			instance = new Controller();
		}
		return instance;
	}

	public String executeTask(String commandStr) {

		Command command = null;
		ParamExtractor paramExtractor = null;
		String response = WRONG_ARG_FORMAT;
		if (null != commandStr && !commandStr.isEmpty()) {

			paramExtractor = new ParamExtractor(commandStr);
			if (paramExtractor.formCommand()) {
				String commandName = paramExtractor.getCommandName();
				String commandValue = paramExtractor.getCommandValue();

				if (ServiceUtil.notNullCheck(commandName)) {

					command = provider.getCommand(commandName);
					 
					response = command.execute(commandValue);
				}
			}
		}
		log.info(Thread.currentThread().getName() + " " + response);
		return response;
	}
}
