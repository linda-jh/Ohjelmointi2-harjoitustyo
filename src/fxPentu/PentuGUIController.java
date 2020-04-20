package fxPentu;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static fxPentu.PentuDialogController.getFieldId;
import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.StringGrid;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import pentu.Elain;
import pentu.Omistaja;
import pentu.Pentu;
import pentu.SailoException;

/**
 * @author linda
 * @version 4.2.2020
 *
 */
public class PentuGUIController implements Initializable{
    @FXML private ComboBoxChooser<String> cbHaku;
    @FXML private TextField textHaku;
    @FXML private ScrollPane panelElain;
    @FXML private ScrollPane panelOmistaja;
    @FXML private ScrollPane panelPennut;
    @FXML private ListChooser<Elain> chooserElaimet;
    @FXML private ListChooser<Omistaja> chooserOmistajat;
    @FXML private ListChooser<Elain> chooserPennut;
    @FXML private StringGrid<Elain> tableOmistajanElaimet;
    @FXML private Label labelVirhe;
    @FXML private Label pentujenLkm;
    @FXML private Label omistajaNimi;
    @FXML private Label luovutusPv;
    @FXML private Label luovutusPv2;
    @FXML private TextField editNimi;
    @FXML private TextField editKutsumanimi;
    @FXML private TextField editSirunro;
    @FXML private TextArea editLisatietoja;

    @FXML private TextField editNimiO;
    @FXML private TextField editKatuosoite;
    @FXML private TextField editPostinro;
    @FXML private TextField editPuhelinnro;
    @FXML private TextField editSPosti;
    
    @FXML private GridPane gridElain;
    
    private String kasvattajannimi = "Karvatassu";
    private Pentu pentu;
    private Elain elainKohdalla;
    private Omistaja omistajaKohdalla;
    private TextField[] edits;
    private TextField[] editsO;
    private int kentta = 0;
    private static Elain apuelain = new Elain();

    
    @Override
    public void initialize(URL url, ResourceBundle bundle) {
        alusta();      
    }
    
    
    /**
     * Käsittelee tapahtuman, kun painetaan tallenna 
     */
    @FXML private void handleTallenna() {
        tallenna();
    }
    
    /**
     * Käsittelee tapahtuman, kun painetaan lopeta tai suljetaan ikkuna ruksista 
     */
    @FXML private void handleLopeta() {
        lopeta();
    }
    
    /**
     * Käsittelee tapahtuman, kun painetaan tietoja
     */
    @FXML private void handleTietoja() {
        Dialogs.showMessageDialog("Penturekisteri");
    }

    /**
     * Käsittelee tapahtuman, kun hakukenttään kirjoitetaan
     */
    @FXML private void handleHaku() {
        hae(0);
    }
    
    
    /**
     * Käsittelee tapahtuman, kun halutaan poistaa valittu eläin
     */
    @FXML private void handlePoistaElain() {
        poistaElain();
    }
    
    
    /**
     * Käsittelee tapahtuman, kun halutaan poistaa valittu omistaja
     */
    @FXML private void handlePoistaOmistaja() {
        poistaOmistaja();
    }
    
    
    /**
     * Käsittelee tapahtuman, kun halutaan poistaa eläin tai omistaja
     */
    @FXML private void handlePoista() {
        // Dialogs.showMessageDialog("Ei osata tätä vielä.");
        Dialogs.showQuestionDialog("Poisto?", "Poistetaanko tiedot? ", "Kyllä", "Ei");
    }
    
    
    /**
     * Käsittelee tapahtuman, kun halutaan lisätä uusi eläin
     */
    @FXML private void handleLisaaElain() {
        uusiElain();
    }
    
    
    /**
     * Käsittelee tapahtuman, kun halutaan lisätä uusi omistaja
     */
    @FXML private void handleLisaaOmistaja() {
        uusiOmistaja();
    }
    
    
    /**
     * Käsittelee tapahtuman, kun halutaan muokata valitun eläimen tietoja
     */
    @FXML private void handleMuokkaaElain() {
        muokkaa(1);   
    }
    
    
    /**
     * Käsittelee tapahtuman, kun halutaan muokata valitun omistajan tietoja
     */
    @FXML private void handleMuokkaaOmistaja() {
        muokkaaOmistaja();
    }
    
    
    @FXML private void handleLisaaLuovutus() {
        //
    }
    
    
    @FXML private void handleMuokkaaLuovutus() {
        //
    }
    
    
    /**
     * Käsittelee tapahtuman, kun halutaan muokata tietoja
     */
    @FXML private void handleMuokkaa() {
        muokkaa(1);
    }
    
