package com.epam.ta.library.controller.command.impl.util;

import java.util.List;

import com.epam.ta.library.bean.Book;
import com.epam.ta.library.service.util.ServiceUtil;

public class CommandBookUtil {

		public static Book buildBook(String bName, Integer bYear, String bDescr, Integer bQuantity) {
		Book book = null;
		if (ServiceUtil.notNullCheck(bYear, bQuantity) && ServiceUtil.notNullCheck(bName, bDescr)) {
			book = new Book();
			book.setName(bName);
			book.setYear(bYear);
			book.setDescription(bDescr);
			book.setQuantity(bQuantity);
		}
		return book;

	}
	
	public static Book buildBook(Integer bId, String bName, Integer bYear, String bDescr, Integer bQuantity) {
		Book book = null;
		if (ServiceUtil.notNullCheck(bId, bYear, bQuantity) && ServiceUtil.notNullCheck(bName, bDescr)) {
			book = new Book();
			book.setId(bId);
			book.setName(bName);
			book.setYear(bYear);
			book.setDescription(bDescr);
			book.setQuantity(bQuantity);
		}
		return book;

	}
	
	public static void printBookList(List<Book> books) {
		for (Book book : books) {
			System.out.println("======Library books:=======");
			System.out.print("title: " + book.getName() + "; year: " + book.getYear());
			if (null != book.getAuthorList()) {
				System.out.print("; authors: ");
				for (String author : book.getAuthorList()) {
					System.out.print(author + ", ");
				}
			}
			System.out.println("; is available: " + book.getIsAvailable());
		}
	}
	
}
