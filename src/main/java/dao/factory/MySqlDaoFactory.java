package dao.factory;

import dao.abstraction.*;
import dao.datasource.PooledConnection;
import dao.exception.DaoException;
import dao.factory.connection.DaoConnection;
import dao.factory.connection.MySqlConnection;
import dao.impl.mysql.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class MySqlDaoFactory extends DaoFactory {
    private final static String NULLABLE_CONNECTION =
            "Null pointer connection!";

    private final static String WRONG_TYPE_CONNECTION =
            "Wrong type connection!";

    private DataSource dataSource = PooledConnection.getInstance();

    public DaoConnection getConnection() {
        try{
            return new MySqlConnection(dataSource.getConnection());
        } catch(SQLException e) {
            throw new DaoException(e);
        }
    }

    @Override
    public RoleDao getRoleDao(DaoConnection connection) {
        return new MySqlRoleDao(getOwnSqlConnection(connection));
    }

    @Override
    public UserDao getUserDao(DaoConnection connection) {
        return new MySqlUserDao(getOwnSqlConnection(connection));
    }

    @Override
    public IncludedPackageDao getIncludedPackageDao(DaoConnection connection) {
        return new MySqlIncludedPackageDao(getOwnSqlConnection(connection));
    }

    @Override
    public IncludedOptionToTariffDao getIncludedOptionToTariffDao(DaoConnection connection) {
        return new MySqlIncludedOptionToTariffDao(getOwnSqlConnection(connection));
    }

    @Override
    public IncludedOptionDao getIncludedOptionDao(DaoConnection connection) {
        return new MySqlIncludedOptionDao(getOwnSqlConnection(connection));
    }

    @Override
    public ServiceDao getServiceDao(DaoConnection connection) {
        return new MySqlServiceDao(getOwnSqlConnection(connection));
    }

    @Override
    public TariffDao getTariffDao(DaoConnection connection) {
        return new MySqlTariffDao(getOwnSqlConnection(connection));
    }

    @Override
    public StatusDao getStatusDao(DaoConnection connection) {
        return new MySqlStatusDao(getOwnSqlConnection(connection));
    }



    private Connection getOwnSqlConnection(DaoConnection connection) {
        checkDaoConnection(connection);
        return (Connection) connection.getNativeConnection();
    }

    private void checkDaoConnection(DaoConnection connection) {
        if(connection == null || connection.getNativeConnection() == null) {
            throw new DaoException(NULLABLE_CONNECTION);
        }
        if(! (connection instanceof MySqlConnection)) {
            throw new DaoException(WRONG_TYPE_CONNECTION);
        }
    }
}