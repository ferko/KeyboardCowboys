/**
 * 
 */
package bcit.ca.infosys.KeyboardCowboys.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * @author Andrej
 * 
 */
@Entity
public class TimeRow implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer trID;
    private double trSun;
    private double trMon;
    private double trTue;
    private double trWed;
    private double trThu;
    private double trFri;
    private double trSat;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TimesheetId")
    private TimeSheet trTimesheet;
    @ManyToOne(fetch = FetchType.LAZY)
    private Project trProject;
    @ManyToOne(fetch = FetchType.LAZY)
    private WorkPackage trWorkPackage;
    private String trNotes;

    /**
     * @return the trID
     */
    public Integer getTrID() {
        return trID;
    }

    /**
     * @param trID
     *            the trID to set
     */
    public void setTrID(Integer trID) {
        this.trID = trID;
    }

    /**
     * @return the trSun
     */
    public double getTrSun() {
        return trSun;
    }

    /**
     * @param trSun
     *            the trSun to set
     */
    public void setTrSun(double trSun) {
        this.trSun = trSun;
    }

    /**
     * @return the trMon
     */
    public double getTrMon() {
        return trMon;
    }

    /**
     * @param trMon
     *            the trMon to set
     */
    public void setTrMon(double trMon) {
        this.trMon = trMon;
    }

    /**
     * @return the trTue
     */
    public double getTrTue() {
        return trTue;
    }

    /**
     * @param trTue
     *            the trTue to set
     */
    public void setTrTue(double trTue) {
        this.trTue = trTue;
    }

    /**
     * @return the trWed
     */
    public double getTrWed() {
        return trWed;
    }

    /**
     * @param trWed
     *            the trWed to set
     */
    public void setTrWed(double trWed) {
        this.trWed = trWed;
    }

    /**
     * @return the trThu
     */
    public double getTrThu() {
        return trThu;
    }

    /**
     * @param trThu
     *            the trThu to set
     */
    public void setTrThu(double trThu) {
        this.trThu = trThu;
    }

    /**
     * @return the trFri
     */
    public double getTrFri() {
        return trFri;
    }

    /**
     * @param trFri
     *            the trFri to set
     */
    public void setTrFri(double trFri) {
        this.trFri = trFri;
    }

    /**
     * @return the trSat
     */
    public double getTrSat() {
        return trSat;
    }

    /**
     * @param trSat
     *            the trSat to set
     */
    public void setTrSat(double trSat) {
        this.trSat = trSat;
    }

    public TimeSheet getTrTimesheet() {
        return trTimesheet;
    }

    public void setTrTimesheet(TimeSheet trTimesheet) {
        this.trTimesheet = trTimesheet;
    }

    /**
     * @return the trWorkPackage
     */
    public WorkPackage getTrWorkPackage() {
        return trWorkPackage;
    }

    /**
     * @param trWorkPackage
     *            the trWorkPackage to set
     */
    public void setTrWorkPackage(WorkPackage trWorkPackage) {
        this.trWorkPackage = trWorkPackage;
    }

    public Project getTrProject() {
        return trProject;
    }

    public void setTrProject(Project trProject) {
        this.trProject = trProject;
    }

    /**
     * @return the trNotes
     */
    public String getTrNotes() {
        return trNotes;
    }

    /**
     * @param trNotes
     *            the trNotes to set
     */
    public void setTrNotes(String trNotes) {
    	System.out.println("SETTING NOTES FOR TIME SHEET");
        this.trNotes = trNotes;
    }

    /**
     * Returns total of the time row sat through fri
     * @return
     */
    public double getTotal() {
        return this.trSat + this.trSun + this.trMon + this.trTue + this.trWed
                + this.trThu + this.trFri;
    }
}