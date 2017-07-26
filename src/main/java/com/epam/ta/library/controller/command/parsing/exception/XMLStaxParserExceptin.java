package com.epam.ta.library.controller.command.parsing.exception;

public class XMLStaxParserExceptin extends XMLParserException{

	private static final long serialVersionUID = 2779121800996854837L;

	public XMLStaxParserExceptin(String message) {
		super(message);
	}

	public XMLStaxParserExceptin(String message, Throwable cause) {
		super(message, cause);
	}

	public XMLStaxParserExceptin(Throwable cause) {
		super(cause);
	}

	
}
