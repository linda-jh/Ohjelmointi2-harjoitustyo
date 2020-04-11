package fxPentu;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pentu.Elain;

/**
 * @author linda
 * @version 12.2.2020
 *
 */
public class PentuDialogController implements ModalControllerInterface<Elain>, Initializable {
    @FXML private Label labelVirhe;
    @FXML private TextField editNimi;
    @FXML private TextField editKutsumanimi;
    @FXML private TextField editSirunro;
    @FXML private TextArea editLisatietoja;
    
    private TextField[] edits;

    private Elain elainKohdalla;
    //@FXML ListChooser<Elain> chooserElaimet;
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta(); 
    }
    
    
    @FXML private void handleCancel() {
        elainKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }
    
    @FXML private void handleOK() {
        if (elainKohdalla != null && elainKohdalla.getNimi().trim().equals("")) {
            naytaVirhe("Nimi ei saa olla tyhjä!");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }
  
    
//----------------------------------------------------------------------------------------------------------------
    
    private void alusta() {
        edits = new TextField[] {editNimi, editKutsumanimi, editSirunro};
        int i = 0;
        for (TextField edit : edits) {
            final int k = ++i;
            edit.setOnKeyReleased(e -> kasitteleMuutosElaimeen(k, (TextField)(e.getSource())));
        }
    }
    
    
    private void tallenna() {
        Dialogs.showMessageDialog("Ei osata tallentaa vielä.");
    }


    @Override
    public void handleShown() {
        editNimi.requestFocus();
    }


    @Override
    public Elain getResult() {
        return elainKohdalla;
    }


    @Override
    public void setDefault(Elain oletus) {
        elainKohdalla = oletus;
        naytaElain(elainKohdalla);
    }
    
    
    /**
     * Näytetään eläimen tiedto TextField komponenteissa
     * @param elain mikä näytetään
     */
    public void naytaElain(Elain elain) {
        naytaElain(edits, elain);
    }
    
    
    /**
     * Näytetään eläimen tiedto TextField komponenteissa
     * @param edit TextFieldit taulukossa
     * @param elain mikä näytetään
     */
    public static void naytaElain(TextField[] edit, Elain elain) {
        if (elain == null) return;
        edit[0].setText(elain.getNimi());
        edit[1].setText(elain.getKutsumanimi());
        edit[2].setText(elain.getSiruNro());
    }
    
    
    private void kasitteleMuutosElaimeen(int k, TextField edit) {
        if (elainKohdalla == null) return;
        String s = edit.getText();
        String virhe = null;
        switch (k) {
            case 1 : virhe = elainKohdalla.setNimi(s); break;
            case 2 : virhe = elainKohdalla.setKutsumanimi(s); break;
            case 3 : virhe = elainKohdalla.setSirunumero(s); break;
            default:
        }
        if (virhe == null) {
            Dialogs.setToolTipText(edit, "");
            edit.getStyleClass().removeAll("virhe");
            naytaVirhe(virhe);
        } else {
            Dialogs.setToolTipText(edit, virhe);
            edit.getStyleClass().add("virhe");
            naytaVirhe(virhe);
        }
    }
    
    
    /**
     * Luodaan kysymysdialogi ja palautetaan sama tietua muutettuna tai null
     * TODO: ei toimi vielä täysin
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä näytetään oletuksena
     * @return null, jos painetaan Kumoa, muuten tietue
     */
    public static Elain kysyElain(Stage modalityStage, Elain oletus) {
        return ModalController.showModal(
                PentuDialogController.class.getResource("PentuDialogView.fxml"), 
                "Muokkaa",
                modalityStage, oletus, null);
    }

    
    private void naytaVirhe(String virhe) {
        if (virhe == null || virhe.isEmpty()) {
            labelVirhe.setText("");
            labelVirhe.getStyleClass().remove("virhe");
            return;
        }
        labelVirhe.setText(virhe);
        labelVirhe.getStyleClass().add("virhe");
    }
    
}
