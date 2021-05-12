package dao.impl.mysql.converter;

import entity.IncludedOption;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IncludedOptionDtoConverter implements DtoConverter<IncludedOption> {
    private final static String ID_FIELD = "id";
    private final static String DEFINITION = "definition";

    @Override
    public IncludedOption convertToObject(ResultSet resultSet) throws SQLException {

        IncludedOption includedOption = IncludedOption.newBuilder()
                .addId(resultSet.getLong(ID_FIELD))
                .addDefinition(resultSet.getString(DEFINITION))
                .build();
        return includedOption;
    }
}
