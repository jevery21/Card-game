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
public class KeyInputGeneratorTest {
    
    public KeyInputGeneratorTest() {
    }

    /**
     * Test of run method, of class KeyInputGenerator.
     */
    @Test
    public void testRun() {
        System.out.println("run");
        KeyInputGenerator instance = new KeyInputGenerator();
        instance.run();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addKeyInputEventListener method, of class KeyInputGenerator.
     */
    @Test
    public void testAddKeyInputEventListener() {
        System.out.println("addKeyInputEventListener");
        KeyInputListenerClass t = null;
        KeyInputGenerator instance = new KeyInputGenerator();
        instance.addKeyInputEventListener(t);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of removeKeyInputEventListener method, of class KeyInputGenerator.
     */
    @Test
    public void testRemoveKeyInputEventListener() {
        System.out.println("removeKeyInputEventListener");
        KeyInputGenerator instance = new KeyInputGenerator();
        instance.removeKeyInputEventListener();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of fireKeyInputEvent method, of class KeyInputGenerator.
     */
    @Test
    public void testFireKeyInputEvent() {
        System.out.println("fireKeyInputEvent");
        KeyInputEvent evt = null;
        KeyInputGenerator instance = new KeyInputGenerator();
        instance.fireKeyInputEvent(evt);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isPaused method, of class KeyInputGenerator.
     */
    @Test
    public void testIsPaused() {
        System.out.println("isPaused");
        KeyInputGenerator instance = new KeyInputGenerator();
        boolean expResult = false;
        boolean result = instance.isPaused();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
