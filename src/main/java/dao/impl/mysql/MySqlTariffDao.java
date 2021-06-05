package dao.impl.mysql;

import dao.abstraction.TariffDao;
import dao.impl.mysql.converter.DtoConverter;
import dao.impl.mysql.converter.TariffDtoConverter;
import entity.Service;
import entity.Tariff;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class MySqlTariffDao implements TariffDao {
    private final static String SELECT_ALL =
            "SELECT tariff.id AS tariff_id ," +
                    "tariff.name AS tariff_name,tariff.cost AS tariff_cost , " +
                    "service.id AS service_id, " +
                    "service.name AS service_name, " +
                    "GROUP_CONCAT(included_option.definition) AS included_options " +
                    "FROM tariff " +
                    "JOIN service on tariff.service_id = service.id " +
                    "JOIN included_options_to_tariff ON included_options_to_tariff.tariff_id = tariff.id " +
                    "JOIN included_option ON included_options_to_tariff.included_option_id = included_option.id ";

    private final static String WHERE_ID =
            "WHERE tariff.id = ? ";
    private final static String GROUP_BY=
            "GROUP BY tariff.id ";
    private final static String ASC_BY_COST=
            "ORDER BY " +
                    "tariff_cost ASC";
    private final static String DESC_BY_COST=
            "ORDER BY " +
                    "tariff_cost DESC";


    private final static String WHERE_SERVICE_ID =
            "WHERE tariff.service_id = ? ";

    private final static String INSERT =
            "INSERT into tariff (name, cost, " +
                    "service_id )" +
                    "VALUES(?, ?, ?) ";


    private final static String UPDATE =
            "UPDATE tariff SET " +
                    "name = ?, " +
                    "cost = ?" +
                    "service_id = ?, ";

    private final static String CHANGE_COST =
            "UPDATE tariff SET " +
                    "cost =  ? ";


    private final static String DELETE =
            "DELETE FROM tariff ";

    private final DefaultDaoImpl<Tariff> defaultDao;

    public MySqlTariffDao(Connection connection) {
        this(connection, new TariffDtoConverter());
    }

    public MySqlTariffDao(Connection connection,
                        DtoConverter<Tariff> converter) {
        this.defaultDao = new DefaultDaoImpl<>(connection, converter);
    }

    public MySqlTariffDao(DefaultDaoImpl<Tariff> defaultDao) {
        this.defaultDao = defaultDao;
    }

    @Override
    public Optional<Tariff> findOne(Long id) {
        return defaultDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<Tariff> findAll() {
        return defaultDao.findAll(SELECT_ALL);
    }

    @Override
    public Tariff insert(Tariff obj) {
        Objects.requireNonNull(obj);

        int id = (int) defaultDao.executeInsertWithGeneratedPrimaryKey(
                INSERT,
                obj.getTariffName(),
                obj.getCost(),
                obj.getService().getId()

        );

        obj.setId(id);
        return obj;
    }

    @Override
    public void update(Tariff obj) {
        Objects.requireNonNull(obj);

        defaultDao.executeUpdate(
                UPDATE + WHERE_ID,
                obj.getTariffName(),
                obj.getCost(),
                obj.getService()
        );
    }

    @Override
    public void delete(Long id) {
        defaultDao.executeUpdate(
                DELETE + WHERE_ID, id);
    }

    @Override
    public List<Tariff> findByService(long serviceId) {
        return defaultDao.findAll(SELECT_ALL + WHERE_SERVICE_ID+ GROUP_BY, serviceId);
    }

    @Override
    public void changeCost(Tariff tariff, BigDecimal cost) {

        Objects.requireNonNull(tariff);

        defaultDao.executeUpdate(
                CHANGE_COST + WHERE_ID,
                cost, tariff.getId()
        );
    }

    @Override
    public List<Tariff> ascByCostTariff(long serviceId) {
        return defaultDao.findAll(SELECT_ALL + WHERE_SERVICE_ID+ GROUP_BY+ASC_BY_COST, serviceId);
    }

    @Override
    public List<Tariff> descByCostTariff(long serviceId) {
        return defaultDao.findAll(SELECT_ALL + WHERE_SERVICE_ID+ GROUP_BY+DESC_BY_COST, serviceId);
    }
}
