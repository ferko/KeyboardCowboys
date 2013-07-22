package bcit.ca.infosys.KeyboardCowboys.test.integration;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.HashSet;
import java.util.Set;
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

import bcit.ca.infosys.KeyboardCowboys.interfaces.ProjectRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;

@RunWith(Arquillian.class)
public class ProjectRegistrationTest {
	@Deployment
	public static Archive<?> createTestArchive() {
        File[] libs = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().as(File.class);
        WebArchive res = ShrinkWrap
				.create(WebArchive.class, "ProjRegTest.war")
                .addPackage("bcit.ca.infosys.KeyboardCowboys.controller")
                .addPackage("bcit.ca.infosys.KeyboardCowboys.converter")
                .addPackage("bcit.ca.infosys.KeyboardCowboys.data")
                .addPackage("bcit.ca.infosys.KeyboardCowboys.exceptions")
                .addPackage("bcit.ca.infosys.KeyboardCowboys.interfaces")
                .addPackage("bcit.ca.infosys.KeyboardCowboys.model")
                .addPackage("bcit.ca.infosys.KeyboardCowboys.qualifiers")
                .addPackage("bcit.ca.infosys.KeyboardCowboys.service")
                .addPackage("bcit.ca.infosys.KeyboardCowboys.util")
				.addAsResource("META-INF/test-persistence.xml",
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
	Logger log;

	@Test
	public void testRegister() throws Exception {
		Project newProject = new Project();
		newProject.setProjID("1");
		newProject.setProjName("pro project");
//		Employee projWorkPackages = new Employee();
//		newProject.setProjManager(projWorkPackages);
		Set<WorkPackage> testProjWorkPackages = new HashSet<WorkPackage>();
		newProject.setProjWorkPackages(testProjWorkPackages);
//		List<Employee> testEmployees = new ArrayList<Employee>();
//		newProject.setProjEmployees(testEmployees);
//		Employee testProjAssistant = new Employee();
//		newProject.setProjAssistant(testProjAssistant);
//		newProject.setProjStartDate("01/01/13");
//		newProject.setProjEndDate("02/02/14");
		newProject.setProjStatus("Good.");
		newProject.setProjCostEstimate(420.00);

		projectRegistration.register(newProject);

		assertNotNull(newProject.getProjID());
		// log.info(newProject.getProjName() + " was persisted with id "
		// + newProject.getProjID());
	}

}
