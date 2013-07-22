package bcit.ca.infosys.KeyboardCowboys.interfaces;


import bcit.ca.infosys.KeyboardCowboys.exceptions.TimeRowAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.model.TimeRow;
/**
 * Interface for Time Row Registration
 * 
 * @author Andrej K
 *
 */

public interface TimeRowRegistrationInterface {

    public void register(TimeRow timeRow) throws TimeRowAlreadyExistsException;
    
}
