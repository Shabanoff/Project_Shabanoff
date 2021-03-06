package service;

import dao.abstraction.IncludedPackageDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import entity.IncludedPackage;
import entity.Tariff;

import java.util.List;

/**
 * Intermediate layer between command layer and dao layer.
 * Implements operations of finding, creating, deleting entities.
 * Package dao layer.
 *
 * @author Shabanoff
 */
public class IncludedPackageService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    private IncludedPackageService() {}

    public static IncludedPackageService getInstance() {
        return Singleton.INSTANCE;
    }

    public void createIncludedPackage(IncludedPackage includedPackage) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            IncludedPackageDao includedPackageDao = daoFactory.getIncludedPackageDao(connection);
            includedPackageDao.insert(includedPackage);
        }
    }

    public List<IncludedPackage> findByUser(long userId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            IncludedPackageDao includedPackage = daoFactory.getIncludedPackageDao(connection);
            return includedPackage.findByUser(userId);
        }
    }

    public void updateIncludePackage(IncludedPackage includedPackage, Tariff tariff, DaoConnection connection) {
        IncludedPackageDao includedPackageDao = daoFactory.getIncludedPackageDao(connection);
        includedPackageDao.updateIncludedPackage(includedPackage, tariff);
    }

    public boolean isIncludedPackageExistsByService(long serviceId, DaoConnection connection) {
        IncludedPackageDao includedPackageDao = daoFactory.getIncludedPackageDao(connection);
        return includedPackageDao.existByService(serviceId);
    }

    public boolean isIncludedPackageExistsByTariff(long tariffId, DaoConnection connection) {
        IncludedPackageDao includedPackageDao = daoFactory.getIncludedPackageDao(connection);
        return includedPackageDao.existByTariff(tariffId);
    }

    public void deleteIncludedPackage(long id) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            IncludedPackageDao includedPackageDao = daoFactory.getIncludedPackageDao(connection);
            includedPackageDao.delete(id);
        }
    }

    private static class Singleton {
        private final static IncludedPackageService INSTANCE = new IncludedPackageService();
    }


}
