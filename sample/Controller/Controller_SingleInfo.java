package sample.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import sample.Model.ModelSingle;

import static java.lang.String.*;



/**
 * Created by Gino on 25-6-2016.
 */
public class Controller_SingleInfo extends Controller {

    @FXML
    private Label titelSingle;

    @FXML
    private  Label artiestSingle;

    @FXML
    private Label jaartalSingle;

    @FXML
    private Label aantal_wekentop40Single;

    @FXML
    private Label hoogst_posTop40Single;

    @FXML
    void initialize()
    {
        ModelSingle modelSingle = ModelSingle.getInstance();
        ModelSingle.Singleinfo singleinfo = modelSingle.getSingleInfo();

        String textInBold = "Java_Prof_Level";

        titelSingle.setText("\n"+"Titel :    "+singleinfo.getTitel());
        artiestSingle.setText("\n"+"Artiest :    "+singleinfo.getArtiest());
        jaartalSingle.setText("\n"+"Jaar uitgebracht:    "+singleinfo.getJaar());
        aantal_wekentop40Single.setText("\n"+"Aantal weken Top-40:   "+singleinfo.getAantal_wekentop40());
        hoogst_posTop40Single.setText("\n"+"Hoogst behaalde positie Top 40:  "+singleinfo.getHoogst_posTop40());





            //System.out.println(titel);
            //lijst.getItems().add(titel);

    }




}
