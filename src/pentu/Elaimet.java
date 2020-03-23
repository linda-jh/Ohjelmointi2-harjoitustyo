package pentu;

import java.util.ArrayList;

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
     * Oletusmuodostaja. Lisää eläinten taulukkoon automaattisesti tyhjän eläimen.
     */
    public Elaimet() {
        Elain e = new Elain();
        alkiot = new Elain[MAX_ELAIMIA];
        try {
            lisaa(e);
        } catch (SailoException e1) {
            e1.printStackTrace();
        }
    }
    
    
    /**
     * Palauttaa eläin taulukon.
     * @return elain taulukon
     */
    public Elain[] getElaimet() {
        return alkiot;
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
     * Etsii eläimen vanhemmat ja alustaa ne. 
     *
    public void vanhemmat() {
        Elain[] e = getElaimet();
        Elain e2;
        Elain vali;
        for(int i = 0; i < e.length; i++) {
            if(tunnusNro != e[i].tunnusNro) {
                if(tunnusNro == e[i].isa || tunnusNro == e[i].aiti) {
                    if(e[i].onkoPoika() == true) isa = e[i].getTunnusNro();
                    else aiti = e[i].getTunnusNro();
                }
            }
        }
    }*/
    
    
    /**
     * Etsii eläimen pennut ja lisää ne listaan. 
     * @param e eläin
     * @return lista pennuista
     */
    public ArrayList<Elain> pennut(Elain e) {
        ArrayList<Elain> l = new ArrayList<Elain>();
        if (l.size() != 0) {
            for (int i = 0; i < alkiot.length; i++) {
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
     */
    public ArrayList<Elain> omistajanElaimet(Omistaja o) {
        ArrayList<Elain> l = new ArrayList<Elain>();
        if (l.size() != 0) {
            for (int i = 0; i < alkiot.length; i++) {
                if (o.getTunnusNro() == alkiot[i].getOmistajaId()) l.add(alkiot[i]);
            }
        }
        return l;
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
