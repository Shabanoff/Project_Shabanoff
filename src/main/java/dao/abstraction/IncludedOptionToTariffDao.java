package dao.abstraction;

import entity.IncludedOptionToTariff;

import java.util.List;
import java.util.Optional;

public interface IncludedOptionToTariffDao extends GenericDao<IncludedOptionToTariff, Long>{
    Optional<IncludedOptionToTariff> findOne(long tariffId,long includedOptionId);
    List<IncludedOptionToTariff> findAllByTariff(long tariffId);
}
