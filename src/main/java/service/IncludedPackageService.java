package service;

import dao.abstraction.IncludedPackageDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import entity.IncludedPackage;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class IncludedPackageService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    private IncludedPackageService() {
    }

    private static class Singleton {
        private final static IncludedPackageService INSTANCE = new IncludedPackageService();
    }

    public static IncludedPackageService getInstance() {
        return Singleton.INSTANCE;
    }

    public List<IncludedPackage> findAllIncludedPackage() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            IncludedPackageDao includedPackage = daoFactory.getIncludedPackageDao(connection);
            return includedPackage.findAll();
        }
    }

    public Optional<IncludedPackage> findIncludedPackageById(long includedPackageId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            IncludedPackageDao includedPackage = daoFactory.getIncludedPackageDao(connection);
            return includedPackage.findOne(includedPackageId);
        }
    }


    public IncludedPackage createIncludedPackage(IncludedPackage includedPackage) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            IncludedPackageDao accountsDao = daoFactory.getIncludedPackageDao(connection);
            IncludedPackage inserted = accountsDao.insert(includedPackage);
            return inserted;
        }
    }

    public Optional<IncludedPackage> findOneByDate(LocalDate date) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            IncludedPackageDao includedPackage = daoFactory.getIncludedPackageDao(connection);
            return includedPackage.findOneByDate(date);
        }
    }

}
