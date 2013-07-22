package bcit.ca.infosys.KeyboardCowboys.test.ui.login;

import java.io.File;
import java.net.URL;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import bcit.ca.infosys.KeyboardCowboys.controller.LoginController;
import bcit.ca.infosys.KeyboardCowboys.data.EmployeeAccess;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.EmployeePayInfo;
import bcit.ca.infosys.KeyboardCowboys.model.PayLevel;
import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.TimeRow;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheet;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheetApproval;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;
import bcit.ca.infosys.KeyboardCowboys.test.ArchiveUtils;
import bcit.ca.infosys.KeyboardCowboys.test.ui.TestSteps;
import bcit.ca.infosys.KeyboardCowboys.util.Resources;
import bcit.ca.infosys.KeyboardCowboys.util.SHAHash;

import com.thoughtworks.selenium.DefaultSelenium;

/**
 * Test cases for the Login use case.
 * 
 * @author Frank Berenyi, Steven Smith, Merisha Shim, Vukasin Simic
 */
@RunWith(Arquillian.class)
public class LoginTest {
    
    /**
     * Required Arquillian Shrinkwrap.
     * 
     * @return WebArchive
     */
	@Deployment(testable = false)
	public static WebArchive createDeployment() {
		File[] libs = Maven.resolver().loadPomFromFile("pom.xml")
				.importRuntimeDependencies().as(File.class);
		WebArchive res = ShrinkWrap
				.create(WebArchive.class, "loginTest.war")
				.addClasses(LoginController.class, Employee.class,
						EmployeeAccess.class, EmployeePayInfo.class,
						PayLevel.class, TimeSheetApproval.class, Project.class,
						TimeSheet.class, TimeRow.class, WorkPackage.class,
						SHAHash.class, Resources.class)
				.addPackage("bcit.ca.infosys.KeyboardCowboys.controller")
				.addPackage("bcit.ca.infosys.KeyboardCowboys.converter")
				.addPackage("bcit.ca.infosys.KeyboardCowboys.data")
				.addPackage("bcit.ca.infosys.KeyboardCowboys.exceptions")
				.addPackage("bcit.ca.infosys.KeyboardCowboys.model")
				.addPackage("bcit.ca.infosys.KeyboardCowboys.service")
				.addPackage("bcit.ca.infosys.KeyboardCowboys.util")
				.addPackage("bcit.ca.infosys.KeyboardCowboys.qualifiers")
				.addPackage("bcit.ca.infosys.KeyboardCowboys.interfaces")
				.addPackage("bcit.ca.infosys.KeyboardCowboys.validators")
				                
				.addAsWebInfResource("test-ds.xml", "test-ds.xml")
				.addAsResource(
						new File(
						"src/test/resources/META-INF/test-persistence.xml"),
						"META-INF/persistence.xml")
                .addAsResource(new File("src/main/resources/import.sql"),
                        "import.sql")
				.addAsWebInfResource(
						new File("src/test/resources/jboss-web.xml"),
						"jboss-web.xml");
		for (File file : libs) {
			res.addAsLibrary(file);
		}
		return ArchiveUtils.addWebResourcesRecursively(res, new File(
				"src/main/webapp"));
	}

	/** Inject application's EntityManager. */
	@Inject
	private EntityManager em;
	
    /** Default implementation of the Selenium browser interface. */
	@Drone
	private DefaultSelenium selenium;
	//@Drone WebDriver browser; // Webdriver 2.0, potential Selenium upgrade
	
    /** Inject the URL of the deployed application. */
	@ArquillianResource
	private URL deploymentUrl;
	
	/** Actions executed before each of the test cases in this class.
	 *  Any preconditions to all these tests cases, like logging in. */
	@Before
    public final void setup() {
	    //Logout user if a user from a previous test case is still logged in.
	    if (selenium.isElementPresent("id=navForm:btnLogout")) {
	        selenium.click("id=navForm:btnLogout");
	        selenium.waitForPageToLoad("30000");
	    }  
    }
    /** Actions executed after each of the test cases in this class.
     *  Resetting the database to its initial state, etc. */
	@After
    public void tearDown()  {
    }
	
    /** Unsuccessful login. */
    @Test
    public final void shouldNotLoginSuccessfully() {
        selenium.open(deploymentUrl + "login.xhtml");
        
        TestSteps.loginAs("invisibleman", "hgwells", selenium);

        Assert.assertFalse("Username should not be logged in!", selenium
                .isElementPresent("xpath=//*[@id='navForm:btnLogout']"));
    }
	
	/** Successful login.
	 *  Uses existing dummy test user "jsmith". */
	@Test
	public final void shouldLoginSuccessfully() {
		selenium.open(deploymentUrl + "login.xhtml");
		
		TestSteps.loginAs("jsmith", "joe", selenium);
		
		Assert.assertTrue("Username should be logged in!", selenium
				.isElementPresent("xpath=//*[@id='navForm:btnLogout']"));
	}
    
    /** Log in as newly created user. */
	@Test
	public final void newUserLogin() {
		selenium.open(deploymentUrl + "login.xhtml");

        TestSteps.loginAs("jsmith", "joe", selenium);

        //Create New Employee link
        selenium.click("id=navForm:urlCreateEmp");
        selenium.waitForPageToLoad("30000");
        
        TestSteps.createNewEmployee("new", "user", "newuser", "Pass4$", "Pass4$", 0,
                null, null, selenium);
        
        //Logout
        selenium.click("id=navForm:btnLogout");
        
        //Attempt to log as newly created user.
		TestSteps.loginAs("newuser", "Pass4$", selenium);
		
		Assert.assertTrue("Username should be logged in!", selenium
				.isElementPresent("xpath=//*[@id='navForm:btnLogout']"));
	} 
	
	/**  */
	@Test
	public final void loginAccess() {
	    selenium.open(deploymentUrl + "login.xhtml");
	    TestSteps.loginAs("jsmith", "joe", selenium);
	    
        selenium.click("//*[@id='navForm:urlCreateEmp']");
        Assert.assertTrue("Should be on Create Employee page", selenium
                .isElementPresent("xpath=//*[@id='createEmp:btnSaveEmp']"));
        
        selenium.click("//*[@id='navForm:urlCreateProj']");
        Assert.assertTrue("Should be on Create Project page", selenium
                .isElementPresent("xpath=//*[@id='formCreateProj:btnSaveProj']"));
        
        selenium.click("//*[@id='navForm:urlCreateTime']");
        Assert.assertTrue("Should be on Create Timesheet page", selenium
               .isElementPresent("xpath=//*[@id='formTSdateSelect:calTSdate_input']"));
        
        //selenium.click("id=navForm:urlViewSR");
        //Assert.assertTrue("Should be on View Status Report page", selenium
        //        .isElementPresent("xpath=//*[@id='formViewStatRpt:srWP_input']"));
 
	}
}