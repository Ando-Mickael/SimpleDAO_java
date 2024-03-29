package mg.ando.dao.jdbc;

import java.sql.*;

public class MyConnection {

    public MyConnection() {
    }

    public static Connection createPostgresqlConnection(String host, String port, String dbname, String user, String passwd) {
        Connection result = null;
        String strConn = "jdbc:postgresql://" + host + ":" + port + "/" + dbname;

        try {
            Class.forName("org.postgresql.Driver");
            result = DriverManager.getConnection(strConn, user, passwd);
        } catch (Exception e) {
            System.out.println(e);
        }

        return result;
    }


}
