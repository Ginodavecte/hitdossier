package sample.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sample.Model.ModelTop40;

/**
 * Created by Gino on 20-6-16.
 */
public class Controller_Top40 extends Controller {

    protected ModelTop40 modelTop40 = new ModelTop40();

    @FXML
    private TableView<ModelTop40.TableRowTop40> ui_tabel;

    @FXML
    private ComboBox<Integer> ui_keuze_week;

    @FXML
    private TableColumn<ModelTop40.TableRowTop40, String> ui_tabelkolom_titel;

    @FXML
    private ComboBox<Integer> ui_keuze_jaar;

    @FXML
    private TableColumn<ModelTop40.TableRowTop40, String> ui_tabelkolom_artiest;

    @FXML
    private TableColumn<ModelTop40.TableRowTop40, Integer> ui_tabelkolom_positie;

    protected void updateTabel() {
        // Tabel leeg maken
        ui_tabel.getItems().clear();
        // Model aanroepen
        for(ModelTop40.TableRowTop40 row : modelTop40.getTop40()) {
            ui_tabel.getItems().add(row);
        }

    }

    @FXML
    void initialize() {

        // Comboboxes vullen met keuzes

        for (int i=1;i<=52;i++)
            ui_keuze_week.getItems().add(i);
        ui_keuze_week.getSelectionModel().selectFirst();
        for (int j=1965;j<=2015;j++)
            ui_keuze_jaar.getItems().add(j);
        ui_keuze_jaar.getSelectionModel().selectFirst();

        // Model koppelen aan kolommen
        ui_tabelkolom_positie.setCellValueFactory(new PropertyValueFactory<>("positie"));
        ui_tabelkolom_titel.setCellValueFactory(new PropertyValueFactory<>("titel"));
        ui_tabelkolom_artiest.setCellValueFactory(new PropertyValueFactory<>("artiest"));
        modelTop40.setWeek(ui_keuze_week.getSelectionModel().getSelectedItem());
        modelTop40.setJaar(ui_keuze_jaar.getSelectionModel().getSelectedItem());
        // Tabel vullen
        updateTabel();
    }
    @FXML
    void week_gewijzigd(ActionEvent event) {

        modelTop40.setWeek(ui_keuze_week.getSelectionModel().getSelectedItem());

        updateTabel();
    }

    @FXML
    void jaar_gewijzigd(ActionEvent event) {

        modelTop40.setJaar(ui_keuze_jaar.getSelectionModel().getSelectedItem());

        updateTabel();
    }


}