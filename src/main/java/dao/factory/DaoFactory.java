package dao.factory;

import dao.abstraction.*;
import dao.exception.DaoException;
import dao.factory.connection.DaoConnection;


import java.util.ResourceBundle;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public abstract class DaoFactory{
        private static final String DB_BUNDLE = "database";
        private static final String DB_CLASS = "factory.class";
        private final static Logger logger = LogManager.getLogger(DaoFactory.class);//TODO: https://mkyong.com/logging/apache-log4j-2-tutorials/

        private static DaoFactory instance;

        /**
         * Gets factory class name from certain properties file.
         * Reflection used for more flexibility.
         *
         * @return specific implemented factory
         */
        public static DaoFactory getInstance() {
            if (instance == null) {
                ResourceBundle bundle = ResourceBundle.getBundle(DB_BUNDLE);
                String className = bundle.getString(DB_CLASS);
                try {
                    instance = (DaoFactory) Class.forName(className).
                            getConstructor().newInstance();
                } catch (Exception e) {
                    logger.error(e);
                    throw new DaoException(e);
                }
            }

            return instance;
        }

        public abstract DaoConnection getConnection();

        public abstract UserDao getUserDao(DaoConnection connection);

        public abstract IncludedPackageDao getIncludedPackageDao(DaoConnection connection);

        public abstract IncludedOptionToTariffDao getIncludedOptionToTariffDao(DaoConnection connection);

        public abstract IncludedOptionDao getIncludedOptionDao(DaoConnection connection);

        public abstract ServiceDao getServicesDao(DaoConnection connection);

        public abstract TariffDao getTariffDao(DaoConnection connection);

        public abstract StatusDao getStatusDao(DaoConnection connection);

        public abstract RoleDao getRoleDao(DaoConnection connection);

}