    @FXML private void handleAvaa() {
        avaa();
    }
    
    
    
// -----------------------------------------------------------------------------------------------------------------    
    
    
    /**
     * Tekee tarvittavat muut alustukset. Vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttä, johon voidaan tulostaa eläinten tiedot.
     * Alustetaan myös eläinlistan kuuntelija. 
     */
    private void alusta() { 
       
        chooserElaimet.clear();
        chooserElaimet.addSelectionListener(e -> naytaElain());
        
        chooserOmistajat.clear();
        chooserOmistajat.addSelectionListener(e -> naytaOmistaja());
        
        edits = PentuDialogController.luoKentat(gridElain);
        editsO = new TextField[] {editNimiO, editKatuosoite, editPostinro, editPuhelinnro, editSPosti} ;
        for (TextField edit : edits) 
            if (edit != null) {
                edit.setEditable(false);
                edit.setOnMouseClicked(e -> { 
                    if (e.getClickCount() > 1) muokkaa(getFieldId(edit, 0)); 
                });
                edit.focusedProperty().addListener((a,o,n) -> kentta = getFieldId(edit,kentta));
            }
        
        chooserPennut.clear();        
        cbHaku.clear();
        
        for (int k = apuelain.ekaKentta(); k < apuelain.getKenttia() - 1; k++) {
            cbHaku.add(apuelain.getKysymys(k), null);
        }
        cbHaku.getSelectionModel().select(0);
    }
    
    
    /**
     * Näyttää listasta valitun eläimen tiedot.
     * Näyttää myös eläimen pentujen nimet ja eläimen omistajan nimen ja luovutuspäivämäärän.
     */
    private void naytaElain() {
        elainKohdalla = chooserElaimet.getSelectedObject();
        if (elainKohdalla == null) return;   
        
        PentuDialogController.naytaElain(edits, elainKohdalla);
        
        //---------------Lisätään pentujen listaan tietyn eläimen pennut-------------------------

        haePennut();    
                
        //-------Lisätään omistaja kenttään eläimen omistajan nimi ja luovutuspäivämäärä---------
        
        Omistaja o;
        int i = elainKohdalla.getOmistajaId();
        boolean vastaus = pentu.loytyyko(i);
        if (!vastaus) {
            elainKohdalla.setOmistajaId(0);
            elainKohdalla.setLuovutusPv("");
        }
        i = elainKohdalla.getOmistajaId();
        o = pentu.annaOmistaja(i);
        omistajaNimi.setText(o.getNimi()); 
        luovutusPv.setText(elainKohdalla.getLuovutusPv());
    }    
    
    
    /**
     * Näyttää listasta valitun omistajan tiedot, tilapäisesti yhteen isoon edit-kenttään
     */
    private void naytaOmistaja() {
        omistajaKohdalla = chooserOmistajat.getSelectedObject();
        if (omistajaKohdalla == null) return;
        
        OmistajaDialogController.naytaOmistaja(editsO, omistajaKohdalla);
        
        // -----------------------Lisätään omistajan pennut listaan-----------------------------
        
        haeOmistajanElaimet(omistajaKohdalla);
        tableOmistajanElaimet.setOnMouseClicked( e -> {if (  e.getClickCount() == 2)  naytaElain();} ); //TODO: korjaa
    }
    
    
    /**
     * Näyttää virheen
     * @param virhe virhe ilmoitus
     */
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
     * Alustaa kasvattajan tiedossa olevat tiedot lukemalla ne valitusta tiedostosta
     * @param nimi tiedosto josta kasvattajan tiedot luetaan
     */
    private void lueTiedosto(String nimi) {
        kasvattajannimi = nimi;
        setTitle("Kasvattaja - " + kasvattajannimi);
        File hakemisto = new File("C:\\MyTemp\\ljhovila\\ohj2\\ht\\" + kasvattajannimi.toLowerCase() );
        if (!hakemisto.exists()) hakemisto.mkdir();

        try {
            pentu.lueTiedostosta(nimi.toLowerCase());
            hae(0);
            haeOmistaja(0);
            // haePennut(0);
            // haeOmistajanElaimet(0);
        } catch (SailoException e) {
            hae(0);
            Dialogs.showMessageDialog(e.getMessage());
        }
    }
    
    
    /**
     * Hakee eläimen tiedot listaan
     * @param enro eläimen numero, joka aktivoidaan haun jälkeen
     */
    private void hae(int enro) {
        int nro = enro;
        if (nro <= 0) {
            Elain kohdalla = elainKohdalla;
            if (kohdalla != null) nro = kohdalla.getTunnusNro();
        }
        int k = cbHaku.getSelectionModel().getSelectedIndex() + apuelain.ekaKentta();
        String ehto = textHaku.getText(); 
        if (ehto.indexOf('*') < 0) ehto = "*" + ehto + "*";

        chooserElaimet.clear();

        int index = 0;
        ArrayList<Elain> elaimet = pentu.etsi(ehto, k);
        int i = 0;
        for (Elain e : elaimet) {
            if (e.getTunnusNro() == 0) continue;
            if (e.getTunnusNro() == nro) index = i;
            chooserElaimet.add(e.getNimi(), e);
            i++;
        }
        chooserElaimet.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää eläimen
    }
    
    
    /**
     * Hakee omistajan tiedot listaan
     * @param enro omistajan numero, joka aktivoidaan haun jälkeen
     */
    private void haeOmistaja(int enro) {
        chooserOmistajat.clear();

        int index = 0;
        for (int i = 1; i < pentu.getOmistajia(); i++) {
            Omistaja o = pentu.annaOmistaja(i);
            if (o.getTunnusNro() == enro) index = i;
            chooserOmistajat.add(o.getNimi(), o);
        }
        chooserOmistajat.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää omistajan
    }
    
    
    /**
     * Hakee eläinten pentujen tiedot listaan
     */
    private void haePennut() {
        chooserPennut.clear();

        ArrayList<Elain> pennut = pentu.getPennut(elainKohdalla);
        
        
        for (int k = 0; k < pennut.size(); k++) {
            Elain p = pennut.get(k);
            chooserPennut.add(p.getNimi(), p);
        }
        chooserPennut.setSelectedIndex(0);
        
        String s = Integer.toString(pennut.size());
        pentujenLkm.setText(s);
    }
    
    
    /**
     * Hakee omistajan eläinten nimet ja luovutuspäivämäärät listaan
     */
    private void haeOmistajanElaimet(Omistaja omistaja) {        
        tableOmistajanElaimet.clear();
        tableOmistajanElaimet.setEditable(false);
        tableOmistajanElaimet.setSortable(-1, false);
        
        if (omistaja == null) return;
        ArrayList<Elain> elaimet = pentu.getElaimet(omistajaKohdalla);
        if (elaimet.size() == 0) return;
        for (Elain e : elaimet) {
            tableOmistajanElaimet.add(e.getNimi(), e.getLuovutusPv());
        }
    }

