package com.tp4.entities;

import javax.persistence.*;
import java.time.Year;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Vehicule {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "vehicule_seq")
    @SequenceGenerator(name = "vehicule_seq", sequenceName = "vehicule_seq", allocationSize = 1)
    private Long id;

    @Column(nullable = false, length = 50)
    private String marque;

    @Column(nullable = false, length = 50)
    private String modele;

    @Column(name = "annee_fabrication", nullable = false)
    private int anneeFabrication;

    protected Vehicule() {
    }

    protected Vehicule(String marque, String modele, int anneeFabrication) {
        this.marque = marque;
        this.modele = modele;
        this.anneeFabrication = anneeFabrication;
    }

    public int calculerAge() {
        return Year.now().getValue() - anneeFabrication;
    }

    public Long getId() {
        return id;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public int getAnneeFabrication() {
        return anneeFabrication;
    }

    public void setAnneeFabrication(int a) {
        this.anneeFabrication = a;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "[id=" + id + ", " + marque + " " + modele
                + ", " + anneeFabrication + ", age=" + calculerAge() + " ans]";
    }
}