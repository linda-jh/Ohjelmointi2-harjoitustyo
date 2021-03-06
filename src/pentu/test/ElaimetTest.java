package pentu.test;
// Generated by ComTest BEGIN
import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.*;
import pentu.*;
// Generated by ComTest END

/**
 * Test class made by ComTest
 * @version 2020.04.24 12:49:37 // Generated by ComTest
 *
 */
@SuppressWarnings({ "all" })
public class ElaimetTest {



  // Generated by ComTest BEGIN
  /** testLisaa55 */
  @Test
  public void testLisaa55() {    // Elaimet: 55
    Elaimet elaimet = new Elaimet(); 
    Elain mirz1 = new Elain(), mirz2 = new Elain(); 
    assertEquals("From: Elaimet line: 58", 0, elaimet.getLkm()); 
    elaimet.lisaa(mirz1); assertEquals("From: Elaimet line: 59", 1, elaimet.getLkm()); 
    elaimet.lisaa(mirz2); assertEquals("From: Elaimet line: 60", 2, elaimet.getLkm()); 
    elaimet.lisaa(mirz1); assertEquals("From: Elaimet line: 61", 3, elaimet.getLkm()); 
    assertEquals("From: Elaimet line: 62", mirz1, elaimet.anna(0)); 
    assertEquals("From: Elaimet line: 63", mirz2, elaimet.anna(1)); 
    assertEquals("From: Elaimet line: 64", mirz1, elaimet.anna(2)); 
    assertEquals("From: Elaimet line: 65", false, elaimet.anna(1) == mirz1); 
    assertEquals("From: Elaimet line: 66", true, elaimet.anna(1) == mirz2); 
    elaimet.lisaa(mirz1); assertEquals("From: Elaimet line: 67", 4, elaimet.getLkm()); 
    elaimet.lisaa(mirz2); assertEquals("From: Elaimet line: 68", 5, elaimet.getLkm()); 
    elaimet.lisaa(mirz2); assertEquals("From: Elaimet line: 69", 6, elaimet.getLkm()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testPennut115 */
  @Test
  public void testPennut115() {    // Elaimet: 115
    Elaimet elaimet = new Elaimet(); 
    Elain elain = new Elain(); 
    Elain elain1 = new Elain(); elain1.parse("1|Karvatassu Rigel|Simba|15.04.2018||985112001635024|poika|5|6|0|"); 
    Elain elain2 = new Elain(); elain2.parse("2|Karvatassu Spica|Nala|15.04.2018|22.07.2018|985112001635025|tyttö|5|6|2|"); 
    Elain elain3 = new Elain(); elain3.parse("3|Karvatassu Castor|Mufasa|15.04.2018|27.07.2018|985112001635026|poika|5|6|3|"); 
    Elain elain4 = new Elain(); elain4.parse("4|Karvatassu Hadar|Pumba|15.04.2018||985112001635027|poika|5|6|0|"); 
    Elain elain5 = new Elain(); elain5.parse("5|Karvatassu Mirzam|Nelli|20.07.2015||985112001346227|tyttö|7|0|0|"); 
    Elain elain6 = new Elain(); elain6.parse("6|Karvatassu Regor|Pörrö|02.03.2016||985112001401021|poika|0|0|0|"); 
    Elain elain7 = new Elain(); elain7.parse("7|Karvatassu Jupiter|Pilkku|30.05.2017||985112001499302|tyttö|0|0|0|"); 
    elaimet.lisaa(elain); elaimet.lisaa(elain1); elaimet.lisaa(elain2); elaimet.lisaa(elain3); elaimet.lisaa(elain4); 
    elaimet.lisaa(elain5); elaimet.lisaa(elain6); elaimet.lisaa(elain7); 
    ArrayList<Elain> p = elaimet.pennut(elain5); 
    assertEquals("From: Elaimet line: 128", 4, p.size()); 
    assertEquals("From: Elaimet line: 129", 4, p.get(3).getTunnusNro()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testOmistajanElaimet152 */
  @Test
  public void testOmistajanElaimet152() {    // Elaimet: 152
    Elaimet elaimet = new Elaimet(); 
    Elain elain = new Elain(); 
    Elain elain1 = new Elain(); elain1.parse("1|Karvatassu Rigel|Simba|15.04.2018||985112001635024|poika|5|6|0|"); 
    Elain elain2 = new Elain(); elain2.parse("2|Karvatassu Spica|Nala|15.04.2018|22.07.2018|985112001635025|tyttö|5|6|2|"); 
    Elain elain3 = new Elain(); elain3.parse("3|Karvatassu Castor|Mufasa|15.04.2018|27.07.2018|985112001635026|poika|5|6|3|"); 
    Elain elain4 = new Elain(); elain4.parse("4|Karvatassu Hadar|Pumba|15.04.2018||985112001635027|poika|5|6|0|"); 
    Elain elain5 = new Elain(); elain5.parse("5|Karvatassu Mirzam|Nelli|20.07.2015||985112001346227|tyttö|7|0|0|"); 
    Elain elain6 = new Elain(); elain6.parse("6|Karvatassu Regor|Pörrö|02.03.2016||985112001401021|poika|0|0|0|"); 
    Elain elain7 = new Elain(); elain7.parse("7|Karvatassu Jupiter|Pilkku|30.05.2017||985112001499302|tyttö|0|0|0|"); 
    elaimet.lisaa(elain); elaimet.lisaa(elain1); elaimet.lisaa(elain2); elaimet.lisaa(elain3); elaimet.lisaa(elain4); 
    elaimet.lisaa(elain5); elaimet.lisaa(elain6); elaimet.lisaa(elain7); 
    Omistaja o = new Omistaja(); 
    ArrayList<Elain> p = elaimet.omistajanElaimet(o); 
    assertEquals("From: Elaimet line: 166", 6, p.size()); 
    assertEquals("From: Elaimet line: 167", 5, p.get(3).getTunnusNro()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testGetNimet186 */
  @Test
  public void testGetNimet186() {    // Elaimet: 186
    Elaimet elaimet = new Elaimet(); 
    Elain elain1 = new Elain(); elain1.parse("1|Karvatassu Rigel|Simba|15.04.2018||985112001635024|poika|5|6|0|"); 
    Elain elain2 = new Elain(); elain2.parse("2|Karvatassu Spica|Nala|15.04.2018|22.07.2018|985112001635025|tyttö|5|6|2|"); 
    Elain elain3 = new Elain(); elain3.parse("3|Karvatassu Castor|Mufasa|15.04.2018|27.07.2018|985112001635026|poika|5|6|3|"); 
    Elain elain4 = new Elain(); elain4.parse("4|Karvatassu Hadar|Pumba|15.04.2018||985112001635027|poika|5|6|0|"); 
    Elain elain5 = new Elain(); elain5.parse("5|Karvatassu Mirzam|Nelli|20.07.2015||985112001346227|tyttö|7|0|0|"); 
    Elain elain6 = new Elain(); elain6.parse("6|Karvatassu Regor|Pörrö|02.03.2016||985112001401021|poika|0|0|0|"); 
    Elain elain7 = new Elain(); elain7.parse("7|Karvatassu Jupiter|Pilkku|30.05.2017||985112001499302|tyttö|0|0|0|"); 
    elaimet.lisaa(elain1); elaimet.lisaa(elain2); elaimet.lisaa(elain3); elaimet.lisaa(elain4); 
    elaimet.lisaa(elain5); elaimet.lisaa(elain6); elaimet.lisaa(elain7); 
    ArrayList<Elain> p = elaimet.getNimet(1); 
    assertEquals("From: Elaimet line: 198", 3, p.size()); 
    assertEquals("From: Elaimet line: 199", 5, p.get(1).getTunnusNro()); 
    ArrayList<Elain> p2 = elaimet.getNimet(0); 
    assertEquals("From: Elaimet line: 201", 4, p2.size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testEtsi290 */
  @Test
  public void testEtsi290() {    // Elaimet: 290
    Elaimet elaimet = new Elaimet(); 
    Elain elain = new Elain(); 
    Elain elain1 = new Elain(); elain1.parse("1|Karvatassu Rigel|Simba|15.04.2018||985112001635024|poika|5|6|0||"); 
    Elain elain2 = new Elain(); elain2.parse("2|Karvatassu Spica|Nala|15.04.2018|22.07.2018|985112001635025|tyttö|5|6|2|"); 
    Elain elain3 = new Elain(); elain3.parse("3|Karvatassu Castor|Mufasa|15.04.2018|27.07.2018|985112001635026|poika|5|6|3|"); 
    Elain elain4 = new Elain(); elain4.parse("4|Karvatassu Hadar|Pumba|15.04.2018||985112001635027|poika|5|6|0|"); 
    Elain elain5 = new Elain(); elain5.parse("5|Karvatassu Mirzam|Nelli|20.07.2015||985112001346227|tyttö|7|0|0|"); 
    Elain elain6 = new Elain(); elain6.parse("6|Karvatassu Regor|Pörrö|02.03.2016||985112001401021|poika|0|0|0|"); 
    Elain elain7 = new Elain(); elain7.parse("7|Karvatassu Jupiter|Pilkku|30.05.2017||985112001499302|tyttö|0|0|0|"); 
    elaimet.lisaa(elain); elaimet.lisaa(elain1); elaimet.lisaa(elain2); elaimet.lisaa(elain3); elaimet.lisaa(elain4); 
    elaimet.lisaa(elain5); elaimet.lisaa(elain6); elaimet.lisaa(elain7); 
    ArrayList<Elain> lista = elaimet.etsi("*i*", 1); 
    assertEquals("From: Elaimet line: 303", 4, lista.size()); 
    assertEquals("From: Elaimet line: 304", true, lista.get(0) == elain7); 
    assertEquals("From: Elaimet line: 305", true, lista.get(2) == elain1); 
    lista = elaimet.etsi(null, -1); 
    assertEquals("From: Elaimet line: 308", 7, lista.size()); 
  } // Generated by ComTest END


  // Generated by ComTest BEGIN
  /** testPoista370 */
  @Test
  public void testPoista370() {    // Elaimet: 370
    Elaimet e = new Elaimet(); 
    Elain a = new Elain(); Elain b = new Elain(); Elain c = new Elain(); 
    e.lisaa(a); e.lisaa(b); e.lisaa(c); 
    assertEquals("From: Elaimet line: 374", 3, e.getLkm()); 
    e.poista(b); 
    assertEquals("From: Elaimet line: 376", 2, e.getLkm()); 
  } // Generated by ComTest END
}