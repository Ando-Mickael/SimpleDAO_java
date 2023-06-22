package main;

import java.sql.Connection;
import java.util.Vector;
import mg.ando.dao.jdbc.MyConnection;
import model.Spectacle;

public class Main {

    public static void main(String[] args) throws Exception {
        Connection connection = MyConnection.createPostgresqlConnection("localhost", "5432", "eval2", "ando", "ando");

//        Vector<Spectacle> v = new Spectacle().select(connection);
//        System.out.println(v.size());
//        
//        for (Spectacle tmp : v) {
//            System.out.println(tmp.toString());
//        }

        Vector<Spectacle> spectacles = new Spectacle(null, null, "2020-01-01 0:00:00", Float.valueOf(1000), null).select(connection);

    }
}
