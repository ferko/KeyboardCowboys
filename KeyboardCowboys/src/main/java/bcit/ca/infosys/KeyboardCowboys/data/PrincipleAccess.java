package bcit.ca.infosys.KeyboardCowboys.data;

import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import bcit.ca.infosys.KeyboardCowboys.interfaces.PrincipleAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.Principle;

@Local(PrincipleAccessInterface.class)
@Stateless
public class PrincipleAccess implements PrincipleAccessInterface{
    @Inject
    private EntityManager em;
    @Inject
    private Logger log;
    
    
    public String getHSHPwd(String empUserName){

        TypedQuery<Principle> query = em.createQuery("SELECT DISTINCT p FROM Principle p JOIN FETCH p.emp WHERE p.emp.empUserName = :userName", Principle.class);
        return query.setParameter("userName", empUserName).getSingleResult().getEmpPassword();
    }

    @Override
    public Principle getPrincipalByEmp(Employee emp)
    {
    	return (Principle) em.createQuery("SELECT DISTINCT p FROM Principle p WHERE p.emp = :emp").setParameter("emp", emp).getSingleResult();
    }
}
