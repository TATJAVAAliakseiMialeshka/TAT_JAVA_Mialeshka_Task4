package com.epam.ta.library.controller.command.parsing.parser.handler;

import java.util.List;

import com.epam.ta.library.controller.command.parsing.entity.CommandEntity;

public interface XMLHandler {

	List<CommandEntity> getCommandListFromXML();
}
