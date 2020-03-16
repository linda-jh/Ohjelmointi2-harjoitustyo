package pentu;

/**
 * @author linda
 * @version 15.3.2020
 *
 */
public class Vanhempi {
    
    private Vanhempi v = new Vanhempi(0);
    private int nro;
    private Elain isa;
    private Elain aiti;
    private Omistaja omistaja;
    private String pv = "";
    
    
    /**
     * Oletusmuodostaja
     * @param n Elain-olion tunnusnumero
     */
    public Vanhempi(int n) {
        nro = n;
    }
    
    


    /**
     * Palauttaa äidiksi merkityn Elain-olion
     * @return Elain-olio
     */
    public Elain getAiti() {
        return aiti;
    }
    
    
    /**
     * Palauttaa isäksi merkityn Elain-olion
     * @return Elain-olio
     */
    public Elain getIsa() {
        return isa;
    }
    
    
    /**
     * Asettaa äidin
     * @param a Elain-olion äiti
     */
    public void setAiti(Elain a) {
        aiti = a;
    }
    
    /**
     * Asettaa isän
     * @param i Elain-olion äiti
     */
    public void setIsa(Elain i) {
        isa = i;
    }
    
    public String getLuovutusPv() {
        return pv;
    }

    
    /**
     * Hakee omistajan
     * @return palauttaa omistajan nimen
     */
    public String getOmistaja() {
        return omistaja.getNimi();
    }


    /**public void lisaaVanhemmat() {
        aiti = 
        v.setIsa(Vanhemmat.getIsa());
        pv = v.getLuovutusPv();
        omistaja = v.getOmistaja();        
    }*/
}
