package com.epam.ta.library.controller.command.parsing.parser.handler;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.epam.ta.library.controller.command.parsing.entity.CommandEntity;


public class XMLDOMHandler {

	public List<CommandEntity> createCommandList(Element root) {

		List<CommandEntity> commands = new ArrayList<>();

		NodeList commandNodes = root.getElementsByTagName("command");
		for (int i = 0; i < commandNodes.getLength(); i++) {
			CommandEntity command = new CommandEntity();
			Element commandEl = (Element) commandNodes.item(i);
			if (commandEl.hasAttribute("authority")) {
				command.setAuthority(commandEl.getAttribute("authority"));
			}
			Element commandName = getDaughterElement(commandEl, "name");
			if(null != commandName){
				command.setCommandName(commandName.getTextContent().trim());
			}
			Element commandClass = getDaughterElement(commandEl, "class");
			if(null != commandClass){
				command.setCommandClass(commandName.getTextContent().trim());
			}
			commands.add(command);
		}
		return commands;
	}

	public Element getDaughterElement(Element parentEl, String childElName) {
		NodeList elements = parentEl.getElementsByTagName(childElName);
		return (Element) elements.item(0);

	}

	public String getElementValue(Element parentEl, String childElName) {
		Element element = getDaughterElement(parentEl, childElName);
		return element != null ? element.getNodeValue() : null;
	}

}
