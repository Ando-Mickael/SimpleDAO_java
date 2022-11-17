package mg.ando.dao.jdbc;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import mg.ando.dao.annotation.Column;
import mg.ando.dao.annotation.PrimaryKey;
import mg.ando.dao.util.Utilities;

public class ObjectDB {

    // select
    String selectQuery(Object obj) {
        String result = "select * from " + Utilities.getTableName(obj);
        String where = " where 1 = 1 ";
        Field[] tabFields = obj.getClass().getDeclaredFields();

        for (int i = 0; i < tabFields.length; i++) {
            Field field = tabFields[i];

            if (field.isAnnotationPresent(Column.class)) {
                String columnName = Utilities.getColumName(field);
                String fieldName = field.getName();
                Object resultGetter = Utilities.resultGetter(obj, fieldName);

                if (resultGetter != null) {
                    String resultGetterStr = resultGetter.toString();
                    char[] resultGetterCharArray = resultGetterStr.toCharArray();

                    where += " and (" + columnName;

                    if (resultGetterCharArray[0] == '%' || resultGetterCharArray[resultGetterStr.length() - 1] == '%') {
                        where += " like ";
                    } else {
                        where += " = ";
                    }

                    if (field.getAnnotation(Column.class).isNumber()) {
                        where += resultGetter;
                    } else {
                        where += Utilities.toString(resultGetter.toString());
                    }

                    where += ")";
                }
            }
        }

        result += where;

        return result;
    }

    public Vector select(Connection connection) throws SQLException {
        Vector result = new Vector();
        String query = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        query = selectQuery(this);
        System.out.println(query);
        ps = connection.prepareStatement(query);
        rs = ps.executeQuery();
        while (rs.next()) {
            result.addElement(Utilities.createClass(this, rs));
        }

        return result;
    }

    public static Object select(Connection connection, String sql) throws SQLException {
        Object result = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        ps = connection.prepareStatement(sql);
        rs = ps.executeQuery();
        if (rs.next()) {
            result = rs.getObject(1);
        }

        return result;
    }

    // insert
    String insertQuery(Object obj) {
        String result = "insert into " + Utilities.getTableName(obj);
        Field[] tabFields = obj.getClass().getDeclaredFields();
        String columns = " (";
        String values = " (";

        for (int i = 0; i < tabFields.length; i++) {
            Field field = tabFields[i];

            if (field.isAnnotationPresent(Column.class)) {
                String columnName = Utilities.getColumName(field);
                String fieldName = field.getName();
                Object resultGetter = Utilities.resultGetter(obj, fieldName);
                if (resultGetter == null) {
                    resultGetter = "null";
                }

                columns += columnName;

                if (field.isAnnotationPresent(PrimaryKey.class)) {

                    if (!field.getAnnotation(Column.class).isNumber()) {
                        values += "concat(" + Utilities.toString(field.getAnnotation(PrimaryKey.class).prefix()) + ",";
                    }
                    values += " nextval(" + Utilities.toString(field.getAnnotation(PrimaryKey.class).seqName()) + ")";
                    if (!field.getAnnotation(Column.class).isNumber()) {
                        values += ")";
                    }

                } else {
                    if (field.getAnnotation(Column.class).isNumber() || resultGetter.equals("null")) {
                        values += resultGetter;
                    } else {
                        values += Utilities.toString(resultGetter.toString());
                    }
                }

                if (i < (tabFields.length - 1)) {
                    columns += ", ";
                    values += ", ";
                }
            }
        }
        columns += ")";
        values += ")";

        result += columns + " values " + values;

        return result;
    }

    public void insert(Connection connection) throws SQLException {
        String query = null;
        PreparedStatement ps = null;

        query = insertQuery(this);
        System.out.println(query);
        ps = connection.prepareStatement(query);

        ps.executeUpdate();
    }

    // update
    String set(Object obj) {
        String result = " set ";
        Field[] tabFields = obj.getClass().getDeclaredFields();

        for (int i = 0; i < tabFields.length; i++) {
            Field field = tabFields[i];

            if (field.isAnnotationPresent(Column.class)) {
                String columnName = Utilities.getColumName(field);
                String fieldName = field.getName();
                Object resultGetter = Utilities.resultGetter(obj, fieldName);

                result += columnName + " = ";

                if (field.getAnnotation(Column.class).isNumber()) {
                    result += resultGetter.toString();
                } else {
                    result += Utilities.toString(resultGetter.toString());
                }

                if (i < tabFields.length - 1) {
                    result += ", ";
                }
            }
        }

        return result;
    }

    static String where(Object obj, String[] tabFieldName) {
        String result = " where 1 = 1 ";

        for (int i = 0; i < tabFieldName.length; i++) {
            Field field = Utilities.getFieldByName(obj, tabFieldName[i]);
            Object resultGetter = Utilities.resultGetter(obj, tabFieldName[i]);

            result += " and " + tabFieldName[i] + " = ";

            if (field.getAnnotation(Column.class).isNumber()) {
                result += resultGetter.toString();
            } else {
                result += Utilities.toString(resultGetter.toString());
            }
        }

        return result;
    }

    String updateQuery(Object obj, String[] tabFieldNameCondition) {
        return ("update " + Utilities.getTableName(obj) + set(obj) + where(obj, tabFieldNameCondition));
    }

    public void update(Connection connection, String[] tabFieldNameCondition) throws SQLException {
        String query = null;
        PreparedStatement ps = null;

        query = updateQuery(this, tabFieldNameCondition);
        System.out.println(query);
        ps = connection.prepareStatement(query);

        ps.executeUpdate();
    }

    // delete
    String deleteQuery(Object obj, String[] tabFieldNameCondition) {
        return ("delete from " + Utilities.getTableName(obj) + where(obj, tabFieldNameCondition));
    }

    public void delete(Connection connection, String[] tabFieldNameCondition) throws SQLException {
        String query = null;
        PreparedStatement ps = null;

        query = deleteQuery(this, tabFieldNameCondition);
        System.out.println(query);
        ps = connection.prepareStatement(query);

        ps.executeUpdate();
    }
}
