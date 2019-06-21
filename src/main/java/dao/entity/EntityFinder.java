package dao.entity;

import anotations.Column;
import anotations.Entity;
import database.CreateConnection;
import datatypes.Domain;
import enums.UserType;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;

public class EntityFinder {
    public <E extends Domain<Integer>> E executeFindById(Class clazz, Integer id) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        assert clazz.isAnnotationPresent(Entity.class);
        String tableName = getTableName(clazz);
        String query = "SELECT * from " + tableName + " where id=" + id;
        try {
            assert connection != null;
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();
            Map<String, String> columnFieldMap = getColumnNames(clazz);
            if (rs.next()) {
                Constructor<? extends Domain> cons = clazz.getConstructor();
                Domain object = cons.newInstance();
                columnFieldMap.remove("id");
                for (String column : columnFieldMap.keySet()) {
                    Object value = rs.getObject(column);
                    Field field = object.getClass().getDeclaredField(columnFieldMap.get(column));
                    field.setAccessible(true);
                    field.set(object, value);
                }
                object.setId(id);
                return (E) object;
            }
        } catch (SQLException | IllegalAccessException | InvocationTargetException | NoSuchFieldException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
        return null;
    }
    public <E extends Domain<Integer>> List<E> executeFindAll(Class clazz) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        List<Domain<Integer>> list = new ArrayList<>();
        assert clazz.isAnnotationPresent(Entity.class);
        String tableName = getTableName(clazz);
        String query = "SELECT * from " + tableName;
        try {
            assert connection != null;
            statement = connection.prepareStatement(query);
            rs = statement.executeQuery();
            Map<String, String> columnFieldMap = getColumnNames(clazz);
            while (rs.next()) {
                Constructor<? extends Domain> cons = clazz.getConstructor();
                Domain object = cons.newInstance();
                columnFieldMap.remove("id");
                for (String column : columnFieldMap.keySet()) {
                    Object value = rs.getObject(column);
                    Field field = object.getClass().getDeclaredField(columnFieldMap.get(column));
                    field.setAccessible(true);
                    value = checkEnum(field, value);
                    field.set(object, value);
                }
                object.setId(rs.getInt("id"));
                list.add(object);
            }
        } catch (SQLException | IllegalAccessException | InvocationTargetException | NoSuchFieldException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
        return (List<E>) list;
    }

    private Object checkEnum(Field field, Object value) {
        if (field.getType().equals( UserType.class)) return UserType.getById((Integer) value);
        return value;
    }

    private Map<String, String> getColumnNames(Class clazz) {
        Map<String, String> columnNames = new HashMap<>();
        while (clazz.getSuperclass() != null) {
            for (Field field : clazz.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    String column = field.getDeclaredAnnotation(Column.class).value();
                    columnNames.put(column, field.getName());
                }
            }
            clazz = clazz.getSuperclass();
        }
        return columnNames;
    }

    private String getTableName(Class clazz) {
        return getString(clazz);
    }

    static String getString(Class clazz) {
        while (clazz.getSuperclass() != null) {
            if (clazz.isAnnotationPresent(Entity.class)) {
                Entity annotation = (Entity) clazz.getAnnotation(Entity.class);
                return annotation.table();
            }
            clazz = clazz.getSuperclass();
        }
        return null;
    }
}
