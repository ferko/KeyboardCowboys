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
 * Test cases for the Assign Employee use case.
 * 
 * @author Steven Smith
 * 
 */
@RunWith(Arquillian.class)
public class AssignEmployeeTest {

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
	 * @author Steven Smith
	 * 
	 *         Login as Supervisor and click "Assign Employees" link. Should be
	 *         able to view "Assign Employees" page.
	 */
	@Test
	public final void assignEmployeesLinkWithSupervisorTest() {
		selenium.open(deploymentUrl + "login.xhtml");

		// Login as Supervisor
		TestSteps.loginAs("supervisor", "joe", selenium);
		selenium.click("//a[@id='navForm:urlAssignEmp']");
		assertTrue(selenium.isTextPresent("Assign Employee To Project"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Login as HR and click "Assign Employees" link. Should not be able
	 *         to go to the page. The link should not even show up.
	 */
	@Test
	public final void assignEmployeesLinkWithHRTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		TestSteps.loginAs("jsmith", "joe", selenium);
		Assert.assertFalse(selenium.isTextPresent("Assign Employees"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Login as Employee and click "Assign Employees" link. Should not
	 *         be able to go to the page. The link should not even show up.
	 */
	@Test
	public final void assignEmployeesLinkWithEmployeeTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create Employee
		selenium.click("id=navForm:urlCreateEmp");
		//selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("Ken", "Masters", "ken", "joe", "joe", 0,
				null, null, selenium);

		// Logout
		selenium.click("//a[@id='navForm:btnLogout']");
		//selenium.waitForPageToLoad("30000");

		// Login as Supervisor
		TestSteps.loginAs("ken", "joe", selenium);
		Assert.assertFalse(selenium.isTextPresent("Assign Employees"));

	}

	/**
	 * @author Steven Smith
	 * 
	 *         Login as Supervisor and assign Employees to Work Packages. Should
	 *         be able created and added to the database. The employees added to
	 *         the work project/word package should show up in their main page
	 *         under "My Projects"
	 * 
	 */
	@Test
	public final void assignEmployeesWithValidDataTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		
		//Create new Project
		TestSteps.loginAs("jsmith", "joe", selenium);
        selenium.click("id=navForm:urlCreateProj");
        selenium.type("id=formCreateProj:projID", "111");
        selenium.type("id=formCreateProj:projName", "Manhattan");
        selenium.type("id=formCreateProj:projCostEstimate", "666");
        selenium.type("id=formCreateProj:projStartDate_input", "01/04/2013");
        selenium.type("id=formCreateProj:projEndDate_input", "30/04/2013");
        selenium.type("id=formCreateProj:projDescription", "Boom!");
        selenium.click("id=formCreateProj:btnSaveProj");
        selenium.click("//a[@id='navForm:btnLogout']");
        
		// Create Supervisor
        /*
		selenium.click("//a[@id='navForm:urlCreateEmp']");
		selenium.waitForPageToLoad("30000");
		TestSteps.createNewEmployee("Super", "Visor", "superv", "Joe123?",
				"Joe123?", 2, selenium);
		//Logout
		selenium.click("//a[@id='navForm:btnLogout']");
		*/

		// Login as Supervisor
		TestSteps.loginAs("supervisor", "joe", selenium);

		//Select Employee to assign
		selenium.click("//a[@id='navForm:urlAssignEmp']");
		selenium.click("//div[@id='formAssignEmp:aetpEmployee']");
		//selenium.click("//div[@id='formAssignEmp:aetpEmployee_panel']/div/ul/li[3]");
		selenium.click("//li[text()='Joe Blow']");
		
		//Assign to this project
		selenium.click("id=formAssignEmp:aetpProj_label");
		selenium.click("//li[text()='Manhattan 111']");
		
		selenium.click("id=formAssignEmp:btnSaveEmp");
		
		// Login as HR
		/*
	    selenium.click("//a[@id='navForm:btnLogout']");
	    TestSteps.loginAs("jsmith", "joe", selenium);
	    //View Project only available to users Managing Projects
	    //selenium.click("//a[@id='navForm:urlViewProj']");
	     selenium.click("//tbody[@id='viewProjects:projTable_data']/tr[9]/td[2]/div/span");
        assertEquals(
                "Marwan Marwan",
                selenium.getText("id=viewProjects:projTable:8:dtProj2:0:j_idt32:0:lblEmpFLName"));
        assertEquals(
                "Jitin Dhillon",
                selenium.getText("id=viewProjects:projTable:8:dtProj2:0:j_idt32:1:lblEmpFLName"));
		*/
		//Login as regular user, assigned to the project
		selenium.click("//a[@id='navForm:btnLogout']");
	    TestSteps.loginAs("user", "joe", selenium);
	    
	    //Project Member can see project under My Projects
	    assertTrue(selenium.isElementPresent("//*[@id='mnSideBar:projTable_data']/tr[td//text()[contains(., 'Manhattan')]]"));
	}

	/**
	 * @author Steven Smith
	 * 
	 *         Select an HR Employee under the "unassigned employees". Any HR
	 *         employee should not show up in the list.
	 *         
	 *         --- This test is failing ---
	 */
	@Test
	public final void assignEmployeesWithHRTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create HR
		selenium.click("//a[@id='navForm:urlCreateEmp']");
		//selenium.waitForPageToLoad("30000");

		TestSteps.createNewEmployee("Human", "Resources", "hrhr", "Joe123?",
				"Joe123?", 3, null, null, selenium);
		
		// Create Supervisor
		selenium.click("//a[@id='navForm:urlCreateEmp']");
		selenium.waitForPageToLoad("30000");

		TestSteps.createNewEmployee("Super", "Visor", "superv", "Joe123?",
				"Joe123?", 2, null, null, selenium);

		// Logout
		selenium.click("//a[@id='navForm:btnLogout']");
		selenium.waitForPageToLoad("30000");

		// Login as Supervisor
		TestSteps.loginAs("superv", "Joe123?", selenium);
		
		selenium.click("//a[@id='navForm:urlAssignEmp']");
		//selenium.waitForPageToLoad("30000");
		selenium.click("//div[@id='formAssignEmp:aetpEmployee']");
		
		assertFalse(selenium.isTextPresent("Human Resources"));

	}

	
	/**
	 * @author Steven Smith
	 * 
	 *         Select an Supervisor Employee under the "unassigned employees". Any Supervisor
	 *         employee should not show up in the list.
	 *         
	 *         --- This test is failing ---
	 */
	@Test
	public final void assignEmployeesWithSuperVisorTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create Supervisor
		selenium.click("//a[@id='navForm:urlCreateEmp']");
		//selenium.waitForPageToLoad("30000");

		TestSteps.createNewEmployee("Superman", "Visor", "svsv", "Joe123?",
				"Joe123?", 2, null, null, selenium);
		
		// Create Supervisor
		selenium.click("//a[@id='navForm:urlCreateEmp']");
		//selenium.waitForPageToLoad("30000");

		TestSteps.createNewEmployee("Super", "Visor", "superv", "Joe123?",
				"Joe123?", 2, null, null, selenium);

		// Logout
		selenium.click("//a[@id='navForm:btnLogout']");
		//selenium.waitForPageToLoad("30000");

		// Login as Supervisor
		TestSteps.loginAs("superv", "Joe123?", selenium);
		
		selenium.click("//a[@id='navForm:urlAssignEmp']");
		//selenium.waitForPageToLoad("30000");
		selenium.click("//div[@id='formAssignEmp:aetpEmployee']/div[2]/span");
		
		assertFalse(selenium.isTextPresent("Superman Visor"));

	}
}