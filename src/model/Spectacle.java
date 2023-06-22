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
    String datePrestation;

    @Column(name = "prix_lieu", isNumber = true)
    Float prixLieu;

    @Column(name = "lieuid", isNumber = true)
    Integer lieuid;

    public Spectacle(Integer id, String titre, String datePrestation, Float prixLieu, Integer lieuid) {
        this.id = id;
        this.titre = titre;
        this.datePrestation = datePrestation;
        this.prixLieu = prixLieu;
        this.lieuid = lieuid;
    }

    public Spectacle() {
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

    public String getDatePrestation() {
        return datePrestation;
    }

    public void setDatePrestation(String datePrestation) {
        this.datePrestation = datePrestation;
    }

    public Float getPrixLieu() {
        return prixLieu;
    }

    public void setPrixLieu(Float prixLieu) {
        this.prixLieu = prixLieu;
    }

    public Integer getLieuid() {
        return lieuid;
    }

    public void setLieuid(Integer lieuid) {
        this.lieuid = lieuid;
    }

}
