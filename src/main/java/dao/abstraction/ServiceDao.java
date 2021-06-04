package dao.abstraction;

import entity.Service;

import java.util.List;
import java.util.Optional;

public interface ServiceDao extends GenericDao<Service, Long>{
    /**
     * Retrieve service from database identified by name.
     * @param name identifier of service
     * @return optional, which contains retrieved object or null
     */
    Optional<Service> findOneByName(String name);

    List<Service> findLimit(int offset, int noOfRecords);
}
