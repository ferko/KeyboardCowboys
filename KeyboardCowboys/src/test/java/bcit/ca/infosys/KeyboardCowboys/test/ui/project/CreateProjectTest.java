package bcit.ca.infosys.KeyboardCowboys.test.ui.project;

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
public class CreateProjectTest {

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

	/** View Create Project page. */
	@Test
	public final void viewCreateProjectPage() {
		selenium.open(deploymentUrl + "login.xhtml");

		TestSteps.loginAs("jsmith", "joe", selenium);
		
		// Create Project Link
		selenium.click("id=navForm:urlCreateProj");
		
		Assert.assertTrue(
				"Should be on create Project Page",
				selenium.isElementPresent("xpath=//*[@id='formCreateProj:btnSaveProj']"));
	}

	/**
	 * ===Failing===
	 */
	@Test
	public final void createNewProject() {
		selenium.open(deploymentUrl + "login.xhtml");

		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create Project Link
		selenium.click("id=navForm:urlCreateProj");
		selenium.waitForPageToLoad("30000");

		TestSteps.createNewProject("987654", "Test Project", "8000",
				"23/02/13", "27/02/13", "This is a test Project", selenium);

		// Check if project was saved
		Assert.assertTrue("Project should have been created",
				selenium.isElementPresent("xpath=//*[@class='messages' and "
						+ "//*[contains(text(), 'Successfully created')]]"));

		// Confirm project details are correct.

		// Click Link for View Projects
		// ===View Project, not available to HR=== Must be Manager of projects
		selenium.click("id=navForm:urlViewProj");
		selenium.waitForPageToLoad("30000");

		Assert.assertTrue("Project ID correct", selenium
				.isElementPresent("xpath=//div[contains(text(), '987654')]"));
		Assert.assertTrue(
				"Project Name not correct",
				selenium.isElementPresent("xpath=//div[contains(text(), 'Test Project')]"));
		Assert.assertTrue(
				"Project End Date not correct",
				// Actual Value =? 0013-02-27 00:00:00.0
				selenium.isElementPresent("xpath=//div[contains(text(), '2013-02-27 00:00:00.0')]"));
		Assert.assertTrue(
				"Project Start Date not correct",
				// Actual Value =? 0013-02-23 00:00:00.0
				selenium.isElementPresent("xpath=//div[contains(text(), '2013-02-23 00:00:00.0')]"));

		// Expand that particular project (click on the 'Expanding' span tag
		// within the row that contains the project id.)
		// selenium.click("//tbody/tr[td/div[contains(text(), '987654321')]/td[2]/div/span");
		// selenium.click("//tbody/tr[td/div[contains(text(), '987654321')]/td/div/span[@class='ui-row-toggler ui-icon ui-icon-circle-triangle-e']");
		// selenium.click("//tbody[@id='j_idt21:projTable_data']/tr[last()]/td/div/span");
		selenium.click("//tbody[@id='viewProjects:projTable_data']/tr[last()]/td[2]/div/span");

		Assert.assertTrue("Project Cost Estimate", selenium
				.isElementPresent("xpath=//div[contains(text(), '8000.0')]"));
		Assert.assertTrue(
				"Project Description correct",
				selenium.isElementPresent("xpath=//div[contains(text(), 'This is a test Project')]"));
		// selenium.open(deploymentUrl + "login.xhtml");
		// selenium.type("id=login_form:firstname", "newuser");
		// selenium.type("id=login_form:password", "joe");
		// selenium.click("id=login_form:login_button");
		// selenium.waitForPageToLoad("30000");
		// // Checks to see if user is not logged in
		// Assert.assertFalse("Username should not be logged in!", selenium
		// .isElementPresent("xpath=//p[contains(text(), 'Welcome to')]"));
		// selenium.type("id=login_form:firstname", "jsmith");
		// selenium.type("id=login_form:password", "joe");
		// selenium.click("id=login_form:login_button");
		// selenium.waitForPageToLoad("30000");
		// // Checks to see if user is logged in
		// Assert.assertTrue("Username should be logged in!", selenium
		// .isElementPresent("xpath=//p[contains(text(), 'Welcome to')]"));
		// selenium.click("//a[@id='j_idt9:j_idt14']/span[2]");
		// selenium.waitForPageToLoad("30000");
		// selenium.type("id=j_idt22:empName", "new user");
		// selenium.type("id=j_idt22:empUserName", "newuser");
		// selenium.click("id=j_idt22:empRole:0");
		// selenium.type("id=j_idt22:empPassword", "joe");
		// selenium.type("id=j_idt22:empPasswordMatch", "joe");
		// selenium.click("id=j_idt22:j_idt33");
		//
		// // Checks to see if user is created
		// Assert.assertTrue(
		// "Username should be created!",
		// selenium.isElementPresent("xpath=//span[contains(text(), 'Employee added successfully')]"));
		// selenium.click("//a[@id='j_idt9:j_idt20']/span[2]");

	}

