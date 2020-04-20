package pentu.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;

import java.util.List;

import org.junit.*;
import pentu.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.04.18 01:34:30 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class ElaimetTest {



  // Generated by ComTest BEGIN
  /** testLisaa54 */
  @Test
  public void testLisaa54() {    // Elaimet: 54
    Elaimet elaimet = new Elaimet(); 
    Elain mirz1 = new Elain(), mirz2 = new Elain(); 
    assertEquals("From: Elaimet line: 57", 0, elaimet.getLkm()); 
    elaimet.lisaa(mirz1); assertEquals("From: Elaimet line: 58", 1, elaimet.getLkm()); 
    elaimet.lisaa(mirz2); assertEquals("From: Elaimet line: 59", 2, elaimet.getLkm()); 
    elaimet.lisaa(mirz1); assertEquals("From: Elaimet line: 60", 3, elaimet.getLkm()); 
    assertEquals("From: Elaimet line: 61", mirz1, elaimet.anna(0)); 
    assertEquals("From: Elaimet line: 62", mirz2, elaimet.anna(1)); 
    assertEquals("From: Elaimet line: 63", mirz1, elaimet.anna(2)); 
    assertEquals("From: Elaimet line: 64", false, elaimet.anna(1) == mirz1); 
    assertEquals("From: Elaimet line: 65", true, elaimet.anna(1) == mirz2); 
    elaimet.lisaa(mirz1); assertEquals("From: Elaimet line: 66", 4, elaimet.getLkm()); 
    elaimet.lisaa(mirz2); assertEquals("From: Elaimet line: 67", 5, elaimet.getLkm()); 
    elaimet.lisaa(mirz2); assertEquals("From: Elaimet line: 68", 6, elaimet.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testEtsi254 */
  @Test
  public void testEtsi254() {    // Elaimet: 254
    Elaimet elaimet = new Elaimet(); 
    Elain elain1 = new Elain(); elain1.parse("1|Karvatassu Rigel|Simba|15.04.2018|poika|985112001635024|5|6|0||-|"); 
    Elain elain2 = new Elain(); elain2.parse("2|Karvatassu Spica|Nala|15.04.2018|tyttö|985112001635025|5|6|2|22.07.2018|-|"); 
    Elain elain3 = new Elain(); elain3.parse("3|Karvatassu Castor|Mufasa|15.04.2018|poika|985112001635026|5|6|3|27.07.2018|-|"); 
    Elain elain4 = new Elain(); elain4.parse("4|Karvatassu Hadar|Pumba|15.04.2018|poika|985112001635027|5|6|0||-|"); 
    Elain elain5 = new Elain(); elain5.parse("5|Karvatassu Mirzam|Nelli|20.07.2015|tyttö|985112001346227|7|0|0||-|"); 
    Elain elain6 = new Elain(); elain5.parse("6|Karvatassu Regor|Pörrö|02.03.2016|poika|985112001401021|0|0|0||-|"); 
    Elain elain7 = new Elain(); elain5.parse("7|Karvatassu Jupiter|Pilkku|30.05.2017|tyttö|985112001499302|0|0|0||-|"); 
    elaimet.lisaa(elain1); elaimet.lisaa(elain2); elaimet.lisaa(elain3); elaimet.lisaa(elain4); elaimet.lisaa(elain5); 
    elaimet.lisaa(elain6); elaimet.lisaa(elain7); 
    List<Elain> lista; 
    lista = elaimet.etsi("*i*", 1); 
    assertEquals("From: Elaimet line: 267", 3, lista.size()); 
    assertEquals("From: Elaimet line: 268", true, lista.get(0) == elain1); 
    assertEquals("From: Elaimet line: 269", true, lista.get(2) == elain5); 
    lista = elaimet.etsi(null, -1); 
    assertEquals("From: Elaimet line: 277", 7, lista.size()); 
  } // Generated by ComTest END
}