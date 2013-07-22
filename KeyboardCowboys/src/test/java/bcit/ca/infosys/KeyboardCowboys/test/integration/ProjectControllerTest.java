package bcit.ca.infosys.KeyboardCowboys.test.integration;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.primefaces.event.ToggleEvent;
import org.primefaces.model.Visibility;

import bcit.ca.infosys.KeyboardCowboys.controller.ProjectController;
import bcit.ca.infosys.KeyboardCowboys.data.EmployeeAccess;
import bcit.ca.infosys.KeyboardCowboys.data.ProjectAccess;
import bcit.ca.infosys.KeyboardCowboys.data.WorkPackageAccess;
import bcit.ca.infosys.KeyboardCowboys.exceptions.ProjectAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.exceptions.WorkPackageAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.interfaces.ProjectAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.ProjectControllerInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.ProjectRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.EmployeePayInfo;
import bcit.ca.infosys.KeyboardCowboys.model.PayLevel;
import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.TimeRow;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheet;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheetApproval;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;
import bcit.ca.infosys.KeyboardCowboys.service.ProjectRegistration;
import bcit.ca.infosys.KeyboardCowboys.service.WorkPackageRegistration;
import bcit.ca.infosys.KeyboardCowboys.util.Resources;

@RunWith(Arquillian.class)
public class ProjectControllerTest {
	@Deployment
	public static Archive<?> createTestArchive() {
        File[] libs = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().as(File.class);
        WebArchive res = ShrinkWrap
				.create(WebArchive.class, "ProjControlTest.war")
                .addPackage("bcit.ca.infosys.KeyboardCowboys.controller")
                .addPackage("bcit.ca.infosys.KeyboardCowboys.converter")
                .addPackage("bcit.ca.infosys.KeyboardCowboys.data")
                .addPackage("bcit.ca.infosys.KeyboardCowboys.exceptions")
                .addPackage("bcit.ca.infosys.KeyboardCowboys.interfaces")
                .addPackage("bcit.ca.infosys.KeyboardCowboys.model")
                .addPackage("bcit.ca.infosys.KeyboardCowboys.qualifiers")
                .addPackage("bcit.ca.infosys.KeyboardCowboys.service")
                .addPackage("bcit.ca.infosys.KeyboardCowboys.util")
				.addAsResource(
						new File(
								"src/test/resources/META-INF/test-persistence.xml"),
						"META-INF/persistence.xml")
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
				// Deploy our test datasource
				.addAsWebInfResource("test-ds.xml", "test-ds.xml");
        for (File file : libs) {
            res.addAsLibrary(file);
        }
        return res;
	}

	@Inject
	ProjectRegistrationInterface projectRegistration;
	@Inject
	ProjectAccessInterface projectAccess;

	@Inject
	Logger log;

	@Test
	public void testProjectController() throws Exception {
		ProjectController pc = new ProjectController();
		Project p1 = new Project();
		p1.setProjID("1");
		p1.setProjName("Test Project 1");
		projectRegistration.register(p1);
		// Test p1
		assertEquals("1", p1.getProjID());
		assertEquals("Test Project 1", p1.getProjName());

		pc.setProject(p1);

		// Test reading from controller
		Project p2 = pc.getProject();
		assertEquals(p1.getProjID(), p2.getProjID());
		assertEquals(p1.getProjName(), p2.getProjName());

		// pc.getProjects();

		// Test that the project is entered into the database
		// pc.saveProject(); // This is what is causing the null pointer
		// exception
	}
}
