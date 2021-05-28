package dao.impl.mysql;

import dao.abstraction.UserDao;
import dao.datasource.PooledConnection;
import dao.impl.mysql.converter.DtoConverter;
import dao.impl.mysql.converter.UserDtoConverter;
import entity.Role;
import entity.Status;
import entity.User;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlUserDao implements UserDao {
    private final static String SELECT_ALL =
            "SELECT user.id AS user_id, " +
                    "user.login, user.password, user.balance" +
                    "user.status_id, user.role_id," +
                    "status.id AS status_id, status.name AS status_name " +
                    "FROM user JOIN status ON user.status_id = status.id" +
                    "role.id AS role_id, role.name AS role_name " +
                    "FROM user JOIN role ON user.role_id = role.id ";

    private final static String WHERE_ID =
            "WHERE tariff.id = ? ";

    private final static String WHERE_LOGIN =
            "WHERE tariff.name = ? ";

    private final static String INSERT =
            "INSERT into user (login, password, balance," +
                    "status_id, role_id)" +
                    "VALUES(?, ?, ?, ?, ?) ";

    private final static String UPDATE_STATUS =
            "UPDATE user SET " +
                    "status_id = ? ";

    private final static String UPDATE =
            "UPDATE user SET " +
                    "login = ?, " +
                    "password = ?" +
                    "balance = ?, " +
                    "status_id = ?, " +
                    "role_id = ?, ";

    private final static String INCREASE_BALANCE =
            "UPDATE user SET " +
                    "balance = balance + ? ";

    private final static String DECREASE_BALANCE =
            "UPDATE user SET " +
                    "balance = balance - ? ";

    private final static String DELETE =
            "DELETE FROM user ";


    private final DefaultDaoImpl<User> defaultDao;

    public MySqlUserDao(Connection connection) {
        this(connection, new UserDtoConverter());
    }

    public MySqlUserDao(Connection connection,
                        DtoConverter<User> converter) {
        this.defaultDao = new DefaultDaoImpl<>(connection, converter);
    }

    public MySqlUserDao(DefaultDaoImpl<User> defaultDao) {
        this.defaultDao = defaultDao;
    }

    @Override
    public Optional<User> findOne(Long id) {
        return defaultDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<User> findAll() {
        return defaultDao.findAll(SELECT_ALL);
    }

    @Override
    public User insert(User obj) {
        Objects.requireNonNull(obj);

        int id = (int) defaultDao.executeInsertWithGeneratedPrimaryKey(
                INSERT,
                obj.getLogin(),
                obj.getPassword(),
                obj.getBalance(),
                obj.getStatus().getId(),
                obj.getRole().getId()

        );

        obj.setId(id);
        return obj;
    }

    @Override
    public void update(User obj) {
        Objects.requireNonNull(obj);

        defaultDao.executeUpdate(
                UPDATE + WHERE_ID,
                obj.getLogin(),
                obj.getPassword(),
                obj.getBalance(),
                obj.getStatus().getId(),
                obj.getRole().getId()
        );

    }

    @Override
    public void delete(Long id) {
        defaultDao.executeUpdate(
                DELETE + WHERE_ID, id);

    }

    @Override
    public Optional<User> findOneByLogin(String login) {
        return defaultDao.findOne(SELECT_ALL + WHERE_LOGIN, login);
    }

    @Override
    public boolean existByLogin(String login) {
        return findOneByLogin(login).isPresent();
    }

    @Override
    public void increaseBalance(User user, BigDecimal amount) {
        Objects.requireNonNull(user);

        defaultDao.executeUpdate(
                INCREASE_BALANCE + WHERE_ID,
                amount, user.getId()
        );
    }

    @Override
    public void decreaseBalance(User user, BigDecimal amount) {
        Objects.requireNonNull(user);

        defaultDao.executeUpdate(
                DECREASE_BALANCE + WHERE_ID,
                amount, user.getId()
        );

    }

    @Override
    public void updateUserStatus(User user, int statusId) {
        Objects.requireNonNull(user);

        defaultDao.executeUpdate(
                UPDATE_STATUS + WHERE_ID,
                statusId,
                user.getId()
        );
    }
    public static void main(String[] args) {
        DataSource dataSource = PooledConnection.getInstance();
        UserDao mySqlUserDao;


        try {
            mySqlUserDao = new MySqlUserDao(dataSource.getConnection());
            int random = (int)(Math.random()*100);
            ((MySqlUserDao) mySqlUserDao).
                    printAll(mySqlUserDao.findAll());
            System.out.println();

            System.out.println("Find one with id 1:");
            System.out.println(mySqlUserDao.findOne((long)1));

            System.out.println("Find one by name Login:");
            System.out.println(mySqlUserDao.findOneByLogin("123"));

            System.out.println("Insert test:");
            User accountType = mySqlUserDao.insert(User.newBuilder().addLogin("first"+random).
                    addStatus(new Status(Status.StatusIdentifier.ACTIVE_STATUS
                            .getId(),"USER")).
                    addPassword("123").
                    addBalance(BigDecimal.TEN).
                    addRole(new Role(Role.RoleIdentifier.
                            USER_ROLE.getId(),"USER")).
                    build()
            );
            ((MySqlUserDao) mySqlUserDao).
                    printAll(mySqlUserDao.findAll());

            System.out.println("Update:");
            accountType.setDefaultBalance();
            mySqlUserDao.update(accountType);
            ((MySqlUserDao) mySqlUserDao).
                    printAll(mySqlUserDao.findAll());

            System.out.println("Delete:");
            mySqlUserDao.delete(accountType.getId());
            ((MySqlUserDao) mySqlUserDao).
                    printAll(mySqlUserDao.findAll());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void printAll(List<User> list) {
        System.out.println("Find all:");
        for (User type : list) {
            System.out.println(type);
        }
    }
}
