package pentu;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author linda
 * @version 11.3.2020
 *
 */
public class Omistajat implements Iterable<Omistaja> {
    /** Taulukko omistajista */
    private final ArrayList<Omistaja> alkiot         = new ArrayList<Omistaja>();
    
    private String                    tiedostonNimi  = "";
    private int                       lkm            = 0;
    private int                       pentuLkm       = 0;

    @Override
    public Iterator<Omistaja> iterator() {
        return alkiot.iterator();
    }
    
    
    /**
     * Oletusmuodostaja. Lisää alkiot listaan kasvattajan automaattisesti.
     */
    public Omistajat() {
        Omistaja o = new Omistaja();
        lisaa(o);
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
        tiedostonNimi = hakemisto + ".har";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }
    
    
    /**
     * Tallentaa omistajien tiedostoon.  
     * TODO Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
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
