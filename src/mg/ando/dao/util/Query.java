package mg.ando.dao.util;

import java.lang.reflect.Field;
import java.util.Vector;
import mg.ando.dao.annotation.PrimaryKey;

public class Query {
    
    private static String whereOneCol(Object obj, Field field) throws Exception {
        String result = "";
        String colName = Utilities.getColumnName(field);
        Object resultGetter = Utilities.resultGetter(obj, field.getName());
        
        if (resultGetter != null) {
            String resultGetterStr = resultGetter.toString();
            char[] resultGetterCharArray = resultGetterStr.toCharArray();
            
            result += " and (" + colName;
            
            if (resultGetterCharArray[0] == '%' || resultGetterCharArray[resultGetterStr.length() - 1] == '%') {
                result += " like ";
            } else {
                result += " = ";
            }
            
            if (Utilities.isNumber(field)) {
                result += resultGetter;
            } else {
                result += Utilities.toString(resultGetter.toString());
            }
            
            result += ")";
        }
        
        return result;
    }
    
    public static String where(Object obj, String[] tabFieldName) throws Exception {
        String result = " where (1 = 1)";
        
        if (tabFieldName != null) {
            for (int i = 0; i < tabFieldName.length; i++) {
                Field field = Utilities.getFieldByName(obj, tabFieldName[i]);
                result += whereOneCol(obj, field);
            }
        } else {
            Vector<Field> vectCol = Utilities.getVectColumn(obj);
            
            for (Field field : vectCol) {
                result += whereOneCol(obj, field);
            }
        }
        
        return result;
    }
    
    private static String set(Object obj) throws Exception {
        String result = " set ";
        Vector<Field> vectColumn = Utilities.getVectColumn(obj);
        
        for (int i = 0; i < vectColumn.size(); i++) {
            Field field = vectColumn.get(i);
            Object resultGetter = Utilities.resultGetter(obj, field.getName());
            if (resultGetter != null) {
                
                result += Utilities.getColumnName(field) + " = ";
                
                if (Utilities.isNumber(field)) {
                    result += resultGetter.toString();
                } else {
                    result += Utilities.toString(resultGetter.toString());
                }
                
                if (i < vectColumn.size() - 1) {
                    result += ", ";
                }
            }
        }
        
        return result;
    }
    
    public static String insert(Object obj) throws Exception {
        String result = "insert into " + Utilities.getTableName(obj);
        Vector<Field> vectField = Utilities.getVectColumn(obj);
        String columns = " (";
        String values = " (";
        
        for (int i = 0; i < vectField.size(); i++) {
            Field field = vectField.elementAt(i);
            String columnName = Utilities.getColumnName(field);
            String fieldName = field.getName();
            Object resultGetter = (Utilities.resultGetter(obj, fieldName) != null) ? Utilities.resultGetter(obj, fieldName) : "null";
            
            columns += columnName;
            
            if (field.isAnnotationPresent(PrimaryKey.class)) {
                
                if (field.getAnnotation(PrimaryKey.class).autoIncrement()) {
                    values += "default";
                } else {
                    if (!Utilities.isNumber(field)) {
                        values += "concat(" + Utilities.toString(field.getAnnotation(PrimaryKey.class).prefixID()) + ",";
                    }
                    values += " nextval(" + Utilities.toString(field.getAnnotation(PrimaryKey.class).sequenceName()) + ")";
                    if (!Utilities.isNumber(field)) {
                        values += ")";
                    }
                }
                
            } else {
                
                if (Utilities.isNumber(field) || resultGetter.equals("null")) {
                    values += resultGetter;
                } else {
                    values += Utilities.toString(resultGetter.toString());
                }
                
            }
            
            if (i < (vectField.size() - 1)) {
                columns += ", ";
                values += ", ";
            }
            
        }
        
        columns += ")";
        values += ")";
        
        result += columns + " values " + values;
        
        return result;
    }
    
    public static String select(Object obj) throws Exception {
        return ("select * from " + Utilities.getTableName(obj) + where(obj, null));
    }
    
    public static String update(Object obj, String[] tabFieldName) throws Exception {
        return ("update " + Utilities.getTableName(obj) + set(obj) + where(obj, tabFieldName));
    }
    
    public static String delete(Object obj) throws Exception {
        return ("delete from " + Utilities.getTableName(obj) + where(obj, null));
    }
    
}
