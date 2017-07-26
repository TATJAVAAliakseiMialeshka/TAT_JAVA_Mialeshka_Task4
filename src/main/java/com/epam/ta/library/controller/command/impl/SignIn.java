package com.epam.ta.library.controller.command.impl;

import org.apache.log4j.Logger;

import com.epam.ta.library.bean.User;
import com.epam.ta.library.controller.command.Command;
import com.epam.ta.library.controller.command.impl.util.CommUserParamValidator;
import com.epam.ta.library.controller.command.impl.util.CommandUtil;
import com.epam.ta.library.controller.session.SessionStorage;
import com.epam.ta.library.service.LoginService;
import com.epam.ta.library.service.exception.ServiceException;
import com.epam.ta.library.service.factory.ServiceFactory;

public final class SignIn implements Command {

	private static final String LOGIN_FAILED_WRONG_CREDENT = "Login operation failed. Wrong credentials";
	private static final String LOGIN_FAILED = "Error during login procedure.";
	
	private final static Logger log = Logger.getLogger(SignIn.class);
	
	@Override
	public String execute(String paramStr) {

		String responce = LOGIN_FAILED_WRONG_CREDENT;
		String[] paramArr = CommandUtil.splitParams(paramStr);
		ServiceFactory serviceFactory = ServiceFactory.getInstance();
		LoginService loginService = serviceFactory.getLoginService();
		SessionStorage session = null;
		
		try {
			if (null != paramArr && paramArr.length == 2) {
				String login = paramArr[0];
				String password = paramArr[1];
				if (CommUserParamValidator.validateLogin(login) && CommUserParamValidator.validatePassword(password)) {
					User user = loginService.loginUser(login, password);
					if (null != user) {
						session = SessionStorage.getInstance();
						session.setSessionUserMap(user);
						responce = "Welcome " + user.getName();
					} else {
						responce = LOGIN_FAILED_WRONG_CREDENT;
					}
				} 
			}
		} catch (ServiceException e) {
			log.error(e);
			responce = LOGIN_FAILED;
		}

		return responce;

	}

}
