package service;

import dao.abstraction.ServiceDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import entity.Service;
import entity.Tariff;

import java.util.List;
import java.util.Optional;

/**
 * Intermediate layer between command layer and dao layer.
 * Implements operations of finding, creating, deleting entities.
 * Service dao layer.
 *
 * @author Shabanoff
 */
public class ServiceForService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private TariffService tariffService = ServiceFactory.getTariffService();


    private ServiceForService() {
    }
    private static class Singleton {
        private final static ServiceForService INSTANCE = new ServiceForService();
    }

    public static ServiceForService getInstance() {
        return Singleton.INSTANCE;
    }
    public List<Service> findAllService() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            List<Service> services = serviceDao.findAll();
            for (Service service: services) {
                service.setTariffs(tariffService.findByService(service.getId()));
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

    public Optional<Service> findOneByName(String name) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            return serviceDao.findOneByName(name);
        }
    }


    public Service createService(String name) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            Service inserted = serviceDao.insert(getDataFromRequestCreating(name));
            return inserted;
        }
    }
    public void deleteService(long serviceId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            List<Tariff> tariffs = tariffService.findByService(serviceId);
            tariffs.forEach(tariff -> tariffService.deleteTariffForService(tariff.getId(), connection));
            serviceDao.delete(serviceId);
            connection.commit();
        }
    }

    public void updateService(Service service) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            serviceDao.update(service);
            connection.commit();
        }
    }
    private Service getDataFromRequestCreating(String name) {
        return Service.newBuilder()
                .addServiceName(name)
                .build();
    }
}
