package bcit.ca.infosys.KeyboardCowboys.interfaces;

import java.util.List;

import bcit.ca.infosys.KeyboardCowboys.model.WorkPackage;

public interface ResBudgetInterface {

	Boolean getSubmitted();

	void endConversation();

	void startConversation();

	String saveBudget();

	void setWp(WorkPackage wp);

	WorkPackage getWp();

	void initBudget();

	void setWps(List<WorkPackage> wps);

	List<WorkPackage> getWps();

}
