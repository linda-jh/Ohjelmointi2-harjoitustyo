package fxPentu;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.ModalControllerInterface;
import fi.jyu.mit.ohj2.Mjonot;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import pentu.Elain;
import pentu.Omistaja;
import pentu.Pentu;

/**
 * Avaa uuden dialogin eläimen tietojen muokkausta varten. 
 * 
 * @author Linda
 * ljhovila@student.jyu.fi
 * @version 12.2.2020
 * 
 * Mikä ei toimi:
 * - Jos eläimelle asettaa syntymäpäivän tai luovutuspäivän, niin kenttää ei saa enää tyhjäksi.
 * - Uutta eläintä lisättäessä, jos dialogin auettua painaa ruksia, ohjelma lisää tyhjän eläimen listaan.
 * - Muokkaus dialogin auetessa vanhempien ComboBoxissa välillä väärä nimi tai ei nimeä ollenkaan.
 * 
 */
public class PentuDialogController implements ModalControllerInterface<Elain>, Initializable {
    @FXML private Label labelVirhe;
    @FXML private TextField editNimi;
    @FXML private TextField editKutsumanimi;
    @FXML private TextField editSirunro;
    @FXML private DatePicker editSyntymapaivaD = new DatePicker();
    @FXML private DatePicker editLuovutuspv = new DatePicker();    
    @FXML private ComboBoxChooser<String> editSukupuoli;
    @FXML private ComboBoxChooser<String> editAiti;
    @FXML private ComboBoxChooser<String> editIsa;
    @FXML private ComboBoxChooser<String> editOmistaja;
    @FXML private GridPane gridElain;

    private TextField[] edits;
    private DatePicker[] paivat = new DatePicker[2];
    private static Elain apuelain = new Elain(); // Eläin, jolta voidaan kysyä tietoja.
    private Elain elainKohdalla;
    private int kentta = 0;
    private static Pentu pentu;
    
    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        //
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
        if (elainKohdalla != null && elainKohdalla.getSukupuoli().equals("") ) {
            naytaVirhe("Eläimelle täytyy valita sukupuoli");
            return;
        }
        ModalController.closeStage(labelVirhe);
    }
    
    
    @FXML private void handleSyntymapv() {
        kasittelePaivamaaraMuutos(editSyntymapaivaD, 4);
    }
    
    @FXML private void handleLuovutuspv() {
        kasittelePaivamaaraMuutos(editLuovutuspv, 5);
    }
  
    
