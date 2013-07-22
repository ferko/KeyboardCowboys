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

import bcit.ca.infosys.KeyboardCowboys.exceptions.EmployeeAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.exceptions.PrincipleAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.exceptions.TimeSheetAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.interfaces.EmployeeRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.PrincipleRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.EmployeePayInfo;
import bcit.ca.infosys.KeyboardCowboys.model.PayLevel;
import bcit.ca.infosys.KeyboardCowboys.model.Principle;
import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.TimeRow;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheet;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheetApproval;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;
import bcit.ca.infosys.KeyboardCowboys.service.EmployeeRegistration;
import bcit.ca.infosys.KeyboardCowboys.service.PrincipleRegistration;
import bcit.ca.infosys.KeyboardCowboys.util.Resources;
import bcit.ca.infosys.KeyboardCowboys.util.SHAHash;
//import bcit.ca.infosys.KeyboardCowboys.controller.PrincipleController;
//import bcit.ca.infosys.KeyboardCowboys.model.PrinciplePayInfo;

@RunWith(Arquillian.class)
public class PrincipleRegistrationTest {
	@Deployment
	public static Archive<?> createTestArchive() {
        File[] libs = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().as(File.class);
        WebArchive res = ShrinkWrap
				.create(WebArchive.class, "PrincipalRegTest.war")
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
	PrincipleRegistrationInterface principleRegistration;

	@Inject
	EmployeeRegistrationInterface employeeRegistration;
	
	@Inject
	Logger log;

	@Test
	public void testRegister() throws Exception {
		Principle newPrinciple = new Principle();
		newPrinciple.setEmpPassword("pa$$w0rd");
		
		Employee emp = new Employee();
		emp.setEmpUserName("foobar");
		
		employeeRegistration.register(emp);
		newPrinciple.setEmp(emp);
		principleRegistration.register(newPrinciple);

		assertNotNull(newPrinciple.getEmp());
		// log.info(newPrinciple.getEmpName() + " was persisted with id "
		// + newPrinciple.getEmpID());
	}

}
