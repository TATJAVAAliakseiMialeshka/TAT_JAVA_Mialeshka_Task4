package com.epam.ta.library.controller.command.impl;

import com.epam.ta.library.controller.command.Command;
import com.epam.ta.library.controller.session.SessionStorage;

public class SignOut implements Command {

	private static final String SIGNOUT_SUCCESS = "Sign out success.";
	private static final String SIGNOUT_FAILED = "Sign out failed.";

	@Override
	public String execute(String paramStr) {

		String responce = null;
		SessionStorage session = SessionStorage.getInstance();

		if (session.emptyCurrentSessionStorage()) {
			responce = SIGNOUT_SUCCESS;
		} else {
			responce = SIGNOUT_FAILED;
		}

		return responce;
	}
}