package fxPentu;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

/**
 * @author linda
 * @version 4.2.2020
 *
 */
public class PentuGUIController {
    @FXML private Label labelVirhe;
    @FXML private TextField textHaku;
    private String kasvattajannimi = "Karvatassu";
     
    @FXML private void handleTallenna() {
        tallenna();
    }
    
    
    @FXML private void handleLopeta() {
        // Dialogs.showMessageDialog("Ei osata tätä vielä.");
        lopeta();
    }
    
    
    @FXML private void handleTietoja() {
        Dialogs.showMessageDialog("Penturekisteri");
    }

    
    @FXML private void handleHaku() {
                  String hakukentta = textHaku.getSelectedText();
                  String ehto = textHaku.getText(); 
                  if ( ehto.isEmpty() )
                      naytaVirhe(null);
                  else
                      naytaVirhe("Ei osata vielä hakea " + hakukentta + ": " + ehto);
              }
    
    @FXML private void handleKumoa() {
        Dialogs.showMessageDialog("Ei osata tätä vielä.");
    }
    
    
    
    @FXML private void handlePoista() {
        // Dialogs.showMessageDialog("Ei osata tätä vielä.");
        Dialogs.showQuestionDialog("Poisto?", "Poistetaanko tiedot? ", "Kyllä", "Ei");
    }
    
    
    @FXML private void handleLisaa() {
        lisaa();
    }
    
    
    @FXML private void handleMuokkaa() {
        muokkaa();
        
    }
    
    // TODO: jokaisesta painikkeesta tulee tapahtua jotain
    // ModalController.showModal(PentuGUIController.class.getResource("PentuDialogView.fxml"), "Muokkaa", null, "");
    
    
    private void tallenna() {
        Dialogs.showMessageDialog("Ei osata tallentaa vielä.");
    }
    
    
    private void lopeta() {
        tallenna();
        Platform.exit();
    }
    
    
    private void lisaa() {
        ModalController.showModal(PentuDialogController.class.getResource("PentuDialogView.fxml"), "Lisää", null, "");
    }
    
    private void muokkaa() {
        ModalController.showModal(PentuDialogController.class.getResource("PentuDialogView.fxml"), "Muokkaa", null, "");
    }
    
    
    private void naytaVirhe(String virhe) {
                 if ( virhe == null || virhe.isEmpty() ) {
                     labelVirhe.setText("");
                     labelVirhe.getStyleClass().removeAll("virhe");
                     return;
                 }
                 labelVirhe.setText(virhe);
                 labelVirhe.getStyleClass().add("virhe");
             }


    /**
     * Tarkistetaan onko tallennus tehty
     * @return true jos saa sulkea sovelluksen, false jos ei
     */
    public boolean voikoSulkea() {
        tallenna();
        return true;
    }
    
    
    /**
     * Kysytään tiedoston nimi ja luetaan se
     * @return true jos onnistui, false jos ei
     */
    public boolean avaa() {
        String uusinimi = PentuNimiController.kysyNimi(null, kasvattajannimi);
        if (uusinimi == null) return false;
        lueTiedosto(uusinimi);
        return true;
    }
    
    
    /**
     * Alustaa kasvattajan tiedossa olevat tiedot lukemalla ne valitusta tiedostosta
     * @param nimi tiedosto josta kasvattajan tiedot luetaan
     */
    protected void lueTiedosto(String nimi) {
        kasvattajannimi = nimi;
        setTitle("Kasvattaja - " + kasvattajannimi);
        String virhe = "Ei osata lukea vielä";  // TODO: tähän oikea tiedoston lukeminen
        // if (virhe != null) 
            Dialogs.showMessageDialog(virhe);
    }
    
    
    private void setTitle(String title) {
        ModalController.getStage(textHaku).setTitle(title);
    }
}
