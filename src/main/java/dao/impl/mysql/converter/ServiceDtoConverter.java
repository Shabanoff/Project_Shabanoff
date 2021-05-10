package dao.impl.mysql.converter;

import entity.Service;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceDtoConverter implements DtoConverter<Service> {
    private final static String ID_FIELD = "id";
    private final static String NAME_FIELD = "name";

    @Override
    public Service convertToObject(ResultSet resultSet) throws SQLException {
        Service service = Service.newBuilder()
                .addServiceNumber(resultSet.getLong(ID_FIELD))
                .addServiceName(resultSet.getString(NAME_FIELD))
                .build();
        return service;
    }
}