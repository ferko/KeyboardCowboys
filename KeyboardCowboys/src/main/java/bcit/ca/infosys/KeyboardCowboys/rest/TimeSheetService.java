package bcit.ca.infosys.KeyboardCowboys.rest;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Logger;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import bcit.ca.infosys.KeyboardCowboys.interfaces.EmployeeAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.TimeSheetAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.model.Employee;
import bcit.ca.infosys.KeyboardCowboys.model.TimeSheetDTO;
import bcit.ca.infosys.KeyboardCowboys.model.User;
import bcit.ca.infosys.KeyboardCowboys.util.SHAHash;

/**
 * 
 * 
 * @author Andrej Koudlai
 * 
 */
@Path("/kk")
@RequestScoped
public class TimeSheetService implements Serializable {

	// http://www.keyboardcowboys.ca/KeyboardCowboys/rest/kk/timesheet?EmployeeID=30&Date=2013-03-22
	@Inject
	private TimeSheetAccessInterface tsAccess;

	@Inject
	private EmployeeAccessInterface employeeAccess;

	@Inject 
	private Logger log;

	private Employee emp;
	
	@POST
	@Path("/timesheet")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TimeSheetDTO getTimeSheet(
			User user, @Context HttpServletRequest servletRequest) {
		SimpleDateFormat sdf = new SimpleDateFormat("y-M-d");
		TimeSheetDTO temp = null;
		emp = employeeAccess.getEmployeeByUserName(user.getUserName());
		try {
			temp = new TimeSheetDTO(tsAccess.getTimeSheetByDate(sdf.parse(user.getDate()),
					emp.getEmpID()));
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return temp;
	}

	@POST
	@Path("/login")
	@Consumes(MediaType.APPLICATION_JSON)
	public Response addUser(User user, @Context HttpServletRequest servletRequest) {
		String result = "vvv";
		log.info("before request");
//		request = (HttpServletRequest) FacesContext.getCurrentInstance()
//				.getExternalContext().getRequest();
		try {
			log.info("Loggining user");
			servletRequest.login(user.getUserName(), SHAHash.hash(user.getPassword()));
			
			log.info("getting user");
			emp = employeeAccess.getEmployeeByUserName(user.getUserName());
	//		request.setAttribute("employee", employee);
			result = "" + emp.getEmpFName() + " " + emp.getEmpLName();
		} catch (ServletException e) {
		}
		return Response.status(201).entity(result).build();
	}
}
