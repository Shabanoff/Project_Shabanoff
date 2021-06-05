package service;

import dao.abstraction.IncludedOptionToTariffDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import entity.IncludedOptionToTariff;

import java.util.List;

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

    public List<IncludedOptionToTariff> findIncludedOptionToTariffByNumber(long IncludedOptionToTariffId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            IncludedOptionToTariffDao includedOptionToTariffDao = daoFactory.getIncludedOptionToTariffDao(connection);
            return includedOptionToTariffDao.findAllByTariff(IncludedOptionToTariffId);
        }
    }

    public void createIncludedOptionToTariff(IncludedOptionToTariff includedOptionToTariff, DaoConnection connection) {
        IncludedOptionToTariffDao includedOptionToTariffDao = daoFactory.getIncludedOptionToTariffDao(connection);
        includedOptionToTariffDao.insert(includedOptionToTariff);

    }

    public void deleteIncludedOptionToTariff(long tariffId, DaoConnection connection) {
        IncludedOptionToTariffDao includedOptionToTariffDao = daoFactory.getIncludedOptionToTariffDao(connection);
        includedOptionToTariffDao.delete(tariffId);
    }

    private static class Singleton {
        private final static IncludedOptionToTariffService INSTANCE = new IncludedOptionToTariffService();
    }
}
