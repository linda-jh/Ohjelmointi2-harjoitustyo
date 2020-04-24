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
 * Pitää yllä varsinaista rekisteriä omistajista. Osaa lukea tiedostosta ja kirjoittaa siihen.
 * Osaa myös lisätä, poistaa, lajitella ja etsiä tietoa.  
 * @author Linda
 * ljhovila@student.jyu.fi
 * @version 11.3.2020
 *
 */
public class Omistajat implements Iterable<Omistaja> {
    
    private final ArrayList<Omistaja> alkiot;
    private int                       lkm            = 0;
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
        // lisaa(new Omistaja());
    }
    
    
    /**
     * Lisätään omistaja omistajiin.
     * @param o lisättävä omistaja
     * @example
     * <pre name="test">
     * Omistajat o = new Omistajat();
     * Omistaja o2 = new Omistaja();
     * Omistaja o3 = new Omistaja();
     * Omistaja o4 = new Omistaja();
     * o.lisaa(o2); o.lisaa(o3); o.lisaa(o4);
     * o.getLkm() === 3;
     * </pre>
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
            throw new IndexOutOfBoundsException("Laiton indeksi: " + i);
        return alkiot.get(i);
    }
    
    
    /**
     * Hakee ja palauttaa omistajien lukumäärän
     * @return omistajien lukumäärä
     */
    public int getLkm() {
        return lkm;
    }
    
    
    /**
     * Palauttaa listan omistajista.
     * @return lista
     * @example
     * <pre name="test">
     * Omistajat o = new Omistajat();
     * Omistaja o1 = new Omistaja();
     * Omistaja o2 = new Omistaja(); o2.parse("1|Ari Katajavuori|Kuusitie 12|10740 Helsinki|0405632942|ari.kvuori@gmail.com|");
     * Omistaja o3 = new Omistaja(); o3.parse("2|Aino Alakiuttu|Pulukatu 24 B 5|40555 Pihtipudas|0503329471|aino4ever@hotmail.com|");
     * Omistaja o4 = new Omistaja(); o4.parse("3|Pilvi Mannio|Linnantie 74|70860 Oulu|041542177|mannio.p@gmail.com|");
     * o.lisaa(o1); o.lisaa(o2); o.lisaa(o3); o.lisaa(o4);
     * ArrayList<Omistaja> omistajat = o.getOmistajat();
     * omistajat.size() === 4;
     * omistajat.get(2).getTunnusNro() === 2;
     * </pre>
     */
    public ArrayList<Omistaja> getOmistajat() {
        ArrayList<Omistaja> omistajat = new ArrayList<Omistaja>();
        for (Omistaja o : alkiot) {
            omistajat.add(o);
        }
        return omistajat;
    }
    
    
    /**
     * Asettaa eläimen tietoihin omistajan
     * @param elain mikä eläin
     * @param s omistajan nimi
     */
    public void aseta(Elain elain, String s) {
        elain.asetaOmistaja(s, alkiot);
    }
    
    
    /**
     * Lukee omistajien tiedostosta.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        File tied = new File(hakemisto + "/omistajat.dat");
        
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
     * @example
     * <pre name="test">
     * Omistajat o = new Omistajat();
     * Omistaja o1 = new Omistaja();
     * Omistaja o2 = new Omistaja(); o2.parse("1|Ari Katajavuori|Kuusitie 12|10740 Helsinki|0405632942|ari.kvuori@gmail.com|");
     * Omistaja o3 = new Omistaja(); o3.parse("2|Aino Alakiuttu|Pulukatu 24 B 5|40555 Pihtipudas|0503329471|aino4ever@hotmail.com|");
     * Omistaja o4 = new Omistaja(); o4.parse("3|Pilvi Mannio|Linnantie 74|70860 Oulu|041542177|mannio.p@gmail.com|");
     * o.lisaa(o1); o.lisaa(o2); o.lisaa(o3); o.lisaa(o4);
     * o.getLkm() === 4;
     * o.poista(o2);
     * o.getLkm() === 3;
     * </pre>
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
     * @example
     * <pre name="test">
     * Omistajat o = new Omistajat();
     * Omistaja o1 = new Omistaja();
     * Omistaja o2 = new Omistaja(); o2.parse("1|Ari Katajavuori|Kuusitie 12|10740 Helsinki|0405632942|ari.kvuori@gmail.com|");
     * Omistaja o3 = new Omistaja(); o3.parse("2|Aino Alakiuttu|Pulukatu 24 B 5|40555 Pihtipudas|0503329471|aino4ever@hotmail.com|");
     * Omistaja o4 = new Omistaja(); o4.parse("3|Pilvi Mannio|Linnantie 74|70860 Oulu|041542177|mannio.p@gmail.com|");
     * o.lisaa(o1); o.lisaa(o2); o.lisaa(o3); o.lisaa(o4);
     * o.loytyyko(2) === true;
     * o.loytyyko(5) === false;
     * </pre>
     */
    public boolean loytyyko(int i) {
        for (Omistaja o : alkiot) {
            if (o.getTunnusNro() == i) return true;
        }
        return false;
    }
    
    
    /**
     * Korvaa omistajan tiedot tietorakenteeseen tai tekee uuden. Etsitään samalla tunnusnumerolla oleva omistaja.
     * @param omistaja lisättävän viite
     */
    public void korvaaTaiLisaa(Omistaja omistaja) {
        int id = omistaja.getTunnusNro();
        int i = 0;
        for (Omistaja o : alkiot) {
            if (o.getTunnusNro() == id) {
                alkiot.set(i, omistaja);
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
     * Omistajat o = new Omistajat();
     * Omistaja o1 = new Omistaja();
     * Omistaja o2 = new Omistaja(); o2.parse("1|Ari Katajavuori|Kuusitie 12|10740 Helsinki|0405632942|ari.kvuori@gmail.com|");
     * Omistaja o3 = new Omistaja(); o3.parse("2|Aino Alakiuttu|Pulukatu 24 B 5|40555 Pihtipudas|0503329471|aino4ever@hotmail.com|");
     * Omistaja o4 = new Omistaja(); o4.parse("3|Pilvi Mannio|Linnantie 74|70860 Oulu|041542177|mannio.p@gmail.com|");
     * o.lisaa(o1); o.lisaa(o2); o.lisaa(o3); o.lisaa(o4);
     * ArrayList<Omistaja> lista = o.etsi("*v*", 1);
     * lista.size() === 2;
     * lista.get(0) == o2 === true;
     * lista.get(1) == o4 === true;
     * 
     * lista = o.etsi(null, -1);
     * lista.size() === 3;
     * </pre>
     */
    public ArrayList<Omistaja> etsi(String hakuehto, int k) {
        String ehto = "*";
        if (hakuehto != null && hakuehto.length() > 0) ehto = hakuehto;
        int hk = k;
        if (hk < 0) hk = apuomistaja.ekaKentta();
        
        ArrayList<Omistaja> lista = new ArrayList<Omistaja>();
        
            for (int i = 1; i < lkm; i++) {
                if (WildChars.onkoSamat(alkiot.get(i).anna(hk), ehto))
                    lista.add(alkiot.get(i));
            
        }
        Collections.sort(lista, new Omistaja.Vertailija(hk));
        
        return lista;
    }
    
    
    /**
     * Testipääohjelma
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
