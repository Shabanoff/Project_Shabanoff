package dao.impl.mysql;

import dao.exception.DaoException;
import dao.impl.mysql.converter.DtoConverter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class DefaultDaoImpl<T> {
    private static final String ERROR_GENERATE_KEY =
            "Can't retrieve generated key";
    private static final String SQL_LIMIT_ONE = " LIMIT 1";

    private static final Logger logger = LogManager.getLogger(DefaultDaoImpl.class);

    /**
     * Connection to database
     */
    private Connection connection;

    /**
     * Converts data from ResultSet to entity object
     */
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
     * @param query  raw sql syntax to select object. Can contains ? wildcard.
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
     * @param query  raw sql syntax for objects selecting. Can contains ? wildcard.
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
     * @param query  sql-based string, which specify update behavior
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

    public int getNumberOfRows(String query) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next())
                return resultSet.getInt(1);

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return 0;
    }

    public boolean exist(String query, Object... params) {
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            setParamsToStatement(statement, params);
            try (ResultSet resultSet = statement.executeQuery()) {
                return resultSet.next();
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
     * @param query  sql-based string, which specify details of insertion operation
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
     * @param query  sql-based string, which specify details of insertion operation
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
     * @param params    parameters to substitute wildcards in raw query
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
        if (rs.next()) {
            return rs.getLong(1);
        } else {
            throw new DaoException(ERROR_GENERATE_KEY);
        }
    }

}