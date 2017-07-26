package com.epam.ta.library.controller.command;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.epam.ta.library.controller.command.parsing.exception.XMLParserException;
import com.epam.ta.library.controller.command.parsing.exception.XMLSAXParserException;
import com.epam.ta.library.controller.command.parsing.parser.ParserFactory;
import com.epam.ta.library.controller.command.parsing.parser.XMLParserRunner;
import com.epam.ta.library.controller.command.parsing.parser.ParserFactory.ParserType;
import com.epam.ta.library.controller.session.SessionStorage;

public class CommandProvider {

	private final Map<CommandName, Command> repository = new HashMap<>();

	private final static Logger log = Logger.getLogger(CommandProvider.class);

	public final String XML_COMMAND_FILE = "Command.xml";
	public final String ERROR_XML_PARSE = "Error received during parsing xml file. Command repository now empty.";

	public CommandProvider() {

		try {
			ParserFactory parserFactory = ParserFactory.getInstance();
			repository.putAll(parserFactory.getParser(ParserType.DOM).runParser(XML_COMMAND_FILE));
			System.out.println();
		} catch (XMLParserException e) {
			log.error(ERROR_XML_PARSE + e);
		} catch (Exception e) {
			log.error(ERROR_XML_PARSE + e);
		}
	}

	public Command getCommand(String commandString) {

		CommandName commandName = null;
		Command command = null;
		SessionStorage session = null;
		XMLParserRunner xmlCommandExtractor = new XMLParserRunner();
		try {
			xmlCommandExtractor.runSAXParser();
		} catch (XMLSAXParserException e) {
			log.error(e);
		}
		try {
			commandName = CommandName.valueOf(commandString.toUpperCase());
			session = SessionStorage.getInstance();
			if (session.checkPermission(commandName)) {
				command = repository.get(commandName);
			} else {
				command = repository.get(CommandName.ACCESS_DINIED);
			}
		} catch (IllegalArgumentException | NullPointerException e) {
			log.error(e);
			command = repository.get(CommandName.WRONG_REQUEST);
		}
		return command;
	}

}
