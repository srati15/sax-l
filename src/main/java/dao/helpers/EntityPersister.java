package dao.helpers;

import anotations.Column;
import anotations.Entity;
import anotations.OneToOne;
import dao.QuestionDao;
import database.CreateConnection;
import datatypes.Domain;
import datatypes.answer.Answer;
import datatypes.question.Question;
import datatypes.question.QuestionResponse;
import enums.QuestionType;
import enums.RequestStatus;
import enums.UserType;

import java.lang.reflect.Field;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static dao.helpers.FinalBlockExecutor.executeFinalBlock;

public class EntityPersister {
    public static boolean executeInsert(Domain<Integer> entity) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        assert entity.getClass().isAnnotationPresent(Entity.class);
        String tableName = getTableName(entity);
        Map<String, Object> columnValueMap = new HashMap<>();
        Map<Object, OneToOne> oneToOneObjects = new HashMap<>();
        setColumnsAndValues(columnValueMap, entity, oneToOneObjects);
        String query = getQuery(new ArrayList<>(columnValueMap.keySet()), tableName);
        try {
            assert connection != null;
            statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            int i = 1;
            for (String columnName : columnValueMap.keySet()) {
                Object value = checkForEnums(columnValueMap.get(columnName));
                statement.setObject(i, value);
                i++;
            }
            System.out.println(statement);
            int result = statement.executeUpdate();
            if (result == 1) {
                rs = statement.getGeneratedKeys();
                rs.next();
                int foreignId = rs.getInt(1);
                entity.setId(foreignId);
                insert(oneToOneObjects, foreignId);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            executeFinalBlock(connection, statement, rs);
        }
        return false;
    }

    private static void insert(Map<Object, OneToOne> oneToOneObjects, int foreignId) {
        Connection connection = CreateConnection.getConnection();
        PreparedStatement statement = null;
        ResultSet rs = null;
        for (Object object : oneToOneObjects.keySet()) {
            try {
                Map<String, Object> childColumnValueMap = new HashMap<>();
                Map<Object, OneToOne> childOneToOneObjects = new HashMap<>();
                setColumnsAndValues(childColumnValueMap, (Domain<Integer>) object, childOneToOneObjects);
                childColumnValueMap.put(oneToOneObjects.get(object).joinColumn(), foreignId);
                String query = getQuery(new ArrayList<>(childColumnValueMap.keySet()), getTableName(object));
                statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
                int i = 1;
                for (String columnName : childColumnValueMap.keySet()) {
                    Object value = checkForEnums(childColumnValueMap.get(columnName));
                    statement.setObject(i, value);
                    i++;
                }
                int result = statement.executeUpdate();
                if (result == 1) {
                    rs = statement.getGeneratedKeys();
                    rs.next();
                    int rsInt = rs.getInt(1);
                    ((Domain<Integer>) object).setId(foreignId);
                    insert(childOneToOneObjects, rsInt);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }


    private static String getTableName(Object entity) {
        Class clazz = entity.getClass();
        while (clazz.getSuperclass() != null) {
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

    private static String getQuery(ArrayList<String> columnNames, String tableName) {
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

    private static void setColumnsAndValues(Map<String, Object> columnValueMap, Domain<Integer> entity, Map<Object, OneToOne> oneToOneObjects) {
        Class current = entity.getClass();
        while (current.getSuperclass() != null) {
            for (Field field : current.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class)) {
                    try {
                        String column = field.getDeclaredAnnotation(Column.class).value();
                        field.setAccessible(true);
                        columnValueMap.put(column, field.get(entity));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                if (field.isAnnotationPresent(OneToOne.class)) {
                    try {
                        OneToOne annotation = field.getDeclaredAnnotation(OneToOne.class);
                        field.setAccessible(true);
                        oneToOneObjects.put(field.get(entity), annotation);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
            current = current.getSuperclass();
        }
    }

    public static void main(String[] args) {
        Answer answer = new Answer("hwr?");
        Question question = new QuestionResponse("asd", 1);
        question.setAnswer(answer);
        new QuestionDao().insert(question);
    }
}
