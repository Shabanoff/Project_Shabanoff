package dao.abstraction;

import entity.User;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface UserDao extends GenericDao<User, Long> {

    /**
     * Retrieve user from database identified by login.
     *
     * @param login identifier of user
     * @return optional, which contains retrieved object or null
     */
    Optional<User> findOneByLogin(String login);


    /**
     * increase balance of certain amount.
     *
     * @param user   user to increase
     * @param amount amount of increasing
     */
    void increaseBalance(User user, BigDecimal amount);

    /**
     * decrease balance of certain amount.
     *
     * @param user   user to decrease
     * @param amount amount of decreasing
     */
    void decreaseBalance(User user, BigDecimal amount);

    /**
     * Updates certain user status.
     *
     * @param user     user which status will be updated.
     * @param statusId new status of user to update
     */
    void updateUserStatus(User user, int statusId);

    List<User> findUsers(int noOfRecords, int offset);

    int getNoOfRecords();
}