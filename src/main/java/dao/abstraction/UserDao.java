package dao.abstraction;

import entity.Status;
import entity.User;

import java.math.BigDecimal;
import java.util.Optional;

public interface UserDao<T extends User> extends GenericDao<T, Long>{

    /**
     * Retrieve user from database identified by login.
     * @param login identifier of user
     * @return optional, which contains retrieved object or null
     */
    Optional<User> findOneByLogin(String login);

    /**
     * Check if user exists in database.
     *
     * @param login user's identifier
     * @return {@code true} if exists else {@code false}
     */
    boolean existByLogin(String login);

    /**
     * increase balance of certain amount.
     *
     * @param user user to increase
     * @param amount amount of increasing
     */
    void increaseBalance(User user, BigDecimal amount);

    /**
     * decrease balance of certain amount.
     *
     * @param user user to decrease
     * @param amount amount of decreasing
     */
    void decreaseBalance(User user, BigDecimal amount);

    /**
     * Updates certain account status.
     *
     * @param user user which status will be updated.
     * @param status new status of user to update
     */
    void updateAccountStatus(T user, Status status);
}