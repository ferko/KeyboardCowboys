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
public class WPEmpEstimate implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "emp_ID")
	private Employee employee;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer estimateID;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wp_ID")
	private StatusReport statusReport;
	private int estimate;
	private double payWage;
	private Double timeSheetSubmission;

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Integer getEstimateID() {
		return estimateID;
	}

	public void setEstimateID(Integer estimateID) {
		this.estimateID = estimateID;
	}

	public int getEstimate() {
		return estimate;
	}

	public void setEstimate(int estimate) {
		this.estimate = estimate;
	}

	public StatusReport getStatusReport() {
		return statusReport;
	}

	public void setStatusReport(StatusReport statusReport) {
		this.statusReport = statusReport;
	}

	public double getPayWage() {
		return payWage;
	}

	public void setPayWage(double payWage) {
		this.payWage = payWage;
	}

	public Double getTimeSheetSubmission() {
		return timeSheetSubmission;
	}

	public void setTimeSheetSubmission(Double timeSheetSubmission) {
		this.timeSheetSubmission = timeSheetSubmission;
	}

	public Double getEac() {
		Double temp = timeSheetSubmission;
		if(temp < 0)
		{
			temp *= -1;
		}
		return temp + (double) estimate;
	}

	public Double getEacDol() {
		Double temp = timeSheetSubmission * payWage;
		if (temp < 0) {
			temp *= -1;
		}
		temp += estimate * payWage;
		return temp;
	}
}
