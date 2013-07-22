package bcit.ca.infosys.KeyboardCowboys.test.integration;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import bcit.ca.infosys.KeyboardCowboys.exceptions.TimeSheetAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.interfaces.TimeSheetRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.EmployeePayInfo;
import bcit.ca.infosys.KeyboardCowboys.model.PayLevel;
import bcit.ca.infosys.KeyboardCowboys.model.Project;
import bcit.ca.infosys.KeyboardCowboys.model.TimeRow;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheet;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheetApproval;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;
import bcit.ca.infosys.KeyboardCowboys.service.TimeSheetRegistration;
import bcit.ca.infosys.KeyboardCowboys.util.Resources;

@RunWith(Arquillian.class)
public class TimeSheetRegistrationTest {
	@Deployment
	public static Archive<?> createTestArchive() {
        File[] libs = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().as(File.class);
        WebArchive res = ShrinkWrap
				.create(WebArchive.class, "timeSheetRegTest.war")
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
	TimeSheetRegistrationInterface timeSheetRegistration;

	@Inject
	Logger log;

	@Test
	public void testRegister() throws Exception {
		TimeSheet newTimeSheet = new TimeSheet();
		
        DateFormat df = new SimpleDateFormat("MM/dd/yy");
        Date testDate = df.parse("03/12/13");
		newTimeSheet.setTsWeekEnding(testDate);
//		Employee testEmployee = new Employee();
//		newTimeSheet.setTsEmp(testEmployee);
//		List<TimeRow> testTsTimeRows = new ArrayList<TimeRow>();
//		newTimeSheet.setTsTimeRows(testTsTimeRows);
		timeSheetRegistration.register(newTimeSheet);

		assertNotNull(newTimeSheet.getTsID());
		log.info("Timesheet was persisted with id " + newTimeSheet.getTsID());
	}

}
