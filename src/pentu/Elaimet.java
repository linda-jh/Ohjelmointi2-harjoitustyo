package pentu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

import fi.jyu.mit.ohj2.WildChars;

/**
 * Pitää yllä varsinaista rekisteriä. Osaa lukea ja kirjottaa tiedostoon sekä poistata, lisätä, lajitella ja etsiä eläimiä.
 * @author Linda
 * ljhovila@stundent.jyu.fi
 * @version 17.2.2020
 */
public class Elaimet {
    
    private static final int    MAX_ELAIMIA     = 5;
    private int                 lkm             = 0;
    private Elain[]             alkiot;
    private String              tiedostonNimi   = "";
    private Elain               apuelain        = new Elain();
    

    /**
     * Oletusmuodostaja. Lisää eläinten taulukkoon automaattisesti tyhjän eläimen.
     */
    public Elaimet() {
        alkiot = new Elain[MAX_ELAIMIA];
        // lisaa(new Elain());
    }
    
    
    /**
     * Asettaa ComboBoxChoosereista saadut tiedot. Viedään eteenpäin myös taulukko eläimistä parametrinä.
     * @param elain kenen kohdalla ollaan
     * @param k mikä kenttä
     * @param s kentän teksti
     * @return virheilmoitus, jos tuli. Muuten null
     */
    public String aseta(Elain elain, int k, String s) {
        return elain.aseta(k, s, alkiot, tiedostonNimi);
    }
    
    
    /**
     * Lisää uuden eläimen tietorakenteeseen.  Ottaa eläimen omistukseensa.
     * @param elain liitettävän eläimen viite.
     * @example
     * <pre name="test">
     * Elaimet elaimet = new Elaimet();
     * Elain mirz1 = new Elain(), mirz2 = new Elain();
     * elaimet.getLkm() === 0;
     * elaimet.lisaa(mirz1); elaimet.getLkm() === 1;
     * elaimet.lisaa(mirz2); elaimet.getLkm() === 2;
     * elaimet.lisaa(mirz1); elaimet.getLkm() === 3;
     * elaimet.anna(0) === mirz1;
     * elaimet.anna(1) === mirz2;
     * elaimet.anna(2) === mirz1;
     * elaimet.anna(1) == mirz1 === false;
     * elaimet.anna(1) == mirz2 === true;
     * elaimet.lisaa(mirz1); elaimet.getLkm() === 4;
     * elaimet.lisaa(mirz2); elaimet.getLkm() === 5;
     * elaimet.lisaa(mirz2); elaimet.getLkm() === 6; 
     * </pre>
     */
    public void lisaa(Elain elain) {
        if (lkm >= alkiot.length) {
            Elain[] uusi = new Elain[lkm+1];
            for (int i = 0; i < alkiot.length; i++) {
                uusi[i] = alkiot[i];
            }
            uusi[lkm] = elain;
            alkiot = uusi;
            lkm++;
        } else {
            alkiot[lkm] = elain;
            lkm++;
        }
    }
    
    
    /**
     * Hakee lukumäärän arvon
     * @return lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
        
    
    /**
     * Palauttaa viitteen i:teen eläimeen.
     * @param i monennenko eläimen viite halutaan
     * @return viite eläimeen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Elain anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot[i];
    }
    
    
    /**
     * Etsii eläimen pennut ja lisää ne listaan. 
     * @param e eläin
     * @return lista pennuista
     * @example
     * <pre name="test">
     * Elaimet elaimet = new Elaimet();
     * Elain elain = new Elain();
     * Elain elain1 = new Elain(); elain1.parse("1|Karvatassu Rigel|Simba|15.04.2018||985112001635024|poika|5|6|0|");
     * Elain elain2 = new Elain(); elain2.parse("2|Karvatassu Spica|Nala|15.04.2018|22.07.2018|985112001635025|tyttö|5|6|2|");
     * Elain elain3 = new Elain(); elain3.parse("3|Karvatassu Castor|Mufasa|15.04.2018|27.07.2018|985112001635026|poika|5|6|3|");
     * Elain elain4 = new Elain(); elain4.parse("4|Karvatassu Hadar|Pumba|15.04.2018||985112001635027|poika|5|6|0|");
     * Elain elain5 = new Elain(); elain5.parse("5|Karvatassu Mirzam|Nelli|20.07.2015||985112001346227|tyttö|7|0|0|");
     * Elain elain6 = new Elain(); elain6.parse("6|Karvatassu Regor|Pörrö|02.03.2016||985112001401021|poika|0|0|0|");
     * Elain elain7 = new Elain(); elain7.parse("7|Karvatassu Jupiter|Pilkku|30.05.2017||985112001499302|tyttö|0|0|0|");
     * elaimet.lisaa(elain); elaimet.lisaa(elain1); elaimet.lisaa(elain2); elaimet.lisaa(elain3); elaimet.lisaa(elain4); 
     * elaimet.lisaa(elain5); elaimet.lisaa(elain6); elaimet.lisaa(elain7);
     * ArrayList<Elain> p = elaimet.pennut(elain5);
     * p.size() === 4;
     * p.get(3).getTunnusNro() === 4;
     * </pre>
     */
    public ArrayList<Elain> pennut(Elain e) {
        ArrayList<Elain> l = new ArrayList<Elain>();
        if (alkiot.length != 0) {
            for (int i = 0; i < lkm; i++) {
                if (e.getTunnusNro() != alkiot[i].getTunnusNro()) {
                    if (e.getTunnusNro() == alkiot[i].getIsaId() || e.getTunnusNro() == alkiot[i].getAitiId()) {
                        l.add(alkiot[i]);
                    }
                }
            }
        }
        return l;
    }
    
    
    /**
     * Haetaan omistajan omistamat eläimet
     * @param o kenen eläimet haetaan
     * @return lista eläimistä
     * @example
     * <pre name="test">
     * Elaimet elaimet = new Elaimet();
     * Elain elain = new Elain();
     * Elain elain1 = new Elain(); elain1.parse("1|Karvatassu Rigel|Simba|15.04.2018||985112001635024|poika|5|6|0|");
     * Elain elain2 = new Elain(); elain2.parse("2|Karvatassu Spica|Nala|15.04.2018|22.07.2018|985112001635025|tyttö|5|6|2|");
     * Elain elain3 = new Elain(); elain3.parse("3|Karvatassu Castor|Mufasa|15.04.2018|27.07.2018|985112001635026|poika|5|6|3|");
     * Elain elain4 = new Elain(); elain4.parse("4|Karvatassu Hadar|Pumba|15.04.2018||985112001635027|poika|5|6|0|");
     * Elain elain5 = new Elain(); elain5.parse("5|Karvatassu Mirzam|Nelli|20.07.2015||985112001346227|tyttö|7|0|0|");
     * Elain elain6 = new Elain(); elain6.parse("6|Karvatassu Regor|Pörrö|02.03.2016||985112001401021|poika|0|0|0|");
     * Elain elain7 = new Elain(); elain7.parse("7|Karvatassu Jupiter|Pilkku|30.05.2017||985112001499302|tyttö|0|0|0|");
     * elaimet.lisaa(elain); elaimet.lisaa(elain1); elaimet.lisaa(elain2); elaimet.lisaa(elain3); elaimet.lisaa(elain4); 
     * elaimet.lisaa(elain5); elaimet.lisaa(elain6); elaimet.lisaa(elain7);
     * Omistaja o = new Omistaja();
     * ArrayList<Elain> p = elaimet.omistajanElaimet(o);
     * p.size() === 6;
     * p.get(3).getTunnusNro() === 5;
     * </pre>
     */
    public ArrayList<Elain> omistajanElaimet(Omistaja o) {
        ArrayList<Elain> l = new ArrayList<Elain>();
        if (alkiot.length != 0) {
            for (int i = 0; i < lkm; i++) {
                if (o.getTunnusNro() == alkiot[i].getOmistajaId()) l.add(alkiot[i]);
            }
        }
        return l;
    }
    
    
    /**
     * Hakee listaan eläinten viitteet sukupuolen perusteella.
     * @param s määrittää sukupuolen. 0 jos haetaan poikia, 1 jos tyttöjä.
     * @return listan nimistä.
     * @example
     * <pre name="test">
     * Elaimet elaimet = new Elaimet();
     * Elain elain1 = new Elain(); elain1.parse("1|Karvatassu Rigel|Simba|15.04.2018||985112001635024|poika|5|6|0|");
     * Elain elain2 = new Elain(); elain2.parse("2|Karvatassu Spica|Nala|15.04.2018|22.07.2018|985112001635025|tyttö|5|6|2|");
     * Elain elain3 = new Elain(); elain3.parse("3|Karvatassu Castor|Mufasa|15.04.2018|27.07.2018|985112001635026|poika|5|6|3|");
     * Elain elain4 = new Elain(); elain4.parse("4|Karvatassu Hadar|Pumba|15.04.2018||985112001635027|poika|5|6|0|");
     * Elain elain5 = new Elain(); elain5.parse("5|Karvatassu Mirzam|Nelli|20.07.2015||985112001346227|tyttö|7|0|0|");
     * Elain elain6 = new Elain(); elain6.parse("6|Karvatassu Regor|Pörrö|02.03.2016||985112001401021|poika|0|0|0|");
     * Elain elain7 = new Elain(); elain7.parse("7|Karvatassu Jupiter|Pilkku|30.05.2017||985112001499302|tyttö|0|0|0|");
     * elaimet.lisaa(elain1); elaimet.lisaa(elain2); elaimet.lisaa(elain3); elaimet.lisaa(elain4); 
     * elaimet.lisaa(elain5); elaimet.lisaa(elain6); elaimet.lisaa(elain7);
     * ArrayList<Elain> p = elaimet.getNimet(1);
     * p.size() === 3;
     * p.get(1).getTunnusNro() === 5;
     * ArrayList<Elain> p2 = elaimet.getNimet(0);
     * p2.size() === 4;
     * </pre>
     */
    public ArrayList<Elain> getNimet(int s) {
        ArrayList<Elain> nimet = new ArrayList<Elain>();
                
        if (alkiot.length != 0) {
            if (s == 0) {
                for (int i = 0; i < lkm; i++) {
                        if (alkiot[i].onkoPoika() == true) nimet.add(alkiot[i]);
                }
            } else {
                for (int i = 0; i < lkm; i++) {
                        if (alkiot[i].onkoPoika() == false) nimet.add(alkiot[i]);
                }
            }
        }
        return nimet;
    }
    
    
    /**
     * Tallentaa eläinten tiedot tiedostoon.
     * @param hakemisto minne tallennetaan
     * @throws SailoException jos ei onnistu
     */
    public void tallenna(String hakemisto) throws SailoException {
        File tied = new File(hakemisto.toLowerCase() + "/elaimet.dat");
        try (PrintStream fo = new PrintStream(new FileOutputStream(tied.getCanonicalPath()))) {
            fo.println(hakemisto);
            for (int i = 0; i < getLkm(); i++) {
                Elain elain = anna(i);
                fo.println(elain.toString());
                
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + tied.getName() + " ei aukea.");
        } catch (IOException ex) {
            throw new SailoException("Tiedostoon " + tied.getName() + " kirjoittaminen ei onnistu.");
        }
    }
    
    
    /**
     * Lukee eläinten tiedostosta.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        File tied = new File(hakemisto.toLowerCase() + "/elaimet.dat");
        tiedostonNimi = hakemisto;
        
        try (Scanner fi = new Scanner(new FileInputStream(tied.getCanonicalPath()))) {
            String kokoNimi = fi.nextLine();
            if (kokoNimi == null) throw new SailoException("Kasvattajan nimi puuttuu");
            while ( fi.hasNext()) {
                String rivi = fi.nextLine();
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == ';') continue;
                Elain elain = new Elain();
                elain.parse(rivi);
                lisaa(elain);
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + tied + " ei aukea.");
        } catch (IOException ex) {
            throw new SailoException("Tiedostoon " + tied + " kirjoittaminen ei onnistu.");
        }
    }
    
    
    /**
     * Etsii eläimen indeksin taulukosta.
     * @param id eläimen tunnusnumero
     * @return indeksin, jos ei löydy niin palauttaa -1.
     */
    public int etsiId(int id) { 
        for (int i = 0; i < lkm; i++) 
             if (id == alkiot[i].getTunnusNro()) return i; 
        return -1; 
    } 
    
    
    /**
     * Palauttaa listassa hakuehtoon vastaavien eläinten viitteet
     * @param hakuehto hakuehto
     * @param k etsittävän kentän indeksi
     * @return lista löydetyistä eläimistä
     * @example
     * <pre name="test">
     * Elaimet elaimet = new Elaimet();
     * Elain elain = new Elain();
     * Elain elain1 = new Elain(); elain1.parse("1|Karvatassu Rigel|Simba|15.04.2018||985112001635024|poika|5|6|0||");
     * Elain elain2 = new Elain(); elain2.parse("2|Karvatassu Spica|Nala|15.04.2018|22.07.2018|985112001635025|tyttö|5|6|2|");
     * Elain elain3 = new Elain(); elain3.parse("3|Karvatassu Castor|Mufasa|15.04.2018|27.07.2018|985112001635026|poika|5|6|3|");
     * Elain elain4 = new Elain(); elain4.parse("4|Karvatassu Hadar|Pumba|15.04.2018||985112001635027|poika|5|6|0|");
     * Elain elain5 = new Elain(); elain5.parse("5|Karvatassu Mirzam|Nelli|20.07.2015||985112001346227|tyttö|7|0|0|");
     * Elain elain6 = new Elain(); elain6.parse("6|Karvatassu Regor|Pörrö|02.03.2016||985112001401021|poika|0|0|0|");
     * Elain elain7 = new Elain(); elain7.parse("7|Karvatassu Jupiter|Pilkku|30.05.2017||985112001499302|tyttö|0|0|0|");
     * elaimet.lisaa(elain); elaimet.lisaa(elain1); elaimet.lisaa(elain2); elaimet.lisaa(elain3); elaimet.lisaa(elain4); 
     * elaimet.lisaa(elain5); elaimet.lisaa(elain6); elaimet.lisaa(elain7);
     * ArrayList<Elain> lista = elaimet.etsi("*i*", 1);
     * lista.size() === 4;
     * lista.get(0) == elain7 === true;
     * lista.get(2) == elain1 === true;
     * 
     * lista = elaimet.etsi(null, -1);
     * lista.size() === 7;
     * </pre>
     */
    public ArrayList<Elain> etsi(String hakuehto, int k) {
        String ehto = "*";
        if (hakuehto != null && hakuehto.length() > 0) ehto = hakuehto;
        int hk = k;
        if (hk < 0) hk = apuelain.ekaKentta();
        ArrayList<Elain> lista = new ArrayList<Elain>();
        
        if (hk == 7 || hk == 8) { 
            for (int i = 1; i < lkm; i++) {
                String s = alkiot[i].anna(hk);
                String nimi = "";
                for (int n = 0; n < alkiot.length; n++) {
                    if (alkiot[n].getTunnusNro() == Integer.parseInt(s)) nimi = alkiot[n].getNimi();
                }
                if (WildChars.onkoSamat(nimi, ehto))
                    lista.add(alkiot[i]);
                
            }
        } else {
            for (int i = 1; i < lkm; i++) {
                if (WildChars.onkoSamat(alkiot[i].anna(hk), ehto))
                    lista.add(alkiot[i]);
            }
        }
        
        Collections.sort(lista, new Elain.Vertailija(hk));
        return lista;
    }
    
    
    /**
     * Etsii eläimiä omistaja-kentän perusteella.
     * @param hakuehto kentän teksti
     * @param omistajat lista omistajista
     * @return listan löydetyistä eläimistä
     */
    public ArrayList<Elain> etsiOmistajanPerusteella(String hakuehto, ArrayList<Omistaja> omistajat) {
        String ehto = "*";
        ArrayList<Elain> lista = new ArrayList<Elain>();
        if (hakuehto != null && hakuehto.length() > 0) ehto = hakuehto;
        
        for (int i = 0; i < lkm; i++) {
            String s = alkiot[i].anna(9);
            String nimi = "";
            for (Omistaja o : omistajat) {
                if (o.getTunnusNro() == Integer.parseInt(s)) nimi = o.getNimi();
            }
            if (WildChars.onkoSamat(nimi, ehto))
                lista.add(alkiot[i]);
        }
        
        return lista;
    }
    

