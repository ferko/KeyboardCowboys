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
public class TimeSheetApproval implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer approvalId;
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="tsaApprover")
	private Employee tsaApprover;
	@OneToOne(fetch=FetchType.LAZY)
	private TimeSheet timeSheet;

	public Integer getApprovalId() {
		return approvalId;
	}
	public void setApprovalId(Integer approvalId) {
		this.approvalId = approvalId;
	}
	
	public Employee getTsaApprover() {
		return tsaApprover;
	}
	public void setTsaApprover(Employee tsaApprover) {
		this.tsaApprover = tsaApprover;
	}
	public TimeSheet getTimeSheet() {
		return timeSheet;
	}
	public void setTimeSheet(TimeSheet timeSheet) {
		this.timeSheet = timeSheet;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
