package by.htp.kirova.task2.dao.daoentity;

import by.htp.kirova.task2.dao.BookingDAO;
import by.htp.kirova.task2.dao.connectionpool.ConnectionPoolException;
import by.htp.kirova.task2.dao.connectionpool.ConnectionPoolImpl;
import by.htp.kirova.task2.entity.Room;
import by.htp.kirova.task2.dao.DAOException;
import org.apache.log4j.Logger;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Provides Room with an opportunity to retrieve, change and delete data from
 * the relevant database table.
 *
 * @author Kseniya Kirava
 * @since Sept 24, 2018
 */
public class RoomDAOImpl implements BookingDAO<Room> {

    /**
     * Instance of {@code org.apache.log4j.Logger} is used for logging.
     */
    private static final Logger LOGGER = Logger.getLogger(RoomDAOImpl.class);

    /**
     * Constant string which represents query to create room.
     */
    private static final String SQL_CREATE_ROOM = "INSERT INTO rooms(name, number, capacity, cost, " +
            "enabled, room_classes_id) VALUES (?, ?, ?, ?, ?, ?)";

    /**
     * Constant string which represents query to select all rooms.
     */
    private static final String SQL_SELECT_FROM_ROOMS = "SELECT * FROM rooms ";

    /**
     * Constant string which represents query to update room.
     */
    private static final String SQL_UPDATE_ROOM = "UPDATE rooms SET name= ?,number= ?,capacity= ?," +
            "cost= %f, enabled=?, room_classes_id= ? WHERE id= ?";

    /**
     * Constant string which represents query to delete room.
     */
    private static final String SQL_DELETE_ROOM = "DELETE FROM rooms WHERE id = ?";

    @Override
    public boolean create(Room room) throws DAOException {
        ConnectionPoolImpl cp = null;
        Connection connection = null;
        PreparedStatement ps = null;


        try {
            cp = ConnectionPoolImpl.getInstance();
            connection = cp.getConnection();

            ps = connection.prepareStatement(SQL_CREATE_ROOM, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, room.getName());
            ps.setString(2, room.getNumber());
            ps.setInt(3, room.getCapacity());
            ps.setDouble(4, room.getCost());
            ps.setBoolean(5,  room.isEnabled());
            ps.setLong(6, room.getRoomClassesId());

            int result = ps.executeUpdate();
            int id = 0;

            if (result > 0) {
                ResultSet generatedKeys = ps.getGeneratedKeys();
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                }
            }

            if (id > 0) {
                room.setId(id);

                connection.setAutoCommit(false);
                connection.commit();
                return true;
            }


        } catch (ConnectionPoolException | SQLException e) {
            if (cp != null) {
                cp.rollbackConnection(connection);
            }
            LOGGER.error("ConnectionPoolImpl error: ", e);
            throw new DAOException("ConnectionPoolImpl error: ", e);

        } finally {
            if (cp != null && connection != null && ps != null) {
                cp.setAutoCommitTrue(connection);
                cp.closePreparedStatement(ps);
                cp.releaseConnection(connection);
            }
        }

        return false;
    }

    @Override
    public List<Room> read(String where) throws DAOException {
        ConnectionPoolImpl cp = null;
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet;

        String sql = SQL_SELECT_FROM_ROOMS + where;

        List<Room> list = new ArrayList<>();

        try {
            cp = ConnectionPoolImpl.getInstance();
            connection = cp.getConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
//            while (resultSet.next()) {
//                list.add(new Room(
//                        resultSet.getLong("id"),
//                        resultSet.getString("name"),
//                        resultSet.getString("number"),
//                        resultSet.getInt("capacity"),
//                        resultSet.getDouble("cost"),
//                        resultSet.getBoolean("enabled"),
//                        resultSet.getLong("room_classes_id")
//                        ));
//            }

        } catch (ConnectionPoolException | SQLException e) {
            LOGGER.error("ConnectionPoolImpl error: ", e);
            throw new DAOException("ConnectionPoolImpl error: ", e);

        } finally {
            if (cp != null && connection != null) {
                cp.closeStatement(statement);
                cp.releaseConnection(connection);
            }
        }

        return list;
    }

    @Override
    public boolean update(Room room) throws DAOException {
        ConnectionPoolImpl cp = null;
        Connection connection = null;
        PreparedStatement ps = null;

        int result;

        try {
            cp = ConnectionPoolImpl.getInstance();
            connection = cp.getConnection();

            ps = connection.prepareStatement(SQL_UPDATE_ROOM);
            ps.setString(1, room.getName());
            ps.setString(2, room.getNumber());
            ps.setInt(3, room.getCapacity());
            ps.setDouble(4, room.getCost());
            ps.setBoolean(5,  room.isEnabled());
            ps.setLong(6, room.getRoomClassesId());
            ps.setLong(7, room.getId());

            result = ps.executeUpdate();

            connection.setAutoCommit(false);
            connection.commit();

        } catch (ConnectionPoolException | SQLException e) {
            if (cp != null) {
                cp.rollbackConnection(connection);
            }
            LOGGER.error("ConnectionPoolImpl error: ", e);
            throw new DAOException("ConnectionPoolImpl error: ", e);

        } finally {
            if (cp != null && connection != null && ps!= null) {
                cp.setAutoCommitTrue(connection);
                cp.closePreparedStatement(ps);
                cp.releaseConnection(connection);
            }
        }

        return result == 1;
    }

    @Override
    public boolean delete(Room room) throws DAOException {
        ConnectionPoolImpl cp = null;
        Connection connection = null;
        PreparedStatement ps = null;

        int result;

        try {
            cp = ConnectionPoolImpl.getInstance();
            connection = cp.getConnection();

            ps = connection.prepareStatement(SQL_DELETE_ROOM);
            ps.setLong(1, room.getId());

            result = ps.executeUpdate();

        } catch (ConnectionPoolException | SQLException e) {
            LOGGER.error("ConnectionPoolImpl error: ", e);
            throw new DAOException("ConnectionPoolImpl error: ", e);

        } finally {
            if (cp != null && connection != null && ps!= null){
                cp.closePreparedStatement(ps);
                cp.releaseConnection(connection);
            }
        }

        return result == 1;
    }
}