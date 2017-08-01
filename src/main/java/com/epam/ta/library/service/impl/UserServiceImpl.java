package com.epam.ta.library.service.impl;

import java.util.List;

import com.epam.ta.library.bean.Book;
import com.epam.ta.library.bean.Subscription;
import com.epam.ta.library.bean.User;
import com.epam.ta.library.dao.BookDao;
import com.epam.ta.library.dao.LoginDao;
import com.epam.ta.library.dao.UserDao;
import com.epam.ta.library.dao.exception.DaoException;
import com.epam.ta.library.dao.factory.DBType;
import com.epam.ta.library.dao.factory.DaoFactory;
import com.epam.ta.library.service.UserService;
import com.epam.ta.library.service.exception.ServiceException;
import com.epam.ta.library.service.util.Encryptor;
import com.epam.ta.library.service.util.ServiceUtil;

public final class UserServiceImpl implements UserService {

	private static final String NULL_PARAMETER = "Received null parameter";

	@Override
	public boolean orderBook(Integer userId, Integer bookId) throws ServiceException {
		if (!ServiceUtil.notNullCheck(userId, bookId)) {
			throw new ServiceException(NULL_PARAMETER);
		}
		try {
			DaoFactory factory = DaoFactory.getDaoFactory(DBType.MYSQL);
			if (null != factory) {
				UserDao userDao = factory.getUserDao();
				if (userDao.orderBook(userId, bookId)) {
					return true;
				}
			}

		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return false;
	}

	@Override
	public boolean refuseOrderedBook(Integer userId, Integer bookId) throws ServiceException {
		if (!ServiceUtil.notNullCheck(userId, bookId)) {
			throw new ServiceException(NULL_PARAMETER);
		}
		try {
			DaoFactory factory = DaoFactory.getDaoFactory(DBType.MYSQL);
			if (null != factory) {
				UserDao userDao = factory.getUserDao();
				if (userDao.refuseOrderedBook(userId, bookId)) {
					return true;
				}
			}
			return false;
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
	}

	@Override
	public List<Subscription> viewHistoryCard(Integer userId) throws ServiceException {
		List<Subscription> sbsList = null;
		if (!ServiceUtil.notNullCheck(userId)) {
			throw new ServiceException(NULL_PARAMETER);
		}
		try {
			DaoFactory factory = DaoFactory.getDaoFactory(DBType.MYSQL);
			if (null != factory) {
				UserDao userDao = factory.getUserDao();
				sbsList = userDao.viewHistoryCard(userId);
			}
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return sbsList;
	}

	@Override
	public List<Book> seeAllBooks() throws ServiceException {
		List<Book> bookList = null;
		try {
			DaoFactory factory = DaoFactory.getDaoFactory(DBType.MYSQL);
			if (null != factory) {
				BookDao bookDao = factory.getBookDao();
				bookList = bookDao.getAllBooks();
			}
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return bookList;
	}

	@Override
	public User seeUserProfile(Integer userId) throws ServiceException {
		if (!ServiceUtil.notNullCheck(userId)) {
			throw new ServiceException(NULL_PARAMETER);
		}
		User user = null;
		try {
			DaoFactory factory = DaoFactory.getDaoFactory(DBType.MYSQL);
			if (null != factory) {
				LoginDao loginDao = factory.getLoginDao();
				loginDao.getUserById(userId);
			}
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return user;
	}

	@Override
	public boolean updateUserProfile(Integer userId, String name, String password, String oldPassword)
			throws ServiceException {
		if (!ServiceUtil.notNullCheck(userId) && !ServiceUtil.notNullCheck(name, password, oldPassword)) {
			throw new ServiceException(NULL_PARAMETER);
		}
		try {
			DaoFactory factory = DaoFactory.getDaoFactory(DBType.MYSQL);
			if (null != factory) {
				UserDao userDao = factory.getUserDao();
				password = Encryptor.encrypt(password);
				oldPassword = Encryptor.encrypt(oldPassword);
				if (userDao.updateUser(userId, name, password, oldPassword)) {
					return true;
				}
			}
		} catch (DaoException e) {
			throw new ServiceException(e);
		}
		return false;
	}
}
