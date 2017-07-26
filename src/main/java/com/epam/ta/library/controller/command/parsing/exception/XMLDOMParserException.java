package com.epam.ta.library.controller.command.parsing.exception;

public class XMLDOMParserException extends XMLParserException {

	private static final long serialVersionUID = -5224433665265275853L;

	public XMLDOMParserException(String message) {
		super(message);
	}

	public XMLDOMParserException(String message, Throwable cause) {
		super(message, cause);
	}

	public XMLDOMParserException(Throwable cause) {
		super(cause);
	}
	
}
