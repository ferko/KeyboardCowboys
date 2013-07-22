package bcit.ca.infosys.KeyboardCowboys.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.Principle;
import bcit.ca.infosys.KeyboardCowboys.util.SHAHash;

/**
 * Principle class tests.
 *
 * @author Vukasin
 */
public class PrincipleTest {
    
    /**
     * Principle instances to be used in testing.
     */
    private Principle testPrinciple;
    
    /**
     * PrincipleTest class constructor.
     */
    public PrincipleTest() {
    }
    
    /**
     * Create new principle for each test case.
     */
    @Before
    public final void newTestPrinciple() {
        testPrinciple = new Principle();
    }

    /**
     * Test getters and setters for empID.
     */
    @Test
    public final void testGetEmpID() {
		Employee emp = new Employee();
		emp.setEmpUserName("foobar");
    	testPrinciple.setEmp(emp);
        assertEquals("foobar", testPrinciple.getEmp().getEmpUserName());
    }

    /**
     * Test getters and setters for empPwd.
     */
    @Test
    public final void testGetEmpPassword() {
        String testPassword = "p@$$w0rd";
        testPrinciple.setEmpPassword(testPassword);
        assertEquals(SHAHash.hash(testPassword),
                testPrinciple.getEmpPassword());
    }
    
    /**
     * Test getters and setters for emp.
     */
    @Test
    public final void testGetEmp() {
        Employee emp = new Employee();
        testPrinciple.setEmp(emp);
        assertEquals(emp, testPrinciple.getEmp());
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
