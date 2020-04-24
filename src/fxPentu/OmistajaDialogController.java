package fxPentu;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pentu.Omistaja;

/**
 * Omistajan muokkaus dialogi. Avaa uuden ikkunan omistajan tietojen muokkausta varten.
 * @author Linda
 * ljhovila@student.jyu.fi
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
        omistajaKohdalla = null;
        ModalController.closeStage(labelVirhe);
    }
    
    
    @FXML private void handleOK() {
        if (omistajaKohdalla != null && omistajaKohdalla.getNimi().trim().equals("")) {
            naytaVirhe("Nimi ei saa olla tyhjä!");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }
    
   
//---------------------------------------------------------------------------------------------------------
    
    
    /**
     * Tekee tarvittavat alustukset.
     */
    public void alusta() {
        // edits = luoKentat(gridOmistaja);
        edits = new TextField[] {editNimiO, editKatuosoite, editPostinro, editPuhelinnro, editSPosti};
        int i = 0;
        for (TextField edit : edits) {
            final int k = ++i;
            // if ( edit != null)
            edit.setOnKeyReleased(e -> kasitteleMuutosOmistajaan(k, (TextField)(e.getSource())));
        }
    }
    
    
    @Override
    public Omistaja getResult() {
        return omistajaKohdalla;
    }

    @Override
    public void handleShown() {
        editNimiO.requestFocus();
    }

    @Override
    public void setDefault(Omistaja oletus) {
        omistajaKohdalla = oletus;
        naytaOmistaja(omistajaKohdalla);
    }
    
    
    /**
     * Näytetään omistajan tiedot TextField komponenteissa
     * @param omistaja mikä näytetään
     */
    public void naytaOmistaja(Omistaja omistaja) {
        naytaOmistaja(edits, omistaja);
    }
    
    
    /**
     * Näytetään omistajan tiedot TextField komponenteissa
     * @param edits TextFieldit taulukossa
     * @param omistaja mikä näytetään
     */
    public static void naytaOmistaja(TextField[] edits, Omistaja omistaja) {
        if (omistaja == null) return;
        /**for (int k = omistaja.ekaKentta(); k < omistaja.getKenttia(); k++) {
            edits[k].setText(omistaja.anna(k));
        }*/
        
        edits[0].setText(omistaja.getNimi());
        edits[1].setText(omistaja.getKatuosoite());
        edits[2].setText(omistaja.getPostinro());
        edits[3].setText(omistaja.getPuhelinnro());
        edits[4].setText(omistaja.getSPosti());
    }
    
    /**
     * Käsitellään omistajan muutos
     * @param k monesko kenttä
     * @param edit mikä kenttä
     */
    private void kasitteleMuutosOmistajaan(int k, TextField edit) {
        if (omistajaKohdalla == null) return;
        // int k = getFieldId(edit, apuomistaja.ekaKentta());
        String s = edit.getText();
        String virhe = null;
        // virhe = omistajaKohdalla.aseta(k, s);
        switch (k) {
            case 1 : virhe = omistajaKohdalla.setNimi(s); break;
            case 2 : virhe = omistajaKohdalla.setKatuosoite(s); break;
            case 3 : virhe = omistajaKohdalla.setPostinro(s); break;
            case 4 : virhe = omistajaKohdalla.setPuhelin(s); break;
            case 5 : virhe = omistajaKohdalla.setSPosti(s); break;
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
     * Luodaan kysymysdialogi ja palautetaan sama tietue muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä näytetään oletuksena
     * @return null, jos painetaan Kumoa, muuten tietue
     */
    public static Omistaja kysyOmistaja(Stage modalityStage, Omistaja oletus) {
        return ModalController.showModal(
                OmistajaDialogController.class.getResource("OmistajaDialogView.fxml"), 
                "Penturekisteri",
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
    
    /**
    /**
     * Luodaan GridPaneen omistajan tiedot
     * @param gridOmistaja mihin tiedot luodaan
     * @return luodut tekstikentät
     *
    public static TextField[] luoKentat(GridPane gridOmistaja) {
        gridOmistaja.getChildren().clear();
        TextField[] edits = new TextField[apuomistaja.getKenttia()];
        
        for (int i = 0, k = apuomistaja.ekaKentta(); k < apuomistaja.getKenttia(); k++, i++) {
            Label label = new Label(apuomistaja.getKysymys(k));
            gridOmistaja.add(label, 0, i);
            TextField edit = new TextField();
            edits[k] = edit;
            edit.setId("e"+k);
            gridOmistaja.add(edit,  1, i);
        }
        return edits;
    }
    
    
    /**
     * Tyhjentää tekstikentät
     * @param edits tyhjennettävät kentät
     *
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits)
            if (edit != null) edit.setText("");
    }
    /**
     * Palautetaan komponentin id:stä saatava luku
     * @param o tutkittava komponentti
     * @param oletus mikä arvo, jos id ei ole kunnollinen
     * @return id lukuna
     *
    public static int getFieldId(Object o, int oletus) {
        if (!(o instanceof Node)) return oletus;
        Node node = (Node)o;
        return Mjonot.erotaInt(node.getId().substring(1), oletus);
    }*/

}
