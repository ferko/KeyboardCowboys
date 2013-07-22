package bcit.ca.infosys.KeyboardCowboys.service;

import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import bcit.ca.infosys.KeyboardCowboys.exceptions.EmployeeAlreadyExistsException;
import bcit.ca.infosys.KeyboardCowboys.interfaces.EmployeeRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
/**
 * Employee Registration class.
 * Registers a new and updates an existing employee.
 * 
 * @author Andrej
 *
 */
@Local(EmployeeRegistrationInterface.class)
@Stateless
public class EmployeeRegistration implements EmployeeRegistrationInterface{
	/**
	 * Entity Manager used to access the database
	 */
	@Inject
	private EntityManager em;
	/**
	 * Logger which is used to print events to the log
	 */
	@Inject
	private Logger log;
	
	/**
	 * Registers an Employee.  If it already exists then it will throw an exception
	 * @param emp Employee to be registered
	 * @throws EmployeeAlreadyExistsException Employee already exists
	 */
	public void register(Employee emp) throws EmployeeAlreadyExistsException {
		if (!em.createQuery(
				"SELECT e FROM Employee e WHERE e.empUserName = :UserName",
				Employee.class).setParameter("UserName", emp.getEmpUserName())
				.getResultList().isEmpty()) {
			log.info("UserName:\'" + emp.getEmpUserName() + "\' with ID:\'" + emp.getEmpID() + "\' already"
					+ " exists @ EmployeeRegistration.class");
			throw new EmployeeAlreadyExistsException(
					"Employee already exists in database");
		}
		em.persist(emp);
		log.info("Registered UserName:\'" + emp.getEmpUserName() + "\' with ID:\'" + emp.getEmpID() + "\' into"
				+ " DB @ EmployeeRegistration.class");
	}

	/**
	 * Update an employee
	 * @param emp employee to be updated
	 */
	public void updateEmployee(Employee emp) {
		em.merge(emp);
	}
}
