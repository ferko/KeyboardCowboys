package bcit.ca.infosys.KeyboardCowboys.interfaces;

import java.util.List;

import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;

/**
 * Interface for Work Package Access
 * 
 * @author Andrej Koudlai
 *
 */
public interface WorkPackageAccessInterface {

	/**
	 * Gets work package by id
	 * @param ID
	 * @return
	 */
	public WorkPackage getWorkPackageByID(WorkPackage wp);
	
	/**
	 * Get all work packages
	 * @return
	 */
	public List<WorkPackage> getAllWorkPackages();
	
	public List<WorkPackage> getWorkPackageByResEngineer(Employee currentEmployee);

}
