package com.epam.ta.library.server.singlecommand;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.ta.library.controller.command.CommandName;
import com.epam.ta.library.server.MultithreadServer;

public class MultithreadServerTestBanUser {

	private MultithreadServer server;
	private static final String EXISTING_USER_ID = "6";
	private static final String SUCCESS_LOGIN = "Welcome";
	private static final String ADMIN_LOGIN = "admin1";
	private static final String ADMIN_PASS = "admin1";
	private static final String ACCESS_DENIED = "You has no permission for this operation.";
	private static final String USER_LOGIN = "user1";
	private static final String USER_PASS = "user1";
	
	private static final String DB_DEFAULT = "../../db/library.sql";
	private static final String rootPath = MultithreadServerTestActivateUser.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	
	
	@BeforeClass
	public void testSetUp(){
		server = MultithreadServer.getInstance();
		MultithreadServerTestUtil.loadDB(rootPath + DB_DEFAULT);
	}
	
	
	@DataProvider
	public Object[][] command_banUser_idvalidData_DP() {

		return new Object[][] { 
				{ "User ban was not succeed. Probably there is no such user.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.BAN_USER.name() + "='100'" },
				{ "User ban was not succeed. Probably there is no such user.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.BAN_USER.name() + "='-1'" },
				{ "User ban was not succeed. Probably there is no such user.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.BAN_USER.name() + "='0'" },
				{ "User ban operation failed  due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.BAN_USER.name() + "='string'" },
				};
	}
	
	
	@Test (groups={"admin","banUser"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000) 
	public void command_banUser_validData(){
		
		
		String [] userCommands = {CommandName.AUTHORIZATION.name() + "='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'", CommandName.BAN_USER.name() +"='"+EXISTING_USER_ID+"'"};

		List<String> responceList = server.executeUserCommands(userCommands);
		Assert.assertTrue(responceList.get(0).startsWith(SUCCESS_LOGIN));
		Assert.assertEquals(responceList.get(1), "User successfully banned.");
	}
	
	@Test (dataProvider="command_banUser_idvalidData_DP", groups={"admin","banUser"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000) 
	public void command_banUser_idvalidData(String userCommandsExpectedResult, String authCommand, String banUserCommand) {
		
		List<String> responceList = server.executeUserCommands(new String [] {authCommand, banUserCommand});
		Assert.assertTrue(responceList.get(0).startsWith(SUCCESS_LOGIN));
		Assert.assertEquals(responceList.get(1), userCommandsExpectedResult);
	}
	
	@Test (groups={"admin","banUser"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000) 
	public void command_banUser_withoutAdminRights(){
		
		String [] userCommands = {CommandName.AUTHORIZATION.name() + "='"+USER_LOGIN+ "' '"+USER_PASS+"'", CommandName.BAN_USER.name() +"='"+EXISTING_USER_ID+"'"};

		List<String> responceList = server.executeUserCommands(userCommands);
		Assert.assertTrue(responceList.get(0).startsWith(SUCCESS_LOGIN));
		Assert.assertEquals(responceList.get(1), ACCESS_DENIED);
	}
	
	@Test (groups={"admin","banUser"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000) 
	public void command_banUser_withoutAuthorization(){
		
		String [] userCommands = {CommandName.BAN_USER.name() +"='"+EXISTING_USER_ID+"'"};

		List<String> responceList = server.executeUserCommands(userCommands);
		Assert.assertEquals(responceList.get(0), ACCESS_DENIED);

	}
}
