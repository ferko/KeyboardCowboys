package bcit.ca.infosys.KeyboardCowboys.test.unit;

import static org.junit.Assert.assertEquals;
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
import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackageBudget;

/**
 * WorkPackage class tests.
 *
 * @author Vukasin
 */
public class WorkPackageTest {

    /**
     * WorkPackage instances to be used in testing.
     */
    private WorkPackage testWorkPackage;
    
    /**
     * WorkPackageTest class constructor.
     */
    public WorkPackageTest() {
    }

    /**
     * Create new WorkPackage for each test case.
     */
    @Before
    public final void newWorkPackage() {
        testWorkPackage = new WorkPackage();
    }
    
    /**
     * Test getters and setters for wpID.
     */
    @Test
    public final void testGetWpID() {
        testWorkPackage.setWpID("#1");
        assertEquals("#1", testWorkPackage.getWpID());
    }
    
    /**
     * Test getters and setters for wpName.
     */
    @Test
    public final void testGetWpName() {
        testWorkPackage.setWpName("packageOfWork");
        assertEquals("packageOfWork", testWorkPackage.getWpName());
    }
    
    /**
     * Test getters and setters for wpProject.
     */
    @Test
    public final void testGetWpProject() {
        Project testProject = new Project();
        testWorkPackage.setWpProject(testProject);
        assertEquals(testProject, testWorkPackage.getWpProject());
    }
    
    /**
     * Test getters and setters for wpPackages.
     */
    @Test
    public final void testGetWpPackages() {
        List<WorkPackage> testWorkPackages = new ArrayList<WorkPackage>();
        testWorkPackage.setWpPackages(testWorkPackages);
        assertEquals(testWorkPackages, testWorkPackage.getWpPackages());
    }
    
    /**
     * Test getters and setters for wpEmployees.
     */
    @Test
    public final void testGetWpEmployees() {
        List<Employee> testEmployees = new ArrayList<Employee>();
        testWorkPackage.setWpEmployees(testEmployees);
        assertEquals(testEmployees, testWorkPackage.getWpEmployees());
    }
    
    /**
     * Test getters and setters for wpPackage.
     */
    @Test
    public final void testGetWpPackage() {
        WorkPackage testInnerWorkPackage = new WorkPackage();
        testWorkPackage.setWpPackage(testInnerWorkPackage);
        assertEquals(testInnerWorkPackage, testWorkPackage.getWpPackage());
    }
    
    /**
     * Test getters and setters for wpResEngineer.
     */
    @Test
    public final void testGetWpResEngineer() {
        Employee testEmployee = new Employee();
        testWorkPackage.setWpResEngineer(testEmployee);
        assertEquals(testEmployee, testWorkPackage.getWpResEngineer());
    }
    
    /**
     * Test getters and setters for wpStartDate.
     */
    @Test
    public final void testGetWpStartDate() {
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Date testWpStartDate;
        try {
            
            testWpStartDate = df.parse("11/12/13");
            testWorkPackage.setWpStartDate(testWpStartDate);
            assertEquals(testWpStartDate, testWorkPackage.getWpStartDate());
            
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
    
    /**
     * Test getters and setters for wpEndDate.
     */
    @Test
    public final void testGetWpEndDate() {
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Date testWpEndDate;
        try {
            
            testWpEndDate = df.parse("01/01/2014");
            testWorkPackage.setWpEndDate(testWpEndDate);
            assertEquals(testWpEndDate, testWorkPackage.getWpEndDate());
            
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test getters and setters for wpStatus.
     */
    @Test
    public final void testGetWpStatus() {
        final String testStatus = "Good";
        testWorkPackage.setWpStatus(testStatus);
        assertEquals(testStatus, testWorkPackage.getWpStatus());
    }
    
    /**
     * Test getters and setters for wpDescription.
     */
    @Test
    public final void testGetWpDescription() {
        final String testDescription = "Good Condition.";
        testWorkPackage.setWpDescription(testDescription);
        assertEquals(testDescription, testWorkPackage.getWpDescription());
    }
    
    /**
     * Test getters and setters for wpResAssistant.
     */
    @Test
    public final void testGetWpResAssistant() {
        Employee testWpResAssistant = new Employee();
        testWorkPackage.setWpResAssistant(testWpResAssistant);
        assertEquals(testWpResAssistant, testWorkPackage.getWpResAssistant());
    }
    
    /**
     * Test getters and setters for wpBudgets.
     */
    @Test
    public final void testGetWpBudgets() {
        List<WorkPackageBudget> testWpBudgets
                = new ArrayList<WorkPackageBudget>();
        testWorkPackage.setWpBudgets(testWpBudgets);
        assertEquals(testWpBudgets, testWorkPackage.getWpBudgets());
    }
}
