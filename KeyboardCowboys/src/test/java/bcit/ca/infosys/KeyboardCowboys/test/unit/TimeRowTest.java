package bcit.ca.infosys.KeyboardCowboys.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.TimeRow;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheet;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;

/**
 * TimeRow class tests.
 *
 * @author Vukasin
 */
public class TimeRowTest {
    
    /**
     * TimeRow instances to be used in testing.
     */
    private TimeRow testTimeRow;
    
    /**
     * TimeRowTest class constructor.
     */
    public TimeRowTest() {
    }

    /**
     * Create new TimeRow for each test case.
     */
    @Before
    public final void newTimeRow() {
        testTimeRow = new TimeRow();
    }
    
    /**
     * Test getters and setters for trSun.
     */
    @Test
    public final void testGetTrSun() {
        final double testTrSun = 7.00;
        testTimeRow.setTrSun(testTrSun);
        assertTrue(Math.abs(testTrSun - testTimeRow.getTrSun()) == 0);
    }
    
    /**
     * Test getters and setters for trMon.
     */
    @Test
    public final void testGetTrMon() {
        final double testTrMon = 1.00;
        testTimeRow.setTrMon(testTrMon);
        assertTrue(Math.abs(testTrMon - testTimeRow.getTrMon()) == 0);
    }

    /**
     * Test getters and setters for trTue.
     */
    @Test
    public final void testGetTrTue() {
        final double testTrTue = 2.00;
        testTimeRow.setTrTue(testTrTue);
        assertTrue(Math.abs(testTrTue - testTimeRow.getTrTue()) == 0);
    }
    
    /**
     * Test getters and setters for trWed.
     */
    @Test
    public final void testGetTrWed() {
        final double testTrWed = 3.00;
        testTimeRow.setTrWed(testTrWed);
        assertTrue(Math.abs(testTrWed - testTimeRow.getTrWed()) == 0);
    }

    /**
     * Test getters and setters for trThu.
     */
    @Test
    public final void testGetTrThu() {
        final double testTrThu = 4.00;
        testTimeRow.setTrThu(testTrThu);
        assertTrue(Math.abs(testTrThu - testTimeRow.getTrThu()) == 0);
    }

    /**
     * Test getters and setters for trFri.
     */
    @Test
    public final void testGetTrFri() {
        final double testTrFri = 5.00;
        testTimeRow.setTrFri(testTrFri);
        assertTrue(Math.abs(testTrFri - testTimeRow.getTrFri()) == 0);
    }

    /**
     * Test getters and setters for trSat.
     */
    @Test
    public final void testGetTrSat() {
        final double testTrSat = 6.00;
        testTimeRow.setTrSat(testTrSat);
        assertTrue(Math.abs(testTrSat - testTimeRow.getTrSat()) == 0);
    }

    /**
     * Test getters and setters for trWorkPackage.
     */
    @Test
    public final void testGetTrWorkPackage() {
        WorkPackage testWorkPackage = new WorkPackage();
        testTimeRow.setTrWorkPackage(testWorkPackage);
        assertEquals(testWorkPackage, testTimeRow.getTrWorkPackage());
    }
    
    /**
     * Test getters and setters for trNotes.
     */
    @Test
    public final void testGetTrNotes() {
        testTimeRow.setTrNotes("NoteToSelf");
        assertEquals("NoteToSelf", testTimeRow.getTrNotes());
    }
    
    /**
     * Test getters and setters for trID.
     */
    @Test
    public final void testGetTrID() {
        testTimeRow.setTrID(1);
        assertEquals(1, testTimeRow.getTrID().intValue());
    }
    
    /**
     * Test getters and setters for trTimesheet.
     */
    @Test
    public final void testGetTrTimesheet() {
        TimeSheet testTimesheet = new TimeSheet();
        testTimeRow.setTrTimesheet(testTimesheet);
        assertEquals(testTimesheet, testTimeRow.getTrTimesheet());
    }
    
    /**
     * Test getters and setters for trProject.
     */
    @Test
    public final void testGetTrProject() {
        Project testProject = new Project();
        testTimeRow.setTrProject(testProject);
        assertEquals(testProject, testTimeRow.getTrProject());
    }

    /**
     * Test getter for auto-generated value Total.
     */
    @Test
    public final void testGetTotal() {
        testTimeRow.setTrFri(8);
        testTimeRow.setTrMon(8);
        testTimeRow.setTrTue(7);
        assertTrue(Math.abs(23 - testTimeRow.getTotal())
                == 0);   
    }

}
