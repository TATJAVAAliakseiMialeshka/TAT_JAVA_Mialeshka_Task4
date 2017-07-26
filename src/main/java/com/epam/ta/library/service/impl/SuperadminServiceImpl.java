package com.epam.ta.library.service.impl;

import com.epam.ta.library.bean.Role.Authority;
import com.epam.ta.library.dao.AdminDao;
import com.epam.ta.library.dao.exception.DaoException;
import com.epam.ta.library.dao.factory.DBType;
import com.epam.ta.library.dao.factory.DaoFactory;
import com.epam.ta.library.service.SuperadminService;
import com.epam.ta.library.service.exception.ServiceException;
import com.epam.ta.library.service.util.ServiceUtil;

public final class SuperadminServiceImpl implements SuperadminService {

	private static final String NULL_PARAMETER = "Received null parameter";
	
	@Override
	public boolean deleteAdminRole(Integer userId) throws ServiceException {
		if (!ServiceUtil.notNullCheck(userId)) {
			throw new ServiceException(NULL_PARAMETER);
		}
		try{
			DaoFactory factory = DaoFactory.getDaoFactory(DBType.MYSQL);
		if (null != factory) {
			AdminDao adminDao = factory.getAdminDao();
			if (adminDao.deleteUserRole(userId, Authority.ROLE_ADMIN.toString())) {
				return true;
			}
		}
		return false;
	} catch (DaoException e) {
		throw new ServiceException(e);
	}
	}

}
