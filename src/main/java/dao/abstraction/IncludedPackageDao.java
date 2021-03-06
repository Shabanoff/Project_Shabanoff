package dao.abstraction;

import entity.IncludedPackage;
import entity.Tariff;

import java.util.List;

public interface IncludedPackageDao extends GenericDao<IncludedPackage, Long> {

    void updateIncludedPackage(IncludedPackage obj, Tariff tariff);

    /**
     * Retrieve includedPackage from database identified by user.
     *
     * @param userId identifier of includedPackage
     * @return optional, which contains retrieved object or null
     */
    List<IncludedPackage> findByUser(long userId);

    /**
     * Retrieve includedPackage from database identified by service.
     *
     * @param serviceId identifier of includedPackage
     */
    boolean existByService(long serviceId);

    /**
     * Retrieve includedPackage from database identified by tariff.
     *
     * @param tariffId identifier of includedPackage
     */
    boolean existByTariff(long tariffId);
}
