package com.epam.ta.library.controller.command.parsing.parser;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderFactory;

import com.epam.ta.library.controller.command.parsing.entity.CommandEntity;
import com.epam.ta.library.controller.command.parsing.exception.XMLDOMParserException;
import com.epam.ta.library.controller.command.parsing.exception.XMLSAXParserException;
import com.epam.ta.library.controller.command.parsing.exception.XMLStaxParserExceptin;
import com.epam.ta.library.controller.command.parsing.parser.handler.XMLDOMHandler;
import com.epam.ta.library.controller.command.parsing.parser.handler.XMLSAXHandler;
import com.epam.ta.library.controller.command.parsing.parser.handler.XMLStAXHandler;

public class XMLParserRunner{
	
	private final String RESOURCE_PATH = XMLParserRunner.class.getProtectionDomain().getCodeSource().getLocation().getPath() + "../../src/main/resources/";
	private final String XML_COMMAND_FILE = "Command.xml";
	
	private List<CommandEntity> commandsSAX = new ArrayList<>();
	private List<CommandEntity> commandsStAX = new ArrayList<>();
	private List<CommandEntity> commandsDOM = new ArrayList<>();
	
	public List<CommandEntity> getCommandsSAX() {
		return commandsSAX;
	}

	public List<CommandEntity> getCommandsStAX() {
		return commandsStAX;
	}

	public List<CommandEntity> getCommandsDOM() {
		return commandsDOM;
	}

	public void runSAXParser() throws XMLSAXParserException{
		XMLReader reader;
		try {
			reader = XMLReaderFactory.createXMLReader();
			XMLSAXHandler handler = new XMLSAXHandler();
			reader.setContentHandler(handler);
			/*
			// ��������� �������� ����������������
			reader.setFeature("http://xml.org/sax/features/validation", true);*/
			// ��������� ��������� ����������� ����
			reader.setFeature("http://xml.org/sax/features/namespaces", true);
			// ��������� ����������� �����
			reader.setFeature("http://xml.org/sax/features/string-interning",
			true);
			// ���������� ��������� ����
			reader.setFeature("http://apache.org/xml/features/validation/schema", false);
			
			reader.parse(new InputSource(RESOURCE_PATH + XML_COMMAND_FILE));
			commandsSAX = handler.getCommands();
		} catch (SAXException e) {
			throw new XMLSAXParserException("glogal SAX exc", e);
		} catch (IOException e) {
			throw new XMLSAXParserException("xml file for parse not found", e);
		}
		
	}
	
	public void runStAXParser () throws XMLStaxParserExceptin {
		XMLStreamReader reader;
		InputStream stream;
		XMLInputFactory factory = XMLInputFactory.newInstance();
	
		try {
			stream = new FileInputStream(RESOURCE_PATH+XML_COMMAND_FILE);
			reader = factory.createXMLStreamReader(stream);
			XMLStAXHandler parser = new XMLStAXHandler();
			commandsStAX = parser.process(reader);
		} catch (XMLStreamException e) {
			throw new XMLStaxParserExceptin("global StAX exception", e);
		} catch (FileNotFoundException e) {
			throw new XMLStaxParserExceptin("xml file for parse not found", e);
		}
	}
	
	public void runDOMParser() throws XMLDOMParserException{
		DocumentBuilder domParser;
		XMLDOMHandler handler;
		try {
			/* create DOM analyzer */
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			domParser = dbf.newDocumentBuilder();
			/* get document tree */
			Document document = domParser.parse(RESOURCE_PATH+XML_COMMAND_FILE);
			/* create document root element */
			Element root = document.getDocumentElement();
			/* get initialize flower collection*/
			
			handler = new XMLDOMHandler();
			commandsDOM = handler.createCommandList(root);
		} catch (SAXException e) {
			throw new XMLDOMParserException("SAX parser exception ", e);
			
		} catch (ParserConfigurationException e) {
			throw new XMLDOMParserException("configuration exception", e);

		} catch (IOException e) {
			throw new XMLDOMParserException("I/O stream error", e);
		}
		
	}
	
}
