package pentu;

/**
 * Hoitelee sen, että monta jäsentä (CRC-kortit)
 * @author linda
 * @version 17.2.2020
 *
 */
public class Elaimet {
    
    private static final int    MAX_ELAIMIA     = 5;
    private int                 lkm             = 0;
    private String              tiedostonNimi   = "";
    private Elain[]             alkiot;

    
    
    /**
     * Oletusmuodostaja
     */
    public Elaimet() {
        alkiot = new Elain[MAX_ELAIMIA];
    }
    
    
    /**
     * Lisää uuden eläimen tietorakenteeseen.  Ottaa eläimen omistukseensa.
     * @param elain liitettävän eläimen viite.
     * @throws SailoException jos tietorakenne on jo täynnä
     * @example
     * <pre name="test">
     * #THROWS SailoException 
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
     * elaimet.anna(3) === mirz1; #THROWS IndexOutOfBoundsException 
     * elaimet.lisaa(aku1); elaimet.getLkm() === 4;
     * elaimet.lisaa(aku1); elaimet.getLkm() === 5;
     * elaimet.lisaa(aku1);  #THROWS SailoException
     * </pre>
     */
    public void lisaa(Elain elain) throws SailoException {
        if (lkm >= alkiot.length) throw new SailoException("Liikaa alkioita");
        // TODO:laita tila kasvamaan
        alkiot[lkm] = elain;
        lkm++;
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
     * @return viite elläimeen, jonka indeksi on i
     * @throws IndexOutOfBoundsException jos i ei ole sallitulla alueella
     */
    public Elain anna(int i) throws IndexOutOfBoundsException {
        if (i < 0 || lkm <= i)
            throw new IndexOutOfBoundsException("Laitoin indeksi: " + i);
        return alkiot[i];
    }
    
    
    /**
     * Lukee eläinten tiedostosta.  Kesken.
     * @param hakemisto tiedoston hakemisto
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String hakemisto) throws SailoException {
        tiedostonNimi = hakemisto + "/nimet.dat";
        throw new SailoException("Ei osata vielä lukea tiedostoa " + tiedostonNimi);
    }
    
    
    /**
     * Tallentaa eläinten tiedostoon.  
     * TODO Kesken.
     * @throws SailoException jos talletus epäonnistuu
     */
    public void talleta() throws SailoException {
        throw new SailoException("Ei osata vielä tallettaa tiedostoa " + tiedostonNimi);
    }
    
    
    
    
    /**
     * Testiohjelma eläimille
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Elaimet elaimet = new Elaimet();
        Elain mirzam = new Elain(), mirzam2 = new Elain();
        
        mirzam.rekisteroi();
        mirzam.taytaElainTiedoilla();
        mirzam2.rekisteroi();
        mirzam2.taytaElainTiedoilla();
       
        try {
            elaimet.lisaa(mirzam);
            elaimet.lisaa(mirzam2);
            
            System.out.println("=========== Eläimet testi ===========");
            
            for (int i = 0; i < elaimet.getLkm(); i++) {
                Elain elain = elaimet.anna(i);
                System.out.println("Eläimen nro: " + i);
                elain.tulosta(System.out);
            }
    
        } catch (SailoException ex) {
            System.out.println(ex.getMessage());
        }
        
    }
}
