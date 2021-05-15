package service;

import dao.abstraction.TariffDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import entity.Tariff;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
/**
 * Intermediate layer between command layer and dao layer.
 * Implements operations of finding, creating, deleting entities.
 * Tariff dao layer.
 *
 * @author Shabanoff
 */
public class TariffService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    private TariffService() {
    }

    private static class Singleton {
        private final static TariffService INSTANCE = new TariffService();
    }

    public static TariffService getInstance() {
        return Singleton.INSTANCE;
    }

    public List<Tariff> findAllTariff() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TariffDao tariffDao = daoFactory.getTariffDao(connection);
            return tariffDao.findAll();
        }
    }

    public Optional<Tariff> findTariffByNumber(long TariffId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TariffDao tariffDao = daoFactory.getTariffDao(connection);
            return tariffDao.findOne(TariffId);
        }
    }

    public Optional<Tariff> findByName(String name) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TariffDao tariffDao = daoFactory.getTariffDao(connection);
            return tariffDao.findOneByName(name);
        }
    }


    public Tariff createTariff(Tariff tariff) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TariffDao tariffDao = daoFactory.getTariffDao(connection);
            Tariff inserted = tariffDao.insert(tariff);
            return inserted;
        }
    }

    public void updateTariff(Tariff tariff) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            TariffDao TariffDao = daoFactory.getTariffDao(connection);
            TariffDao.update(tariff);
            connection.commit();
        }
    }
    public void changeCost(Tariff tariff, BigDecimal cost) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            TariffDao tariffDao = daoFactory.getTariffDao(connection);
            tariffDao.changeCost(tariff, cost);
            connection.commit();
        }
    }
}
