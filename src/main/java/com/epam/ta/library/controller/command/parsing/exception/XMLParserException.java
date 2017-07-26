package com.epam.ta.library.controller.command.parsing.exception;

public class XMLParserException extends Exception{

	private static final long serialVersionUID = -727316864426953925L;

	public XMLParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public XMLParserException(String message) {
		super(message);
	}

	public XMLParserException(Throwable cause) {
		super(cause);
	}
	
	
}
