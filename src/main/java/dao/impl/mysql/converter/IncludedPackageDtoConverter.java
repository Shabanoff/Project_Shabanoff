package dao.impl.mysql.converter;

import entity.IncludedPackage;
import entity.Service;
import entity.Tariff;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IncludedPackageDtoConverter implements DtoConverter<IncludedPackage> {
    private final static String ID_FIELD = "id";
    private final static String SUBSCRIPTION_DATE = "subscription_date";

    private final DtoConverter<User> userConverter;
    private final DtoConverter<Service> serviceConverter;
    private final DtoConverter<Tariff> tariffConverter;

    public IncludedPackageDtoConverter() {
        this(new UserDtoConverter(), new ServiceDtoConverter(), new TariffDtoConverter());
    }

    public IncludedPackageDtoConverter(DtoConverter<User> userConverter, DtoConverter<Service> serviceConverter, DtoConverter<Tariff> tariffConverter) {
        this.userConverter = userConverter;
        this.serviceConverter = serviceConverter;
        this.tariffConverter = tariffConverter;
    }


    @Override
    public IncludedPackage convertToObject(ResultSet resultSet) throws SQLException {

        User user = userConverter.convertToObject(resultSet);
        Service service = serviceConverter.convertToObject(resultSet);
        Tariff tariff = tariffConverter.convertToObject(resultSet);

        IncludedPackage includedPackage = IncludedPackage.newBuilder()
                .addPackageNumber(resultSet.getLong(ID_FIELD))
                .addSubscriptionDate(resultSet.getTimestamp(SUBSCRIPTION_DATE).toLocalDateTime().toLocalDate())
                .addPackageHolder(user)
                .addService(service)
                .addTariff(tariff)
                .build();
        return includedPackage;
    }
}