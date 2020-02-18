package pentu;

/**
 * CRC korteista samat tekstit
 * @author linda
 * @version 18.2.2020
 *
 */
public class Pentu {
    
    Elaimet elaimet = new Elaimet();
    // Omistajat omistajat = new Omistajat(); <- tämä tulee sitten kun on omistajat luokka tehty yms
    
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
     * Haetaan eläinten lukumäärän eläimet luokasta
     * @return palauttaa lukumäärän
     */
    public int getElaimia() {
        return elaimet.getLkm();
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
     * @param args ei käytössä
     */
    public static void main(String[] args) {
        Pentu pentu = new Pentu();
        
        Elain mirzam = new Elain(), mirzam2 = new Elain();
        mirzam.rekisteroi();
        mirzam.taytaMirTiedoilla();
        mirzam2.rekisteroi();
        mirzam2.taytaMirTiedoilla();        
        
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
                
        System.out.println("=========== Eläimet testi ===========");
        
        for (int i = 0; i < pentu.getElaimia(); i++) {
            Elain elain = pentu.annaElain(i);
            System.out.println("Eläin paikassa: " + i);
            elain.tulosta(System.out);
        }
    }

}
