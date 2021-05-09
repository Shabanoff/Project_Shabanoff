package dao.impl.mysql.converter;

import entity.Service;
import entity.Tariff;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DtoTariff implements DtoConverter<Tariff> {
    private final static String ID_FIELD = "id";
    private final static String TARIFF_NAME = "name";
    private final static String COST = "cost";
    private final DtoConverter<Service> serviceConverter;

    public DtoTariff(DtoConverter<Service> serviceConverter) {
        this.serviceConverter = serviceConverter;
    }

    @Override
    public Tariff convertToObject(ResultSet resultSet) throws SQLException {
        Service service = serviceConverter.convertToObject(resultSet);
        Tariff tariff = Tariff.newBuilder().addTariffNumber(resultSet.getLong(ID_FIELD))
                .addTariffName(resultSet.getString(TARIFF_NAME))
                .addCost(resultSet.getBigDecimal(COST))
                .addService(service)
                .build();
        return null;
    }
}
