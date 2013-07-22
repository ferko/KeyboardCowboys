package bcit.ca.infosys.KeyboardCowboys.service;

import java.io.Serializable;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import bcit.ca.infosys.KeyboardCowboys.interfaces.TimeSheetApprovalRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheetApproval;

@Local(TimeSheetApprovalRegistrationInterface.class)
@Stateless
public class TimeSheetApprovalRegistration implements Serializable, TimeSheetApprovalRegistrationInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager em;
	@Override
	public void registerTimeSheetApproval(TimeSheetApproval tsa) {
		em.persist(tsa);
	}

}
