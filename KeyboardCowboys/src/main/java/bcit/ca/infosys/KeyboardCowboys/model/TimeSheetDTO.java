package bcit.ca.infosys.KeyboardCowboys.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class TimeSheetDTO implements Serializable{

    /**
     * POJO that is required to be able to return a restful object avoiding
     * dependencies.
     * 
     * @author Andrej Koudlai
     */
    private static final long serialVersionUID = 1L;
    private Integer tsID;
    private String tsWeekEnding;
    private Integer tsEmpID;
    private List<TimeRowDTO> tsTimeRowsDTO;
    private double epiOvertime;
    private double epiFlex;
    private String status;
    private String approvalNotes = "";

    public TimeSheetDTO(TimeSheet t){
        tsTimeRowsDTO = new ArrayList<TimeRowDTO>();
        this.tsID = t.getTsID();
        this.tsWeekEnding = t.getTsWeekEnding().toString();
        this.setTsEmpID(t.getTsEmp().getEmpID());
        for(TimeRow tr : t.getTsTimeRows()){
            this.tsTimeRowsDTO.add(new TimeRowDTO(tr));
        }
        this.epiOvertime = t.getEpiOvertime();
        this.epiFlex = t.getEpiFlex();
        this.status = t.getStatus();
        this.approvalNotes = t.getApprovalNotes();
    }

    /**
     * @return the tsID
     */
    public Integer getTsID() {
        return tsID;
    }

    /**
     * @param tsID
     *            the tsID to set
     */
    public void setTsID(Integer tsID) {
        this.tsID = tsID;
    }

    /**
     * @return the tsWeekEnding
     */
    public String getTsWeekEnding() {
        return tsWeekEnding;
    }

    /**
     * @param tsWeekEnding
     *            the tsWeekEnding to set
     */
    public void setTsWeekEnding(String tsWeekEnding) {
        this.tsWeekEnding = tsWeekEnding;
    }

    /**
     * @return the tsTimeRows
     */
    public List<TimeRowDTO> getTsTimeRows() {
        return tsTimeRowsDTO;
    }

    /**
     * @param tsTimeRows
     *            the tsTimeRows to set
     */
    public void setTsTimeRows(List<TimeRowDTO> tsTimeRows) {
        this.tsTimeRowsDTO = tsTimeRows;
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

	public Integer getTsEmpID() {
		return tsEmpID;
	}

	public void setTsEmpID(Integer tsEmpID) {
		this.tsEmpID = tsEmpID;
	}

}
