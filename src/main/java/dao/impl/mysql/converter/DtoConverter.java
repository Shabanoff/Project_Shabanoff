package dao.impl.mysql.converter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Common interface for all dto converters.
 *
 * @param <T> type of entity object
 *
 * @author Shabanoff
 */

public interface DtoConverter <T>{

    /**
     * Read data from a result set and convert it to list of objects.
     *
     * @param resultSet result set from the database
     * @return list of converted objects
     * @throws SQLException
     */
    default List<T> convertToObjectList (ResultSet resultSet)
            throws SQLException {
        List<T> convertedObjects = new ArrayList<T>();

        while (resultSet.next()){
            convertedObjects.add(convertToObject(resultSet));
        }

        return convertedObjects;
    }
    /**
     * Read data from a result set and convert it to certain object.
     *
     * @param resultSet result set from the database
     * @return converted object
     * @throws SQLException
     */
    T convertToObject(ResultSet resultSet) throws SQLException ;
}
