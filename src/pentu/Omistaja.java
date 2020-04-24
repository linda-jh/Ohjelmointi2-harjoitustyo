package pentu;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Comparator;

import fi.jyu.mit.ohj2.Mjonot;


/**
 * Tietää omistajan kentät ja osaa antaa niistä tietoa ja asettaa tietoa niihin.
 * Osaa tarkistaa tietyn kentän oikeellisuuden. Osaa muuttaa 1|Ari Katajavuori|..| merkkijonon omistajan tiedoiksi.
 * @author Linda
 * ljhovila@student.jyu.fi
 * @version 11.3.2020
 *
 */
public class Omistaja implements Cloneable {
    private int              tunnusNro       = 0;
    private String           nimi            = "Kasvattaja";
    private String           katuosoite      = "";
    private String           nroKaupunki     = "";
    private String           puhelin         = "";
    private String           sposti          = "";
    
    private static int seuraavaNro = 1;
    
    
    /**
     * Vertailee tietyn kentän mukaan.
     * @author Linda
     * @version 18.4.2020
     *
     */
    public static class Vertailija implements Comparator<Omistaja> {
        
        private int k;
        
        /**
         * @param k minkä kentän mukaan vertaillaan.
         */
        public Vertailija(int k) {
            this.k = k;
        }

        @Override
        public int compare(Omistaja omistaja1, Omistaja omistaja2) {
            return omistaja1.getAvain(k).compareToIgnoreCase(omistaja2.getAvain(k));
        }
    }
    
    
    /**
     * Oletusmuodostaja
     */
    public Omistaja() {
        //
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
     * Hakee omistajan id:n
     * @return tunnusnumero
     */
    public int getTunnusNro() {
        return this.tunnusNro;
    }
    
    
    /**
     * Hakee omistajan nimen
     * @return omistajan nimen
     */
    public String getNimi() {
        return nimi;
    }


    /**
     * Hakee omistajan katuosoitteen
     * @return katuosoite
     */
    public String getKatuosoite() {
        return katuosoite;
    }


    /**
     * Hakee omistajan postinumeron ja paikkakunnan
     * @return postinumero ja paikkakunta
     */
    public String getPostinro() {
        return nroKaupunki;
    }


    /**
     * Hakee omistajan puhelinnumeron
     * @return puhelinnumero
     */
    public String getPuhelinnro() {
        return puhelin;
    }


    /**
     * Hakee omistajan sähköpostiosoitteen
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
        if (!s.matches("[0-9].*[a-zA-Z]")) return "Postinumero tai kaupunki puuttuu";
        nroKaupunki = s;
        return null;
    }
    
    
    /**
     * Asettaa olion puhelinnumeron
     * @param s numero
     * @return virheilmoitus, null jos ok
     */
    public String setPuhelin(String s) {
        if (!s.matches("[0-9]")) return "Puhelinnumero täytyy olla numeerinen";
        puhelin = s;
        return null;
    }
    
    
    /**
     * Asettaa olion sähköpostin
     * @param s sähköpostiosoite
     * @return virheilmoitus, null jos ok
     */
    public String setSPosti(String s) {
        if (!s.matches("[a-z].*[@].*[a-z]")) return "Sähköposti osoite ei ole oikeassa muodossa.";
        sposti = s;
        return null;
    }
    
    
    /**
     * Palauttaa kentän k mukaisen lajitteluavaimen
     * @param k mikä kenttä
     * @return lajitteluavain
     */
    public String getAvain(int k) {
        switch ( k ) {
        case 0: return "" + tunnusNro;
        case 1: String n = kaannaNimi(nimi); return "" + n;
        case 2: return "" + katuosoite;
        case 3: return "" + nroKaupunki;
        case 4: return "" + puhelin;
        case 5: return "" + sposti;
        default: return "Ääliö";
        }
    }
    
    
    /**
     * Kääntää nimen Etunimi Sukunimi muodosta Sukunimi Etunimi muotoon.
     * @param n nimi
     * @return käännetty nimi
     * @example
     * <pre name="test">
     *  Omistaja o = new Omistaja();
     *  String s = "Aku Ankka";
     *  String s2 = o.kaannaNimi(s);
     *  s2 === "Ankka Aku";
     * </pre>
     */
    public String kaannaNimi(String n) {
        StringBuilder sb = new StringBuilder();
        String s = n;
        sb.append(s);
        String etunimi = Mjonot.erota(sb, ' ', "");
        sb.append(" " + etunimi);
        return sb.toString();
    }
    
    
    /**
     * Lisätään omistajalle tunnusnumero
     * @return omistajan tunnusnumeron
     * @example
     * <pre name="test">
     *  Omistaja a = new Omistaja();
     *  a.getTunnusNro() === 0;
     *  a.rekisteroi();
     *  Omistaja b = new Omistaja();
     *  b.rekisteroi();
     *  int n1 = a.getTunnusNro();
     *  int n2 = b.getTunnusNro();
     *  n1 === n2 - 1;
     * </pre>
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
     * Antaa k:n kentän sisällön merkkijonona
     * @param k monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
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

    
    /**
     * Palauttaa omistajan kentän kysymykset/aiheet.
     * @param k monesko kenttä
     * @return aihe/kysymys, joka vastaa k:tta kenttää
     */
    public String getKysymys(int k) {
        switch (k) {
        case 0: return "Tunnusnumero";
        case 1: return "Nimi";
        case 2: return "Osoite";
        case 3: return "Kaupunki";
        case 4: return "Puhelinnumero";
        case 5: return "Sähköpostiosoite";
        default: return "Ääliö";
        }
    }
    
    /**
     * Tekee omistajasta kloonin ja palauttaa sen.
     */
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
