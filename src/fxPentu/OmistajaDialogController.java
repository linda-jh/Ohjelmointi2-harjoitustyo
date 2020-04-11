package fxPentu;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pentu.Omistaja;

/**
 * @author linda
 * @version 6.4.2020
 *
 */
public class OmistajaDialogController implements ModalControllerInterface<Omistaja>, Initializable {
    @FXML private Label labelVirhe;
    @FXML private TextField editNimiO;
    @FXML private TextField editKatuosoite;
    @FXML private TextField editPostinro;
    @FXML private TextField editPuhelinnro;
    @FXML private TextField editSPosti;
    
    private Omistaja omistajaKohdalla;
    private TextField[] edits;

    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();        
    }

    
    @FXML private void handleCancel() {
        ModalController.closeStage(labelVirhe);
        //Dialogs.showMessageDialog("Ei osata tätä vielä.");
        //ModalController.closeStage(textNimiD);
    }
    
    @FXML private void handleOK() {
        // if( nimi != elainKohdalla.getNimi()) elainKohdalla.setNimi(nimi);
        // tallenna();
        ModalController.closeStage(labelVirhe);
    }
    
   
//---------------------------------------------------------------------------------------------------------
    
    
    public void alusta() {
        edits = new TextField[] {editNimiO, editKatuosoite, editPostinro, editPuhelinnro, editSPosti};
    }
    
    
    @Override
    public Omistaja getResult() {
        return null;
    }

    @Override
    public void handleShown() {
        // TODO Auto-generated method stub
    }

    @Override
    public void setDefault(Omistaja oletus) {
        omistajaKohdalla = oletus;
        naytaOmistaja(omistajaKohdalla);
    }
    
    
    /**
     * Näytetään omistajan tiedto TextField komponenteissa
     * @param omistaja mikä näytetään
     */
    public void naytaOmistaja(Omistaja omistaja) {
        naytaOmistaja(edits, omistaja);
    }
    
    
    /**
     * Näytetään eläimen tiedto TextField komponenteissa
     * @param edits TextFieldit taulukossa
     * @param omistaja mikä näytetään
     */
    public static void naytaOmistaja(TextField[] edits, Omistaja omistaja) {
        if (omistaja == null) return;
        edits[0].setText(omistaja.getNimi());
        edits[1].setText(omistaja.getKatuosoite());
        edits[2].setText(omistaja.getPostinro());
        edits[3].setText(omistaja.getPuhelinnro());
        edits[4].setText(omistaja.getSPosti());
    }


    /**
     * Luodaan kysymysdialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä näytetään oletuksena
     * @return null, jos painetaan Kumoa, muuten tietue
     */
    public static Omistaja kysyOmistaja(Stage modalityStage, Omistaja oletus) {
        return ModalController.showModal(
                OmistajaDialogController.class.getResource("OmistajaDialogView.fxml"), 
                "Muokkaa",
                modalityStage, oletus, null);
    }

}
