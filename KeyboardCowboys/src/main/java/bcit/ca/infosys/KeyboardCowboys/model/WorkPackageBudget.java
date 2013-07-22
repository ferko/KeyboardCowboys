package bcit.ca.infosys.KeyboardCowboys.model;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class WorkPackageBudget {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer wpbID;
	@ManyToOne(fetch=FetchType.EAGER)
	private PayLevel payLevel;
	private int days;
	@ManyToOne(fetch=FetchType.LAZY)
	private WorkPackage workPackage;
	@ManyToOne(fetch=FetchType.LAZY)
	private WorkPackage resWorkPackage;
	public Integer getWpbID() {
		return wpbID;
	}
	public void setWpbID(Integer wpbID) {
		this.wpbID = wpbID;
	}
	public PayLevel getPayLevel() {
		return payLevel;
	}
	public void setPayLevel(PayLevel payLevel) {
		this.payLevel = payLevel;
	}
	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public WorkPackage getWorkPackage() {
		return workPackage;
	}
	public void setWorkPackage(WorkPackage workPackage) {
		this.workPackage = workPackage;
	}
	public WorkPackage getResWorkPackage() {
		return resWorkPackage;
	}
	public void setResWorkPackage(WorkPackage resWorkPackage) {
		this.resWorkPackage = resWorkPackage;
	}
	
}
