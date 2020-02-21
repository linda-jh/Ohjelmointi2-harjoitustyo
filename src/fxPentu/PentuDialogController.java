package fxPentu;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import pentu.Elain;

/**
 * @author linda
 * @version 12.2.2020
 *
 */
public class PentuDialogController implements ModalControllerInterface<String> {
    // @FXML ListChooser<Elain> chooserElaimet;
    // private TextField textNimiD;
    // private String nimiD = null;
    // private Elain elainKohdalla = chooserElaimet.getSelectedObject();
    
    
    @FXML private void handleDefaultCancel() {
        Dialogs.showMessageDialog("Ei osata tätä vielä.");
        //ModalController.closeStage(textNimiD);
    }
    
    @FXML private void handleDefaultOK() {
        // if( nimi != elainKohdalla.getNimi()) elainKohdalla.setNimi(nimi);
        tallenna();
    }
    
    
    private void tallenna() {
        Dialogs.showMessageDialog("Ei osata tallentaa vielä.");
    }


    @Override
    public String getResult() {
        //return nimiD;
        return null;
    }


    @Override
    public void handleShown() {
        // textNimiD.requestFocus();
        
    }


    @Override
    public void setDefault(String oletus) {
        // textNimiD.setText(oletus);
        
    }

}
