package pentu;

import java.io.OutputStream;
import java.io.PrintStream;
import fi.jyu.mit.ohj2.Mjonot;


/**
 * @author linda
 * @version 11.3.2020
 *
 */
public class Omistaja implements Cloneable {
    private int              tunnusNro       = 0;
    private String           nimi            = "";
    private String           katuosoite      = "";
    private String           nroKaupunki     = "";
    private String           puhelin         = "";
    private String           sposti          = "";
    
    private static int seuraavaNro = 1;
    
    
    /**
     * Oletusmuodostaja
     */
    public Omistaja() {
        nimi = "Kasvattaja";
    }
    
    
    /**
     * Palauttaa omistajan kenttien lukumäärän
     * @return kenttien lukumäärä
     */
    public int getKenttia() {
        return 6;
    }
    
    
    /**
     * Ensimmäinen kenttä, joka on mielekäs kysyttäväksi
     * @return kentän indeksi
     */
    public int ekaKentta() {
        return 1;
    }
    
    
    /**
     * @return tunnusnumero
     */
    public int getTunnusNro() {
        return this.tunnusNro;
    }
    
    
    /**
     * @return omistajan nimen
     */
    public String getNimi() {
        return nimi;
    }


    /**
     * @return katuosoite
     */
    public String getKatuosoite() {
        return katuosoite;
    }


    /**
     * @return postinumero ja paikkakunta
     */
    public String getPostinro() {
        return nroKaupunki;
    }


    /**
     * @return puhelinnumero
     */
    public String getPuhelinnro() {
        return puhelin;
    }


    /**
     * @return sähköposti
     */
    public String getSPosti() {
        return sposti;
    }
    
    
    /**
     * Asettaa olion nimen
     * @param s nimi
     * @return virheilmoitus, null jos ok
     */
    public String setNimi(String s) {
        nimi = s;
        return null;
    }
    
    
    /**
     * Asettaa olion katuosoitteen
     * @param s osoite
     * @return virheilmoitus, null jos ok
     */
    public String setKatuosoite(String s) {
        katuosoite = s;
        return null;
    }
    
    
    /**
     * Asettaa postinumeron ja kaupungin
     * @param s postinumero ja kaupunki
     * @return virheilmoitus, null jos ok
     */
    public String setPostinro(String s) {
        // if (!s.matches("[0-9]*")) return "Sirunumeron on oltava numeerinen";
        nroKaupunki = s;
        return null;
    }
    
    
    /**
     * Asettaa olion puhelinnumeron
     * @param s numero
     * @return virheilmoitus, null jos ok
     */
    public String setPuhelin(String s) {
        puhelin = s;
        return null;
    }
    
    
    /**
     * Asettaa olion sähköpostin
     * @param s sähköpostiosoite
     * @return virheilmoitus, null jos ok
     */
    public String setSPosti(String s) {
        sposti = s;
        return null;
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
     * Palauttaa i:n eläimen
     * @param i monesko eläin palautetaan
     * @return viite i:teen eläimeen 
     * @throws IndexOutOfBoundsException jos tulee poikkeus
     
    public Elain annaElain(int i) throws IndexOutOfBoundsException {
        Elaimet elaimet = new Elaimet();
        return elaimet.anna(i);
    }*/
    
    
    /**
     * Palauttaa eläimen tiedot merkkijonona jonka voi tallentaa tiedostoon.
     * @return jäsen tolppaeroteltuna merkkijonona 
     * @example
     * <pre name="test">
     *   Omistaja omistaja = new Omistaja();
     *   omistaja.parse("   3  |  Ari Katajavuori   | Kuusitie 12");
     *   omistaja.toString().startsWith("3|Ari Katajavuori|Kuusitie 12|") === true; // on enemmäkin kuin 3 kenttää, siksi loppu |
     * </pre>  
     */
    @Override
    public String toString() {
        return "" +
                getTunnusNro() + "|" +
                nimi + "|" +
                katuosoite + "|" +
                nroKaupunki + "|" +
                puhelin + "|" +
                sposti + "|";
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
    /**
     * Selvitää harrastuksen tiedot | erotellusta merkkijonosta.
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusnro.
     * @param rivi josta harrastuksen tiedot otetaan
     * @example
     * <pre name="test">
     *   Omistaja omistaja = new Omistaja();
     *   omistaja.parse("   1   |  Ari Katajavuori  |   Kuusitie 12  ");
     *   omistaja.getTunnusNro() === 1;
     *   omistaja.toString().startsWith("1|Ari Katajavuori|Kuusitie 12|") === true;
     *   
     *   omistaja.rekisteroi();
     *   int n = omistaja.getTunnusNro();
     *   omistaja.parse(""+(n+20));
     *   omistaja.rekisteroi();
     *   omistaja.getTunnusNro() === n+20+1;
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        nimi = Mjonot.erota(sb, '|', nimi);
        katuosoite = Mjonot.erota(sb, '|', katuosoite);
        nroKaupunki = Mjonot.erota(sb, '|', nroKaupunki);
        puhelin = Mjonot.erota(sb, '|', puhelin);
        sposti = Mjonot.erota(sb, '|', sposti);
    }
    
    
    /**

    public int getKenttia() {
        return 6;
    }
   
    public int ekaKentta() {
        return 1;
    }
    
    public String anna(int k) {
        switch ( k ) {
        case 0: return "" + tunnusNro;
        case 1: return "" + nimi;
        case 2: return "" + katuosoite;
        case 3: return "" + nroKaupunki;
        case 4: return "" + puhelin;
        case 5: return "" + sposti;
        default: return "Äääliö";
        }
    }
    
    public String aseta(int k, String jono) {
        String tjono = jono.trim();
        StringBuffer sb = new StringBuffer(tjono);
        switch ( k ) {
        case 0:
            setTunnusNro(Mjonot.erota(sb, '§', getTunnusNro()));
            return null;
        case 1:
            nimi = tjono;
            return null;
        case 2:
            katuosoite = tjono;
            return null;
        case 3:
            nroKaupunki = tjono;
            return null;
        case 4:
            puhelin = tjono;
            return null;
        case 5:
            sposti = tjono;
            return null;
        default:
            return "ÄÄliö";
        }
    }

    public String getKysymys(int k) {
        switch (k) {
        case 0: return "Tunnusnumero";
        case 1: return "Nimi";
        case 2: return "Osoite";
        case 3: return "Postinumero";
        case 4: return "Puhelinnumero";
        case 5: return "Sähköpostiosoite";
        default: return "Ääliö";
        }
    }*/
    
    
    @Override
    public Omistaja clone() throws CloneNotSupportedException {
        Omistaja uusi = (Omistaja) super.clone();
        return uusi;
    }
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Omistaja ariK = new Omistaja();
        ariK.taytaTiedoilla();
        ariK.rekisteroi();
        ariK.tulosta(System.out);
        
        Omistaja ainoA = new Omistaja();
        ainoA.taytaTiedoilla();
        ainoA.rekisteroi();
        ainoA.tulosta(System.out);
    }
}
