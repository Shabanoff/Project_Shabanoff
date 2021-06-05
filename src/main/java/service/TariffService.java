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

    private static final IncludedOptionToTariffService includedOptionToTariffService =
            ServiceFactory.getIncludedOptionToTariffService();
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private static final IncludedPackageService includedPackageService =
            ServiceFactory.getIncludedPackageService();

    private TariffService() {
    }

    public static TariffService getInstance() {
        return Singleton.INSTANCE;
    }

    public Optional<Tariff> findTariffById(long tariffId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            TariffDao tariffDao = daoFactory.getTariffDao(connection);
            return tariffDao.findOne(tariffId);
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

    public List<String> deleteTariff(long tariffId) {
        List<String> errors = new ArrayList<>();
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            if (includedPackageService.isIncludedPackageExistsByTariff(tariffId, connection)) {
                errors.add(ADDED_BY_USER);
                return errors;
            }
            includedOptionToTariffService.deleteIncludedOptionToTariff(tariffId, connection);
            TariffDao tariffDao = daoFactory.getTariffDao(connection);
            tariffDao.delete(tariffId);
            connection.commit();

        }
        return errors;
    }

    public void deleteTariffForService(long tariffId, DaoConnection connection) {
        includedOptionToTariffService.deleteIncludedOptionToTariff(tariffId, connection);
        TariffDao tariffDao = daoFactory.getTariffDao(connection);
        tariffDao.delete(tariffId);
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
        private static final TariffService INSTANCE = new TariffService();
    }
}
