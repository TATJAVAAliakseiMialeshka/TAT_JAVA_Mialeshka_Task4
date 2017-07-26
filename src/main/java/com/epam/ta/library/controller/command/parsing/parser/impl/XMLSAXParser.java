package com.epam.ta.library.controller.command.parsing.parser.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.epam.ta.library.controller.command.Command;
import com.epam.ta.library.controller.command.CommandName;
import com.epam.ta.library.controller.command.parsing.exception.XMLParserException;
import com.epam.ta.library.controller.command.parsing.exception.XMLSAXParserException;
import com.epam.ta.library.controller.command.parsing.parser.XMLParser;
import com.epam.ta.library.controller.command.parsing.parser.handler.XMLSAXHandler;

public class XMLSAXParser extends XMLParser {

	public Map<CommandName, Command> runParser(String xmlPath) throws XMLParserException {
		XMLReader reader;
		Map<CommandName, Command> commandList = null;
		try {
			reader = XMLReaderFactory.createXMLReader();
			XMLSAXHandler handler = new XMLSAXHandler();
			reader.setContentHandler(handler);

			reader.setFeature("http://xml.org/sax/features/namespaces", true);
			reader.setFeature("http://xml.org/sax/features/string-interning", true);
			reader.setFeature("http://apache.org/xml/features/validation/schema", false);

			reader.parse(new InputSource(RESOURCE_PATH + xmlPath));

			commandList = generateCommandsFromCommandEntities(handler.getCommands());
			

		} catch (SAXException e) {
			throw new XMLSAXParserException("glogal SAX exc", e);
		} catch (IOException e) {
			throw new XMLSAXParserException("xml file for parse not found", e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return commandList;
	}
}
