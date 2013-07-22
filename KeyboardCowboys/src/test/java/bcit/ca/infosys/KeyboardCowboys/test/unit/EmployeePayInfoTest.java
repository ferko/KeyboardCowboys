package bcit.ca.infosys.KeyboardCowboys.test.unit;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.EmployeePayInfo;
import bcit.ca.infosys.KeyboardCowboys.model.PayLevel;

/**
 * EmployeePayInfo class tests.
 *
 * @author Vukasin
 */
public class EmployeePayInfoTest {
    
    /**
     * EmployeePayInfo instances to be used in testing.
     */
    private EmployeePayInfo testEmployeePayInfo;
    
    /**
     * EmployeePayInfoTest class constructor.
     */
    public EmployeePayInfoTest() {
    }
    
    /**
     * Create new employeePayInfo for each test case.
     */
    @Before
    public final void newTestEmployeePayInfo() {
        testEmployeePayInfo = new EmployeePayInfo();
    }
    
    /**
     * Test getters and setters for epiPayLevel.
     */
    @Test
    public final void testGetEpiPayLevel() {
        PayLevel testEpiPayLevel = new PayLevel();
        testEmployeePayInfo.setEpiPayLevel(testEpiPayLevel);
        assertEquals(testEpiPayLevel, testEmployeePayInfo.getEpiPayLevel());
    }
    
    /**
     * Test getters and setters for epiEmployee.
     */
    @Test
    public final void testGetEpiEmployee() {
        Employee testEpiEmployee = new Employee();
        testEmployeePayInfo.setEpiEmployee(testEpiEmployee);
        assertEquals(testEpiEmployee, testEmployeePayInfo.getEpiEmployee());
    }
    
    /**
     * Test getters and setters for epiHolidays.
     */
    @Test
    public final void testGetEpiHolidays() {
        final double holidayCount = 25.0;
        testEmployeePayInfo.setEpiHolidays(holidayCount);
        assertTrue(Math.abs(holidayCount - testEmployeePayInfo.getEpiHolidays())
                == 0);
    }
    
    /**
     * Test getters and setters for epiFlex.
     */
    @Test
    public final void testGetEpiFlex() {
        final double flexCount = 9.00;
        testEmployeePayInfo.setEpiFlex(flexCount);
        assertTrue(Math.abs(flexCount - testEmployeePayInfo.getEpiFlex()) == 0);
    }
    
    /**
     * Test getters and setters for epiId.
     */
    @Test
    public final void testGetEpiId() {
        final int testId = 123;
        testEmployeePayInfo.setEpiId(testId);
        assertEquals(testId, testEmployeePayInfo.getEpiId().intValue());
    }
    
    /**
     * Test getters and setters for epiOvertime.
     */
    @Test
    public final void testGetEpiOvertime() {
        final double testOvertime = 6.66;
        testEmployeePayInfo.setEpiOvertime(testOvertime);
        assertTrue(Math.abs(testOvertime - testEmployeePayInfo
                .getEpiOvertime()) == 0);
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
