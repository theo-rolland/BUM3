package eus.ehu.businesslogic;

import eus.ehu.data_access.DbAccessManager;
import eus.ehu.domain.Pilot;
import java.util.List;

public class BusinessLogic implements BlInterface {
    private DbAccessManager db = new DbAccessManager();

    @Override
    public List<Pilot> getPilots() {
        return db.getAllPilots();
    }

    @Override
    public List<Pilot> getPilotsByNationality(String nat) {
        return db.getPilotsByNationality(nat);
    }

    @Override
    public void storePilot(String name, String nat, int pts) {
        Pilot pilot = new Pilot(name, nat, pts);
        db.savePilot(pilot);
    }

    @Override
    public void deletePilotByName(String name) {
        db.deletePilotByName(name);
    }

    @Override
    public void deletePilot(Pilot p) {
        db.deletePilot(p);
    }

    // Pense à fermer la connexion si besoin via un setter ou un hook d'arrêt
}
