package com.epam.ta.library.marshalling.entity;


import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "books")

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "booklist", propOrder = { "books"})

public class BookEntities {

	public BookEntities(){}
	
	@XmlElement(name="book", required = true)
	private List<BookEntity> books;

	
	public List<BookEntity> getBooks() {
		return books;
	}

	public void setBooks(List<BookEntity> books) {
		this.books = books;
	}
	
	
}
