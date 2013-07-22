/**
 * 
 */
package bcit.ca.infosys.KeyboardCowboys.model;

import java.io.Serializable;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author Andrej
 * @author Ivy
 *
 */
@Entity
public class TimeSheet implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer tsID;
    private Date tsWeekEnding;
    @ManyToOne(fetch=FetchType.LAZY)
    private Employee tsEmp;
    @OneToMany(cascade= CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="trTimesheet")
    private List<TimeRow> tsTimeRows;
	private double epiOvertime;
	private double epiFlex;
    private String status;
    private String approvalNotes = "";
    
    /**
     * @return the tsID
     */
    public Integer getTsID() {
        return tsID;
    }
    /**
     * @param tsID the tsID to set
     */
    public void setTsID(Integer tsID) {
        this.tsID = tsID;
    }
    /**
     * @return the tsWeekEnding
     */
    public Date getTsWeekEnding() {
        return tsWeekEnding;
    }
    /**
     * @param tsWeekEnding the tsWeekEnding to set
     */
    public void setTsWeekEnding(Date tsWeekEnding) {
		Calendar calendar = new GregorianCalendar();
		calendar.setTime(tsWeekEnding);
		calendar.set(Calendar.DAY_OF_WEEK, 6);
        this.tsWeekEnding = calendar.getTime();
    }
    /**
     * @return the tsEmp
     */
    public Employee getTsEmp() {
        return tsEmp;
    }
    /**
     * @param tsEmp the tsEmp to set
     */
    public void setTsEmp(Employee tsEmp) {
        this.tsEmp = tsEmp;
    }
    /**
     * @return the tsTimeRows
     */
    public List<TimeRow> getTsTimeRows() {
        return tsTimeRows;
    }
    /**
     * @param tsTimeRows the tsTimeRows to set
     */
    public void setTsTimeRows(List<TimeRow> tsTimeRows) {
        this.tsTimeRows = tsTimeRows;
    }
	public double getEpiOvertime() {
		return epiOvertime;
	}
	public void setEpiOvertime(double epiOvertime) {
		this.epiOvertime = epiOvertime;
	}
	public double getEpiFlex() {
		return epiFlex;
	}
	public void setEpiFlex(double epiFlex) {
		this.epiFlex = epiFlex;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApprovalNotes() {
		return approvalNotes;
	}
	public void setApprovalNotes(String approvalNotes) {
		this.approvalNotes = approvalNotes;
	}
	
    /**
     * Returns grand total of hours worked. Adds totals of all rows.
     * 
     * @author Andrej K
     * @return result that is the grand total of all rows totals.
     */
    public double getTotal() {
        double result = 0;
        for (TimeRow tr : this.getTsTimeRows()) {
            result += tr.getTotal();
        }
        return result;
    }

    /**
     * Returns totals for each day of the row.
     * 
     * @author Andrej K
     * @return result that is the total of each day of the week.
     * @param day is the day of the week passed in from view.
     */
    public double getTotalForDay(int day) {
        double result = 0;

        for (TimeRow row : this.getTsTimeRows()) {
            switch (day) {
            case 0:
                result += row.getTrSat();
                break;
            case 1:
                result += row.getTrSun();
                break;
            case 2:
                result += row.getTrMon();
                break;
            case 3:
                result += row.getTrTue();
                break;
            case 4:
                result += row.getTrWed();
                break;
            case 5:
                result += row.getTrThu();
                break;
            case 6:
                result += row.getTrFri();
            }
        }
        return result;
    }
}
