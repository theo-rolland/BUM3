package eus.ehu.presentation;

import eus.ehu.businesslogic.BusinessLogic;
import eus.ehu.businesslogic.BlInterface;
import eus.ehu.domain.Pilot;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

public class MainController {
    @FXML
    private ListView<Pilot> listDrivers;
    @FXML
    private Button deleteButton;
    @FXML
    private Button addButton;
    @FXML
    private Label countLabel;

    private ObservableList<Pilot> drivers;
    private BlInterface bl = new BusinessLogic();

    @FXML
    public void initialize() {
        // Charger les pilotes depuis la BD via la couche BusinessLogic
        drivers = FXCollections.observableArrayList(bl.getPilots());
        listDrivers.setItems(drivers);
        // Lier le label au nombre de pilotes dans la liste
        countLabel.textProperty().bind(Bindings.size(drivers).asString("Nombre de pilotes: %d"));
    }

    @FXML
    private void handleDelete() {
        Pilot selected = listDrivers.getSelectionModel().getSelectedItem();
        if (selected != null) {
            bl.deletePilot(selected);
            drivers.remove(selected);
        }
    }

    @FXML
    private void handleAdd() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Ajouter un pilote");
        dialog.setHeaderText("Ajouter un nouveau pilote");
        dialog.setContentText("Nom du pilote:");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            // Utiliser le constructeur surchargé qui définit par défaut la nationalité "unknown" et 0 points
            Pilot newPilot = new Pilot(name);
            bl.storePilot(newPilot.getName(), "unknown", 0);
            drivers.add(newPilot);
        });
    }
}
