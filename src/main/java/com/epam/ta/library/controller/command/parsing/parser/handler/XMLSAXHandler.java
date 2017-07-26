package com.epam.ta.library.controller.command.parsing.parser.handler;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import com.epam.ta.library.controller.command.parsing.entity.CommandEntity;
import com.epam.ta.library.controller.command.parsing.entity.CommandEntity.CommandEnum;

public class XMLSAXHandler implements ContentHandler {

	private List<CommandEntity> commands = new ArrayList<>();
	private CommandEntity currentCommand;
	private CommandEnum currentEnum;

	public void characters(char[] ch, int start, int length) throws SAXException {

		String tagBody = new String(ch, start, length).trim();
		if (currentEnum != null) {
			switch (currentEnum) {
			case NAME:
				currentCommand.setCommandName(tagBody);
				break;
			case CLASS:
				currentCommand.setCommandClass(tagBody);
				break;			
			default:
				break;
			}
		}
	}

	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub

	}

	public void endElement(String uri, String localName, String qName) throws SAXException {

		if (localName.equalsIgnoreCase(CommandEnum.COMMAND.name())) {
			
			commands.add(currentCommand);
		}
		currentEnum = null;
	}

	public void endPrefixMapping(String prefix) throws SAXException {
		// TODO Auto-generated method stub

	}

	public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
		// TODO Auto-generated method stub

	}

	public void processingInstruction(String target, String data) throws SAXException {
		// TODO Auto-generated method stub

	}

	public void setDocumentLocator(Locator locator) {
		// TODO Auto-generated method stub

	}

	public void skippedEntity(String name) throws SAXException {
		// TODO Auto-generated method stub

	}

	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub

	}

	public void startElement(String uri, String localName, String qName, Attributes atts) throws SAXException {
		if (localName.equalsIgnoreCase(CommandEnum.COMMAND.name())) {
			currentCommand = new CommandEntity();
			if (atts.getLength() > 0) {
				currentCommand.setAuthority(atts.getValue(0));
			}
		} else {
			if (!localName.equalsIgnoreCase(CommandEnum.COMMANDS.name())) {

				currentEnum = CommandEnum.valueOf(qName.toUpperCase());
			}
		}
	}

	public void startPrefixMapping(String prefix, String uri) throws SAXException {
		// TODO Auto-generated method stub

	}

	public List<CommandEntity> getCommands() {
		return commands;
	}

}
