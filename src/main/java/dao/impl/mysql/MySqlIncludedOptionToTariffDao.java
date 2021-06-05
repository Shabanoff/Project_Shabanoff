package dao.impl.mysql;

import dao.abstraction.IncludedOptionToTariffDao;
import dao.impl.mysql.converter.DtoConverter;
import dao.impl.mysql.converter.IncludedOptionToTariffDtoConverter;
import entity.IncludedOptionToTariff;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlIncludedOptionToTariffDao implements IncludedOptionToTariffDao {
    private final static String SELECT_ALL =
            "SELECT included_options_to_tariff.tariff_id,tariff.name AS tariff_name, " +
                    "included_options_to_tariff.included_option_id, " +
                    "included_option.definition AS definition " +
                    "FROM tariff " +
                    "JOIN included_options_to_tariff ON included_options_to_tariff.tariff_id = tariff.id " +
                    "JOIN included_option ON included_options_to_tariff.included_option_id = included_option.id ";

    private final static String WHERE_TARIFF_OPTION =
            "WHERE tariff.id = ? " +
                    "AND included_option_id = ?";

    private final static String WHERE_TARIFF =
            "WHERE tariff_id = ? ";


    private final static String INSERT =
            "INSERT into included_options_to_tariff (tariff_id, " +
                    "included_option_id )" +
                    "VALUES(?, ?) ";


    private final static String UPDATE =
            "UPDATE included_options_to_tariff SET " +
                    "tariff_id = ?, " +
                    "included_option.id = ?";




    private final static String DELETE =
            "DELETE FROM included_options_to_tariff ";


    private final DefaultDaoImpl<IncludedOptionToTariff> defaultDao;

    public MySqlIncludedOptionToTariffDao(Connection connection) {
        this(connection, new IncludedOptionToTariffDtoConverter());
    }

    public MySqlIncludedOptionToTariffDao(Connection connection,
                                  DtoConverter<IncludedOptionToTariff> converter) {
        this.defaultDao = new DefaultDaoImpl<>(connection, converter);
    }

    public Optional<IncludedOptionToTariff> findOne(long tariffId, long includedOptionId) {
        return defaultDao.findOne(SELECT_ALL + WHERE_TARIFF_OPTION, tariffId, includedOptionId);
    }

    @Override
    public Optional<IncludedOptionToTariff> findOne(Long aLong) {
        return Optional.empty();
    }

    @Override
    public List<IncludedOptionToTariff> findAll() {
        return defaultDao.findAll(SELECT_ALL);
    }
    @Override
    public List<IncludedOptionToTariff> findAllByTariff(long tariffId) {
        return defaultDao.findAll(SELECT_ALL+WHERE_TARIFF, tariffId);
    }

    @Override
    public IncludedOptionToTariff insert(IncludedOptionToTariff obj) {
        Objects.requireNonNull(obj);

         defaultDao.executeInsert(
                INSERT,
                obj.getTariffId(),
                obj.getOptionId()

        );

        return obj;
    }

    @Override
    public void update(IncludedOptionToTariff obj) {
        Objects.requireNonNull(obj);

        defaultDao.executeUpdate(
                UPDATE + WHERE_TARIFF_OPTION,
                obj.getTariffId(),
                obj.getOptionId()
        );
    }

    @Override
    public void delete(Long id) {
        defaultDao.executeUpdate(
                DELETE + WHERE_TARIFF, id);
    }

    public void deleteTariffOptional(Long tariffId, Long optionalId) {
        defaultDao.executeUpdate(
                DELETE + WHERE_TARIFF_OPTION, tariffId, optionalId);
    }


}
