package com.epam.ta.library.dao.connection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.epam.ta.library.dao.exception.DaoException;

/**
 * <code>ConnetionClosable</code> has a default db closing connection methods
 * connections
 */

public interface ConnectionClosable {

	String ERROR_CLOSING_CONNECTION = "Failed to close database connection.";
	String ERROR_CLOSING_STATEMENT = "Failed to close database connection statement.";
	String ERROR_CLOSING_RESULT_SET = "Failed to close database connection result set.";

	default void closeConnection(PreparedStatement stm, Connection conn) throws DaoException {

		closePreparedStatement(stm);
		closeConnection(conn);
	}

	default void closeConnection(ResultSet rs, PreparedStatement stm, Connection conn) throws DaoException {

		closeResultSet(rs);
		closePreparedStatement(stm);
		closeConnection(conn);
	}

	default void closeConnection(ResultSet rs, Statement stm, Connection conn) throws DaoException {
		closeResultSet(rs);
		closeStatement(stm);
		closeConnection(conn);
	}

	default void closeConnection(Connection conn) throws DaoException {
		if (conn != null) {
			try {
				conn.close();
			} catch (SQLException e) {
				throw new DaoException(ERROR_CLOSING_CONNECTION, e);
			}
		}
	}

	default void closeStatement(Statement stm) throws DaoException {
		if (stm != null) {
			try {
				stm.close();
			} catch (SQLException e) {
				throw new DaoException(ERROR_CLOSING_STATEMENT, e);
			}
		}
	}

	default void closePreparedStatement(PreparedStatement stm) throws DaoException {
		if (stm != null) {
			try {
				stm.close();
			} catch (SQLException e) {
				throw new DaoException(ERROR_CLOSING_STATEMENT, e);
			}
		}
	}

	default void closeResultSet(ResultSet rs) throws DaoException {
		if (rs != null) {
			try {
				rs.close();
			} catch (SQLException e) {
				throw new DaoException(ERROR_CLOSING_RESULT_SET, e);
			}
		}
	}
}
