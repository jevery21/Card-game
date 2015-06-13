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
public class KeyInputListenerClassTest {
    
    public KeyInputListenerClassTest() {
    }

    /**
     * Test of keyInputOccurance method, of class KeyInputListenerClass.
     */
    @Test
    public void testKeyInputOccurance() {
        System.out.println("keyInputOccurance");
        KeyInputEvent e = null;
        KeyInputListenerClass instance = new KeyInputListenerClass();
        instance.keyInputOccurance(e);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
