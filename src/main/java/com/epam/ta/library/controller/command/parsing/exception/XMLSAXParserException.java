package com.epam.ta.library.controller.command.parsing.exception;

public class XMLSAXParserException extends XMLParserException{


	private static final long serialVersionUID = -6869262223174831319L;
	
		public XMLSAXParserException(String message, Throwable cause) {
			super(message, cause);
		}

		public XMLSAXParserException(String message) {
			super(message);
		}
		
		public XMLSAXParserException(Throwable cause) {
			super(cause);
		}

}
