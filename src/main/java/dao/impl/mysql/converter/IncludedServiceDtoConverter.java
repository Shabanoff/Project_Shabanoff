package dao.impl.mysql.converter;

import entity.IncludedService;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IncludedServiceDtoConverter implements DtoConverter<IncludedService> {
    private final static String ID_FIELD = "id";
    private final static String DEFINITION = "definition";

    @Override
    public IncludedService convertToObject(ResultSet resultSet) throws SQLException {

        IncludedService includedService = IncludedService.newBuilder()
                .addId(resultSet.getLong(ID_FIELD))
                .addDefinition(resultSet.getString(DEFINITION))
                .build();
        return includedService;
    }
}
