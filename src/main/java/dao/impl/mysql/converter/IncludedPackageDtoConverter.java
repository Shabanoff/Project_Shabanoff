package dao.impl.mysql.converter;

import entity.IncludedPackage;
import entity.Service;
import entity.Tariff;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IncludedPackageDtoConverter implements DtoConverter<IncludedPackage> {
    private final static String ID_FIELD = "id";
    private final static String SUBSCRIPTION_DATE = "subscription_date";
    private final DtoConverter<Service> serviceConverter;
    private final DtoConverter<Tariff> tariffConverter;

    public IncludedPackageDtoConverter() {
        this(new ServiceDtoConverter(), new TariffDtoConverter());
    }

    public IncludedPackageDtoConverter(DtoConverter<Service> serviceConverter, DtoConverter<Tariff> tariffConverter) {
        this.serviceConverter = serviceConverter;
        this.tariffConverter = tariffConverter;
    }


    @Override
    public IncludedPackage convertToObject(ResultSet resultSet) throws SQLException {
        Service service = serviceConverter.convertToObject(resultSet);
        Tariff tariff = tariffConverter.convertToObject(resultSet);
        return IncludedPackage.newBuilder()
                .addId(resultSet.getLong(ID_FIELD))
                .addSubscriptionDate(resultSet.getTimestamp(SUBSCRIPTION_DATE).toLocalDateTime().toLocalDate())
                .addTariff(tariff)
                .addService(service)
                .build();
    }
}