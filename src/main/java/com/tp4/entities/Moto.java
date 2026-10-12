package com.tp4.entities;

import javax.persistence.*;

@Entity
@Table(name = "motos")
public class Moto extends Vehicule {

    @Column(nullable = false)
    private Integer cylindree;

    @Column(name = "avec_sidecar", nullable = false)
    private Boolean avecSidecar;

    protected Moto() {
    }

    public Moto(String marque, String modele, int annee, Integer cylindree, Boolean avecSidecar) {
        super(marque, modele, annee);
        this.cylindree = cylindree;
        this.avecSidecar = avecSidecar;
    }

    public Integer getCylindree() {
        return cylindree;
    }

    public void setCylindree(Integer c) {
        this.cylindree = c;
    }

    public Boolean getAvecSidecar() {
        return avecSidecar;
    }

    public void setAvecSidecar(Boolean b) {
        this.avecSidecar = b;
    }
}