    /**
     * Poistaa eläimen tiedostosta ja taulukosta.
     * @param elain mikä eläin poistetaan
     * @example
     * <pre name="test">
     *  Elaimet e = new Elaimet();
     *  Elain a = new Elain(); Elain b = new Elain(); Elain c = new Elain();
     *  e.lisaa(a); e.lisaa(b); e.lisaa(c);
     *  e.getLkm() === 3;
     *  e.poista(b);
     *  e.getLkm() === 2;
     * </pre>
     */
    public void poista(Elain elain) {
        int nro = elain.getTunnusNro();
        int ind = etsiId(nro);
        
        if (ind < 0) return;
        lkm--; 
        for (int i = ind; i < lkm; i++) 
             alkiot[i] = alkiot[i + 1]; 
        alkiot[lkm] = null;
    }
    
    
    /**
     * Korvaa eläimen tiedot tietorakenteeseen. Etsitään samalla tunnusnumerolla oleva eläin
     * ja jos ei löydy, niin lisätään uutena eläimenä.
     * @param elain lisättävän viite
     */
    public void korvaaTaiLisaa(Elain elain) {
        int id = elain.getTunnusNro();
        for (int i = 0; i < lkm; i++) {
            if (alkiot[i].getTunnusNro() == id) {
                alkiot[i] = elain;
                return;
            }
        }
        lisaa(elain);
    }
    
    
    /**
     * Testiohjelma eläimille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Elaimet elaimet = new Elaimet();
        Elain mirzam = new Elain(), mirzam2 = new Elain(), teppo = new Elain();
        
        mirzam.rekisteroi();
        mirzam.taytaElainTiedoilla();
        mirzam2.rekisteroi();
        mirzam2.taytaElainTiedoilla();
        teppo.rekisteroi();
        teppo.taytaElainTiedoilla();
       
       
        elaimet.lisaa(mirzam);
        elaimet.lisaa(mirzam2);
        elaimet.lisaa(mirzam);
        elaimet.lisaa(teppo);
        elaimet.lisaa(mirzam2);
        elaimet.lisaa(mirzam);
        elaimet.lisaa(mirzam2);
        
        System.out.println("=========== Eläimet testi ===========");
        
        for (int i = 0; i < elaimet.getLkm(); i++) {
            Elain elain = elaimet.anna(i);
            System.out.println("Eläimen nro: " + i);
            elain.tulosta(System.out);
        }
        System.out.println("\nEläimiä on " + elaimet.lkm);
        
        int tNro = teppo.getTunnusNro();
        elaimet.poista(teppo);
        Elain e2 = new Elain();
        
        for (int i = 0; i < elaimet.getLkm(); i++) {
            Elain e = elaimet.anna(i);
            if (e.getTunnusNro() == tNro) e2 = e;
        }
        
        if ( e2.getTunnusNro() == tNro) System.out.println("Eläin ei poistunut!");
        else System.out.println("Eläintä ei löydy! Eläimiä on enää " + elaimet.lkm);
    }
}
