package bcit.ca.infosys.KeyboardCowboys.interfaces;


import bcit.ca.infosys.KeyboardCowboys.exceptions.PrincipleAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.model.Principle;
/**
 * Interface for Principle Registration Class
 * 
 * @author Andrej Koudlai
 *
 */

public interface PrincipleRegistrationInterface {


    
    public void register(Principle principle) throws PrincipleAlreadyExistsException;

	void merge(Principle principle);
}
