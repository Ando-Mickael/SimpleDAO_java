package main;

import java.sql.Connection;
import java.util.Vector;
import mg.ando.dao.jdbc.MyConnection;
import model.Spectacle;

public class Main {

    public static void main(String[] args) throws Exception {
        Connection connection = MyConnection.pgConnection("localhost", "5432", "eval2", "ando", "ando");

//        Vector<Spectacle> v = new Spectacle().select(connection);
//        System.out.println(v.size());
//        
//        for (Spectacle tmp : v) {
//            System.out.println(tmp.toString());
//        }

        Spectacle s = (Spectacle) new Spectacle(4, null, null, null, null).select(connection).get(0);
        s.setTitre("aaa");
        s.update(connection, new String[]{"id"});
        System.out.println(s.toString());

    }
}
