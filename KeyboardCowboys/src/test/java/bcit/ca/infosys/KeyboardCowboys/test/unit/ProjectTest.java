package bcit.ca.infosys.KeyboardCowboys.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;


/**
 * Project class tests.
 *
 * @author Vukasin
 */
public class ProjectTest {
    
    /**
     * Project instances to be used in testing.
     */
    private Project testProject;
    
    /**
     * ProjectTest class constructor.
     */
    public ProjectTest() {
    }
    
    /**
     * Create new Project for each test case.
     */
    @Before
    public final void newTestProject() {
        testProject = new Project();
    }
    
    /**
     * Test getters and setters for projID.
     */
    @Test
    public final void testGetProjID() {
        final String testId = "#1";
        testProject.setProjID(testId);
        assertEquals(testId, testProject.getProjID());
    }

    /**
     * Test getters and setters for projName.
     */
    @Test
    public final void testGetProjName() {
        final String testName = "Manhattan";
        testProject.setProjName(testName);
        assertEquals(testName, testProject.getProjName());
    }
    
    /**
     * Test getters and setters for projManager.
     */
    @Test
    public final void testGetProjManager() {
        Employee testProjWorkPackages = new Employee();
        testProject.setProjManager(testProjWorkPackages);
        assertEquals(testProjWorkPackages, testProject.getProjManager());
    }

    /**
     * Test getters and setters for projWorkPackages.
     */
    @Test
    public final void testGetProjWorkPackages() {
        Set<WorkPackage> testProjWorkPackages = new HashSet<WorkPackage>();
        testProject.setProjWorkPackages(testProjWorkPackages);
        assertEquals(testProjWorkPackages, testProject.getProjWorkPackages());
    }
    
    /**
     * Test getters and setters for projEmployees.
     */
    @Test
    public final void testGetProjEmployees() {
        Set<Employee> testEmployees = new HashSet<Employee>();
        testProject.setProjEmployees(testEmployees);
        assertEquals(testEmployees, testProject.getProjEmployees());
    }

    /**
     * Test getters and setters for projAssistant.
     */
    @Test
    public final void testGetProjAssistant() {
        Employee testProjAssistant = new Employee();
        testProject.setProjAssistant(testProjAssistant);
        assertEquals(testProjAssistant, testProject.getProjAssistant());
    }

    /**
     * Test getters and setters for projStartDate.
     * @throws ParseException 
     */
    @Test
    public final void testGetProjStartDate() throws ParseException {
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Date testProjStartDate = df.parse("01/08/13");
        testProject.setProjStartDate(testProjStartDate);
        assertEquals(testProjStartDate, testProject.getProjStartDate());
    }

    /**
     * Test getters and setters for projEndDate.
     * @throws ParseException 
     */
    @Test
    public final void testGetProjEndDate() throws ParseException {
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Date testProjectEndDate = df.parse("04/08/13");
        testProject.setProjEndDate(testProjectEndDate);
        assertEquals(testProjectEndDate, testProject.getProjEndDate());
    }


    /**
     * Test getters and setters for projStatus.
     */
    @Test
    public final void testGetProjStatus() {
        String testProjStatus = "Blocked";
        testProject.setProjStatus(testProjStatus);
        assertEquals(testProjStatus, testProject.getProjStatus());
    }

    /**
     * Test getters and setters for projCostEstimate.
     */
    @Test
    public final void testGetProjCostEstimate() {
        final double testProjCostEstimate = 99.9;
        testProject.setProjCostEstimate(testProjCostEstimate);
        assertTrue(Math.abs(testProjCostEstimate - testProject
                .getProjCostEstimate()) == 0);
    }
    
    /**
     * Test getters and setters for projDescription.
     */
    @Test
    public final void testGetProjDescription() {
        final String testDescription = "ineffable";
        testProject.setProjDescription(testDescription);
        assertEquals(testDescription, testProject.getProjDescription());
    }
    
    /**
     * Test getter for generated value projEmployeesList.
     */
    @Test
    public final void testGetProjEmployeesList() {
        List<Employee> testEmployees = new ArrayList<Employee>();
        assertEquals(testEmployees, testProject.getProjEmployeesList());
    }
    
    /**
     * Test getter for generated value workPackagesList.
     */
    @Test
    public final void testGetWorkPackagesList() {
        List<WorkPackage> testWorkPackages = new ArrayList<WorkPackage>();
        testProject.setProjWorkPackages(new TreeSet<WorkPackage>());
        assertEquals(testWorkPackages, testProject.getWorkPackagesList());
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
