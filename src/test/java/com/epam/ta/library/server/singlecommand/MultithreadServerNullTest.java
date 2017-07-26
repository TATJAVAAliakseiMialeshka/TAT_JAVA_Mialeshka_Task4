package com.epam.ta.library.server.singlecommand;


import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.epam.ta.library.server.MultithreadServer;

public class MultithreadServerNullTest {

	private MultithreadServer server;
	
	private static final String DB_DEFAULT = "../../db/library.sql";
	
	private static final String rootPath = MultithreadServerTestActivateUser.class.getProtectionDomain().getCodeSource().getLocation().getPath();

	
	@BeforeTest ()
	public void testSetUp(){
		server = MultithreadServer.getInstance();
		MultithreadServerTestUtil.loadDB(rootPath+DB_DEFAULT);
	}
	
	@Test (groups="general", threadPoolSize = 5, invocationCount = 10, timeOut = 100000) 
	public void nullParameters(){
		String [] userCommands = null;
		String responce = server.executeUserCommands(userCommands).get(0);
		Assert.assertEquals(responce, "Requested operation failed due to wrong arguments format.");
	}
	
	@Test (groups="general", threadPoolSize = 5, invocationCount = 10, timeOut = 100000) 
	public void emptyParameters(){
		String [] userCommands = {""};
		String responce = server.executeUserCommands(userCommands).get(0);
		Assert.assertEquals(responce, "Requested operation failed due to wrong arguments format.");
	}

}
