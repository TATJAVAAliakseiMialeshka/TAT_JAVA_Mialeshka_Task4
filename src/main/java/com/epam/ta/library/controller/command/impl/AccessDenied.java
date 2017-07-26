package com.epam.ta.library.controller.command.impl;

import com.epam.ta.library.controller.command.Command;

public final class AccessDenied implements Command{

	private static final String ACCESS_DENIED = "You has no permission for this operation.";
	
	@Override
	public String execute(String paramStr) {
		return ACCESS_DENIED;
	}

}
