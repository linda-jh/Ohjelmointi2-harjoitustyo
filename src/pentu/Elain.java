package pentu;

import java.io.PrintStream;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * CRC-kortit
 * @author linda
 * @version 17.2.2020
 *
 */
public class Elain implements Cloneable {
    
    private int         tunnusNro;   
    private String      nimi             = "(tyhjä)";
    private String      kutsumanimi      = "";
    private String      syntymapaiva     = "";
    private String      sukupuoli        = "";
    private long        sirunumero       = 0;
    private String      lisatietoja      = "";
    private int         aitiId           = 0;
    private int         isaId            = 0;  
    private int         omistajaId       = 0;
    private String      luovutusPv       = "";
    
    private static int  seuraavaNro      = 1;
    
    
    /**
     * Oletusmuodostaja
     */
    public Elain() {
        //
    }
    
    
    /**
     * Onko eläin poika
     * @return true, jos on. Muuten false.
     */
    public boolean onkoPoika() {
        if(sukupuoli == "poika") return true;
        return false;
    }
    
    
    /**
     * Palauttaa isän id
     * @return id
     */
    public int getIsaId() {
        return isaId;
    }
    
    
    /**
     * Palauttaa äidin id
     * @return id
     */
    public int getAitiId() {
        return aitiId;
    }
    
        
    /**
     * Hakee eläimen nimen
     * @return eläimen nimen
     */
    public String getNimi() {
        return nimi;
    }
      
    
    /**
     * Hakee eläimen tunnusnumeron
     * @return tunnusnumero
     */
    public int getTunnusNro() {
        return tunnusNro;
    }
    
    
    /**
     * Palauttaa eläimen omistajan id:n
     * @return omistajan id
     */
    public int getOmistajaId() {
        return omistajaId;
    }
    
    
    /**
     * Palauttaa eläimen omistajan id:n
     * @return omistajan id
     */
    public String getLuovutusPv() {
        return luovutusPv;
    }
    
    
    /**
     * Palauttaa eläimen kutsumanimen
     * @return kutsumanimi
     */
    public String getKutsumanimi() {
        return kutsumanimi;
    }


    /**
     * Palauttaa sirunumeron merkkijonona
     * @return sirunumero
     */
    public String getSiruNro() {
        return Long.toString(sirunumero);
    }

    
    /**
     * Asettaa tunnusnumeron ja samalla varmistaa että
     * seuraava numero on aina suurempi kuin tähän mennessä suurin.
     * @param nr asetettava tunnusnumero
     */
    private void setTunnusNro(int nr) {
        tunnusNro = nr;
        if (tunnusNro >= seuraavaNro) seuraavaNro = tunnusNro + 1;
    }
    
    
    /**
     * Asettaa eläimen nimen
     * @param s nimi
     * @return virheilmoitus, null jos ok
     */
    public String setNimi(String s) {
        nimi = s;
        return null;
    }
    
    
    /**
     * Asettaa eläimen kutsumanimen
     * @param s kutsumanimi
     * @return virheilmoitus, null jos ok
     */
    public String setKutsumanimi(String s) {
        kutsumanimi = s;
        return null;
    }
    
    
    /**
     * Asettaa eläimen nimen
     * @param s nimi
     * @return virheilmoitus, null jos ok
     */
    public String setSirunumero(String s) {
        if (!s.matches("[0-9]*")) return "Sirunumeron on oltava numeerinen";
        sirunumero = Long.parseLong(s);
        return null;
    }
    
    
    /**
     * Asettaa eläimen omistajan id:n
     * @param i id
     */
    public void setOmistajaId(int i) {
        omistajaId = i;
    }
    
    
    /**
     * Asettaa eläimen luovutuspäivämään
     * @param s päivämäärä
     */
    public void setLuovutusPv(String s) {
        luovutusPv = s;
    }

