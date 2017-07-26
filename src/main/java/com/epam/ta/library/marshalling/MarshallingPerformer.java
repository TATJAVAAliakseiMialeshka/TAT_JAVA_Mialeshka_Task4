package com.epam.ta.library.marshalling;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.log4j.Logger;

import com.epam.ta.library.marshalling.entity.BookEntities;
import com.epam.ta.library.marshalling.entity.BookEntity;
import com.epam.ta.library.marshalling.parser.JaxbMarshaller;
import com.epam.ta.library.marshalling.parser.JaxbUnmarshaller;

public class MarshallingPerformer {

	private final static Logger log = Logger.getLogger(MarshallingPerformer.class);

	private final static String RESOURCE_PATH = MarshallingRunner.class.getProtectionDomain().getCodeSource()
			.getLocation().getPath() + "../../src/main/resources/";

	private static final String XML_ENTITY_PATH = "book_marshalling.xml";

	private static final String ERR_XML_NOT_FOUND = "XML output not found";
	private static final String ERR_XML_READ = "Error reading XML output file";
	private static final String MARSH_RESULT_MSG = "Marshalling. Created xml file:\n";
	private static final String UNMARSH_RESULT_MSG = "\nUnmarshalling. Readed books from xml:\n";

	public void performMarshallilng() throws MarshallingException {

		JaxbMarshaller marshaller = new JaxbMarshaller();
		File resultXML = marshaller.marsh(generateTestBookList(), RESOURCE_PATH + XML_ENTITY_PATH);
		if (null != resultXML) {
			printXMLFileToConsole(resultXML);
		}
	}

	public void performUnmarshalling() throws MarshallingException {

		JaxbUnmarshaller unmarshaller = new JaxbUnmarshaller();
		Object unmarshalObject = unmarshaller.unmarshall(RESOURCE_PATH + XML_ENTITY_PATH, BookEntities.class);
		if (unmarshalObject instanceof BookEntities) {
			BookEntities bookEntities = (BookEntities) unmarshalObject;
			List<BookEntity> books = bookEntities.getBooks();
			printBookList(books);
		}

	}

	public void printBookList(List<BookEntity> books) {
		if (null != books) {
			log.info(UNMARSH_RESULT_MSG);
			for (BookEntity book : books) {
				log.info(book.toString());
			}
		}
	}

	public void printXMLFileToConsole(File file) throws MarshallingException {
		if (null != file) {
			log.info(MARSH_RESULT_MSG);
			try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)))) {

				String line = null;

				while ((line = br.readLine()) != null) {
					log.info(line);
				}
			} catch (FileNotFoundException e) {
				throw new MarshallingException(ERR_XML_NOT_FOUND);
			} catch (IOException e) {
				throw new MarshallingException(ERR_XML_READ);
			}
		}
	}

	public BookEntities generateTestBookList() {

		List<BookEntity> books = new ArrayList<>();
		BookEntities bookEntityMarshList = new BookEntities();

		books.addAll(Arrays.asList(new BookEntity(1, 2017, 5, "book1name", "book1 description", true, "book1Author"),
				new BookEntity(5, 1900, 5, "book4name", "book4 description", true, "book4Author"),
				new BookEntity(123, 1886, 5, "book34name", "book34 description", true, "book34Author"),
				new BookEntity(6, 998, 5, "book3name", "book3 description", true, "book3Author"),
				new BookEntity(2, 2004, 5, "book7name", "book7 description", true, "book7Author"),
				new BookEntity(345, 2010, 5, "book8name", "book8 description", true, "book8Author"),
				new BookEntity(7, 1998, 5, "book41name", "book41 description", true, "book41Author"),
				new BookEntity(4, 1817, 5, "book42name", "book42 description", true, "book42Author")));
		bookEntityMarshList.setBooks(books);
		return bookEntityMarshList;
	}
}
