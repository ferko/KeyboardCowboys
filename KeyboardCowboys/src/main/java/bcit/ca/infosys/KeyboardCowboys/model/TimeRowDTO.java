package bcit.ca.infosys.KeyboardCowboys.model;

/**
 * POJO that is required to be able to return a restful object avoiding
 * dependencies.
 * 
 * @author Andrej Koudlai
 */

public class TimeRowDTO {
    private Integer trID;
    private double trSun;
    private double trMon;
    private double trTue;
    private double trWed;
    private double trThu;
    private double trFri;
    private double trSat;
    private String trProjectName;
    private String trWorkPackageName;
    private String trNotes;

    public TimeRowDTO(TimeRow tr){
        this.trID = tr.getTrID();
        this.trSun = tr.getTrSun();
        this.trMon = tr.getTrMon();
        this.trTue = tr.getTrTue();
        this.trWed = tr.getTrWed();
        this.trThu = tr.getTrThu();
        this.trFri = tr.getTrFri();
        this.trSat = tr.getTrSat();
        this.trProjectName = tr.getTrProject().getProjName();
        this.trWorkPackageName = tr.getTrWorkPackage().getWpName();
        this.trNotes = tr.getTrNotes();
    }
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
    /**
     * @return the trProjectName
     */
    public String getTrProjectName() {
        return trProjectName;
    }
    /**
     * @param trProjectName the trProjectName to set
     */
    public void setTrProjectName(String trProjectName) {
        this.trProjectName = trProjectName;
    }
    /**
     * @return the trWorkPackageName
     */
    public String getTrWorkPackageName() {
        return trWorkPackageName;
    }
    /**
     * @param trWorkPackageName the trWorkPackageName to set
     */
    public void setTrWorkPackageName(String trWorkPackageName) {
        this.trWorkPackageName = trWorkPackageName;
    }
}
