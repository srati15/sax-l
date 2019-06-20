package dao.helpers;

import anotations.Column;
import anotations.Entity;
import database.CreateConnection;
import datatypes.Domain;
import enums.QuestionType;
import enums.RequestStatus;
import enums.UserType;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;

public class EntityPersister {
    public static boolean executeInsert(Domain<Integer> entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        assert entity.getClass().isAnnotationPresent(Entity.class);
        String tableName = getTableName(entity);
        List<Object> values = new ArrayList<>();
        List<String> columnNames = new ArrayList<>();
        setColumnsAndValues(columnNames, values, entity);
        assert columnNames.size() == values.size();
        String query = getQuery(columnNames, tableName);
        try {
            assert connection != null;
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            for (int i = 0; i < values.size(); i++) {
                Object value = checkForEnums(values.get(i));
                statement.setObject(i + 1, value);
            }
            System.out.println(statement);
            int result = statement.executeUpdate();
            if (result == 1) {
                rs = statement.getGeneratedKeys();
                rs.next();
                entity.setId(rs.getInt(1));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
        return false;
    }

    private static String getTableName(Object entity) {
        Class clazz = entity.getClass();
        while (clazz.getSuperclass()!=null) {
            if (clazz.isAnnotationPresent(Entity.class)) {
                Entity annotation = (Entity) clazz.getAnnotation(Entity.class);
                return annotation.table();
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }

    private static Object checkForEnums(Object o) {
        if (o instanceof UserType) return ((UserType) o).getValue();
        if (o instanceof QuestionType) return ((QuestionType) o).getValue();
        if (o instanceof RequestStatus) return ((RequestStatus) o).getValue();
        return o;
    }

    private static String getQuery(List<String> columnNames, String tableName) {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ").append(tableName).append(" (");
        for (int i = 0; i < columnNames.size(); i++) {
            builder.append((i + 1 != columnNames.size()) ? columnNames.get(i) + ", " : columnNames.get(i) + ") VALUES (");
        }
        for (int i = 0; i < columnNames.size(); i++) {
            builder.append(i + 1 != columnNames.size() ? "?, " : "?)");
        }
        return builder.toString();
    }

    private static void setColumnsAndValues(List<String> columnNames, List<Object> values, Domain<Integer> entity) {
        Class current = entity.getClass();
        while (current.getSuperclass()!=null) {
            for (Field field : current.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    try {
                        columnNames.add(field.getDeclaredAnnotation(Column.class).value());
                        field.setAccessible(true);
                        values.add(field.get(entity));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            current = current.getSuperclass();
        }
    }

}
