package bcit.ca.infosys.KeyboardCowboys.test.ui.project;

import static org.junit.Assert.*;

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
 * Test cases for the Create Time Sheet use case.
 * 
 * @author Steven Smith
 * 
 */
@RunWith(Arquillian.class)
@RunAsClient
public class CreateWorkPackageTest {

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
	 * @author Steven Smith
	 * 
	 *         Login as HR and click "Create Work Package" link. Should not be
	 *         able to go to the page. The link should not even show up.
	 */
	@Test
	public final void createWPLinkWithHRTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		TestSteps.loginAs("jsmith", "joe", selenium);
		
        assertFalse("Should not see Create Workpackage link", selenium
                .isElementPresent("xpath=//a[@id='navForm:urlCreateWP']"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Login as Employee and click “Create Work Package” link. Should
	 *         not be able to go to the page. The link should not even show up.
	 */
	@Test
	public final void createWPLinkWithEmployeeTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		TestSteps.loginAs("user", "joe", selenium);

        assertFalse("Should not see Create Workpackage link", selenium
                .isElementPresent("xpath=//a[@id='navForm:urlCreateWP']"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Login as Supervisor and click “Create Work Package” link. Should
	 *         not be able to view “Create Work Package” page.
	 */
	@Test
	public final void createWPLinkWithSupervisorTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		TestSteps.loginAs("supervisor", "joe", selenium);
		
        assertFalse("Should not see Create Workpackage link", selenium
                .isElementPresent("xpath=//a[@id='navForm:urlCreateWP']"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Select a Project (if there is none, create one from HR) and leave
	 *         “Parent Work Package” null. Should not show an error as you are
	 *         allowed to not have a parent work package. Should be added to the
	 *         database if all other values are correct.
	 */
	@Test
	public final void createWPWithNullValueForParentWPTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login HR
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create Project
		selenium.click("//a[@id='navForm:urlCreateProj']");
		//selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "999");
		selenium.type("id=formCreateProj:projName", "Sample Project");
		selenium.type("id=formCreateProj:projCostEstimate", "550");
		selenium.click("id=formCreateProj:projStartDate_input");
		selenium.click("link=3");
		selenium.click("id=formCreateProj:projEndDate_input");
		selenium.click("link=4");
		selenium.type("id=formCreateProj:projDescription", "Description");
		selenium.click("id=formCreateProj:btnSaveProj");

		// Logout
		selenium.click("//a[@id='navForm:btnLogout']");

		// Login Supervisor
		TestSteps.loginAs("supervisor", "joe", selenium);

		// Assign employee
		selenium.click("//a[@id='navForm:urlAssignEmp']");
		//selenium.waitForPageToLoad("30000");
		selenium.click("id=formAssignEmp:aetpEmployee_label");
		selenium.click("//div[@id='formAssignEmp:aetpEmployee_panel']/div/ul/li[2]");
		selenium.click("//div[@id='formAssignEmp:aetpProj']/div[2]/span");
		selenium.click("//div[@id='formAssignEmp:aetpProj_panel']/div/ul/li[2]");
		selenium.click("id=formAssignEmp:btnSaveEmp");

		// Logout
		selenium.click("//a[@id='navForm:btnLogout']");

		// Login as HR, make manager
		TestSteps.loginAs("jsmith", "joe", selenium);
		selenium.click("//a[@id='navForm:urlViewProj']");
		//selenium.waitForPageToLoad("30000");
		selenium.click("//tbody[@id='viewProjects:projTable_data']/tr/td[2]/div/span");
		selenium.click("id=viewProjects:projTable:0:btnShowProj");
		selenium.click("id=viewProjects:btnEditProj");
		selenium.click("//div[@id='viewProjects:projManager']/div[2]/span");
		selenium.click("//div[@id='viewProjects:projManager_panel']/div/ul/li[2]");
		selenium.click("id=viewProjects:btnUpdateProj");
		selenium.click("css=span.ui-icon.ui-icon-closethick");

		// Logout
		selenium.click("//a[@id='navForm:btnLogout']");

		// Login as user (manager)
		TestSteps.loginAs("user", "joe", selenium);

		// Create WP
		selenium.click("//a[@id='navForm:urlCreateWP']");
		//selenium.waitForPageToLoad("30000");
		selenium.click("//div[@id='formCreateWP:wpProj']/div[2]/span");
		selenium.click("//div[@id='formCreateWP:wpProj_panel']/div/ul/li[2]");
		selenium.click("id=formCreateWP:wpWorkPackages_label");
		selenium.type("id=formCreateWP:wpID", "543");
		selenium.type("id=formCreateWP:wpName", "No Parent");
		selenium.click("id=formCreateWP:wpStartDate_input");
		selenium.click("link=3");
		selenium.click("id=formCreateWP:wpEndDate_input");
		selenium.click("link=5");
		selenium.dragAndDropToObject("id=formCreateWP:allEmployees:0:pnl",
				"id=formCreateWP:dropArea");
		selenium.click("id=formCreateWP:btnSaveWP");
		assertTrue(selenium.isTextPresent("Successfully created Work Package."));

	}

	/**
	 * @author Steven Smith
	 * 
	 *         Select a Project (if there is none, create one from HR) and leave
	 *         “Engineer” null. Error message should show up stating that
	 *         Engineer should be selected .
	 */
	@Test
	public final void createWPWithNullValueForEngineerTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login HR
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create Project
		selenium.click("//a[@id='navForm:urlCreateProj']");
		//selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "999");
		selenium.type("id=formCreateProj:projName", "Sample Project");
		selenium.type("id=formCreateProj:projCostEstimate", "550");
		selenium.click("id=formCreateProj:projStartDate_input");
		selenium.click("link=3");
		selenium.click("id=formCreateProj:projEndDate_input");
		selenium.click("link=4");
		selenium.type("id=formCreateProj:projDescription", "Description");
		selenium.click("id=formCreateProj:btnSaveProj");

		// Logout
		selenium.click("//a[@id='navForm:btnLogout']");

		// Login Supervisor
		TestSteps.loginAs("supervisor", "joe", selenium);

		// Assign employee
		selenium.click("//a[@id='navForm:urlAssignEmp']");
		//selenium.waitForPageToLoad("30000");
		selenium.click("id=formAssignEmp:aetpEmployee_label");
		selenium.click("//div[@id='formAssignEmp:aetpEmployee_panel']/div/ul/li[2]");
		selenium.click("//div[@id='formAssignEmp:aetpProj']/div[2]/span");
		selenium.click("//div[@id='formAssignEmp:aetpProj_panel']/div/ul/li[2]");
		selenium.click("id=formAssignEmp:btnSaveEmp");

		// Logout
		selenium.click("//a[@id='navForm:btnLogout']");

		// Login as HR, make manager
		TestSteps.loginAs("jsmith", "joe", selenium);
		selenium.click("//a[@id='navForm:urlViewProj']");
		//selenium.waitForPageToLoad("30000");
		selenium.click("//tbody[@id='viewProjects:projTable_data']/tr/td[2]/div/span");
		selenium.click("id=viewProjects:projTable:0:btnShowProj");
		selenium.click("id=viewProjects:btnEditProj");
		selenium.click("//div[@id='viewProjects:projManager']/div[2]/span");
		selenium.click("//div[@id='viewProjects:projManager_panel']/div/ul/li[2]");
		selenium.click("id=viewProjects:btnUpdateProj");
		selenium.click("css=span.ui-icon.ui-icon-closethick");

		// Logout
		selenium.click("//a[@id='navForm:btnLogout']");

		// Login as user (manager)
		TestSteps.loginAs("user", "joe", selenium);

		// Create WP
		selenium.click("//a[@id='navForm:urlCreateWP']");
		//selenium.waitForPageToLoad("30000");
		selenium.click("//div[@id='formCreateWP:wpProj']/div[2]/span");
		selenium.click("//div[@id='formCreateWP:wpProj_panel']/div/ul/li[2]");
		selenium.click("id=formCreateWP:wpWorkPackages_label");
		selenium.type("id=formCreateWP:wpID", "543");
		selenium.type("id=formCreateWP:wpName", "No Engineer");
		selenium.click("id=formCreateWP:wpStartDate_input");
		selenium.click("link=3");
		selenium.click("id=formCreateWP:wpEndDate_input");
		selenium.click("link=5");
		selenium.dragAndDropToObject("id=formCreateWP:allEmployees:0:pnl",
				"id=formCreateWP:dropArea");
		selenium.click("id=formCreateWP:btnSaveWP");
		assertFalse(selenium
				.isTextPresent("Successfully created Work Package."));
		assertTrue(selenium.isTextPresent("select engineer"));
	}

	/**
	 * @author Steven Smith
	 * 
	 * 
	 *         Select a Project (if there is none, create one from HR) and leave
	 *         “WP ID” null. Error message should show up stating that WP ID
	 *         should not be null
	 */
	@Test
	public final void createWPWithNullValueForWPIDTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login HR
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create Project
		selenium.click("//a[@id='navForm:urlCreateProj']");
		//selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "999");
		selenium.type("id=formCreateProj:projName", "Sample Project");
		selenium.type("id=formCreateProj:projCostEstimate", "550");
		selenium.click("id=formCreateProj:projStartDate_input");
		selenium.click("link=3");
		selenium.click("id=formCreateProj:projEndDate_input");
		selenium.click("link=4");
		selenium.type("id=formCreateProj:projDescription", "Description");
		selenium.click("id=formCreateProj:btnSaveProj");

		// Logout
		selenium.click("//a[@id='navForm:btnLogout']");

		// Login Supervisor
		TestSteps.loginAs("supervisor", "joe", selenium);

		// Assign employee
		selenium.click("//a[@id='navForm:urlAssignEmp']");
		//selenium.waitForPageToLoad("30000");
		selenium.click("id=formAssignEmp:aetpEmployee_label");
		selenium.click("//div[@id='formAssignEmp:aetpEmployee_panel']/div/ul/li[2]");
		selenium.click("//div[@id='formAssignEmp:aetpProj']/div[2]/span");
		selenium.click("//div[@id='formAssignEmp:aetpProj_panel']/div/ul/li[2]");
		selenium.click("id=formAssignEmp:btnSaveEmp");

		// Logout
		selenium.click("//a[@id='navForm:btnLogout']");

		// Login as HR, make manager
		TestSteps.loginAs("jsmith", "joe", selenium);
		selenium.click("//a[@id='navForm:urlViewProj']");
		//selenium.waitForPageToLoad("30000");
		selenium.click("//tbody[@id='viewProjects:projTable_data']/tr/td[2]/div/span");
		selenium.click("id=viewProjects:projTable:0:btnShowProj");
		selenium.click("id=viewProjects:btnEditProj");
		selenium.click("//div[@id='viewProjects:projManager']/div[2]/span");
		selenium.click("//div[@id='viewProjects:projManager_panel']/div/ul/li[2]");
		selenium.click("id=viewProjects:btnUpdateProj");
		selenium.click("css=span.ui-icon.ui-icon-closethick");

		// Logout
		selenium.click("//a[@id='navForm:btnLogout']");

		// Login as user (manager)
		TestSteps.loginAs("user", "joe", selenium);

		// Create WP
		selenium.click("//a[@id='navForm:urlCreateWP']");
		//selenium.waitForPageToLoad("30000");
		selenium.click("//div[@id='formCreateWP:wpProj']/div[2]/span");
		selenium.click("//div[@id='formCreateWP:wpProj_panel']/div/ul/li[2]");
		selenium.click("id=formCreateWP:wpWorkPackages_label");
		selenium.type("id=formCreateWP:wpName", "No WP ID");
		selenium.click("id=formCreateWP:wpStartDate_input");
		selenium.click("link=3");
		selenium.click("id=formCreateWP:wpEndDate_input");
		selenium.click("link=5");
		selenium.dragAndDropToObject("id=formCreateWP:allEmployees:0:pnl",
				"id=formCreateWP:dropArea");
		selenium.click("id=formCreateWP:btnSaveWP");
		assertFalse(selenium
				.isTextPresent("Successfully created Work Package."));
		assertTrue(selenium.isTextPresent("regexpi:WP_ID cannot be empty"));
	}

	/**
	 * @author Steven Smith
	 * 
	 * 
	 *         Select a Project (if there is none, create one from HR) and
	 *         leave “WP Name” null. Error message should show up stating that
	 *         WP Name should not be null.
	 */
	@Test
	public final void createWPWithNullValueForNameTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		// Login HR
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create Project
		selenium.click("//a[@id='navForm:urlCreateProj']");
		//selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "999");
		selenium.type("id=formCreateProj:projName", "Sample Project");
		selenium.type("id=formCreateProj:projCostEstimate", "550");
		selenium.click("id=formCreateProj:projStartDate_input");
		selenium.click("link=3");
		selenium.click("id=formCreateProj:projEndDate_input");
		selenium.click("link=4");
		selenium.type("id=formCreateProj:projDescription", "Description");
		selenium.click("id=formCreateProj:btnSaveProj");

		// Logout
		selenium.click("//a[@id='navForm:btnLogout']");

		// Login Supervisor
		TestSteps.loginAs("supervisor", "joe", selenium);

		// Assign employee
		selenium.click("//a[@id='navForm:urlAssignEmp']");
		//selenium.waitForPageToLoad("30000");
		selenium.click("id=formAssignEmp:aetpEmployee_label");
		selenium.click("//div[@id='formAssignEmp:aetpEmployee_panel']/div/ul/li[2]");
		selenium.click("//div[@id='formAssignEmp:aetpProj']/div[2]/span");
		selenium.click("//div[@id='formAssignEmp:aetpProj_panel']/div/ul/li[2]");
		selenium.click("id=formAssignEmp:btnSaveEmp");

		// Logout
		selenium.click("//a[@id='navForm:btnLogout']");

		// Login as HR, make manager
		TestSteps.loginAs("jsmith", "joe", selenium);
		selenium.click("//a[@id='navForm:urlViewProj']");
		//selenium.waitForPageToLoad("30000");
		selenium.click("//tbody[@id='viewProjects:projTable_data']/tr/td[2]/div/span");
		selenium.click("id=viewProjects:projTable:0:btnShowProj");
		selenium.click("id=viewProjects:btnEditProj");
		selenium.click("//div[@id='viewProjects:projManager']/div[2]/span");
		selenium.click("//div[@id='viewProjects:projManager_panel']/div/ul/li[2]");
		selenium.click("id=viewProjects:btnUpdateProj");
		selenium.click("css=span.ui-icon.ui-icon-closethick");

		// Logout
		selenium.click("//a[@id='navForm:btnLogout']");

		// Login as user (manager)
		TestSteps.loginAs("user", "joe", selenium);

		// Create WP
		selenium.click("//a[@id='navForm:urlCreateWP']");
		//selenium.waitForPageToLoad("30000");
		selenium.click("//div[@id='formCreateWP:wpProj']/div[2]/span");
		selenium.click("//div[@id='formCreateWP:wpProj_panel']/div/ul/li[2]");
		selenium.click("id=formCreateWP:wpWorkPackages_label");
		selenium.type("id=formCreateWP:wpID", "51616");
		selenium.click("id=formCreateWP:wpStartDate_input");
		selenium.click("link=3");
		selenium.click("id=formCreateWP:wpEndDate_input");
		selenium.click("link=5");
		selenium.dragAndDropToObject("id=formCreateWP:allEmployees:0:pnl",
				"id=formCreateWP:dropArea");
		selenium.click("id=formCreateWP:btnSaveWP");
		assertFalse(selenium
				.isTextPresent("Successfully created Work Package."));
		assertTrue(selenium.isTextPresent("regexpi:WP_Name must be provided"));
	}
}