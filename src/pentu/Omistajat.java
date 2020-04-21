package pentu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

import fi.jyu.mit.ohj2.WildChars;

/**
 * @author linda
 * @version 11.3.2020
 *
 */
public class Omistajat implements Iterable<Omistaja> {
    /** Taulukko omistajista */
    private final ArrayList<Omistaja> alkiot;
    
    private int                       lkm            = 0;
    private boolean                   muutettu;
    private String                    tiedostonNimi  = "";
    private Omistaja                  apuomistaja    = new Omistaja();

    @Override
    public Iterator<Omistaja> iterator() {
        return alkiot.iterator();
    }
    
    
    /**
     * Oletusmuodostaja.
     */
    public Omistajat() {
        alkiot = new ArrayList<Omistaja>();
    }
    
    
    /**
     * Lisätään omistaja omistajiin.
     * @param o lisättävä omistaja
     */
    public void lisaa(Omistaja o) {
        alkiot.add(o);
        lkm = alkiot.size();
    }
    
    
    /**
     * Palauttaa viitteen i:teen omistajaan.
     * @param i monennenko omistajan viite halutaan
     * @return viite omistajaan, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Omistaja anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laitoin indeksi: " + i);
        return alkiot.get(i);
    }
    
    
    /**
     * Palauttaa listan omistajista.
     * @return lista
     */
    public ArrayList<Omistaja> getNimet() {
        ArrayList<Omistaja> omistajat = new ArrayList<Omistaja>();
        for (Omistaja o : alkiot) {
            omistajat.add(o);
        }
        return omistajat;
    }
    
    
    /**
     * Lukee omistajien tiedostosta.  
     * TODO Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        File tied = new File(hakemisto + "/omistajat.dat");
        tiedostonNimi = hakemisto;
        //throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
        
        try (Scanner fi = new Scanner(new FileInputStream(tied.getCanonicalPath()))) { // Jotta UTF8/ISO-8859 toimii
            String kokoNimi = fi.nextLine();
            if (kokoNimi == null) throw new SailoException("Kasvattajan nimi puuttuu");
            while ( fi.hasNext()) {
                String rivi = fi.nextLine();
                rivi = rivi.trim();
                if ("".equals(rivi) || rivi.charAt(0) == ';') continue;
                Omistaja omistaja = new Omistaja();
                omistaja.parse(rivi);
                lisaa(omistaja);
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + tied + " ei aukea.");
        } catch (IOException ex) {
            throw new SailoException("Tiedostoon " + tied + " kirjoittaminen ei onnistu.");
        }

    }
       
    
    /**
     * Tulostetaan omistajien tiedot
     */
    public void tulosta() {
        
        System.out.println("=========== Omistajat testi ===========");
        for(Omistaja o : alkiot) {
            o.tulosta(System.out);
        }
        System.out.println("Omistajien lukumäärä: " + lkm);
    }
    
    
    /**
     * Hakee ja palauttaa omistajien lukumäärän
     * @return omistajien lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * Tallentaa omistajien tiedot tiedostoon.
     * @param hakemisto mihin tallennetaan
     * @throws SailoException jos ei onnistu
     */
    public void tallenna(String hakemisto) throws SailoException {
        File tied = new File(hakemisto.toLowerCase() + "/omistajat.dat");
        try (PrintStream fo = new PrintStream(new FileOutputStream(tied.getCanonicalPath()))) {
            fo.println(hakemisto);
            for (int i = 0; i < getLkm(); i++) {
                Omistaja omistaja = anna(i);
                fo.println(omistaja.toString());
                
            }
        } catch (FileNotFoundException ex) {
            throw new SailoException("Tiedosto " + tied.getName() + " ei aukea.");
        } catch (IOException ex) {
            throw new SailoException("Tiedostoon " + tied.getName() + " kirjoittaminen ei onnistu.");
        }
    }
    
    
    /**
     * Poistaa valitun omistajan
     * @param omistaja mikä poistetaan
     */
    public void poista(Omistaja omistaja) {
        int nro = omistaja.getTunnusNro();
        
        for (int i = 0; i < alkiot.size(); i++) {
            Omistaja o = alkiot.get(i);
            if(o.getTunnusNro() == nro) alkiot.remove(i);
        }
        lkm = alkiot.size();
    }
    

