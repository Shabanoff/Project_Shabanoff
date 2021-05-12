package dao.impl.mysql;

import dao.abstraction.TariffDao;
import dao.impl.mysql.converter.DtoConverter;
import dao.impl.mysql.converter.TariffDtoConverter;
import entity.Tariff;

import java.math.BigDecimal;
import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


public class MySqlTariffDao implements TariffDao {
    private final static String SELECT_ALL =
            "SELECT tariff.id,service.name AS service_name, " +
                    "tariff.name AS tariff_name,tariff.cost , " +
                    "GROUP_CONCAT(included_option.definition) AS tariff_definition " +
                    "FROM tariff " +
                    "JOIN service on tariff.service_id = service.id " +
                    "JOIN included_options_to_tariff ON included_options_to_tariff.tariff_id = tariff.id " +
                    "JOIN included_option ON included_options_to_tariff.included_option_id = included_option.id";

    private final static String WHERE_ID =
            "WHERE tariff.id = ? ";

    private final static String WHERE_NAME =
            "WHERE tariff.NAME = ? ";

    private final static String INSERT =
            "INSERT into tariff (login, password, balance," +
                    "status_id, role_id)" +
                    "VALUES(?, ?, ?, ?, ?) ";


    private final static String UPDATE =
            "UPDATE tariff SET " +
                    "name = ?, " +
                    "cost = ?" +
                    "service_id = ?, ";

    private final static String CHANGE_COST =
            "UPDATE tariff SET " +
                    "cost =  ? ";

    private final static  String INSERT_DEFINITION =
            "INSERT INTO included_options_to_tariff "+
                    " (tariff_id, included_option_id)"+
                    " VALUES (?,?)";


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
                obj.getServiceId()

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
                obj.getServiceId()
        );
    }

    @Override
    public void delete(Long id) {
        defaultDao.executeUpdate(
                DELETE + WHERE_ID, id);
    }

    @Override
    public Optional<Tariff> findOneByName(String name) {
        return defaultDao.findOne(SELECT_ALL + WHERE_NAME, name);
    }

    @Override
    public void changeCost(Tariff tariff, BigDecimal cost) {

        Objects.requireNonNull(tariff);

        defaultDao.executeUpdate(
                CHANGE_COST + WHERE_ID,
                cost, tariff.getId()
        );
    }
}
