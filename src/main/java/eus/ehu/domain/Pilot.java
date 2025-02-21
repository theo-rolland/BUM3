package eus.ehu.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Pilot {
    @Id
    private String name;
    private String nationality;
    private int points;

    // Constructeur principal
    public Pilot(String name, String nat, int pts) {
        this.name = name;
        this.nationality = nat;
        this.points = pts;
    }

    // Constructeur surchargé : seulement le nom, par défaut "unknown" et 0 points
    public Pilot(String name) {
        this(name, "unknown", 0);
    }

    // Constructeur par défaut requis par JPA
    public Pilot() { }

    public Pilot(String name, String nationality) {
        this.name = name;
        this.nationality = nationality;
    }


    // Getter pour le nom (utilisé notamment pour éviter les duplications)
    public String getName() {
        return name;
    }

    // Autres getters si nécessaire
    public String getNationality() {
        return nationality;
    }

    public int getPoints() {
        return points;
    }

    // Méthode pour ajouter des points
    public void addPoints(int morePoints) {
        this.points += morePoints;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) - %d points", name, nationality, points);
    }
}
