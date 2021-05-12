package dao.impl.mysql.converter;

import entity.Service;
import entity.Tariff;

import java.sql.ResultSet;
import java.sql.SQLException;

public class TariffDtoConverter implements DtoConverter<Tariff> {
    private final static String ID_FIELD = "id";
    private final static String TARIFF_NAME = "name";
    private final static String COST = "cost";
    private final static String SERVICE_ID = "service_id";;

    @Override
    public Tariff convertToObject(ResultSet resultSet) throws SQLException {
        Tariff tariff = Tariff.newBuilder().addTariffNumber(resultSet.getLong(ID_FIELD))
                .addTariffName(resultSet.getString(TARIFF_NAME))
                .addCost(resultSet.getBigDecimal(COST))
                .addServiceId(resultSet.getLong(SERVICE_ID))
                .build();
        return tariff;
    }
}
