package dao.factory.connection;

import dao.exception.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.sql.Connection;
import java.sql.SQLException;

public class MySqlConnection implements DaoConnection {
    private static final String ERROR_DURING_START =
            "Failed to start transaction";
    private static final String ERROR_DURING_COMMIT =
            "Failed to commit transaction";
    private static final String ERROR_DURING_ROLLBACK =
            "Failed to rollback transaction";
    private static final String ERROR_DURING_CLOSE =
            "Failed to close transaction";

    private static final Logger logger = LogManager.getLogger(MySqlConnection.class);

    private final Connection connection;
    private boolean isTransactionActive;

    public MySqlConnection(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void startTransaction() {
        try {
            connection.setAutoCommit(false);
            isTransactionActive = true;
        } catch (SQLException e) {
            logger.error(ERROR_DURING_START, e);
            throw new DaoException(e);
        }
    }
    @Override
    public void startSerializableTransaction() {
        try {
            connection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
            connection.setAutoCommit(false);
            isTransactionActive = true;
        } catch (SQLException e) {
            logger.error(ERROR_DURING_START, e);
            throw new DaoException(e);
        }
    }

    @Override
    public void commit() {
        try {
            connection.commit();
            connection.setAutoCommit(true);
            isTransactionActive = false;
        } catch (SQLException e) {
            logger.error(ERROR_DURING_COMMIT, e);
            throw new DaoException(e);
        }
    }

    @Override
    public void rollback() {
        try {
            connection.rollback();
            connection.setAutoCommit(true);
            isTransactionActive = false;
        } catch (SQLException e) {
            logger.error(ERROR_DURING_ROLLBACK, e);
            throw new DaoException(e);
        }
    }

    @Override
    public void close() {
        if(isTransactionActive) {
            rollback();
        }

        try {
            if (connection != null) {
                connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
                connection.close();
            }
        } catch (SQLException e) {
            logger.error(ERROR_DURING_CLOSE, e);
            throw new DaoException(e);
        }
    }

    @Override
    public Object getNativeConnection() {
        return connection;
    }
}
