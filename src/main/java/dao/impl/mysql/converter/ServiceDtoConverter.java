package dao.impl.mysql.converter;

import entity.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceDtoConverter implements DtoConverter<Service> {
    private final static String ID_FIELD = "service_id";
    private final static String NAME_FIELD = "service_name";

    @Override
    public Service convertToObject(ResultSet resultSet) throws SQLException {
        return Service.newBuilder()
                .addId(resultSet.getLong(ID_FIELD))
                .addServiceName(resultSet.getString(NAME_FIELD))
                .build();
    }
}