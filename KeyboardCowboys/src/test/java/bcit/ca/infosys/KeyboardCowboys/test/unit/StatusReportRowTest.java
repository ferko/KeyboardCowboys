package bcit.ca.infosys.KeyboardCowboys.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.StatusReport;
import bcit.ca.infosys.KeyboardCowboys.model.StatusReportRow;

/**
 * StatusReportRow class tests.
 *
 * @author Vukasin
 */
public class StatusReportRowTest {
    
    /**
     * StatusReportRow instances to be used in testing.
     */
    private StatusReportRow testSrRow;  
    
    /**
     * StatusReportRowTest class constructor.
     */
    public StatusReportRowTest() {
    }
    
    /**
     * Create new StatusReportRow for each test case.
     */
    @Before
    public final void newTestStatusReportRow() {
        testSrRow = new StatusReportRow();
    }
    
    /**
     * Test getters and setters for srRowId.
     */
    @Test
    public final void testGetSrRowId() {
        final int testId = 10;
        testSrRow.setSrRowId(testId);
        assertEquals(testId, testSrRow.getSrRowId().intValue());
    }
    
    /**
     * Test getters and setters for statusReport.
     */
    @Test
    public final void testGetStatusReport() {
        StatusReport testStatusReport = new StatusReport();
        testSrRow.setStatusReport(testStatusReport);
        assertEquals(testStatusReport, testSrRow.getStatusReport());
    }
    
    /**
     * Test getters and setters for srEmp.
     */
    @Test
    public final void testGetSrEmp() {
        Employee testSrEmp = new Employee();
        testSrRow.setSrEmp(testSrEmp);
        assertEquals(testSrEmp, testSrRow.getSrEmp());
    }
    
    /**
     * Test getters and setters for bcwsPDay.
     */
    @Test
    public final void testGetBcwsPDay() {
        final int testBcwsPDay = 99;
        testSrRow.setBcwsPDay(testBcwsPDay);
        assertEquals(testBcwsPDay, testSrRow.getBcwsPDay());
    }
    
    /**
     * Test getters and setters for bcwsCost.
     */
    @Test
    public final void testGetBcwsCost() {
        final double testBcwsCost = 1867.0701;
        testSrRow.setBcwsCost(testBcwsCost);
        assertTrue(Math.abs(testBcwsCost - testSrRow
                .getBcwsCost()) == 0);
    }
    
    /**
     * Test getters and setters for tsSubmission.
     */
    @Test
    public final void testGetTsSubmission() {
        final double testTsSubmission = 1776.0704;
        testSrRow.setTsSubmission(testTsSubmission);
        assertTrue(Math.abs(testTsSubmission - testSrRow
                .getTsSubmission()) == 0);
    }
    
    /**
     * Test getters and setters for tsActualCost.
     */
    @Test
    public final void testGetActualCost() {
        final double testActualCost = 1810.0916;
        testSrRow.setActualCost(testActualCost);
        assertTrue(Math.abs(testActualCost - testSrRow
                .getActualCost()) == 0);
    }
    
    /**
     * Test getters and setters for etcDay.
     */
    @Test
    public final void testGetEtcDay() {
        final int testEtcDay = 000;
        testSrRow.setEtcDay(testEtcDay);
        assertEquals(testEtcDay, testSrRow.getEtcDay());
    }
    
    /**
     * Test getters and setters for etcCost.
     */
    @Test
    public final void testGetEtcCost() {
        final double testEtcCost = 1902.0520;
        testSrRow.setEtcCost(testEtcCost);
        assertTrue(Math.abs(testEtcCost - testSrRow
                .getEtcCost()) == 0);
    }
    
    /**
     * Test getters and setters for eacDay.
     */
    @Test
    public final void testGetEacDay() {
        final int testEacDay = 000;
        testSrRow.setEacDay(testEacDay);
        assertEquals(testEacDay, testSrRow.getEacDay());
    }
    
    /**
     * Test getters and setters for eacCost.
     */
    @Test
    public final void testGetEacCost() {
        final double testEacCost = 1962.0806;
        testSrRow.setEacCost(testEacCost);
        assertTrue(Math.abs(testEacCost - testSrRow
                .getEacCost()) == 0);
    }

}
