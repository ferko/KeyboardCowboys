package bcit.ca.infosys.KeyboardCowboys.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.util.EmployeeComparer;

/**
 * 
 * Compare Employee by EmpName.
 * 
 * @author Vukasin
 *
 */
public class EmployeeComparerTest {
    
    /**
     * EmployeeComparer instances to be used in testing.
     */
    private EmployeeComparer testEmployeeComparer;
    
    /**
     * EmployeeComparerTest class constructor.
     */
    public EmployeeComparerTest() {
    }
    
    /**
     * Create new EmployeeComparer for each test case.
     */
    @Before
    public final void newEmployeeComparer() {
        testEmployeeComparer = new EmployeeComparer();
    }
    
    /**
     * Test Employee comparison, first is less than.
     * Compare Employee by EmpName.
     */
    @Test
    public final void testCompareLess() {
        Employee first = new Employee();
        first.setEmpFName("Bean");
        first.setEmpLName("Mr.");
        
        Employee second = new Employee();
        second.setEmpFName("Been");
        second.setEmpLName("Mr.");
        
        assertTrue(testEmployeeComparer.compare(first, second) < 0);
    }
    
    /**
     * Test Employee comparison, first is less than.
     */
    @Test
    public final void testCompareEqual() {
        Employee first = new Employee();
        first.setEmpFName("AllPersons");
        first.setEmpLName("Equal");
        
        Employee second = new Employee();
        second.setEmpFName("AllPersons");
        second.setEmpLName("Equal");
        
        assertEquals(0,  testEmployeeComparer.compare(first, second));
    }
    
    /**
     * Test Employee comparison, first is less than.
     */
    @Test
    public final void testCompareGreater() {
        Employee first = new Employee();
        first.setEmpFName("Zebra");
        first.setEmpLName("Animal");
        
        Employee second = new Employee();
        second.setEmpFName("Asp");
        second.setEmpLName("Animal");
        
        assertTrue(testEmployeeComparer.compare(first, second) > 0);
    }

}
