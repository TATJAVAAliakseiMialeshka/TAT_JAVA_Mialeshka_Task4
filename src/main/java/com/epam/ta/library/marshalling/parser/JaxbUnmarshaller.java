package com.epam.ta.library.marshalling.parser;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.epam.ta.library.marshalling.MarshallingException;

public class JaxbUnmarshaller {

	private final static String ERR_MARSH = "Exception occured during marshalling procedure";

	public <T> Object unmarshall(String xmlFileName, Class<T> className) throws MarshallingException {

		Object unmarshObj;

		try {
			File file = new File(xmlFileName);
			JAXBContext jaxbContext = JAXBContext.newInstance(className);
			Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

			unmarshObj = jaxbUnmarshaller.unmarshal(file);
		} catch (JAXBException e) {
			throw new MarshallingException(ERR_MARSH, e);
		}
		return unmarshObj;
	}
}
