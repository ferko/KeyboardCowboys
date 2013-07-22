package bcit.ca.infosys.KeyboardCowboys.test.unit;


import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheet;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheetApproval;

/**
 * TimeSheetApproval class tests.
 *
 * @author Vukasin
 */
public class TimeSheetApprovalTest {
    
    /**
     * TimeSheetApproval instances to be used in testing.
     */
    private TimeSheetApproval testTimeSheetApproval;
    
    /**
     * TimeSheetApprovalTest class constructor.
     */
    public TimeSheetApprovalTest() {
    }
    
    /**
     * Create new TimeSheetApproval for each test case.
     */
    @Before
    public final void newTestTimeSheetApproval() {
        testTimeSheetApproval = new TimeSheetApproval();
    }
    
    /**
     * Test getters and setters for tsaApprover.
     */
    @Test
    public final void testGetTsaApprover() {
        Employee testEmployee = new Employee();
        testTimeSheetApproval.setTsaApprover(testEmployee);
        assertEquals(testEmployee, testTimeSheetApproval.getTsaApprover());
    }
    
    /**
     * Test getters and setters for approvalId. 
     */
    @Test
    public final void testGetApprovalId() {
        testTimeSheetApproval.setApprovalId(1);
        assertEquals(1, testTimeSheetApproval.getApprovalId().intValue());
    }
    
    /**
     * Test getters and setters for timeSheets.
     */
    @Test
    public final void testGetTimeSheets() {
        TimeSheet testTimesheet = new TimeSheet();
        testTimeSheetApproval.setTimeSheet(testTimesheet);
        assertEquals(testTimesheet, testTimeSheetApproval.getTimeSheet());
    }
}
