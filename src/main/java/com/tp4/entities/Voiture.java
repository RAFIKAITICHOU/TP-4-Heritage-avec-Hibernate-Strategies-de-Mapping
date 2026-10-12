package com.tp4.entities;

import javax.persistence.*;

@Entity
@Table(name = "voitures")
public class Voiture extends Vehicule {

    @Column(name = "nombre_portes", nullable = false)
    private Integer nombrePortes;

    @Column(name = "type_carburant", length = 20, nullable = false)
    private String typeCarburant;

    protected Voiture() {
    }

    public Voiture(String marque, String modele, int annee, Integer nombrePortes, String typeCarburant) {
        super(marque, modele, annee);
        this.nombrePortes = nombrePortes;
        this.typeCarburant = typeCarburant;
    }

    public Integer getNombrePortes() {
        return nombrePortes;
    }

    public void setNombrePortes(Integer n) {
        this.nombrePortes = n;
    }

    public String getTypeCarburant() {
        return typeCarburant;
    }

    public void setTypeCarburant(String t) {
        this.typeCarburant = t;
    }
}