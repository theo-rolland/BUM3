package eus.ehu.data_access;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import eus.ehu.domain.Pilot;
import java.util.List;

public class DbAccessManager {
    private EntityManagerFactory emf;
    private EntityManager db;

    public DbAccessManager() {
        emf = Persistence.createEntityManagerFactory("formula1PU");
        db = emf.createEntityManager();
    }

    // Sauvegarder un pilote (vérifie l'existence pour éviter les doublons)
    public void savePilot(Pilot pilot) {
        if (getPilotByName(pilot.getName()) != null) {
            System.out.println("Le pilote " + pilot.getName() + " existe déjà.");
            return;
        }
        db.getTransaction().begin();
        db.persist(pilot);
        db.getTransaction().commit();
        System.out.println(pilot + " a été ajouté à la base de données.");
    }

    // Récupérer tous les pilotes
    public List<Pilot> getAllPilots() {
        TypedQuery<Pilot> query = db.createQuery("SELECT p FROM Pilot p", Pilot.class);
        return query.getResultList();
    }

    // Récupérer des pilotes par nationalité
    public List<Pilot> getPilotsByNationality(String nat) {
        TypedQuery<Pilot> query = db.createQuery("SELECT p FROM Pilot p WHERE p.nationality = :nat", Pilot.class);
        query.setParameter("nat", nat);
        return query.getResultList();
    }

    // Récupérer les pilotes ayant plus de points qu'un seuil
    public List<Pilot> getPilotsWithMoreThanPoints(int points) {
        TypedQuery<Pilot> query = db.createQuery("SELECT p FROM Pilot p WHERE p.points > :points", Pilot.class);
        query.setParameter("points", points);
        return query.getResultList();
    }

    // Supprimer un pilote par son nom
    public boolean deletePilotByName(String name) {
        db.getTransaction().begin();
        Query query = db.createQuery("DELETE FROM Pilot p WHERE p.name = :name");
        query.setParameter("name", name);
        int rowsDeleted = query.executeUpdate();
        db.getTransaction().commit();
        return rowsDeleted > 0;
    }

    // Supprimer un pilote en passant l'objet
    public void deletePilot(Pilot p) {
        db.getTransaction().begin();
        // Si le pilote n'est pas managé, on fusionne
        Pilot managed = db.contains(p) ? p : db.merge(p);
        db.remove(managed);
        db.getTransaction().commit();
    }

    // Récupérer un pilote par son nom
    public Pilot getPilotByName(String name) {
        TypedQuery<Pilot> query = db.createQuery("SELECT p FROM Pilot p WHERE p.name = :name", Pilot.class);
        query.setParameter("name", name);
        List<Pilot> pilots = query.getResultList();
        return pilots.isEmpty() ? null : pilots.get(0);
    }

    public void close() {
        db.close();
        emf.close();
    }
}
