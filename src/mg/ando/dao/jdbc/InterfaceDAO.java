package mg.ando.dao.jdbc;

import java.sql.Connection;
import java.util.Vector;

public interface InterfaceDAO {

    public Vector select(Object obj, Connection connection);
    public void insert(Object obj, Connection connection);
    public void delete(Object obj, Connection connection);
    public void update(Object obj, Connection connection, String[] tabFieldName);
    
}