	/**
	 * @author Steven Smith
	 * 
	 *         Login as HR and click "Create Project" link. Should be able to
	 *         view "Create Project" page.
	 */
	@Test
	public final void createProjectLinkWithHRTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Go to Create Project page
		selenium.click("//a[@id='navForm:urlCreateProj']/span[2]");
		selenium.waitForPageToLoad("30000");
		Assert.assertTrue(selenium.isTextPresent("Create Project"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Login as Employee and click "Create Project" link. Should not be
	 *         able to go to the page. The link should not even show up.
	 */
	@Test
	public final void createProjectLinkWithEmployeeTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login as Active Employee
		TestSteps.loginAs("sSmith", "joe", selenium);

		// Check that Create Project link is not visible
		Assert.assertFalse(selenium
				.isElementPresent("//a[@id='navForm:urlCreateProj']"));

		// Check that Create Project page is not accessible
		selenium.open("/KeyboardCowboys/pages/createProject.xhtml");
		Assert.assertFalse(selenium.isTextPresent("Create Project"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Login as Supervisor and click "Create Project" link. Should not
	 *         be able to go to the page. The page should not even show up.
	 */
	@Test
	public final void createProjectWithSupervisorTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create Supervisor and login
		selenium.click("//a[@id='navForm:urlCreateEmp']");
		selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("Lenny", "Lenard", "llen", "joe", "joe", 2,
				null, null, selenium);
		selenium.click("//a[@id='navForm:btnLogout']");
		selenium.waitForPageToLoad("30000");
		TestSteps.loginAs("llen", "joe", selenium);

		// Check that Create Project link is not visible
		Assert.assertFalse(selenium
				.isElementPresent("//a[@id='navForm:urlCreateProj']/span[2]"));

		// Check that Create Project page is not accessible
		selenium.open("/KeyboardCowboys/pages/createProject.xhtml");
		Assert.assertFalse(selenium.isTextPresent("Create Project"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Login as HR and create a Project with all valid data. Project ID
	 *         = only numeric characters. Should be created and added into the
	 *         database.
	 */
	@Test
	public final void createProjectWithValidDataTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create Project
		selenium.click("//a[@id='navForm:urlCreateProj']/span[2]");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "1049");
		selenium.type("id=formCreateProj:projName", "Valid Data Project");
		selenium.type("id=formCreateProj:projCostEstimate", "50");
		selenium.type("id=formCreateProj:projStartDate_input", "3/7/13");
		selenium.type("id=formCreateProj:projEndDate_input", "3/20/13");

		selenium.type("id=formCreateProj:projDescription",
				"Description of project.");
		selenium.click("id=formCreateProj:btnSaveProj");

		Assert.assertTrue(selenium
				.isTextPresent("Successfully created project."));

	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create a project with nothing in the Project ID. Error message
	 *         should show up stating that the Project ID should not be emtpy.
	 * 
	 *         --- The validation message is displayed twice. --
	 */
	@Test
	public final void createProjectWithNullProjectIDTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create Project
		selenium.click("//a[@id='navForm:urlCreateProj']/span[2]");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "");
		selenium.type("id=formCreateProj:projName", "Null ID Project");
		selenium.type("id=formCreateProj:projCostEstimate", "50");
		selenium.type("id=formCreateProj:projStartDate_input", "3/7/13");
		selenium.type("id=formCreateProj:projEndDate_input", "3/20/13");

		selenium.type("id=formCreateProj:projDescription",
				"Description of project.");
		selenium.click("id=formCreateProj:btnSaveProj");

		Assert.assertTrue(selenium
				.isTextPresent("Project ID: Validation Error: Value is required."));

	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create a project with the Project ID containing alphabets and
	 *         numbers.
	 * 
	 *         --- This test fails. ---
	 */
	@Test
	public final void createProjectWithProjectIDWithAlphaNumericTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		selenium.click("//a[@id='navForm:urlCreateProj']/span[2]");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "Alpha 33");
		selenium.type("id=formCreateProj:projName", "Alpha Number Project");
		selenium.type("id=formCreateProj:projCostEstimate", "50");
		selenium.type("id=formCreateProj:projStartDate_input", "3/7/13");
		selenium.type("id=formCreateProj:projEndDate_input", "3/20/13");

		selenium.type("id=formCreateProj:projDescription",
				"Description of project.");
		selenium.click("id=formCreateProj:btnSaveProj");

		Assert.assertTrue(selenium.isTextPresent("Project ID must be between three to six characters long and can only contains numbers"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create a project with the project ID containing only alpha
	 *         characters.
	 * 
	 *         --- This test fails. ---
	 */
	@Test
	public final void createProjectWithProjectIDOlnyAlphaTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		selenium.click("//a[@id='navForm:urlCreateProj']");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "AlphaOnly");
		selenium.type("id=formCreateProj:projName", "Alpha Only Project");
		selenium.type("id=formCreateProj:projCostEstimate", "50");
		selenium.type("id=formCreateProj:projStartDate_input", "3/7/13");
		selenium.type("id=formCreateProj:projEndDate_input", "3/20/13");

		selenium.type("id=formCreateProj:projDescription",
				"Description of project.");
		selenium.click("id=formCreateProj:btnSaveProj");

		Assert.assertTrue(selenium.isTextPresent("Project ID must be between three to six characters long and can only contains numbers"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create a project with the project ID containing more than 10
	 *         characters. Error message should show up stating that Project ID
	 *         should be 10 characters or less.
	 * 
	 *         --- This test fails. ---
	 */
	@Test
	public final void createProjectWithProjectIDMoreThan10CharsTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		selenium.click("//a[@id='navForm:urlCreateProj']/");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "123451234512345");
		selenium.type("id=formCreateProj:projName", "Too many numbers");
		selenium.type("id=formCreateProj:projCostEstimate", "50");
		selenium.type("id=formCreateProj:projStartDate_input", "3/7/13");
		selenium.type("id=formCreateProj:projEndDate_input", "3/20/13");

		selenium.type("id=formCreateProj:projDescription",
				"Description of project.");
		selenium.click("id=formCreateProj:btnSaveProj");

		Assert.assertTrue(selenium.isTextPresent("Project ID must be between three to six characters long and can only contains numbers"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create a project with a name that has alphabets and numbers.
	 *         Project should be created and added to the database.
	 * 
	 */
	@Test
	public final void createProjectWithProjectNameWithAlphaNumerics() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		selenium.click("//a[@id='navForm:urlCreateProj']");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "420");
		selenium.type("id=formCreateProj:projName", "Project With Numb3rs");
		selenium.type("id=formCreateProj:projCostEstimate", "50");
		selenium.type("id=formCreateProj:projStartDate_input", "3/7/13");
		selenium.type("id=formCreateProj:projEndDate_input", "3/20/13");
		selenium.type("id=formCreateProj:projDescription", "Project");
		selenium.click("id=formCreateProj:btnSaveProj");

		Assert.assertTrue(selenium.isTextPresent("Successfully created project"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create a project with a name that has more than 20 chars. Error
	 *         message should show up stating that Project Name should be 20
	 *         chars or less.
	 * 
	 */
	@Test
	public final void createProjectWithProjectNameWithMoreThan20CharTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		selenium.click("//a[@id='navForm:urlCreateProj']/span[2]");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "420");
		selenium.type("id=formCreateProj:projName",
				"ThisNameHasMoreThanTwentyChars");
		selenium.type("id=formCreateProj:projCostEstimate", "50");
		selenium.type("id=formCreateProj:projStartDate_input", "3/7/13");
		selenium.type("id=formCreateProj:projEndDate_input", "3/20/13");
		selenium.type("id=formCreateProj:projDescription", "Project");
		selenium.click("id=formCreateProj:btnSaveProj");

		Assert.assertFalse(selenium.isTextPresent("Project created"));
		Assert.assertTrue(selenium.isTextPresent("between three to twenty"));

	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create a project with a name that has only numbers. Error message
	 *         should show up stating that Project Name should be alpha numeric.
	 * 
	 */
	@Test
	@Ignore //Not a requirement?
	public final void createProjectWithProjectNameWithOnlyNumericsTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		selenium.click("//a[@id='navForm:urlCreateProj']/span[2]");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "420");
		selenium.type("id=formCreateProj:projName", "123123");
		selenium.type("id=formCreateProj:projCostEstimate", "50");
		selenium.type("id=formCreateProj:projStartDate_input", "3/7/13");
		selenium.type("id=formCreateProj:projEndDate_input", "3/20/13");
		selenium.type("id=formCreateProj:projDescription", "Project");
		selenium.click("id=formCreateProj:btnSaveProj");

		Assert.assertFalse(selenium.isTextPresent("Project created"));
		//Assert.assertTrue(selenium.isTextPresent("must be"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create a project with a and have the Cost Estimate be "0.0".
	 *         Error message should show up stating that the cost estimate must
	 *         be over 0.0
	 * 
	 */
	@Test
	public final void createProjectWithCostEstimateOfZeroTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		selenium.click("//a[@id='navForm:urlCreateProj']/span[2]");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "421");
		selenium.type("id=formCreateProj:projName", "Zero Cost");
		selenium.type("id=formCreateProj:projCostEstimate", "0.0");
		selenium.type("id=formCreateProj:projStartDate_input", "3/7/13");
		selenium.type("id=formCreateProj:projEndDate_input", "3/20/13");
		selenium.type("id=formCreateProj:projDescription", "Project");
		selenium.click("id=formCreateProj:btnSaveProj");

		Assert.assertFalse(selenium.isTextPresent("Project created"));
		//Assert.assertTrue(selenium.isTextPresent("must be"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create a project and have the Cost Estimate "100". Project should
	 *         be created and added to the database.
	 * 
	 */
	@Test
	public final void createProjectWithProjectWithCostEstimateWithNoDecimalsTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		selenium.click("//a[@id='navForm:urlCreateProj']");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "422");
		selenium.type("id=formCreateProj:projName", "No Decimal");
		selenium.type("id=formCreateProj:projCostEstimate", "100");
		selenium.type("id=formCreateProj:projStartDate_input", "3/7/13");
		selenium.type("id=formCreateProj:projEndDate_input", "3/20/13");
		selenium.type("id=formCreateProj:projDescription", "Project");
		selenium.click("id=formCreateProj:btnSaveProj");

		Assert.assertTrue(selenium.isTextPresent("Successfully created project"));
		//Assert.assertFalse(selenium.isTextPresent("must be"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create a project and have the Cost Estimate "100.00". Project
	 *         should be created and added to the database.
	 * 
	 */
	@Test
	public final void createProjectWithProjectWithCostEstimateWithDecimalsTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		selenium.click("//a[@id='navForm:urlCreateProj']/span[2]");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "423");
		selenium.type("id=formCreateProj:projName", "Decimal");
		selenium.type("id=formCreateProj:projCostEstimate", "100.00");
		selenium.type("id=formCreateProj:projStartDate_input", "3/7/13");
		selenium.type("id=formCreateProj:projEndDate_input", "3/20/13");
		selenium.type("id=formCreateProj:projDescription", "Project");
		selenium.click("id=formCreateProj:btnSaveProj");

		Assert.assertTrue(selenium.isTextPresent("Successfully created project"));
		//Assert.assertFalse(selenium.isTextPresent("must be"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create a project and have the Cost Estimate "100.0.0". Error
	 *         message should show up.
	 * 
	 */
	@Test
	public final void createProjectWithProjectWithCostEstimateWithWrongDecimalsTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		selenium.click("//a[@id='navForm:urlCreateProj']/span[2]");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "424");
		selenium.type("id=formCreateProj:projName", "Wrong Decimal");
		selenium.type("id=formCreateProj:projCostEstimate", "100.0.0");
		selenium.type("id=formCreateProj:projStartDate_input", "3/7/13");
		selenium.type("id=formCreateProj:projEndDate_input", "3/20/13");
		selenium.type("id=formCreateProj:projDescription", "Project");
		selenium.click("id=formCreateProj:btnSaveProj");

		Assert.assertFalse(selenium.isTextPresent("project created"));
		Assert.assertTrue(selenium.isTextPresent("must be"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create a project and have the Cost Estimate "-1.0". Error message
	 *         should show up.
	 * 
	 */
	@Test
	public final void createProjectWithProjectWithNegativeCostEstimateTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		selenium.click("//a[@id='navForm:urlCreateProj']/span[2]");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "425");
		selenium.type("id=formCreateProj:projName", "Neg Dec");
		selenium.type("id=formCreateProj:projCostEstimate", "-1.0");
		selenium.type("id=formCreateProj:projStartDate_input", "3/7/13");
		selenium.type("id=formCreateProj:projEndDate_input", "3/20/13");
		selenium.type("id=formCreateProj:projDescription", "Project");
		selenium.click("id=formCreateProj:btnSaveProj");

		Assert.assertFalse(selenium.isTextPresent("project created"));
		Assert.assertTrue(selenium.isTextPresent("Invalid"));
	}

	/**
	 * @author Steven Smith
	 *         Create a project and have the project date in the past. Error
	 *         message should show up.
	 * 
	 *         --- Fails ---
	 */
	@Test
	public final void createProjectWithPastStartDateTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		selenium.click("//a[@id='navForm:urlCreateProj']/span[2]");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "426");
		selenium.type("id=formCreateProj:projName", "Past Date");
		selenium.type("id=formCreateProj:projCostEstimate", "100");
		selenium.type("id=formCreateProj:projStartDate_input", "1/1/2013");
		selenium.type("id=formCreateProj:projEndDate_input", "3/20/2013");
		selenium.type("id=formCreateProj:projDescription", "Project");
		selenium.click("id=formCreateProj:btnSaveProj");

		Assert.assertFalse(selenium.isTextPresent("Successfully created project"));
		//Assert.assertTrue(selenium.isTextPresent("must be"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create a project and have the project end date in the past. Error
	 *         message should show up.
	 * 
	 *         --- Fails ---
	 * 
	 */
	@Test
	public final void createProjectWithPastEndDateTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		selenium.click("//a[@id='navForm:urlCreateProj']");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "427");
		selenium.type("id=formCreateProj:projName", "Past Date");
		selenium.type("id=formCreateProj:projCostEstimate", "100");
		selenium.type("id=formCreateProj:projStartDate_input", "1/1/2013");
		selenium.type("id=formCreateProj:projEndDate_input", "1/2/2013");
		selenium.type("id=formCreateProj:projDescription", "Project");
		selenium.click("id=formCreateProj:btnSaveProj");

