package bcit.ca.infosys.KeyboardCowboys.util;

import java.util.Comparator;

import bcit.ca.infosys.KeyboardCowboys.model.Employee;

/**
 * Employee Comparator.
 * 
 * @author Frank, Andrej
 *
 */
public class EmployeeComparer implements Comparator<Employee> {
    
    /**
     * Compare method to be overridden.
     * Compares two employees by first, then if first names are equal - 
     * by last names and returns outcome.
     * 
     * @return negative if first is less, 0 if equal, 1 if first is greater.
     * @param x first employee object to be compared to.
     * @param y second employee object to compare with first.
     */
    @Override
    public int compare(final Employee x, final Employee y) {
        int xy;
        xy = x.getEmpFName().compareTo(y.getEmpFName());
        if(xy == 0){
            xy = x.getEmpLName().compareTo(y.getEmpLName());
        }
        return xy;
    }
}
