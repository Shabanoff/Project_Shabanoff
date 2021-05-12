package dao.abstraction;

import entity.IncludedPackage;

import java.time.LocalDate;
import java.util.Optional;

public interface IncludedPackageDao extends GenericDao<IncludedPackage, Long>{
    /**
     * Retrieve includedPackage from database identified by date.
     *
     * @param date identifier of includedPackage
     * @return optional, which contains retrieved object or null
     */
    Optional<IncludedPackage> findOneByDate(LocalDate date);

}
