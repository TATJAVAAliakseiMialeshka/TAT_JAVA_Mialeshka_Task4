package com.epam.ta.library.controller.command.parsing.parser.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Map;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.ta.library.controller.command.Command;
import com.epam.ta.library.controller.command.CommandName;
import com.epam.ta.library.controller.command.parsing.exception.XMLParserException;
import com.epam.ta.library.controller.command.parsing.exception.XMLStaxParserExceptin;
import com.epam.ta.library.controller.command.parsing.parser.XMLParser;
import com.epam.ta.library.controller.command.parsing.parser.handler.XMLStAXHandler;

public class XMLStAXParser extends XMLParser {

	public Map<CommandName, Command> runParser(String xmlPath) throws XMLParserException {
		XMLStreamReader reader;
		InputStream stream;
		XMLInputFactory factory = XMLInputFactory.newInstance();

		try {
			stream = new FileInputStream(RESOURCE_PATH + xmlPath);
			reader = factory.createXMLStreamReader(stream);
			XMLStAXHandler parser = new XMLStAXHandler();

			Map<CommandName, Command> commandList = generateCommandsFromCommandEntities(parser.process(reader));
			return commandList;

		} catch (XMLStreamException e) {
			throw new XMLStaxParserExceptin("global StAX exception", e);
		} catch (FileNotFoundException e) {
			throw new XMLStaxParserExceptin("xml file for parse not found", e);
		}
	}

}
