package bcit.ca.infosys.KeyboardCowboys.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Local;
import javax.ejb.Stateful;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import bcit.ca.infosys.KeyboardCowboys.interfaces.LoginControllerInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.PayLevelAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.ResBudgetInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.WorkPackageAccessInterface;
import bcit.ca.infosys.KeyboardCowboys.interfaces.WorkPackageRegistrationInterface;
import bcit.ca.infosys.KeyboardCowboys.model.PayLevel;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;
import bcit.ca.infosys.KeyboardCowboys.model.WorkPackageBudget;
import bcit.ca.infosys.KeyboardCowboys.qualifiers.GetRes;

@Named("ResBudget")
@Local(ResBudgetInterface.class)
@ConversationScoped
@Stateful
public class ResBudget implements Serializable, ResBudgetInterface {

	@Inject
	private Logger log;
	@Inject
	private PayLevelAccessInterface payLevelAccess;
	@Inject 
	private Conversation conversation;
	@Inject
	private WorkPackageAccessInterface workPackageAccess;
	@Inject
	private WorkPackageRegistrationInterface workPackageRegistration;
	@Inject
	private LoginControllerInterface login;
	private List<WorkPackage> wps;
	private WorkPackage wp;
	private Boolean submitted = false;
	
	@Produces
	@GetRes
	@Override
	public List<WorkPackage> getWps() {
		if(wps == null)
		{
			wps = workPackageAccess.getWorkPackageByResEngineer(login.getCurrentEmployee());
		}
		return wps;
	}
	@Override
	public void setWps(List<WorkPackage> wps) {
		this.wps = wps;
	}
	@Override
	public void initBudget() {
		if (wp.getResBudget() == null || wp.getResBudget().isEmpty()) {
			List<PayLevel> payLevels = payLevelAccess.getAllPayLevels();
			wp.setResBudget(new ArrayList<WorkPackageBudget>());
			for (PayLevel payLevel : payLevels) {
				WorkPackageBudget budget = new WorkPackageBudget();
				budget.setPayLevel(payLevel);
				budget.setResWorkPackage(wp);
				wp.getResBudget().add(budget);
			}
		}
	}
	@Override
	public WorkPackage getWp() {
		return wp;
	}
	@Override
	public void setWp(WorkPackage wp) {
		this.wp = wp;
		initBudget();
	}
	@Override
	public String saveBudget()
	{
		log.info("saveingBudget");
		workPackageRegistration.mergeWorkPackage(wp);
		FacesMessage msg = new FacesMessage();
		msg.setSeverity(FacesMessage.SEVERITY_INFO);
		msg.setSummary("Successfully created Budget.");
		submitted = true;
		endConversation();
		FacesContext.getCurrentInstance().addMessage("formCreateWP", msg);
		return "null";
	}
	@Override
	public void startConversation()
	{
		if(conversation.isTransient())
		{
			conversation.begin();
		}
	}
	
	@Override
	public void endConversation()
	{
		if(!conversation.isTransient())
		{
			conversation.end();
		}
	}
	@Override
	public Boolean getSubmitted()
	{
		return submitted;
	}
}
