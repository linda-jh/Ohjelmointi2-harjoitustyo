package pentu;

import java.util.ArrayList;

/**
 * Huolehtii Elaimet ja Omistajat -luokkien välisestä yhteistyöstä ja välittää tietoja pyydettäessä, esimerkiksi
 * lukee tiedostosta ja kirjoittaa siihen.
 * 
 * @author Linda
 * ljhovila@student.jyu.fi
 * @version 18.2.2020
 *
 */
public class Pentu {
    
    private Elaimet elaimet = new Elaimet();
    private Omistajat omistajat = new Omistajat();
    
    
    /**
     * Oletusmuodostaja
     */
    public Pentu() {
        // ei tarvitse tehdä mitään
    }
        
    
    /**
     * Listätään uusia eläimiä rekisteriin
     * @param elain lisättävä eläin
     * @example
     * <pre name="test">
     * Pentu pentu = new Pentu();
     * Elain mirz1 = new Elain(), mirz2 = new Elain();
     * pentu.getElaimia() === 0;
     * pentu.lisaa(mirz1); pentu.getElaimia() === 1;
     * pentu.lisaa(mirz2); pentu.getElaimia() === 2;
     * pentu.lisaa(mirz1); pentu.getElaimia() === 3;
     * pentu.annaElain(0) === mirz1;
     * pentu.annaElain(1) === mirz2;
     * pentu.annaElain(2) === mirz1;
     * pentu.annaElain(1) == mirz1 === false;
     * pentu.annaElain(1) == mirz2 === true;
     * pentu.annaElain(3) === mirz1; #THROWS IndexOutOfBoundsException 
     * pentu.lisaa(mirz1); pentu.getElaimia() === 4;
     * pentu.lisaa(mirz2); pentu.getElaimia() === 5;
     * </pre>
     */
    public void lisaa(Elain elain) {
        elaimet.lisaa(elain);
    }
    
    
    /**
     * Listään uusi harrastus kerhoon
     * @param o lisättävä omistaja 
     * @example
     * <pre name="test">
     * Pentu pentu = new Pentu();
     * Omistaja a = new Omistaja(), b = new Omistaja();
     * pentu.getOmistajia() === 0;
     * pentu.lisaa(a); pentu.getOmistajia() === 1;
     * pentu.lisaa(b); pentu.getOmistajia() === 2;
     * pentu.lisaa(a); pentu.getOmistajia() === 3;
     * pentu.annaOmistaja(0) === a;
     * pentu.annaOmistaja(1) === b;
     * pentu.annaOmistaja(2) === a;
     * pentu.annaOmistaja(1) == a === false;
     * pentu.annaOmistaja(1) == b === true;
     * pentu.annaOmistaja(3) === a; #THROWS IndexOutOfBoundsException 
     * pentu.lisaa(a); pentu.getOmistajia() === 4;
     * pentu.lisaa(b); pentu.getOmistajia() === 5;
     * </pre>
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
     * @param elain eläin, jonka kohdalla ollaan
     * @param k mikä kenttä
     * @param s kentän teksti
     * @return palautetaan virheilmoitus, jos tuli, mutta muuten null
     */
    public String aseta(Elain elain, int k, String s) {
        return elaimet.aseta(elain, k, s);
    }
    
    
    /**
     * Asettaa eläimen tietoihin omistajan
     * @param elain mikä eläin
     * @param s omistajan nimi
     */
    public void asetaOmistaja(Elain elain, String s) {
        omistajat.aseta(elain, s);
    }
    
    
    /**
     * Palauttaa i:n eläimen
     * @param i monesko eläin palautetaan
     * @return viite i:teen eläimeen 
     * @throws IndexOutOfBoundsException jos tulee poikkeus
     * @example
     * <pre name="test">
     * Pentu pentu = new Pentu();
     * Elain mirz1 = new Elain(), mirz2 = new Elain();
     * pentu.lisaa(mirz1); pentu.lisaa(mirz2); pentu.lisaa(mirz1);
     * pentu.annaElain(0) === mirz1;
     * pentu.annaElain(1) === mirz2;
     * pentu.annaElain(2) === mirz1;
     * pentu.annaElain(1) == mirz1 === false;
     * pentu.annaElain(1) == mirz2 === true;
     * </pre>
     */
    public Elain annaElain(int i) throws IndexOutOfBoundsException {
        return elaimet.anna(i);
    }
    
    
    /**
     * Palauttaa i:n omistajan
     * @param i monesko omistaja palautetaan
     * @return viite i:teen eläimeen 
     * @throws IndexOutOfBoundsException jos tulee poikkeus
     * @example
     * <pre name="test">
     * Pentu pentu = new Pentu();
     * Omistaja a = new Omistaja(), b = new Omistaja();
     * pentu.getOmistajia() === 0;
     * pentu.lisaa(a);
     * pentu.lisaa(b);
     * pentu.lisaa(a);
     * pentu.annaOmistaja(0) === a;
     * pentu.annaOmistaja(1) === b;
     * pentu.annaOmistaja(2) === a;
     * pentu.annaOmistaja(1) == a === false;
     * pentu.annaOmistaja(1) == b === true;
     * </pre>
     */
    public Omistaja annaOmistaja(int i) throws IndexOutOfBoundsException {
        return omistajat.anna(i);
    }
    
    
    /**
     * Haetaan eläinten lukumäärän eläimet luokasta
     * @return palauttaa lukumäärän
     */
    public int getElaimia() {
        return elaimet.getLkm();
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
     * Haetaan omistajien lukumäärän omistajat luokasta
     * @return palauttaa lukumäärän
     */
    public int getOmistajia() {
        return omistajat.getLkm();
    }
    
    
    /**
     * Palauttaa listan omistajista.
     * @return lista
     */
    public ArrayList<Omistaja> getOmistajat() {
        return omistajat.getOmistajat();
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
     * Palauttaa eläimet listassa sukupuolen mukaan
     * @param s jos halutaan tyttöjä niin pitää olla 1, muuten 0
     * @return lista eläimistä
     */
    public ArrayList<Elain> getNimet(int s) {
        return elaimet.getNimet(s);
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
            virhe += e.getMessage();
        }
        
        try {
            omistajat.tallenna(hakemisto);
        } catch (SailoException e) {
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
        if(elain == null) return;
        elaimet.poista(elain);        
    }
    
    
    /**
     * Poistaa valitun omistajan
     * @param omistaja mikä poistetaan
     */
    public void poistaOmistaja(Omistaja omistaja) {
        if (omistaja == null) return;
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
     * Korvaa omistajan tiedot tietorakenteeseen. Etsitään samalla tunnusnumerolla oleva omistaja
     * ja jos ei löydy, niin listään uutena omistajana.
     * @param omistaja listätävän viite
     */
    public void korvaaTaiLisaa(Omistaja omistaja) {
        omistajat.korvaaTaiLisaa(omistaja);
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
     * Palauttaa listassa hakuehtoon vastaavien eläinten viitteet
     * @param ehto hakuehto
     * @param k etsittävän kentän indeksi
     * @return lista löydetyistä eläimistä
     */
    public ArrayList<Elain> etsi(String ehto, int k) {
        if (k == 9) {
            ArrayList<Omistaja> omist = omistajat.getOmistajat();
            return elaimet.etsiOmistajanPerusteella(ehto, omist);
        }
        return elaimet.etsi(ehto, k);
    }

    
    /**
     * Palauttaa listassa hakuehtoon vastaavien omistajien viitteet
     * @param ehto hakuehto
     * @param k etsittävän kentän indeksi
     * @return lista löydetyistä eläimistä
     */
    public ArrayList<Omistaja> etsiO(String ehto, int k) {
        return omistajat.etsi(ehto, k);
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
        

        pentu.lisaa(mirzam2);
        pentu.lisaa(mirzam);
        pentu.lisaa(mirzam2);
        pentu.lisaa(mirzam);
        pentu.lisaa(mirzam2);
        pentu.lisaa(mirzam);
        pentu.lisaa(mirzam2);
        pentu.lisaa(mirzam);
        
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