//----------------------------------------------------------------------------------------------------------------
    
    /**
     * Tekee tarvittavie alustuksia, esimerkiksi GridPaneen lisätään kentät.
     */
    private void alusta() {
        for (TextField edit : edits) {
            if ( edit != null)
                edit.setOnKeyReleased(e -> kasitteleMuutosElaimeen((TextField)(e.getSource())));
        }
        
        //------------------------Lisätään DatePickerit------------------------------------------
        
        paivat[0] = editSyntymapaivaD;
        paivat[1] = editLuovutuspv;
        
        if (!elainKohdalla.anna(4).equals("")) {
            LocalDate ld = LocalDate.parse(kaannaPv(elainKohdalla.anna(4)));
            editSyntymapaivaD.setValue(ld);
        }
        
        if (!elainKohdalla.anna(5).equals("")) {
            LocalDate ld2 = LocalDate.parse(kaannaPv(elainKohdalla.anna(5)));
            editLuovutuspv.setValue(ld2);
        }
        
        gridElain.add(editSyntymapaivaD, 1, 3);
        gridElain.add(editLuovutuspv, 1, 4);
        
        //-----------------------------------------------------------------------------------------
        
        editSukupuoli.clear();
        editAiti.clear();
        editIsa.clear();
        editOmistaja.clear();
        ArrayList<Elain> aidit = new ArrayList<Elain>();
        ArrayList<Elain> isat = new ArrayList<Elain>();
        ArrayList<Omistaja> omistajat = new ArrayList<Omistaja>();
        
        //---------------------------Lisätään sukupuolet ComboBoxiin--------------------------------
        
        editSukupuoli.add("tyttö");
        editSukupuoli.add("poika");
        if (elainKohdalla.anna(0).equals("")) {
            int g = 0;
            if (elainKohdalla.anna(6).equals("poika")) g = 1; 
            editSukupuoli.getSelectionModel().select(g);
        }
        gridElain.add(editSukupuoli, 1, 5);
        editSukupuoli.addSelectionListener(e -> kasitteleBoksinMuutos(editSukupuoli, 6));

        //--------------------------Lisätään äidit ComboBoxiin--------------------------------------
    
        aidit = pentu.getNimet(1);
        if (aidit.size() != 0) editAiti.add(aidit.get(0).getNimi());
        if (aidit.size() != 0) editIsa.add(aidit.get(0).getNimi());
        
        for (int i = 1; i < aidit.size(); i++) {
            if (aidit.get(i).getTunnusNro() == elainKohdalla.getTunnusNro()) continue;
            editAiti.add(aidit.get(i).getNimi());
        }
        
        String id = elainKohdalla.anna(7);
        int j = 0;
        for (int i = 0; i < aidit.size(); i++) {
            if (aidit.get(i).getTunnusNro() == Integer.parseInt(id)) j = i;
        }
        
        editAiti.getSelectionModel().select(j);
        gridElain.add(editAiti,  1, 6);
        editAiti.addSelectionListener(e -> kasitteleBoksinMuutos(editAiti, 7));
        
        //--------------------------Lisätään isät ComboBoxiin---------------------------------------
        
        isat = pentu.getNimet(0);
        for (int i = 0; i < isat.size(); i++) {
            if (isat.get(i).getTunnusNro() == elainKohdalla.getTunnusNro()) continue;
            editIsa.add(isat.get(i).getNimi());
        }
        
        int id2 = Integer.parseInt(elainKohdalla.anna(8));
        int n = 0;
        for (int i = 0; i < isat.size(); i++) {
            if (isat.get(i).getTunnusNro() == id2) n = i;
        }
        
        editIsa.getSelectionModel().select(n);
        gridElain.add(editIsa,  1, 7);
        editIsa.addSelectionListener(e -> kasitteleBoksinMuutos(editIsa, 8));
        
        //------------------------Lisätään omistajat ComboBoxiin------------------------------------
        
        omistajat = pentu.getOmistajat();
        for (Omistaja o: omistajat) {
            editOmistaja.add(o.getNimi());
        }
        
        String id3 = elainKohdalla.anna(9);
        int v = 0;
        for (int i = 0; i < omistajat.size(); i++) {
            if (omistajat.get(i).getTunnusNro() == Integer.parseInt(id3)) v = i;
        }
        
        editOmistaja.getSelectionModel().select(v);
        gridElain.add(editOmistaja,  1, 8);
        editOmistaja.addSelectionListener(e -> kasitteleOmistajanMuutos(editOmistaja));
    }


    /**
     * Luodaan kysymysdialogi ja palautetaan sama tietua muutettuna tai null
     * @param modalityStage mille ollaan modaalisia, null = sovellukselle
     * @param oletus mitä näytetään oletuksena
     * @param k kenttä, joka saa fokuksen kun näytetään
     * @param p pentu
     * @return null, jos painetaan Kumoa, muuten tietue
     */
    public static Elain kysyElain(Stage modalityStage, Elain oletus, int k, Pentu p) {
        return ModalController.<Elain, PentuDialogController>showModal(
                PentuDialogController.class.getResource("PentuDialogView.fxml"), 
                "Penturekisteri",
                modalityStage, oletus, ctrl -> {ctrl.setKentta(k); ctrl.setPentu(p);});
    }
    

    @Override
    public void handleShown() {
        kentta = Math.max(apuelain.ekaKentta(), Math.min(kentta, apuelain.getKenttia() - 1));
    }


    @Override
    public Elain getResult() {
        return elainKohdalla;
    }


    @Override
    public void setDefault(Elain oletus) {
        edits =  luoKentat(gridElain, 4);
        elainKohdalla = oletus;
        naytaElain(elainKohdalla);
    }

    
    /**
     * Asetetaan pentu.
     * @param p rekisteri
     */
    public void setPentu(Pentu p) {
        pentu = p;
        alusta();
    }    
    
    
    private void setKentta(int k) {
        this.kentta = k;
    }
    
    
    /**
     * Näytetään eläimen tiedot TextField komponenteissa
     * @param elain mikä näytetään
     */
    public void naytaElain(Elain elain) {
        naytaElain(edits, elain, 4, pentu);
    }
    
    
    /**
     * Näytetään eläimen tiedot TextField komponenteissa
     * @param edit TextFieldit taulukossa
     * @param elain mikä näytetään
     * @param lkm montako kenttää luodaan
     * @param p rekisteri
     */
    public static void naytaElain(TextField[] edit, Elain elain, int lkm, Pentu p) {
        if (elain == null) return;
        for (int k = elain.ekaKentta(); k < lkm; k++) {
            if (k >= 4 && k <=9 && k != 6) {
                if (k == 4 || k == 5) {
                    edit[k].setText(elain.anna(k));
                }
                if (k == 7) {
                    ArrayList<Elain> elaimet = p.getNimet(1);
                    for (Elain e : elaimet) {
                        if (elain.anna(k).equals(Integer.toString(e.getTunnusNro()))) {
                            edit[k].setText(e.getNimi());
                        }
                    }
                }
                if (k == 8) {
                    ArrayList<Elain> isat = new ArrayList<Elain>();
                    isat.add(p.annaElain(0));
                    ArrayList<Elain> elaimet2= p.getNimet(0);
                    for (Elain e2 : elaimet2) {
                        isat.add(e2);
                    }
                    for (Elain e : isat) {
                        if (elain.anna(k).equals(Integer.toString(e.getTunnusNro()))) {
                            edit[k].setText(e.getNimi());
                        }
                    }
                }
                if (k == 9) {
                    ArrayList<Omistaja> omistajat = p.getOmistajat();
                    for (Omistaja o : omistajat) {
                        if (elain.anna(k).equals(Integer.toString(o.getTunnusNro()))) {
                            edit[k].setText(o.getNimi());
                        }
                    }
                }
            } else {
                edit[k].setText(elain.anna(k));
            }
        }
    }
    
    
    /**
     * Käsitellään päivämäärän kohdalle tullut muutos
     * @param p minkä kentän muutos
     * @param k mikä kenttä eläimen tiedoissa
     */
    private void kasittelePaivamaaraMuutos(DatePicker p, int k) {
        if (elainKohdalla == null) return;
        LocalDate pv = p.getValue();
        String virhe = null;
        virhe = pentu.aseta(elainKohdalla, k, pv.toString());
        naytaVirhe(virhe);
    }
    
    
    /**
     * Käsittelee omistajan kohdalle tulleen muutoksen
     * @param cb minkä kentän muutos
     */
    private void kasitteleOmistajanMuutos(ComboBoxChooser<String> cb) {
        if (elainKohdalla == null) return;
        String s = cb.getSelectedObject();
        pentu.asetaOmistaja(elainKohdalla, s);
    }
    
    
    /**
     * Käsittelee ComboBox:ssa tapahtuneen muutoksen
     * @param cb mikä ComboBox
     * @param k mikä kenttä eläimen tiedoissa
     */
    private void kasitteleBoksinMuutos(ComboBoxChooser<String> cb, int k) {
        if (elainKohdalla == null) return;
        String s = cb.getSelectedObject();
        String virhe = null;
        virhe = pentu.aseta(elainKohdalla, k, s);
        naytaVirhe(virhe);
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
        virhe = pentu.aseta(elainKohdalla, k, s);
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
     * Luodaan GridPaneen eläinten tiedot
     * @param gridElain mihin tiedot luodaan
     * @param lkm montako textfield kenttää luodaan
     * @return luodut tekstikentät
     */
    public static TextField[] luoKentat(GridPane gridElain, int lkm) {
        gridElain.getChildren().clear();
        
        for (int i = 0, k = apuelain.ekaKentta(); k < apuelain.getKenttia(); k++, i++) {
            Label label = new Label(apuelain.getKysymys(k));
            gridElain.add(label, 0, i);
        }
        
        TextField[] edits = luoTextField(gridElain, lkm);
        
        return edits;
    }
    
    
    /**
     * Luodaan GridPaneen TextField kentät
     * @param gridElain mihin luodaan
     * @param lkm montako kenttää luodaan
     * @return taulukko kentistä
     */
    private static TextField[] luoTextField(GridPane gridElain, int lkm) {
        TextField[] edits = new TextField[apuelain.getKenttia()];
        for (int i = 0, k = apuelain.ekaKentta(); k < lkm; k++, i++) {
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
     * Kääntää päivämäärän toisinpäin. pp.kk.vvvv -> vvvv/kk/pp.
     * @param paiva päivämäärä
     * @return käännetty päivä
     * @example
     * <pre name="test">
     * String pv = "22.02.1997";
     * kaannaPv(pv) === "1997.02.22";
     * String pv1 = "22.02.";
     * kaannaPv(pv1) === "0000.02.22";
     * </pre>
     */
    public static String kaannaPv(String paiva) {
        StringBuilder sb = new StringBuilder();
        sb.append(paiva);
        String pp = Mjonot.erota(sb, '.', "00");
        String kk = Mjonot.erota(sb, '.', "00");
        if (sb.toString().equals("")) sb.append("0000");
        sb.append("-" + kk);
        sb.append("-" + pp);
        return sb.toString();
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
