package bcit.ca.infosys.KeyboardCowboys.test.ui;

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
public class Demo {

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

	@Test
	public final void demoTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		TestSteps.loginAs("jsmith", "joe", selenium);

		// Create Employees
		selenium.click("id=navForm:urlCreateEmp");
		selenium.waitForPageToLoad("30000");
        selenium.click("//div[@id='createEmp:empPLevel_panel']/div/ul/li[3]");
		TestSteps.createNewEmployee("AAA", "AAA", "AAA", "Pass123?",
				"Pass123?", 2, null, null, selenium);

		selenium.click("id=navForm:urlCreateEmp");
        selenium.click("//div[@id='createEmp:empPLevel_panel']/div/ul/li[2]");
		TestSteps.createNewEmployee("BBB", "BBB", "BBB", "Pass123?",
				"Pass123?", 0, "AAA AAA", "AAA AAA", selenium);

		selenium.click("id=navForm:urlCreateEmp");
        selenium.click("//div[@id='createEmp:empPLevel_panel']/div/ul/li[4]");
		TestSteps.createNewEmployee("CCC", "CCC", "CCC", "Pass123?",
				"Pass123?", 0, "AAA AAA", "AAA AAA", selenium);

		selenium.click("id=navForm:urlCreateEmp");
        selenium.click("//div[@id='createEmp:empPLevel_panel']/div/ul/li[3]");
		TestSteps.createNewEmployee("DDD", "DDD", "DDD", "Pass123?",
				"Pass123?", 0, "AAA AAA", "AAA AAA", selenium);

		selenium.click("id=navForm:urlCreateEmp");
        selenium.click("//div[@id='createEmp:empPLevel_panel']/div/ul/li[1]");
		TestSteps.createNewEmployee("EEE", "EEE", "EEE", "Pass123?",
				"Pass123?", 0, "AAA AAA", "AAA AAA", selenium);

		selenium.click("id=navForm:urlCreateEmp");
        selenium.click("//div[@id='createEmp:empPLevel_panel']/div/ul/li[3]");
		TestSteps.createNewEmployee("FFF", "FFF", "FFF", "Pass123?",
				"Pass123?", 0, "AAA AAA", "AAA AAA", selenium);

		// Create Project
		selenium.click("//a[@id='navForm:urlCreateProj']/span[2]");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=formCreateProj:projID", "1234");
		selenium.type("id=formCreateProj:projName", "Demo Project");
		selenium.type("id=formCreateProj:projCostEstimate", "1000");
		selenium.type("id=formCreateProj:projStartDate_input", "4/9/2013");
		selenium.type("id=formCreateProj:projEndDate_input", "7/20/2013");
		selenium.type("id=formCreateProj:projDescription",
				"Description of the project.");
		selenium.click("id=formCreateProj:btnSaveProj");

		// Logout
		selenium.click("//a[@id='navForm:btnLogout']");

		// Login as Supervisor

		TestSteps.loginAs("AAA", "Pass123?", selenium);

		// Assign Employees
		// selenium.open("/createProjectTest/pages/assignEmployeeToProject.xhtml");
		selenium.click("//a[@id='navForm:urlAssignEmp']/span[2]");
		selenium.waitForPageToLoad("30000");
		selenium.click("//div[@id='formAssignEmp:aetpEmployee_panel']/div/ul/li[2]");
		selenium.click("id=formAssignEmp:aetpProj_label");
		selenium.click("//div[@id='formAssignEmp:aetpProj_panel']/div/ul/li[2]");
		selenium.click("id=formAssignEmp:btnSaveEmp");

		selenium.click("//a[@id='navForm:urlAssignEmp']/span[2]");
		selenium.click("id=formAssignEmp:aetpEmployee_label");
		selenium.click("//div[@id='formAssignEmp:aetpEmployee_panel']/div/ul/li[3]");
		selenium.click("id=formAssignEmp:aetpProj_label");
		selenium.click("//div[@id='formAssignEmp:aetpProj_panel']/div/ul/li[2]");
		selenium.click("id=formAssignEmp:btnSaveEmp");

