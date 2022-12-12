package Gomo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import utility.Constant;
import utility.Control;
import utility.Custom_Functions;
import utility.Generic;

public class DataMart {
	

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


 

	@Test (priority=1,enabled=false)	
	public static void LoginTo_DataMart() throws Exception{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Control.hover("HomePage", "UserName");
		Control.customWait("HomePage","UserAccountInfo", 60);
		Control.objExists("HomePage","UserAccountInfo", true);
		Control.customWait("HomePage","UserAccountInformationText", 60);
		Control.objExists("HomePage","UserAccountInformationText", true);
		Control.customWait("HomePage","GroupText", 60);
		Control.objExists("HomePage","GroupText", true);
		Control.customWait("HomePage","NameOfGroup", 60);
		Control.objExists("HomePage","NameOfGroup", true);
		Control.customWait("HomePage","RoleText", 60);
		Control.objExists("HomePage","RoleText", true);
		Control.customWait("HomePage","NameOfRole", 60);
		Control.objExists("HomePage","NameOfRole", true);
		Control.customWait("HomePage","EtlApproverText", 60);
		Control.objExists("HomePage","EtlApproverText", true);
		Control.customWait("HomePage","NameOfEtlApprover", 60);
		Control.objExists("HomePage","NameOfEtlApprover", true);
		Control.customWait("HomePage","UserManualDoc", 60);
		Control.objExists("HomePage","UserManualDoc", true);
		Control.customWait("HomePage","PpmDoc", 60);
		Control.objExists("HomePage","PpmDoc", true);
		Control.customWait("HomePage","Logout", 60);
		Control.objExists("HomePage","Logout", true);
		Control.customWait("HomePage","UserSettings", 60);
		Control.objExists("HomePage","UserSettings", true);
		Control.takeScreenshot();
		Thread.sleep(2000);
		//Control.compareText("HomePage", "UserAccountInfo", Generic.ReadFromExcel("AccountInfo", "Tiles", 1));
		Control.customWait("HomePage","UserSettings", 60);
		Control.click("HomePage", "UserSettings");
		Control.takeScreenshot();
		Control.customWait("HomePage","UserSettingsHeader", 60);
		Control.objExists("HomePage","UserSettingsHeader", true);
		Thread.sleep(5000);
		Control.objExists("HomePage","BackButton", true);
		Control.click("HomePage","BackButton");
		Thread.sleep(2000);
		//actions.moveToElement(Control.findElement("HomePage", "UserName")).perform();
		Control.hover("HomePage", "UserName");
		Control.click("HomePage","Logout");
		Thread.sleep(3000);
		Control.objExists("LoginPage", "LoginWithGoogle", true);
		Generic.TestScriptEnds();
	}
	

	
	@Test (priority=2,enabled=false)	
	public static void System_Configuration() throws Exception{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("System Configuration", "System Configuration Validation", "ExpectedResult", "ActualResult");
		Custom_Functions.SystemConfiguration();
		Generic.TestScriptEnds();

		
	}	
	
	
	@Test (priority=3,enabled=false)	
	public static void UserManagement_Validation() throws Exception{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("User Management Validation", "User Management Validation via Clicking Tile", "ExpectedResult", "ActualResult");
		Custom_Functions.UserManagementValidation();
		Generic.TestScriptEnds();
	}
	
	
	
	@Test (priority=4,enabled=false)	
	public static void Create_Group_OkButton() throws Exception{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Create Group", "Create Group Button", "ExpectedResult", "ActualResult");
		Custom_Functions.CreateGroup();
		Generic.TestScriptEnds();



	}
	
	@Test (priority=5,enabled=false)	
	public static void Create_Group_Xicon() throws Exception{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Create Group Xicon", "Create Group Xicon", "ExpectedResult", "ActualResult");
		Custom_Functions.CreateGroupXicon();
		Generic.TestScriptEnds();
	}
	
	@Test (priority=6,enabled=false)	
	public static void Group_Validation() throws Exception{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Group Validation", "Group Validation", "ExpectedResult", "ActualResult");
		Custom_Functions.GroupValidation();
		Generic.TestScriptEnds();
	}
	
	
	

	@Test (priority=7,enabled=false)	
	public static void Edit_Group() throws Exception{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Edit Group", "Edit Group", "ExpectedResult", "ActualResult");
		Custom_Functions.EditGroup();
		Generic.TestScriptEnds();
	}
	
	

	@Test (priority=8,enabled=false)	
	public static void Edit_Group_Xicon() throws Exception{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Edit Group Xicon", "Edit Group Xicon", "ExpectedResult", "ActualResult");
		Custom_Functions.EditGroupXicon();
		Generic.TestScriptEnds();	
	
	}
	
	

	
	@Test (priority=9,enabled=false)	
	public static void Add_Group_Member_Super_Admin() throws Exception{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Add Group Member As Super Admin", "Add Group Member", "ExpectedResult", "ActualResult");
		Custom_Functions.AddGroupMember();
		Generic.TestScriptEnds();	

	}
	

