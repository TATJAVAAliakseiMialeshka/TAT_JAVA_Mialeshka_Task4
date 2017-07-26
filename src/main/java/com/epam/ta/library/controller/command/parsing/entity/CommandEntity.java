package com.epam.ta.library.controller.command.parsing.entity;

public class CommandEntity {

	private String commandName;
	
	private String authority;
	
	private String commandClass;
	
	
	public enum CommandEnum {
		NAME, COMMAND, COMMANDS, CLASS, AUTHORITY;
	}

	public String getCommandName() {
		return commandName;
	}

	public String getAuthority() {
		return authority;
	}
	
	public void setCommandName(String commandName) {
		this.commandName = commandName;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}

	public String getCommandClass() {
		return commandClass;
	}

	public void setCommandClass(String commandClass) {
		this.commandClass = commandClass;
	}

}
