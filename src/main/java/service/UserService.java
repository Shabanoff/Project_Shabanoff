package service;

import controller.util.Util;
import controller.util.validator.LoginValidator;
import controller.util.validator.PasswordValidator;
import dao.abstraction.UserDao;
import dao.factory.DaoFactory;
import dao.factory.connection.DaoConnection;
import dao.util.PasswordStorage;
import entity.IncludedPackage;
import entity.Tariff;
import entity.User;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static dao.util.Constants.*;

/**
 * Intermediate layer between command layer and dao layer. Implements operations of finding,
 * creating, deleting entities. Account dao layer.
 *
 * @author Shabanoff
 */
public class UserService {
    private static final DaoFactory daoFactory = DaoFactory.getInstance();
    private static final IncludedPackageService includedPackageService = ServiceFactory
            .getIncludedPackageService();

    private UserService() {
    }

    public static UserService getInstance() {
        return Singleton.INSTANCE;
    }

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

    public void createUser(User user) {
        Objects.requireNonNull(user);

        if (user.getRole() == null) {
            user.setDefaultRole();
        }
        if (user.getStatus() == null) {
            user.setDefaultStatus();
        }

        String hash = PasswordStorage.getSecurePassword(
                user.getPassword());
        user.setPassword(hash);

        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            userDao.insert(user);
        }
    }

    public void updateUserStatus(User user, int statusId) {
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
            if (user.isBlocked()){
                user.setDefaultStatus();
            }
            connection.commit();
        }

    }

    public void decreaseUserBalance(User user, BigDecimal amount, DaoConnection connection) {
        UserDao userDao = daoFactory.getUserDao(connection);
        userDao.decreaseBalance(user, amount);
    }

    public void decreaseUserBalanceScheduler(User user, BigDecimal amount) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            userDao.decreaseBalance(user, amount);
        }

    }

    public boolean isCredentialsValid(String login, String password) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            Optional<User> user = userDao.findOneByLogin(login);

            return user
                    .filter(u -> PasswordStorage.checkSecurePassword(
                            password, u.getPassword()))
                    .isPresent();
        }

    }

    public boolean isUserExists(User user) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.exist(user.getId());
        }
    }

    public List<String> addingTariff(User currentUser, Tariff addingTariff) {
        List<String> error = new ArrayList<>();
        BigDecimal cost = addingTariff.getCost();

        List<IncludedPackage> includedPackages =
                includedPackageService.findByUser(currentUser.getId());

        Optional<IncludedPackage> existedIncludedPackage =
                includedPackages.stream()
                        .filter(includedPackage ->
                                includedPackage.getService().getId() ==
                                        addingTariff.getService().getId())
                        .findFirst();

        if (existedIncludedPackage.isPresent() && existedIncludedPackage.get().getTariff()
                .equals(addingTariff)) {
            error.add(ALREADY_ADD);
            return error;
        }
        if (currentUser.getBalance().compareTo(addingTariff.getCost()) < 0) {
            error.add(NOT_ENOUGH_MONEY);
            return error;
        }

        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            if (existedIncludedPackage.isPresent()) {
                includedPackageService
                        .updateIncludePackage(existedIncludedPackage.get(), addingTariff,
                                connection);
            } else {
                includedPackageService.createIncludedPackage(
                        collectIncludedPackage(currentUser, addingTariff));
            }
            decreaseUserBalance(currentUser, cost, connection);
            connection.commit();
        }
        return error;

    }

    private IncludedPackage collectIncludedPackage(User user, Tariff tariff) {
        return IncludedPackage.newBuilder()
                .addSubscriptionDate(LocalDate.now())
                .addUserId(user.getId())
                .addTariff(tariff)
                .addService(tariff.getService())
                .build();
    }

    public List<String> createUser(String login, String password) {
        User userDto = getDataFromRequestCreating(login, password);
        List<String> errors = validateData(userDto);
        if (errors.isEmpty()) {
            createUser(userDto);
        }
        return errors;
    }

    public List<User> findAll(int limit, int offset) {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.findAll(limit, offset);
        }
    }

    public int getNumberOfRows() {
        try (DaoConnection connection = daoFactory.getConnection()) {
            UserDao userDao = daoFactory.getUserDao(connection);
            return userDao.getNumberOfRows();
        }
    }

    private User getDataFromRequestCreating(String login, String password) {
        return User.newBuilder()
                .addLogin(login)
                .addPassword(password)
                .addDefaultBalance()
                .build();
    }

    private List<String> validateData(User user) {
        List<String> errors = new ArrayList<>();

        Util.validateField(new LoginValidator(), user.getLogin(), errors);
        Util.validateField(new PasswordValidator(), user.getPassword(), errors);

        if (errors.isEmpty() && isUserExists(user)) {
            errors.add(USER_ALREADY_EXISTS);
        }

        return errors;
    }

    public void userPagination(HttpServletRequest request) {
        int page = 1;
        int recordsPerPage = 5;
        if (request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        List<User> list = findAll(recordsPerPage, (page - 1) * recordsPerPage);
        int numberOfRows = getNumberOfRows();
        int numberOfPages = (int) Math.ceil(numberOfRows * 1.0 / recordsPerPage);
        request.setAttribute("users", list);
        request.setAttribute("numberOfPages", numberOfPages);
        request.setAttribute("currentPage", page);
    }

    private static class Singleton {

        private final static UserService INSTANCE = new UserService();
    }

}
