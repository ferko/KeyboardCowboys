package bcit.ca.infosys.KeyboardCowboys.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
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
import bcit.ca.infosys.KeyboardCowboys.interfaces.WorkPackageAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.WorkPackageRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;

@RunWith(Arquillian.class)
public class WorkPackageParentTest {
	@Deployment
	public static Archive<?> createTestArchive() {
        File[] libs = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().as(File.class);
        WebArchive res = ShrinkWrap
				.create(WebArchive.class, "wpParentTest.war")
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
	WorkPackageRegistrationInterface workPackageRegistration;
	
	@Inject
	WorkPackageAccessInterface workPackageAccess;
	
	@Inject
	Logger log;

	@Test
	public void testRegister() throws Exception {
		Project newProj = new Project();
		newProj.setProjID("333232");
		projectRegistration.register(newProj);
		
		WorkPackage wpOne = new WorkPackage();
		wpOne.setWpID("1");
		wpOne.setWpName("one");
		wpOne.setWpProject(newProj);
		workPackageRegistration.register(wpOne);
		
		WorkPackage wpThree = new WorkPackage();
		wpThree.setWpID("3");
		wpThree.setWpName("three");
		workPackageRegistration.register(wpThree);

		WorkPackage wpTwo = new WorkPackage();
		wpTwo.setWpID("2");
		wpTwo.setWpName("two");
		wpTwo.setWpPackage(wpOne);
		List<WorkPackage> list = new ArrayList<WorkPackage>();
		list.add(wpThree);
		wpThree.setWpPackage(wpTwo);
		wpTwo.setWpPackages(list);

		workPackageRegistration.register(wpTwo);
		workPackageRegistration.mergeWorkPackage(wpThree);
		
		WorkPackage wpNew  = workPackageAccess.getWorkPackageByID(wpOne);
		assertNotNull(wpNew);
		assertEquals("one", wpNew.getWpName());
		assertNotNull(wpNew.getWpPackages());
/*		assertNotNull(wpNew.getWpPackages().get(0));
		assertEquals("two", wpNew.getWpPackages().get(0).getWpName());
		assertEquals("three", wpNew.getWpPackages().get(0).getWpPackages().get(0).getWpName());
		assertEquals("one", wpNew.getWpPackages().get(0).getWpPackage().getWpName());
*/	}
}
