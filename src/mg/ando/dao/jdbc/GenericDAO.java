package mg.ando.dao.jdbc;

import java.sql.*;
import java.util.Vector;
import mg.ando.dao.util.Query;
import mg.ando.dao.util.Utilities;

public class GenericDAO implements InterfaceDAO {
    
    public Vector select(Object obj, Connection connection, String query) {
        Vector result = new Vector();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        try {
            System.out.println("GenericDAO : " + query);
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                result.add(Utilities.createClass(obj, rs));
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                ps.close();
                rs.close();
            } catch (SQLException ex) {
            }
        }
        
        return result;
    }
    
    public void execute(String query, Connection connection) {
        PreparedStatement ps = null;
        try {
            System.out.println("GenericDAO : " + query);
            ps = connection.prepareStatement(query);
            ps.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            try {
                ps.close();
            } catch (SQLException ex) {
                System.out.println(ex);
            }
        }
    }
    
    @Override
    public Vector select(Object obj, Connection connection) {
        String query = "";
        try {
            query = Query.select(obj);
        } catch (Exception ex) {
            System.out.println(ex);
        }
        
        return select(obj, connection, query);
    }
    
    @Override
    public void insert(Object obj, Connection connection) {
        String query = "";
        try {
            query = Query.insert(obj);
            execute(query, connection);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    @Override
    public void delete(Object obj, Connection connection) {
        String query = "";
        try {
            query = Query.delete(obj);
            execute(query, connection);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
    @Override
    public void update(Object obj, Connection connection, String[] columnsIdentifiers) {
        String query = "";
        try {
            query = Query.update(obj, columnsIdentifiers);
            execute(query, connection);
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }
    
}
