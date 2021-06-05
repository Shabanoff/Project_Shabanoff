package dao.impl.mysql;

import dao.abstraction.ServiceDao;
import dao.impl.mysql.converter.DtoConverter;
import dao.impl.mysql.converter.ServiceDtoConverter;
import entity.Service;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlServiceDao implements ServiceDao {
    private final static String SELECT_ALL =
            "SELECT id AS service_id, name AS service_name " +
                    "FROM service ";

    private final static String INSERT =
            "INSERT INTO service (name) " +
                    "VALUES(?);";

    private final static String UPDATE =
            "UPDATE service SET name = ? ";

    private final static String DELETE =
            "DELETE FROM service ";

    private final static String WHERE_ID =
            "WHERE id = ? ";


    private final DefaultDaoImpl<Service> defaultDao;

    public MySqlServiceDao(Connection connection) {
        this(connection, new ServiceDtoConverter());
    }

    public MySqlServiceDao(Connection connection,
                           DtoConverter<Service> converter) {
        this.defaultDao = new DefaultDaoImpl<>(connection, converter);
    }

    public MySqlServiceDao(DefaultDaoImpl<Service> defaultDao) {
        this.defaultDao = defaultDao;
    }

    @Override
    public Optional<Service> findOne(Long id) {
        return defaultDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<Service> findAll() {
        return defaultDao.findAll(SELECT_ALL);
    }


    @Override
    public Service insert(Service obj) {
        Objects.requireNonNull(obj);

        int id = (int) defaultDao.executeInsertWithGeneratedPrimaryKey(
                INSERT,
                obj.getServiceName()
        );

        obj.setId(id);

        return obj;
    }

    @Override
    public void update(Service obj) {
        Objects.requireNonNull(obj);

        defaultDao.executeUpdate(
                UPDATE + WHERE_ID,
                obj.getServiceName(),
                obj.getId()
        );
    }

    @Override
    public void delete(Long id) {
        defaultDao.executeUpdate(
                DELETE + WHERE_ID, id);
    }

}
