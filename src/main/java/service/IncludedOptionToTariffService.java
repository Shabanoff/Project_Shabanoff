package service;

import dao.abstraction.IncludedOptionToTariffDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import entity.IncludedOptionToTariff;

import java.util.List;
import java.util.Optional;

/**
 * Intermediate layer between command layer and dao layer.
 * Implements operations of finding, creating, deleting entities.
 * OptionToTariff dao layer.
 *
 * @author Shabanoff
 */
public class IncludedOptionToTariffService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    private IncludedOptionToTariffService() {
    }

    public static IncludedOptionToTariffService getInstance() {
        return Singleton.INSTANCE;
    }

    public List<IncludedOptionToTariff> findAllIncludedOptionToTariff() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            IncludedOptionToTariffDao includedOptionToTariffDao = daoFactory.getIncludedOptionToTariffDao(connection);
            return includedOptionToTariffDao.findAll();
        }
    }

    public List<IncludedOptionToTariff> findIncludedOptionToTariffByNumber(long IncludedOptionToTariffId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            IncludedOptionToTariffDao includedOptionToTariffDao = daoFactory.getIncludedOptionToTariffDao(connection);
            return includedOptionToTariffDao.findAllByTariff(IncludedOptionToTariffId);
        }
    }

    public Optional<IncludedOptionToTariff> findByTariffIdOptionId(long tariffId, long optionId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            IncludedOptionToTariffDao includedOptionToTariffDao = daoFactory.getIncludedOptionToTariffDao(connection);
            return includedOptionToTariffDao.findOne(tariffId, optionId);
        }
    }

    public Optional<IncludedOptionToTariff> findByTariffId(long tariffId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            IncludedOptionToTariffDao includedOptionToTariffDao = daoFactory.getIncludedOptionToTariffDao(connection);
            return includedOptionToTariffDao.findOne(tariffId);
        }
    }

    public IncludedOptionToTariff createIncludedOptionToTariff(IncludedOptionToTariff includedOptionToTariff, DaoConnection connection) {

            IncludedOptionToTariffDao includedOptionToTariffDao = daoFactory.getIncludedOptionToTariffDao(connection);
            IncludedOptionToTariff inserted = includedOptionToTariffDao.insert(includedOptionToTariff);
            return inserted;

    }

    public void updateIncludedOptionToTariff(IncludedOptionToTariff includedOptionToTariff) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            IncludedOptionToTariffDao includedOptionToTariffDao = daoFactory.getIncludedOptionToTariffDao(connection);
            includedOptionToTariffDao.update(includedOptionToTariff);
            connection.commit();
        }
    }

    public void deleteIncludedOptionToTariff(long tariffId, DaoConnection connection) {

        IncludedOptionToTariffDao includedOptionToTariffDao = daoFactory.getIncludedOptionToTariffDao(connection);
        includedOptionToTariffDao.delete(tariffId);


    }

    private static class Singleton {
        private final static IncludedOptionToTariffService INSTANCE = new IncludedOptionToTariffService();
    }
}
