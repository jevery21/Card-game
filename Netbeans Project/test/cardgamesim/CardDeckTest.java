/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgamesim;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author John
 */
public class CardDeckTest {
    
    public CardDeckTest() {
    
        testAddCard();
    }
   

    /**
     * Test of addCard method, of class CardDeck.
     * @return Nothing.
     */
    @Test
    public void testAddCard() {
        System.out.println("addCard");
        Card testCard = new Card(5);
        CardDeck deck1 = new CardDeck(1);
        deck1.addCard(testCard);
        int size = deck1.getSize();
        assertEquals(1, size);
        Card result = deck1.popCard();
        assertEquals(testCard, result);
    }
    
    /**
     * Test of saveToFile method
     * @return Nothing.
     */
    @Test
    public void testSaveToFile() {
        Card testCard1 = new Card(10);
        Card testCard2 = new Card(20);
        
        CardDeck deck2 = new CardDeck(2);
        deck2.addCard(testCard1);
        deck2.addCard(testCard2);
        int size = deck2.getSize();
        assertEquals(2, size);
        deck2.saveToFile();
    }
}
