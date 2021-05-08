package dao.abstraction;

import entity.Tariff;

import java.math.BigDecimal;
import java.util.Optional;

public interface TariffDao extends GenericDao<Tariff, Long>{
    /**
     * Retrieve tariff from database identified by name.
     * @param name identifier of tariff
     * @return optional, which contains retrieved object or null
     */
    Optional<Tariff> findOneByName(String name);

    /**
     * increase cost of certain amount.
     *
     * @param tariff tariff to increase
     * @param cost cost of increasing
     */
    void increaseBalance(Tariff tariff, BigDecimal cost);

    /**
     * decrease cost of certain amount.
     *
     * @param tariff tariff to decrease
     * @param cost cost of decreasing
     */
    void decreaseBalance(Tariff tariff, BigDecimal cost);
}
