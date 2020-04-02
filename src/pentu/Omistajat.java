package pentu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author linda
 * @version 11.3.2020
 *
 */
public class Omistajat implements Iterable<Omistaja> {
    /** Taulukko omistajista */
    private final ArrayList<Omistaja> alkiot         = new ArrayList<Omistaja>();
    
    private int                       lkm            = 0;
    // private String                    tiedostonNimi  = "";

    @Override
    public Iterator<Omistaja> iterator() {
        return alkiot.iterator();
    }
    
    
    /**
     * Oletusmuodostaja.
     */
    public Omistajat() {
        //
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
     * @param i monennenko eläimen viite halutaan
     * @return viite omistajaan, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Omistaja anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laitoin indeksi: " + i);
        return alkiot.get(i);
    }
    
    
    /**
     * Lukee omistajien tiedostosta.  
     * TODO Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        File tied = new File(hakemisto + "/omistajat.dat");
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
     * Palauttaa i:n eläimen
     * @param i monesko eläin palautetaan
     * @return viite i:teen eläimeen 
     * @throws IndexOutOfBoundsException jos tulee poikkeus
     */
    public Elain annaElain(int i) throws IndexOutOfBoundsException {
        Elaimet elaimet = new Elaimet();
        return elaimet.anna(i);
    }
    
    
    /**
     * Tallentaa omistajien tiedot tiedostoon.
     * @throws SailoException jos ei onnistu
     */
    public void tallenna() throws SailoException {
        File tied = new File("karvatassu/omistajat.dat");
        try (PrintStream fo = new PrintStream(new FileOutputStream(tied.getCanonicalPath()))) {
            fo.println("Karvatassu");
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
