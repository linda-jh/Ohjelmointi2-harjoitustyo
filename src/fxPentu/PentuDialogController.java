package fxPentu;

import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
    @FXML private TextField editSyntymapaivaD;
    @FXML private TextArea editLisatietoja;
    @FXML private TextField editSukupuoli;
    @FXML private TextField editAiti;
    @FXML private TextField editIsa;
    @FXML private GridPane gridElain;

    private TextField[] edits;
    private static Elain apuelain = new Elain(); // Eläin, jolta voidaan kysyä tietoja.
    private Elain elainKohdalla;
    private int kentta = 0;
    
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
        edits =  luoKentat(gridElain); 
        // edits = new TextField[] {editNimi, editKutsumanimi, editSirunro, editSyntymapaivaD };
        // int i = 0;
        for (TextField edit : edits) {
            // final int k = ++i;
            if ( edit != null)
                edit.setOnKeyReleased(e -> kasitteleMuutosElaimeen((TextField)(e.getSource())));
        }
       
    }
    

    @Override
    public void handleShown() {
        kentta = Math.max(apuelain.ekaKentta(), Math.min(kentta, apuelain.getKenttia() - 1));
        edits[kentta].requestFocus();
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
        for (int k = elain.ekaKentta(); k < elain.getKenttia(); k++) {
            edit[k].setText(elain.anna(k));
        }
        
        /**
        edit[0].setText(elain.getNimi());
        edit[1].setText(elain.getKutsumanimi());
        edit[2].setText(elain.getSiruNro());
        */
    }
    
    
    /**
     * Käsitellään eläimen muutos
     * @param edit muuttunut kenttä
     */
    private void kasitteleMuutosElaimeen(TextField edit) {
        if (elainKohdalla == null) return;
        int k = getFieldId(edit, apuelain.ekaKentta());
        String s = edit.getText();
        String virhe = null;
        virhe = elainKohdalla.aseta(k, s);
        /** switch (k) {
            case 1 : virhe = elainKohdalla.setNimi(s); break;
            case 2 : virhe = elainKohdalla.setKutsumanimi(s); break;
            case 3 : virhe = elainKohdalla.setSirunumero(s); break;
            default:
        }*/ 
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
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä näytetään oletuksena
     * @param k kenttä, joka saa fokuksen kun näytetään
     * @return null, jos painetaan Kumoa, muuten tietue
     */
    public static Elain kysyElain(Stage modalityStage, Elain oletus, int k) {
        return ModalController.<Elain, PentuDialogController>showModal(
                PentuDialogController.class.getResource("PentuDialogView.fxml"), 
                "Muokkaa",
                modalityStage, oletus, ctrl -> ctrl.setKentta(k));
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
    
    
    private void setKentta(int k) {
        this.kentta = k;
    }
    
    
    /**
     * Luodaan GridPaneen eläinten tiedot
     * @param gridElain mihin tiedot luodaan
     * @return luodut tekstikentät
     */
    public static TextField[] luoKentat(GridPane gridElain) {
        gridElain.getChildren().clear();
        TextField[] edits = new TextField[apuelain.getKenttia()];
        
        for (int i = 0, k = apuelain.ekaKentta(); k < apuelain.getKenttia(); k++, i++) {
            Label label = new Label(apuelain.getKysymys(k));
            gridElain.add(label, 0, i);
            TextField edit = new TextField();
            edits[k] = edit;
            edit.setId("e"+k);
            gridElain.add(edit,  1, i);
        }
        return edits;
    }
    
    
    /**
     * Tyhjentää tekstikentät
     * @param edits tyhjennettävät kentät
     */
    public static void tyhjenna(TextField[] edits) {
        for (TextField edit : edits)
            if (edit != null) edit.setText("");
    }
    /**
     * Palautetaan komponentin id:stä saatava luku
     * @param o tutkittava komponentti
     * @param oletus mikä arvo, jos id ei ole kunnollinen
     * @return id lukuna
     */
    public static int getFieldId(Object o, int oletus) {
        if (!(o instanceof Node)) return oletus;
        Node node = (Node)o;
        return Mjonot.erotaInt(node.getId().substring(1), oletus);
    }
}