    /**
     * Antaa jäsenelle seuraavan rekisterinumeron.
     * @return eläimen uusi tunnusnumero
     * @example
     * <pre name="test">
     *  Elain mirz1 = new Elain();
     *  mirz1.getTunnusNro() === 0;
     *  mirz1.rekisteroi();
     *  Elain mirz2 = new Elain();
     *  mirz2.rekisteroi();
     *  int n1 = mirz1.getTunnusNro();
     *  int n2 = mirz2.getTunnusNro();
     *  n1 === n2 - 1;
     * </pre>
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    
    /**
     * Täytetään eläimen tiedot
     */
    public void taytaElainTiedoilla() {
        nimi = "Karvatassu Mirzam" + " " + rand(1000,9999);
        kutsumanimi = "Nelli";
        syntymapaiva = "20.7.2015";
        sukupuoli = "tyttö";
        sirunumero = 985112001635024L;
        luovutusPv = "20.05.2015";
    }
    
    
    /**
     * Selvitää jäsenen tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta jäsenen tiedot otetaan
     * 
     * @example
     * <pre name="test">
     *   Elain elain = new Elain();
     *   elain.parse("   2  |  Karvatassu Mirzam   | Nelli");
     *   elain.getTunnusNro() === 2;
     *   elain.toString().startsWith("2|Karvatassu Mirzam|Nelli|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     *
     *   elain.rekisteroi();
     *   int n = elain.getTunnusNro();
     *   elain.parse(""+(n+20));       // Otetaan merkkijonosta vain tunnusnumero
     *   elain.rekisteroi();           // ja tarkistetaan että seuraavalla kertaa tulee yhtä isompi
     *   elain.getTunnusNro() === n+20+1;
     *     
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        nimi = Mjonot.erota(sb, '|', nimi);
        kutsumanimi = Mjonot.erota(sb, '|', kutsumanimi);
        syntymapaiva = Mjonot.erota(sb, '|', syntymapaiva);
        sukupuoli = Mjonot.erota(sb, '|', sukupuoli);
        sirunumero = (long) Mjonot.erota(sb, '|', sirunumero);
        aitiId = Mjonot.erota(sb, '|', aitiId);
        isaId = Mjonot.erota(sb, '|', isaId);
        omistajaId = Mjonot.erota(sb, '|', omistajaId);
        luovutusPv = Mjonot.erota(sb, '|', luovutusPv);
        lisatietoja = Mjonot.erota(sb, '|', lisatietoja);
    }
    
    
    /**
     * Palauttaa eläimen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return jäsen tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Elain elain = new Elain();
     *   elain.parse("   2  |  Karvatassu Mirzam   | Nelli");
     *   elain.toString().startsWith("2|Karvatassu Mirzam|Nelli|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     * </pre>  
     */
    @Override
    public String toString() {
        return "" +
                getTunnusNro() + "|" +
                nimi + "|" +
                kutsumanimi + "|" +
                syntymapaiva + "|" +
                sukupuoli + "|" +
                sirunumero + "|" +
                aitiId + "|" +
                isaId + "|" +
                omistajaId + "|" +
                luovutusPv + "|" +
                lisatietoja + "|";
    }


    /**
     * Arpoo satunnaisen luvun annetulla välillä
     * @param ala arvonnan alaraja
     * @param yla arvonna ylaraja
     * @return satunnainen luku välillä [ala, yla]
     */
    public static int rand(int ala, int yla) {
        double n = (yla-ala) * Math.random() + ala;
        return (int)Math.round(n);
    }

    
    @Override
    public Elain clone() throws CloneNotSupportedException {
        Elain uusi = (Elain) super.clone();
        return uusi;
    }

    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        
        Elain mirzam = new Elain();
        Elain mirzam2 = new Elain();
        
        mirzam.rekisteroi();
        mirzam2.rekisteroi();
        
        mirzam.tulosta(System.out);
        mirzam.taytaElainTiedoilla();
        mirzam.tulosta(System.out);
        

        mirzam2.tulosta(System.out);
        mirzam2.taytaElainTiedoilla();
        mirzam2.tulosta(System.out);
        
        Elain[] t = new Elain[5];
        t[0] = mirzam;
        t[1] = mirzam2;
              

    }
    
    
    /**
     * Tulostetaan olion tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro) + " " + nimi + " " + kutsumanimi);
        out.println("Syntymäpäivä: " + syntymapaiva);
        out.println("Sukupuoli: " + sukupuoli);
        out.println("Sirunumero: " + sirunumero);
        out.println("Äidin indeksi: " + aitiId);
        out.println("Isän indeksi: " + isaId);
        out.println("Lisätiedot: " + lisatietoja);       
    }
}
