package bcit.ca.infosys.KeyboardCowboys.interfaces;
//package bcit.ca.infosys.KeyboardCowboys.controller;
//
//import java.io.IOException;
//import java.io.Serializable;
//
//import javax.enterprise.context.SessionScoped;
//import javax.enterprise.inject.Produces;
//import javax.faces.application.ConfigurableNavigationHandler;
//import javax.faces.application.FacesMessage;
//import javax.faces.context.FacesContext;
//import javax.faces.event.ComponentSystemEvent;
//import javax.inject.Inject;
//import javax.inject.Named;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//
//import www.jointeams.com.QApp.data.UserAccess;
//import www.jointeams.com.QApp.model.User;
//import www.jointeams.com.QApp.util.SHAHash;
//
//@SessionScoped
//@Named("UserController")
//public class UserController implements Serializable {
//
//	/**
//	 * 
//	 */
//	private static final long serialVersionUID = 1L;
//	private User currentUser = new User();
//	@Inject
//	private UserAccess userAccess;
//
//	
//	@Produces
//	@Named("currentUser")
//	public User getCurrentUser() {
//		return currentUser;
//	}
//	
//	@Produces
//	@Named("isAdmin")
//	public boolean isAdmin()
//	{
//		return (currentUser.getRole().equals("ADMIN"))?true:false;
//	}
//
//	public String logIn() throws ServletException, IOException {
//		FacesMessage msg = new FacesMessage();
//		msg.setSeverity(FacesMessage.SEVERITY_ERROR);
//		
//		
//		
//		HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance( ).getExternalContext( ).getRequest( );        
//	    try {
//	        request.login( currentUser.getUserName(),SHAHash.hash(currentUser.getPassword(), currentUser.getUserName()) );
//	    }
//	    catch ( ServletException e ) {
//	    	msg.setSummary("Incorrect UserName/Password");
//	        FacesContext.getCurrentInstance().addMessage( "unique", msg);
//	        return null;
//	    }     
//	    currentUser = userAccess.findUserByName(currentUser.getUserName());
//	    return "welcome";
//	}
//
//	public String logOut() {
//		getRequest().getSession().invalidate();
//		return "logout";
//	}
//
//	public void logedIn(ComponentSystemEvent event) {
//
//		FacesContext fc = FacesContext.getCurrentInstance();
//		if (currentUser.getRole() != null) {
//			ConfigurableNavigationHandler nav = (ConfigurableNavigationHandler) fc
//					.getApplication().getNavigationHandler();
//
//			nav.performNavigation("welcome");
//		}
//
//	}
//	
//	private HttpServletRequest getRequest() {
//		return (HttpServletRequest) FacesContext.getCurrentInstance()
//				.getExternalContext().getRequest();
//	}
//}
