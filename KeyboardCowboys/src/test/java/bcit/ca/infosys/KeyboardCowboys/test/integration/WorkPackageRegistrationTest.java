package bcit.ca.infosys.KeyboardCowboys.test.integration;

import static org.junit.Assert.assertNotNull;

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

import bcit.ca.infosys.KeyboardCowboys.interfaces.WorkPackageRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;

@RunWith(Arquillian.class)
public class WorkPackageRegistrationTest {
	@Deployment
	public static Archive<?> createTestArchive() {
        File[] libs = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().as(File.class);
        WebArchive res = ShrinkWrap
				.create(WebArchive.class, "workPackageRegTest.war")
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
	WorkPackageRegistrationInterface workPackageRegistration;

	@Inject
	Logger log;

	@Test
	public void testRegister() throws Exception {
		WorkPackage newWorkPackage = new WorkPackage();
		newWorkPackage.setWpID("1");
		newWorkPackage.setWpName("Name of Package");
//		newWorkPackage.setWpStartDate("Start date 2012");
//		newWorkPackage.setWpEndDate("End date 2012");
	//	Project testProject = new Project();
//		newWorkPackage.setWpProject(testProject);
//		List<WorkPackage> testWorkPackages = new ArrayList<WorkPackage>();
	//	newWorkPackage.setWpPackages(testWorkPackages);

		workPackageRegistration.register(newWorkPackage);

		assertNotNull(newWorkPackage.getWpID());
		log.info("Workpackage was persisted with id "
				+ newWorkPackage.getWpID());
	}

}
