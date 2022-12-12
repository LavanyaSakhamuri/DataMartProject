package Gomo;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import utility.Constant;
import utility.Control;
import utility.Custom_Functions;
import utility.Generic;

public class NonUAM {
	
	
	@BeforeSuite
	public static void start() throws Exception 
	{
		Generic.TestScriptStart("DataMart");
		Control.OpenApplication(Constant.Browser , Generic.ReadFromExcel("Url","AI_TestData",1));
		Thread.sleep(5000);
	}
	
	@AfterSuite
	public static void End() throws Exception 
	{
		
		Control.GeneratePDFReport();
		Constant.driver.quit();
		
	}

	@Test (priority=34,enabled=false)	
	public static void Deafault_Status() throws Exception
	{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LogIn("DM_Admin","DM_Pwd");
		Control.click("LoginPage", "Second_account");
		Generic.TestScriptEnds();
		Thread.sleep(15000);
		Generic.WriteTestCase("For Published Script-Filter Function-DEFAULT Request Status filter ", "For Published Script-Filter Function-DEFAULT Request Status filter ", "ExpectedResult", "ActualResult");
		Custom_Functions.DEFAULT_Status_filter();
		Generic.TestScriptEnds();
	}
	
	@Test (priority=35,enabled=false)	
	public static void Filter_Fun() throws Exception
	{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LogIn("email", "password");
		Control.click("LoginPage", "Second_account");
		Generic.TestScriptEnds();
		Generic.WriteTestCase("For Published Script- Filter Function", "For Published Script- Filter Function", "ExpectedResult", "ActualResult");
		Custom_Functions.Filter_Function();
		Generic.TestScriptEnds();
	}
	
	
	@Test (priority=36,enabled=false)	
	public static void NewPublish() throws Exception
	{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LogIn("DM_Admin", "DM_Pwd");
		Control.click("LoginPage", "First_account");
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Approver View Actual Script", "Approver View Actual Script", "ExpectedResult", "ActualResult");
		Custom_Functions.actualscript_newpublish();
		Generic.TestScriptEnds();
	}
	
	@Test (priority=37,enabled=false)	
	public static void UpdateNewPublish() throws Exception
	{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LogIn("DM_Admin", "DM_Pwd");
		Control.takeScreenshot();
		Control.click("LoginPage", "First_account");
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Approver View Actual Script- Update Script for Publish ", "Approver View Actual Script- Update Script for Publish ", "ExpectedResult", "ActualResult");
		Custom_Functions.updatenewpublish();
		Generic.TestScriptEnds();
	}
	
	
	@Test (priority=38,enabled=false)	
	public static void RollBack() throws Exception
	{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LogIn("DM_Admin", "DM_Pwd");
		Control.takeScreenshot();
		Control.click("LoginPage", "First_account");
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Approver View Actual Script- RollBack", "Approver View Actual Script- RollBack", "ExpectedResult", "ActualResult");
		Custom_Functions.actualscript_rollback();
		Generic.TestScriptEnds();
	}
	
	@Test (priority=39,enabled=false)	
	public static void Newpublish_Multiple() throws Exception
	{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LogIn("DM_Admin", "DM_Pwd");
		Control.takeScreenshot();
		Control.click("LoginPage", "First_account");
		Generic.TestScriptEnds();
		Generic.WriteTestCase(" New Script Publish with Multiple attached file ", " New Script Publish with Multiple attached file ", "ExpectedResult", "ActualResult");
		Custom_Functions.actualscript_newscriptMultidownload();
		Generic.TestScriptEnds();
	}
	
	@Test (priority=40,enabled=false)	
	public static void Newpublish_Single() throws Exception
	{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LogIn("DM_Admin", "DM_Pwd");
		Control.takeScreenshot();
		Control.click("LoginPage", "First_account");
		Generic.TestScriptEnds();
		Generic.WriteTestCase(" New Script Publish with one file attached ", " New Script Publish with one file attached ", "ExpectedResult", "ActualResult");
		Custom_Functions.actualscript_newscriptSingledownload();
		Generic.TestScriptEnds();
	}
	
