package bcit.ca.infosys.KeyboardCowboys.test.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import bcit.ca.infosys.KeyboardCowboys.model.Member;

/**
 * Member class tests.
 *
 * @author Vukasin
 */
public class MemberTest {
    
    /**
     * Member instances to be used in testing.
     */
    private Member testMember;
    
    /**
     * MemberTest class constructor.
     */
    public MemberTest() {
    }
    
    /**
     * Create new member for each test case.
     */
    @Before
    public final void newTestMember() {
        testMember = new Member();
    }

    /**
     * Test getters and setters for id.
     */
    @Test
    public final void testGetId() {
        testMember.setId(1L);
        assertEquals((Long) 1L, (testMember.getId()));
    }

    /**
     * Test getters and setters for name.
     */
    @Test
    public final void testGetName() {
        testMember.setName("Taylor");
        assertEquals("Taylor", testMember.getName());
    }

    /**
     * Test getters and setters for email.
     */
    @Test
    public final void testGetEmail() {
        testMember.setEmail("cowboys@keyboard.com");
        assertEquals("cowboys@keyboard.com", testMember.getEmail());
    }
    
    /**
     * Test getters and setters for phoneNumber.
     */
    @Test
    public final void testGetPhoneNumber() {
        testMember.setPhoneNumber("cowboys@keyboard.com");
        assertEquals("cowboys@keyboard.com", testMember.getPhoneNumber());
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
