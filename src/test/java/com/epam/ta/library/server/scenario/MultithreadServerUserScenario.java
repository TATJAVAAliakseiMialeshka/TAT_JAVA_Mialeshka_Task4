package com.epam.ta.library.server.scenario;

import java.util.Arrays;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.ta.library.controller.command.CommandName;
import com.epam.ta.library.server.MultithreadServer;
import com.epam.ta.library.server.singlecommand.MultithreadServerTestActivateUser;
import com.epam.ta.library.server.singlecommand.MultithreadServerTestUtil;

/**
 * MultithreadServerScenario class 
 * Test scenario: 
 * 1. login as user 
 * 2. see library books 
 * 3. order book 
 * 4. refuse order 
 * 5. sign out
 */

public class MultithreadServerUserScenario {

	private MultithreadServer server;

	private static final String DB_DEFAULT = "../../db/library.sql";

	private static final String rootPath = MultithreadServerTestActivateUser.class.getProtectionDomain().getCodeSource()
			.getLocation().getPath();

	private final List<String> userCommandsExpRes = Arrays.asList(
			"Welcome",
			"Library books list successfully loaded.",
			"Order for book successfully placed. Please, wait for your order approval.",
			"Order for book successfully canceled.",
			"Sign out success.");
	
	@DataProvider
	public Object[][] scenario_user1_DP() {

		List<String> userCommandsList = Arrays.asList(
				CommandName.AUTHORIZATION.name() + "='user1' 'user1'",
				CommandName.SEE_ALL_BOOKS.name(),
				CommandName.ORDER_BOOK.name() + "='4' '1'",
				CommandName.REFUSE_BOOK.name() + "='4' '1'",
				CommandName.SIGN_OUT.name()
				);
		return new Object[][] { { userCommandsList, userCommandsExpRes }, };
	}
	
	@DataProvider
	public Object[][] scenario_user2_DP() {

		List<String> userCommandsList = Arrays.asList(
				CommandName.AUTHORIZATION.name() + "='user2' 'user2'",
				CommandName.SEE_ALL_BOOKS.name(),
				CommandName.ORDER_BOOK.name() + "='5' '1'",
				CommandName.REFUSE_BOOK.name() + "='5' '1'",
				CommandName.SIGN_OUT.name()
				);
		return new Object[][] { { userCommandsList, userCommandsExpRes }, };
	}
	
	@DataProvider
	public Object[][] scenario_user3_DP() {

		List<String> userCommandsList = Arrays.asList(
				CommandName.AUTHORIZATION.name() + "='user3' 'user3'",
				CommandName.SEE_ALL_BOOKS.name(),
				CommandName.ORDER_BOOK.name() + "='6' '1'",
				CommandName.REFUSE_BOOK.name() + "='6' '1'",
				CommandName.SIGN_OUT.name()
				);
		return new Object[][] { { userCommandsList, userCommandsExpRes }, };
	}

	@BeforeTest()
	public void testSetUp() {
		server = MultithreadServer.getInstance();
		MultithreadServerTestUtil.loadDB(rootPath + DB_DEFAULT);
	}

	@Test(dataProvider = "scenario_user1_DP", groups = "positive_scenario") 
	public void scenario_user1(List<String> userCommandsList, List<String> userCommandsExpRes) {
		String[] userCommands = new String[userCommandsList.size()];
		userCommands = userCommandsList.toArray(userCommands);
		List<String> responceList = server.executeUserCommands(userCommands);
		Assert.assertTrue(responceList.get(0).startsWith(userCommandsExpRes.get(0)));
		for (int i = 1; i < responceList.size(); i++) {
			Assert.assertEquals(responceList.get(i), userCommandsExpRes.get(i));
		}
	}
	
	@Test(dataProvider = "scenario_user2_DP", groups = "positive_scenario") 
	public void scenario_user2(List<String> userCommandsList, List<String> userCommandsExpRes) {
		String[] userCommands = new String[userCommandsList.size()];
		userCommands = userCommandsList.toArray(userCommands);
		List<String> responceList = server.executeUserCommands(userCommands);
		Assert.assertTrue(responceList.get(0).startsWith(userCommandsExpRes.get(0)));
		for (int i = 1; i < responceList.size(); i++) {
			Assert.assertEquals(responceList.get(i), userCommandsExpRes.get(i));
		}
	}
	
	@Test(dataProvider = "scenario_user3_DP", groups = "positive_scenario") 
	public void scenario_user3(List<String> userCommandsList, List<String> userCommandsExpRes) {
		String[] userCommands = new String[userCommandsList.size()];
		userCommands = userCommandsList.toArray(userCommands);
		List<String> responceList = server.executeUserCommands(userCommands);
		Assert.assertTrue(responceList.get(0).startsWith(userCommandsExpRes.get(0)));
		for (int i = 1; i < responceList.size(); i++) {
			Assert.assertEquals(responceList.get(i), userCommandsExpRes.get(i));
		}
	}

}
