package mg.ando.dao.util;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Vector;

import mg.ando.dao.annotation.Column;
import mg.ando.dao.annotation.Table;

public class Utilities {

    public static String getTableName(Object obj) {
        if (obj.getClass().isAnnotationPresent(Table.class)) {
            return (obj.getClass().getAnnotation(Table.class).name());
        }
        return (obj.getClass().getSimpleName());
    }

    public static String getColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).name().equals("")) {
            return (field.getAnnotation(Column.class).name());
        }
        return (field.getName());
    }

    public static Vector<Field> getVectColumn(Object obj) {
        Vector<Field> result = new Vector<>();
        Field[] tabField = obj.getClass().getDeclaredFields();

        for (Field field : tabField) {
            if (field.isAnnotationPresent(Column.class)) {
                result.addElement(field);
            }
        }
        return result;
    }

    public static boolean isNumber(Field field) {
        return (field.getAnnotation(Column.class).number());
    }

    public static String toString(String word) {
        return (String.format("'%s'", word));
    }

    private static String myCapitalize(String word) {
        String firstChar = word.substring(0, 1);
        return (firstChar.toUpperCase() + word.substring(1, word.length()));
    }

    public static String createGetter(String fieldName) {
        return ("get" + myCapitalize(fieldName));
    }

    public static String createSetter(String fieldName) {
        return ("set" + myCapitalize(fieldName));
    }

    public static Object resultGetter(Object obj, String fieldName) throws Exception {
        String methodName = createGetter(fieldName);
        return (obj.getClass().getMethod(methodName).invoke(obj));
    }

    public static Object resultGetter(Object obj, Field field) throws Exception {
        String methodName = createGetter(field.getName());
        return (obj.getClass().getMethod(methodName).invoke(obj));
    }

    public static Field getFieldByName(Object obj, String fieldName) {
        Field[] tabField = obj.getClass().getDeclaredFields();

        for (Field field : tabField) {
            String tmpName = field.getName();

            if (fieldName.equalsIgnoreCase(tmpName)) {
                return field;
            }
        }
        return null;
    }

    public static Object createClass(Object obj, ResultSet rs) throws Exception {
        Object result = obj.getClass().newInstance();
        Vector<Field> vectField = getVectColumn(obj);

        for (Field field : vectField) {
            String fieldName = field.getName();
            String setterName = createSetter(fieldName);
            Object rsValue = rs.getString(fieldName);
            String fieldTypeName = field.getType().getSimpleName();

            if (fieldTypeName.equalsIgnoreCase("Integer")) {
                rsValue = Integer.parseInt((String) rsValue);
            } else if (fieldTypeName.equalsIgnoreCase("Double")) {
                rsValue = Double.parseDouble((String) rsValue);
            } else if (fieldTypeName.equalsIgnoreCase("Float")) {
                rsValue = Float.parseFloat((String) rsValue);
            } else {
                rsValue = (String) rsValue;
            }

            result.getClass().getMethod(setterName, field.getType()).invoke(result, rsValue);
        }

        return result;
    }

}
