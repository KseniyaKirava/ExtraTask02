package by.htp.kirova.task2.java.dao.daoentity;


import by.htp.kirova.task2.java.connectionpool.ConnectionPool;
import by.htp.kirova.task2.java.connectionpool.ConnectionPoolException;
import by.htp.kirova.task2.java.dao.DAOException;
import by.htp.kirova.task2.java.dao.GenericDAO;
import by.htp.kirova.task2.java.entity.Authority;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Provides Facilities with an opportunity to retrieve, change and delete data from
 * the relevant database table.
 *
 * @author Kseniya Kirava
 * @since Sept 24, 2018
 */
public class AuthorityDAOImpl implements GenericDAO<Authority> {

    /**
     * Instance of {@code org.apache.log4j.Logger} is used for logging.
     */
    private static final Logger LOGGER = Logger.getLogger(AuthorityDAOImpl.class);

    /**
     * Constant string which represents query to create authority.
     */
    private static final String SQL_CREATE_AUTHORITY = "INSERT INTO authorities(authority, username, enable) " +
            "VALUES (?, ?, ?)";

    /**
     * Constant string which represents query to select authorities.
     */
    private static final String SQL_SELECT_FROM_AUTHORITIES = "SELECT * FROM authorities ";

    /**
     * Constant string which represents query to update authority.
     */
    private static final String SQL_UPDATE_AUTHORITY = "UPDATE authorities SET authority= ? , enable= ? " +
            "WHERE username= ?";

    /**
     * Constant string which represents query to delete authority.
     */
    private static final String SQL_DELETE_AUTHORITY = "DELETE FROM authorities WHERE username= ?";


    @Override
    public boolean create(Authority authority) throws DAOException {
        ConnectionPool cp = null;
        Connection connection = null;
        PreparedStatement ps = null;

        int result;

        try {
            cp = ConnectionPool.getInstance();
            connection = cp.extractConnection();

            ps = connection.prepareStatement(SQL_CREATE_AUTHORITY);
            ps.setString(1, authority.getAuthority());
            ps.setString(2, authority.getUsername());
            ps.setBoolean(3, authority.isEnable());

            result = ps.executeUpdate();

            connection.setAutoCommit(false);
            connection.commit();

        } catch (ConnectionPoolException | SQLException e) {
            if (cp != null) {
                cp.rollbackConnection(connection);
            }
            LOGGER.error("ConnectionPool error: ", e);
            throw new DAOException("ConnectionPool error: ", e);

        } finally {
            if (cp != null && connection != null && ps != null) {
                cp.setAutoCommitTrue(connection);
                cp.closePreparedStatement(ps);
                cp.returnConnection(connection);
            }
        }

        return result == 1;

    }

    @Override
    public List<Authority> read(String where) throws DAOException {
        ConnectionPool cp = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;

        String sql = SQL_SELECT_FROM_AUTHORITIES + where;

        List<Authority> list = new ArrayList<>();

        try {
            cp = ConnectionPool.getInstance();
            connection = cp.extractConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                list.add(new Authority(
                        resultSet.getString("authority"),
                        resultSet.getString("username"),
                        resultSet.getBoolean("enable")
                ));
            }

        } catch (ConnectionPoolException | SQLException e) {
            LOGGER.error("ConnectionPool error: ", e);
            throw new DAOException("ConnectionPool error: ", e);

        } finally {
            if (cp != null && connection != null) {
                cp.closeStatement(statement);
                cp.returnConnection(connection);
            }
        }

        return list;
    }

    @Override
    public boolean update(Authority authority) throws DAOException {
        ConnectionPool cp = null;
        Connection connection = null;
        PreparedStatement ps = null;

        int result;

        try {
            cp = ConnectionPool.getInstance();
            connection = cp.extractConnection();

            ps = connection.prepareStatement(SQL_UPDATE_AUTHORITY);
            ps.setString(1, authority.getAuthority());
            ps.setBoolean(2, authority.isEnable());
            ps.setString(3, authority.getUsername());

            result = ps.executeUpdate();

            connection.setAutoCommit(false);
            connection.commit();

        } catch (ConnectionPoolException | SQLException e) {
            if (cp != null) {
                cp.rollbackConnection(connection);
            }
            LOGGER.error("ConnectionPool error: ", e);
            throw new DAOException("ConnectionPool error: ", e);

        } finally {
            if (cp != null && connection != null && ps!= null) {
                cp.setAutoCommitTrue(connection);
                cp.closePreparedStatement(ps);
                cp.returnConnection(connection);
            }
        }

        return result == 1;
    }

    @Override
    public boolean delete(Authority authority) throws DAOException {
        ConnectionPool cp = null;
        Connection connection = null;
        PreparedStatement ps = null;

        int result;

        try {
            cp = ConnectionPool.getInstance();
            connection = cp.extractConnection();

            ps = connection.prepareStatement(SQL_DELETE_AUTHORITY);
            ps.setString(1, authority.getUsername());

            result = ps.executeUpdate();

        } catch (ConnectionPoolException | SQLException e) {
            LOGGER.error("ConnectionPool error: ", e);
            throw new DAOException("ConnectionPool error: ", e);

        } finally {
            if (cp != null && connection != null && ps!= null){
                cp.closePreparedStatement(ps);
                cp.returnConnection(connection);
            }
        }

        return result == 1;
    }

}

