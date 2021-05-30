package dao.impl.mysql.converter;

import entity.IncludedOption;
import entity.Service;
import entity.Tariff;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TariffDtoConverter implements DtoConverter<Tariff> {
    private final static String ID_FIELD = "tariff_id";
    private final static String TARIFF_NAME = "tariff_name";
    private final static String COST = "tariff_cost";
    private final DtoConverter<Service> serviceConverter;
    private final static String INCLUDED_OPTIONS = "included_options";

    public TariffDtoConverter() {this(new ServiceDtoConverter());
    }

    public TariffDtoConverter(DtoConverter<Service> serviceConverter) {
        this.serviceConverter = serviceConverter;
    }

    @Override
    public Tariff convertToObject(ResultSet resultSet) throws SQLException {
        Service service = serviceConverter.convertToObject(resultSet);
        return Tariff.newBuilder().addTariffId(resultSet.getLong(ID_FIELD))
                .addTariffName(resultSet.getString(TARIFF_NAME))
                .addCost(resultSet.getBigDecimal(COST))
                .addService(service)
                .addIncludedOptions(getIncludedOptions(resultSet.getString(INCLUDED_OPTIONS)))
                .build();
    }

    private List<IncludedOption> getIncludedOptions(String concatIncludedOptions){
        return Stream.of(concatIncludedOptions.split(",")).map(split -> IncludedOption.newBuilder().addDefinition(split).build()).collect(Collectors.toList());
    }
}
