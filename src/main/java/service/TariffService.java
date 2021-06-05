package service;

import dao.abstraction.TariffDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import entity.IncludedOption;
import entity.IncludedOptionToTariff;
import entity.Tariff;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dao.util.Constants.ADDED_BY_USER;

/**
 * Intermediate layer between command layer and dao layer.
 * Implements operations of finding, creating, deleting entities.
 * Tariff dao layer.
 *
 * @author Shabanoff
 */
public class TariffService {

    private static IncludedOptionToTariffService includedOptionToTariffService =
            ServiceFactory.getIncludedOptionToTariffService();
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    IncludedPackageService includedPackageService = ServiceFactory.getIncludedPackageService();

    private TariffService() {
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

    public Optional<Tariff> findTariffById(long TariffId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TariffDao tariffDao = daoFactory.getTariffDao(connection);
            return tariffDao.findOne(TariffId);
        }
    }

    public List<Tariff> findByService(long serviceId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TariffDao tariffDao = daoFactory.getTariffDao(connection);
            return tariffDao.findByService(serviceId);
        }
    }
    public List<Tariff> ascByCostTariff(long serviceId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TariffDao tariffDao = daoFactory.getTariffDao(connection);
            return tariffDao.ascByCostTariff(serviceId);
        }
    }public List<Tariff> descByCostTariff(long serviceId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TariffDao tariffDao = daoFactory.getTariffDao(connection);
            return tariffDao.descByCostTariff(serviceId);
        }
    }

    public void createTariff(Tariff tariff) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            TariffDao tariffDao = daoFactory.getTariffDao(connection);
            tariffDao.insert(tariff);
            for (IncludedOption includedOption : tariff.getIncludedOptions()) {
                includedOptionToTariffService.createIncludedOptionToTariff(
                        createIncludedOptionToTariffEntity(tariff, includedOption.getId()), connection);
            }
            connection.commit();

        }
    }

    public IncludedOptionToTariff createIncludedOptionToTariffEntity(Tariff tariff, long optionId) {
        return IncludedOptionToTariff.newBuilder()
                .addTariffId(tariff.getId())
                .addOptionId(optionId)
                .build();
    }

    public void updateTariff(Tariff tariff) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            TariffDao TariffDao = daoFactory.getTariffDao(connection);
            TariffDao.update(tariff);
            connection.commit();
        }
    }

    public List<String> deleteTariff(long tariffId) {
        List<String> erorrs = new ArrayList<>();
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            if (includedPackageService.findIncludedPackageByTariff(tariffId, connection).isPresent()) {
                erorrs.add(ADDED_BY_USER);
                return erorrs;
            }
                includedOptionToTariffService.deleteIncludedOptionToTariff(tariffId, connection);
                TariffDao TariffDao = daoFactory.getTariffDao(connection);
                TariffDao.delete(tariffId);
                connection.commit();

        }
        return erorrs;
    }

    public void deleteTariffForService(long tariffId, DaoConnection connection) {
        includedOptionToTariffService.deleteIncludedOptionToTariff(tariffId, connection);
        TariffDao TariffDao = daoFactory.getTariffDao(connection);
        TariffDao.delete(tariffId);
    }

    public void changeCost(Tariff tariff, BigDecimal cost) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            TariffDao tariffDao = daoFactory.getTariffDao(connection);
            tariffDao.changeCost(tariff, cost);
            connection.commit();
        }
    }

    private static class Singleton {
        private final static TariffService INSTANCE = new TariffService();
    }
}
