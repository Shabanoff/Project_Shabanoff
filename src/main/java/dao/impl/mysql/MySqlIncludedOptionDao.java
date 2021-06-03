package dao.impl.mysql;

import dao.abstraction.IncludedOptionDao;
import dao.impl.mysql.converter.DtoConverter;
import dao.impl.mysql.converter.IncludedOptionDtoConverter;
import entity.IncludedOption;

import java.sql.Connection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class MySqlIncludedOptionDao implements IncludedOptionDao {
    private final static String SELECT_ALL =
            "SELECT included_option.*" +
                    "FROM included_option ";
    private final static String WHERE_ID =
            "WHERE included_option.id = ? ";

    private final static String INSERT =
            "INSERT into included_option (definition)" +
                    "VALUES(?) ";

    private final static String UPDATE =
            "UPDATE included_option SET " +
                    "definition = ?, ";


    private final static String DELETE =
            "DELETE FROM included_option ";

    private final DefaultDaoImpl<IncludedOption> defaultDao;

    public MySqlIncludedOptionDao(Connection connection) {
        this(connection, new IncludedOptionDtoConverter());
    }

    public MySqlIncludedOptionDao(Connection connection,
                                  DtoConverter<IncludedOption> converter) {
        this.defaultDao = new DefaultDaoImpl<>(connection, converter);
    }

    @Override
    public Optional<IncludedOption> findOne(Long id) {
        return defaultDao.findOne(SELECT_ALL + WHERE_ID, id);
    }

    @Override
    public List<IncludedOption> findAll() {
        return defaultDao.findAll(SELECT_ALL);
    }

    @Override
    public IncludedOption insert(IncludedOption obj) {
        Objects.requireNonNull(obj);

        int id = (int) defaultDao.executeInsertWithGeneratedPrimaryKey(
                INSERT,
                obj.getDefinition()
        );

        obj.setId(id);
        return obj;
    }

    @Override
    public void update(IncludedOption obj) {
        Objects.requireNonNull(obj);

        defaultDao.executeUpdate(
                UPDATE + WHERE_ID,
                obj.getDefinition()
        );

    }

    @Override
    public void delete(Long id) {
        defaultDao.executeUpdate(
                DELETE + WHERE_ID, id);
    }
}