    public ArrayList<String> getNimet(int s) {
        return pentu.getNimet(s);
    }
    
    /**
     * Luodaan uusi eläin, rekisteröidään se, täytetään tiedoilla ja lisätään listaan
     */
    private void uusiElain() {
        Elain uusi = new Elain();
        uusi = PentuDialogController.kysyElain(null, uusi, 1);
        if (uusi == null) return;
        uusi.rekisteroi();
        // uusi.taytaElainTiedoilla();
        try {
            pentu.lisaa(uusi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Ongelmia uuden luomisessa " + e.getMessage());
            return;
        }
        hae(uusi.getTunnusNro());
    }
    
    
    /** 
     * Tekee uuden tyhjän omistajan editointia varten 
     */ 
    public void uusiOmistaja() { 
        if(pentu.getOmistajia() == 0) {
            Omistaja u = new Omistaja();
            u.rekisteroi();
            pentu.lisaa(u);
            haeOmistaja(u.getTunnusNro());
        } else {
            Omistaja uusi = new Omistaja();
            uusi = OmistajaDialogController.kysyOmistaja(null, uusi);
            if (uusi == null) return;
            uusi.rekisteroi();
            pentu.lisaa(uusi);
            haeOmistaja(uusi.getTunnusNro());
        }
    }

    
    private void poistaElain() {
        Elain elain = elainKohdalla;
        if (elain == null) return;
        boolean vastaus = Dialogs.showQuestionDialog("Poisto?", "Poistetaanko tiedot? ", "Kyllä", "Ei");
        if (vastaus == true) {
           pentu.poistaElain(elain);
        }
        
        int index = chooserElaimet.getSelectedIndex();
        hae(0);
        chooserElaimet.setSelectedIndex(index);
    }
    
    
    private void poistaOmistaja() {
        Omistaja omistaja = omistajaKohdalla;
        if (omistaja == null) return;
        boolean vastaus = Dialogs.showQuestionDialog("Poisto?", "Poistetaanko tiedot? ", "Kyllä", "Ei");
        if (vastaus == true) {
           pentu.poistaOmistaja(omistaja);
        }
        
        int index = chooserOmistajat.getSelectedIndex();
        haeOmistaja(0);
        chooserOmistajat.setSelectedIndex(index);
    }
    

    /**
     * Tallentaa tiedot
     */
    private void tallenna() {        
        try {
            pentu.tallenna(kasvattajannimi);
        } catch (SailoException e) {
            Dialogs.showMessageDialog("Tallennuksessa tuli virhe: " + e.getMessage());
        }
    }
    
    
    /**
     * Lopettaa ohjelman
     */
    private void lopeta() {
        tallenna();
        Platform.exit();
    }
    
    
    /**
     * Avaa ikkunan, josta lisätään tietoja
     */
    private void lisaa() {
        ModalController.showModal(PentuDialogController.class.getResource("PentuDialogView.fxml"), "Lisää", null, "");
    }
    
    
    /**
     * Avaa ikkunan, jossa voidaan muokata tietoja
     * @param k kentän id numero
     */
    private void muokkaa(int k) {
        if (elainKohdalla == null) return;
        try {
            Elain elain = PentuDialogController.kysyElain(null, elainKohdalla.clone(), k);
            if (elain == null) return;
            pentu.korvaaTaiLisaa(elain);
            hae(elain.getTunnusNro()); 
        } catch (CloneNotSupportedException e) {
            //
        }      
    }
    
    
    /**
     * Avaa ikkunan, jossa voidaan muokata tietoja
     */
    private void muokkaaOmistaja() {
        if (omistajaKohdalla == null);
        Omistaja omistaja;
        try {
            omistaja = OmistajaDialogController.kysyOmistaja(null, omistajaKohdalla.clone());
            if (omistaja == null) return;
            pentu.korvaaTaiLisaa(omistaja);
            haeOmistaja(omistaja.getTunnusNro());
        } catch (CloneNotSupportedException e) {
            //
        }
    }
     
    
    /**
     * Asetetaan kontrollerin pentuviite
     * @param pentu mihin viitataan
     */
    public void setPentu(Pentu pentu) {
        this.pentu = pentu;
    }
    
    
    public Elain getElain() {
        return elainKohdalla;
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
    
    
    private void setTitle(String title) {
        ModalController.getStage(textHaku).setTitle(title);
    }
}
