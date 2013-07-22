package bcit.ca.infosys.KeyboardCowboys.test.ui;

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
import bcit.ca.infosys.KeyboardCowboys.util.Resources;
import bcit.ca.infosys.KeyboardCowboys.util.SHAHash;

import com.thoughtworks.selenium.DefaultSelenium;

/**
 * Demo testcases.
 * 
 * @author Steven Smith
 */
@RunWith(Arquillian.class)
public class WPempPROJtest {
    
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
				.create(WebArchive.class, "wpprojtest.war")
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
    
    /** Test Employee Creation functionality. Demonstration */
    @Ignore
	@Test
	public void createEmployeesTest() {
		selenium.open(deploymentUrl + "login.xhtml");
		
		//TODO, failing, incorrect username and password?
		TestSteps.loginAs("supervisor", "joe", selenium);
		
        Assert.assertTrue("Username should be logged in!", selenium
                .isElementPresent("xpath=//*[@id='navForm:btnLogout']"));

		// Show that no projects exist
		selenium.click("id=navForm:urlViewProj");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=navForm:urlAssignEmp");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=formAssignEmp:aetpEmployee");

		// Create employee #1
		selenium.click("id=navForm:urlCreateEmp");
		selenium.waitForPageToLoad("30000");
		selenium.type("id=createEmp:empName", "Jimmy Conway");
		selenium.type("id=createEmp:empUserName", "jconway");
		selenium.click("id=createEmp:empPassword");
		selenium.type("id=createEmp:empPassword", "qwe");
		selenium.click("id=createEmp:empPasswordMatch");
		selenium.type("id=createEmp:empPasswordMatch", "qwe");
		selenium.click("//div[@id='createEmp:empSuperVisor']/div[2]/span");
		selenium.click("//div[@id='createEmp:empSuperVisor']/div[2]/span");
		selenium.click("id=createEmp:empRole:0");
		selenium.click("//table[@id='createEmp:empRole']/tbody/tr/td/div/div[2]");
		selenium.click("id=createEmp:btnSaveEmp");
		selenium.click("id=navForm:urlCreateEmp");
		
		// Create employee #2
		selenium.type("id=createEmp:empName", "Paul Cicero");
		selenium.type("id=createEmp:empUserName", "pcicero");
		selenium.type("id=createEmp:empPassword", "qqq");
		selenium.type("id=createEmp:empPasswordMatch", "qqq");
		selenium.click("id=createEmp:empRole:0");
		selenium.click("//table[@id='createEmp:empRole']/tbody/tr/td/div/div[2]");
		selenium.click("id=createEmp:btnSaveEmp");
		selenium.click("id=navForm:urlCreateEmp");
		
		// Create employee #3
		selenium.type("id=createEmp:empName", "Billy Batts");
		selenium.type("id=createEmp:empUserName", "bbatts");
		selenium.type("id=createEmp:empPassword", "123");
		selenium.type("id=createEmp:empPasswordMatch", "123");
		// selenium.click("//table[@id='createEmp:j_idt22']/tbody/tr[3]/td[2]");
		selenium.click("id=createEmp:empRole:0");
		selenium.click("//table[@id='createEmp:empRole']/tbody/tr/td/div/div[2]");
		selenium.click("id=createEmp:btnSaveEmp");
		selenium.click("id=navForm:urlCreateEmp");
		
		// Create employee #4
		selenium.type("id=createEmp:empName", "Henry Hill");
		selenium.type("id=createEmp:empUserName", "hhill");
		selenium.click("id=createEmp:empPassword");
		selenium.type("id=createEmp:empPassword", "999");
		selenium.type("id=createEmp:empPasswordMatch", "999");
		selenium.click("id=createEmp:empRole:0");
		selenium.click("//table[@id='createEmp:empRole']/tbody/tr/td/div/div[2]");
		selenium.click("id=createEmp:btnSaveEmp");
		selenium.click("id=navForm:urlCreateEmp");
		
		// Create employee #5
		selenium.type("id=createEmp:empName", "Frankie Carbone");
		selenium.type("id=createEmp:empUserName", "fcarbone");
		selenium.type("id=createEmp:empPassword", "pass");
		selenium.type("id=createEmp:empPasswordMatch", "pass");
		selenium.click("id=createEmp:empRole:0");
		selenium.click("//table[@id='createEmp:empRole']/tbody/tr/td/div/div[2]");
		selenium.click("id=createEmp:btnSaveEmp");
		selenium.click("id=navForm:urlCreateProj");
		selenium.waitForPageToLoad("30000");
		
		// Create a project
		selenium.type("id=formCreateProj:projID", "1");
		selenium.type("id=formCreateProj:projName", "Lufthansa Project");
		selenium.type("id=formCreateProj:projCostEstimate", "25000");
		selenium.click("id=formCreateProj:projStartDate_input");
		selenium.click("link=26");
		selenium.click("id=formCreateProj:projEndDate_input");
		selenium.click("link=28");
		selenium.type("id=formCreateProj:projDescription",
				"Sample description of the project.");

