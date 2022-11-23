package main;

import mg.ando.dao.jdbc.ObjectDB;
import model.Personne;

public class Main {
    public static void main(String[] args) {
        Personne p = new Personne(null, "ando", 18);
        System.out.println(ObjectDB.insertQuery(p));
    }
}
