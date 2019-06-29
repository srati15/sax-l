package dao.helpers;

public class QueryGenerator {
    /*
        Method parameters: 1) table name 2) where condition column names (optional)
     */
    public static String getSelectQuery(String tableName, String... whereColumnNames) {
        StringBuilder builder = new StringBuilder();
        builder.append("SELECT * FROM ").append(tableName);
        builder.append(whereColumnNames.length>0?" WHERE ":"");
        for (int i = 0; i < whereColumnNames.length ; i++) {
            if (i+1 == whereColumnNames.length) builder.append(whereColumnNames[i]).append("=?");
            else builder.append(whereColumnNames[i]).append("=? AND ");
        }
        return builder.toString();
    }

    /*
        Method parameters: 1) table name 2) column name on which where condition is checked 3) updatable column names
     */
    public static String getUpdateQuery(String tableName, String whereColumn, String... columnNames){
        StringBuilder query = new StringBuilder("UPDATE "+tableName+" set ");
        for (String column : columnNames) {
            query.append(column).append("=?, ");
        }
        query.deleteCharAt(query.lastIndexOf(","));
        query.append("WHERE ").append(whereColumn).append(" =?");
        return query.toString();
    }

    public static String getInsertQuery(String tableName, String... columnNames) {
        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ").append(tableName).append(" (");
        for (int i = 0; i < columnNames.length ; i++) {
            builder.append( (i+1 != columnNames.length) ?columnNames[i]+", ":columnNames[i]+") VALUES (");
        }
        for (int i = 0; i < columnNames.length; i++) {
            builder.append(i+1 != columnNames.length? "?, ":"?)");
        }
        return builder.toString();
    }
    public static String getDeleteQuery(String tableName, String whereColumnName) {
        return "DELETE FROM " + tableName + " WHERE " + whereColumnName + " =?";
    }
}