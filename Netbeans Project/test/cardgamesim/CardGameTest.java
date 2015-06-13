/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cardgamesim;

import static org.junit.Assert.*;
import org.junit.Test;

/**
 *
 * @author John
 */
public class CardGameTest {
    
    public CardGameTest() {
    }

    /**
     * Test of main method, of class CardGame.
     */
    @Test
    public void testMain() throws Exception {
        System.out.println("main");
        String[] args = null;
        CardGame.main(args);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setWinner method, of class CardGame.
     */
    @Test
    public void testSetWinner() {
        System.out.println("setWinner");
        int winnerID = 0;
        CardGame.setWinner(winnerID);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWinner method, of class CardGame.
     */
    @Test
    public void testGetWinner() {
        System.out.println("getWinner");
        int expResult = 0;
        int result = CardGame.getWinner();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isDeckEmpty method, of class CardGame.
     */
    @Test
    public void testIsDeckEmpty() {
        System.out.println("isDeckEmpty");
        int deckNo = 0;
        boolean expResult = false;
        boolean result = CardGame.isDeckEmpty(deckNo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTopCard method, of class CardGame.
     */
    @Test
    public void testGetTopCard() {
        System.out.println("getTopCard");
        int deckNo = 0;
        Card expResult = null;
        Card result = CardGame.getTopCard(deckNo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of putCardBack method, of class CardGame.
     */
    @Test
    public void testPutCardBack() {
        System.out.println("putCardBack");
        int deckNo = 0;
        Card card = null;
        CardGame.putCardBack(deckNo, card);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
