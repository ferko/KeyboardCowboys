package bcit.ca.infosys.KeyboardCowboys.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class EmployeePayInfo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer epiId;
	@OneToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="epiPLevel")
    private PayLevel epiPayLevel;
	@OneToOne(fetch=FetchType.LAZY)
	private Employee epiEmployee;
	private double epiHolidays;
	private double epiFlex;
	private double epiOvertime;
	
	public PayLevel getEpiPayLevel() {
		return epiPayLevel;
	}
	public void setEpiPayLevel(PayLevel epiPayLevel) {
		this.epiPayLevel = epiPayLevel;
	}
	public Employee getEpiEmployee() {
		return epiEmployee;
	}
	public void setEpiEmployee(Employee epiEmployee) {
		this.epiEmployee = epiEmployee;
	}
	public double getEpiHolidays() {
		return epiHolidays;
	}
	public void setEpiHolidays(double epiHolidays) {
		this.epiHolidays = epiHolidays;
	}
	public double getEpiFlex() {
		return epiFlex;
	}
	public void setEpiFlex(double epiFlex) {
		this.epiFlex = epiFlex;
	}
	public Integer getEpiId() {
		return epiId;
	}
	public void setEpiId(Integer epiId) {
		this.epiId = epiId;
	}
	public double getEpiOvertime() {
		return epiOvertime;
	}
	public void setEpiOvertime(double epiOvertime) {
		this.epiOvertime = epiOvertime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
	
    
}
