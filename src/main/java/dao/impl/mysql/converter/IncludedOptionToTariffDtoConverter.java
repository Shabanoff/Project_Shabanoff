package dao.impl.mysql.converter;

import entity.IncludedOptionToTariff;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IncludedOptionToTariffDtoConverter implements DtoConverter<IncludedOptionToTariff> {
    private final static String OPTION_ID = "included_option_id";
    private final static String TARIFF_ID = "tariff_id";

    public IncludedOptionToTariffDtoConverter() {
    }

    @Override
    public IncludedOptionToTariff convertToObject(ResultSet resultSet) throws SQLException {
        IncludedOptionToTariff includedOptionToTariffDao =IncludedOptionToTariff.newBuilder()
                .addId(resultSet.getLong(ID_FIELD))
                .addOptionId(resultSet.getLong(OPTION_ID))
                .addTariffId(resultSet.getLong(TARIFF_ID))
                .build();
        return includedOptionToTariffDao;
    }
}