		selenium.click("//a[@id='navForm:urlAssignEmp']/span[2]");
		selenium.click("id=formAssignEmp:aetpEmployee_label");
		selenium.click("//div[@id='formAssignEmp:aetpEmployee_panel']/div/ul/li[4]");
		selenium.click("id=formAssignEmp:aetpProj_label");
		selenium.click("//div[@id='formAssignEmp:aetpProj_panel']/div/ul/li[2]");
		selenium.click("id=formAssignEmp:btnSaveEmp");

		selenium.click("//a[@id='navForm:urlAssignEmp']/span[2]");
		selenium.click("id=formAssignEmp:aetpEmployee_label");
		selenium.click("//div[@id='formAssignEmp:aetpEmployee_panel']/div/ul/li[5]");
		selenium.click("id=formAssignEmp:aetpProj_label");
		selenium.click("//div[@id='formAssignEmp:aetpProj_panel']/div/ul/li[2]");
		selenium.click("id=formAssignEmp:btnSaveEmp");

		selenium.click("//a[@id='navForm:urlAssignEmp']/span[2]");
		selenium.click("id=formAssignEmp:aetpEmployee_label");
		selenium.click("//div[@id='formAssignEmp:aetpEmployee_panel']/div/ul/li[6]");
		selenium.click("id=formAssignEmp:aetpProj_label");
		selenium.click("//div[@id='formAssignEmp:aetpProj_panel']/div/ul/li[2]");
		selenium.click("id=formAssignEmp:btnSaveEmp");

		// Assign Manager to Project
		selenium.click("//a[@id='navForm:urlViewProj']/span[2]");
		selenium.waitForPageToLoad("30000");
		selenium.click("//tbody[@id='viewProjects:projTable_data']/tr/td[2]/div/span");
		selenium.click("id=viewProjects:projTable:0:btnShowProj");
		selenium.click("id=viewProjects:btnEditProj");
		selenium.click("//div[@id='viewProjects:projManager']/div[2]");
		selenium.click("//div[@id='viewProjects:projManager_panel']/div/ul/li[2]");
		selenium.click("id=viewProjects:btnUpdateProj");
		selenium.click("css=span.ui-icon.ui-icon-closethick");

		// Logout
		selenium.click("//a[@id='navForm:btnLogout']");

		// Login as Manager

		TestSteps.loginAs("BBB", "Pass123?", selenium);

		// Create WP 1
		selenium.click("//a[@id='navForm:urlCreateWP']/span[2]");
		selenium.waitForPageToLoad("30000");
		selenium.click("//div[@id='formCreateWP:wpProj']/div[2]/span");
		selenium.click("//div[@id='formCreateWP:wpProj_panel']/div/ul/li[2]");
		selenium.type("id=formCreateWP:wpID", "10000");
		selenium.type("id=formCreateWP:wpName", "WP1");
		selenium.click("id=formCreateWP:wpStartDate_input");
		selenium.click("link=9");
		selenium.click("id=formCreateWP:wpEndDate_input");
		selenium.click("link=23");
		selenium.click("id=formCreateWP:btnSaveWP");

		// WP 2
		selenium.click("//a[@id='navForm:urlCreateWP']/span[2]");
		selenium.waitForPageToLoad("30000");
		selenium.click("//div[@id='formCreateWP:wpProj']/div[2]/span");
		selenium.click("//div[@id='formCreateWP:wpProj_panel']/div/ul/li[2]");
		selenium.type("id=formCreateWP:wpID", "20000");
		selenium.type("id=formCreateWP:wpName", "WP2");
		selenium.click("id=formCreateWP:wpStartDate_input");
		selenium.click("link=30");
		selenium.click("id=formCreateWP:wpEndDate_input");
		selenium.click("css=span.ui-icon.ui-icon-circle-triangle-e");
		selenium.click("link=7");
		selenium.click("id=formCreateWP:btnSaveWP");