		// Assign employees to project
		selenium.click("id=formCreateProj:btnSaveProj");
		selenium.click("id=navForm:urlAssignEmp");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=formAssignEmp:aetpEmployee_label");
		selenium.click("//div[@id='formAssignEmp:aetpEmployee_panel']/div/ul/li[2]");
		selenium.click("id=formAssignEmp:aetpProj_label");
		selenium.click("//div[@id='formAssignEmp:aetpProj_panel']/div/ul/li[2]");
		selenium.click("id=formAssignEmp:btnSaveEmp");
		selenium.click("id=navForm:urlAssignEmp");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=formAssignEmp:aetpEmployee_label");
		selenium.click("//div[@id='formAssignEmp:aetpEmployee_panel']/div/ul/li[2]");
		selenium.click("id=formAssignEmp:aetpProj_label");
		selenium.click("//div[@id='formAssignEmp:aetpProj_panel']/div/ul/li[2]");
		selenium.click("id=formAssignEmp:btnSaveEmp");
		selenium.click("id=navForm:urlAssignEmp");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=formAssignEmp:aetpEmployee_label");
		selenium.click("//div[@id='formAssignEmp:aetpEmployee_panel']/div/ul/li[2]");
		selenium.click("id=formAssignEmp:aetpProj_label");
		selenium.click("//div[@id='formAssignEmp:aetpProj_panel']/div/ul/li[2]");
		selenium.click("id=formAssignEmp:btnSaveEmp");
		selenium.click("id=navForm:urlAssignEmp");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=formAssignEmp:aetpEmployee_label");
		selenium.click("//div[@id='formAssignEmp:aetpEmployee_panel']/div/ul/li[2]");
		selenium.click("id=formAssignEmp:aetpProj_label");
		selenium.click("//div[@id='formAssignEmp:aetpProj_panel']/div/ul/li[2]");
		selenium.click("id=formAssignEmp:btnSaveEmp");
		selenium.click("id=navForm:urlAssignEmp");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=formAssignEmp:aetpEmployee_label");
		selenium.click("//div[@id='formAssignEmp:aetpEmployee_panel']/div/ul/li[3]");
		selenium.click("id=formAssignEmp:aetpProj_label");
		selenium.click("//div[@id='formAssignEmp:aetpProj_panel']/div/ul/li[2]");
		selenium.click("id=formAssignEmp:btnSaveEmp");

		// Create WP
		selenium.click("id=navForm:urlCreateWP");
		selenium.waitForPageToLoad("30000");
		selenium.click("//div[@id='formCreateWP:wpProj']/div[2]/span");
		selenium.click("//div[@id='formCreateWP:wpProj_panel']/div/ul/li[2]");
		selenium.click("id=formCreateWP:wpWorkPackages_label");
		selenium.click("//div[@id='formCreateWP:wpWorkPackages']/div[2]/span");
		selenium.click("//div[@id='formCreateWP:wpEngineer']/div[2]/span");
		selenium.click("//div[@id='formCreateWP:wpEngineer_panel']/div/ul/li[3]");
		selenium.type("id=formCreateWP:wpID", "1");
		selenium.type("id=formCreateWP:wpName", "Sample WP");
		selenium.click("id=formCreateWP:wpStartDate_input");
		selenium.click("link=26");
		selenium.click("id=formCreateWP:wpEndDate_input");
		selenium.click("link=28");

		selenium.dragAndDropToObject("id=formCreateWP:allEmployees:0:pnl",
				"id=formCreateWP:dropArea");
		selenium.dragAndDropToObject("id=formCreateWP:allEmployees:1:pnl",
				"id=formCreateWP:dropArea");
		selenium.dragAndDropToObject("id=formCreateWP:allEmployees:2:pnl",
				"id=formCreateWP:dropArea");
		selenium.dragAndDropToObject("id=formCreateWP:allEmployees:3:pnl",
				"id=formCreateWP:dropArea");
		selenium.dragAndDropToObject("id=formCreateWP:allEmployees:4:pnl",
				"id=formCreateWP:dropArea");

		selenium.click("id=formCreateWP");
		selenium.click("id=formCreateWP:btnSaveWP");

		// Assign manager and assisstant to project. Verify everything exists
		selenium.click("id=navForm:urlViewProj");
		selenium.waitForPageToLoad("30000");
		selenium.click("id=viewProjects:projTable:0:btnShowProj");
		selenium.click("id=viewProjects:btnEditProj");
		selenium.click("//div[@id='viewProjects:projManager']/div[2]/span");
		selenium.click("//div[@id='viewProjects:projManager_panel']/div/ul/li[5]");
		selenium.click("//div[@id='viewProjects:projAssistant']/div[2]");
		selenium.click("//div[@id='viewProjects:projAssistant_panel']/div/ul/li[2]");
		selenium.click("id=viewProjects:btnUpdateProj");
		selenium.click("css=span.ui-icon.ui-icon-closethick");
		selenium.click("//tbody[@id='viewProjects:projTable_data']/tr/td[2]/div/span");
		selenium.click("id=viewProjects:projTable:0:dtProj2:0:dtWP:0:btnShowWP");
		selenium.click("//div[@id='viewProjects:unique']/div/a/span");

		// Edit WP
		selenium.click("id=viewProjects:btnEditWP");
		selenium.type("id=viewProjects:wpName", "Sample Work Package");
		selenium.click("id=viewProjects:btnSaveWP");

	}
}
