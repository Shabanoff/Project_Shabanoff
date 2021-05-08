package dao.abstraction;

import entity.IncludedPackage;
import entity.IncludedService;

import java.time.LocalDate;
import java.util.Optional;

public interface IncludedPackageDao {
    /**
     * Retrieve includedPackage from database identified by date.
     * @param date identifier of includedPackage
     * @return optional, which contains retrieved object or null
     */
    Optional<IncludedService> findOneByDate(LocalDate date);
    /**
     * Add subscription date
     *
     * @param includedPackage includedPackage which date will be add.
     * @param date new date of includedPackage to add
     */
    void addDate(IncludedPackage includedPackage, LocalDate date);
}
