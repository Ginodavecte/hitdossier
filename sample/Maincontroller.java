package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import sample.Controller.Controller;

import java.awt.*;
import java.io.IOException;

public class Maincontroller {

    public enum Screen { ARTIESTLIJST,ARTIESTINFO, SINGLELIJST ,SINGLEINFO, TOP40 };

    @FXML
    private BorderPane borderpane;

    protected Screen current_screen;

    public void setScreen(Screen screen) {
        if (screen!=current_screen) {
            String fxmlfilename;
            switch(screen) {
                case ARTIESTLIJST:
                    fxmlfilename="ArtiestLijst";
                    break;
                case ARTIESTINFO:
                    fxmlfilename="ArtiestInfo";
                    break;
                case SINGLELIJST:
                    fxmlfilename="SingleLijst";
                    break;
                case SINGLEINFO:
                    fxmlfilename="SingleInfo";
                    break;
                case TOP40:
                    fxmlfilename="Top40";
                    break;
                default:
                    throw new Error("Screen unknown or not implemented.");
            }

            System.out.println("Load fxml: "+fxmlfilename+".fxml");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("UserInterface/"+fxmlfilename+".fxml"));
            Parent element=null;
            try {
                element = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
                throw new Error("Load fxml failed.");
            }
            Controller c=loader.getController();
            c.setMaincontroller(this); // pass maincontroller
            current_screen=screen;
            borderpane.setCenter(element);
        }
    }

    @FXML
    void initialize() {
        setScreen(Screen.ARTIESTLIJST);
    }

    @FXML
    void klik_knop_Artiest(ActionEvent event) {
        setScreen(Screen.ARTIESTLIJST);
    }

    @FXML
    void klik_knop_Single(ActionEvent event) {
        setScreen(Screen.SINGLELIJST);
    }

    @FXML
    void klik_knop_Top40(ActionEvent event) {
        setScreen(Screen.TOP40);
    }


}
