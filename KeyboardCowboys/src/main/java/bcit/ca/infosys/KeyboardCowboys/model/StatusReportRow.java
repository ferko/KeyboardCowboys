package bcit.ca.infosys.KeyboardCowboys.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class StatusReportRow implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer srRowId;
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="srID")
	private StatusReport statusReport;
	
	@OneToOne(fetch=FetchType.LAZY)
    private Employee srEmp;
	
	private int bcwsPDay;
	
	private double bcwsCost;
	
	private double tsSubmission;
	
	private double actualCost;
	
	private int etcDay;
	
	private double etcCost;
	
	private int eacDay;
	
	private double eacCost;

	public Integer getSrRowId() {
		return srRowId;
	}

	public void setSrRowId(Integer srRowId) {
		this.srRowId = srRowId;
	}

	public StatusReport getStatusReport() {
		return statusReport;
	}

	public void setStatusReport(StatusReport statusReport) {
		this.statusReport = statusReport;
	}

	public Employee getSrEmp() {
		return srEmp;
	}

	public void setSrEmp(Employee srEmp) {
		this.srEmp = srEmp;
	}

	public int getBcwsPDay() {
		return bcwsPDay;
	}

	public void setBcwsPDay(int bcwsPDay) {
		this.bcwsPDay = bcwsPDay;
	}

	public double getBcwsCost() {
		return bcwsCost;
	}

	public void setBcwsCost(double bcwsCost) {
		this.bcwsCost = bcwsCost;
	}

	public double getTsSubmission() {
		return tsSubmission;
	}

	public void setTsSubmission(double tsSubmission) {
		this.tsSubmission = tsSubmission;
	}

	public double getActualCost() {
		return actualCost;
	}

	public void setActualCost(double actualCost) {
		this.actualCost = actualCost;
	}

	public int getEtcDay() {
		return etcDay;
	}

	public void setEtcDay(int etcDay) {
		this.etcDay = etcDay;
	}

	public double getEtcCost() {
		return etcCost;
	}

	public void setEtcCost(double etcCost) {
		this.etcCost = etcCost;
	}

	public int getEacDay() {
		return eacDay;
	}

	public void setEacDay(int eacDay) {
		this.eacDay = eacDay;
	}

	public double getEacCost() {
		return eacCost;
	}

	public void setEacCost(double eacCost) {
		this.eacCost = eacCost;
	}
	
	
}
