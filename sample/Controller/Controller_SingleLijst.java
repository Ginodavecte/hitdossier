package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.Maincontroller;
import sample.Model.ModelSingle;

/**
 * Created by Gino on 20-6-16.
 */
public class Controller_SingleLijst extends Controller {


//    @FXML
//    void knop_ga_naar_Artiest1(ActionEvent event) {
//        maincontroller.setScreen(Maincontroller.Screen.ARTIESTLIJST);
//    }
    ModelSingle modelSingle = new ModelSingle();
    @FXML
    private TableView<ModelSingle.SingleLijstItem> lijst;

    @FXML
    protected TableColumn<ModelSingle.SingleLijstItem, String> c1;

    @FXML
    private TableColumn<ModelSingle.SingleLijstItem, String> c2;

    @FXML
    private Label infolabel;

    @FXML
    private TextField zoekSingle;

    @FXML
    protected void initialize() {
        c1.setCellValueFactory(new PropertyValueFactory<>("titel"));
        c2.setCellValueFactory(new PropertyValueFactory<>("naam"));
    }

    @FXML
    void knop(ActionEvent event) {
        //ModelSingle modelSingle = ModelSingle.getInstance();

        lijst.getItems().clear(); // Lijst leeg maken

        // De lijst vullen met singles
        for(ModelSingle.SingleLijstItem singleLijstItem : modelSingle.getSinglelijst(zoekSingle.getText())) {
            lijst.getItems().add(singleLijstItem);
        }

        infolabel.setText("Dubbelklik op een single...");
    }

    // Geklikt op tabel
    @FXML
    void tabelklik(MouseEvent event) {
        ModelSingle.SingleLijstItem singleLijstItem = lijst.getSelectionModel().getSelectedItem();
        if (event.getClickCount()==1) { // dubbelklik
            if (singleLijstItem!=null) {
                infolabel.setText("Geklikt op de single " + singleLijstItem.getNaam() + " met singleID " + singleLijstItem.getId());

            }
        }else{
            int geselecteerdeID = singleLijstItem.getId();
            ModelSingle.getInstance().setGeselecteerdeID(geselecteerdeID);
            maincontroller.setScreen(Maincontroller.Screen.SINGLEINFO);
        }
    }

}
