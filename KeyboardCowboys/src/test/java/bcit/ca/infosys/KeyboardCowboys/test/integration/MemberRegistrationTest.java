package bcit.ca.infosys.KeyboardCowboys.test.integration;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.logging.Logger;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import bcit.ca.infosys.KeyboardCowboys.controller.MemberController;
import bcit.ca.infosys.KeyboardCowboys.model.Member;
import bcit.ca.infosys.KeyboardCowboys.service.MemberRegistration;
import bcit.ca.infosys.KeyboardCowboys.util.Resources;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class MemberRegistrationTest {
   @Deployment
   public static Archive<?> createTestArchive() {
       File[] libs = Maven.resolver().loadPomFromFile("pom.xml")
               .importRuntimeDependencies().as(File.class);
       WebArchive res = ShrinkWrap.create(WebArchive.class, "MemberRegTest.war")
            .addPackage("bcit.ca.infosys.KeyboardCowboys.controller")
            .addPackage("bcit.ca.infosys.KeyboardCowboys.converter")
            .addPackage("bcit.ca.infosys.KeyboardCowboys.data")
            .addPackage("bcit.ca.infosys.KeyboardCowboys.exceptions")
            .addPackage("bcit.ca.infosys.KeyboardCowboys.interfaces")
            .addPackage("bcit.ca.infosys.KeyboardCowboys.model")
            .addPackage("bcit.ca.infosys.KeyboardCowboys.qualifiers")
            .addPackage("bcit.ca.infosys.KeyboardCowboys.service")
            .addPackage("bcit.ca.infosys.KeyboardCowboys.util")
            .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
            // Deploy our test datasource
            .addAsWebInfResource("test-ds.xml", "test-ds.xml");
       for (File file : libs) {
           res.addAsLibrary(file);
       }
       return res;
   }

   @Inject
   MemberRegistration memberRegistration;

   @Inject
   Logger log;

   @Test
   public void testRegister() throws Exception {
      Member newMember = new Member();
      newMember.setName("Jane Doe");
      newMember.setEmail("jane@mailinator.com");
      newMember.setPhoneNumber("2125551234");
      memberRegistration.register(newMember);
      assertNotNull(newMember.getId());
      log.info(newMember.getName() + " was persisted with id " + newMember.getId());
   }
   
}
