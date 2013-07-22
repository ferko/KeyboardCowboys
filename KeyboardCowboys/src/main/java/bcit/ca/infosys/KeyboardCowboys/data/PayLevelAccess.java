package bcit.ca.infosys.KeyboardCowboys.data;

import java.io.Serializable;
import java.util.List;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;

import bcit.ca.infosys.KeyboardCowboys.interfaces.PayLevelAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.model.PayLevel;

@Local(PayLevelAccessInterface.class)
@Stateless
public class PayLevelAccess implements Serializable, PayLevelAccessInterface{

	@Inject
	private EntityManager em;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public List<PayLevel> getAllPayLevels() {
		return em.createQuery("SELECT pl FROM PayLevel pl", PayLevel.class).getResultList();
	}

}
