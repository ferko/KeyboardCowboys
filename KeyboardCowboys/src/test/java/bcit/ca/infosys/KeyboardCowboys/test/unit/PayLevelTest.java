package bcit.ca.infosys.KeyboardCowboys.test.unit;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import bcit.ca.infosys.KeyboardCowboys.model.PayLevel;

/**
 * PayLevel class tests.
 *
 * @author Vukasin
 */
public class PayLevelTest {
    /**
     * Employee instances to be used in testing.
     */
    private PayLevel testPayLevel;
    
    /**
     * PayLevelTest class constructor.
     */
    public PayLevelTest() {
    }
    
    /**
     * Create new PayLevel for each test case.
     */
    @Before
    public final void newTestPayLevel() {
        testPayLevel = new PayLevel();
    }
    
    /**
     * Test getters and setters for plLevel.
     */
    @Test
    public final void testGetPlLevel() {
        testPayLevel.setPlLevel("FairWage");
        assertEquals("FairWage", testPayLevel.getPlLevel());
    }
    
    /**
     * Test getters and setters for plRate.
     */
    @Test
    public final void testGetPlRate() {
        final double payRate = 40.00;
        testPayLevel.setPlRate(payRate);
        assertTrue(Math.abs(payRate - testPayLevel.getPlRate()) == 0);
    }

    /**
     * Retrieving serial version uid.
     */
    @Test
    @Ignore
    public final void testGetSerialversionuid() {
        fail("Not yet implemented");
    }
}
