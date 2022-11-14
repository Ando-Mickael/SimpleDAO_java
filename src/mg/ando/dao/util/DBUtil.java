package mg.ando.dao.util;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Vector;

import mg.ando.dao.annot.Column;
import mg.ando.dao.annot.Table;

public class DBUtil {

	public static String getTableName(Object obj) {
		String result = new String();

		if (obj.getClass().isAnnotationPresent(Table.class)) {
			result = obj.getClass().getAnnotation(Table.class).name();
		} else {
			result = obj.getClass().getSimpleName();
		}

		return result;
	}

	public static String getColumName(Field field) {
		String result = null;

		if (field.isAnnotationPresent(Column.class)) {
			result = field.getAnnotation(Column.class).name();
		} else {
			result = field.getName();
		}

		return result;
	}

	public static boolean isNumber(Field field) {
		boolean result = false;

		if (field.isAnnotationPresent(Column.class)) {
			result = field.getAnnotation(Column.class).isNumber();
		}

		return result;
	}

	public static Vector<String> getVectColumn(Object obj) {
		Vector<String> result = new Vector<>();
		Field[] tabFields = obj.getClass().getDeclaredFields();

		for (Field field : tabFields) {
			if (field.isAnnotationPresent(Column.class)) {
				result.addElement(field.getName());
			}
		}

		return result;
	}

	public static String toString(String word) {
		String result = new String();

		result = "'" + word + "'";

		return result;
	}

	private static String myCapitalize(String word) {
		String result = null;

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
                String colName = null;
                String methodName = null;
                Object resultRS = null;
                String fieldTypeName = null;

                colName = field.getName();
                methodName = createSetter(colName);
                resultRS = rs.getString(colName);
                fieldTypeName = field.getType().getSimpleName();

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
