/**
 * 
 */
package bcit.ca.infosys.KeyboardCowboys.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import bcit.ca.infosys.KeyboardCowboys.util.SHAHash;

/**
 * @author Andrej Koudlai
 * 
 */
@Entity
public class Principle implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@OneToOne
	@JoinColumn(name="empID")
    private Employee emp;
    private String empPassword;

    /**
     * @return the empID
     */
    public Employee getEmp() {
        return emp;
    }

    /**
     * @param empID
     *            the empID to set
     */
    public void setEmp(Employee emp) {
        this.emp = emp;
    }

    /**
     * @return the empPwd
     */
    public String getEmpPassword() {
        return empPassword;
    }

    /**
     * @param empPwd
     *            the empPwd to set
     */
    public void setEmpPassword(String empPassword) {
            this.empPassword = empPassword;         
    }

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
