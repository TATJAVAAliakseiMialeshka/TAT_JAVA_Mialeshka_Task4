package com.epam.ta.library.controller.command.parsing.parser;

import com.epam.ta.library.controller.command.parsing.parser.impl.XMLDOMParser;
import com.epam.ta.library.controller.command.parsing.parser.impl.XMLSAXParser;
import com.epam.ta.library.controller.command.parsing.parser.impl.XMLStAXParser;

public class ParserFactory {

	private static ParserFactory instance;

	private final XMLSAXParser xmlsaxRunner = new XMLSAXParser();
	private final XMLStAXParser xmlstaxRunner = new XMLStAXParser();
	private final XMLDOMParser xmldomRunner = new XMLDOMParser();

	public enum ParserType {
		SAX, STAX, DOM
	}

	private ParserFactory() {
	}

	public static synchronized ParserFactory getInstance() {
		if (null == instance) {
			instance = new ParserFactory();
		}
		return instance;
	}

	public XMLParser getParser(ParserType type) {
		XMLParser parser = xmlsaxRunner;
		switch (type) {
		case SAX:
			parser = xmlsaxRunner;
			break;
		case STAX:
			parser = xmlsaxRunner;
			break;
		case DOM:
			parser = xmlsaxRunner;
		}
		return parser;
	}
}
