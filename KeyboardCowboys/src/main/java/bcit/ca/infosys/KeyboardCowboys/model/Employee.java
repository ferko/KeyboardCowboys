/**
 * 
 */
package bcit.ca.infosys.KeyboardCowboys.model;

import java.io.Serializable;
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
import javax.persistence.OneToOne;

/**
 * @author Andrej
 *
 */
@Entity
public class Employee implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer empID;
    private String empFName;
    private String empLName;
    private String empUserName;
    private String empRole = "";
    @OneToOne(cascade = CascadeType.ALL, mappedBy="epiEmployee", fetch=FetchType.LAZY)
    private EmployeePayInfo empPayInfo;
    @OneToMany(mappedBy="tsaApprover", fetch=FetchType.LAZY)
    private List<TimeSheetApproval>  tsaApprovals;
    @OneToOne(fetch=FetchType.LAZY)
    @JoinTable(name="TimeSheetApprovers")
    private Employee tsApprover;
    @ManyToMany(mappedBy="projEmployees" ,fetch=FetchType.LAZY)
    private List<Project> projects;
    @ManyToMany(mappedBy = "wpEmployees", fetch=FetchType.LAZY)
    private List<WorkPackage> workPackages;
    @OneToMany(mappedBy = "projManager", fetch=FetchType.LAZY)
    private List<Project> managedProjects;
    @OneToMany(mappedBy = "projAssistant", fetch=FetchType.LAZY)
    private List<Project> assistedProjects;
    @OneToMany(mappedBy = "wpResEngineer", fetch=FetchType.LAZY)
    private List<WorkPackage> wpEngineered;
    @OneToMany(mappedBy = "wpResAssistant", fetch=FetchType.LAZY)
    private List<WorkPackage> wpResAssisted;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="empSuperVisor")
    private Employee empSuperVisor;
    @OneToMany(fetch=FetchType.EAGER, mappedBy="empSuperVisor")
    private List<Employee> superVisees;
    
    /**
     * @return the empID
     */
    public Integer getEmpID() {
        return empID;
    }
    /**
     * @param empID the empID to set
     */
    public void setEmpID(Integer empID) {
        this.empID = empID;
    }
    /**
     * @return the empUName
     */
    public String getEmpUserName() {
        return empUserName;
    }
    /**
     * @param empUName the empUName to set
     */
    public void setEmpUserName(String empUserName) {
        this.empUserName = empUserName;
    }
	public String getEmpRole() {
		return empRole;
	}
	public void setEmpRole(String empRole) {
		this.empRole = empRole;
	}
	public List<TimeSheetApproval> getTsaApprovals() {
		return tsaApprovals;
	}
	public void setTsaApprovals(List<TimeSheetApproval> tsaApprovals) {
		this.tsaApprovals = tsaApprovals;
	}
	public EmployeePayInfo getEmpPayInfo() {
		return empPayInfo;
	}
	public void setEmpPayInfo(EmployeePayInfo empPayInfo) {
		this.empPayInfo = empPayInfo;
	}
	public List<Project> getProjects() {
		return projects;
	}
	public void setProjects(List<Project> projects) {
		this.projects = projects;
	}
	public Employee getTsApprover() {
		return tsApprover;
	}
	public void setTsApprover(Employee tsApprover) {
		this.tsApprover = tsApprover;
	}
	public List<WorkPackage> getWorkPackages() {
		return workPackages;
	}
	public void setWorkPackages(List<WorkPackage> workPackages) {
		this.workPackages = workPackages;
	}
	public List<Project> getManagedProjects() {
		return managedProjects;
	}
	public void setManagedProjects(List<Project> managedProjects) {
		this.managedProjects = managedProjects;
	}
	public List<Project> getAssistedProjects() {
		return assistedProjects;
	}
	public void setAssistedProjects(List<Project> assistedProjects) {
		this.assistedProjects = assistedProjects;
	}
	public List<WorkPackage> getWpEngineered() {
		return wpEngineered;
	}
	public void setWpEngineered(List<WorkPackage> wpEngineered) {
		this.wpEngineered = wpEngineered;
	}
	public List<WorkPackage> getWpResAssisted() {
		return wpResAssisted;
	}
	public void setWpResAssisted(List<WorkPackage> wpResAssisted) {
		this.wpResAssisted = wpResAssisted;
	}
	
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public Employee getEmpSuperVisor() {
		return empSuperVisor;
	}
	public void setEmpSuperVisor(Employee empSuperVisor) {
		this.empSuperVisor = empSuperVisor;
	}
	public List<Employee> getSuperVisees() {
		return superVisees;
	}
	public void setSuperVisees(List<Employee> superVisees) {
		this.superVisees = superVisees;
	}
    /**
     * @return the empFName
     */
    public String getEmpFName() {
        return empFName;
    }
    /**
     * @param empFName the empFName to set
     */
    public void setEmpFName(String empFName) {
        this.empFName = empFName;
    }
    /**
     * @return the empLName
     */
    public String getEmpLName() {
        return empLName;
    }
    /**
     * @param empLName the empLName to set
     */
    public void setEmpLName(String empLName) {
        this.empLName = empLName;
    }
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ("" + empID).hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Employee other = (Employee) obj;
		if (empID != other.getEmpID())
			return false;
		return true;
	}
	
}
