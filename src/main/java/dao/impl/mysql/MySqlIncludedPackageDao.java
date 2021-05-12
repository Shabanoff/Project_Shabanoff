package dao.impl.mysql;

import dao.abstraction.IncludedPackageDao;
import dao.impl.mysql.converter.DtoConverter;
import dao.impl.mysql.converter.IncludedPackageDtoConverter;
import entity.IncludedPackage;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlIncludedPackageDao implements IncludedPackageDao {
    private final static String SELECT_ALL =
            "SELECT included_package.id, included_package.subscription_date, " +
                    "service.name AS service_name, " +
                    "tariff.name AS tariff_name, tariff.cost, " +
                    "GROUP_CONCAT(included_option.definition) AS tariff_definition " +
                    "FROM included_package " +
                    "JOIN service ON included_package.service_id = service.id " +
                    "JOIN tariff ON included_package.tariff_id = tariff.id " +
                    "JOIN included_options_to_tariff ON tariff.id = included_options_to_tariff.tariff_id " +
                    "JOIN included_option ON included_option.id = included_options_to_tariff.included_option_id";
    private final static String WHERE_ID =
            "included_package.id = ? ";

    private final static String WHERE_SERVICE_ID=
            "included_package.service_id = ? ";
    private final static String WHERE_DATE=
            "included_package.subscription_date = ? ";

    private final static String INSERT =
            "INSERT into included_package (subscription_date, user_id, service_id," +
                    "tariff_id)" +
                    "VALUES(?, ?, ?, ?, ?) ";

    private final static String UPDATE =
            "UPDATE included_package SET " +
                    "subscription_date = ?, " +
                    "user_id = ?" +
                    "service_id = ?, " +
                    "tariff_id = ?, ";


    private final static String DELETE =
            "DELETE FROM included_package";


    private final DefaultDaoImpl<IncludedPackage> defaultDao;

    public MySqlIncludedPackageDao(Connection connection) {
        this(connection, new IncludedPackageDtoConverter());
    }

    public MySqlIncludedPackageDao(Connection connection,
                                   DtoConverter<IncludedPackage> converter) {
        this.defaultDao = new DefaultDaoImpl<>(connection, converter);
    }
    @Override
    public Optional<IncludedPackage> findOne(Long id) {
        return defaultDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<IncludedPackage> findAll() {
        return defaultDao.findAll(SELECT_ALL);
    }

    @Override
    public IncludedPackage insert(IncludedPackage obj) {
        Objects.requireNonNull(obj);

        int id = (int) defaultDao.executeInsertWithGeneratedPrimaryKey(
                INSERT,
                obj.getSubscriptionDate(),
                obj.getServiceId(),
                obj.getTariffId(),
                obj.getUserId()

        );

        obj.setId(id);
        return obj;
    }

    @Override
    public void update(IncludedPackage obj) {
        Objects.requireNonNull(obj);

        defaultDao.executeUpdate(
                UPDATE + WHERE_ID,
                obj.getSubscriptionDate(),
                obj.getServiceId(),
                obj.getTariffId(),
                obj.getUserId()
        );

    }
    public void updateService(IncludedPackage obj) {
        Objects.requireNonNull(obj);

        defaultDao.executeUpdate(
                UPDATE + WHERE_SERVICE_ID,
                obj.getSubscriptionDate(),
                obj.getServiceId(),
                obj.getTariffId(),
                obj.getUserId()
        );

    }

    @Override
    public void delete(Long id) {
        defaultDao.executeUpdate(
                DELETE + WHERE_ID, id);
    }

    @Override
    public Optional<IncludedPackage> findOneByDate(LocalDate date) {
        return defaultDao.findOne(SELECT_ALL + WHERE_DATE,date);
    }
}
