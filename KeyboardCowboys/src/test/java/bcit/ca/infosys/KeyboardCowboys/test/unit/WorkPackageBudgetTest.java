package bcit.ca.infosys.KeyboardCowboys.test.unit;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import bcit.ca.infosys.KeyboardCowboys.model.PayLevel;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackageBudget;

/**
 * WorkPackageBudget class tests.
 *
 * @author Vukasin
 */
public class WorkPackageBudgetTest {
    
    /**
     * WorkPackageBudget instances to be used in testing.
     */
    private WorkPackageBudget testWorkPackageBudget;
    
    /**
     * WorkPackageBudgetTest class constructor.
     */
    public WorkPackageBudgetTest() {
    }
    
    /**
     * Create new WorkPackageBudget for each test case.
     */
    @Before
    public final void newTestProject() {
        testWorkPackageBudget = new WorkPackageBudget();
    }
    
    /**
     * Test getters and setters for wpbID.
     */
    @Test
    public final void testGetWpbID() {
        final int testId = 01;
        testWorkPackageBudget.setWpbID(testId);
        assertEquals(testId, testWorkPackageBudget.getWpbID().intValue());
    }
    
    /**
     * Test getters and setters for payLevel.
     */
    @Test
    public final void testGetPayLevel() {
        PayLevel testPayLevel = new PayLevel();
        testWorkPackageBudget.setPayLevel(testPayLevel);
        assertEquals(testPayLevel, testWorkPackageBudget.getPayLevel());
    }
    
    /**
     * Test getters and setters for days.
     */
    @Test
    public final void testGetDays() {
        final int testDays = 7;
        testWorkPackageBudget.setDays(testDays);
        assertEquals(testDays, testWorkPackageBudget.getDays());
    }
    
    /**
     * Test getters and setters for workPackage.
     */
    @Test
    public final void testGetWorkPackage() {
        WorkPackage testWorkPackage = new WorkPackage();
        testWorkPackageBudget.setWorkPackage(testWorkPackage);
        assertEquals(testWorkPackage, testWorkPackageBudget.getWorkPackage());
    }

}
