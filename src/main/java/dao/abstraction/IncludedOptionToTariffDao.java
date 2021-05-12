package dao.abstraction;

import entity.IncludedOptionToTariff;

import java.util.Optional;

public interface IncludedOptionToTariffDao extends GenericDao<IncludedOptionToTariff, Long>{
    Optional<IncludedOptionToTariff> findOne(Long tariffId, Long includedOptionId);
}
