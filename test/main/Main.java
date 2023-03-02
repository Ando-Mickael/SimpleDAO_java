package main;

import java.sql.Connection;
import java.util.Vector;
import mg.ando.dao.jdbc.GenericDAO;
import mg.ando.dao.jdbc.MyConnection;
import model.Personne;

public class Main {

    public static void main(String[] args) throws Exception {
        GenericDAO dao = new GenericDAO();
        Connection connection = MyConnection.pgConnection("localhost", "5432", "test", "ando", "ando");
        Personne p = new Personne(null, "%ando", 18);
        dao.delete(new Personne(2, "Mick", 20), connection);
        Vector<Personne> vect = dao.select(new Personne(), connection);
        System.out.println(vect.size());
    }
}
