package bcit.ca.infosys.KeyboardCowboys.rest;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import bcit.ca.infosys.KeyboardCowboys.data.PrincipleAccess;
import bcit.ca.infosys.KeyboardCowboys.interfaces.PrincipleAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.util.SHAHash;

/**
 * Validation of username/password in restful way using JSON.
 * Main purpose: Android application
 * 
 * @author Andrej Koudlai
 *
 */

@Path("/android")
@RequestScoped
public class LoginValidationService {
    
    @Inject private PrincipleAccessInterface prAccess;
    @GET
    @Path("/login")
    @Produces(MediaType.APPLICATION_JSON)
    public String validateUser(@QueryParam("ID") String empID, @QueryParam("PWD") String empPWD){
        String result = "Login Failed";
        if(prAccess.getHSHPwd(empID).equals(SHAHash.hash(empPWD))){
            result = "Hello";
        }
        return result;
    }
}
