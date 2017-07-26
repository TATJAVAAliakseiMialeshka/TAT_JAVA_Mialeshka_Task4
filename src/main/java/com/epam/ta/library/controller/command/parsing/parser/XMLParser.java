package com.epam.ta.library.controller.command.parsing.parser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.epam.ta.library.controller.command.Command;
import com.epam.ta.library.controller.command.CommandName;
import com.epam.ta.library.controller.command.parsing.entity.CommandEntity;
import com.epam.ta.library.controller.command.parsing.exception.XMLParserException;

public abstract class XMLParser {

	private final static String COMMAND_CLASS_PATH = "com.epam.ta.library.controller.command.impl.";

	public final String RESOURCE_PATH = XMLParserRunner.class.getProtectionDomain().getCodeSource().getLocation()
			.getPath() + "../../src/main/resources/";

	private final static String COMMAND_CLASS_REFL_ERROR = "Failed to create command class through reflection access.";
	public final String COMMAND_CLASS_NOT_FOULD = "Command class wasn't found in provided path.";
	public final String XML_PARSE_ERROR = "XML parsing global error.";
	
	//protected Map<CommandName, Command> commandList = Collections.emptyMap();

	public abstract Map<CommandName, Command> runParser(String xmlPath) throws XMLParserException;

	public Map<CommandName, Command> generateCommandsFromCommandEntities(List<CommandEntity> commandEntities) throws XMLParserException {

		Map<CommandName, Command> commandList = new HashMap<>();
		
		if (null != commandEntities) {
			for (CommandEntity entity : commandEntities) {
				try {
					String commandClassName = COMMAND_CLASS_PATH + entity.getCommandClass();
					Class<?> commandClass = Class.forName(commandClassName);

					commandList.put(CommandName.valueOf(entity.getCommandName().toUpperCase()),
							(Command) commandClass.newInstance());

					
				} catch (ClassNotFoundException e) {
					throw new XMLParserException(COMMAND_CLASS_NOT_FOULD);
				} catch (InstantiationException | IllegalAccessException e) {
					throw new XMLParserException(COMMAND_CLASS_REFL_ERROR);
				} 
			}
		}
		return commandList;
	}

}
