package com.epam.ta.library.marshalling;

import com.epam.ta.library.exception.AbstractAppException;

public class MarshallingException extends AbstractAppException{

	private static final long serialVersionUID = -1721788138197668125L;

	public MarshallingException(Exception e) {
		super(e);
	}

	public MarshallingException(String message, Throwable innerEx) {
		super(message, innerEx);
	}

	public MarshallingException(String message) {
		super(message);
	}

}
