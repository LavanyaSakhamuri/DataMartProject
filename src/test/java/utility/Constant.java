/*
 *Description: Control Functions library 
'Author :Sunanda Tirunagari and Ankit Kumar
 */

package utility;

import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
//import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.SessionId;

/*import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;*/

public class Constant {
    //public static final String TestDataFilePath = "NF_WebTool.xlsx";
    public static final String TestDataFilePath = "DataSheet.xlsx";
	public static final String Environment = "SIT";
	public static final String Browser = "Chrome";
//	public static final String PropertiesFilePath = TestDataFilePath;
	public static int SeqID = 1;
	public static int StepIndex = 0;
	public static int TestStepIndex = 0;
	public static int StepStatus = 0;
	public static int TestCaseIndex = 0;
	public static int TestCaseNumber = 0;
	public static int PassedCases = 0;
	public static int FailedCases = 0;
	public static int RowNo = 0;
	public static int lastTestCaseNumber=-1;
	public static boolean atleastOneFailure=false;
	public static boolean testFailed = false;
	public static HashMap<String, HashMap<String, String>> TestData_All = new HashMap<String,HashMap<String,String>>();
	public static HashMap<String, String> TestData1 = new HashMap<String,String>();
	public static HashMap<String, HashMap<String, String>> Map = new HashMap<String,HashMap<String,String>>();
	public static HashMap<String, HashMap<String, String>> Map2 = new HashMap<String,HashMap<String,String>>();
	public static final int defaultBrowserTimeOut = 15;
	public static String UserStoryName = null;
	public static String ResultFilePath = null;
	public static String ScreenshotFolderName = null;
	public static String strScenarioDesc=null;
	public static String strExpectedResult=null;
	public static String strActualResult=null;
	public static String PageName=null;
	public static String locator=null;
	public static String RecentScreenshot=null;
	public static WebDriver driver = null;
//	public static AppiumDriver driver = null;
//	public static AndroidDriver<MobileElement> driver = null;
	public static WebElement webelement;
	public static List<WebElement> webelements;
	public static boolean DefaultoptionalFlag = true;
	public static boolean NF_AddOperationFlag = true;
	
	public static final String path_to_python_scripts="D:\\Backup\\Python_PDF\\Python27_Excel_PDF\\Python27_Excel_PDF\\";
    public static final String Device_Type="PC";
	
	public static URL url = null;
	public static SessionId sid = null;
	
	public static String SheetName = null;
	public static String StepParameters_Flag="n";
	public static String Messagetypes = "";
	public static final String DriverPath = "D:\\chromedriver_win32\\";
	public static String Reward="";
}

 