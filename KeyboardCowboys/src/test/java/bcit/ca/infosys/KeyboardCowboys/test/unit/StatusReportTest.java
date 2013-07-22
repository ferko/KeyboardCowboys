package bcit.ca.infosys.KeyboardCowboys.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.StatusReport;
import bcit.ca.infosys.KeyboardCowboys.model.StatusReportRow;
import bcit.ca.infosys.KeyboardCowboys.model.WPEmpEstimate;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;

/**
 * StatusReport class tests.
 *
 * @author Vukasin
 */
public class StatusReportTest {
    
    /**
     * StatusReport instances to be used in testing.
     */
    private StatusReport testStatusReport;
    
    /**
     * StatusReportTest class constructor.
     */
    public StatusReportTest() {
    }
    
    /**
     * Create new StatusReport for each test case.
     */
    @Before
    public final void newTestStatusReport() {
        testStatusReport = new StatusReport();
    }
    
    /**
     * Test getters and setters for srId.
     */
    @Test
    public final void testGetSrId() {
        final int testId = 42;
        testStatusReport.setSrId(testId);
        assertEquals(testId, testStatusReport.getSrId().intValue());
    }
    
    /**
     * Test getters and setters for srDate.
     */
    @Test
    public final void testGetSrDate() {
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Date testSrDate;
        try {
            
            testSrDate = df.parse("03/12/13");
            testStatusReport.setSrDate(testSrDate);
            assertTrue("Dates comparison not precise", (testSrDate.getTime()
                    - testStatusReport.getSrDate().getTime()) < 1000);
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test getters and setters for srWeek.
     */
    @Test
    public final void testGetSrWeek() {
        final int testWeek = 6;
        testStatusReport.setSrWeek(testWeek);
        assertEquals(testWeek, testStatusReport.getSrWeek());
    }

    /**
     * Test getters and setters for comments.
     */
    @Test
    public final void testGetComments() {
        final String testComments = "No comment.";
        testStatusReport.setComments(testComments);
        assertEquals(testComments, testStatusReport.getComments());
    }
    
    /**
     * Test getters and setters for workAccomplished.
     */
    @Test
    public final void testGetWorkAccomplished() {
        final String testAccomplished = "Eureka!";
        testStatusReport.setWorkAccomplished(testAccomplished);
        assertEquals(testAccomplished, testStatusReport.getWorkAccomplished());
    }
    
    /**
     * Test getters and setters for workPlan.
     */
    @Test
    public final void testGetWorkPlan() {
        final String testWorkPlan = "What plan?";
        testStatusReport.setWorkPlan(testWorkPlan);
        assertEquals(testWorkPlan, testStatusReport.getWorkPlan());
    }
    
    /**
     * Test getters and setters for problemAnticipated.
     */
    @Test
    public final void testGetProblemAnticipated() {
        final String testProblem = "Don't worry about it.";
        testStatusReport.setProblemAnticipated(testProblem);
        assertEquals(testProblem, testStatusReport.getProblemAnticipated());
    }
    
    /**
     * Test getters and setters for workPackage.
     */
    @Test
    public final void testGetWorkPackage() {
        WorkPackage testWorkPackage = new WorkPackage();
        testStatusReport.setWorkPackage(testWorkPackage);
        assertEquals(testWorkPackage, testStatusReport.getWorkPackage());
    }
    
    /**
     * Test getters and setters for projManager.
     */
    @Test
    public final void testGetProjManager() {
        Employee testProjManager = new Employee();
        testStatusReport.setProjManager(testProjManager);
        assertEquals(testProjManager, testStatusReport.getProjManager());
    }
    
    /**
     * Test getters and setters for projAssistant.
     */
    @Test
    public final void testGetProjAssistant() {
        Employee testProjAssistant = new Employee();
        testStatusReport.setProjAssistant(testProjAssistant);
        assertEquals(testProjAssistant, testStatusReport.getProjAssistant());
    }
    
    /**
     * Test getters and setters for wpResEngineer.
     */
    @Test
    public final void testGetWpResEngineer() {
        Employee testWpResEngineer = new Employee();
        testStatusReport.setWpResEngineer(testWpResEngineer);
        assertEquals(testWpResEngineer, testStatusReport.getWpResEngineer());
    }
    
    /**
     * Test getters and setters for wpAssistant.
     */
    @Test
    public final void testGetWpAssistant() {
        Employee testWpAssistant = new Employee();
        testStatusReport.setWpAssistant(testWpAssistant);
        assertEquals(testWpAssistant, testStatusReport.getWpAssistant());
    }
    
    /**
     * Test getters and setters for problemForPeriod.
     */
    @Test
    public final void testGetProblemForPeriod() {
        final String testProblem = "UhOh!";
        testStatusReport.setProblemForPeriod(testProblem);
        assertEquals(testProblem, testStatusReport.getProblemForPeriod());
    }
    
    /**
     * Test getters and setters for status.
     */
    @Test
    public final void testGetStatus() {
        final String testStatus = "Nyuck!";
        testStatusReport.setStatus(testStatus);
        assertEquals(testStatus, testStatusReport.getStatus());
    }
    
    /**
     * Test getter for autogenerated value totalTimeSheetSubmission.
     */
    @Test
    public final void testGetTotalTimeSheetSubmission() {
        List<WPEmpEstimate> empEstimates = new ArrayList<WPEmpEstimate>();
        
        WPEmpEstimate empEstimate1 = new WPEmpEstimate();
        empEstimate1.setTimeSheetSubmission(2.0);
        WPEmpEstimate empEstimate2 = new WPEmpEstimate();
        empEstimate2.setTimeSheetSubmission(5.0);
        WPEmpEstimate empEstimate3 = new WPEmpEstimate();
        empEstimate3.setTimeSheetSubmission(3.0);
        
        empEstimates.add(empEstimate1);
        empEstimates.add(empEstimate2);
        empEstimates.add(empEstimate3);
        
        testStatusReport.setEmpEstimate(empEstimates);
        assertTrue(Math.abs(10.0 - testStatusReport.getTotalTimeSheetSubmission())
                == 0);
    }
    
    /**
     * Test getter for autogenerated value totalAC.
     */
    @Test
    public final void testGetTotalAC() {
        List<WPEmpEstimate> empEstimates = new ArrayList<WPEmpEstimate>();
        
        WPEmpEstimate empEstimate1 = new WPEmpEstimate();
        empEstimate1.setPayWage(10.0);
        empEstimate1.setTimeSheetSubmission(1.0);
        WPEmpEstimate empEstimate2 = new WPEmpEstimate();
        empEstimate2.setPayWage(20.0);
        empEstimate2.setTimeSheetSubmission(1.0);
        WPEmpEstimate empEstimate3 = new WPEmpEstimate();
        empEstimate3.setPayWage(30.0);
        empEstimate3.setTimeSheetSubmission(1.0);
        
        empEstimates.add(empEstimate1);
        empEstimates.add(empEstimate2);
        empEstimates.add(empEstimate3);
        
        testStatusReport.setEmpEstimate(empEstimates);
        assertTrue(Math.abs(60.0 - testStatusReport.getTotalAC())
                == 0);
    }
    
    /**
     * Test getter for autogenerated value TotalEst.
     */
    @Test
    public final void testGetTotalEst() {
        List<WPEmpEstimate> empEstimates = new ArrayList<WPEmpEstimate>();
        
        WPEmpEstimate empEstimate1 = new WPEmpEstimate();
        empEstimate1.setEstimate(5);
        
        WPEmpEstimate empEstimate2 = new WPEmpEstimate();
        empEstimate2.setEstimate(5);
        
        WPEmpEstimate empEstimate3 = new WPEmpEstimate();
        empEstimate3.setEstimate(4);
        
        empEstimates.add(empEstimate1);
        empEstimates.add(empEstimate2);
        empEstimates.add(empEstimate3);
        
        testStatusReport.setEmpEstimate(empEstimates);
        assertTrue(Math.abs(14 - testStatusReport.getTotalEst())
                == 0);
    }
    
    /**
     * Test getter for autogenerated value TotalEstDollars.
     */
    @Test
    public final void testGetTotalEstDollers() {
        List<WPEmpEstimate> empEstimates = new ArrayList<WPEmpEstimate>();
        
        WPEmpEstimate empEstimate1 = new WPEmpEstimate();
        empEstimate1.setEstimate(5);
        empEstimate1.setPayWage(10.0);
        WPEmpEstimate empEstimate2 = new WPEmpEstimate();
        empEstimate2.setEstimate(5);
        empEstimate2.setPayWage(20.0);
        WPEmpEstimate empEstimate3 = new WPEmpEstimate();
        empEstimate3.setEstimate(4);
        empEstimate3.setPayWage(30.0);
        
        empEstimates.add(empEstimate1);
        empEstimates.add(empEstimate2);
        empEstimates.add(empEstimate3);
        
        testStatusReport.setEmpEstimate(empEstimates);
        assertTrue(Math.abs(270.0 - testStatusReport.getTotalEstDollers())
                == 0);
    }
    
    /**
     * Test getter for autogenerated value TotalEstAct.
     */
    @Test
    public final void testGetTotalEstAct() {
        List<WPEmpEstimate> empEstimates = new ArrayList<WPEmpEstimate>();
        
        WPEmpEstimate empEstimate1 = new WPEmpEstimate();
        empEstimate1.setTimeSheetSubmission(1.0);
        empEstimate1.setEstimate(2);
        WPEmpEstimate empEstimate2 = new WPEmpEstimate();
        empEstimate2.setTimeSheetSubmission(3.0);
        empEstimate2.setEstimate(4);
        WPEmpEstimate empEstimate3 = new WPEmpEstimate();
        empEstimate3.setTimeSheetSubmission(5.0);
        empEstimate3.setEstimate(6);
        
        empEstimates.add(empEstimate1);
        empEstimates.add(empEstimate2);
        empEstimates.add(empEstimate3);
        
        testStatusReport.setEmpEstimate(empEstimates);
        assertTrue(Math.abs(21.0 - testStatusReport.getTotalEstAct())
                == 0);
    }
    
    /**
     * Test getter for autogenerated value TotalEstActDol.
     */
    @Test
    public final void testGetTotalEstActDol() {
        List<WPEmpEstimate> empEstimates = new ArrayList<WPEmpEstimate>();
        
        WPEmpEstimate empEstimate1 = new WPEmpEstimate();
        empEstimate1.setEstimate(1);
        empEstimate1.setTimeSheetSubmission(2.0);
        empEstimate1.setPayWage(10.0);
        
        WPEmpEstimate empEstimate2 = new WPEmpEstimate();
        empEstimate2.setEstimate(3);
        empEstimate2.setTimeSheetSubmission(5.0);
        empEstimate2.setPayWage(20.0);
        
        WPEmpEstimate empEstimate3 = new WPEmpEstimate();
        empEstimate3.setEstimate(5);
        empEstimate3.setTimeSheetSubmission(3.0);
        empEstimate3.setPayWage(30.0);
        
        
        empEstimates.add(empEstimate1);
        empEstimates.add(empEstimate2);
        empEstimates.add(empEstimate3);
        
        testStatusReport.setEmpEstimate(empEstimates);
        assertTrue(Math.abs(430.0 - testStatusReport.getTotalEstActDol())
                == 0);
    }

}
