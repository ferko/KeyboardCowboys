package bcit.ca.infosys.KeyboardCowboys.interfaces;


import bcit.ca.infosys.KeyboardCowboys.exceptions.WorkPackageAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;

/**
 * Interface for Work Package Registration
 * 
 * @author Andrej K
 *
 */

public interface WorkPackageRegistrationInterface {

    public void register(WorkPackage wPackage) throws WorkPackageAlreadyExistsException;
    
    public void mergeWorkPackage(WorkPackage workPackage);
}
