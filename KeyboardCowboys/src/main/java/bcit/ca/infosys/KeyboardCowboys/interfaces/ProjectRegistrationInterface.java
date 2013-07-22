package bcit.ca.infosys.KeyboardCowboys.interfaces;

import bcit.ca.infosys.KeyboardCowboys.exceptions.ProjectAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.model.Project;

/**
 * Interface for Project Registration Class
 * 
 * @author Andrej K
 *
 */

public interface ProjectRegistrationInterface {

    public void register(Project proj) throws ProjectAlreadyExistsException;
    public void update(Project proj);
}
