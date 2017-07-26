package com.epam.ta.library.marshalling;


import org.apache.log4j.Logger;

public class MarshallingRunner {

	private final static Logger log = Logger.getLogger(MarshallingRunner.class);
	
	public static void main(String[] args) {
		MarshallingPerformer performer = new MarshallingPerformer();
		try {
			performer.performMarshallilng();
			performer.performUnmarshalling();
		} catch (MarshallingException e) {
			log.error(e);
		}
	}
	
}
