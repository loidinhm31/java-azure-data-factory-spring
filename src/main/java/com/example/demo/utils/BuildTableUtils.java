package com.example.demo.utils;

import org.mybatis.dynamic.sql.SqlColumn;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.*;

public class BuildTableUtils {
    public static List<String> getActualTypeForSqlColumn(Class<?> clazz) {
        List<String> types = new ArrayList<>();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            types.add(((ParameterizedType) field.getGenericType()).getActualTypeArguments()[0].getTypeName());
        }
        return types;
    }

    public static String convertToSqlType(String javaType) {
        switch (javaType) {
            case "BIGINT":
                return "BIGINT";
            case "INTEGER":
                return "INT";
            case "VARCHAR":
                return "NVARCHAR(256)";
            case "DATE":
                return "DATETIME2(3)";
            default:
                throw new RuntimeException(javaType);
        }
    }

    public static Map<String, String> getFieldType(Object object, List<String> types) throws IllegalAccessException {
        Map<String, String> map = new LinkedHashMap<>();

        Field[] fields = object.getClass().getDeclaredFields();

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);

            String type = types.get(i);

            switch (type) {
                case "java.lang.Long":
                    SqlColumn<Long> longValue = (SqlColumn<Long>) fields[i].get(object);
                    map.put(longValue.name(), longValue.jdbcType().isPresent() ? longValue.jdbcType().get().getName() : "");
                    break;
                case "java.lang.Integer":
                    SqlColumn<Integer> intValue = (SqlColumn<Integer>) fields[i].get(object);
                    map.put(intValue.name(), intValue.jdbcType().isPresent() ? intValue.jdbcType().get().getName() : "");
                    break;
                case "java.lang.String":
                    SqlColumn<String> stringValue = (SqlColumn<String>) fields[i].get(object);
                    map.put(stringValue.name(), stringValue.jdbcType().isPresent() ? stringValue.jdbcType().get().getName() : "");
                    break;
                case "java.util.Date":
                    SqlColumn<Date> dateValue = (SqlColumn<Date>) fields[i].get(object);
                    map.put(dateValue.name(), dateValue.jdbcType().isPresent() ? dateValue.jdbcType().get().getName() : "");
                    break;
                default:
                    throw new RuntimeException("Cannot mapping field in statement");
            }

        }

        return map;
    }
}
