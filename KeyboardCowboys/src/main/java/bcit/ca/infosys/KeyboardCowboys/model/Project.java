/**
 * 
 */
package bcit.ca.infosys.KeyboardCowboys.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
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
public class Project implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Id of the project
	 */
    @Id
	private String projID;
	/**
	 * Name of the project
	 */
	private String projName;
	/**
	 * Project's manager
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(name="ProjectManagers")
	private Employee projManager;
	
	private String projDescription;
	
	@ManyToMany(fetch=FetchType.LAZY)
	private Set<Employee> projEmployees;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinTable(name="Assistants")
	private Employee projAssistant;
	
	@OneToMany(mappedBy = "wpProject", fetch=FetchType.LAZY)
	private Set<WorkPackage> projWorkPackages;

	private Date projStartDate;
	private Date projEndDate;
	private String projStatus;
	private double projCostEstimate;
	/**
	 * @return the projID
	 */
	public String getProjID() {
		return projID;
	}

	/**
	 * @param projID
	 *            the projID to set
	 */
	public void setProjID(String projID) {
		this.projID = projID;
	}

	/**
	 * @return the projName
	 */
	public String getProjName() {
		return projName;
	}

	/**
	 * @param projName
	 *            the projName to set
	 */
	public void setProjName(String projName) {
		this.projName = projName;
	}

	/**
	 * @return the projManager
	 */
	public Employee getProjManager() {
		return projManager;
	}

	/**
	 * @param projManager
	 *            the projManager to set
	 */
	public void setProjManager(Employee projManager) {
		this.projManager = projManager;
	}

	/**
	 * @return the projWorkPackages
	 */
	public Set<WorkPackage> getProjWorkPackages() {
		return projWorkPackages;
	}

	/**
	 * @param projWorkPackages
	 *            the projWorkPackages to set
	 */
	public void setProjWorkPackages(Set<WorkPackage> projWorkPackages) {
		this.projWorkPackages = projWorkPackages;
	}

	public Set<Employee> getProjEmployees() {
		return projEmployees;
	}

	public void setProjEmployees(Set<Employee> projEmployees) {
		this.projEmployees = projEmployees;
	}

	public Employee getProjAssistant() {
		return projAssistant;
	}

	public void setProjAssistant(Employee projAssistant) {
		this.projAssistant = projAssistant;
	}

	public Date getProjStartDate() {
		return projStartDate;
	}

	public void setProjStartDate(Date projStartDate) {
		this.projStartDate = projStartDate;
	}

	public Date getProjEndDate() {
		return projEndDate;
	}

	public void setProjEndDate(Date projEndDate) {
		this.projEndDate = projEndDate;
	}

	public String getProjStatus() {
		return projStatus;
	}

	public void setProjStatus(String projStatus) {
		this.projStatus = projStatus;
	}

	public double getProjCostEstimate() {
		return projCostEstimate;
	}

	public void setProjCostEstimate(double projCostEstimate) {
		this.projCostEstimate = projCostEstimate;
	}

	public String getProjDescription() {
		return projDescription;
	}

	public void setProjDescription(String projDescription) {
		this.projDescription = projDescription;
	}

	public List<Employee> getProjEmployeesList()
	{
		ArrayList<Employee> temp = new ArrayList<Employee>();
		if(projEmployees != null)
		{
			temp = new ArrayList<Employee>(projEmployees);
		}
		return temp;
	}
	
	public List<WorkPackage> getWorkPackagesList()
	{
		return new ArrayList<WorkPackage>(projWorkPackages);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

}
