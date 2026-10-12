package com.tp4;

import com.tp4.entities.*;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("tp4-pu");
        EntityManager em = emf.createEntityManager();

        try {
            // ---------- CREATE ----------
            em.getTransaction().begin();
            Voiture v = new Voiture("Renault", "Clio", 2019, 5, "Essence");
            Moto m = new Moto("Yamaha", "MT-07", 2021, 689, false);
            Camion c = new Camion("Volvo", "FH16", 2017, 25.5, 3);
            em.persist(v);
            em.persist(m);
            em.persist(c);
            em.getTransaction().commit();
            System.out.println("\n>>> CREATE : 3 véhicules insérés");

            // ---------- READ (requête polymorphe : UNION ALL) ----------
            List<Vehicule> tous = em.createQuery("SELECT v FROM Vehicule v", Vehicule.class).getResultList();
            System.out.println("\n>>> READ : tous les véhicules");
            tous.forEach(System.out::println);

            // ---------- READ (requête sur une sous-classe : table unique) ----------
            List<Moto> motos = em.createQuery("SELECT m FROM Moto m WHERE m.cylindree > :seuil", Moto.class).setParameter("seuil", 500).getResultList();
            System.out.println("\n>>> READ : motos > 500 cc : " + motos.size());

            // ---------- READ par id (UNION ALL aussi) ----------
            em.clear(); // vide le cache de 1er niveau pour forcer un vrai SELECT
            Vehicule trouve = em.find(Vehicule.class, v.getId());
            System.out.println("\n>>> find() renvoie le type réel : " + trouve.getClass().getSimpleName());

            // ---------- UPDATE ----------
            em.getTransaction().begin();
            Camion camionAModifier = em.find(Camion.class, c.getId());
            camionAModifier.setChargeUtileTonnes(30.0);
            em.getTransaction().commit();
            System.out.println("\n>>> UPDATE : " + em.find(Camion.class, c.getId()).getChargeUtileTonnes() + " t");

            // ---------- COMPTAGE PAR TYPE ----------
            List<Object[]> stats = em.createQuery("SELECT TYPE(v), COUNT(v) FROM Vehicule v GROUP BY TYPE(v)", Object[].class).getResultList();
            System.out.println("\n>>> Statistiques par type :");
            for (Object[] ligne : stats) {
                System.out.println("   " + ((Class<?>) ligne[0]).getSimpleName() + " -> " + ligne[1]);
            }

            // ---------- Tables créées par Hibernate ----------
            System.out.println("\n>>> Tables générées :");
            List<?> tables = em.createNativeQuery("SELECT TABLE_NAME FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = 'PUBLIC' ORDER BY TABLE_NAME").getResultList();
            tables.forEach(t -> System.out.println("   - " + t));

            // ---------- Les attributs hérités sont dupliqués dans chaque table ----------
            System.out.println("\n>>> Table voitures (attributs hérités + spécifiques) :");
            em.createNativeQuery("SELECT id, marque, modele, annee_fabrication, nombre_portes FROM voitures").getResultList().forEach(l -> System.out.println("   " + Arrays.toString((Object[]) l)));

            System.out.println("\n>>> Table camions (attributs hérités + spécifiques) :");
            em.createNativeQuery("SELECT id, marque, modele, charge_utile_tonnes, nombre_essieux FROM camions").getResultList().forEach(l -> System.out.println("   " + Arrays.toString((Object[]) l)));

            // ---------- Les ids sont uniques sur toute la hiérarchie ----------
            System.out.println("\n>>> Ids générés : Voiture=" + v.getId() + ", Moto=" + m.getId() + ", Camion=" + c.getId());

            // ---------- Requête sur une sous-classe : observe le SQL généré ----------
            System.out.println("\n>>> Camions de plus de 20 tonnes :");
            em.createQuery("SELECT c FROM Camion c WHERE c.chargeUtileTonnes > :t", Camion.class).setParameter("t", 20.0).getResultList().forEach(System.out::println);

            // ---------- DELETE ----------
            em.getTransaction().begin();
            em.remove(em.find(Moto.class, m.getId()));
            em.getTransaction().commit();
            long restants = em.createQuery("SELECT COUNT(v) FROM Vehicule v", Long.class).getSingleResult();
            System.out.println("\n>>> DELETE : véhicules restants = " + restants);

        } finally {
            em.close();
            emf.close();
        }
    }
}