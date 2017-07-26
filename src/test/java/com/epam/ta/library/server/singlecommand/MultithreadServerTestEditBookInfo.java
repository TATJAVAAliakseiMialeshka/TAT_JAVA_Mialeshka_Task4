package com.epam.ta.library.server.singlecommand;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.ta.library.controller.command.CommandName;
import com.epam.ta.library.server.MultithreadServer;

public class MultithreadServerTestEditBookInfo {

	
	private MultithreadServer server;
	
	private static final String rootPath = MultithreadServerTestAddBookDescription.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private static final String DB_DEFAULT = "../../db/library_add_test_books.sql";

	private static final String SUCCESS_LOGIN = "Welcome";
	private static final String USER_LOGIN = "user1";
	private static final String USER_PASS = "user1";
	private static final String ADMIN_LOGIN = "admin1";
	private static final String ADMIN_PASS = "admin1";
	private static final String ACCESS_DENIED = "You has no permission for this operation.";
	
	@BeforeClass
	public void testSetUp(){
		server = MultithreadServer.getInstance();
		MultithreadServerTestUtil.loadDB(rootPath+DB_DEFAULT);
	}
	
	
	@DataProvider
	public Object[][] command_editBookInfo_validData_DP() {
		
	return new Object[][] {
		{"Book description successfully updated.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.EDIT_BOOK_INFO.name() + "='113' 'b_name' '9999' 'b_descr' '100'"},
		{"Book description successfully updated.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.EDIT_BOOK_INFO.name() + "='114' 'b_name' '9999' 'b_descr' '0'"},
	};

	}
	
	@DataProvider
	public Object[][] command_editBookInfo_invalidData_DP() {
	
	return new Object[][] {
		{ "Book description update operation failed. Maybe there is no such book.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.EDIT_BOOK_INFO.name() + "='100' 'b_name' '9999' 'b_descr' '100'"},
		{ "Book description update operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.EDIT_BOOK_INFO.name() + "='-1' 'b_name' '9999' 'b_descr' '100'"},
		{ "Book description update operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.EDIT_BOOK_INFO.name() + "='0' 'b_name' '9999' 'b_descr' '100'"},
		{ "Book description update operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.EDIT_BOOK_INFO.name() + "='113' '' '9999' 'b_descr' '100'"},
		{ "Book description update operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.EDIT_BOOK_INFO.name() + "='113' 'b_name' '99' 'b_descr' '100'"},
		{ "Book description update operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.EDIT_BOOK_INFO.name() + "='113' 'b_name' '99999' 'b_descr' '100'"},
		{ "Book description update operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.EDIT_BOOK_INFO.name() + "='113' 'b_name' '0' 'b_descr' '100'"},
		{ "Book description update operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.EDIT_BOOK_INFO.name() + "='113' 'b_name' '-1' 'b_descr' '100'"},
		{ "Book description update operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.EDIT_BOOK_INFO.name() + "='113' 'b_name' 'text' 'b_descr' '100'"},
		{ "Book description update operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.EDIT_BOOK_INFO.name() + "='113' 'b_name' '9999' '!@#$%' '100'"},
		{ "Book description update operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.EDIT_BOOK_INFO.name() + "='113' 'b_name' '9999' '' '100'"},
		{ "Book description update operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.EDIT_BOOK_INFO.name() + "='113' 'b_name' '9999' 'b_descr' 'text'"},
		{ "Book description update operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.EDIT_BOOK_INFO.name() + "='113' 'b_name' '9999' 'b_descr' '-1'"},
	};
	}
	
	@Test (dataProvider="command_editBookInfo_validData_DP", groups={"admin","editBookInfo"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000) 
	public void command_editBookInfo_validData(String userCommandsExpectedResult, String authCommand, String editBookDescrCommand) {
		List<String> responceList = server.executeUserCommands(new String []{authCommand, editBookDescrCommand});
		Assert.assertTrue(responceList.get(0).startsWith(SUCCESS_LOGIN));
		Assert.assertEquals(responceList.get(1), userCommandsExpectedResult);
	}
	
	@Test (dataProvider="command_editBookInfo_invalidData_DP", groups={"admin","editBookInfo"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000) 
	public void command_editBookInfo_invalidData(String userCommandsExpectedResult, String authCommand, String editBookDescrCommand) {
		List<String> responceList = server.executeUserCommands(new String []{authCommand, editBookDescrCommand});
		Assert.assertTrue(responceList.get(0).startsWith(SUCCESS_LOGIN));
		Assert.assertEquals(responceList.get(1), userCommandsExpectedResult);
	}
	
	@Test (groups={"admin","editBookInfo"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000) 
	public void command_editBookInfo_withoutAdminRights(){
		String [] userCommands = {CommandName.AUTHORIZATION.name() + "='"+USER_LOGIN+ "' '"+USER_PASS+"'", CommandName.EDIT_BOOK_INFO.name() + "='113' 'b_name' '9999' 'b_descr' '100'"};
		String responce = server.executeUserCommands(userCommands).get(1);
		Assert.assertEquals(responce, ACCESS_DENIED );
	}
	
	@Test (groups={"admin","editBookInfo"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000)  
	public void command_editBookInfo_withoutAuthorization(){
		String [] userCommands = {CommandName.EDIT_BOOK_INFO.name() + "='113' 'b_name' '9999' 'b_descr' '100'"};
		String responce = server.executeUserCommands(userCommands).get(0);
		Assert.assertEquals(responce, ACCESS_DENIED );
	}
}
