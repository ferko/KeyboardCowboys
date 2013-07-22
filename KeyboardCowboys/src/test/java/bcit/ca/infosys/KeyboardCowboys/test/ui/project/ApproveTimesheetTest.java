package bcit.ca.infosys.KeyboardCowboys.test.ui.project;

import static org.junit.Assert.assertTrue;

import java.io.File;
import java.net.URL;

import javax.inject.Inject;
import javax.persistence.EntityManager;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
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
 * Test cases for the Create Project use case.
 * 
 * @author Frank Berenyi, Vukasin Simic, Steven Smith
 * 
 */
@RunWith(Arquillian.class)
@RunAsClient
public class ApproveTimesheetTest {

	/*
	 * TODO
	 * 
	 * -login as non-hr(supervisor,active employee, non-active), view
	 * CreateProject page, CreateProject link -login as hr and view
	 * CreateProject page, CreateProject link -create project with null values
	 * test project id, project name, cost estimate, dates, description (length,
	 * allowed values, ranges for cost and dates) -create project, logout and
	 * back in, confirm present in ViewProjects
	 */

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
				.create(WebArchive.class, "createProjectTest.war")
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
						new File("src/test/resources/META-INF/"
								+ "test-persistence.xml"),
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
	// @Drone WebDriver browser;

	/** Inject the URL of the deployed application. */
	@ArquillianResource
	private URL deploymentUrl;

	/**
	 * Actions executed before each of the test cases in this class. Any
	 * preconditions to all these tests cases, like logging in.
	 */
	@Before
	public final void setup() {
		// Logout user if a user from a previous test case is still logged in.
		if (selenium.isElementPresent("id=navForm:btnLogout")) {
			selenium.click("id=navForm:btnLogout");
			selenium.waitForPageToLoad("30000");
		}
	}

	/**
	 * Actions executed after each of the test cases in this class. Resetting
	 * the database to its initial state, etc.
	 */
	@After
	public void tearDown() {
	}
	/**
	 * @author Merisha Shim
	 * 			Log in as HR and try to go to approve timesheets link.
	 *			Link should not show up and should not be able to go to the Approve Timesheets page.
	 */
	@Test
	public final void approveTimesheetAsHRTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);
		
		
		selenium.click("//a[@id='navForm:btnLogout']");
		//selenium.waitForPageToLoad("30000");
		Assert.assertFalse(selenium.isTextPresent("Approve Timesheets"));

	}
	/**
	 * @author Merisha Shim
	 * 			Log in as Supervisor and try to go to approve timesheets link.
	 *			Link should not show up and should not be able to go to the Approve Timesheets page.
	 */
	@Test
	public final void approveTimesheetsAsSupervisorTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("supervisor", "joe", selenium);
		
		
		selenium.click("//a[@id='navForm:btnLogout']");
		//selenium.waitForPageToLoad("30000");
		
		Assert.assertFalse(selenium.isTextPresent("Approve Timesheets"));

	}
	/**
	 * @author Merisha Shim
	 * 			Log in as an employee and try to go to approve timesheets link.
	 *			Link should not show up and should not be able to go to the Approve Timesheets page.
	 */
	@Test
	public final void approveTimesheetAsEmployeeTest() {
		
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("user", "joe", selenium);
		selenium.click("//a[@id='navForm:btnLogout']");
		selenium.waitForPageToLoad("30000");
		Assert.assertFalse(selenium.isTextPresent("Approve Timesheets"));

	}
	
	/**
	 * @author Merisha Shim
	 * 			Be sure to have a supervisor assigned as a timesheet approver. Log in as that supervisor and try to go to approve timesheets link.
	 *			Link should show up and should be able to go to Approve Timesheets page.
	 */
	@Test
	public final void approveTimesheetAsTimesheetApprover() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);
				
		selenium.click("id=navForm:urlCreateEmp");
		//selenium.waitForPageToLoad("30000");
		selenium.type("id=createEmp:empFirstName", "Merisha");
		selenium.type("id=createEmp:empLastName", "Shim");
		selenium.type("id=createEmp:empUserName", "mshim");
		selenium.type("id=createEmp:empPassword", "Password1234!");
		selenium.type("id=createEmp:empPasswordMatch", "Password1234!");
		selenium.click("id=createEmp:empRole:0");
		selenium.click("//table[@id='createEmp:empRole']/tbody/tr/td/div/div[2]");
		selenium.click("//div[@id='createEmp:empSuperVisor']/div[2]/span");
		selenium.click("//div[@id='createEmp:empSuperVisor_panel']/div/ul/li[2]");
		selenium.click("//div[@id='createEmp:empTimeSheetApprover']/div[2]/span");
		selenium.click("//div[@id='createEmp:empTimeSheetApprover_panel']/div/ul/li[2]");
		selenium.click("id=createEmp:btnSaveEmp");
		selenium.click("//a[@id='navForm:btnLogout']");
		//selenium.waitForPageToLoad("30000");
		selenium.type("id=login_form:username", "mshim");
		selenium.type("id=login_form:password", "Password1234!");
		selenium.click("id=login_form:login_button");
		//selenium.waitForPageToLoad("30000");
		selenium.click("//a[@id='navForm:btnLogout']");
		//selenium.waitForPageToLoad("30000");
		selenium.type("id=login_form:username", "supervisor");
		selenium.type("id=login_form:password", "joe");
		selenium.click("id=login_form:login_button");
		//selenium.waitForPageToLoad("30000");
		selenium.click("//a[@id='navForm:urlViewTSA']");
		
        Assert.assertTrue("Should see Approve Timesheets link", selenium
                .isElementPresent("xpath=//a[@id='navForm:urlViewTSA']"));
		
	}
	/**
	 * @author Merisha Shim
	 * 			Have a supervisor be assigned as a timesheet approver to two employees. 
	 *			The two employees should create a timesheet and submit it for approval.
	 *			The timesheets should show up on the Approve Timesheet page for the supervisor
	 */
	@Ignore
	@Test
	public final void approveMultipleTimesheetsTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);
		
		
	}
}
