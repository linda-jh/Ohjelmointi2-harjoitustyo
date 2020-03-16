package fxPentu;

import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;

import fi.jyu.mit.fxgui.ComboBoxChooser;
import fi.jyu.mit.fxgui.Dialogs;
import fi.jyu.mit.fxgui.ListChooser;
import fi.jyu.mit.fxgui.ModalController;
import fi.jyu.mit.fxgui.TextAreaOutputStream;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
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
    @FXML ComboBoxChooser<String> cbHaku;
    @FXML private Label labelVirhe;
    @FXML private TextField textHaku;
    @FXML private ScrollPane panelElain;
    @FXML ListChooser<Elain> chooserElaimet;
    @FXML private ScrollPane panelOmistaja;
    @FXML ListChooser<Omistaja> chooserOmistajat;
    
    private String kasvattajannimi = "Karvatassu";
    private Pentu pentu;
    private Elain elainKohdalla;
    private TextArea areaElain = new TextArea();
    private TextArea areaOmistaja = new TextArea();
    private Omistaja omistajaKohdalla;
    
    
    

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
        // Dialogs.showMessageDialog("Ei osata tätä vielä.");
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
                  String hakukentta = cbHaku.getSelectedText();
                  String ehto = textHaku.getText(); 
                  if ( ehto.isEmpty() )
                      naytaVirhe(null);
                  else
                      naytaVirhe("Ei osata vielä hakea " + hakukentta + ": " + ehto);
              }
    
    
    /**
     * Käsittelee tapahtuman, kun painetaan kumoa
     */
    @FXML private void handleKumoa() {
        Dialogs.showMessageDialog("Ei osata tätä vielä.");
    }
    
    
    /**
     * Käsittelee tapahtuman, kun halutaan poistaa valittu eläin
     */
    @FXML private void handlePoistaElain() {
        // Dialogs.showMessageDialog("Ei osata tätä vielä.");
        Dialogs.showQuestionDialog("Poisto?", "Poistetaanko tiedot? ", "Kyllä", "Ei");
    }
    
    
    /**
     * Käsittelee tapahtuman, kun halutaan poistaa valittu omistaja
     */
    @FXML private void handlePoistaOmistaja() {
        // Dialogs.showMessageDialog("Ei osata tätä vielä.");
        Dialogs.showQuestionDialog("Poisto?", "Poistetaanko tiedot? ", "Kyllä", "Ei");
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
        muokkaa();   
    }
    
    
    /**
     * Käsittelee tapahtuman, kun halutaan muokata valitun omistajan tietoja
     */
    @FXML private void handleMuokkaaOmistaja() {
        muokkaa();
    }
    
    /**
     * Käsittelee tapahtuman, kun halutaan muokata tietoja
     */
    @FXML private void handleMuokkaa() {
        muokkaa();
    }
    
    @FXML private void handleAvaa() {
        avaa();
    }
    
    
    
// -----------------------------------------------------------------------------------------------------------------

    
    
    // ModalController.showModal(PentuGUIController.class.getResource("PentuDialogView.fxml"), "Muokkaa", null, "");
    
    /**
     * Luodaan uusi eläin, rekisteröidään se, täytetään tiedoilla ja lisätään listaan
     */
    private void uusiElain() {
        // TODO: oikeasti tässä avattaisiin uusi dialogi johon täytettäisiin uuden eläimen tiedot
        Elain uusi = new Elain();
        uusi.rekisteroi();
        uusi.taytaElainTiedoilla();
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
        Omistaja uusi = new Omistaja();
        uusi.rekisteroi();
        uusi.taytaTiedoilla();
        pentu.lisaa(uusi);
        haeOmistaja(uusi.getTunnusNro());
    }
        
    
    /**
     * Hakee eläimen tiedot listaan
     * @param enro eläimen numero, joka aktivoidaan haun jälkeen
     */
    private void hae(int enro) {
        chooserElaimet.clear();

        int index = 0;
        for (int i = 0; i < pentu.getElaimia(); i++) {
            Elain elain = pentu.annaElain(i);
            if (elain.getTunnusNro() == enro) index = i;
            chooserElaimet.add(elain.getNimi(), elain);
        }
        chooserElaimet.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää jäsenen
    }
    
    /**
     * Hakee omistajan tiedot listaan
     * @param enro omistajan numero, joka aktivoidaan haun jälkeen
     */
    private void haeOmistaja(int enro) {
        chooserOmistajat.clear();

        int index = 0;
        for (int i = 0; i < pentu.getOmistajia(); i++) {
            Omistaja o = pentu.annaOmistaja(i);
            if (o.getTunnusNro() == enro) index = i;
            chooserOmistajat.add(o.getNimi(), o);
        }
        chooserOmistajat.setSelectedIndex(index); // tästä tulee muutosviesti joka näyttää jäsenen
    }
    
    /**
     * Tallentaa tiedot
     */
    private void tallenna() {
        Dialogs.showMessageDialog("Ei osata tallentaa vielä.");
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
     */
    private void muokkaa() {
        ModalController.showModal(PentuDialogController.class.getResource("PentuDialogView.fxml"), "Muokkaa", null, "");
        
    }
     
    
    /**
     * Asetetaan kontrollerin pentuviite
     * @param pentu mihin viitataan
     */
    public void setPentu(Pentu pentu) {
        this.pentu = pentu;
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
    private void lueTiedosto(String nimi) {
        kasvattajannimi = nimi;
        setTitle("Kasvattaja - " + kasvattajannimi);
        String virhe = "Ei osata lukea vielä";  // TODO: tähän oikea tiedoston lukeminen
        // if (virhe != null) 
            Dialogs.showMessageDialog(virhe);
    }
    
    
    /**
     * Tekee tarvittavat muut alustukset. Vaihdetaan GridPanen tilalle
     * yksi iso tekstikenttä, johon voidaan tulostaa eläinten tiedot.
     * Alustetaan myös eläinlistan kuuntelija. 
     */
    private void alusta() {
        panelElain.setContent(areaElain);
        areaElain.setFont(new Font("Courier New", 12));
        panelElain.setFitToHeight(true);
        
        chooserElaimet.clear();
        chooserElaimet.addSelectionListener(e -> naytaElain());
        
        panelOmistaja.setContent(areaOmistaja);
        areaOmistaja.setFont(new Font("Courier New", 12));
        panelOmistaja.setFitToHeight(true);
        
        chooserOmistajat.clear();
        chooserOmistajat.addSelectionListener(e -> naytaOmistaja());
    }
    
    
    /**
     * Näyttää listasta valitun eläimen tiedot, tilapäisesti yhteen isoon edit-kenttään
     */
    private void naytaElain() {
        elainKohdalla = chooserElaimet.getSelectedObject();

        if (elainKohdalla == null) return;

        areaElain.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaElain)) {
            elainKohdalla.tulosta(os);
        }
    }
    
    
    /**
     * Näyttää listasta valitun omistajan tiedot, tilapäisesti yhteen isoon edit-kenttään
     */
    private void naytaOmistaja() {
        omistajaKohdalla = chooserOmistajat.getSelectedObject();

        if (omistajaKohdalla == null) return;

        areaOmistaja.setText("");
        try (PrintStream os = TextAreaOutputStream.getTextPrintStream(areaOmistaja)) {
            omistajaKohdalla.tulosta(os);
        }
    }
    
    
    private void setTitle(String title) {
        ModalController.getStage(textHaku).setTitle(title);
    }
}
