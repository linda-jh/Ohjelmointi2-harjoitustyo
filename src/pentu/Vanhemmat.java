package pentu;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author linda
 * @version 15.3.2020
 *
 */
public class Vanhemmat implements Iterable<Vanhempi> {
    
    private final ArrayList<Vanhempi> alkiot         = new ArrayList<Vanhempi>();
    private String tiedostonNimi   = "";
    
    
    @Override
    public Iterator<Vanhempi> iterator() {
        return alkiot.iterator();
    }
    
    
    /**
     * Lukee vanhempien tiedostosta.  
     * TODO Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + ".har";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }
    
    
    /**
     * Lisätään vanhempi-olioita listaan.
     * @param v vanhempi, joka lisätään
     */
    public void lisaa(Vanhempi v) {
        alkiot.add(v);
    }
    
    
    /**
     * Tulostetaan vanhempien tiedot
     */
    public void tulosta() {
        
        System.out.println("=========== Vanhemmat testi ===========");
        for(Vanhempi v : alkiot) {
            v.tulosta(System.out);
        }
    }
    
    
    /**
     * Palauttaa i:n vanhemman
     * @param i monesko vanhempi
     * @return palauttaa i:ssä olevan vanhemman
     */
    public Vanhempi anna(int i) {
        if (i < 0 || alkiot.size() <= i)
            throw new IndexOutOfBoundsException("Laitoin indeksi: " + i);
        return alkiot.get(i);
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Vanhemmat van = new Vanhemmat();
        Vanhempi p = new Vanhempi(1), pa = new Vanhempi(2);
        p.taytaTiedoilla();
        pa.taytaTiedoilla();
        van.lisaa(p);
        van.lisaa(pa);
        van.tulosta();
        
    }

}