		Assert.assertFalse(selenium.isTextPresent("Successfully created project"));
		//Assert.assertTrue(selenium.isTextPresent("must be"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create a project and have the project start date and end date the
	 *         same day. Error message should show up.
	 * 
	 * 
	 *         --- Fails ---
	 * 
	 */
	@Test
	public final void createProjectWithSameStartAndEndDateTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		selenium.click("//a[@id='navForm:urlCreateProj']/span[2]");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "428");
		selenium.type("id=formCreateProj:projName", "Same Date");
		selenium.type("id=formCreateProj:projCostEstimate", "100");
		selenium.type("id=formCreateProj:projStartDate_input", "5/5/2013");
		selenium.type("id=formCreateProj:projEndDate_input", "5/5/2013");
		selenium.type("id=formCreateProj:projDescription", "Project");
		selenium.click("id=formCreateProj:btnSaveProj");

		Assert.assertFalse(selenium.isTextPresent("Successfully created project"));
		//Assert.assertTrue(selenium.isTextPresent("must be"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create a project and have the start date later than the end date.
	 *         Error message should show up.
	 * 
	 *         --- Fails ---
	 * 
	 */
	@Test
	public final void createProjectWithEndDateSmallerThanStartDateTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		selenium.click("//a[@id='navForm:urlCreateProj']/span[2]");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "429");
		selenium.type("id=formCreateProj:projName", "Smaller Date");
		selenium.type("id=formCreateProj:projCostEstimate", "100");
		selenium.type("id=formCreateProj:projStartDate_input", "8/1/13");
		selenium.type("id=formCreateProj:projEndDate_input", "5/2/13");
		selenium.type("id=formCreateProj:projDescription", "Project");
		selenium.click("id=formCreateProj:btnSaveProj");

		Assert.assertFalse(selenium.isTextPresent("Successfully created project"));
		//Assert.assertTrue(selenium.isTextPresent("must be"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Create a project and have the project Description more than 255
	 *         chars. Error message should show up.
	 * 
	 */
	@Test
	public final void createProjectWithProjectDescriptionMoreThan255CharsTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login
		TestSteps.loginAs("jsmith", "joe", selenium);

		selenium.click("//a[@id='navForm:urlCreateProj']");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "430");
		selenium.type("id=formCreateProj:projName", "Long Desc");
		selenium.type("id=formCreateProj:projCostEstimate", "100");
		selenium.type("id=formCreateProj:projStartDate_input", "5/1/13");
		selenium.type("id=formCreateProj:projEndDate_input", "5/2/13");
		selenium.type(
				"id=formCreateProj:projDescription",
				"This project description has a lot of characters.  This project description has a lot of characters.  This project description has a lot of characters.  This project description has a lot of characters. This project description has a lot of characters dddddddd.");
		
		selenium.click("id=formCreateProj:btnSaveProj");

        Assert.assertFalse(selenium.isTextPresent("Successfully created project"));
        //Assert.assertTrue(selenium.isTextPresent("must be"));
	}
}