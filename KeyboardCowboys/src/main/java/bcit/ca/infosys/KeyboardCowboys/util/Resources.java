package bcit.ca.infosys.KeyboardCowboys.util;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.InjectionPoint;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;

/**
 * This class uses CDI to alias Java EE resources, such as the persistence context, to CDI beans
 * 
 * <p>
 * Example injection on a managed bean field:
 * </p>
 * 
 * <pre>
 * &#064;Inject
 * private EntityManager em;
 * </pre>
 */
public class Resources {
   // use @SuppressWarnings to tell IDE to ignore warnings about field not being referenced directly
   @SuppressWarnings("unused")
   @Produces
   @PersistenceContext(type=PersistenceContextType.EXTENDED)
   private EntityManager em;
   
   @Produces
   public Logger produceLog(InjectionPoint injectionPoint) {
       try {
           //Using FileHandler class:
           //http://javasourcecode.org/html/open-source/jdk/jdk-6u23/java/util/logging/FileHandler.java.html
           //Alternative:
           //http://stackoverflow.com/questions/8248899/java-logging-how-to-redirect-output-to-a-custom-log-file-for-a-logger
           
           //Log Files can be found in your system temp folder e.g. "C:\Users\student327\AppData\Local\Temp"
           FileHandler fh = new FileHandler("%t/cowboy.log", 1048576, 1, true);
           fh.setFormatter(new SimpleFormatter()); //Default formatter is XMLFormatter
           Logger.getLogger("").addHandler(fh);
           } catch (SecurityException e) {
           } catch (IOException e) {
       }
       return Logger.getLogger(injectionPoint.getMember().getDeclaringClass().getName());
   }
   
   @Produces
   @RequestScoped
   public FacesContext produceFacesContext() {
      return FacesContext.getCurrentInstance();
   }
   
}
