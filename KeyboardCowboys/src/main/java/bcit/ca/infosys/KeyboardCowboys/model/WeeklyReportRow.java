package bcit.ca.infosys.KeyboardCowboys.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


/**
 *
 * @author
 *
 */

public class WeeklyReportRow {
    private WorkPackage wrrWorkPackage;
    private Employee wrrEmployee;
    private double currentWeek;
    private double currentMonth;
    private double wrrTotal;


    public WorkPackage getWrrWorkPackage() {
        return wrrWorkPackage;
    }

    public void setWrrWorkPackage(WorkPackage wrrWorkPackage) {
        this.wrrWorkPackage = wrrWorkPackage;
    }

    public Employee getWrrEmployee() {
        return wrrEmployee;
    }

    public void setWrrEmployee(Employee wrrEmployee) {
        this.wrrEmployee = wrrEmployee;
    }

    public double getCurrentWeek() {
        return currentWeek;
    }

    public void setCurrentWeek(double currentWeek) {
        this.currentWeek = currentWeek;
    }

    public double getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(double currentMonth) {
        this.currentMonth = currentMonth;
    }

    public double getWrrTotal() {
        return wrrTotal;
    }

    public void setWrrTotal(double wrrTotal) {
        this.wrrTotal = wrrTotal;
    }
}
