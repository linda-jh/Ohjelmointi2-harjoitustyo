package fxPentu;

import fi.jyu.mit.fxgui.Dialogs;
// import fi.jyu.mit.fxgui.ModalController;
import javafx.fxml.FXML;

/**
 * @author linda
 * @version 12.2.2020
 *
 */
public class PentuDialogController {
    
    @FXML private void handleTallenna() {
        tallenna();
    }
    
    @FXML private void handleKumoa() {
        Dialogs.showMessageDialog("Ei osata tätä vielä.");
    }
    
    
    private void tallenna() {
        Dialogs.showMessageDialog("Ei osata tallentaa vielä.");
    }
    

}
