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
 * 1. login as admin
 * 2. add book description
 * 3. edit another book description
 * 4. disable another book
 * 5. sign out
 */

public class MultithreadServerAdminScenario2 {

	private MultithreadServer server;

	private static final String DB_DEFAULT = "../../db/library.sql";

	private static final String rootPath = MultithreadServerTestActivateUser.class.getProtectionDomain().getCodeSource()
			.getLocation().getPath();

	private final List<String> adminCommandsExpRes = Arrays.asList(
			"Welcome",
			"Book description successfully added.",
			"Book description successfully updated.",
			"Book successfully disabled for ordering.",
			"Sign out success.");
	
	@DataProvider
	public Object[][] scenario_admin1_connection1_DP() {

		List<String> adminCommandsList = Arrays.asList(
				CommandName.AUTHORIZATION.name() + "='admin1' 'admin1'",
				CommandName.ADD_BOOK_DESCRIPTION.name() +"='b_name' '9999' 'b_descr' '100'",
				CommandName.EDIT_BOOK_INFO.name() + "='22' 'b_name' '9999' 'b_descr' '0'",
				CommandName.DISABLE_BOOK.name() + "='1'",
				CommandName.SIGN_OUT.name()
				);
		return new Object[][] { { adminCommandsList, adminCommandsExpRes }, };
	}
	
	@DataProvider
	public Object[][] scenario_admin1_connection2_DP() {

		List<String> adminCommandsList = Arrays.asList(
				CommandName.AUTHORIZATION.name() + "='admin1' 'admin1'",
				CommandName.ADD_BOOK_DESCRIPTION.name() +"='b_name' '9999' 'b_descr' '100'",
				CommandName.EDIT_BOOK_INFO.name() + "='21' 'b_name' '9999' 'b_descr' '0'",
				CommandName.DISABLE_BOOK.name() + "='1'",
				CommandName.SIGN_OUT.name()
				);
		return new Object[][] { { adminCommandsList, adminCommandsExpRes }, };
	}

	@BeforeTest()
	public void testSetUp() {
		server = MultithreadServer.getInstance();
		MultithreadServerTestUtil.loadDB(rootPath + DB_DEFAULT);
	}

	@Test(dataProvider = "scenario_admin1_connection1_DP", groups = "positive_scenario") 
	public void scenario_user1(List<String> adminCommandsList, List<String> adminCommandsExpRes) {
		String[] adminCommands = new String[adminCommandsList.size()];
		adminCommands = adminCommandsList.toArray(adminCommands);
		List<String> responceList = server.executeUserCommands(adminCommands);
		Assert.assertTrue(responceList.get(0).startsWith(adminCommandsExpRes.get(0)));
		for (int i = 1; i < responceList.size(); i++) {
			Assert.assertEquals(responceList.get(i), adminCommandsExpRes.get(i));
		}
	}
	
	@Test(dataProvider = "scenario_admin1_connection2_DP", groups = "positive_scenario") 
	public void scenario_user2(List<String> adminCommandsList, List<String> adminCommandsExpRes) {
		String[] adminCommands = new String[adminCommandsList.size()];
		adminCommands = adminCommandsList.toArray(adminCommands);
		List<String> responceList = server.executeUserCommands(adminCommands);
		Assert.assertTrue(responceList.get(0).startsWith(adminCommandsExpRes.get(0)));
		for (int i = 1; i < responceList.size(); i++) {
			Assert.assertEquals(responceList.get(i), adminCommandsExpRes.get(i));
		}
	}

}

