package bcit.ca.infosys.KeyboardCowboys.test.unit;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.StatusReport;
import bcit.ca.infosys.KeyboardCowboys.model.WPEmpEstimate;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;

/**
 * WPEmpEstimate class tests.
 *
 * @author Vukasin
 */
public class WPEmpEstimateTest {
    
    /**
     * WPEmpEstimate instances to be used in testing.
     */
    private WPEmpEstimate testWPEmpEstimate;
    
    /**
     * WPEmpEstimateTest class constructor.
     */
    public WPEmpEstimateTest() {
    }
    
    /**
     * Create new WPEmpEstimate for each test case.
     */
    @Before
    public final void newTestWPEmpEstimate() {
        testWPEmpEstimate = new WPEmpEstimate();
    }
    
    /**
     * Test getters and setters for employee.
     */
    @Test
    public final void testGetEmployee() {
        Employee testEmployee = new Employee();
        testWPEmpEstimate.setEmployee(testEmployee);
        assertEquals(testEmployee, testWPEmpEstimate.getEmployee());
    }
    
    /**
     * Test getters and setters for estimateID.
     */
    @Test
    public final void testGetEstimateID() {
        final int testEstimateId = 23;
        testWPEmpEstimate.setEstimateID(testEstimateId);
        assertEquals(testEstimateId, testWPEmpEstimate.getEstimateID().intValue());
    }
    
    /**
     * Test getters and setters for estimate.
     */
    @Test
    public final void testGetEstimate() {
        final int testEstimate = 411;
        testWPEmpEstimate.setEstimate(testEstimate);
        assertEquals(testEstimate, testWPEmpEstimate.getEstimate());
    }
    
    /**
     * Test getters and setters for statusReport.
     */
    @Test
    public final void testGetStatusReport() {
        StatusReport testSR = new StatusReport();
        testWPEmpEstimate.setStatusReport(testSR);
        assertEquals(testSR, testWPEmpEstimate.getStatusReport());
    }
}
