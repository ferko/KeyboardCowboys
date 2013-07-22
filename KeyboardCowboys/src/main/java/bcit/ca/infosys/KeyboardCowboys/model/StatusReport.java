package bcit.ca.infosys.KeyboardCowboys.model;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class StatusReport implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer srId;
	private Date srDate;
	private int srWeek;
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private List<WPEmpEstimate> empEstimate;
	private String comments;
	private String workAccomplished;
	private String workPlan;
	private String problemAnticipated;
	private String problemForPeriod;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "wpId")
	private WorkPackage workPackage;
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "pmId")
	private Employee projManager;
	private String status = "UNSAVED";

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "asstId")
	private Employee projAssistant;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "engineersId")
	private Employee wpResEngineer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resAssistantsId")
	private Employee wpAssistant;

	public Integer getSrId() {
		return srId;
	}

	public void setSrId(Integer srId) {
		this.srId = srId;
	}

	public List<WPEmpEstimate> getEmpEstimate() {
		return empEstimate;
	}

	public void setEmpEstimate(List<WPEmpEstimate> empEstimate) {
		this.empEstimate = empEstimate;
	}

	public Date getSrDate() {
		return srDate;
	}

	public void setSrDate(Date srDate) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(srDate);
		calendar.set(Calendar.DAY_OF_WEEK, 6);
		this.srDate = calendar.getTime();
	}

	public int getSrWeek() {
		return srWeek;
	}

	public void setSrWeek(int srWeek) {
		this.srWeek = srWeek;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getWorkAccomplished() {
		return workAccomplished;
	}

	public void setWorkAccomplished(String workAccomplished) {
		this.workAccomplished = workAccomplished;
	}

	public String getWorkPlan() {
		return workPlan;
	}

	public void setWorkPlan(String workPlan) {
		this.workPlan = workPlan;
	}

	public String getProblemAnticipated() {
		return problemAnticipated;
	}

	public void setProblemAnticipated(String problemAnticipated) {
		this.problemAnticipated = problemAnticipated;
	}

	public WorkPackage getWorkPackage() {
		return workPackage;
	}

	public void setWorkPackage(WorkPackage workPackage) {
		this.workPackage = workPackage;
	}

	public Employee getProjManager() {
		return projManager;
	}

	public void setProjManager(Employee projManager) {
		this.projManager = projManager;
	}

	public Employee getProjAssistant() {
		return projAssistant;
	}

	public void setProjAssistant(Employee projAssistant) {
		this.projAssistant = projAssistant;
	}

	public Employee getWpResEngineer() {
		return wpResEngineer;
	}

	public void setWpResEngineer(Employee wpResEngineer) {
		this.wpResEngineer = wpResEngineer;
	}

	public Employee getWpAssistant() {
		return wpAssistant;
	}

	public void setWpAssistant(Employee wpAssistant) {
		this.wpAssistant = wpAssistant;
	}

	public String getProblemForPeriod() {
		return problemForPeriod;
	}

	public void setProblemForPeriod(String problemForPeriod) {
		this.problemForPeriod = problemForPeriod;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public double getTotalTimeSheetSubmission() {
		double total = 0;
		for (WPEmpEstimate est : empEstimate) {
			double temp = 0;
			if (est.getTimeSheetSubmission() != null) {
				temp = est.getTimeSheetSubmission();
				if(temp < 0)
				{
					temp *= -1;
				}
				total += temp;
			}
		}
		return total;
	}

	public double getTotalAC() {
		double total = 0;
		for (WPEmpEstimate est : empEstimate) {
			double temp = 0;
			if (est.getTimeSheetSubmission() != null) {
				temp = (est.getTimeSheetSubmission() * est.getPayWage());
				if(temp < 0)
				{
					temp *= -1;
				}
				total += temp;
			}
		}
		return total;
	}

	public double getTotalEst() {
		double total = 0;
		for (WPEmpEstimate est : empEstimate) {
			total += est.getEstimate();
		}
		return total;
	}

	public double getTotalEstDollers() {
		double total = 0;
		for (WPEmpEstimate est : empEstimate) {
			total += (est.getEstimate() * est.getPayWage());
		}
		return total;
	}

	public double getTotalEstAct() {
		double total = 0;
		total = getTotalTimeSheetSubmission();
		total += getTotalEst();
		return total;
	}

	public double getTotalEstActDol() {
		double total = 0;
		total = getTotalAC();
		total += getTotalEstDollers();
		return total;
	}

	public String getVariance()
	{
		NumberFormat nf = new DecimalFormat("#0.00");
		return nf.format(getTotalTimeSheetSubmission() / workPackage.getTotalBudgetDays() * 100);
	}
	
	public String getCompletion()
	{
		NumberFormat nf = new DecimalFormat("#0.00");
		return nf.format(100 - (getTotalEst() /  workPackage.getTotalBudgetDays() * 100));
	}
	
	public String getVarianceDol()
	{
		NumberFormat nf = new DecimalFormat("#0.00");
		return nf.format(getTotalAC() / workPackage.getTotalBudgetDollers() * 100);
	}
	
}