    /**
     * Etsii löytyykö annettulla id:llä omistajaa
     * @param i id, jota etsitään
     * @return true, jos löytyy. Muuten false.
     */
    public boolean loytyyko(int i) {
        for (Omistaja o : alkiot) {
            if (o.getTunnusNro() == i) return true;
        }
        return false;
    }
    
    
    /**
     * @param omistaja lisättävän viite
     */
    public void korvaaTaiLisaa(Omistaja omistaja) {
        int id = omistaja.getTunnusNro();
        int i = 0;
        for (Omistaja o : alkiot) {
            if (o.getTunnusNro() == id) {
                alkiot.set(i, omistaja);
                muutettu = true;
                return;
            }
            i++;
        }
        lisaa(omistaja);
    }
    
    
    /**
     * Palauttaa listassa hakuehtoon vastaavien eläinten viitteet
     * @param hakuehto hakuehto
     * @param k etsittävän kentän indeksi
     * @return lista löydetyistä eläimistä
     * @example
     * <pre name="test">
     * Elaimet elaimet = new Elaimet();
     * Elain elain1 = new Elain(); elain1.parse("1|Karvatassu Rigel|Simba|15.04.2018|poika|985112001635024|5|6|0||-|");
     * Elain elain2 = new Elain(); elain2.parse("2|Karvatassu Spica|Nala|15.04.2018|tyttö|985112001635025|5|6|2|22.07.2018|-|");
     * Elain elain3 = new Elain(); elain3.parse("3|Karvatassu Castor|Mufasa|15.04.2018|poika|985112001635026|5|6|3|27.07.2018|-|");
     * Elain elain4 = new Elain(); elain4.parse("4|Karvatassu Hadar|Pumba|15.04.2018|poika|985112001635027|5|6|0||-|");
     * Elain elain5 = new Elain(); elain5.parse("5|Karvatassu Mirzam|Nelli|20.07.2015|tyttö|985112001346227|7|0|0||-|");
     * Elain elain6 = new Elain(); elain5.parse("6|Karvatassu Regor|Pörrö|02.03.2016|poika|985112001401021|0|0|0||-|");
     * Elain elain7 = new Elain(); elain5.parse("7|Karvatassu Jupiter|Pilkku|30.05.2017|tyttö|985112001499302|0|0|0||-|");
     * elaimet.lisaa(elain1); elaimet.lisaa(elain2); elaimet.lisaa(elain3); elaimet.lisaa(elain4); elaimet.lisaa(elain5); 
     * elaimet.lisaa(elain6); elaimet.lisaa(elain7);
     * List<Elain> lista;
     * lista = elaimet.etsi("*i*", 1);
     * lista.size() === 3;
     * lista.get(0) == elain1 === true;
     * lista.get(2) == elain5 === true;
     * 
     * lista = elaimet.etsi(null, -1);
     * lista.size() === 7;
     * </pre>
     */
    public ArrayList<Omistaja> etsi(String hakuehto, int k) {
        String ehto = "*";
        if (hakuehto != null && hakuehto.length() > 0) ehto = hakuehto;
        int hk = k;
        if (hk < 0) hk = apuomistaja.ekaKentta();
        
        ArrayList<Omistaja> lista = new ArrayList<Omistaja>();
        
        if (hk == 5 || hk == 6 || hk == 7) {
            for (int i = 1; i < lkm; i++) {
                String nimi = anna(hk).getNimi();
                if (WildChars.onkoSamat(nimi, ehto))
                    lista.add(alkiot.get(i));
            }
        } else {
            for (int i = 1; i < lkm; i++) {
                if (WildChars.onkoSamat(alkiot.get(i).anna(hk), ehto))
                    lista.add(alkiot.get(i));
            }
        }
        Collections.sort(lista, new Omistaja.Vertailija(hk));
        
        return lista;
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Omistajat ihmiset = new Omistajat();
        Omistaja ari = new Omistaja();
        ari.taytaTiedoilla();
        ari.rekisteroi();
        Omistaja aino = new Omistaja();
        aino.taytaTiedoilla();
        aino.rekisteroi();
        
        ihmiset.lisaa(ari);
        ihmiset.lisaa(aino);

        ihmiset.tulosta();
    }
}
