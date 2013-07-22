package bcit.ca.infosys.KeyboardCowboys.service;

import java.io.Serializable;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import bcit.ca.infosys.KeyboardCowboys.interfaces.StatusReportRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.StatusReport;

@Local(StatusReportRegistration.class)
@Stateless
public class StatusReportRegistration implements Serializable, StatusReportRegistrationInterface {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Inject
	private EntityManager em;	
	
	public void saveStatusReport(StatusReport statusReport) {
		em.persist(statusReport);
	}

	public void mergeStatusReport(StatusReport statusReport) {
		em.merge(statusReport);
	}

}
