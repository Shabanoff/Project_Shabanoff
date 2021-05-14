package service;

import dao.abstraction.ServiceDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import entity.Service;

import java.util.List;
import java.util.Optional;

/**
 * Intermediate layer between command layer and dao layer.
 * Implements operations of finding, creating, deleting entities.
 * Service dao layer.
 *
 * @author Shabanoff
 */
public class ServiceService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    private ServiceService() {
    }
    private static class Singleton {
        private final static ServiceService INSTANCE = new ServiceService();
    }

    public static ServiceService getInstance() {
        return Singleton.INSTANCE;
    }
    public List<Service> findAllService() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            return serviceDao.findAll();
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


    public Service createService(Service service) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            ServiceDao serviceDao = daoFactory.getServiceDao(connection);
            Service inserted = serviceDao.insert(service);
            return inserted;
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
}
