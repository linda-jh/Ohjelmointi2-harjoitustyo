package pentu;

import java.io.PrintStream;
import java.util.ArrayList;

/**
 * CRC-kortit
 * @author linda
 * @version 17.2.2020
 *
 */
public class Elain {
    
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
     * Asetaa isän id:n.
     * @param i id
     */
    public void setIsa(int i) {
        isaId = i;
    }
    
    
    /**
     * Asetaa äidin id:n.
     * @param i id
     */
    public void setAiti(int i) {
        aitiId = i;
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
     * Arpoo satunnaisen luvun annetulla välillä
     * @param ala arvonnan alaraja
     * @param yla arvonna ylaraja
     * @return satunnainen luku välillä [ala, yla]
     */
    public static int rand(int ala, int yla) {
        double n = (yla-ala) * Math.random() + ala;
        return (int)Math.round(n);
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
