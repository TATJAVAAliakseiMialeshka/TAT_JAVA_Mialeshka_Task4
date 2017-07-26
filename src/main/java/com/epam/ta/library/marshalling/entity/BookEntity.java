package com.epam.ta.library.marshalling.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Book", propOrder = { "name", "description", "isAvailable", "quantity", "year", "author" })
public class BookEntity {

	@XmlAttribute(required = true)
	private int id;
	@XmlElement(required = true)
	private int year;
	@XmlElement(required = true)
	private int quantity;
	@XmlElement(required = true)
	private String name;
	@XmlElement(required = true)
	private String description;
	@XmlElement(required = true)
	private boolean isAvailable;
	@XmlElement(required = true)
	private String author;

	
	public BookEntity() {
	}

	public BookEntity(int id, int year, int quantity, String name, String description, boolean isAvailable,
			String author) {
		this.id = id;
		this.year = year;
		this.quantity = quantity;
		this.name = name;
		this.description = description;
		this.isAvailable = isAvailable;
		this.author = author;
	}

	public int getId() {
		return id;
	}

	public int getYear() {
		return year;
	}

	public int getQuantity() {
		return quantity;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public boolean isAvailable() {
		return isAvailable;
	}

	public String getAuthor() {
		return author;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setAvailable(boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((author == null) ? 0 : author.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + (isAvailable ? 1231 : 1237);
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + quantity;
		result = prime * result + year;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BookEntity other = (BookEntity) obj;
		if (author == null) {
			if (other.author != null)
				return false;
		} else if (!author.equals(other.author))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (isAvailable != other.isAvailable)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (quantity != other.quantity)
			return false;
		if (year != other.year)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "BookEntity [id=" + id + ", year=" + year + ", quantity=" + quantity + ", name=" + name
				+ ", description=" + description + ", isAvailable=" + isAvailable + ", author=" + author + "]";
	}

}
