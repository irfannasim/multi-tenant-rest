package com.rd.mtr.configuration;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.*;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JdbcEntityManager {
    private static final Logger logger = LogManager.getLogger(JdbcEntityManager.class);

    /**
     * returns a single instance of "tClass" (with @Column annotated fields) based on the ResultSet from
     * the exsecuted query.  this only returns a single instance from the first row.
     *
     * @param conn
     * @param tClass
     * @param sql
     * @param args
     * @param <T>
     * @return
     * @throws SQLException
     */
    public static <T> T getSingle(Connection conn, final Class<T> tClass, String sql, Object... args) throws SQLException {
        final Object[] t = new Object[1];
        executeQuery(conn, sql, rs -> {
            if (rs.next()) {
                t[0] = construct(rs, tClass);
            }
        }, args);
        return (T) t[0];
    }

    /**
     * returns many instances of "tClass" (with @Column annotated fields) based on the ResultSet from
     * the executed query.  this returns multiple instances from all the rows.
     *
     * @param conn
     * @param tClass
     * @param sql
     * @param args
     * @param <T>
     * @return
     * @throws SQLException
     */
    public static <T> List<T> getMultiple(Connection conn, final Class<T> tClass, String sql, Object... args) throws SQLException {
        List<T> list = new ArrayList<>();
        executeQuery(conn, sql, rs -> {
            while (rs.next()) {
                list.add(construct(rs, tClass));
            }
        }, args);
        return list;
    }

    /**
     * construct a new instance of "tClass" (with @Column annotated fields) based on the ResultSet.  the "tClass"
     * needs a no-arg constructor.
     * <p>
     * there's a bug (https://bugs.mysql.com/bug.php?id=84084) such that the last column should never be null....
     *
     * @param rs
     * @param tClass
     * @param <T>
     * @return
     * @throws SQLException
     * @see Column
     */
    public static <T> T construct(ResultSet rs, Class<T> tClass) throws SQLException {
        try {
            T t = tClass.newInstance();

            for (Field field : tClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Column.class) || field.isAnnotationPresent(Columns.class)) {
                    for (Annotation annotation : field.getDeclaredAnnotations()) {
                        Class<?> fieldType = field.getType();
                        if (Column.class.equals(annotation.annotationType())) {
                            Column columnAnnotation = (Column) annotation;
                            field.setAccessible(true);
                            String columnName = columnAnnotation.name();

                            if (Integer.class.isAssignableFrom(fieldType) || int.class.isAssignableFrom(fieldType)) {
                                field.set(t, rs.getInt(columnName));
                            } else if (Long.class.isAssignableFrom(fieldType) || long.class.isAssignableFrom(fieldType)) {
                                field.set(t, rs.getLong(columnName));
                            } else if (String.class.isAssignableFrom(fieldType)) {
                                field.set(t, rs.getString(columnName));
                            } else if (Timestamp.class.isAssignableFrom(fieldType)) {
                                field.set(t, rs.getTimestamp(columnName));
                            } else if (Date.class.isAssignableFrom(fieldType)) {
                                field.set(t, rs.getDate(columnName));
                            } else if (ZonedDateTime.class.isAssignableFrom(fieldType)) {
                                Timestamp ts = rs.getTimestamp(columnName);

                                if (ts != null) {
                                    field.set(t, ZonedDateTime.ofInstant(ts.toInstant(), ZoneId.of(columnAnnotation.tz())));
                                }
                            } else if (Float.class.isAssignableFrom(fieldType) || float.class.isAssignableFrom(fieldType)) {
                                field.set(t, rs.getFloat(columnName));
                            } else if (Double.class.isAssignableFrom(fieldType) || double.class.isAssignableFrom(fieldType)) {
                                field.set(t, rs.getDouble(columnName));
                            } else if (Boolean.class.isAssignableFrom(fieldType) || boolean.class.isAssignableFrom(fieldType)) {
                                field.set(t, rs.getInt(columnName) == 1);
                            } else if (BigDecimal.class.isAssignableFrom(fieldType)) {
                                BigDecimal decimal = new BigDecimal(rs.getDouble(columnName));
                                decimal.setScale(columnAnnotation.scale(), BigDecimal.ROUND_HALF_UP);
                                field.set(t, decimal);
                            } else {
                                logger.warn("field \"{}\" of type \"{}\" cannot be handled", field.getName(), fieldType.getName());
                            }
                        } else if (Columns.class.equals(annotation.annotationType())) {
                            field.setAccessible(true);
                            field.set(t, construct(rs, fieldType));
                        }
                    }
                }
            }

            return t;
        } catch (IllegalAccessException | InstantiationException e) {
            throw new SQLException("Error constructing " + tClass.getName(), e);
        }
    }

    public static void executeQuery(Connection conn, String sql, ResultSetHandler handler, Object... args) throws SQLException {
        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            executeQuery(statement, handler, args);
        }
    }

    static String toString(Object... args) {
        StringBuilder sb = new StringBuilder("[");

        if (args != null && args.length > 0) {
            for (int i = 0; i < args.length; i++) {
                if (i > 0) {
                    sb.append(", ");
                }

                if (args[i] == null) {
                    sb.append("NULL");
                } else {
                    sb.append(args[i].toString());
                }
            }
        }

        sb.append("]");
        return sb.toString();
    }

    public static void executeQuery(PreparedStatement statement, ResultSetHandler handler, Object... args) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            setParameter(statement, i + 1, args[i]);
        }

        String sqlToString = null;
        String argsToString = null;

        if (logger.isDebugEnabled()) {
            sqlToString = statement.toString();
            argsToString = toString(args);
            logger.debug("going to execute SQL {} with parameters {}", sqlToString, argsToString);
        }

        try (ResultSet rs = statement.executeQuery()) {
            if (logger.isDebugEnabled()) {
                logger.debug("executed SQL {} with parameters {}", sqlToString, argsToString);
            }

            handler.handle(rs);
        }
    }

    static void setParameter(PreparedStatement statement, int index, Object arg) throws SQLException {
        if (statement.getParameterMetaData().getParameterCount() < index) {
            //quietly ignore extra parameters
            logger.debug("ignoring {} parameter {}", index, arg);
        } else if (arg == null) {
            statement.setObject(index, null);
        } else if (arg instanceof Integer) {
            statement.setInt(index, (Integer) arg);
        } else if (arg instanceof Long) {
            statement.setLong(index, (Long) arg);
        } else if (arg instanceof String) {
            statement.setString(index, (String) arg);
        } else if (arg instanceof Timestamp) {
            statement.setTimestamp(index, (Timestamp) arg);
        } else if (arg instanceof Float) {
            statement.setFloat(index, (Float) arg);
        } else if (arg instanceof Double) {
            statement.setDouble(index, (Double) arg);
        } else if (arg instanceof Boolean) {
            statement.setBoolean(index, (Boolean) arg);
        } else if (arg instanceof Date) {
            statement.setTimestamp(index, new Timestamp(((Date) arg).getTime()));
        } else if (arg instanceof ZonedDateTime) {
            statement.setTimestamp(index, Timestamp.from(((ZonedDateTime) arg).toInstant()));
        } else if (arg instanceof BigDecimal) {
            statement.setFloat(index, ((BigDecimal) arg).floatValue());
        } else {
            throw new IllegalArgumentException("Unknown type " + arg.getClass().getName());
        }
    }

    public static void executeQuery(Connection conn, String sql, ResultSetHandler handler) throws SQLException {
        if (logger.isDebugEnabled()) {
            logger.debug("going to execute SQL {}", sql);
        }

        try (Statement statement = conn.createStatement();
             ResultSet rs = statement.executeQuery(sql)) {
            if (logger.isDebugEnabled()) {
                logger.debug("executed SQL {}", sql);
            }

            handler.handle(rs);
        }
    }

    public static boolean executeUpdate(Connection conn, String sql, Object... args) throws SQLException {
        String argsToString = null;
        int numberRowsAffected;

        try (PreparedStatement statement = conn.prepareStatement(sql)) {
            for (int i = 0; i < args.length; i++) {
                setParameter(statement, i + 1, args[i]);
            }

            if (logger.isDebugEnabled()) {
                argsToString = toString(args);
                logger.debug("going to execute SQL {} with parameters {}", sql, argsToString);
            }

            numberRowsAffected = statement.executeUpdate();

            if (logger.isDebugEnabled()) {
                logger.debug("executed SQL {} with parameters {}", sql, argsToString);
            }

            return 0 < numberRowsAffected;
        }
    }

    public static boolean executeUpdate(PreparedStatement statement, Object... args) throws SQLException {
        for (int i = 0; i < args.length; i++) {
            setParameter(statement, i + 1, args[i]);
        }

        String sqlToString = null;
        String argsToString = null;

        if (logger.isDebugEnabled()) {
            sqlToString = statement.toString();
            argsToString = toString(args);
            logger.debug("going to execute SQL {} with parameters {}", sqlToString, argsToString);
        }

        statement.executeUpdate();

        if (logger.isDebugEnabled()) {
            logger.debug("executed SQL {} with parameters {}", sqlToString, argsToString);
        }

        return true;
    }

    public static ZonedDateTime asZonedInUTC(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        return ZonedDateTime.ofInstant(timestamp.toInstant(), ZoneId.of("UTC"));
    }

    public static <R> R executeSingleCommit(ConnectionProvider provider, JdbcFunction<Connection, R> toExecute) throws SQLException, IOException {
        boolean success = false;
        Connection conn = null;
        try {
            conn = provider.getConnection();
            conn.setAutoCommit(false);
            R result = toExecute.apply(conn);
            success = true;
            return result;
        } finally {
            if (conn != null) {
                if (success) {
                    conn.commit();
                } else {
                    conn.rollback();
                }
                conn.close();
            }
        }
    }

    /**
     * Execute update and return generated key
     */
    public static <T> T executeInsert(Connection conn, String sql, Class<T> generatedKeyType, Object... args) throws SQLException, IOException {
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        executeUpdate(statement, args);
        try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                return generatedKeys.getObject(1, generatedKeyType);
            } else {
                throw new SQLException("Insert failed, no ID obtained.");
            }
        }
    }

    public static <T> void executeInsertByEntity(Connection conn, T entity, String table) throws SQLException, IOException {
        Map<String, Object> namesToValues = getEntityColumnValues(entity, true);
        String names = String.join(",", namesToValues.keySet());
        String values = namesToValues.keySet().stream().map(s -> "?").collect(Collectors.joining(","));
        String sql = "INSERT INTO " + table + " ( " + names + " ) VALUES ( " + values + " )";
        executeUpdate(conn, sql, namesToValues.values().toArray());
    }

    public static <T, K> K executeInsertByEntity(Connection conn, T entity, String table, Class<K> generatedKeyType) throws SQLException, IOException {
        Map<String, Object> namesToValues = getEntityColumnValues(entity, true);
        String names = String.join(",", namesToValues.keySet());
        String values = namesToValues.keySet().stream().map(s -> "?").collect(Collectors.joining(","));
        String sql = "INSERT INTO " + table + " ( " + names + " ) VALUES ( " + values + " )";
        return executeInsert(conn, sql, generatedKeyType, namesToValues.values().toArray());
    }

    public static <T> void executeUpdateByEntity(Connection conn, T entity, String table, String pkColumnName) throws SQLException, IOException {
        Map<String, Object> namesToValues = getEntityColumnValues(entity, false);
        Map<String, Object> namesToValuesWithoutPK = namesToValues.entrySet().stream()
                .filter(e -> !pkColumnName.equals(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        String setStatements = namesToValuesWithoutPK.entrySet().stream()
                .map(e -> e.getKey() + " = ?")
                .collect(Collectors.joining(","));
        String sql = "UPDATE " + table + " SET " + setStatements + " WHERE " + pkColumnName + " = ?";
        List<Object> params = new ArrayList<>();
        params.addAll(namesToValuesWithoutPK.values());
        params.add(namesToValues.get(pkColumnName));
        executeUpdate(conn, sql, params.toArray());
    }

    private static <T> Map<String, Object> getEntityColumnValues(T entity, boolean insertNulls) {
        Map<String, Object> namesToValues = new LinkedHashMap(); //order of elements is important
        for (Field field : entity.getClass().getDeclaredFields()) {
            for (Annotation annotation : field.getDeclaredAnnotations()) {
                if (Column.class.equals(annotation.annotationType())) {
                    try {
                        Column columnAnnotation = (Column) annotation;
                        field.setAccessible(true);
                        String columnName = columnAnnotation.name();
                        Object columnValue = null;
                        if (Boolean.class.equals(field.getType()) || boolean.class.equals(field.getType())) {
                            columnValue = (Boolean) field.get(entity) ? 1 : 0;
                        } else {
                            columnValue = field.get(entity);
                        }
                        if (insertNulls || (columnValue != null)) {
                            namesToValues.put(columnName, columnValue);
                        }

                    } catch (IllegalAccessException | SecurityException e) {
                        logger.error("Can't access entity's field " + field.getName(), e);
                    }
                }
            }
        }
        return namesToValues;
    }
}
