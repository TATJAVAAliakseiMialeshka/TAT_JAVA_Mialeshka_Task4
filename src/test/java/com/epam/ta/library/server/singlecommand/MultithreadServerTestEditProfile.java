package com.epam.ta.library.server.singlecommand;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.ta.library.controller.command.CommandName;
import com.epam.ta.library.server.MultithreadServer;

public class MultithreadServerTestEditProfile {

	
	private MultithreadServer server;
	
	private static final String DB_ACTIVATED_USERS = "../../db/library.sql";
	
	private static final String ACCESS_DENIED = "You has no permission for this operation.";

	private static final String USER_LOGIN = "user1";
	private static final String USER_PASS = "user1";
	private static final String SUCCESS_LOGIN = "Welcome";
	
	private static final String rootPath = MultithreadServerTestSignIn.class.getProtectionDomain().getCodeSource().getLocation().getPath();
		
	@BeforeClass
	public void testSetUp(){
		server = MultithreadServer.getInstance();
		MultithreadServerTestUtil.loadDB(rootPath+DB_ACTIVATED_USERS);
	}
	
	
	@DataProvider
	public Object[][] command_editProfile_validLoginPassword_DP() {

	return new Object[][] {
		
		{"User profile successfully updated.", CommandName.AUTHORIZATION.name() + "='"+USER_LOGIN+ "' '"+USER_PASS+"'", CommandName.EDIT_PROFILE.name() + "='8' 'user5' 'user5' 'user5'"},
	};
	}
	
	@DataProvider
	public Object[][] command_editProfile_validLoginPlusInvalidSymbols_DP() {
		
	return new Object[][] {
		{ "User profile update operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+USER_LOGIN+ "' '"+USER_PASS+"'", CommandName.EDIT_PROFILE.name() + "='8' ' user5 ' 'user5' 'user5'"},
		{ "User profile update operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+USER_LOGIN+ "' '"+USER_PASS+"'", CommandName.EDIT_PROFILE.name() + "='8' 'user5!@#$%^&*()' 'user5' 'user5'"},
	};
	}
	
	
	@DataProvider
	public Object[][] command_editProfile_invalidLogin_DP() {

	return new Object[][] {
		{ "User profile update operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+USER_LOGIN+ "' '"+USER_PASS+"'", CommandName.EDIT_PROFILE.name() + "='8' ' smth ' 'user5'"},
		{ "User profile update operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+USER_LOGIN+ "' '"+USER_PASS+"'", CommandName.EDIT_PROFILE.name() + "='8' '!@#$%^&*()smth' 'user5'"},
		{ "User profile update operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+USER_LOGIN+ "' '"+USER_PASS+"'", CommandName.EDIT_PROFILE.name() + "='8' '' 'user5' 'user5'"},
		{ "User profile update operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+USER_LOGIN+ "' '"+USER_PASS+"'", CommandName.EDIT_PROFILE.name() + "='8' '' 'user5' 'user5'"},
	};
	}
	
	@DataProvider
	public Object[][] command_editProfile_validPasswordPlusInvalidSymbols_DP() {
		
	return new Object[][] {
		{ "User profile update operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+USER_LOGIN+ "' '"+USER_PASS+"'", CommandName.EDIT_PROFILE.name() + "='8' 'user5' 'user5' 'user5 ~'"},
	};
	}
	
	@DataProvider
	public Object[][] command_editProfile_invalidPassword_DP() {
		
	return new Object[][] {
		{ "User profile update operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+USER_LOGIN+ "' '"+USER_PASS+"'", CommandName.EDIT_PROFILE.name() + "='8' 'user5'  ' ~' 'user5'"},
		{ "User profile update operation failed due to wrong arguments format.", CommandName.AUTHORIZATION.name() + "='"+USER_LOGIN+ "' '"+USER_PASS+"'", CommandName.EDIT_PROFILE.name() + "='8' 'user5' '' 'user5'"},
	};
	}
	
	
	@Test (dataProvider="command_editProfile_validLoginPassword_DP", groups={"user","editProfile"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000) 
	public void command_editProfile_validLoginPassword(String userCommandsExpectedResult, String authCommand, String editProfileCommand) {
		List<String> responceList = server.executeUserCommands(new String [] {authCommand, editProfileCommand});
		Assert.assertTrue(responceList.get(0).startsWith(SUCCESS_LOGIN));
		Assert.assertEquals(responceList.get(1), userCommandsExpectedResult);
	}
	
	@Test (dataProvider="command_editProfile_validLoginPlusInvalidSymbols_DP", groups={"user","editProfile"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000) 
	public void command_editProfile_validLoginPlusInvalidSymbols(String userCommandsExpectedResult, String authCommand, String editProfileCommand) {
		List<String> responceList = server.executeUserCommands(new String [] {authCommand, editProfileCommand});
		Assert.assertTrue(responceList.get(0).startsWith(SUCCESS_LOGIN));
		Assert.assertEquals(responceList.get(1), userCommandsExpectedResult);
	}
	
	@Test (dataProvider="command_editProfile_invalidLogin_DP", groups={"user","editProfile"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000) 
	public void command_editProfile_invalidLogin(String userCommandsExpectedResult, String authCommand, String editProfileCommand) {
		List<String> responceList = server.executeUserCommands(new String [] {authCommand, editProfileCommand});
		Assert.assertTrue(responceList.get(0).startsWith(SUCCESS_LOGIN));
		Assert.assertEquals(responceList.get(1), userCommandsExpectedResult);	}
	
	@Test (dataProvider="command_editProfile_validPasswordPlusInvalidSymbols_DP", groups={"user","editProfile"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000) 
	public void command_editProfile_validPasswordPlusInvalidSymbols(String userCommandsExpectedResult, String authCommand, String editProfileCommand) {
		List<String> responceList = server.executeUserCommands(new String [] {authCommand, editProfileCommand});
		Assert.assertTrue(responceList.get(0).startsWith(SUCCESS_LOGIN));
		Assert.assertEquals(responceList.get(1), userCommandsExpectedResult);	}
	
	@Test (dataProvider="command_editProfile_invalidPassword_DP", groups={"user","editProfile"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000) 
	public void command_editProfile_invalidPassword(String userCommandsExpectedResult, String authCommand, String editProfileCommand) {
		List<String> responceList = server.executeUserCommands(new String [] {authCommand, editProfileCommand});
		Assert.assertTrue(responceList.get(0).startsWith(SUCCESS_LOGIN));
		Assert.assertEquals(responceList.get(1), userCommandsExpectedResult);	}
	
	@Test (groups={"user","editProfile"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000) 
	public void command_editProfile_withoutAuthorization() {
		String [] userCommands = {CommandName.EDIT_PROFILE.name() + "='"+USER_LOGIN+ "' '"+USER_PASS+"'"};
		String responce = server.executeUserCommands(userCommands).get(0);

		Assert.assertEquals(responce, ACCESS_DENIED);	}
}