	@Test (priority=41,enabled=false)	
	public static void Updatescript_multiple() throws Exception
	{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LogIn("DM_Admin", "DM_Pwd");
		Control.takeScreenshot();
		Control.click("LoginPage", "First_account");
		Generic.TestScriptEnds();
		Generic.WriteTestCase(" New Script Publish with one file attached ", " New Script Publish with one file attached ", "ExpectedResult", "ActualResult");
		Custom_Functions.UpdatecriptMultipledownload();
		Generic.TestScriptEnds();
	}
	
	
	@Test (priority=42,enabled=false)	
	public static void Updatescript_Single() throws Exception
	{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LogIn("DM_Admin", "DM_Pwd");
		Control.takeScreenshot();
		Control.click("LoginPage", "First_account");
		Generic.TestScriptEnds();
		Generic.WriteTestCase(" New Script Publish with one file attached ", " New Script Publish with one file attached ", "ExpectedResult", "ActualResult");
		Custom_Functions.UpdatecriptSingledownload();
		Generic.TestScriptEnds();
	}
	
	
	@Test (priority=49,enabled=false)	
	public static void Profile() throws Exception
	{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LogIn("DM_Admin", "DM_Pwd");
		Control.takeScreenshot();
		Control.click("LoginPage", "First_account");
		Thread.sleep(20000);
		Generic.TestScriptEnds();
		Generic.WriteTestCase("View reference documents under user profile as a link out", "View reference documents under user profile as a link out", "ExpectedResult", "ActualResult");
		Custom_Functions.Profile();
		Generic.TestScriptEnds();
	}	
	
	@Test (priority=51,enabled=false)	
	public static void NonProd_Datamart_header() throws Exception
	{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LogIn("DM_Admin", "DM_Pwd");
		Control.takeScreenshot();
		Control.click("LoginPage", "First_account");
		Thread.sleep(20000);
		Generic.TestScriptEnds();
		Generic.WriteTestCase("[Non-Prod ]Datamart app header-Datamart Admin", "[Non-Prod ]Datamart app header-Datamart Admin", "ExpectedResult", "ActualResult");
		Thread.sleep(8000);
		Control.hover("Profile", "Datamart_Header");
		Control.takeScreenshot();
		Generic.TestScriptEnds();
	}
	
	@Test (priority=52,enabled=false)	
	public static void Prod_Datamart_header() throws Exception
	{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("[Prod ]Datamart app header-Datamart Admin", "[Prod ]Datamart app header-Datamart Admin", "ExpectedResult", "ActualResult");
		Control.hover("Profile", "Datamart_Header");
		Control.takeScreenshot();
		Generic.TestScriptEnds();
	}
	
	@Test (priority=53,enabled=false)	
	public static void Newscriptemail() throws Exception
	{
		Generic.WriteTestCase("Email Notifications to Requestor- Reject New Script Publish ", "Email Notifications to Requestor- Reject New Script Publish ", "ExpectedResult", "ActualResult");
		Custom_Functions.Mail_RejectNewScriptPublish();
		Generic.TestScriptEnds();
	}
	
	@Test (priority=54,enabled=false)	
	public static void Updatescriptemail() throws Exception
	{
		Generic.WriteTestCase("Email Notifications to Requestor- Reject update Script Publish ", "Email Notifications to Requestor- Reject Update Script Publish ", "ExpectedResult", "ActualResult");
		Custom_Functions.Mail_RejectUpdateScriptPublish();
		Generic.TestScriptEnds();
	}
	
	@Test (priority=55,enabled=false)	
	public static void Rollbackscriptemail() throws Exception
	{
		Generic.WriteTestCase("Email Notifications to Requestor- Reject update Script Publish ", "Email Notifications to Requestor- Reject Update Script Publish ", "ExpectedResult", "ActualResult");
		Custom_Functions.Mail_RejectRollbackScript();
		Generic.TestScriptEnds();
	}
	
	
	
	@Test (priority=56,enabled=false)	
	public static void ETLRequests() throws Exception
	{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LogIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Control.click("LoginPage", "First_account");
		Control.click("LoginPage", "Allow_Button");
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Update role permission-ETL Features", "Update role permission-ETL Features", "ExpectedResult", "ActualResult");
		Custom_Functions.ETL_Features();
		Generic.TestScriptEnds();
	}
	
	@Test (priority=57,enabled=false)	
	public static void ChangeStatus() throws Exception
	{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LogIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Control.click("LoginPage", "First_account");
		Control.click("LoginPage", "Allow_Button");
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Datamart Admin changes the status - started", "Datamart Admin changes the status - started", "ExpectedResult", "ActualResult");
		Custom_Functions.ChangeStatus();
		Generic.TestScriptEnds();
	}
	
	@Test (priority=58,enabled=false)	
	public static void ChangeStatus_Completed() throws Exception
	{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LogIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Control.click("LoginPage", "First_account");
		Control.click("LoginPage", "Allow_Button");
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Datamart Admin changes the status - completed", "Datamart Admin changes the status - completed", "ExpectedResult", "ActualResult");
		Custom_Functions.ChangeStatus_Completed();
		Generic.TestScriptEnds();
	}
	
	@Test (priority=59,enabled=true)	
	public static void ChangeStatus_Cannotpublish() throws Exception
	{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LogIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Control.click("LoginPage", "First_account");
		Control.click("LoginPage", "Allow_Button");
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Datamart Admin changes the status - completed", "Datamart Admin changes the status - completed", "ExpectedResult", "ActualResult");
		Custom_Functions.ChangeStatus_CannotPublish();
		Generic.TestScriptEnds();
	}
	
	
	
	

	}

