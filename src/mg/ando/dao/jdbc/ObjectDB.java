package mg.ando.dao.jdbc;

import java.sql.Connection;
import java.util.Vector;

public class ObjectDB {

    GenericDAO dao;
    
    public Vector select(Connection connection) {
        return dao.select(this, connection);
    }
    
    public void insert(Connection connection) {
        dao.insert(this, connection);
    }
    
    public void delete(Connection connection) {
        dao.delete(this, connection);
    }
    
    public void update(Connection connection, String[] tabFieldName) {
        dao.update(this, connection, tabFieldName);
    }

}
