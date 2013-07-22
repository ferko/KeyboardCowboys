package bcit.ca.infosys.KeyboardCowboys.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.TimeRow;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheet;

/**
 * TimeSheet class tests.
 *
 * @author Vukasin
 */
public class TimeSheetTest {
    
    /**
     * TimeSheet instances to be used in testing.
     */
    private TimeSheet testTimeSheet;
    
    /**
     * TimeSheetTest class constructor.
     */
    public TimeSheetTest() {
    }
    
    /**
     * Create new TimeSheet for each test case.
     */
    @Before
    public final void newTimeSheet() {
        testTimeSheet = new TimeSheet();
    }
    
    /**
     * Test getters and setters for tsID.
     */
    @Test
    public final void testGetTsID() {
        testTimeSheet.setTsID(1);
        assertEquals(1, testTimeSheet.getTsID().intValue());
    }
    
    /**
     * Test getters and setters for tsWeekEnding.
     */
    @Test
    public final void testGetTsWeekEnding() {
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Date testWeekEndingDate;
        try {
        	testWeekEndingDate = df.parse("03/17/13");
        	testTimeSheet.setTsWeekEnding(testWeekEndingDate);
        	assertTrue("Dates comparison not precise",
        	        (testWeekEndingDate.getTime() - testTimeSheet
        	                .getTsWeekEnding().getTime()) < 1000);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
    
    /**
     * Test getters and setters for tsEmp.
     */
    @Test
    public final void testGetTsEmp() {
        Employee testEmployee = new Employee();
        testTimeSheet.setTsEmp(testEmployee);
        assertEquals(testEmployee, testTimeSheet.getTsEmp());
    }
    
    /**
     * Test getters and setters for tsTimeRows.
     */
    @Test
    public final void testGetTsTimeRows() {
        List<TimeRow> testTsTimeRows = new ArrayList<TimeRow>();
        testTimeSheet.setTsTimeRows(testTsTimeRows);
        assertEquals(testTsTimeRows, testTimeSheet.getTsTimeRows());
    }

}
