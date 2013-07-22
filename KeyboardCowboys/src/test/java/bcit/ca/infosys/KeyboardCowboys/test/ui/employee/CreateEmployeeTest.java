package bcit.ca.infosys.KeyboardCowboys.test.ui.employee;

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
 * Test cases for the Create Employee use case.
 * 
 * @author Frank Berenyi, Merisha Shim, Vukasin Simic, Steven Smith
 * 
 */
@RunWith(Arquillian.class)
public class CreateEmployeeTest {
	/*
	 * TODO
	 * 
	 * -login go to create employee page create employees, logout, ensure they
	 * can login unless not active, and are viewable on employee list -try enter
	 * & save an existing employee (by username) assert the msg comes up -enter
	 * employee with non matching passwords assert msg is displayed -create
	 * employees testing the positions, HR, Active, Not active, Supervisor try
	 * logging in with non active user, fail! try logging in with active user
	 * try loggin in with supervisor try loggin in with HR -make sure employee
	 * can only be created by hr employee should be assigned an employee,
	 * validation msg should appear -employee firstname and last name should
	 * only contain character that would be in a name All input boxes should be
	 * max of 20 characters -Cannot create employee with empty fields only hr
	 * can view createEmployee page, and corresponding Actions link
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

	/** Create New Employee. */
	@Test
	public final void shouldCreateNewEmployee() {
		selenium.open(deploymentUrl + "login.xhtml");

		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create New Employee link
		selenium.click("id=navForm:urlCreateEmp");
		//selenium.waitForPageToLoad("30000");

		TestSteps.createNewEmployee("foobar", "barfoo", "foobar", "Pass4$", "Pass4$", 0,
				null, null, selenium);

		Assert.assertTrue("New user should be created!",
				selenium.isElementPresent(
				// or @id='createEmp:messages'
				"xpath=//*[@class='messages' and //*[contains(text(),"
						+ " 'Employee added successfully')]]"));
	}

