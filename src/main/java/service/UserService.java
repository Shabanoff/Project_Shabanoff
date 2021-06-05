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
            connection.commit();
        }

    }

    public void decreaseUserBalance(User user, BigDecimal amount,DaoConnection connection ) {

            UserDao userDao = daoFactory.getUserDao(connection);
            userDao.decreaseBalance(user, amount);

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

    private final IncludedPackageService includedPackageService = ServiceFactory.getIncludedPackageService();

    private final static String NOT_ENOUGH_MONEY = "no.money";
    private final static String ALREADY_ADD = "already.add";
    public List<String> addingTariff (User currentUser, Optional<Tariff> addingTariff){
        List error = new ArrayList();
        BigDecimal cost = addingTariff.get().getCost();

            List<IncludedPackage> includedPackages =
                    includedPackageService.findByUser(currentUser.getId());

            Optional<IncludedPackage> existedPackagePackage =
                    includedPackages.stream().filter(includedPackage ->
                            includedPackage.getService().getId() ==
                                    addingTariff.get().getService().getId()).findFirst();
            if (existedPackagePackage.isPresent()  && existedPackagePackage.get().getTariff().equals(addingTariff.get())){
                error.add(ALREADY_ADD);
                return error;
            }
            if ( currentUser.getBalance().compareTo(addingTariff.get().getCost())<0){
                error.add(NOT_ENOUGH_MONEY);
                return error;
            }
        try (DaoConnection connection = daoFactory.getConnection()) {
            connection.startSerializableTransaction();
            if (existedPackagePackage.isPresent()) {
                        includedPackageService.updateIncludePackage(existedPackagePackage.get(),addingTariff.get(),connection);
            } else {
                    includedPackageService.createIncludedPackage(
                            getDataFromRequest(currentUser, addingTariff.get()));
            }
                decreaseUserBalance(currentUser, cost, connection);
            connection.commit();
        }
        return error;

    }
    private IncludedPackage getDataFromRequest(User user, Tariff tariff) {
        return IncludedPackage.newBuilder()
                .addSubscriptionDate(LocalDate.now())
                .addUserId(user.getId())
                .addTariff(tariff)
                .addService(tariff.getService())
                .build();
    }
    public List<String> createUser (String login, String password){
        User userDto = getDataFromRequestCreating(login, password);
        List<String> errors = validateData(userDto);
        if (errors.isEmpty()){
            createUser(userDto);
        }
        return errors;
    }
    private User getDataFromRequestCreating(String login, String password) {
        return User.newBuilder()
                .addLogin(login)
                .addPassword(password)
                .addDefaultBalance()
                .build();
    }
    private int noOfRecords;
    public int getNoOfRecords() {
        return noOfRecords;
    }

    public  final String USER_ALREADY_EXISTS = "user.exists";
    private List<String> validateData(User user) {
        List<String> errors = new ArrayList<>();

        Util.validateField(new LoginValidator(), user.getLogin(), errors);
        Util.validateField(new PasswordValidator(), user.getPassword(), errors);


        if(errors.isEmpty() && isUserExists(user)) {
            errors.add(USER_ALREADY_EXISTS);
        }

        return errors;
    }
    public void userPagination(HttpServletRequest request){
        int page = 1;
        int recordsPerPage = 5;
        if(request.getParameter("page") != null)
            page = Integer.parseInt(request.getParameter("page"));
        List<User> list = findUsers((page-1)*recordsPerPage,
                recordsPerPage);
        int noOfRecords = getNoOfRecords();
        int noOfPages = (int) Math.ceil(noOfRecords * 1.0 / recordsPerPage);
        request.setAttribute("users", list);
        request.setAttribute("noOfPages", noOfPages);
        request.setAttribute("currentPage", page);
    }

    private static class Singleton {

        private final static UserService INSTANCE = new UserService();
    }

}
