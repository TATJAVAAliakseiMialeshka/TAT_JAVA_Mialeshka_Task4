package com.epam.ta.library.controller.command.parsing.parser.impl;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.epam.ta.library.controller.command.Command;
import com.epam.ta.library.controller.command.CommandName;
import com.epam.ta.library.controller.command.parsing.exception.XMLDOMParserException;
import com.epam.ta.library.controller.command.parsing.exception.XMLParserException;
import com.epam.ta.library.controller.command.parsing.parser.XMLParser;
import com.epam.ta.library.controller.command.parsing.parser.handler.XMLDOMHandler;

public class XMLDOMParser extends XMLParser {
	
	public Map<CommandName, Command> runParser(String xmlPath) throws XMLParserException{
		DocumentBuilder domParser;
		XMLDOMHandler handler;
		try {
			/* create DOM analyzer */
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			domParser = dbf.newDocumentBuilder();
			/* get document tree */
			Document document = domParser.parse(RESOURCE_PATH+xmlPath);
			/* create document root element */
			Element root = document.getDocumentElement();
			/* get initialize flower collection*/
			
			handler = new XMLDOMHandler();
			
			Map<CommandName, Command> commandList = generateCommandsFromCommandEntities(handler.createCommandList(root));
			return commandList;
			
		} catch (SAXException e) {
			throw new XMLDOMParserException("SAX parser exception ", e);
			
		} catch (ParserConfigurationException e) {
			throw new XMLDOMParserException("configuration exception", e);

		} catch (IOException e) {
			throw new XMLDOMParserException("I/O stream error", e);
		}
		
	}
	
}
