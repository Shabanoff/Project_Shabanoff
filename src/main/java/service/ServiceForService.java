package service;

import dao.abstraction.ServiceDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import entity.Service;
import entity.Tariff;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static dao.util.Constants.ADDED_BY_USER;

/**
 * Intermediate layer between command layer and dao layer.
 * Implements operations of finding, creating, deleting entities.
 * Service dao layer.
 *
 * @author Shabanoff
 */
public class ServiceForService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    IncludedPackageService includedPackageService = ServiceFactory.getIncludedPackageService();
    private final TariffService tariffService = ServiceFactory.getTariffService();

    private ServiceForService() {
    }

    public static ServiceForService getInstance() {
        return Singleton.INSTANCE;
    }

    public List<Service> findAllService() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            List<Service> services = serviceDao.findAll();
            for (Service service : services) {
                service.setTariffs(tariffService.findByService(service.getId()));
            }
            return services;
        }
    }

    public List<Service> ascByCostTariff() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            List<Service> services = serviceDao.findAll();
            for (Service service : services) {
                service.setTariffs(tariffService.ascByCostTariff(service.getId()));
            }
            return services;
        }
    }

    public List<Service> descByCostTariff() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            List<Service> services = serviceDao.findAll();
            for (Service service : services) {
                service.setTariffs(tariffService.descByCostTariff(service.getId()));
            }
            return services;
        }
    }

    public Optional<Service> findServiceById(long serviceId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            return serviceDao.findOne(serviceId);
        }
    }

    public void createService(String name) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            serviceDao.insert(getDataFromRequestCreating(name));
        }
    }

    public List<String> deleteService(long serviceId) {
        List<String> errors = new ArrayList<>();
        try (DaoConnection connection = daoFactory.getConnection()) {
            if (includedPackageService.isIncludedPackageExistsByService(serviceId, connection)) {
                errors.add(ADDED_BY_USER);
                return errors;
            }
            connection.startSerializableTransaction();
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            List<Tariff> tariffs = tariffService.findByService(serviceId);
            tariffs.forEach(tariff -> tariffService.deleteTariffForService(tariff.getId(), connection));
            serviceDao.delete(serviceId);
            connection.commit();
        }
        return errors;
    }

    private Service getDataFromRequestCreating(String name) {
        return Service.newBuilder()
                .addServiceName(name)
                .build();
    }

    private static class Singleton {
        private final static ServiceForService INSTANCE = new ServiceForService();
    }

}
