package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.Maincontroller;
import sample.Model.ModelArtiest;

import java.util.List;

/**
 * Created by Gino on 20-6-16.
 */
public class Controller_ArtiestLijst extends Controller {


    @FXML
    private ListView<ModelArtiest.ArtiestLijstItem> lijst;

    @FXML
    private Label infolabel;

    @FXML
    private TextField zoekArtiest;

    @FXML
    void knop(ActionEvent event) {
        ModelArtiest modelArtiest = ModelArtiest.getInstance();

        lijst.getItems().clear(); // Lijst leeg maken

        // De lijst vullen met artiesten
        for(ModelArtiest.ArtiestLijstItem artiestLijstItem : modelArtiest.getArtiestlijst(zoekArtiest.getText())) {
            lijst.getItems().add(artiestLijstItem);
        }

        infolabel.setText("Dubbelklik op een artiest...");
    }
    // Geklikt op tabel
    @FXML
    void tabelklik(MouseEvent event) {
        ModelArtiest.ArtiestLijstItem artiestLijstItem = lijst.getSelectionModel().getSelectedItem();
        if (event.getClickCount()==1) { // dubbelklik
            if (artiestLijstItem!=null) {
                infolabel.setText("Geklikt op artiest " + artiestLijstItem.getNaam() + " met artiestID " + artiestLijstItem.getId());

            }
        }else{
            int geselecteerdeID = artiestLijstItem.getId();
            ModelArtiest.getInstance().setGeselecteerdeID(geselecteerdeID);
            maincontroller.setScreen(Maincontroller.Screen.ARTIESTINFO);
        }
    }





}
