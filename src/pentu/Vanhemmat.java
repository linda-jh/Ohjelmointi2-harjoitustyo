package pentu;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author linda
 * @version 15.3.2020
 *
 */
public class Vanhemmat {
    
    private ArrayList<Vanhempi> v = new ArrayList<Vanhempi>();
    private String tiedostonNimi   = "";
    
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

    public static Elain getIsa() {
        // TODO Auto-generated method stub
        return null;
    }
}
