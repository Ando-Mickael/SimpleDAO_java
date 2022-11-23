package mg.ando.dao.util;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Vector;

import mg.ando.dao.annotation.Column;
import mg.ando.dao.annotation.Table;
import mg.ando.dao.annotation.Number;

public class Utilities {

    public static String getTableName(Object obj) {
        String result = new String();

        if (obj.getClass().isAnnotationPresent(Table.class)) {
            result = obj.getClass().getAnnotation(Table.class).name();
        } else {
            result = obj.getClass().getSimpleName();
        }

        return result;
    }

    public static Vector<Field> getVectColumns(Object obj) {
        Vector<Field> result = new Vector<>();
        Field[] tabFields = obj.getClass().getDeclaredFields();

        for (Field field : tabFields) {
            if (field.isAnnotationPresent(Column.class)) {
                result.addElement(field);
            }
        }

        return result;
    }
    
    public static String getColumnName(Field field) {
        String result = new String();

        if (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).name().equals("")) {
            result = field.getAnnotation(Column.class).name();
        } else {
            result = field.getName();
        }

        return result;
    }

    public static boolean isNumber(Field field) {
        return field.isAnnotationPresent(Number.class);
    }

    public static String toString(String word) {
        return ("'" + word + "'");
    }

    private static String myCapitalize(String word) {
        String result = new String();
        String firstChar = word.substring(0, 1);
        
        result = firstChar.toUpperCase() + word.substring(1, word.length());

        return result;
    }

    public static String createGetter(String colName) {
        return ("get" + myCapitalize(colName));
    }

    public static String createSetter(String colName) {
        return ("set" + myCapitalize(colName));
    }

    public static Object resultGetter(Object obj, String columnName) {
        Object result = null;
        String methodName = createGetter(columnName);

        try {
            result = obj.getClass().getMethod(methodName).invoke(obj);
        } catch (Exception e) {
            System.out.println(e);
        }

        return result;
    }

    public static Field getFieldByName(Object obj, String fieldName) {
        Field result = null;
        Field[] tabFields = obj.getClass().getDeclaredFields();

        for (Field field : tabFields) {
            String tmpName = field.getName();
            
            if (fieldName.equalsIgnoreCase(tmpName)) {
                result = field;
                break;
            }
        }

        return result;
    }

    public static Object createClass(Object obj, ResultSet rs) {
        Object result = null;
        Field[] tabFields = null;

        try {
            result = obj.getClass().newInstance();
            tabFields = obj.getClass().getDeclaredFields();

            for (Field field : tabFields) {
                String colName = field.getName();
                String methodName = createSetter(colName);
                Object resultRS = rs.getString(colName);;
                String fieldTypeName = field.getType().getSimpleName();

                if (fieldTypeName.equalsIgnoreCase("Integer")) {
                    resultRS = Integer.parseInt((String) resultRS);
                } else if (fieldTypeName.equalsIgnoreCase("Double")) {
                    resultRS = Double.parseDouble((String) resultRS);
                } else if (fieldTypeName.equalsIgnoreCase("Float")) {
                    resultRS = Float.parseFloat((String) resultRS);
                } else {
                    resultRS = (String) resultRS;
                }

                result.getClass().getMethod(methodName, field.getType()).invoke(result, resultRS);
            }

        } catch (Exception e) {
        }

        return result;
    }
    
}
