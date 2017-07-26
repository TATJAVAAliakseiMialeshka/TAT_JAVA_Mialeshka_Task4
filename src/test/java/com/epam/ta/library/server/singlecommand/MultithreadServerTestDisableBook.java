package com.epam.ta.library.server.singlecommand;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.ta.library.controller.command.CommandName;
import com.epam.ta.library.server.MultithreadServer;

public class MultithreadServerTestDisableBook {

	private MultithreadServer server;
	private static final String EXISTING_BOOK_ID = "22";
	
	private static final String SUCCESS_LOGIN = "Welcome";
	private static final String USER_LOGIN = "user1";
	private static final String USER_PASS = "user1";
	private static final String ADMIN_LOGIN = "admin1";
	private static final String ADMIN_PASS = "admin1";
	private static final String ACCESS_DENIED = "You has no permission for this operation.";
	
	private static final String rootPath = MultithreadServerTestAddBookDescription.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	private static final String DB_DEFAULT = "../../db/library.sql";

	@BeforeClass
	public void testSetUp(){
		server = MultithreadServer.getInstance();
		MultithreadServerTestUtil.loadDB(rootPath+DB_DEFAULT);
	}
	
	@DataProvider
	public Object[][] command_disableBook_idvalidData_DP() {
		
	return new Object[][] {
		{ "Book disable operation failed. Probably there is no such book.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.DISABLE_BOOK.name() + "='100'"},
		{ "Book disable operation failed. Probably there is no such book.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.DISABLE_BOOK.name() + "='-1'"},
		{ "Book disable operation failed. Probably there is no such book.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.DISABLE_BOOK.name() + "='0'"},
		{ "Book disable operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.DISABLE_BOOK.name() + "='string'"},
	};
	}
	
	
	@Test (groups={"admin","disableBook"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000) 
	public void command_disableBook_validData(){
		
		String [] userCommands = {CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.DISABLE_BOOK.name() + "='"+EXISTING_BOOK_ID+ "'"};
		List<String> responceList = server.executeUserCommands(userCommands);
		Assert.assertTrue(responceList.get(0).startsWith(SUCCESS_LOGIN));
		Assert.assertEquals(responceList.get(1), "Book successfully disabled for ordering.");	
	}
	
	@Test (dataProvider="command_disableBook_idvalidData_DP", groups={"admin","disableBook"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000)
	public void command_disableBook_idvalidData(String userCommandsExpectedResult, String authCommand, String editBookDescrCommand) {
		List<String> responceList = server.executeUserCommands(new String [] {authCommand, editBookDescrCommand});
		Assert.assertTrue(responceList.get(0).startsWith(SUCCESS_LOGIN));
		Assert.assertEquals(responceList.get(1), userCommandsExpectedResult);	
	}
	
	@Test (groups={"admin","disableBook"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000)
	public void command_disableBook_withoutAdminRights(){
		
		String [] userCommands = {CommandName.AUTHORIZATION.name() + "='"+USER_LOGIN+ "' '"+USER_PASS+"'", CommandName.DISABLE_BOOK.name() + "='"+EXISTING_BOOK_ID+ "'"};
		List<String> responceList = server.executeUserCommands(userCommands);
		Assert.assertTrue(responceList.get(0).startsWith(SUCCESS_LOGIN));
		Assert.assertEquals(responceList.get(1), ACCESS_DENIED);	
		
	}
	
	@Test (groups={"admin","disableBook"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000)
	public void command_disableBook_withoutAuthorization(){
		
		String [] userCommands = {CommandName.DISABLE_BOOK.name() + "='"+EXISTING_BOOK_ID+ "'"};
		List<String> responceList = server.executeUserCommands(userCommands);
		Assert.assertEquals(responceList.get(0), ACCESS_DENIED);		
	}
	
	
}
