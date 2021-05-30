package dao.abstraction;

import entity.Service;
import entity.Tariff;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface TariffDao extends GenericDao<Tariff, Long>{
    /**
     * Retrieve tariff from database identified by name.
     * @param service identifier of tariff
     * @return optional, which contains retrieved object or null
     */
    List<Tariff> findByService(Service service);

    /**
     * increase cost of certain amount.
     *
     * @param tariff tariff to increase
     * @param cost cost of increasing
     */
    void changeCost(Tariff tariff, BigDecimal cost);

}
