package bcit.ca.infosys.KeyboardCowboys.data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import bcit.ca.infosys.KeyboardCowboys.interfaces.TimeSheetAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.TimeRow;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheet;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.CurrentEmployee;

/**
 * Time Sheet access that retrieves data from TimeSheet entity
 * 
 * @author Andrej Koudlai
 * 
 */

@Local(TimeSheetAccessInterface.class)
@Stateless
public class TimeSheetAccess implements TimeSheetAccessInterface {
    /**
     * Entity Manager used to access the database
     */
    @Inject
    private EntityManager em;
    /**
     * Logger which is used to print events to the log
     */
    @Inject
    private Logger log;
    
    public List<TimeSheet> getAllTimeSheetsByEmp(Employee currentEmp) {

        TypedQuery<TimeSheet> query = em.createQuery(
                "SELECT DISTINCT t FROM TimeSheet t WHERE t.tsEmp = :emp",
                TimeSheet.class);
        query.setParameter("emp", currentEmp);
        List<TimeSheet> timeSheets = query.getResultList();
        return timeSheets;
    }

    public TimeSheet getTimeSheetByID(int tsID) {
        TypedQuery<TimeSheet> query = em
                .createQuery(
                        "SELECT DISTINCT t FROM TimeSheet t JOIN FETCH t.tsTimeRows r JOIN FETCH r.trProject JOIN FETCH r.trWorkPackage WHERE t.tsID = :id",
                        TimeSheet.class);
        query.setParameter("id", tsID);
        System.out.println("#### Executing Query ####");
        TimeSheet timesheet = query.getSingleResult();
        System.out.println("TS ID: " + timesheet.getTsID());
        System.out.println("Project ID: "
                + timesheet.getTsTimeRows().get(0).getTrProject().getProjID());
        System.out
                .println("WP ID: "
                        + timesheet.getTsTimeRows().get(0).getTrWorkPackage()
                                .getWpID());

        return timesheet;
    }

    @Override
    public List<TimeSheet> getRejectedTimeSheets(Employee currentEmployee)
    {
    	return em.createQuery("SELECT DISTINCT t FROM TimeSheet t WHERE t.status = 'Rejected' AND t.tsEmp = :emp", TimeSheet.class)
    			.setParameter("emp", currentEmployee).getResultList();
    }
    @Override
    public List<TimeSheet> getAllTimeSheets() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public TimeSheet getTimeSheetByDate(Date tsDate, Integer employeeID) {
        TimeSheet timeSheet;
        TypedQuery<TimeSheet> query = em
                .createQuery(
                        "SELECT DISTINCT t FROM TimeSheet t JOIN FETCH t.tsTimeRows r JOIN FETCH t.tsEmp JOIN FETCH r.trProject JOIN FETCH r.trWorkPackage WHERE t.tsWeekEnding = :date AND t.tsEmp.empID = :employeeID",
                        TimeSheet.class);
        query.setParameter("date", tsDate);
        query.setParameter("employeeID", employeeID);
        System.out.println("#### Executing Query ####");
        List<TimeSheet> timesheets = query.getResultList();
        if (!timesheets.isEmpty()) {
            return timesheets.get(0);
        } else {
            timeSheet = new TimeSheet();
            Employee employee = em.find(Employee.class, employeeID);
            timeSheet.setTsEmp(employee);
            timeSheet.setTsWeekEnding(tsDate);
            timeSheet.setStatus("Not Saved");
            timeSheet.setTsTimeRows(new ArrayList<TimeRow>());
            if (!employee.getWorkPackages().isEmpty()) {
                for (WorkPackage wp : employee.getWorkPackages()) {
                    TimeRow tr = new TimeRow();
                    tr.setTrWorkPackage(wp);
                    log.info(tr.getTrWorkPackage().getWpName());
                    tr.setTrProject(wp.getWpProject());
                    log.info(tr.getTrProject().getProjName());
                    tr.setTrTimesheet(timeSheet);
                    timeSheet.getTsTimeRows().add(tr);
                }
            }
        }
        /*
         * System.out.println("TS ID: " + timesheet.getTsID());
         * System.out.println("Project ID: " +
         * timesheet.getTsTimeRows().get(0).getTrProject().getProjID());
         * System.out .println("WP ID: " +
         * timesheet.getTsTimeRows().get(0).getTrWorkPackage() .getWpID());
         */

        return timeSheet;
    }
}
