package sample.Controller;


import com.sun.org.apache.xpath.internal.operations.Mod;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import sample.Model.ModelArtiest;
import javafx.scene.control.Label;

/**
 * Created by Gino on 21-6-2016.
 */
public class Controller_ArtiestInfo extends Controller {

    @FXML
    private ListView<String> lijst;

    @FXML
       private Label naamArtiest;


    @FXML
    void initialize()
    {
          ModelArtiest modelArtiest = ModelArtiest.getInstance();
          ModelArtiest.Artiestinfo artiestinfo = modelArtiest.getArtiestInfo();


        lijst.getItems().clear(); // Lijst leeg maken

        naamArtiest.setText(artiestinfo.getNaam());
        for(String titel : artiestinfo.getSingles() ){
            System.out.println(titel);
            lijst.getItems().add(titel);
        }
    }

}
