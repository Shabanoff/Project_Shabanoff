package service;

import dao.abstraction.IncludedOptionDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import entity.IncludedOption;

import java.util.List;
import java.util.Optional;

/**
 * Intermediate layer between command layer and dao layer.
 * Implements operations of finding, creating, deleting entities.
 * Option dao layer.
 *
 * @author Shabanoff
 */
public class IncludedOptionService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();

    private IncludedOptionService() {
    }

    public static IncludedOptionService getInstance() {
        return Singleton.INSTANCE;
    }

    public List<IncludedOption> findAllIncludedOption() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            IncludedOptionDao includedOptionDao = daoFactory.getIncludedOptionDao(connection);
            return includedOptionDao.findAll();
        }
    }

    public Optional<IncludedOption> findIncludedOptionByNumber(long includedOptionId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            IncludedOptionDao includedOptionDao = daoFactory.getIncludedOptionDao(connection);
            return includedOptionDao.findOne(includedOptionId);
        }
    }

    public void deleteIncludedOptionByNumber(long includedOptionId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            IncludedOptionDao includedOptionDao = daoFactory.getIncludedOptionDao(connection);
            includedOptionDao.delete(includedOptionId);
        }
    }

    public IncludedOption createIncludedOption(String definition) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            IncludedOptionDao includedOptionDao = daoFactory.getIncludedOptionDao(connection);
            return includedOptionDao.insert(getDataFromRequestCreating(definition));
        }
    }

    public void updateIncludedOption(IncludedOption includedOption) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            IncludedOptionDao includedOptionDao = daoFactory.getIncludedOptionDao(connection);
            includedOptionDao.update(includedOption);
            connection.commit();
        }
    }

    private IncludedOption getDataFromRequestCreating(String definition) {
        return IncludedOption.newBuilder()
                .addDefinition(definition)
                .build();
    }

    private static class Singleton {
        private final static IncludedOptionService INSTANCE = new IncludedOptionService();
    }
}
