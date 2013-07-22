package bcit.ca.infosys.KeyboardCowboys.test.ui;

import com.thoughtworks.selenium.DefaultSelenium;

/**
 * Store arquillian/selenium scripts used frequently in test cases.
 * There must be a better way to reuse arquillian code.
 * 
 * @author Vukasin Simic
 *
 */
public abstract class TestSteps {
    
    /**
     * Login as specified user.
     * 
     * @param username to login as.
     * @param password to login with.
     * @param selenium see class description.
     */
    public static void loginAs(final String username, final String password,
            final DefaultSelenium selenium) {
        selenium.type("id=login_form:username", username);
        selenium.type("id=login_form:password", password);
        selenium.click("id=login_form:login_button");
        // Used to slow down selenium so that it has enough time for the page to load
        if(!selenium.isElementPresent("id=login_form:username"));
    }
    
    /**
     * 
     * Fillout and submit Create Employee form.
     * 
     * @param fullname Employee name
     * @param username Employee username
     * @param password Employee password
     * @param confirmPassword Password confirmation
     * @param roleIndex Active[0]/NotActive[1]/Supervisor[2]/HR[3]
     * @param selenium see class description
     */
    public static void createNewEmployee(final String firstName,
            final String lastName, final String username,
            final String password, final String confirmPassword,
            final int roleIndex, final String supervisorName, final String approverName,
            final DefaultSelenium selenium) {
        //
        selenium.type("id=createEmp:empFirstName", firstName);
        selenium.type("id=createEmp:empLastName", lastName);
        selenium.type("id=createEmp:empUserName", username);
        selenium.type("id=createEmp:empPassword", password);
        selenium.type("id=createEmp:empPasswordMatch", confirmPassword);
        
        //Select supervisor
        if(supervisorName != null){
            selenium.click("//div[@id='createEmp:empSuperVisor']"); //Open supervisor dropdown
            selenium.click("//div[@id='createEmp:empSuperVisor_panel']/div/ul/li[text()='"+ supervisorName +"']");
        }
        
        //Select Approver
        if(approverName != null){
            selenium.click("//div[@id='createEmp:empTimeSheetApprover']"); //Open approver dropdown
            selenium.click("//div[@id='createEmp:empTimeSheetApprover_panel']/div/ul/li[text()='"+ approverName +"']");
        }
        
        
        
        //Select payLevel?
        //selenium.click("//div[@id='createEmp:empPLevel']/div[2]"); //Open payLevel dropdown
        //selenium.click("//div[@id='createEmp:empPLevel_panel']/div/ul/li[2]"); //select 1st option(P1)
//        selenium.click("//div[@id='createEmp:empPLevel_panel']/div/ul/li[3]");

        //TODO Bug, selecting role clears supervisor
        selenium.click("id=createEmp:empRole:" + roleIndex); //select Active role, first option
        
        selenium.click("id=createEmp:btnSaveEmp");
    }
    
    /**
     * 
     * Fillout and submit Create Project form.
     * 
     * @param id Project ID
     * @param name Project name
     * @param cost Projected project cost
     * @param dateStart Project start date
     * @param dateEnd Projected project end date
     * @param description Project description
     * @param selenium see class description
     */
    public static void createNewProject(final String id, final String name,
            final String cost, final String dateStart, final String dateEnd,
            final String description, final DefaultSelenium selenium) {
        // Create Project Link
        selenium.click("id=navForm:urlCreateProj");
        selenium.waitForPageToLoad("30000");

        // Fill in Project ID
        selenium.type("id=formCreateProj:projID", id);
        // Fill in Project Name
        selenium.type("id=formCreateProj:projName", name);
        // Fill in Coast Estimate
        selenium.type("id=formCreateProj:projCostEstimate", cost);
        
        //Type in start date, format: dd/MM/yy. Calendar is for convenience
        selenium.type("id=formCreateProj:projStartDate_input", dateStart);
        //Type in end date, format: dd/MM/yy. Calendar is for convenience
        selenium.type("id=formCreateProj:projEndDate_input", dateEnd);
        
        /*
        selenium.click("id=formCreateProj:projStartDate_input");// Open Calender
        selenium.click("link=23");// Select Start Date
        selenium.click("id=formCreateProj:projEndDate_input");// Open Calender
        selenium.click("link=27");// Select End Date
        */
        
        // Fill in Project Description
        selenium.type("id=formCreateProj:projDescription", description);

        // Click Save Button
        selenium.click("id=formCreateProj:btnSaveProj");
    }

}