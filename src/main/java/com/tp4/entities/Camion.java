package com.tp4.entities;

import javax.persistence.*;

@Entity
@Table(name = "camions")
public class Camion extends Vehicule {

    @Column(name = "charge_utile_tonnes", nullable = false)
    private Double chargeUtileTonnes;

    @Column(name = "nombre_essieux", nullable = false)
    private Integer nombreEssieux;

    protected Camion() {
    }

    public Camion(String marque, String modele, int annee, Double chargeUtileTonnes, Integer nombreEssieux) {
        super(marque, modele, annee);
        this.chargeUtileTonnes = chargeUtileTonnes;
        this.nombreEssieux = nombreEssieux;
    }

    public Double getChargeUtileTonnes() {
        return chargeUtileTonnes;
    }

    public void setChargeUtileTonnes(Double c) {
        this.chargeUtileTonnes = c;
    }

    public Integer getNombreEssieux() {
        return nombreEssieux;
    }

    public void setNombreEssieux(Integer n) {
        this.nombreEssieux = n;
    }
}