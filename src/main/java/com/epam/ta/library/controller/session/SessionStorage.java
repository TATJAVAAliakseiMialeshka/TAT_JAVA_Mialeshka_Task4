package com.epam.ta.library.controller.session;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.epam.ta.library.bean.Role;
import com.epam.ta.library.bean.User;
import com.epam.ta.library.bean.Role.Authority;
import com.epam.ta.library.controller.command.CommandName;

public class SessionStorage {

	private SessionStorage() {
	}

	private static SessionStorage instance;

	private volatile Map<Integer, User> sessionUserMap = new ConcurrentHashMap<>();

	public synchronized static SessionStorage getInstance() {
		if (null == instance) {
			instance = new SessionStorage();
		}
		return instance;
	}

	public User getSessionUserMap(int threadHash) {
		return sessionUserMap.get(threadHash);
	}

	public void setSessionUserMap(User user) {
		int threadHash = Thread.currentThread().hashCode();
		this.sessionUserMap.put(threadHash, user);
	}

	public boolean emptyCurrentSessionStorage() {
		boolean isSessionClosed = false;
		int threadHash = Thread.currentThread().hashCode();
		if (null != this.sessionUserMap.remove(threadHash)) {
			isSessionClosed = true;
		}
		return isSessionClosed;
	}

	public boolean checkPermission(CommandName commandName) {

		Authority authority = commandName.getAuthority();
		if (null != authority) {
			int threadHash = Thread.currentThread().hashCode();
			User sessionUser = getSessionUserMap(threadHash);

			if (null != sessionUser) {
				List<Role> roles = sessionUser.getRoleList();
				for (Role role : roles) {
					if (role.getAuthority().equals(authority)) {
						return true;
					}
				}
			}
			return false;
		}
		return true;
	}

}
