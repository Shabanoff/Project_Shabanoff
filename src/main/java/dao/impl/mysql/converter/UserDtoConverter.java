package dao.impl.mysql.converter;

import entity.Role;
import entity.Status;
import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDtoConverter implements DtoConverter<User> {
    private final static String ID_FIELD = "user_id";
    private final static String LOGIN = "login";
    private final static String PASSWORD = "password";
    private final static String BALANCE = "balance";
    private final DtoConverter<Status> statusConverter;
    private final DtoConverter<Role> roleConverter;

    public UserDtoConverter() {
        this(new StatusDtoConverter(), new RoleDtoConverter());
    }

    public UserDtoConverter(DtoConverter<Status> statusConverter, DtoConverter<Role> roleConverter) {
        this.statusConverter = statusConverter;
        this.roleConverter = roleConverter;
    }

    @Override
    public User convertToObject(ResultSet resultSet) throws SQLException {
        Status status = statusConverter.convertToObject(resultSet);
        Role role = roleConverter.convertToObject(resultSet);
        User user = User.newBuilder()
                .addId(resultSet.getLong(ID_FIELD))
                .addLogin(resultSet.getString(LOGIN))
                .addPassword(resultSet.getString(PASSWORD))
                .addBalance(resultSet.getBigDecimal(BALANCE))
                .addStatus(status)
                .addRole(role)
                .build();

        return user;
    }
}
