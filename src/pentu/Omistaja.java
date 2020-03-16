package pentu;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author linda
 * @version 11.3.2020
 *
 */
public class Omistaja {
    private int         tunnusNro       = 0;
    private String      nimi            = "";
    private String      katuosoite      = "";
    private String      nroKaupunki     = "";
    private String      puhelin         = "";
    private String      sposti          = "";
    private Collection<Elain> lemmikit  = new ArrayList<Elain>();
    
    private static int seuraavaNro = 1;
    
    
    /**
     * Oletusmuodostaja
     */
    public Omistaja() {
        //
    }
    
    
    /**
     * @return tunnusnumero
     */
    public int getTunnusNro() {
        return this.tunnusNro;
    }
    
    
    /**
     * Lisätään omistajalle tunnusnumero
     * @return omistajan tunnusnumeron
     */
    public int rekisteroi() {
        tunnusNro = seuraavaNro;
        seuraavaNro++;
        return tunnusNro;
    }
    
    /**
     * Alustetaan olion tiedot.
     */
    public void taytaTiedoilla() {
        this.nimi = "Ari Katajavuori" + " " + rand(1000,9999);
        this.katuosoite = "Kuusitie 12";
        this.nroKaupunki = "56210 Virmutjoki";
        this.puhelin = "0405632942";
        this.sposti = "ari.kvuori@gmail.com";
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
     * @param os tietovirta, johon tulostetaan
     */
    public void tulosta(OutputStream os) {
        tulosta(new PrintStream(os));
    }
    
    
    /**
     * Tulostetaan omistajan tiedot
     * @param out tietovirta johon tulostetaan
     */
    public void tulosta(PrintStream out) {
        out.println(String.format("%03d", tunnusNro) + " " + nimi);
        out.println("Osoite: " + katuosoite + ", " + nroKaupunki);
        out.println("Puhelinnumero: " + puhelin);
        out.println("Sähkoposti: " + sposti);
    }    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Omistaja ariK = new Omistaja();
        ariK.taytaTiedoilla();
        ariK.rekisteroi();
        ariK.tulosta(System.out);
        // ariK.haeElaimet();
        
        Omistaja ainoA = new Omistaja();
        ainoA.taytaTiedoilla();
        ainoA.rekisteroi();
        ainoA.tulosta(System.out);
    }


    /**
     * @return omistajan nimen
     */
    public String getNimi() {
        return nimi;
    }
}