	/** Attempt to create already existing employee. */
	@Test
	public final void createExistingEmployee() {
		selenium.open(deploymentUrl + "login.xhtml");

		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create New Employee link
		selenium.click("id=navForm:urlCreateEmp");
		//selenium.waitForPageToLoad("30000");

		TestSteps.createNewEmployee("john", "smith", "jsmith", "Joe!23", "Joe!23", 0,
				null, null, selenium);

		Assert.assertTrue("Already Exists error should be displayed!",
				selenium.isElementPresent("xpath=//*[@class='messages' and "
						+ "//*[contains(text(), 'Employee already exists in database')]]"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Login as HR and be able to go to the Create Employee Page.
	 */
	@Test
	public final void createEmployeeLinkwithHRTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login as HR
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Go to the Create Employee Page
		selenium.click("//a[@id='navForm:urlCreateEmp']");
		
		Assert.assertTrue("Create Employee Page not visible",
	            selenium.isElementPresent("xpath=//td[contains(text(), 'Create Employee')]"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Login as Active Employee and test whether the employee can go to
	 *         the Create Employee page. Should NOT have the Create Employee
	 *         link visible and should NOT be on the Create Employee page.
	 */
	@Test
	public final void createEmployeeLinkwithEmployeeTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login as Active Employee
		TestSteps.loginAs("jSmith", "joe", selenium);

		// Check that Create Employee link is not visible
		Assert.assertFalse(selenium
				.isElementPresent("//a[@id='navForm:urlCreateEmp']"));

		// Check that Create Employee page is not accessible
		selenium.open("/KeyboardCowboys/pages/createEmployee.xhtml");
		Assert.assertFalse(selenium.isTextPresent("Create Employee"));

	}

	/**
	 * @author Steven Smith
	 * 
	 *         Login as Supervisor and test whether the supervisor can go to the
	 *         Create Employee page. Should not have the Create Employee link
	 *         visible and should not be on the Create Employee page.
	 */
	@Test
	public final void createEmployeeLinkwithSupervisorTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create Supervisor
		selenium.click("//a[@id='navForm:urlCreateEmp']");
		selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("Robert", "Deniro", "robd", "Joe!23", "Joe!23", 2,
				null, null, selenium);

		// Logout and login as supervisor
		selenium.click("//a[@id='navForm:btnLogout']");
		//selenium.waitForPageToLoad("30000");
		TestSteps.loginAs("rd", "joe", selenium);

		// Check that Create Employee link is not visible
		Assert.assertFalse(selenium
				.isElementPresent("//a[@id='navForm:urlCreateEmp']"));

		// Check that Create Employee page is not accessible
		selenium.open("/KeyboardCowboys/pages/createEmployee.xhtml");
		Assert.assertFalse(selenium.isTextPresent("Create Employee"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create Employee with all null values. Error messages for all
	 *         values saying that they should not be null.
	 */
	@Test
	public final void createEmployeeWithNullValuesTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create New Employee with null values
		selenium.click("id=navForm:urlCreateEmp");
		//selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("", "", "", "", "", 1, null, null, selenium);

		// Check that validation errors are displayed
		Assert.assertTrue(selenium
				.isTextPresent("Employee First Name: Validation Error: Value is required."));
		Assert.assertTrue(selenium
		        .isTextPresent("Employee Last Name: Validation Error: Value is required."));
		Assert.assertTrue(selenium
				.isTextPresent("Employee User Name: Validation Error: Value is required."));
		Assert.assertTrue(selenium
				.isTextPresent("Password 1: Validation Error: Value is required."));
		Assert.assertTrue(selenium
				.isTextPresent("Password 2: Validation Error: Value is required."));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create an existing active Employee. Error message should show up
	 *         stating that the Employee already exists.
	 */
	@Test
	public final void createExistingActiveEmployeeTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create New Employee
		selenium.click("id=navForm:urlCreateEmp");
		//selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("Korean", "Alex", "kAlex", "Joe!23", "Joe!23", 1,
				null, null, selenium);
		
	      // Create New Employee that already exists
        selenium.click("id=navForm:urlCreateEmp");
        //selenium.waitForPageToLoad("30000");
        TestSteps.createNewEmployee("Korean", "Alex", "kAlex", "Joe!23", "Joe!23", 1,
        		null, null, selenium);

		Assert.assertTrue(selenium
				.isTextPresent("Employee already exists in database"));

	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create an existing active employee. Should create a regular
	 *         employee and the newly created employee should be able to login.
	 *         The newly created employee is sent to the main page.
	 */
	@Test
	public final void createAndLoginAsActiveEmployeeTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create New Employee
		selenium.click("id=navForm:urlCreateEmp");
		//selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("Cosmo", "Kramer", "kramer", "Joe!23", "Joe!23",
				0, null, null, selenium);

		// Logout and login as created user
		selenium.click("//a[@id='navForm:btnLogout']");
		selenium.waitForPageToLoad("30000");
		TestSteps.loginAs("kramer", "Joe!23", selenium);
		Assert.assertTrue("New user should be able to login.",
				selenium.isElementPresent("//a[@id='navForm:btnLogout']"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create an inactive employee and login as that inactive employee.
	 *         Error message should show up saying that
	 *         "Invalid Username / Password"
	 * 
	 *         TODO BUG---- When inactive user attempts to login, the error message does not show up?
	 */
	@Test
	public final void createAndLoginAsInactiveEmployeeTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create New Inactive Employee
		selenium.click("id=navForm:urlCreateEmp");
		//selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("George", "Costanza", "costanza", "Joe!23",
				"Joe!23", 1, null, null, selenium);

		// Logout and login as created user
		selenium.click("//a[@id='navForm:btnLogout']");
		//selenium.waitForPageToLoad("30000");
		TestSteps.loginAs("costanza", "Joe!23", selenium);
		Assert.assertTrue(selenium.isTextPresent("Invalid"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create an existing inactive employee. Should display an error
	 *         message saying that the Employee already exists.
	 */
	@Test
	public final void createExistingInactiveEmployeeTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create New Inactive Employee
		selenium.click("id=navForm:urlCreateEmp");
		selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("Bryant", "Reeves", "bryant", "Joe!23", "Joe!23", 1,
				null, null, selenium);

		// Create New the same Inactive Employee
		selenium.click("id=navForm:urlCreateEmp");
		selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("Bryant", "Reeves", "bryant", "Joe!23", "Joe!23", 1,
				null, null, selenium);

		Assert.assertTrue(selenium
				.isTextPresent("Employee already exists in database"));

	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create an employee with non-matching passwords. Error message
	 *         saying that passwords are mismatched should be displayed.
	 */
	@Test
	public final void createEmployeeWithNonMatchingPasswordsTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create New Inactive Employee
		selenium.click("id=navForm:urlCreateEmp");
		selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("Chris", "Chelios", "cc24", "Joe!23", "joE!23",
				1, null, null, selenium);

		Assert.assertTrue(selenium
				.isTextPresent("Passwords do not match!"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create Employee with a name that has alphabets and numbers.
	 *         Should display Error message saying that Name should only contain
	 *         alphabetic characters.
	 * 
	 *         --- This test is failing. ---
	 */
	@Test
	public final void createEmployeeWithAlphaNumbericNameTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create New Employee
		selenium.click("id=navForm:urlCreateEmp");
		selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("Chris", "007", "numberemployee", "Joe!23",
				"Joe!23", 1, null, null, selenium);

		Assert.assertTrue(selenium.isTextPresent("only contain alpha"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create Employee with only numbers. Error message should display
	 *         saying that Name should only contain alphabetic characters.
	 * 
	 */
	@Test
	public final void createEmployeeWithNumericNameTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create New Employee
		selenium.click("id=navForm:urlCreateEmp");
		selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("4four", "20Twenty", "numonly", "Joe!23", "Joe!23", 1,
				null, null, selenium);

		Assert.assertTrue(selenium.isTextPresent("only contain alpha"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create Employee with a name that has over 40 characters.
	 * 
	 */
	@Test
	public final void createEmployeeWithNameOver40CharsTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create New Employee
		selenium.click("id=navForm:urlCreateEmp");
		selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee(
				"This name has more than Forty characters in it.",
				"Names must have under Forty characters to be valid.", "toomany",
				"Joe!23", "Joe!23", 1, null, null, selenium);

		Assert.assertTrue(selenium.isTextPresent("Employee First Name must be between three"
		        +" to twenty characters long and can only contain alphabetic characters"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create an active supervisor and login as the newly created
	 *         supervisor. Once logged in, clear "Create Work Package" link.
	 *         
	 *         ---FAILING--- TODO critical, create new supervisor and use them to set up a new project fully.
	 */
	@Test
	public final void createAndLoginAsActiveSupervisorTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create New Supervisor
		selenium.click("id=navForm:urlCreateEmp");
		//selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("Gino", "Odjick", "ginooo", "Joe!23", "Joe!23",
				0, "Kent Clark", null, selenium);

		//Create Project
        selenium.click("id=navForm:urlCreateProj");
        selenium.type("id=formCreateProj:projID", "222");
        selenium.type("id=formCreateProj:projName", "Peace");
        selenium.type("id=formCreateProj:projCostEstimate", "777");
        selenium.type("id=formCreateProj:projStartDate_input", "01/04/2013");
        selenium.type("id=formCreateProj:projEndDate_input", "30/04/2013");
        selenium.type("id=formCreateProj:projDescription", "Yay!");
        selenium.click("id=formCreateProj:btnSaveProj");
        
        selenium.click("//a[@id='navForm:btnLogout']");
        TestSteps.loginAs("supervisor", "joe", selenium);
        
        //Assign employee to project
        selenium.click("//a[@id='navForm:urlAssignEmp']");
        //Select Employee to assign
        selenium.click("//div[@id='formAssignEmp:aetpEmployee']");
        //???
        selenium.click("//li[text()='Gino Odjick']");
        
        //Assign to this project
        selenium.click("id=formAssignEmp:aetpProj_label");
        selenium.click("//li[text()='Peace 222']");
        selenium.click("id=formAssignEmp:btnSaveEmp");
        
		// Login as new employee
		selenium.click("//a[@id='navForm:btnLogout']");
		TestSteps.loginAs("ginooo", "Joe!23", selenium);

		// Create WP
		//===CreateWP not available for ginooo???===
		selenium.click("//a[@id='navForm:urlCreateWP']");
		Assert.assertTrue(selenium.isTextPresent("WP Project:"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create an active HR and login as the new created HR. Once logged
	 *         in, click "Create Employee" link and "Create Project" link.
	 *         Should be able to login, go to create employee page, and create
	 *         project page.
	 */
	@Test
	public final void createAndLoginAsActiveHRTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create New Supervisor
		selenium.click("id=navForm:urlCreateEmp");
		selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("Homer", "Simpson", "hsimp", "Joe!23", "Joe!23",
				3, null, null, selenium);

		// Login as new employee
		selenium.click("//a[@id='navForm:btnLogout']");
		selenium.waitForPageToLoad("30000");
		TestSteps.loginAs("hsimp", "Joe!23", selenium);

		// Go to Create Employee page
		selenium.click("//a[@id='navForm:urlCreateEmp']");
		selenium.waitForPageToLoad("30000");
		Assert.assertTrue(selenium.isTextPresent("Create Employee"));

		// Go to Create Project page
		selenium.click("//a[@id='navForm:urlCreateProj']");
		selenium.waitForPageToLoad("30000");
		Assert.assertTrue(selenium.isTextPresent("Create Project"));

	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create Employee with a username that has over 15 characters.
	 *         Should display an error message saying that the username should
	 *         be less than 20 characters.
	 * 
	 */
	@Test
	public final void createEmployeeWithUsernameOver15CharsTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create New Employee
		selenium.click("id=navForm:urlCreateEmp");
		selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("Ned", "Flanders",
				"thisusernamehasmorethanfifteencharactersinit", "Joe!23", "Joe!23", 3,
				null, null, selenium);
		Assert.assertTrue(selenium.isTextPresent("Employee User Name must be three to"
				+" fifteen characters long and cannot contain special characters"));

	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create Employee with a Password that has over 20 chars. Should
	 *         display an error message that the password should be less than 20
	 *         characters.
	 * 
	 * 	FAILURE: TODO BUG, Gives Passwords do not match! message instead. May have something to do with the password fields not being required.s
	 */
	@Test
	public final void createEmployeeWithPasswordOver20CharsTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create New Employee
		selenium.click("id=navForm:urlCreateEmp");
		selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("Will", "Smith", "wsmith",
				"thispasswordistoolongtobevalidA!1",
				"thispasswordistoolongtobevalidA!1", 3, null, null, selenium);
		Assert.assertTrue(selenium.isTextPresent("Password must be 3 - 20 characters long"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create Employee with a first name that has alphabets and numbers.
	 *         Error message should show up stating that there should not be
	 *         numbers.
	 * 
	 */
	@Test
	public final void createEmployeeWithAlphaNumericFirstNameTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create New Employee
		selenium.click("id=navForm:urlCreateEmp");
		selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("Kent123", "Brockman", "kbrock", "Joe!23",
				"Joe!23", 3, null, null, selenium);
		Assert.assertTrue(selenium.isTextPresent("Employee First Name must be between three to"
				+ " twenty characters long and can only contain alphabetic characters"));
		Assert.assertFalse(selenium
				.isTextPresent("Employee Added Successfully"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create Employee with a first name that has only numbers. Error
	 *         message should show up stating that there should not be numbers.
	 * 
	 */
	@Test
	public final void createEmployeeWithNumericFirstNameTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create New Employee
		selenium.click("id=navForm:urlCreateEmp");
		selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("4201124", "Last Name", "user", "Joe!23",
				"Joe!23", 3, null, null, selenium);
		Assert.assertTrue(selenium.isTextPresent("Employee First Name must be between"
				+" three to twenty characters long and can only contain alphabetic characters"));
		Assert.assertFalse(selenium
				.isTextPresent("Employee Added Successfully"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create Employee with a first name that has more than 20
	 *         characters. Error message should show up stating that there
	 *         should be less than 20 characters.
	 * 
	 */
	@Test
	public final void createEmployeeWithFirstNameMoreThan20CharsTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create New Employee
		selenium.click("id=navForm:urlCreateEmp");
		selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("thisfirstnamehasmorethantwentychars",
				"Last Name", "user", "Joe!23", "Joe!23", 0, null, null, selenium);
		Assert.assertTrue(selenium.isTextPresent("Employee First Name must be between three"
				+" to twenty characters long and can only contain alphabetic characters"));
		Assert.assertFalse(selenium
				.isTextPresent("Employee Added Successfully"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create Employee with a last name that has alphabets and numbers.
	 *         Error message should show up stating that there not be numbers.
	 * 
	 */
	@Test
	public final void createEmployeeWithAlphaNumericLastNameTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create New Employee
		selenium.click("id=navForm:urlCreateEmp");
		selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("First Name", "Last234", "user", "Joe!23",
				"Joe!23", 3, null, null, selenium);
		Assert.assertTrue(selenium.isTextPresent("Employee Last Name must be between three"
				+" to twenty characters long and can only contain alphabetic characters"));
		Assert.assertFalse(selenium
				.isTextPresent("Employee Added Successfully"));
	}
	
	
	/**
	 * @author Steven Smith
	 * 
	 *         Create Employee with a last name that has only numbers.
	 *         Error message should show up stating that there not be numbers.
	 * 
	 */
	@Test
	public final void createEmployeeWithNumericLastNameTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create New Employee
		selenium.click("id=navForm:urlCreateEmp");
		selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("First Name", "3333", "user", "Joe!23",
				"Joe!23", 3, null, null, selenium);
		Assert.assertTrue(selenium.isTextPresent("Employee Last Name must be between three"
				+" to twenty characters long and can only contain alphabetic characters"));
		Assert.assertFalse(selenium
				.isTextPresent("Employee Added Successfully"));
	}
	
	
	/**
	 * @author Steven Smith
	 * 
	 *         Create Employee with a last name that has more than 20
	 *         characters. Error message should show up stating that there
	 *         should be less than 20 characters.
	 * 
	 */
	@Test
	public final void createEmployeeWithLastNameMoreThan20CharsTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create New Employee
		selenium.click("id=navForm:urlCreateEmp");
		selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("First Name",
				"thisislongerthantwentychars", "user", "Joe!23", "Joe!23", 0, null, null, selenium);
		Assert.assertTrue(selenium.isTextPresent("Employee Last Name must be between three"
		        + " to twenty characters long and can only contain alphabetic characters"));
		Assert.assertFalse(selenium
				.isTextPresent("Employee Added Successfully"));
	}
}
