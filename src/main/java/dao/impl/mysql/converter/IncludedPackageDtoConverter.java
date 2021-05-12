package dao.impl.mysql.converter;

import entity.IncludedPackage;

import java.sql.ResultSet;
import java.sql.SQLException;

public class IncludedPackageDtoConverter implements DtoConverter<IncludedPackage> {
    private final static String ID_FIELD = "id";
    private final static String SUBSCRIPTION_DATE = "subscription_date";
    private final static String USER_ID = "user_id";
    private final static String TARIFF_ID = "tariff_id";
    private final static String SERVICE_ID = "service_id";

    public IncludedPackageDtoConverter() {
    }


    @Override
    public IncludedPackage convertToObject(ResultSet resultSet) throws SQLException {
        IncludedPackage includedPackage = IncludedPackage.newBuilder().
                addSubscriptionDate(resultSet.getTimestamp(SUBSCRIPTION_DATE).toLocalDateTime().toLocalDate())
                .addUserId(resultSet.getLong(USER_ID))
                .addTariffId(resultSet.getLong(TARIFF_ID))
                .addServiceId(resultSet.getLong(SERVICE_ID))
                .build();
        return includedPackage;
    }
}