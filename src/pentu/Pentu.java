package pentu;

import java.util.ArrayList;

/**
 * CRC korteista samat tekstit
 * @author linda
 * @version 18.2.2020
 *
 */
public class Pentu {
    
    private Elaimet elaimet = new Elaimet();
    private Omistajat omistajat = new Omistajat(); // <- tämä tulee sitten kun on omistajat luokka tehty yms
    
    /**
     * Alustetaan Pennun tiedot (tätä ei ole pakko tehdä)
     */
    public Pentu() {
        // ei tarvitse tehdä mitään
    }
        
    
    /**
     * Listätään uusia eläimiä rekisteriin
     * @param elain lisättävä eläin
     * @throws SailoException jos ei mahdu
     * @example
     * <pre name="test">
     * #THROWS SailoException 
     * Elaimet elaimet = new Elaimet();
     * Elain mirz1 = new Elain(), mirz2 = new Elain();
     * pentu.getLkm() === 0;
     * pentu.lisaa(mirz1); pentu.getElaimia() === 1;
     * pentu.lisaa(mirz2); pentu.getElaimia() === 2;
     * pentu.lisaa(mirz1); pentu.getElaimia() === 3;
     * pentu.annaElain(0) === mirz1;
     * pentu.annaElain(1) === mirz2;
     * pentu.annaElain(2) === mirz1;
     * pentu.annaElain(1) == mirz1 === false;
     * pentu.annaElain(1) == mirz2 === true;
     * pentu.annaElain(3) === mirz1; #THROWS IndexOutOfBoundsException 
     * pentu.lisaa(aku1); pentu.getElaimia() === 4;
     * pentu.lisaa(aku1); pentu.getElaimia() === 5;
     * pentu.lisaa(aku1);  #THROWS SailoException
     * </pre>
     */
    public void lisaa(Elain elain) throws SailoException {
        elaimet.lisaa(elain);
    }
    
    
    /**
     * Listään uusi harrastus kerhoon
     * @param o lisättävä omistaja 
     */
    public void lisaa(Omistaja o) {
        omistajat.lisaa(o);
    }
    
    
    /**
     * Lukee penturekisterin tiedot tiedostosta
     * @param nimi jota käyteään lukemisessa
     * @throws SailoException jos lukeminen epäonnistuu
     */
    public void lueTiedostosta(String nimi) throws SailoException {
        elaimet.lueTiedostosta(nimi);
        omistajat.lueTiedostosta(nimi);
    }
       
    
    /**
     * Haetaan eläinten lukumäärän eläimet luokasta
     * @return palauttaa lukumäärän
     */
    public int getElaimia() {
        return elaimet.getLkm();
    }
    
    /**
     * Haetaan omistajien lukumäärän omistajat luokasta
     * @return palauttaa lukumäärän
     */
    public int getOmistajia() {
        return omistajat.getLkm();
    }
    
    
    /**
     * Palauttaa i:n eläimen
     * @param i monesko eläin palautetaan
     * @return viite i:teen eläimeen 
     * @throws IndexOutOfBoundsException jos tulee poikkeus
     */
    public Elain annaElain(int i) throws IndexOutOfBoundsException {
        return elaimet.anna(i);
    }
    
    
    /**
     * Palauttaa i:n omistajan
     * @param i monesko omistaja palautetaan
     * @return viite i:teen eläimeen 
     * @throws IndexOutOfBoundsException jos tulee poikkeus
     */
    public Omistaja annaOmistaja(int i) throws IndexOutOfBoundsException {
        return omistajat.anna(i);
    }
    
    
    /**
     * Etsii eläimen pennut
     * @param e eläin, jonka pentuja etsitään
     * @return lista pennuista
     */
    public ArrayList<Elain> getPennut(Elain e) {
        return elaimet.pennut(e);
    }
    
    
    /**
     * Hakee omistajan eläimet
     * @param o kenen eläimet haetaan
     * @return lista eläimistä
     */
    public ArrayList<Elain> getElaimet(Omistaja o) {
        return elaimet.omistajanElaimet(o);        
    }
    
    
    /**
     * Tallentaa tiedostot eläimistä ja omistajista.
     * @param hakemisto minne tallennetaan
     * @throws SailoException jos ei onnistu
     */
    public void tallenna(String hakemisto) throws SailoException {
        String virhe = "";
        
        try {
            elaimet.tallenna(hakemisto);
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            virhe += e.getMessage();
        }
        
        try {
            omistajat.tallenna(hakemisto);
        } catch (SailoException e) {
            // TODO Auto-generated catch block
            virhe += e.getMessage();
        }
        
        if (virhe.length() > 0) {
            throw new SailoException(virhe);
        }
    }
    
    
    /**
     * Poistaa valitun eläimen
     * @param elain mikä poistetaan
     */
    public void poistaElain(Elain elain) {
        elaimet.poista(elain);        
    }
    
    
    /**
     * Poistaa valitun omistajan
     * @param omistaja mikä poistetaan
     */
    public void poistaOmistaja(Omistaja omistaja) {
        omistajat.poista(omistaja);
    }
    
    /**
     * Korvaa eläimen tiedot tietorakenteeseen. Etsitään samalla tunnusnumerolla oleva eläin
     * ja jos ei löydy, niin listään uutena eläimenä.
     * @param elain listätävän viite
     */
    public void korvaaTaiLisaa(Elain elain) {
        elaimet.korvaaTaiLisaa(elain);
    }
    
    
    /**
     * Etsii löytyykö omistajista henkilöä annetulla id:llä
     * @param i id, jota etsitään
     * @return true, jos löytyy ja jos ei niin false
     */
    public boolean loytyyko(int i) {
        return omistajat.loytyyko(i);
    }
    
    
    /**
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Pentu pentu = new Pentu();
        
        Elain mirzam = new Elain(), mirzam2 = new Elain();
        mirzam.rekisteroi();
        mirzam.taytaElainTiedoilla();
        mirzam2.rekisteroi();
        mirzam2.taytaElainTiedoilla();
        
        
        Omistaja kasvattaja = new Omistaja(), ari = new Omistaja(); 
        kasvattaja.rekisteroi();
        ari.rekisteroi();
        ari.taytaTiedoilla();
        
        
        try {
            pentu.lisaa(mirzam2);
            pentu.lisaa(mirzam);
            pentu.lisaa(mirzam2);
            pentu.lisaa(mirzam);
            pentu.lisaa(mirzam2);
            pentu.lisaa(mirzam);
            pentu.lisaa(mirzam2);
            pentu.lisaa(mirzam);
        } catch (SailoException e) {
            System.err.println(e.getMessage());
            System.err.flush();
        }
        
        ArrayList<Elain> p = pentu.getPennut(mirzam2);
        
        System.out.println("=========== Eläimet testi ===========");
        
        for (int i = 0; i < pentu.getElaimia(); i++) {
            Elain elain = pentu.annaElain(i);
            System.out.println("Eläin paikassa: " + i);
            elain.tulosta(System.out);
        }
        
        
        pentu.lisaa(kasvattaja);
        pentu.lisaa(ari);
        pentu.lisaa(kasvattaja);
        pentu.lisaa(ari);
        
        System.out.println("\n" + "=========== Omistajat testi ===========");
        
        for(Omistaja o : pentu.omistajat) {
            o.tulosta(System.out);
        }
        
        
        System.out.println("\n" + "=========== Pennut testi ===========");
        for(Elain e : p) {
            System.out.println(e.getNimi());
        }
        System.out.println("Pentujen lukumäärä: " + p.size());
    }
}
