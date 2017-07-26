package com.epam.ta.library.controller.command.parsing.parser.handler;

import java.util.ArrayList;
import java.util.List;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

import com.epam.ta.library.controller.command.parsing.entity.CommandEntity;
import com.epam.ta.library.controller.command.parsing.entity.CommandEntity.CommandEnum;

public class XMLStAXHandler {

	public List<CommandEntity> process(XMLStreamReader reader) throws XMLStreamException {

		List<CommandEntity> commands = new ArrayList<>();
		CommandEntity currentCommand = null;
		CommandEnum tagName = null;

		while (reader.hasNext()) {
			int type = reader.next();

			switch (type) {
			case XMLStreamReader.START_ELEMENT:
				tagName = CommandEnum.valueOf(reader.getLocalName().toUpperCase());

				// String tag = reader.getLocalName();
				if (tagName == CommandEnum.COMMAND) {
					currentCommand = new CommandEntity();
					if (reader.getAttributeCount() > 0) {
						currentCommand.setAuthority(reader.getAttributeValue(0));
					}
				}
				break;

			case XMLStreamReader.CHARACTERS:
				String text = reader.getText().trim();
				if (text != null && !text.isEmpty()) {
					switch (tagName) {
					case NAME:
						currentCommand.setCommandName(text);
						break;
					case CLASS:
						currentCommand.setCommandClass(text);
						break;
					}
				}
				break;

			case XMLStreamReader.END_ELEMENT:
				tagName = CommandEnum.valueOf(reader.getLocalName().toUpperCase());
				switch (tagName) {
				case COMMAND:
					commands.add(currentCommand);
					currentCommand = null;
					break;
				}
				break;
			}
		}
		return commands;
	}
}