		// WP 3
		selenium.click("//a[@id='navForm:urlCreateWP']/span[2]");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=formCreateWP:wpProj_label");
		selenium.click("//div[@id='formCreateWP:wpProj_panel']/div/ul/li[2]");
		selenium.type("id=formCreateWP:wpID", "11000");
		selenium.type("id=formCreateWP:wpName", "WP3");
		selenium.click("id=formCreateWP:wpWorkPackages_label");
		selenium.click("//div[@id='formCreateWP:wpWorkPackages_panel']/div/ul/li[2]");
		selenium.click("id=formCreateWP:wpStartDate_input");
		selenium.click("link=9");
		selenium.click("id=formCreateWP:wpEndDate_input");
		selenium.click("link=14");
		selenium.click("id=formCreateWP:wpEngineer_label");
		selenium.click("//div[@id='formCreateWP:wpEngineer_panel']/div/ul/li[2]");

		selenium.dragAndDropToObject("id=formCreateWP:allEmployees:1:pnl",
				"id=formCreateWP:dropArea");
		selenium.dragAndDropToObject("id=formCreateWP:allEmployees:2:pnl",
				"id=formCreateWP:dropArea");
		selenium.dragAndDropToObject("id=formCreateWP:allEmployees:3:pnl",
				"id=formCreateWP:dropArea");
		selenium.dragAndDropToObject("id=formCreateWP:allEmployees:4:pnl",
				"id=formCreateWP:dropArea");
		selenium.click("id=formCreateWP:btnSaveWP");

		// WP 4
		selenium.click("//a[@id='navForm:urlCreateWP']/span[2]");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=formCreateWP:wpProj_label");
		selenium.click("//div[@id='formCreateWP:wpProj_panel']/div/ul/li[2]");
		selenium.type("id=formCreateWP:wpID", "12000");
		selenium.type("id=formCreateWP:wpName", "WP4");
		selenium.click("id=formCreateWP:wpWorkPackages_label");
		selenium.click("//div[@id='formCreateWP:wpWorkPackages_panel']/div/ul/li[3]");
		selenium.click("id=formCreateWP:wpStartDate_input");
		selenium.click("link=15");
		selenium.click("id=formCreateWP:wpEndDate_input");
		selenium.click("link=26");
		selenium.click("id=formCreateWP:wpEngineer_label");
		selenium.click("//div[@id='formCreateWP:wpEngineer_panel']/div/ul/li[2]");

		selenium.dragAndDropToObject("id=formCreateWP:allEmployees:1:pnl",
				"id=formCreateWP:dropArea");
		selenium.dragAndDropToObject("id=formCreateWP:allEmployees:3:pnl",
				"id=formCreateWP:dropArea");
		selenium.click("id=formCreateWP:btnSaveWP");

		// WP 5
		selenium.click("//a[@id='navForm:urlCreateWP']/span[2]");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=formCreateWP:wpProj_label");
		selenium.click("//div[@id='formCreateWP:wpProj_panel']/div/ul/li[2]");
		selenium.type("id=formCreateWP:wpID", "21000");
		selenium.type("id=formCreateWP:wpName", "WP5");
		selenium.click("id=formCreateWP:wpWorkPackages_label");
		selenium.click("//div[@id='formCreateWP:wpWorkPackages_panel']/div/ul/li[2]");
		selenium.click("id=formCreateWP:wpStartDate_input");
		selenium.click("link=15");
		selenium.click("id=formCreateWP:wpEndDate_input");
		selenium.click("link=26");
		selenium.click("id=formCreateWP:wpEngineer_label");
		selenium.click("//div[@id='formCreateWP:wpEngineer_panel']/div/ul/li[2]");
		selenium.dragAndDropToObject("id=formCreateWP:allEmployees:1:pnl",
				"id=formCreateWP:dropArea");
		selenium.dragAndDropToObject("id=formCreateWP:allEmployees:2:pnl",
				"id=formCreateWP:dropArea");
		selenium.dragAndDropToObject("id=formCreateWP:allEmployees:4:pnl",
				"id=formCreateWP:dropArea");
		selenium.click("id=formCreateWP:btnSaveWP");

		// Logout
		selenium.click("//a[@id='navForm:btnLogout']");

		// Login as Manager
		TestSteps.loginAs("BBB", "Pass123?", selenium);

		System.out.println("");

		// Create Budget

	}

}
