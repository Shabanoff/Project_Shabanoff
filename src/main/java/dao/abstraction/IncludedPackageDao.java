package dao.abstraction;

import entity.IncludedPackage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IncludedPackageDao extends GenericDao<IncludedPackage, Long>{
    /**
     * Retrieve includedPackage from database identified by user.
     *
     * @param userId identifier of includedPackage
     * @return optional, which contains retrieved object or null
     */
    List<IncludedPackage> findByUser(long userId);

}
