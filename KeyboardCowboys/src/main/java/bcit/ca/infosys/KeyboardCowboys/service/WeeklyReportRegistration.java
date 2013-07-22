package bcit.ca.infosys.KeyboardCowboys.service;

import java.io.Serializable;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import bcit.ca.infosys.KeyboardCowboys.interfaces.WeeklyReportRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.WeeklyReport;


@Local(WeeklyReportRegistration.class)
@Stateless
public class WeeklyReportRegistration implements Serializable, WeeklyReportRegistrationInterface {

	@Inject
	private EntityManager em;

    @Override
    public void saveWeeklyReport(WeeklyReport weeklyReport) {
        em.persist(weeklyReport); 
    }

}
