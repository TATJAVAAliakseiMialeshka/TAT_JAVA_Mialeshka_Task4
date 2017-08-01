package com.epam.ta.library.marshalling.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.epam.ta.library.marshalling.MarshallingException;

public class JaxbMarshaller {

	private final static String ERR_EMPTY_M_OBJ = "Empty object received in marshalling method";
	private final static String ERR_XML_NOT_SPECIFIED = "XML file path not specified.";
	private final static String ERR_XML_NOT_FOUND = "Xml output file not found";
	private final static String ERR_MARSH = "Exception occured during marshalling procedure";

	public <T> File marsh(T marshObject, String xmlFilePath) throws MarshallingException {

		if (null == marshObject) {
			throw new MarshallingException(ERR_EMPTY_M_OBJ);
		}
		if (null == xmlFilePath) {
			throw new MarshallingException(ERR_XML_NOT_SPECIFIED);
		}

		File resultXMLFile = null;
		try {
			JAXBContext context = JAXBContext.newInstance(marshObject.getClass());
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

			m.marshal(marshObject, new FileOutputStream(xmlFilePath));
			resultXMLFile = new File(xmlFilePath);

		} catch (FileNotFoundException e) {
			throw new MarshallingException(ERR_XML_NOT_FOUND, e);
		} catch (JAXBException e) {
			throw new MarshallingException(ERR_MARSH, e);
		}
		return resultXMLFile;
	}

}
