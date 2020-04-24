package pentu;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Comparator;

import fi.jyu.mit.ohj2.Mjonot;

/**
 * Tietää eläimen kentät (nimi, syntymäpäivä, jne.) ja osaa tarkistaa niiden oikeellisuuden. Osaa muuttaa 
 * 1|Karvatassu Rigel|..| - merkkijonon eläimen tiedoiksi. Osaa antaa tietyn kentän tiedot ja myös asettaa tietoa 
 * tiettyyn kenttään.
 * @author Linda
 * ljhovila@student.jyu.fi
 * @version 17.2.2020
 * 
 * Mikä ei toimi:
 * - Kun nimen kirjoittamisesta tulee virhe, virheilmoituksen näyttökohdasta punainen väri ei lähde pois,
 *   vaikka virhettä ei enää olisi
 *
 */
public class Elain implements Cloneable {
    
    private int         tunnusNro;   
    private String      nimi             = "-";
    private String      kutsumanimi      = "";
    private String      syntymapaiva     = "";
    private String      sukupuoli        = "";
    private long        sirunumero       = 0;
    private int         aitiId           = 0;
    private int         isaId            = 0;
    private int         omistajaId       = 0;
    private String      luovutusPv       = "";
      
    private static int  seuraavaNro      = 1;
    
    
    /**
     * Vertailee tietyn kentän mukaan.
     * @author Linda
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
        case 3: return "" + String.format("%15d", sirunumero);
        case 4: String pv = kaannaPv(syntymapaiva); return "" + pv;
        case 5: String pv2 = kaannaPv(luovutusPv); return "" + pv2;
        case 6: return "" + sukupuoli;
        case 7: return "" + aitiId;
        case 8: return "" + isaId;
        case 9: return "" + omistajaId;
        default: return "Virhe";
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
        return 10;
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
        case 3: return "Sirunumero";
        case 4: return "Syntymäpäivä";
        case 5: return "Luovutuspäivämäärä";
        case 6: return "Sukupuoli";
        case 7: return "Äiti";
        case 8: return "Isä";
        case 9: return "Omistaja";
        default: return "Virhe";
        }
    }
    
    
    /**
     * Onko eläin poika
     * @return true, jos on. Muuten false.
     */
    public boolean onkoPoika() {
        if(sukupuoli.equals("poika")) return true;
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
     * Hakee eläimen sukupuolen.
     * @return sukupuoli
     */
    public String getSukupuoli() {
        return sukupuoli;
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
     * Palauttaa eläimen luovutuspäivämäärän
     * @return luovutuspäivämäärä
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
        aitiId = 0;
        isaId = 0;
        omistajaId = 0;
    }
    
    
    /**
     * Selvittää eläimen tiedot | erotellusta merkkijonosta
     * Pitää huolen että seuraavaNro on suurempi kuin tuleva tunnusNro.
     * @param rivi josta tiedot otetaan
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
     * </pre>
     */
    public void parse(String rivi) {
        StringBuilder sb = new StringBuilder(rivi);
        setTunnusNro(Mjonot.erota(sb, '|', getTunnusNro()));
        nimi = Mjonot.erota(sb, '|', nimi);
        kutsumanimi = Mjonot.erota(sb, '|', kutsumanimi);
        sirunumero = (long) Mjonot.erota(sb, '|', sirunumero);
        syntymapaiva = Mjonot.erota(sb, '|', syntymapaiva);
        luovutusPv = Mjonot.erota(sb, '|', luovutusPv);
        sukupuoli = Mjonot.erota(sb, '|', sukupuoli);
        aitiId = Mjonot.erota(sb, '|', aitiId);
        isaId = Mjonot.erota(sb, '|', isaId);
        omistajaId = Mjonot.erota(sb, '|', omistajaId);
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
        case 3: return "" + sirunumero;
        case 4: return "" + syntymapaiva;
        case 5: return "" + luovutusPv;
        case 6: return "" + sukupuoli;
        case 7: return "" + aitiId;
        case 8: return "" + isaId;
        case 9: return "" + omistajaId;
        default: return "Virhe";
        }
    }
    
    
    /**
     * Asettaa kentän arvoksi parametrinä tuodun omistajan nimen
     * @param jono omistajan nimi
     * @param lista lista omistajista
     */
    public void asetaOmistaja(String jono, ArrayList<Omistaja> lista) {
        String tjono = jono.trim();

        Omistaja o = new Omistaja();
        for (Omistaja omistaja : lista) {
            if (omistaja.getNimi().equals(tjono)) o = omistaja;
        }
        omistajaId = o.getTunnusNro();
    }
    
    
    /**
     * Asettaa k:n kentän arvoksi parametrina tuodun merkkijonon arvon
     * @param k kuinka monennen kentän arvo asetetaan. Vain kentät 6-8
     * @param jono jonoa joka asetetaan kentän arvoksi
     * @param t taulukko eläimistä
     * @param tiedosto kasvattajan nimi
     * @return null jos asettaminen onnistuu, muuten vastaava virheilmoitus.
     * @example
     * <pre name="test">
     *   Elain[] t = new Elain[5];
     *   Elain e = new Elain();
     *   String s = "Karhukolo";
     *   e.aseta(1,"Karhukolo Puh", t, s) === null;
     *   e.aseta(1,"Nalle Puh", t, s) === "Nimen täytyy sisältää kasvattajan nimi ja eläimen nimi";
     *   e.aseta(3,"nalle", t, s) === "Sirunumeron on oltava numeerinen";
     * </pre>
     */
    public String aseta(int k, String jono, Elain[] t, String tiedosto) {
        String tjono = jono.trim();
        StringBuilder sb = new StringBuilder(tjono);
        switch ( k ) {
        case 0:
            setTunnusNro(Mjonot.erota(sb, '§', getTunnusNro()));
            return null;
        case 1:
            if (tjono.contains(tiedosto) == false) return "Nimen täytyy sisältää kasvattajan nimi ja eläimen nimi";
            nimi = tjono;
            return null;
        case 2: 
            kutsumanimi = tjono;
            return null;
        case 3:
            if (!tjono.matches("[0-9]*")) return "Sirunumeron on oltava numeerinen";
            if (tjono.length() != 0) sirunumero = Long.parseLong(tjono);
            return null;
        case 4:
            int vuosi = Mjonot.erota(sb, '-', 0000);
            int kk = Mjonot.erota(sb, '-', 00);
            String s = String.format("%02d", kk);
            sb.append("." + s + "." + vuosi);
            syntymapaiva = sb.toString();
            return null;
        case 5:
            int vv = Mjonot.erota(sb, '-', 0000);
            int kk2 = Mjonot.erota(sb, '-', 00);
            String ss = String.format("%02d", kk2);
            sb.append("." + ss + "." + vv);
            luovutusPv = sb.toString();
            return null;
        case 6:
            sukupuoli = tjono;
            return null;
        case 7:
            Elain e = new Elain();
            for (int i = 0; i < t.length; i++) {
                if (t[i].getNimi().equals(tjono)) e = t[i];
            }
            aitiId = e.getTunnusNro();
            return null;
        case 8:
            Elain e2 = new Elain();
            for (int i = 0; i < t.length; i++) {
                if (t[i].getNimi().equals(tjono)) e2 = t[i];
            }
            isaId = e2.getTunnusNro();
            return null;        
        default:
            return "Virhe";
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
                sirunumero + "|" +
                syntymapaiva + "|" +
                luovutusPv + "|" +
                sukupuoli + "|" +
                aitiId + "|" +
                isaId + "|" +
                omistajaId + "|";
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
}