	@Test (priority=10,enabled=false)	
	public static void Add_Group_Member_Datamart_Admin() throws Exception{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Add Group Member As DataMart Admin", "Add Group Member", "ExpectedResult", "ActualResult");
		Custom_Functions.AddGroupMemberAlreadyRegisterd();
		Generic.TestScriptEnds();	

	}

	@Test (priority=11,enabled=false)
	public static void Group_Search_Function() throws Exception{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Group Search Function", "Group Search Function", "ExpectedResult", "ActualResult");
		Custom_Functions.GroupSearchFunction();
		Generic.TestScriptEnds();	


		
	}
	
	@Test (priority=12,enabled=false)
	public static void Group_Filter_Function() throws Exception{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Group Filter Function", "Group Filter Function", "ExpectedResult", "ActualResult");
		Custom_Functions.GroupFilterFunction();
		Generic.TestScriptEnds();	
		
	}
	
	
	@Test (priority=13,enabled=false)	
	   public static void Add_User_Super_Admin_Not_Registered() throws Exception{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Add User Not Yet Registered", "Add User Not Yet Registered", "ExpectedResult", "ActualResult");
		Custom_Functions.AddUserNotRegistered(Generic.ReadFromExcel("Email", "UserCreation", 1),Generic.ReadFromExcel("Group", "UserCreation", 1),Generic.ReadFromExcel("Role", "UserCreation", 1),Generic.ReadFromExcel("EtlApprover", "UserCreation", 1));
		Generic.TestScriptEnds();			
	}
	
	
	@Test (priority=14,enabled=false)	
	   public static void Add_User_Super_Admin_Already_Registered() throws Exception{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Add User Already Registered", "Add User Already Registered", "ExpectedResult", "ActualResult");
		Custom_Functions.AddUserAlreadyRegistered(Generic.ReadFromExcel("RegisteredEmail", "UserCreation", 1),Generic.ReadFromExcel("Group", "UserCreation", 1),Generic.ReadFromExcel("Role", "UserCreation", 1),Generic.ReadFromExcel("EtlApprover", "UserCreation", 1));
		Generic.TestScriptEnds();			
	}
	
	
	@Test (priority=15,enabled=false)	
	   public static void Create_Bulk_User() throws Exception{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Create Bulk User", "Create Bulk User", "ExpectedResult", "ActualResult");
		Custom_Functions.CreateBulkUser();
		Generic.TestScriptEnds();			
	}
	
	@Test (priority=16,enabled=false)	
	   public static void User_Search_Function() throws Exception{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("User Search Function", "User Search Function", "ExpectedResult", "ActualResult");
		Custom_Functions.UserSearchFunction();
		Generic.TestScriptEnds();			
	}
	
	@Test (priority=17,enabled=false)	
	   public static void User_Filter_Function() throws Exception{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("User Filter Function", "User Filter Function", "ExpectedResult", "ActualResult");
		Custom_Functions.UserFilterFunction();
		Generic.TestScriptEnds();			
	}
	
	@Test (priority=19,enabled=false)	
	   public static void Add_Role_Function() throws Exception{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Add Role Function", "Add Role Function", "ExpectedResult", "ActualResult");
		Custom_Functions.AddRoleFunction(Generic.ReadFromExcel("RoleName", "UserCreation", 1));
		Generic.TestScriptEnds();			
	}
	
	@Test (priority=22,enabled=false)	
	   public static void Edit_Role_Function() throws Exception{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Edit Role Function", "Edit Role Function", "ExpectedResult", "ActualResult");
		Custom_Functions.EditRoleFunction(Generic.ReadFromExcel("UpdateRoleName", "UserCreation", 1));
		Generic.TestScriptEnds();			
	}
	
	@Test (priority=23,enabled=false)	
	   public static void Role_Search_Function() throws Exception{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Role Search  Function", "Role Search Function", "ExpectedResult", "ActualResult");
		Custom_Functions.RoleSearchFunction(Generic.ReadFromExcel("SearchRoleName", "UserCreation", 1),Generic.ReadFromExcel("SearchRoleDescription", "UserCreation", 1));
		Generic.TestScriptEnds();			
	}
	
	@Test (priority=25,enabled=true)	
	public static void PaginationFunction() throws Exception
	{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("Pagination  Function", "Pagination Function", "ExpectedResult", "ActualResult");
		Custom_Functions.PaginationFunction();
		Generic.TestScriptEnds();
	}
	
	@Test (priority=26,enabled=false)
	public static void CreateRoleFunction() throws Exception
	{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("CreateRole Function", "CreateRole Function", "ExpectedResult", "ActualResult");
		Custom_Functions.CreateRole();
		Generic.TestScriptEnds();
	}
	
	@Test (priority=27,enabled=false)
	public static void EditRoleFunction() throws Exception
	{
		Generic.WriteTestCase("Login", "LoginToDataMart", "ExpectedResult", "ActualResult");
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("email", "AI_TestData", 1),Generic.ReadFromExcel("password", "AI_TestData", 1));
		Generic.TestScriptEnds();
		Generic.WriteTestCase("EditRole Function", "EditRole Function", "ExpectedResult", "ActualResult");
		Custom_Functions.EditRole();
		Generic.TestScriptEnds();
	}
	
	


	
	
	
	

	
}
