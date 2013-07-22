package bcit.ca.infosys.KeyboardCowboys.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author
 *
 */

public class WeeklyReport implements Serializable {
    
    private Project wrProject;
    private Map<Employee, List<WeeklyReportRow>> empMap;
    private Map<WorkPackage, List<WeeklyReportRow>> wpMap;
    private Date wrDate;
    private int wrWeek;
    
    
    
    public Project getWrProject() {
        return wrProject;
    }
    public void setWrProject(Project wrProject) {
        this.wrProject = wrProject;
    }
    public int getWrWeek() {
        return wrWeek;
    }
    public void setWrWeek(int wrWeek) {
        this.wrWeek = wrWeek;
    }
    public Date getWrDate() {
        return wrDate;
    }
    public void setWrDate(Date wrDate) {
        this.wrDate = wrDate;
    }
	public Map<Employee, List<WeeklyReportRow>> getEmpMap() {
		return empMap;
	}
	public void setEmpMap(Map<Employee, List<WeeklyReportRow>> empMap) {
		this.empMap = empMap;
	}
	public Map<WorkPackage, List<WeeklyReportRow>> getWpMap() {
		return wpMap;
	}
	public void setWpMap(Map<WorkPackage, List<WeeklyReportRow>> wpMap) {
		this.wpMap = wpMap;
	}

}
