package com.epam.ta.library.server.singlecommand;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.ta.library.controller.command.CommandName;
import com.epam.ta.library.server.MultithreadServer;

public class MultithreadServerTestActivateUser {

	private MultithreadServer server;
	private static final String EXISTING_USER_ID = "5";

	private static final String ACCESS_DENIED = "You has no permission for this operation.";
	
	private static final String SUCCESS_LOGIN = "Welcome";
	private static final String DB_DEFAULT = "../../db/library.sql";
	private static final String rootPath = MultithreadServerTestActivateUser.class.getProtectionDomain().getCodeSource()
			.getLocation().getPath();

	@DataProvider
	public Object[][] command_activateUser_invalidId_DP() {

		return new Object[][] { 
				{ "User activation was not succeed. Probably there is no such user.", CommandName.AUTHORIZATION.name() + "='admin1' 'admin1'", CommandName.ACTIVATE_USER.name() + "='100'"},
				{ "User activation was not succeed. Probably there is no such user.", CommandName.AUTHORIZATION.name() + "='admin1' 'admin1'", CommandName.ACTIVATE_USER.name() + "='-100'"},
				{ "User activation was not succeed. Probably there is no such user.", CommandName.AUTHORIZATION.name() + "='admin1' 'admin1'", CommandName.ACTIVATE_USER.name() + "='0'"},
				{ "User activation operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='admin1' 'admin1'", CommandName.ACTIVATE_USER.name() + "='string'" },
				};
	}

	@BeforeClass
	public void testSetUp() {
		server = MultithreadServer.getInstance();
		MultithreadServerTestUtil.loadDB(rootPath + DB_DEFAULT);
	}
	
	@Test(groups = { "admin", "activateUser" } ) 
	public void command_activateUser_existingId() {

		String [] userCommands = {CommandName.AUTHORIZATION.name() + "='admin1' 'admin1'", CommandName.ACTIVATE_USER.name() +"='"+EXISTING_USER_ID+"'"};
		List<String> responceList = server.executeUserCommands(userCommands);
		
		Assert.assertTrue(responceList.get(0).startsWith(SUCCESS_LOGIN));
		Assert.assertEquals(responceList.get(1), "User successfully activated.");
	}

	@Test(dataProvider = "command_activateUser_invalidId_DP", groups = { "admin", "activateUser" }, threadPoolSize = 5, invocationCount = 10, timeOut = 100000) 
	public void command_activateUser_invalidId(String userCommandsExpectedResult, String authCommand, String activateUserCommand) {
		
		List<String> responceList = server.executeUserCommands(new String [] {authCommand, activateUserCommand});
		Assert.assertTrue(responceList.get(0).startsWith(SUCCESS_LOGIN));
		Assert.assertEquals(responceList.get(1), userCommandsExpectedResult);
	}

	@Test(groups = { "admin", "activateUser" }, threadPoolSize = 5, invocationCount = 10, timeOut = 100000) 
	public void command_activateUser_withoutAdminRights() {
	
		String [] userCommands = {CommandName.AUTHORIZATION.name() + "='user1' 'user1'", CommandName.ACTIVATE_USER.name() +"='"+EXISTING_USER_ID+"'"};
		
		List<String> responceList = server.executeUserCommands(userCommands);
		Assert.assertTrue(responceList.get(0).startsWith(SUCCESS_LOGIN));
		Assert.assertEquals(responceList.get(1), ACCESS_DENIED);
	}

	@Test(groups = { "admin", "activateUser" }, threadPoolSize = 5, invocationCount = 10, timeOut = 100000) 
	public void command_activateUser_withoutAuthorization() {

		String [] userCommands = {CommandName.ACTIVATE_USER.name() +"='"+EXISTING_USER_ID+"'"};
		
		String responce = server.executeUserCommands(userCommands).get(0);
		Assert.assertEquals(responce, ACCESS_DENIED);
	}
}
