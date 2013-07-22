package bcit.ca.infosys.KeyboardCowboys.test.ui.employee;

import static org.junit.Assert.*;

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
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.thoughtworks.selenium.DefaultSelenium;

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

/**
 * 
 * Test cases for the View Employee use case.
 * 
 * @author Merisha Shim
 * 
 */
@RunWith(Arquillian.class)
public class ViewEmployeesTest {

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
	// @Drone WebDriver browser; // Webdriver 2.0, potential Selenium upgrade

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
	 * 
	 * Login as HR and click “View Employees” link.
	 * Should be able to view “View Employees” page.
	 * @author Merisha Shim
	 */

	@Test
	public final void viewEmployeesWithHRTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		TestSteps.loginAs("jsmith", "joe", selenium);

		assertTrue(selenium.isElementPresent("//a[@id='navForm:urlViewEmp']"));
	}

	/**
	 * Login as Employee and click “View Employees” link.
     * Should not be able to go to the page. The link should not even show up.
	 * @author Merisha Shim
	 */

	@Test
	public final void viewEmployeesWithEmployeeTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		
		TestSteps.loginAs("sSmith", "joe", selenium);
		assertFalse(selenium.isElementPresent("//a[@id='navForm:urlViewEmp']"));

	}

	/**
	 * Login as Supervisor and click “View Supervisor”
     *         link. Should not be able to go to the page. The link should not
     *         even show up.
     *         
	 * @author Merisha Shim 
	 */

	@Test
	public final void viewEmployeesWithSupervisorTest() {
		
		selenium.open(deploymentUrl + "login.xhtml");
		
		//login as Supervisor
		TestSteps.loginAs("jsmith", "joe", selenium);

		//create a new employee
		selenium.click("//a[@id='navForm:urlCreateEmp']");
		selenium.waitForPageToLoad("30000");
		
		selenium.type("id=createEmp:empFirstName", "Merisha");
		selenium.type("id=createEmp:empLastName", "Shim");
		selenium.type("id=createEmp:empUserName", "mmmshim");
		selenium.type("id=createEmp:empPassword", "mmmshim");
		selenium.type("id=createEmp:empPasswordMatch", "mmmshim");
		//selenium.click("//div[@id='createEmp:empSuperVisor']/div[2]/span");
		//There are no supervisors available to choose from
		//selenium.click("//div[@id='createEmp:empSuperVisor_panel']/div/ul/li[2]");
		
		//selenium.click("//div[@id='createEmp:empTimeSheetApprover']/div[2]/span");
		//There are no timesheet approvers available to choose from
		//selenium.click("//div[@id='createEmp:empTimeSheetApprover_panel']/div/ul/li[2]");
		
		//selenium.click("//div[@id='createEmp:empPLevel']/div[2]/span");
		//There are no paylevels available to choose from
		//selenium.click("//div[@id='createEmp:empPLevel_panel']/div/ul/li[4]");
		
		selenium.click("id=createEmp:empRole:2");
		selenium.click("//table[@id='createEmp:empRole']/tbody/tr/td[5]/div/div[2]");
		selenium.click("id=createEmp:btnSaveEmp");
		selenium.click("//a[@id='navForm:btnLogout']");
		//selenium.waitForPageToLoad("30000");

        assertFalse(selenium.isElementPresent("//a[@id='navForm:urlViewEmp']"));
	} 
	
	/**
     * Edit an employee and select their role as “not active”. Logout and login into that user.
     * There should be an error stating that it is wrong username/password.
	 * @author Merisha Shim
	 *			
	 * TODO Critical Cannot edit employee
	 */
	@Test
	public final void editEmployeeToNonActiveTest() {
		
		//login as HR
		selenium.open(deploymentUrl + "login.xhtml");
		TestSteps.loginAs("jsmith", "joe", selenium);
		
		
		selenium.click("//a[@id='navForm:urlViewEmp']");
		//selenium.waitForPageToLoad("30000");
		
		//View Details on Joe Blow
		selenium.click("//tr[td/div/label[text()='Joe Blow']]/td/div/button[@title='View Detail']");
		selenium.click("id=viewEmployees:btnEditWP");
		selenium.click("id=viewEmployees:empRole:1");
		selenium.click("//table[@id='viewEmployees:empRole']/tbody/tr/td[3]/div/div[2]");
		//NEW BUG CANNOT EDIT EMPLOYEES
		selenium.click("id=viewEmployees:btnSaveWP");
		selenium.click("css=span.ui-icon.ui-icon-closethick");
		
		selenium.click("//a[@id='navForm:btnLogout']");

		selenium.type("id=login_form:username", "user");
		selenium.type("id=login_form:password", "joe");
		selenium.click("id=login_form:login_button");
		
		//check to see if validation shows 
		Assert.assertTrue(selenium
				.isTextPresent("Incorrect Username/Password"));
	}
	
	/**
	 * Edit an employee and select their role as “active”. Logout and log into that user.
     * Should be able to log into that account with only employee privileges 
     * 
	 * @author Merisha Shim
	 *  ===EDIT EMPLOYEE PASSWORD BUG===
	 *
	 */
	@Test
	public final void editEmployeeFromNonActiveToActiveTest() {
		//login as HR
		selenium.open(deploymentUrl + "login.xhtml");
		TestSteps.loginAs("jsmith", "joe", selenium);
						
		selenium.click("//a[@id='navForm:urlCreateEmp']");
		//selenium.waitForPageToLoad("30000");
		selenium.type("id=createEmp:empFirstName", "randy");
		selenium.type("id=createEmp:empLastName", "smith");
		selenium.type("id=createEmp:empUserName", "randy");
		selenium.type("id=createEmp:empPassword", "C12345!1c");
		selenium.type("id=createEmp:empPasswordMatch", "C12345!1c");
		
		selenium.click("//div[@id='createEmp:empSuperVisor']");
		selenium.click("//div[@id='createEmp:empSuperVisor_panel']/div/ul/li[2]");
		
		selenium.click("//div[@id='createEmp:empTimeSheetApprover']");
		selenium.click("//div[@id='createEmp:empTimeSheetApprover_panel']/div/ul/li[2]");
		
		//TODO No paylevels available?
		//selenium.click("id=createEmp:empPLevel_label");
		//selenium.click("//div[@id='createEmp:empPLevel_panel']/div/ul/li[2]");
		
		//Set Role Inactive
		selenium.click("id=createEmp:empRole:1");
		//selenium.click("//table[@id='createEmp:empRole']/tbody/tr/td[3]/div/div[2]");
		
		
		selenium.click("id=createEmp:btnSaveEmp");
		selenium.click("//a[@id='navForm:urlViewEmp']");
		
		//View Details on Randy Smith
		selenium.click("//tr[td/div/label[text()='randy smith']]/td/div/button[@title='View Detail']");
		selenium.click("id=viewEmployees:btnEditWP");
		//Set Role to Active
		selenium.click("id=viewEmployees:empRole:0");
		//selenium.click("//table[@id='viewEmployees:empRole']/tbody/tr/td/div/div[2]");
		//MAY NOT SAVE AS PASSWORD IS REQUIRED BUG TODO
        selenium.type("id=viewEmployees:empPassword", "C12345!1c");
        selenium.type("id=viewEmployees:empPasswordMatch", "C12345!1c");
		selenium.click("id=viewEmployees:btnSaveWP");
		selenium.click("css=span.ui-icon.ui-icon-closethick");
		selenium.click("id=navForm:btnLogout");
		
		selenium.type("id=login_form:username", "randy");
		selenium.type("id=login_form:password", "C12345!1c");
		selenium.click("id=login_form:login_button");
        Assert.assertTrue("Should see Create Timesheet link", selenium
                .isElementPresent("xpath=//*[@id='navForm:urlCreateTime']"));
	}
	
	/**
     * Edit an employee and select their role as “HR”. Logout and log into that user.
     * Should be able to log into that account with HR privileges
	 * @author Merisha Shim
	 * 
	 * ===EDIT EMPLOYEE PASSWORD BUG===
	 */
	@Test
	public final void editEmployeeToHRTest() {
		//login as HR
		selenium.open(deploymentUrl + "login.xhtml");
		TestSteps.loginAs("jsmith", "joe", selenium);
		
		selenium.click("//a[@id='navForm:urlViewEmp']");
		//selenium.waitForPageToLoad("30000");
		
		selenium.click("//tr[td/div/label[text()='Joe Blow']]/td/div/button[@title='View Detail']");
		//selenium.click("id=viewEmployees:employeesTable:1:btnShowEmp");
		selenium.click("id=viewEmployees:btnEditWP");
		//Set to HR
		selenium.click("id=viewEmployees:empRole:3");
		//selenium.click("//table[@id='viewEmployees:empRole']/tbody/tr/td[7]/div/div[2]");
		//MAY NOT SAVE AS PASSWORD IS REQUIRED BUG TODO
	    selenium.type("id=viewEmployees:empPassword", "C12345!1c");
	    selenium.type("id=viewEmployees:empPasswordMatch", "C12345!1c");
		selenium.click("id=viewEmployees:btnSaveWP");
		//TODO BUG CRASHING ON EDIT SAVE
		selenium.click("css=span.ui-icon.ui-icon-closethick");
		selenium.click("//a[@id='navForm:btnLogout']");
		//selenium.waitForPageToLoad("30000");
		selenium.type("id=login_form:username", "supervisor");
		selenium.type("id=login_form:password", "C12345!1c");
		selenium.click("id=login_form:login_button");
		
        Assert.assertTrue("Should see Create Employee link", selenium
                .isElementPresent("xpath=//a[@id='navForm:urlCreateEmp']"));
        Assert.assertTrue("Should see Create Project link", selenium
                .isElementPresent("xpath=//a[@id='navForm:urlCreateProj']"));
        Assert.assertTrue("Should see Edit Paylevels link", selenium
                .isElementPresent("xpath=//a[@id='navForm:urlEditPayLevel']"));
        Assert.assertTrue("Should see View Projects link", selenium
                .isElementPresent("xpath=//a[@id='navForm:urlViewProj']"));
        Assert.assertTrue("Should see View Employees link", selenium
                .isElementPresent("xpath=//a[@id='navForm:urlViewEmp']"));
	
	}
	
	/**
     * Edit an employee and select their role as “Supervisor”. Logout and log into that user.
     * Should be able to log into that account with Supervisor privileges.
	 * @author Merisha Shim
	 */

	@Test
	public final void editEmployeeToSupervisorTest() {
		//login as HR
		selenium.open(deploymentUrl + "login.xhtml");
		TestSteps.loginAs("jsmith", "joe", selenium);
		
		selenium.click("//a[@id='navForm:urlViewEmp']");
		//selenium.waitForPageToLoad("30000");
		selenium.click("//tr[td/div/label[text()='Joe Blow']]/td/div/button[@title='View Detail']");
		//selenium.click("id=viewEmployees:employeesTable:2:btnShowEmp");
		
		selenium.click("id=viewEmployees:btnEditWP");
		selenium.click("id=viewEmployees:empRole:2");
		selenium.click("//table[@id='viewEmployees:empRole']/tbody/tr/td[5]/div/div[2]");
		selenium.click("id=viewEmployees:btnSaveWP");
		selenium.click("css=span.ui-icon.ui-icon-closethick");
		selenium.click("//a[@id='navForm:btnLogout']");
		//selenium.waitForPageToLoad("30000");
		selenium.type("id=login_form:username", "user");
		selenium.type("id=login_form:password", "joe");
		selenium.click("id=login_form:login_button");
		
        Assert.assertTrue("Should see Assign Employees link", selenium
                .isElementPresent("xpath=//a[@id='navForm:urlAssignEmp']"));
        Assert.assertTrue("Should see View Projects link", selenium
                .isElementPresent("xpath=//a[@id='navForm:urlViewProj']"));
        Assert.assertTrue("Should see View Approve Timesheets link", selenium
                .isElementPresent("xpath=//a[@id='navForm:urlViewTSA']"));
        
	}
	
	/** 
     * Edit an employee’s first name with 20 or less characters and only alphabets.
     * Should be able to be edited into the database.
	 * @author Merisha Shim
	 * 
	 * Edit bug, failing to save change
	 */
	@Test
	public final void editEmployeeFirstNameTest() {
		//login as HR
		selenium.open(deploymentUrl + "login.xhtml");
		TestSteps.loginAs("jsmith", "joe", selenium);
		
		selenium.click("//a[@id='navForm:urlViewEmp']");
		//selenium.waitForPageToLoad("30000");
		selenium.click("//tr[td/div/label[text()='Joe Blow']]/td/div/button[@title='View Detail']");
		//selenium.click("id=viewEmployees:employeesTable:1:btnShowEmp");
		selenium.click("id=viewEmployees:btnEditWP");
		
		selenium.type("id=viewEmployees:j_idt27", "Andy");//first name field needs id
		selenium.click("id=viewEmployees:btnSaveWP");
		selenium.click("css=span.ui-icon.ui-icon-closethick");
		
		assertTrue(false);//assert name has changed here
		
	}	
	
	/**
	 * @author Merisha Shim
	 * 			Edit an employee’s last name with 20 or less characters and only alphabets.
	 *			Should be able to be edited into the database.
	 * Edit bug, failing to save change
	 */

	@Test
	public final void editEmployeeLastNameTest() {
		//login as HR
		selenium.open(deploymentUrl + "login.xhtml");
		TestSteps.loginAs("jsmith", "joe", selenium);
		
		selenium.click("//a[@id='navForm:urlViewEmp']");
		
		//Select view details of a specific employee
		//selenium.click("//tr[td/div/label[text()='Joe Blow']]/td/div/button[@title='View Detail']");
		selenium.click("id=viewEmployees:employeesTable:1:btnShowEmp");
		selenium.click("id=viewEmployees:btnEditWP");
		selenium.type("id=viewEmployees:j_idt29", "Koudlaii");//last name field needs id
		selenium.click("id=viewEmployees:btnSaveWP");
		selenium.click("css=span.ui-icon.ui-icon-closethick");
		
		assertTrue(false);//assert name has changed here
	}
	
	
	/**
	 * @author Merisha Shim
	 * 			Edit an employee’s first name with numbers
	 *			Should have an error message stating that the first name should not contain numbers OR that it should only contain alphabets?
	 * Edit bug, failing to save change
	 */

	@Test
	public final void editEmployeeWithNumericsFirstNameTest() {
		//login as HR
		selenium.open(deploymentUrl + "login.xhtml");
		TestSteps.loginAs("jsmith", "joe", selenium);
		
		selenium.open("/loginTest/pages/index.xhtml");
		selenium.type("id=login_form:username", "jsmith");
		selenium.type("id=login_form:password", "joe");
		selenium.click("id=login_form:login_button");
		//selenium.waitForPageToLoad("30000");
		selenium.click("//a[@id='navForm:urlViewEmp']");
		
		//Select view details of a specific employee
		//selenium.click("//tr[td/div/label[text()='Joe Blow']]/td/div/button[@title='View Detail']");
		selenium.click("id=viewEmployees:employeesTable:2:btnShowEmp");
		selenium.click("id=viewEmployees:btnEditWP");
		selenium.type("id=viewEmployees:j_idt27", "Joe333");//first name field needs id
		selenium.click("id=viewEmployees:btnSaveWP");

		assertTrue(false);//assert error message is present
	
	}
	
	/**
	 * @author Merisha Shim
	 * 
	 * 			Edit an employee’s first name with over 20 characters.
	 *			Should have an error message stating that the name should be 20 or less characters
	 *
	 * Edit bug, failing to save change
	 */

	@Test
	public final void editEmployeeWithOver20CharsFirstNameTest() {
		//login as HR
		selenium.open(deploymentUrl + "login.xhtml");
		TestSteps.loginAs("jsmith", "joe", selenium);
		
		selenium.click("//a[@id='navForm:urlViewEmp']");
		
		//Select view details of a specific employee
		//selenium.click("//tr[td/div/label[text()='Joe Blow']]/td/div/button[@title='View Detail']");
		selenium.click("id=viewEmployees:employeesTable:2:btnShowEmp");
		selenium.click("id=viewEmployees:btnEditWP");
		selenium.type("id=viewEmployees:j_idt27", "Joeertyuiopawertyuigfdd");//first name field needs id
		selenium.click("id=viewEmployees:btnSaveWP");

		assertTrue(false);//assert error message is present

	}
	/**
	 * @author Merisha Shim
	 * 			Edit an employee’s last name with numbers
	 *			Should have an error message stating that the last name should not contain numbers OR that it should only contain alphabets?
	 *
	 * Edit bug, failing to save change
	 */

	@Test
	public final void editEmployeeWithNumericLastNameTest() {
		//login as HR
		selenium.open(deploymentUrl + "login.xhtml");
		TestSteps.loginAs("jsmith", "joe", selenium);
		
		selenium.click("//a[@id='navForm:urlViewEmp']");
		
		//Select view details of a specific employee
		//selenium.click("//tr[td/div/label[text()='Joe Blow']]/td/div/button[@title='View Detail']");
		selenium.click("id=viewEmployees:employeesTable:2:btnShowEmp");
		selenium.click("id=viewEmployees:btnEditWP");
		selenium.type("id=viewEmployees:j_idt30", "Blow3243423");//last name field needs id
		selenium.click("id=viewEmployees:btnSaveWP");
		
		assertTrue(false);//assert error message is present


	}
	
	/**
	 * @author Merisha Shim
	 * 			Edit an employee’s last name with over 20 characters.
	 *			Should have an error message stating that the name should be less than 20 characters
	 * Edit bug, failing to save change
	 */

	@Test
	public final void editEmployeeWithOver20CharsLastNameTest() {

		//login as HR
		selenium.open(deploymentUrl + "login.xhtml");
		TestSteps.loginAs("jsmith", "joe", selenium);
		
		selenium.click("//a[@id='navForm:urlViewEmp']");
		
		//Select view details of a specific employee
		//selenium.click("//tr[td/div/label[text()='Joe Blow']]/td/div/button[@title='View Detail']");
		selenium.click("id=viewEmployees:employeesTable:2:btnShowEmp");
		selenium.click("id=viewEmployees:btnEditWP");
		selenium.type("id=viewEmployees:j_idt30", "Blowdasgdasdgasdgasdfasd");//last name field needs id
		selenium.click("id=viewEmployees:btnSaveWP");
		
		assertTrue(false);//assert error message is present

	}
	
	/**
	 * @author Merisha Shim
	 *			Edit an employee’s username with 20 or less characters.
	 *			Should be able to be edited into the database.
	 *
	 * Edit bug, failing to save change
	 */
	@Test
	public final void editEmployeeUsernameTest() {
		//login as HR
		selenium.open(deploymentUrl + "login.xhtml");
		TestSteps.loginAs("jsmith", "joe", selenium);

		selenium.click("//a[@id='navForm:urlViewEmp']");
		
		//Select view details of a specific employee
		//selenium.click("//tr[td/div/label[text()='Joe Blow']]/td/div/button[@title='View Detail']");
		selenium.click("id=viewEmployees:employeesTable:2:btnShowEmp");
		selenium.click("id=viewEmployees:btnEditWP");
		selenium.type("id=viewEmployees:j_idt32", "username");//username field needs id
		selenium.click("id=viewEmployees:employeesTable:0:btnShowEmp");
		selenium.click("css=span.ui-icon.ui-icon-closethick");
		selenium.click("id=viewEmployees:employeesTable:2:btnShowEmp");
		selenium.click("id=viewEmployees:btnEditWP");
		selenium.click("id=viewEmployees:btnSaveWP");
		selenium.click("css=span.ui-icon.ui-icon-closethick");
		selenium.click("id=viewEmployees:employeesTable:2:btnShowEmp");
		selenium.click("css=span.ui-icon.ui-icon-closethick");
		selenium.click("//a[@id='navForm:btnLogout']");
		//selenium.waitForPageToLoad("30000");
		selenium.type("id=login_form:username", "username");
		selenium.type("id=login_form:password", "joe");
		selenium.click("id=login_form:login_button");
		
		assertTrue(selenium.isElementPresent("//a[@id='navForm:btnLogout']"));//asserting successful login

	}
	/**
	 * @author Merisha Shim
	 * 			Edit an employee’s username with over 20 characters.
	 *			Should have an error message stating that the name should be less than 20 characters
	 *
	 * Edit bug, failing to save change
	 */
	@Test
	public final void editEmployeeWithOver20CharsUsernameTest() {
		//login as HR
		selenium.open(deploymentUrl + "login.xhtml");
		TestSteps.loginAs("jsmith", "joe", selenium);
		
		selenium.click("//a[@id='navForm:urlViewEmp']");
		
		//Select view details of a specific employee
		//selenium.click("//tr[td/div/label[text()='Joe Blow']]/td/div/button[@title='View Detail']");
		selenium.click("id=viewEmployees:employeesTable:2:btnShowEmp");
		selenium.click("id=viewEmployees:btnEditWP");
		selenium.type("id=viewEmployees:j_idt32", "userdfasdf23fsdfasdfasdf");//username field needs id
		selenium.click("id=viewEmployees:btnSaveWP");
		selenium.click("css=span.ui-icon.ui-icon-closethick");

		assertTrue(false);//assert error message is present
	}
}