package model;

import mg.ando.dao.annotation.Column;
import mg.ando.dao.annotation.Number;
import mg.ando.dao.annotation.PrimaryKey;
import mg.ando.dao.jdbc.ObjectDB;

public class Personne extends ObjectDB {

    @Column @Number @PrimaryKey(seqName = "s_personne")
    Integer id;

    @Column
    String nom;

    @Column @Number
    Integer age;

    String[] tabNom;

    public Personne() {
    }

    public Personne(Integer id, String nom, Integer age) {
        this.id = id;
        this.nom = nom;
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
