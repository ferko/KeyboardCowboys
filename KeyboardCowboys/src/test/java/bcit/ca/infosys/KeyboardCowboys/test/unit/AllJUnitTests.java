package bcit.ca.infosys.KeyboardCowboys.test.unit;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * JUnit Test Suite class for running all existing JUnit tests.
 */
@RunWith(Suite.class)
@SuiteClasses({ EmployeePayInfoTest.class, EmployeeTest.class,
        MemberTest.class, PayLevelTest.class, PrincipleTest.class,
        ProjectTest.class, StatusReportRowTest.class, StatusReportTest.class,
        TimeRowTest.class, TimeSheetApprovalTest.class, TimeSheetTest.class,
        WorkPackageBudgetTest.class, WorkPackageTest.class,
        WPEmpEstimateTest.class, EmployeeComparerTest.class })
public class AllJUnitTests {

}
