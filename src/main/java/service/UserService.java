package service;

import dao.abstraction.UserDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import dao.util.PasswordStorage;
import entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Intermediate layer between command layer and dao layer.
 * Implements operations of finding, creating, deleting entities.
 * Account dao layer.
 *
 * @author Shabanoff
 */
public class UserService {
    private final DaoFactory daoFactory = DaoFactory.getInstance();
    private UserService(){}
    private static class Singleton {
        private final static UserService INSTANCE = new UserService();
    }
 public static UserService getInstance(){return Singleton.INSTANCE;}

    public List<User> findAllUser() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.findAll();
        }
    }
    public Optional<User> findUserByNumber(long userId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.findOne(userId);
        }
    }
    public Optional<User> findByLogin(String login) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.findOneByLogin(login);
        }
    }


    public User createUser(User user) {
        Objects.requireNonNull(user);

        if (user.getRole() == null) {
            user.setDefaultRole();
        }

        String hash = PasswordStorage.getSecurePassword(
                user.getPassword());
        user.setPassword(hash);

        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            User inserted = userDao.insert(user);
            return inserted;
        }
    }

    public void updateAccountStatus(User user, int statusId) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            UserDao userDao = daoFactory.getUserDao(connection);
            userDao.updateUserStatus(user, statusId);
            connection.commit();
        }
    }
    public void increaseUserBalance(User user, BigDecimal amount) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            UserDao userDao = daoFactory.getUserDao(connection);
            userDao.increaseBalance(user, amount);
            connection.commit();
        }
    }
    public void decreaseUserBalance(User user, BigDecimal amount) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            UserDao userDao = daoFactory.getUserDao(connection);
            userDao.decreaseBalance(user, amount);
            connection.commit();
        }
    }
    public boolean isUserExists(User user) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.exist(user.getId());
        }
    }

}
