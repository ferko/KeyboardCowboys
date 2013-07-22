package bcit.ca.infosys.KeyboardCowboys.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.Ignore;

import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.EmployeePayInfo;
import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheetApproval;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;


/**
 * Employee class tests.
 *
 * @author Vukasin
 */
public class EmployeeTest {
    /**
     * Employee instances to be used in testing.
     */
    private Employee testEmployee;
    
    /**
     * EmployeeTest class constructor.
     */
    public EmployeeTest() {
    }

    /**
     * Create new Employee for each test case.
     */
    @Before
    public final void newTestEmployee() {
        testEmployee = new Employee();
    }
    
    /**
     * Test getters and setters for empID.
     */
    @Test
    public final void testGetEmpID() {
        testEmployee.setEmpID(1);
        assertEquals(1, testEmployee.getEmpID().intValue());
    }
    
    /**
     * Test getters and setters for EmpFName.
     */
    @Test
    public final void testGetEmpFName() {
        final String fname = "Morgan";
        testEmployee.setEmpFName(fname);
        assertEquals(fname, testEmployee.getEmpFName());
    }

    /**
     * Test getters and setters for EmpLName.
     */
    @Test
    public final void testGetEmpLName() {
        final String lname = "Doe";
        testEmployee.setEmpLName(lname);
        assertEquals(lname, testEmployee.getEmpLName());
    }
    
    /**
     * Test getters and setters for Role.
     */
    @Test
    public final void testGetEmpRole() {
        testEmployee.setEmpRole("testRole");
        assertEquals("testRole", testEmployee.getEmpRole());
    }
    

    /**
     * Test getters and setters for EmpUserName.
     */
    @Test
    public final void testGetEmpUserName() {
        testEmployee.setEmpUserName("jDoe");
        assertEquals("jDoe", testEmployee.getEmpUserName());
    }
    
    /**
     * Test getters and setters for tsaApprovals.
     */
    @Test
    public final void testGetTsaApprovals() {
        List<TimeSheetApproval> testTsaApprovals
            = new ArrayList<TimeSheetApproval>();
        testEmployee.setTsaApprovals(testTsaApprovals);
        assertEquals(testTsaApprovals, testEmployee.getTsaApprovals());
    }
    
    /**
     * Test getters and setters for empPayInfo.
     */
    @Test
    public final void testGetEmpPayInfo() {
        EmployeePayInfo testEmpPayInfo = new EmployeePayInfo();
        testEmployee.setEmpPayInfo(testEmpPayInfo);
        assertEquals(testEmpPayInfo, testEmployee.getEmpPayInfo());
    }
    
    /**
     * Test getters and setters for projects.
     */
    @Test
    public final void testGetProjects() {
        List<Project> testProjects = new ArrayList<Project>();
        testEmployee.setProjects(testProjects);
        assertEquals(testProjects, testEmployee.getProjects());
    }
    
    /**
     * Test getters and setters for tsApprover.
     */
    @Test
    public final void testGetTsApprover() {
        Employee testApprover = new Employee();
        testEmployee.setTsApprover(testApprover);
        assertEquals(testApprover, testEmployee.getTsApprover());
    }
    
    /**
     * Test getters and setters for workPackages.
     */
    @Test
    public final void testGetWorkPackages() {
        List<WorkPackage> testWorkPackages = new ArrayList<WorkPackage>();
        testEmployee.setWorkPackages(testWorkPackages);
        assertEquals(testWorkPackages, testEmployee.getWorkPackages());
    }

    /**
     * Test getters and setters for managedProjects.
     */
    @Test
    public final void testGetManagedProjects() {
        List<Project> testProjects = new ArrayList<Project>();
        testEmployee.setManagedProjects(testProjects);
        assertEquals(testProjects, testEmployee.getManagedProjects());
    }

    /**
     * Test getters and setters for assistedProjects.
     */
    @Test
    public final void testGetAssistedProjects() {
        List<Project> testProjects = new ArrayList<Project>();
        testEmployee.setAssistedProjects(testProjects);
        assertEquals(testProjects, testEmployee.getAssistedProjects());
    }

    /**
     * Test getters and setters for wpEngineered.
     */
    @Test
    public final void testGetWpEngineered() {
        List<WorkPackage> testWorkPackages = new ArrayList<WorkPackage>();
        testEmployee.setWpEngineered(testWorkPackages);
        assertEquals(testWorkPackages, testEmployee.getWpEngineered());
    }
    
    /**
     * Test getters and setters for assistedWorkPackages.
     */
    @Test
    public final void testGetWpResAssistanted() {
        List<WorkPackage> testWorkPackages = new ArrayList<WorkPackage>();
        testEmployee.setWpResAssisted(testWorkPackages);
        assertEquals(testWorkPackages, testEmployee.getWpResAssisted());
    }
    
    /**
     * Test getters and setters for empSuperVisor.
     */
    @Test
    public final void testGetEmpSuperVisor() {
        Employee testSupervisor = new Employee();
        testEmployee.setEmpSuperVisor(testSupervisor);
        assertEquals(testSupervisor, testEmployee.getEmpSuperVisor());
    }

    /**
     * Test getters and setters for superVisees.
     */
    @Test
    public final void testGetSuperVisees() {
        List<Employee> testSupervisees = new ArrayList<Employee>();
        testEmployee.setSuperVisees(testSupervisees);
        assertEquals(testSupervisees, testEmployee.getSuperVisees());
    }
    
    /**
     * Test getters and setters for wpResAssisted.
     */
    @Test
    public final void testGetWpResAssisted() {
        List<WorkPackage> testWpResAssisted = new ArrayList<WorkPackage>();
        testEmployee.setWpResAssisted(testWpResAssisted);
        assertEquals(testWpResAssisted, testEmployee.getWpResAssisted());
    }

    /**
     * 
     */
    @Test
    @Ignore
    public final void testHashCode() {
        fail("Not yet implemented");
    }

    /**
     * 
     */
    @Test
    @Ignore
    public final void testEqualsObject() {
        fail("Not yet implemented");
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