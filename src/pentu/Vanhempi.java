package pentu;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * @author linda
 * @version 15.3.2020
 *
 */
public class Vanhempi {
    
    private int         nro;
    private int         isaId       = 0;
    private int         aitiId      = 0;
    private int         omistaja    = 1;
    private String      pv          = "";
    
    
    /**
     * Oletusmuodostaja
     * @param n Elain-olion tunnusnumero
     */
    public Vanhempi(int n) {
        nro = n;
    }
    
    
    /**
     * Hakee ja palauttaa
     * @return luovutuspäivämäärän
     */
    public String getLuovutusPv() {
        return pv;
    }
    
    
    /**
     * Täyttää olion atribuutit tiedoilla.
     */
    public void taytaTiedoilla() {
        aitiId = 4;
        isaId = 5;
        pv = "23.6.2017";
        omistaja = 1;
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
        out.println(rand(1000,9999));
        out.println(String.format("%03d", nro));
        out.println("Äiti id: " + aitiId);
        out.println("Isä id: " + isaId);
        out.println("Omistaja id: " + omistaja);
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {        
        Vanhempi v = new Vanhempi(0);
        v.taytaTiedoilla();
        v.tulosta(System.out);
        
    }
}
