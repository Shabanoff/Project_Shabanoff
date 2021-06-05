package dao.impl.mysql;

import dao.abstraction.RoleDao;
import dao.abstraction.StatusDao;
import dao.abstraction.TariffDao;
import dao.exception.DaoException;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import dao.impl.mysql.converter.DtoConverter;
import dao.impl.mysql.converter.UserDtoConverter;
import entity.Role;
import entity.Status;
import entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DefaultDaoImpl <T> {
    private static final String ERROR_GENERATE_KEY =
            "Can't retrieve generated key";
    private static final String SQL_LIMIT_ONE = " LIMIT 1";

    private static final Logger logger = LogManager.getLogger(DefaultDaoImpl.class);

    /** Connection to database */
    private Connection connection;

    /** Converts data from ResultSet to entity object */
    private DtoConverter<T> converter;

    public DefaultDaoImpl(Connection connection, DtoConverter<T> converter) {
        this.connection = connection;
        this.converter = converter;
    }

    public Connection getConnection() {
        return connection;
    }

    /**
     * Retrieve one object from database which matches given query.
     *
     * @param query raw sql syntax to select object. Can contains ? wildcard.
     * @param params parameters to substitute wildcards in query
     * @return Optional object, which contains retrieved object or null
     */
    public Optional<T> findOne(String query, Object... params) {
        List<T> results = findAll(query + SQL_LIMIT_ONE, params);
        return Optional.ofNullable(results.isEmpty() ? null : results.get(0));
    }

    /**
     * Retrieve all objects from database which match given query.
     *
     * @param query raw sql syntax for objects selecting. Can contains ? wildcard.
     * @param params parameters to substitute wildcards in query
     * @return list of retrieved objects
     */
    public List<T> findAll(String query, Object... params) {
        try (PreparedStatement statement = connection
                .prepareStatement(query)) {

            setParamsToStatement(statement, params);
            try (ResultSet resultSet = statement.executeQuery()) {
                return converter.convertToObjectList(resultSet);
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    /**
     * Perform update of some table in database
     * based on given query and parameters.
     *
     * @param query sql-based string, which specify update behavior
     * @param params parameters to substitute wildcards in query
     */
    public void executeUpdate(String query, Object... params) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setParamsToStatement(statement, params);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }
    private final static String ID_FIELD = "id";
    private final static String LOGIN = "login";
    private final static String PASSWORD = "password";
    private final static String BALANCE = "balance";
    private final static String STATUS_ID_FIELD = "status_id";
    private final static String ROLE_ID_FIELD = "role_id";

    public List<User> findUsers(int noOfRecords,int offset )    {
        DaoFactory daoFactory = DaoFactory.getInstance();
        StatusDao statusDao= daoFactory.getStatusDao(daoFactory.getConnection());
        RoleDao roleDao= daoFactory.getRoleDao(daoFactory.getConnection());
        String query = "select SQL_CALC_FOUND_ROWS * from user limit "
                + offset + " offset " + noOfRecords;
        List<User> list = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(query)){
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                User user = User.newBuilder()
                        .addId(resultSet.getLong(ID_FIELD))
                        .addLogin(resultSet.getString(LOGIN))
                        .addPassword(resultSet.getString(PASSWORD))
                        .addBalance(resultSet.getBigDecimal(BALANCE))
                        .addStatus(statusDao.findOne(resultSet.getInt(STATUS_ID_FIELD)).get())
                        .addRole(roleDao.findOne(resultSet.getInt(ROLE_ID_FIELD)).get())
                        .build();
                list.add(user);
            }
            resultSet.close();

            resultSet = statement.executeQuery("SELECT FOUND_ROWS()");
            if(resultSet.next())
                setNoOfRecords(resultSet.getInt(1));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
    private int noOfRecords;
    public int getNoOfRecords() {
        return noOfRecords;
    }
    public void setNoOfRecords(int noOfRecords) {
        this.noOfRecords =noOfRecords;
    }

    public boolean exist(String query, Object... params) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setParamsToStatement(statement, params);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return true;
            } else {
               return false;
            }

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    /**
     * Performs insertion for entities with generated primary key field.
     * For entities, which doesn't have auto-generated fields -
     * use {@link #executeUpdate(String, Object...)} method
     * to properly persist data.
     *
     * @param query sql-based string, which specify details of insertion operation
     * @param params parameters to substitute wildcards in query
     * @return generated id
     */
    public long executeInsertWithGeneratedPrimaryKey(String query,
                                                     Object... params) {
        try (PreparedStatement statement = connection
                .prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            setParamsToStatement(statement, params);
            statement.executeUpdate();

            return getGeneratedPrimaryKey(statement);

        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }
    /**
     * Performs insertion for entities with generated primary key field.
     * For entities, which doesn't have auto-generated fields -
     * use {@link #executeUpdate(String, Object...)} method
     * to properly persist data.
     *
     * @param query sql-based string, which specify details of insertion operation
     * @param params parameters to substitute wildcards in query
     */
    public void executeInsert(String query, Object... params) {
        try (PreparedStatement statement = connection
                .prepareStatement(query)) {

            setParamsToStatement(statement, params);
            statement.executeUpdate();


        } catch (SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    /**
     * Sets all parameters to statement.
     *
     * @param statement
     * @param params parameters to substitute wildcards in raw query
     * @throws SQLException
     */
    private void setParamsToStatement(PreparedStatement statement, Object... params)
            throws SQLException {
        Objects.requireNonNull(params);

        for (int i = 0; i < params.length; i++) {
            if (params[i] != null) {
                statement.setObject(i + 1, params[i]);
            } else {
                statement.setNull(i + 1, Types.OTHER);
            }
        }
    }

    /**
     * Get from resultSet generated by database primary key.
     * Use only after execution statement.
     * Statement must be in Statement.RETURN_GENERATED_KEYS mode.
     *
     * @param statement
     * @return generated key
     * @throws SQLException if statement doesn't generates key
     */
    private long getGeneratedPrimaryKey(PreparedStatement statement)
            throws SQLException {
        ResultSet rs = statement.getGeneratedKeys();
        if(rs.next()) {
            return rs.getLong(1);
        } else {
            throw new DaoException(ERROR_GENERATE_KEY);
        }
    }

}