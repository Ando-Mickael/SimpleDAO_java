package model;

import mg.ando.dao.annotation.Column;
import mg.ando.dao.annotation.PrimaryKey;
import mg.ando.dao.annotation.Table;
import mg.ando.dao.jdbc.ObjectDB;

@Table(name = "spectacle")
public class Spectacle extends ObjectDB {

    @PrimaryKey(autoIncrement = true, sequenceName = "spectacle_id_seq")
    @Column(name = "id", isNumber = true)
    Integer id;

    @Column(name = "titre", isNumber = false)
    String titre;

    @Column(name = "date_prestation", isNumber = false)
    String date_prestation;

    @Column(name = "prix_lieu", isNumber = true)
    Float prix_lieu;

    @Column(name = "lieuid", isNumber = true)
    Integer lieuid;

    public Spectacle() {
    }

    public Spectacle(Integer id, String titre, String date_prestation, Float prix_lieu, Integer lieuid) {
        this.id = id;
        this.titre = titre;
        this.date_prestation = date_prestation;
        this.prix_lieu = prix_lieu;
        this.lieuid = lieuid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getDate_prestation() {
        return date_prestation;
    }

    public void setDate_prestation(String date_prestation) {
        this.date_prestation = date_prestation;
    }

    public Float getPrix_lieu() {
        return prix_lieu;
    }

    public void setPrix_lieu(Float prix_lieu) {
        this.prix_lieu = prix_lieu;
    }

    public Integer getLieuid() {
        return lieuid;
    }

    public void setLieuid(Integer lieuid) {
        this.lieuid = lieuid;
    }

    @Override
    public String toString() {
        return "Spectacle{" + "id=" + id + ", titre=" + titre + ", date_prestation=" + date_prestation + ", prix_lieu=" + prix_lieu + ", lieuid=" + lieuid + '}';
    }

}
