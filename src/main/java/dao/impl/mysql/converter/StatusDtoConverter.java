package dao.impl.mysql.converter;

import entity.Status;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StatusDtoConverter implements DtoConverter<Status> {
    private final static String ID_FIELD = "status_id";
    private final static String NAME_FIELD = "status_name";

    @Override
    public Status convertToObject(ResultSet resultSet) throws SQLException {
        int statusId = resultSet.getInt(ID_FIELD);
        String statusName = resultSet.getString(NAME_FIELD);
        return new Status(statusId, statusName);
    }
}
