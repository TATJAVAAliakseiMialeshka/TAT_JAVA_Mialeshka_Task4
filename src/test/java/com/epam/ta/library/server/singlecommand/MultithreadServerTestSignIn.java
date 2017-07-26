package com.epam.ta.library.server.singlecommand;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.ta.library.controller.command.CommandName;
import com.epam.ta.library.server.MultithreadServer;

public class MultithreadServerTestSignIn {

	private MultithreadServer server;

	private static final String DB_ACTIVATED_USERS = "../../db/library.sql";
	private static final String SUCCESS_LOGIN = "Welcome";
	
	private static final String USER_LOGIN = "user1";
	private static final String USER_PASS = "user1";
	private static final String ADMIN_LOGIN = "admin1";
	private static final String ADMIN_PASS = "admin1";
	private static final String SUPERADMIN_LOGIN = "superadmin";
	private static final String SUPERADMIN_PASS = "superadmin";
	
	private static final String rootPath = MultithreadServerTestSignIn.class.getProtectionDomain().getCodeSource().getLocation().getPath();
	
	
	@BeforeClass
	public void testSetUp(){
		server = MultithreadServer.getInstance();
		MultithreadServerTestUtil.loadDB(rootPath+DB_ACTIVATED_USERS);
	}
	
	@DataProvider
	public Object[][] command_SignIn_validLoginPassword_DP() {

		return new Object[][] {
				{ SUCCESS_LOGIN, CommandName.AUTHORIZATION.name() +"='"+USER_LOGIN+ "' '"+USER_PASS+"'" },
				{ SUCCESS_LOGIN, CommandName.AUTHORIZATION.name() +"='"+ADMIN_LOGIN+ "' '"+ADMIN_PASS+"'" },
				{ SUCCESS_LOGIN, CommandName.AUTHORIZATION.name() +"='"+SUPERADMIN_LOGIN+ "' '"+SUPERADMIN_PASS+"'" },
				};
	}
	
	@DataProvider
	public Object[][] command_SignIn_validLoginPlusInvalidSymbols_DP() {
		
		return new Object[][] {
				{ "Login operation failed. Wrong credentials", CommandName.AUTHORIZATION.name() +"='user1!@#$%^&*()' 'user1'"}
				};
	}
	
	@DataProvider
	public Object[][] command_SignIn_notExistingValidLogin_DP() {

		return new Object[][] {
				{ "Login operation failed. Wrong credentials", CommandName.AUTHORIZATION.name() +"='some_-0login' 'user1'"}
		};
	}
	
	@DataProvider
	public Object[][] command_SignIn_invalidLogin_DP() {

		return new Object[][] {
				{ "Login operation failed. Wrong credentials",  CommandName.AUTHORIZATION.name() + "=' smth ' 'user1'"},
				{ "Login operation failed. Wrong credentials",  CommandName.AUTHORIZATION.name() + "='!@#$%^&*()smth' 'user1'"},
				{ "Login operation failed. Wrong credentials",  CommandName.AUTHORIZATION.name() + "='' 'user1'"},
				{ "Login operation failed. Wrong credentials",  CommandName.AUTHORIZATION.name() + "='' 'tenth'"},
				};
	}
	
	@DataProvider
	public Object[][] command_SignIn_notExistingValidPassword_DP() {
		
	return new Object[][] {
		
		{ "Login operation failed. Wrong credentials", CommandName.AUTHORIZATION.name() + "='user1' 'aZ0_!$%&#'"},
	};
	}
	
	@DataProvider
	public Object[][] command_SignIn_validPasswordPlusInvalidSymbols_DP() {
		
	return new Object[][] {
		{ "Login operation failed. Wrong credentials", CommandName.AUTHORIZATION.name() + "='user1' 'user1 ~'"},
	};
	}
	
	@DataProvider
	public Object[][] command_SignIn_invalidPassword_DP() {
		
		return new Object[][] {
		{ "Login operation failed. Wrong credentials", CommandName.AUTHORIZATION.name() + "='user1' ' ~'"},
		{ "Login operation failed. Wrong credentials", CommandName.AUTHORIZATION.name() + "='user1' ''"},
	};
	}
	
	@Test (dataProvider="command_SignIn_validLoginPassword_DP", threadPoolSize = 5, invocationCount = 10, timeOut = 100000)
	public void command_SignIn_validLoginPassword(String userCommandsExpectedResult, String loginCommand) {
	String responce = server.executeUserCommands(new String [] {loginCommand}).get(0);
	Assert.assertTrue(responce.startsWith(userCommandsExpectedResult));
	}
	
	@Test (dataProvider="command_SignIn_validLoginPlusInvalidSymbols_DP", groups={"user","signIn"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000)
	public void command_SignIn_validLoginPlusInvalidSymbols(String userCommandsExpectedResult, String loginCommand) {
		List<String> list = server.executeUserCommands(new String [] {loginCommand});
		Assert.assertEquals(list.get(0),userCommandsExpectedResult);
	}
	
	@Test (dataProvider="command_SignIn_notExistingValidLogin_DP", groups={"user","signIn"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000)
	public void command_SignIn_notExistingValidLogin(String userCommandsExpectedResult, String loginCommand) {
		String responce = server.executeUserCommands(new String [] {loginCommand}).get(0);
		Assert.assertEquals(responce,userCommandsExpectedResult);
	}
	
	@Test (dataProvider="command_SignIn_invalidLogin_DP", groups={"user","signIn"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000)
	public void command_SignIn_invalidLogin(String userCommandsExpectedResult, String loginCommand) {
		String responce = server.executeUserCommands(new String [] {loginCommand}).get(0);
		Assert.assertEquals(responce, userCommandsExpectedResult);
	}
	
	@Test (dataProvider="command_SignIn_validPasswordPlusInvalidSymbols_DP", groups={"user","signIn"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000)
	public void command_SignIn_validPasswordPlusInvalidSymbols(String userCommandsExpectedResult, String loginCommand) {
		String responce = server.executeUserCommands(new String [] {loginCommand}).get(0);
		Assert.assertEquals(responce,userCommandsExpectedResult);
	}
	
	@Test (dataProvider="command_SignIn_invalidPassword_DP", groups={"user","signIn"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000)
	public void command_SignIn_invalidPassword(String userCommandsExpectedResult, String loginCommand) {
		String responce = server.executeUserCommands(new String [] {loginCommand}).get(0);
		Assert.assertEquals(responce,userCommandsExpectedResult);
	}
	
	@Test (groups={"user","signIn"}, threadPoolSize = 5, invocationCount = 10, timeOut = 100000)
	public void command_SignIn_nullParameters(){

		String [] userCommands = {null};

		List<String> responceList = server.executeUserCommands(userCommands);
		Assert.assertEquals(responceList.get(0), "Requested operation failed due to wrong arguments format.");
		}
	
}
