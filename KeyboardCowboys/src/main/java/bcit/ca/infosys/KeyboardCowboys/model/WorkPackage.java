/**
 * 
 */
package bcit.ca.infosys.KeyboardCowboys.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author Andrej
 * 
 */

@Entity
public class WorkPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dbID;
    private String wpID;
    private String wpName;
    private Date wpStartDate;
    private Date wpEndDate;
    private String wpStatus;
    private String wpDescription;
    @ManyToOne(fetch=FetchType.LAZY)
    private Project wpProject;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="workPackage", cascade= CascadeType.ALL)
    private List<WorkPackageBudget> wpBudgets;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="resWorkPackage", cascade= CascadeType.ALL)
    
    private List<WorkPackageBudget> resBudget;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="wpParent")
    private WorkPackage wpPackage;
    @OneToMany(fetch=FetchType.LAZY, mappedBy="wpPackage")
    private List<WorkPackage> wpPackages;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinTable(name="Engineers")
    private Employee wpResEngineer;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinTable(name="ResAssistants")
    private Employee wpResAssistant;
	@ManyToMany(fetch=FetchType.LAZY)
    private List<Employee> wpEmployees;
    /**
     * @return the wpID
     */
    public String getWpID() {
        return wpID;
    }

    /**
     * @param wpID
     *            the wpID to set
     */
    public void setWpID(String wpID) {
        this.wpID = wpID;
    }

    /**
     * @return the wpName
     */
    public String getWpName() {
        return wpName;
    }

    /**
     * @param wpName
     *            the wpName to set
     */
    public void setWpName(String wpName) {
        this.wpName = wpName;
    }

    /**
     * @return the wpStartDate
     */
    public Date getWpStartDate() {
        return wpStartDate;
    }

    /**
     * @param wpStartDate
     *            the wpStartDate to set
     */
    public void setWpStartDate(Date wpStartDate) {
        this.wpStartDate = wpStartDate;
    }

    /**
     * @return the wpEndDate
     */
    public Date getWpEndDate() {
        return wpEndDate;
    }

    /**
     * @param wpEndDate
     *            the wpEndDate to set
     */
    public void setWpEndDate(Date wpEndDate) {
        this.wpEndDate = wpEndDate;
    }

    /**
     * @return the wpProject
     */
    public Project getWpProject() {
        return wpProject;
    }

    /**
     * @param wpProject
     *            the wpProject to set
     */
    public void setWpProject(Project wpProject) {
        this.wpProject = wpProject;
    }

    /**
     * @return the wpPackages
     */
    public List<WorkPackage> getWpPackages() {
        return wpPackages;
    }

    /**
     * @param wpPackages
     *            the wpPackages to set
     */
    public void setWpPackages(List<WorkPackage> wpPackages) {
        this.wpPackages = wpPackages;
    }

    /**
     * @return the wpEmployees
     */
    public List<Employee> getWpEmployees() {
        return wpEmployees;
    }

    /**
     * @param wpEmployees
     *            the wpEmployees to set
     */
    public void setWpEmployees(List<Employee> wpEmployees) {
        this.wpEmployees = wpEmployees;
    }

    public WorkPackage getWpPackage() {
		return wpPackage;
	}

	public void setWpPackage(WorkPackage wpPackage) {
		this.wpPackage = wpPackage;
	}


	public Employee getWpResEngineer() {
		return wpResEngineer;
	}

	public void setWpResEngineer(Employee wpResEngineer) {
		this.wpResEngineer = wpResEngineer;
	}

    /**
     * @return the wpStatus
     */
    public String getWpStatus() {
        return wpStatus;
    }

    /**
     * @param wpStatus the wpStatus to set
     */
    public void setWpStatus(String wpStatus) {
        this.wpStatus = wpStatus;
    }

    /**
     * @return the wpDescription
     */
    public String getWpDescription() {
        return wpDescription;
    }

    /**
     * @param wpDescription the wpDescription to set
     */
    public void setWpDescription(String wpDescription) {
        this.wpDescription = wpDescription;
    }



	public Employee getWpResAssistant() {
		return wpResAssistant;
	}

	public void setWpResAssistant(Employee wpResAssistant) {
		this.wpResAssistant = wpResAssistant;
	}

	public List<WorkPackageBudget> getWpBudgets() {
		return wpBudgets;
	}

	public void setWpBudgets(List<WorkPackageBudget> wpBudgets) {
		this.wpBudgets = wpBudgets;
	}
	
	public double getTotalBudgetDays()
	{
		double total = 0;
		for(WorkPackageBudget wpb : wpBudgets)
		{
			total += wpb.getDays();
		}
		return total;
	}
	
	public double getTotalBudgetDollers()
	{
		double total = 0;
		for(WorkPackageBudget wpb : wpBudgets)
		{
			total += (wpb.getDays() * wpb.getPayLevel().getPlRate());
		}
		return total;
	}
	
	public double getTotalResBudgetDays()
	{
		double total = 0;
		for(WorkPackageBudget wpb : resBudget)
		{
			total += wpb.getDays();
		}
		return total;
	}
	
	public double getTotalResBudgetDollers()
	{
		double total = 0;
		for(WorkPackageBudget wpb : resBudget)
		{
			total += (wpb.getDays() * wpb.getPayLevel().getPlRate());
		}
		return total;
	}

	public Integer getDbID() {
		return dbID;
	}

	public void setDbID(Integer dbID) {
		this.dbID = dbID;
	}

	public List<WorkPackageBudget> getResBudget() {
		return resBudget;
	}

	public void setResBudget(List<WorkPackageBudget> resBudget) {
		this.resBudget = resBudget;
	}
	
}
