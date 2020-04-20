package pentu;

import java.io.PrintStream;
import java.util.Comparator;

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
    private int         aitiId           = 0;
    private int         isaId            = 0;  
    private String      lisatietoja      = "";
    private int         omistajaId       = 0;
    private String      luovutusPv       = "";
    
    private static int  seuraavaNro      = 1;
    
    /**
     * Vertailee tietyn kentän mukaan.
     * @author linda
     * @version 18.4.2020
     *
     */
    public static class Vertailija implements Comparator<Elain> {
        
        private int k;
        
        /**
         * @param k minkä kentän mukaan vertaillaan.
         */
        public Vertailija(int k) {
            this.k = k;
        }

        @Override
        public int compare(Elain elain1, Elain elain2) {
            return elain1.getAvain(k).compareToIgnoreCase(elain2.getAvain(k));
        }
    }
    
    /**
     * Oletusmuodostaja
     */
    public Elain() {
        //
    }
    
    
    /**
     * Palauttaa kentän k mukaisen lajitteluavaimen
     * @param k mikä kenttä
     * @return lajitteluavain
     */
    public String getAvain(int k) {
        switch ( k ) {
        case 0: return "" + tunnusNro;
        case 1: return "" + nimi;
        case 2: return "" + kutsumanimi;
        case 3: String pv = kaannaPv(syntymapaiva); return "" + pv;
        case 4: return "" + sukupuoli;
        case 5: return "" + String.format("%15", sirunumero);
        case 6: return "" + aitiId;
        case 7: return "" + isaId;
        default: return "Ääliö";
        }
    }
    
    
    /**
     * Kääntää päivämäärän toisinpäin. pp.kk.vvvv -> vvvv.kk.pp.
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
        sb.append("." + kk);
        sb.append("." + pp);
        return sb.toString();
    }
    
    
    /**
     * Palauttaa eläimen kenttien lukumäärän
     * @return kenttien lukumäärä
     */
    public int getKenttia() {
        return 9;
    }
    
    
    /**
     * Ensimmäinen kenttä, joka on mielekäs kysyttäväksi
     * @return kentän indeksi
     */
    public int ekaKentta() {
        return 1;
    }
    
    
    /**
     * Palauttaa eläimen kentän kysymyksen, joka vastaa k:ta.
     * @param k monennenko kentän kysymys palautatetaan
     * @return kysymys, joka vastaa k:tta kenttää
     */
    public String getKysymys(int k) {
        switch (k) {
        case 0: return "Tunnusnumero";
        case 1: return "Nimi";
        case 2: return "Kutsumanimi";
        case 3: return "Syntymäpäivä";
        case 4: return "Sukupuoli";
        case 5: return "Sirunumero";
        case 6: return "Äiti";
        case 7: return "Isä";
        case 8: return "Lisätietoja";
        default: return "Ääliö";
        }
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
     * Hakee eläimen kutsumanimen.
     * @return kutsumanimi
     */
    public String getKutsumanimi() {
        return kutsumanimi;
    }
    
    
    /**
     * Hakee eläimen sirunumeron
     * @return sirunumero
     */
    public String getSiruNro() {
        return Long.toString(sirunumero);
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
     * Antaa k:n kentän sisällön merkkijonona
     * @param k monenenko kentän sisältö palautetaan
     * @return kentän sisältö merkkijonona
     */
    public String anna(int k) {
        switch ( k ) {
        case 0: return "" + tunnusNro;
        case 1: return "" + nimi;
        case 2: return "" + kutsumanimi;
        case 3: return "" + syntymapaiva;
        case 4: return "" + sukupuoli;
        case 5: return "" + sirunumero;
        case 6: return "" + aitiId;
        case 7: return "" + isaId;
        case 8: return "" + lisatietoja;
        default: return "Äääliö";
        }
    }
    
    
    /**
     * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon
     * @param k kuinka monennen kentän arvo asetetaan
     * @param jono jonoa joka asetetaan kentän arvoksi
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus.
     * TODO: testit
     * TODO: tarkistukset tähän ohjelmaan
     */
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
            kutsumanimi = tjono;
            return null;
        case 3:
            syntymapaiva = tjono;
            return null;
        case 4:
            sukupuoli = tjono;
            return null;
        case 5:
            sirunumero = Long.parseLong(tjono);
            return null;
        case 6:
            // Elain elain = pentu.Elaimet.anna(Integer.parseInt(tjono));
            // aiti = elain.getNimi();
            aitiId = Integer.parseInt(tjono); // TODO: korjaa tämä!
            return null;
        case 7:
            isaId = Integer.parseInt(tjono); // TODO: korjaa tämä!
            return null;
        case 8:
            lisatietoja = tjono;
            return null;
        default:
            return "ÄÄliö";
        }
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
        
        
        String pv = "22.02.1997";
        System.out.println(kaannaPv(pv));
        String pv1 = "22.02.";
        System.out.println(kaannaPv(pv1));
              

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
