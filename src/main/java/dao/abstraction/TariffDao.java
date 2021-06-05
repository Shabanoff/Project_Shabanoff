package dao.abstraction;

import entity.Tariff;

import java.math.BigDecimal;
import java.util.List;

public interface TariffDao extends GenericDao<Tariff, Long> {
    /**
     * Retrieve tariff from database identified by name.
     *
     * @param serviceId identifier of tariff
     * @return optional, which contains retrieved object or null
     */
    List<Tariff> findByService(long serviceId);

    /**
     * increase cost of certain amount.
     *
     * @param tariff tariff to increase
     * @param cost   cost of increasing
     */
    void changeCost(Tariff tariff, BigDecimal cost);

    List<Tariff> ascByCostTariff(long serviceId);

    List<Tariff> descByCostTariff(long serviceId);


}
