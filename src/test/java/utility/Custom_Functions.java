package utility;

import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.util.SystemOutLogger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Custom_Functions{

	private static String FlowID=null;
	
	public static String Province,City,Barangay;
	
	//static Actions action = new Actions(Constant.driver);
	
    public static String TimeOut(String PageName, String locatorName, String urltext) throws Exception 
	{
	
	 int ctr=-1;
     
     while(++ctr<3)
     {
           if(Control.findElement(PageName, locatorName)!=null)
           {
                  //Control.objExists(PageName, locatorName,true);
                  Thread.sleep(1000);
                  if(Constant.driver.getCurrentUrl().contains(urltext))
                         break;
           }
           else
           {
                  //Constant.driver.get(URL);
        	   Constant.driver.navigate().refresh();
           }
           
     }
     
     if(ctr==4)
     {
           //fail
           Generic.WriteTestData("Unable to load page", "", "", "Should be able to load page successfully", "Unable to load page successfully", "Fail");
           //return null;
     }
	return null;
	}
	
	
	
	public static void ScrollToView(String PageName, String locatorName) throws Exception
    {
		WebElement element = Control.findElement(PageName, locatorName);
		JavascriptExecutor js = (JavascriptExecutor) Constant.driver;
		js.executeScript("arguments[0].scrollIntoView(true);",element);  
		js.executeScript("window.scrollBy(0,-50)");
    } 
	
    public static void ChooseLead(String LeadName) throws Exception
	{
	try 
	{ 
	   JavascriptExecutor js = (JavascriptExecutor) Constant.driver;
	   js.executeScript("arguments[0].setAttribute('style', arguments[1]);",Constant.driver.findElement(By.xpath("//a[@title='"+LeadName+"']")), "border: 1px solid DeepPink;");
	   Generic.WriteTestData("Selecting Lead","Select on Lead"," ","Selecting Lead should be successful","Selecting Lead is successful","Passed");		  
	   Control.takeScreenshot();
	   Constant.driver.findElement(By.xpath("//a[@title='"+LeadName+"']")) .click();
	   Thread.sleep(1000);
	}
	catch (AssertionError Ae) 
	{
	 Ae.printStackTrace(); 
	} 
	}
    
    
		public static void AddSimToCart() throws Exception{
		Control.click("HomePage", "Shop");
		Control.customWait("ShopPage", "ShopHeader", 60);
		Control.takeScreenshot();
		Control.objExists("ShopPage", "GomoSim", true);
		Control.click("ShopPage", "GomoSim");
		Control.customWait("GomoSimPage", "GomoSimHeader", 60);
		Control.objExists("GomoSimPage", "GomoSimHeader", true);
		Control.takeScreenshot();
		
		Control.click("GomoSimPage", "Sim");
		Thread.sleep(1000);
		Control.takeScreenshot();
		Control.click("GomoSimPage", "AddToCart");
		Control.objExists("GomoSimPage", "AddtoCartNotif", true);
		Control.takeScreenshot();
		
		Control.click("GomoSimPage", "CartIcon");
		Control.customWait("CartPage", "CartHeader", 60);
		Control.objExists("CartPage", "ProductName", true );
		Control.compareText("CartPage", "ProductName", Generic.ReadFromExcel("ProductName", "DawnSpiels", 1));
		Control.compareText("CartPage", "ProductDetail", Generic.ReadFromExcel("ProductInfo", "DawnSpiels", 1));
		Control.compareText("CartPage", "Freebies", Generic.ReadFromExcel("ProductFreebie", "DawnSpiels", 1));
		Control.compareText("CartPage", "ProductPrice", Generic.ReadFromExcel("ProductPrice", "DawnSpiels", 1));
		
		Control.click("CartPage", "SelectAll");
		Thread.sleep(2000);
		Control.compareText("CartPage", "CheckoutPrice", Generic.ReadFromExcel("ProductPrice", "DawnSpiels", 1));
		
	}
	
	
	public static void SimPurchaseGCash2() throws Exception{
		
		GetSim2();
		SelecQuantity();
		Control.click("GetSimPage", "AddAddressButton");
		Thread.sleep(2000);
		Control.objExists("GetSimPage", "ManageAddress", true);
		Control.objExists("GetSimPage", "ConfirmAddress", true);
		Control.click("GetSimPage", "ConfirmAddress");
		Control.customWait("GetSimPage", "SimOrderTitle", 60);
		Control.takeScreenshot();
		PaymentSection();
		Control.objExists("GetSimPage", "IAgreeCheckbox", true);
		Control.objExists("GetSimPage", "PayButton", true);
		Control.click("GetSimPage", "IAgreeCheckbox");
		Thread.sleep(3000);
		ScrollToView("GetSimPage", "PayButton");
		try
		{
			Control.click("GetSimPage", "PayButton");
		}
		catch(Exception e)
		{
			try
			{
				Control.click("GetSimPage", "PayButton");
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		Thread.sleep(2000);
		OrderConfirmation();
		GCashPayment();
		PaymentSuccessPage();
	}
	
	public static void SimPurchaseCC() throws Exception{
			
			Control.OpenApplication(Constant.Browser , Generic.ReadFromExcel("Url","AI_TestData",1));
			Control.customWait("HomePage", "HomePageLogo", 60);
			Control.objExists("HomePage", "GetSim", true);
			if(Control.findElement("HomePage", "CloseButton")!=null) {
				Control.click("HomePage", "CloseButton");
			}
			Control.takeScreenshot();
			
			
			//Purchase sim for Luzon
			GetSim();
			SelecQuantity();
			ShippingDetails();
			EnterShippingDetails();
			SelectProvince("Luzon");
			Control.click("ShippingDetailsPage", "IAgreeCheckbox");
			Thread.sleep(1000);
			Control.takeScreenshot();
			Control.click("ShippingDetailsPage", "OKButton");
			Control.customWait("GetSimPage", "SimOrderTitle", 60);
			Control.takeScreenshot();
			PaymentSectionCC();
			Control.objExists("GetSimPage", "IAgreeCheckbox", true);
			Control.objExists("GetSimPage", "PayButton", true);
			Control.click("GetSimPage", "IAgreeCheckbox");
			Thread.sleep(3000);
			ScrollToView("GetSimPage", "PayButton");
			try
			{
				Control.click("GetSimPage", "PayButton");
			}
			catch(Exception e)
			{
				try
				{
					Control.click("GetSimPage", "PayButton");
				}
				catch(Exception ex)
				{
					ex.printStackTrace();
				}
			}
			Thread.sleep(2000);
			OrderConfirmation();
			CardPayment();
			PaymentSuccessPage();
	}
	public static void SimPurchaseCC2() throws Exception{
		
		GetSim2();
		SelecQuantity();
		Control.click("GetSimPage", "AddAddressButton");
		Thread.sleep(2000);
		Control.objExists("GetSimPage", "ManageAddress", true);
		Control.objExists("GetSimPage", "ConfirmAddress", true);
		Control.click("GetSimPage", "ConfirmAddress");
		Control.customWait("GetSimPage", "SimOrderTitle", 60);
		Control.takeScreenshot();
		PaymentSectionCC();
		Control.objExists("GetSimPage", "IAgreeCheckbox", true);
		Control.objExists("GetSimPage", "PayButton", true);
		Control.click("GetSimPage", "IAgreeCheckbox");
		Thread.sleep(3000);
		ScrollToView("GetSimPage", "PayButton");
		try
		{
			Control.click("GetSimPage", "PayButton");
		}
		catch(Exception e)
		{
			try
			{
				Control.click("GetSimPage", "PayButton");
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		Thread.sleep(2000);
		OrderConfirmation();
		CardPayment();
		PaymentSuccessPage();
	}
	
	//use without login
	public static void GetSim() throws Exception{
		try 
		{
			Control.click("HomePage", "GetSim");
			Control.customWait("GetSimPage", "SimOrderTitle", 60);
			Control.objExists("GetSimPage", "OrderImage", true);
			Control.objExists("GetSimPage", "OrderDescription", true);
			Control.objExists("GetSimPage", "DataOffer", true);
			Control.objExists("GetSimPage", "DataOfferSpecial", true);
			Control.objExists("GetSimPage", "DataOfferQty", true);
			Control.objExists("GetSimPage", "DataPrice", true);
			Control.objExists("GetSimPage", "SimCard", true);
			Control.objExists("GetSimPage", "SimCardQty", true);
			Control.objExists("GetSimPage", "SimCardPriceFree", true);
			Control.objExists("GetSimPage", "ShippingFeeLabel", true);
			Control.objExists("GetSimPage", "ShippingFeeFree", true);
			Control.objExists("GetSimPage", "ItemQty", true);
			Control.objExists("GetSimPage", "AddButton", true);
			Control.objExists("GetSimPage", "SubtractButton", true);
			Control.objExists("GetSimPage", "TotalLabel", true);
			Control.objExists("GetSimPage", "TotalPrice", true);
			Control.objExists("GetSimPage", "ViewDetails", true);
			Control.objExists("GetSimPage", "AddressTitle", true);
			Control.objExists("GetSimPage", "AddAddressButton", true);
			Control.takeScreenshot();
		}
		catch(Exception e)
		{
			Control.takeScreenshot();
			e.printStackTrace();
		}
	}
	
	//Use after login
	public static void GetSim2() throws Exception{
		Actions action = new Actions(Constant.driver);
		Control.customWait("Dashboard", "Shop", 60);
		//Control.findElement("Dashboard", "Shop");
		action.moveToElement(Constant.driver.findElement(By.xpath("//div[@class='item-menu']//a/span[contains(text(),'Shop')]"))).click().build().perform();
		Thread.sleep(1000);
		//action.moveToElement(Control.findElement()).click().build().perform();
		Control.click("Dashboard", "SimCard");
		Control.customWait("SimPage", "30GBNoExpiry", 60);
		Control.click("SimPage", "30GBNoExpiry");
		Thread.sleep(2000);
		Control.objExists("GetSimPage", "OrderImage", true);
		Control.objExists("SimPage","30GBNoExpiry", true);
		Control.objExists("SimPage","HexaSIM", true);
		Control.objExists("SimPage","HexaWith", true);
		Control.objExists("SimPage","Hexa30GB", true);
		Control.objExists("SimPage","MiniHexaNoExpiryData", true);
		Control.objExists("SimPage","MiniHexa5GLTEReady", true);
		Control.objExists("SimPage","MiniHexaFreeDelivery", true);
		Control.objExists("SimPage","SimPrice", true);
		Control.objExists("SimPage","Quantity", true);
		Control.objExists("SimPage","BuyNow", true);
		Control.click("SimPage","BuyNow");
		
		try 
		{
			Control.customWait("GetSimPage", "SimOrderTitle", 60);
			Control.objExists("GetSimPage", "OrderImage", true);
			Control.objExists("GetSimPage", "OrderDescription", true);
			Control.objExists("GetSimPage", "DataOffer", true);
			Control.objExists("GetSimPage", "DataOfferSpecial", true);
			Control.objExists("GetSimPage", "DataOfferQty", true);
			Control.objExists("GetSimPage", "DataPrice", true);
			Control.objExists("GetSimPage", "SimCard", true);
			Control.objExists("GetSimPage", "SimCardQty", true);
			Control.objExists("GetSimPage", "SimCardPriceFree", true);
			Control.objExists("GetSimPage", "ShippingFeeLabel", true);
			Control.objExists("GetSimPage", "ShippingFeeFree", true);
			Control.objExists("GetSimPage", "ItemQty", true);
			Control.objExists("GetSimPage", "AddButton", true);
			Control.objExists("GetSimPage", "SubtractButton", true);
			Control.objExists("GetSimPage", "TotalLabel", true);
			Control.objExists("GetSimPage", "TotalPrice", true);
			Control.objExists("GetSimPage", "ViewDetails", true);
			Control.objExists("GetSimPage", "AddressTitle", true);
			Control.objExists("GetSimPage", "AddAddressButton", true);
			Control.takeScreenshot();
		}
		catch(Exception e)
		{
			Control.takeScreenshot();
			e.printStackTrace();
		}
	}
	
	
	
	public static void SelecQuantity() throws Exception{
		int InitialQty = 0;
		int InitialItemPrice = 0;
		int InitialDataPrice = 0;
		int DataOfferQty = 0;
		int SimCardQty = 0;
		int ItemQty = 0;
		int DataPrice = 0;
		int TotalItemPrice = 0;
		
		try 
		{
			InitialQty = Integer.parseInt(Control.findElement("GetSimPage", "ItemQty").getAttribute("value"));
			InitialDataPrice = Integer.parseInt(Control.findElement("GetSimPage", "DataPrice").getText().replace('P', ' ').trim());
			InitialItemPrice = Integer.parseInt(Control.findElement("GetSimPage", "TotalPrice").getText().replace('P', ' ').trim());
			
			Control.click("GetSimPage", "AddButton");
			Thread.sleep(2000);
			Constant.driver.navigate().refresh();
			Thread.sleep(2000);
			DataOfferQty = Integer.parseInt(Control.findElement("GetSimPage", "DataOfferQty").getText().replace('x', ' ').trim());
			SimCardQty = Integer.parseInt(Control.findElement("GetSimPage", "SimCardQty").getText().replace('x', ' ').trim());
			ItemQty = Integer.parseInt(Control.findElement("GetSimPage", "ItemQty").getAttribute("value"));
			DataPrice = Integer.parseInt(Control.findElement("GetSimPage", "DataPrice").getText().replace('P', ' ').trim());
			TotalItemPrice = Integer.parseInt(Control.findElement("GetSimPage", "TotalPrice").getText().replace('P', ' ').trim()); 
			System.out.println(DataOfferQty);
			System.out.println(SimCardQty);
			System.out.println(ItemQty);
			
			if(ItemQty==InitialQty+1) {
				if(DataOfferQty==ItemQty && SimCardQty==ItemQty) {
					Generic.WriteTestData("Item quantity displayed","","","Item quantity is displayed correctly","Item quantity is displayed correctly","Passed");
				}else {
					Generic.WriteTestData("Item quantity displayed","","","Item quantity is displayed correctly","Item quantity is not displayed correctly","Failed");
				}
			}else {
				Generic.WriteTestData("Item quantity displayed",""," ","Item quantity should increment when + button is clicked","Item quantity did not increment when + button is clicked","Failed");
			}
			
			if(DataPrice==InitialDataPrice*DataOfferQty) {
				if(DataPrice==TotalItemPrice && InitialItemPrice*ItemQty==TotalItemPrice) {
					Generic.WriteTestData("Item price displayed","","","Item price is displayed correctly","Item quantity is displayed correctly","Passed");
				}else {
					Generic.WriteTestData("Item price displayed","","","Item price is displayed correctly","Item quantity is not displayed correctly","Failed");
				}
			}
			Control.takeScreenshot();
		}
		catch(Exception e)
		{
			Control.takeScreenshot();
			e.printStackTrace();
		}
		
		try 
		{
			InitialQty = Integer.parseInt(Control.findElement("GetSimPage", "ItemQty").getAttribute("value"));
			InitialDataPrice = Integer.parseInt(Control.findElement("GetSimPage", "DataPrice").getText().replace('P', ' ').trim());
			InitialItemPrice = Integer.parseInt(Control.findElement("GetSimPage", "TotalPrice").getText().replace('P', ' ').trim());
			Control.click("GetSimPage", "SubtractButton");
			Thread.sleep(2000);
			
			DataOfferQty = Integer.parseInt(Control.findElement("GetSimPage", "DataOfferQty").getText().replace('x', ' ').trim());
			SimCardQty = Integer.parseInt(Control.findElement("GetSimPage", "SimCardQty").getText().replace('x', ' ').trim());
			ItemQty = Integer.parseInt(Control.findElement("GetSimPage", "ItemQty").getAttribute("value"));
			DataPrice = Integer.parseInt(Control.findElement("GetSimPage", "DataPrice").getText().replace('P', ' ').trim());
			TotalItemPrice = Integer.parseInt(Control.findElement("GetSimPage", "TotalPrice").getText().replace('P', ' ').trim()); 
			System.out.println(DataOfferQty);
			System.out.println(SimCardQty);
			System.out.println(ItemQty);
			
			if(ItemQty==InitialQty-1) {
				if(DataOfferQty==ItemQty && SimCardQty==ItemQty) {
					Generic.WriteTestData("Item quantity displayed","","","Item quantity is displayed correctly","Item quantity is displayed correctly","Passed");
				}else {
					Generic.WriteTestData("Item quantity displayed","","","Item quantity is displayed correctly","Item quantity is not displayed correctly","Failed");
				}
			}else {
				Generic.WriteTestData("Item quantity displayed",""," ","Item quantity should increment when + button is clicked","Item quantity did not increment when + button is clicked","Failed");
			}
			
			if(DataPrice==InitialDataPrice*DataOfferQty) {
				if(DataPrice==TotalItemPrice && InitialItemPrice*ItemQty==TotalItemPrice) {
					Generic.WriteTestData("Item price displayed","","","Item price is displayed correctly","Item quantity is displayed correctly","Passed");
				}else {
					Generic.WriteTestData("Item price displayed","","","Item price is displayed correctly","Item quantity is not displayed correctly","Failed");
				}
			}
			Control.takeScreenshot();
			
		}
		catch(Exception e)
		{
			Control.takeScreenshot();
			e.printStackTrace();
		}
	}
	
	public static void ShippingDetails() throws Exception{
		
		try
		{
			Control.click("GetSimPage", "AddAddressButton");
			Control.customWait("ShippingDetailsPage", "ShippingDetailsTitle", 60);
			Control.objExists("ShippingDetailsPage", "ShippingDetailsTitle", true);
			Control.compareText("ShippingDetailsPage", "ShippingDetailsSpiel1", Generic.ReadFromExcel("ShippingDetailsSpiel1", "DawnSpiels", 1));
			Control.compareText("ShippingDetailsPage", "ShippingDetailsSpiel2", Generic.ReadFromExcel("ShippingDetailsSpiel2", "DawnSpiels", 1));
			Control.compareText("ShippingDetailsPage", "ShippingDetailsSpiel3", Generic.ReadFromExcel("ShippingDetailsSpiel3", "DawnSpiels", 1));
			Control.compareText("ShippingDetailsPage", "EnterDetails", Generic.ReadFromExcel("EnterDetails", "DawnSpiels", 1));
			Control.objExists("ShippingDetailsPage", "FirstNameField", true);
			Control.objExists("ShippingDetailsPage", "LastNameField", true);
			Control.objExists("ShippingDetailsPage", "UnitHouseBldgField", true);
			Control.objExists("ShippingDetailsPage", "StreetNameField", true);
			Control.objExists("ShippingDetailsPage", "Province", true);
			Control.objExists("ShippingDetailsPage", "City", true);
			Control.objExists("ShippingDetailsPage", "Barangay", true);
			Control.objExists("ShippingDetailsPage", "VillageSubdivision", true);
			Control.objExists("ShippingDetailsPage", "ZipCode", true);
			Control.objExists("ShippingDetailsPage", "ShippingDetailsSpiel4", true);
			Control.objExists("ShippingDetailsPage", "EmailAddress", true);
			Control.objExists("ShippingDetailsPage", "MobileNumber", true);
			Control.objExists("ShippingDetailsPage", "IAgreeCheckbox", true);
			Control.objExists("ShippingDetailsPage", "ShippingNote", true);
			Control.objExists("ShippingDetailsPage", "OKButton", true);
			Control.objExists("ShippingDetailsPage", "BackButton", true);
			Control.takeScreenshot();
		}
		catch(Exception e)
		{
			Control.takeScreenshot();
			e.printStackTrace();
		}
		
		
	}
	
	public static void ShippingDetails2() throws Exception{
		
		try
		{
			Control.click("CartPage", "Address");
			Control.customWait("ShippingDetailsPage", "AddAddressHeader", 60);
			Control.objExists("ShippingDetailsPage", "AddAddressHeader", true);
			Control.objExists("ShippingDetailsPage", "FirstNameField", true);
			Control.objExists("ShippingDetailsPage", "LastNameField", true);
			Control.objExists("ShippingDetailsPage", "UnitHouseBldgField", true);
			Control.objExists("ShippingDetailsPage", "StreetNameField", true);
			Control.objExists("ShippingDetailsPage", "Province", true);
			Control.objExists("ShippingDetailsPage", "City", true);
			Control.objExists("ShippingDetailsPage", "Barangay", true);
			Control.objExists("ShippingDetailsPage", "VillageSubdivision", true);
			Control.objExists("ShippingDetailsPage", "ZipCode", true);
			Control.objExists("ShippingDetailsPage", "ShippingDetailsSpiel4", true);
			Control.objExists("ShippingDetailsPage", "EmailAddress", true);
			Control.objExists("ShippingDetailsPage", "MobileNumber", true);
			Control.objExists("ShippingDetailsPage", "IAgreeCheckbox", true);
//			Control.objExists("ShippingDetailsPage", "ShippingNote", true);
//			Control.objExists("ShippingDetailsPage", "OKButton", true);
			Control.objExists("ShippingDetailsPage", "ConfirmAddress", true);
			Control.objExists("ShippingDetailsPage", "BackButton", true);
			Control.takeScreenshot();
		}
		catch(Exception e)
		{
			Control.takeScreenshot();
			e.printStackTrace();
		}
		
		
	}
	
	
	public static void EnterShippingDetails() throws Exception{
		
		try
		{	
			Control.findElement("ShippingDetailsPage", "FirstNameField").clear();
			Control.findElement("ShippingDetailsPage", "FirstNameField").sendKeys("Firstname");
			Control.findElement("ShippingDetailsPage", "LastNameField").clear();
			Control.findElement("ShippingDetailsPage", "LastNameField").sendKeys("Lastname");
			Control.findElement("ShippingDetailsPage", "UnitHouseBldgField").sendKeys("Test Building");
			Control.findElement("ShippingDetailsPage", "StreetNameField").sendKeys("Test Street");
			Control.findElement("ShippingDetailsPage", "VillageSubdivision").sendKeys("123 Test Village Subdivision");
			Control.findElement("ShippingDetailsPage", "ZipCode").sendKeys("1234");
			Control.findElement("ShippingDetailsPage", "EmailAddress").clear();
			Control.findElement("ShippingDetailsPage", "EmailAddress").sendKeys(Generic.ReadFromExcel("email", "AI_TestData", 1));
			Control.findElement("ShippingDetailsPage", "MobileNumber").clear();
			Control.click("ShippingDetailsPage", "MobileNumber");
			Thread.sleep(1000);
			Control.findElement("ShippingDetailsPage", "MobileNumber").sendKeys(Generic.ReadFromExcel("testNumber", "AI_TestData", 1));
			Control.takeScreenshot();
		}
		catch(Exception e)
		{
			Control.takeScreenshot();
			e.printStackTrace();
		}
	}
	
	public static String GroupName() throws Exception{
		String[] groups= {"Yondu Group","Yondy Group","Regression_UAM 1","TCoE Group","Shayne_Group"};
		Random random = new Random();
		String group=groups[random.nextInt(groups.length)];
		System.out.println(group);
		return group;
		
	}
	
	public static String RoleName() throws Exception{
		String[] roles= {"Datamart Admin","Shayne Role","ETL DEV - Glory","Super Admin", "Yondu Role"};
		Random random = new Random();
		String role=roles[random.nextInt(roles.length)];
		System.out.println(role);
		return role;
		
	}
	
	public static String EmailName() throws Exception{
		String[] emails= {"aakruti.mohanty@globe.com.ph","abhishek.upare@globe.com.ph", "aditya.nipane@globe.com.ph","albert.olea@globe.com.ph","alaxander.mallorca@globe.com.ph","alexis.reyes@globe.com.ph","alexzandro.espina@globe.com.ph","alvarado.reymundo@globe.com.ph","amiel.ferrer@globe.com.ph","ananya.pradhan@globe.com.ph"};
		Random random = new Random();
		String email=emails[random.nextInt(emails.length)];
		System.out.println(email);
		return email;
		
	}
	
	public static String EtlName() throws Exception{
		String[] etls= {"aakruti.mohanty@globe.com.ph","zmgballinan@globe.com.ph","zjscaparanga@globe.com.ph","abhishek.upare@globe.com.ph", "aditya.nipane@globe.com.ph","albert.olea@globe.com.ph","alaxander.mallorca@globe.com.ph","alexis.reyes@globe.com.ph","alexzandro.espina@globe.com.ph","alvarado.reymundo@globe.com.ph","amiel.ferrer@globe.com.ph","ananya.pradhan@globe.com.ph","jayson.julian@globe.com.ph","sakhamuri.lavanya@globe.com.ph","ralph.arinque@globe.com.ph","jero.edurea@globe.com.ph","zrconcepcion@globe.com.ph","ztacastillo@globe.com.ph","zjmstuart@globe.com.ph"};
		Random random = new Random();
		String etl=etls[random.nextInt(etls.length)];
		System.out.println(etl);
		return etl;
		
	}


	
	
	public static void SelectProvince(String IslandGroup) throws Exception{
		String [] Luzon = {"Benguet", "Kalinga", "Ilocos Norte", "Ilocos Sur", "La Union", "Pangasinan", 
				"Cagayan", "Isabela", "Nueva Vizcaya", "Bataan", "Bulacan", "Nueva Ecija", "Pampanga", "Tarlac", 
				"Zambales", "Batangas", "Cavite", "Laguna", "Quezon", "Rizal", "Marinduque", "Camarines Sur"};
		String [] Visayas = {"Aklan", "Antique", "Capiz", "Iloilo", "Negros Occidental", "Bohol", "Biliran", 
				"Cebu", "Negros Oriental",  "Eastern Samar", "Northern Samar", "Samar", "Leyte", "Southern Leyte"};
		String [] Mindanao = {"Zamboanga del Norte", "Zamboanga del Sur", "Zamboanga Sibugay", "Lanao del Norte", 
				"Misamis Occidental", "Misamis Oriental","Davao del Norte", "Davao del Sur", "Cotabato", 
				"South Cotabato", "Agusan del Norte", "Surigao del Norte", "Maguindanao"};
		Random random = new Random();
		
		Control.click("ShippingDetailsPage", "Province");
		Thread.sleep(1000);
		Control.takeScreenshot();
		Control.click("ShippingDetailsPage", "ProvinceDropdown");
		Thread.sleep(1000);
		Control.takeScreenshot();
		if(IslandGroup.contentEquals("Luzon"))
		{
			Province = Luzon[random.nextInt(Luzon.length)];
			Control.findElement("ShippingDetailsPage", "ProvinceDropdown").sendKeys(Province);
			Thread.sleep(1000);
		}
		else if(IslandGroup.contentEquals("Visayas"))
		{
			Province = Visayas[random.nextInt(Visayas.length)];
			Control.findElement("ShippingDetailsPage", "ProvinceDropdown").sendKeys(Province);
			Thread.sleep(1000);
		}
		else if(IslandGroup.contentEquals("Mindanao"))
		{
			Province = Mindanao[random.nextInt(Mindanao.length)];
			Control.findElement("ShippingDetailsPage", "ProvinceDropdown").sendKeys(Province);
			Thread.sleep(1000);
		}
		else if(IslandGroup.contentEquals("Metro Manila"))
		{
			Province = "Metro Manila";
			Control.findElement("ShippingDetailsPage", "ProvinceDropdown").sendKeys(Province);
			Thread.sleep(1000);
		}
		else
		{
			Generic.WriteTestData("Shipping Details Page","","","Island group must be Luzon, Visayas, Mindanao, or NCR only.","Invalid island group. Must be Luzon, Visayas, Mindanao, or NCR only.","Failed");
		}
		Control.takeScreenshot();
		Control.click("ShippingDetailsPage", "SearchedProvince");
		Thread.sleep(1000);
		Control.takeScreenshot();
		
		Control.click("ShippingDetailsPage", "City");
		Thread.sleep(1000);
		Control.takeScreenshot();
		List<WebElement> ListCity = findElements("ShippingDetailsPage","ListOfCity");
		//List<WebElement> City = Constant.driver.findElements(By.xpath("//*[@name='city']//following-sibling::div//descendant::li[@class='dropdown-custom__li']//span"));
		City = ListCity.get(random.nextInt(ListCity.size())).getText();
		System.out.println(City);
		Control.click("ShippingDetailsPage", "CityDropdown");
		Thread.sleep(1000);
		Control.takeScreenshot();
		Control.findElement("ShippingDetailsPage", "CityDropdown").sendKeys(City);
		Thread.sleep(1000);
		Control.takeScreenshot();
		Control.click("ShippingDetailsPage", "SearchedCity");
		Thread.sleep(1000);
		Control.takeScreenshot();
		
		Control.click("ShippingDetailsPage", "Barangay");
		Thread.sleep(1000);
		Control.takeScreenshot();
		List<WebElement> ListBarangay = findElements("ShippingDetailsPage","ListOfBarangay");
		//List<WebElement> Barangay = Constant.driver.findElements(By.xpath("//*[@name='barangay']//following-sibling::div//descendant::li[@class='dropdown-custom__li']//span"));
		Barangay = ListBarangay.get(random.nextInt(ListBarangay.size())).getText();
		System.out.println(Barangay);
		Control.click("ShippingDetailsPage", "BarangayDropdown");
		Thread.sleep(1000);
		Control.takeScreenshot();
		Control.findElement("ShippingDetailsPage", "BarangayDropdown").sendKeys(Barangay);
		Thread.sleep(1000);
		Control.takeScreenshot();
		Control.click("ShippingDetailsPage", "SearchedBarangay");
		Thread.sleep(1000);
		Control.takeScreenshot();
	}
	
	public static void PaymentSection() throws Exception{
		Control.objExists("GetSimPage", "PaymentTitle", true);
		Control.objExists("GetSimPage", "PaymentDesc", true);
		Control.objExists("GetSimPage", "GCash", true);
		Control.objExists("GetSimPage", "PaymentNote", true);
		Control.objExists("GetSimPage", "CreditOrDebitCard", true);
		//Control.takeScreenshot();
		//Control.click("GetSimPage", "CreditOrDebitCard");
		//Thread.sleep(1000);
		Control.takeScreenshot();
		Control.click("GetSimPage", "GCash");
		Thread.sleep(1000);
		Control.takeScreenshot();
	}
	
	public static void PaymentSectionCC() throws Exception{
		Constant.driver.navigate().refresh();
		Thread.sleep(4000);
		Control.objExists("GetSimPage", "PaymentTitle", true);
		Control.objExists("GetSimPage", "PaymentDesc", true);
		Control.objExists("GetSimPage", "GCash", true);
		Control.objExists("GetSimPage", "PaymentNote", true);
		Control.objExists("GetSimPage", "CreditOrDebitCard", true);
		Control.takeScreenshot();
//		Control.click("GetSimPage", "GCash");
//		Thread.sleep(1000);
//		Control.takeScreenshot();
		Control.click("GetSimPage", "CreditOrDebitCard");
		Thread.sleep(1000);
		Control.takeScreenshot();
	}
	
	public static void OrderConfirmation() throws Exception{
		
		Control.objExists("OrderConfirmModal", "OrderDetailsTitle", true);
		Control.objExists("OrderConfirmModal", "OrderSummaryLabel", true);
		Control.objExists("OrderConfirmModal", "EditOrder", true);
		Control.objExists("OrderConfirmModal", "DataOffer", true);
		Control.objExists("OrderConfirmModal", "DataOfferSpecial", true);
		Control.objExists("OrderConfirmModal", "DataOfferQty", true);
		Control.objExists("OrderConfirmModal", "DataPrice", true);
		Control.objExists("OrderConfirmModal", "SimCard", true);
		Control.objExists("OrderConfirmModal", "SimCardQty", true);
		Control.objExists("OrderConfirmModal", "SimCardPriceFree", true);
		Control.objExists("OrderConfirmModal", "ShippingFeeLabel", true);
		Control.objExists("OrderConfirmModal", "EditShippingDetails", true);
		Control.objExists("OrderConfirmModal", "Name", true);
		Control.objExists("OrderConfirmModal", "Address", true);
		Control.objExists("OrderConfirmModal", "EmailAddress", true);
		Control.objExists("OrderConfirmModal", "PhoneNumber", true);
		Control.objExists("OrderConfirmModal", "EstimatedDeliveryLabel", true);
		Control.objExists("OrderConfirmModal", "EstimatedDeliveryText", true);
		Control.objExists("OrderConfirmModal", "ConfirmOrder", true);
		Control.takeScreenshot();
		
//		Control.compareText("OrderConfirmModal", "DataOffer", "30GB");
		Control.compareText("OrderConfirmModal", "DataOfferQty", "x 1");
		Control.compareText("OrderConfirmModal", "DataPrice", "P299");
		Control.compareText("OrderConfirmModal", "SimCard", "SIM Card");
		Control.compareText("OrderConfirmModal", "SimCardQty", "x 1");
//		Control.compareText("OrderConfirmModal", "SimCardPriceFree", "Free");
		Control.compareText("OrderConfirmModal", "Name", "Firstname Lastname");
//		Control.compareText("OrderConfirmModal", "Address", "Test Building Test Street, 123 Test Village Subdivision, "+Barangay+", "+City+", "+Province+", 1234");
		Control.compareText("OrderConfirmModal", "EmailAddress", Generic.ReadFromExcel("email", "AI_TestData", 1));
//		Control.compareText("OrderConfirmModal", "PhoneNumber", "+63 917 123 4567");
		Control.takeScreenshot();
		
		if(Control.findElement("OrderConfirmModal", "ConfirmOrder")==null) {
			ScrollToView("OrderConfirmModal", "ConfirmOrder");
		}
		Control.click("OrderConfirmModal", "ConfirmOrder");
		Control.takeScreenshot();
		
	}
	
	public static void GCashPayment() throws Exception{
		Actions action = new Actions(Constant.driver);
		Control.customWait("GCashPage", "GCashLogo", 60);
		Control.takeScreenshot();
		Control.objExists("GCashPage", "GCashLogo", true);
		Control.objExists("GCashPage", "GCashNumber", true);
		Control.findElement("GCashPage", "GCashNumber").sendKeys(Generic.ReadFromExcel("GCashNumber", "AI_TestData", 1));
		Control.click("GCashPage", "NextButton");
		Thread.sleep(1000);
		Control.customWait("GCashPage", "OtpInput", 60);
		if(Control.findElement("GCashPage", "OtpInput")!=null) {
			Control.takeScreenshot();
			action.sendKeys(Generic.ReadFromExcel("GCashOTP", "AI_TestData", 1)).build().perform();
			Thread.sleep(2000);
			Control.takeScreenshot();
			Control.click("GCashPage", "SubmitButton");
		}
		Control.customWait("GCashPage", "PinInput", 60);
		Control.takeScreenshot();
		action.sendKeys(Generic.ReadFromExcel("GCashPIN", "AI_TestData", 1)).build().perform();
		Control.takeScreenshot();
		Control.click("GCashPage", "SubmitButton");
		Control.customWait("GCashPage", "ConfirmButton", 60);
		Control.takeScreenshot();
		Control.click("GCashPage", "ConfirmButton");
		Control.takeScreenshot();
	}
	
	public static void CardPayment() throws Exception{
		Actions action = new Actions(Constant.driver);
		WaitPromoLoading();
		if(Control.findElement("CreditCardPage", "CreditCardLogo")==null) {
			Constant.driver.navigate().refresh();
			Control.customWait("PurchasePromoPage", "CreditOrDebitCard", 60);
			Control.click("PurchasePromoPage", "CreditOrDebitCard");
			Thread.sleep(1000);
			Control.click("PurchasePromoPage", "PurchasePromoButton");
			Thread.sleep(1000);
			WaitPromoLoading();
		}
		Control.takeScreenshot();
		
		Control.objExists("CreditCardPage", "CreditCardLogo", true);
		Control.objExists("CreditCardPage", "CreditCardLabel", true);
		Control.objExists("CreditCardPage", "CardNumberLabel", true);
		//Control.objExists("CreditCardPage", "CardNumberField", true);
		Control.objExists("CreditCardPage", "ExpiryDateLabel", true);
		//Control.objExists("CreditCardPage", "ExpiryDateField", true);
		Control.objExists("CreditCardPage", "CVCCVVLabel", true);
		//Control.objExists("CreditCardPage", "CVCCVVField", true);
		Control.objExists("CreditCardPage", "PayButton", true);
		Control.takeScreenshot();
		
		Control.click("CreditCardPage", "CardNumberLabel");
		Thread.sleep(2000);
		action.sendKeys(Generic.ReadFromExcel("CreditCard", "AI_TestData", 1)).build().perform();
		Thread.sleep(2000);
		Control.click("CreditCardPage", "ExpiryDateLabel");
		Thread.sleep(1000);
		action.sendKeys(Generic.ReadFromExcel("CCDate", "AI_TestData", 1)).build().perform();
		Thread.sleep(1000);
		Control.click("CreditCardPage", "CVCCVVLabel");
		Thread.sleep(1000);
		action.sendKeys(Generic.ReadFromExcel("CVC", "AI_TestData", 1)).build().perform();
		Thread.sleep(1000);
		Control.takeScreenshot();
		
		Control.click("CreditCardPage", "PayButton");
		Thread.sleep(1000);
		WaitPromoLoading();
		Control.takeScreenshot();
	}
	
	public static void PaymentSuccessPage() throws Exception{
		Control.customWait("SuccessPage", "SuccessLogo", 60);
		Thread.sleep(2000);
		if(Control.findElement("SuccessPage", "SurveyClose")!=null) {
			Control.takeScreenshot();
			try
			{
				Control.click("SuccessPage", "SurveyClose");
				if(Control.findElement("SuccessPage", "DelayInPromoHeader")!=null) {
					Control.takeScreenshot();
					try
					{
						Control.click("SuccessPage", "DelayClose");
						
					}
					catch(Exception e)
					{
						Constant.driver.findElement(By.xpath("//div[@class='declineButtonTextDiv']/button"));
					}
					Thread.sleep(1000);
				}
			}
			catch(Exception e)
			{
				Constant.driver.findElement(By.xpath("//div[@class='popup__footer']//button"));
				if(Control.findElement("SuccessPage", "DelayInPromoHeader")!=null) {
					Control.takeScreenshot();
					try
					{
						Control.click("SuccessPage", "DelayClose");
						
					}
					catch(Exception ex)
					{
						Constant.driver.findElement(By.xpath("//div[@class='declineButtonTextDiv']/button"));
					}
					Thread.sleep(1000);
				}
			}
			Thread.sleep(1000);
		}
		
		if(Control.findElement("SuccessPage", "DelayInPromoHeader")!=null) {
			Control.takeScreenshot();
			try
			{
				Control.click("SuccessPage", "DelayClose");
				if(Control.findElement("SuccessPage", "SurveyClose")!=null) {
					Control.takeScreenshot();
					try
					{
						Control.click("SuccessPage", "SurveyClose");
					}
					catch(Exception e)
					{
						Constant.driver.findElement(By.xpath("//div[@class='popup__footer']//button"));
					}
					Thread.sleep(1000);
				}
				
			}
			catch(Exception e)
			{
				Constant.driver.findElement(By.xpath("//div[@class='declineButtonTextDiv']/button"));
				if(Control.findElement("SuccessPage", "SurveyClose")!=null) {
					Control.takeScreenshot();
					try
					{
						Control.click("SuccessPage", "SurveyClose");
					}
					catch(Exception ex)
					{
						Constant.driver.findElement(By.xpath("//div[@class='popup__footer']//button"));
					}
					Thread.sleep(1000);
				}
			}
			Thread.sleep(1000);
		}
		
		
		Control.takeScreenshot();
		Control.objExists("SuccessPage", "SuccessMessage", true);
		Control.objExists("SuccessPage", "PaymentMethodLabel", true);
		Control.objExists("SuccessPage", "PaymentMethod", true);
		Control.objExists("SuccessPage", "AmountPaidLabel", true);
		Control.objExists("SuccessPage", "AmountPaid", true);
		Control.objExists("SuccessPage", "TransactionNumberLabel", true);
		Control.objExists("SuccessPage", "TransactionNumber", true);
		Control.objExists("SuccessPage", "PurchasedItemLabel", true);
		Control.objExists("SuccessPage", "PurchasedItem", true);
		Control.objExists("SuccessPage", "BackToDashboard", true);
		Control.click("SuccessPage", "BackToDashboard");
		Control.customWait("HomePage", "HomePageLogo", 60);
		Control.takeScreenshot();
	}
	
	
	
	public static void AccountCreation() throws Exception{
		Generic.WriteTestCase("Account Creation", "Home Page Creation", "ExpectedResult", "ActualResult");
		Control.objExists("HomePage", "Account", true);
		Control.takeScreenshot();
		Control.click("HomePage", "Account");
		Control.customWait("AccountCreation", "VerifyNumberSpiel", 60);
		Control.compareText("AccountCreation", "VerifyNumberSpiel", Generic.ReadFromExcel("VerifyNumberSpiel", "DawnSpiels", 1));
		Control.objExists("AccountCreation", "EnterGomoNumber", true);
		Control.objExists("AccountCreation", "LetsGoButton", true);
		Control.takeScreenshot();
		Control.click("AccountCreation", "EnterGomoNumber");
		Control.findElement("AccountCreation", "EnterGomoNumber").sendKeys(Generic.ReadFromExcel("GOMOMSISDN", "AccountCreation", 1));
		Thread.sleep(2000);
		Control.takeScreenshot();
		Control.click("AccountCreation", "LetsGoButton");
		Thread.sleep(2000);
		
		Control.objExists("AccountCreation", "InsertSimImg", true);
		Control.objExists("AccountCreation", "InsertSimTitle", true);
		Control.objExists("AccountCreation", "InsertSimSpiel", true);
		Control.objExists("AccountCreation", "CancelButton", true);
		Control.objExists("AccountCreation", "ProceedButton", true);
		Control.takeScreenshot();
		Control.click("AccountCreation", "ProceedButton");
		Thread.sleep(2000);
		
		Control.objExists("AccountCreation", "OtpTitle", true);
		Control.objExists("AccountCreation", "OtpSpiel", true);
		Control.objExists("AccountCreation", "MobileNumber", true);
		Control.objExists("AccountCreation", "OtpNotMyNumber", true);
		Control.takeScreenshot();
		Constant.driver.findElement(By.xpath("//input[@name='input-1']")).click();
		Actions action = new Actions(Constant.driver);
		action.sendKeys(getOtp(Generic.ReadFromExcel("GOMOMSISDN", "AccountCreation", 1),false)).build().perform();
		Control.takeScreenshot();
		
		Control.objExists("CreateAccountPage", "CreateAccountTitle", true);
		Control.compareText("CreateAccountPage", "CreateAccounSpiel1", Generic.ReadFromExcel("CreateAccounSpiel1", "DawnSpiels", 1));
		String [] CreateAccountSpiels = Control.findElement("CreateAccountPage", "CreateAccounSpiel2").getText().split("\\r?\\n");
		Control.compareText3(CreateAccountSpiels[0],Generic.ReadFromExcel("CreateAccounSpiel2", "DawnSpiels", 1));
		Control.compareText3(CreateAccountSpiels[1],Generic.ReadFromExcel("CreateAccounSpiel3", "DawnSpiels", 1));
		Control.compareText3(CreateAccountSpiels[2],Generic.ReadFromExcel("CreateAccounSpiel4", "DawnSpiels", 1));
		Control.objExists("CreateAccountPage", "FirstName", true);
		Control.findElement("CreateAccountPage", "FirstName").clear();
		Control.findElement("CreateAccountPage", "FirstName").sendKeys("Fname");
		Thread.sleep(1000);
		Control.takeScreenshot();
		Control.objExists("CreateAccountPage", "LastName", true);
		Control.findElement("CreateAccountPage", "LastName").clear();
		Control.findElement("CreateAccountPage", "LastName").sendKeys("Lname");
		Thread.sleep(1000);
		Control.takeScreenshot();
		Control.objExists("CreateAccountPage", "NickName", true);
		Control.findElement("CreateAccountPage", "NickName").clear();
		Control.findElement("CreateAccountPage", "NickName").sendKeys("Nname");
		Thread.sleep(1000);
		Control.takeScreenshot();
		Control.objExists("CreateAccountPage", "Gender", true);
		String gender = Control.findElement("CreateAccountPage", "Gender").getAttribute("value");
		System.out.println(gender);
		Control.click("CreateAccountPage", "Gender");
		Thread.sleep(1000);
		Control.click("CreateAccountPage", "GenderFemale");
		Thread.sleep(1000);
		gender = Control.findElement("CreateAccountPage", "Gender").getAttribute("value");
		System.out.println(gender);
		Control.click("CreateAccountPage", "Gender");
		Thread.sleep(1000);
		Control.click("CreateAccountPage", "GenderUndisclosed");
		Thread.sleep(1000);
		gender = Control.findElement("CreateAccountPage", "Gender").getAttribute("value");
		System.out.println(gender);
		Control.click("CreateAccountPage", "Gender");
		Thread.sleep(1000);
		Control.click("CreateAccountPage", "GenderMale");
		Thread.sleep(1000);
		gender = Control.findElement("CreateAccountPage", "Gender").getAttribute("value");
		System.out.println(gender);
		Control.objExists("CreateAccountPage", "EmailAddress", true);
		Control.findElement("CreateAccountPage", "EmailAddress").clear();
		Control.findElement("CreateAccountPage", "EmailAddress").sendKeys("zrpbayo@globe.com.ph");
		Thread.sleep(1000);
		Control.takeScreenshot();
		Control.objExists("CreateAccountPage", "GomoNumber", true);
		Control.objExists("CreateAccountPage", "Birthday", true);
		//DatePicker("November", 15,1993);
		action.moveToElement(Control.findElement("CreateAccountPage", "CreateAccountButton")).build().perform();
		Control.objExists("CreateAccountPage", "IAgreeCheckbox", true);
		Control.objExists("CreateAccountPage", "CreateAccountButton", true);
		Control.click("CreateAccountPage", "IAgreeCheckbox");
		Thread.sleep(1000);
		Control.takeScreenshot();
		Control.click("CreateAccountPage", "CreateAccountButton");
		Thread.sleep(1000);
		Control.customWait("CreateAccountPage", "SetPinTitle", 60);
		Control.takeScreenshot();
		Control.objExists("CreateAccountPage", "SetPinTitle", true);
		Control.objExists("CreateAccountPage", "SetPinLabel", true);
		Control.objExists("CreateAccountPage", "ConfirmPin", true);
		Constant.driver.findElement(By.xpath("(//input[@name='input-1'])[1]")).click();
		action.sendKeys(Generic.ReadFromExcel("GomoPin", "AI_TestData", 1)).build().perform();
		Constant.driver.findElement(By.xpath("(//input[@name='input-1'])[2]")).click();
		action.sendKeys(Generic.ReadFromExcel("GomoPin", "AI_TestData", 1)).build().perform();
		Control.takeScreenshot();
		Control.customWait("CreateAccountPage", "SuccessTitle", 60);
		Control.takeScreenshot();
		Control.objExists("CreateAccountPage", "SuccessTitle", true);
		Control.objExists("CreateAccountPage", "SuccessLogo", true);
		Control.objExists("CreateAccountPage", "SuccessSpiel", true);
		Control.objExists("CreateAccountPage", "GoToDashboard", true);
		Control.click("CreateAccountPage", "GoToDashboard");
		Control.customWait("Dashboard", "Dashboard", 60);
		Control.takeScreenshot();
		
		
		Control.customWait("CreateAccountPage", "TutorialHeader", 60);
		Control.objExists("CreateAccountPage", "TutorialDetail", true);
		Control.objExists("CreateAccountPage", "DoneButton", true);
		Control.takeScreenshot();
		Control.click("CreateAccountPage", "RightArrow");
		Thread.sleep(2000);
		Control.takeScreenshot();
		Control.customWait("CreateAccountPage", "TutorialHeader", 60);
		Control.objExists("CreateAccountPage", "TutorialDetail", true);
		Control.objExists("CreateAccountPage", "DoneButton", true);
		Control.takeScreenshot();
		Control.click("CreateAccountPage", "DoneButton");
		Thread.sleep(2000);
		
		Control.takeScreenshot();
		
		
	}
	
	public static void Login() throws Exception{
		Generic.WriteTestCase("Login", "Login", "ExpectedResult", "ActualResult");
		Control.objExists("HomePage", "Account", true);
		Control.takeScreenshot();
		Control.click("HomePage", "Account");
		Control.customWait("Login", "VerifyNumberSpiel", 60);
		Control.compareText("Login", "VerifyNumberSpiel", Generic.ReadFromExcel("VerifyNumberSpiel", "DawnSpiels", 1));
		Control.objExists("Login", "EnterGomoNumber", true);
		Control.objExists("Login", "LetsGoButton", true);
		Control.takeScreenshot();
		Control.click("Login", "EnterGomoNumber");
		Control.findElement("Login", "EnterGomoNumber").sendKeys(Generic.ReadFromExcel("GomoNumber", "AI_TestData", 1));
		Thread.sleep(2000);
		Control.takeScreenshot();
		Control.click("Login", "LetsGoButton");
		Control.customWait("Login", "VerifyPin", 60);
		Thread.sleep(2000);
		Control.takeScreenshot();
		Generic.TestScriptEnds();
		EnterPIN();
		
	}
	
	public static void EnterPIN() throws Exception{
		Generic.WriteTestCase("Enter PIN", "Enter PIN", "ExpectedResult", "ActualResult");
		Control.objExists("Login", "VerifyPin", true);
		Control.objExists("Login", "MobileNumber", true);
		Control.objExists("Login", "NotMyNumber", true);
		Control.objExists("Login", "EnterPin", true);
		Control.objExists("Login", "ForgotPin", true);
		Control.takeScreenshot();
		Constant.driver.findElement(By.xpath("//input[@name='input-1']")).click();
		Actions action = new Actions(Constant.driver);
		action.sendKeys(Generic.ReadFromExcel("GomoPin", "AI_TestData", 1)).build().perform();
		Control.takeScreenshot();
		Control.customWait("Dashboard", "Dashboard", 60);
		Generic.TestScriptEnds();
	}
	
	
	public static void PromoPurchase(String PaymentMethod) throws Exception{
		Generic.WriteTestCase("PromoPurchase", "PromoPurchase", "ExpectedResult", "ActualResult");
		
		Actions action = new Actions(Constant.driver);
		///
		
		//Control.click("Dashboard","Dashboard1");
	   Control.click("Dashboard","Promos");
		//WaitGomoLogo();
		
		if(Control.findElement("Dashboard", "Promos")!=null) {
			Control.click("Dashboard", "Promos");
			WaitGomoLogo();
		}
		
		
	//	Control.click("Dashboard", "Promos");
		Control.takeScreenshot();
		action.moveToElement(Control.findElement("Promos", "BuyPromo")).build().perform();
		Control.objExists("Promos", "PromoHeader", true);
		Control.objExists("Promos", "PromoLogo", true);
		Control.objExists("Promos", "PromoIcon", true);
		Control.objExists("Promos", "MoreDetails", true);
		if(Control.findElement("Promos", "SpecialOffer")!=null) {
			Control.objExists("Promos", "SpecialOffer", true);
		}
		Control.objExists("Promos", "PromoName", true);
		Control.objExists("Promos", "PromoInfo", true);
		Control.objExists("Promos", "PromoExpiry", true);
		Control.objExists("Promos", "BuyPromo", true);
		Control.takeScreenshot();
		
		Control.click("Promos", "BuyPromo");
		Thread.sleep(2000);
		WaitGomoLogo();
		
		Control.objExists("PurchasePromoPage", "AccountLabel", true);
		Control.objExists("PurchasePromoPage", "AccountNumber", true);
		Control.objExists("PurchasePromoPage", "OrderLabel", true);
		Control.objExists("PurchasePromoPage", "PromoName", true);
		Control.objExists("PurchasePromoPage", "PromoSpecial", true);
		Control.objExists("PurchasePromoPage", "PromoPrice", true);
		Control.objExists("PurchasePromoPage", "TotalLabel", true);
		Control.objExists("PurchasePromoPage", "TotalPrice", true);
		Control.objExists("PurchasePromoPage", "PaymentLabel", true);
		Control.objExists("PurchasePromoPage", "PaymentSpiel", true);
		Control.objExists("PurchasePromoPage", "GCash", true);
		Control.objExists("PurchasePromoPage", "NoGCash", true);
		Control.objExists("PurchasePromoPage", "SignUpNow", true);
		Control.objExists("PurchasePromoPage", "CreditOrDebitCard", true);
		Control.objExists("PurchasePromoPage", "PurchasePromoButton", true);
		Control.takeScreenshot();
		
		if(PaymentMethod.equalsIgnoreCase("Credit Card")||PaymentMethod.equalsIgnoreCase("Debit Card")) {
			Control.click("PurchasePromoPage", "CreditOrDebitCard");
			Thread.sleep(1000);
			Control.takeScreenshot();
			Control.click("PurchasePromoPage", "PurchasePromoButton");
			Thread.sleep(1000);
			CardPayment();
		}
		else if(PaymentMethod.equalsIgnoreCase("GCash")) {
			/*
			Control.click("PurchasePromoPage", "SignUpNow");
			Thread.sleep(1000);
			Control.takeScreenshot();
			String GCashRegisterPage = Constant.driver.getTitle();
			System.out.println(GCashRegisterPage);
			Thread.sleep(2000);
			ArrayList<String>tabs = new ArrayList<String>(Constant.driver.getWindowHandles());
			System.out.println(tabs.size());
			if(tabs.size()>1) {
				Constant.driver.switchTo().window(tabs.get(1));
				Thread.sleep(2000);
				Control.takeScreenshot();
				String pageTitle = Constant.driver.getTitle();
				System.out.println(pageTitle);
				if(pageTitle.contentEquals("GCash Registration")) {
					Generic.WriteTestData("GCash Registration Page","","","Should be able to navigate to GCash Registration Page","Able to navigate to GCash Registration Page","Pass");
				}else {
					Generic.WriteTestData("GCash Registration Page","","","Should be able to navigate to GCash Registration Page","Unable to navigate to GCash Registration Page","Failed");
				}
				Thread.sleep(2000);
				Constant.driver.close();
				Thread.sleep(2000);
				Constant.driver.switchTo().window(tabs.get(0));
				Thread.sleep(2000);
			}else {
				Generic.WriteTestData("GCash Registration Page","","","Should be able to navigate to GCash Registration Page","Unable to navigate to GCash Registration Page","Failed");
			}
			*/
			Control.click("PurchasePromoPage", "GCash");
			Thread.sleep(2000);
			Control.takeScreenshot();
			Control.click("PurchasePromoPage", "PurchasePromoButton");
			Thread.sleep(1000);
			GCashPayment();
		}else {
			Generic.WriteTestData("Promo Purchase",""," ","User should enter GCash or Credit Card or Debit Card only","User did not enter GCash or Credit Card or Debit Card","Failed");
		}
		
		////////////////////////
	Control.click("HomePage", "Account");
//		WaitGomoLogo();
		Control.takeScreenshot();
		Generic.TestScriptEnds();
	}
	
	public static void EditName() throws Exception{

		Control.takeScreenshot();
		Control.click("Dashboard","Account");
		Control.customWait("AccountPage", "AccounTitle", 60);
		Control.takeScreenshot();
		Control.objExists("AccountPage", "AccounTitle", true);
		Control.objExists("AccountPage", "NumberTitle", true);
		Control.objExists("AccountPage", "BasicInfo", true);
		Control.objExists("AccountPage", "SavedAddress", true);
		
		Control.click("AccountPage","BasicInfo");
		Control.customWait("AccountPage", "BasicInfoTitle", 60);
		Control.takeScreenshot();
		Control.objExists("AccountPage", "BasicInfoTitle", true);
		Control.objExists("AccountPage", "EditButton", true);
		Control.objExists("AccountPage", "AccountNumber", true);
		Control.objExists("AccountPage", "AccountName", true);
		Control.objExists("AccountPage", "NameLabel", true);
		Control.objExists("AccountPage", "Name", true);
		String initialName = Control.findElement("AccountPage", "Name").getText();
		System.out.println("Name: "+initialName);
		Control.objExists("AccountPage", "EmailLabel", true);
		Control.objExists("AccountPage", "Email", true);
		Control.objExists("AccountPage", "GenderLabel", true);
		Control.objExists("AccountPage", "Gender", true);
		Control.objExists("AccountPage", "BirthdayLabel", true);
		Control.objExists("AccountPage", "Birthday", true);
		
		Control.click("AccountPage", "EditButton");
		Control.customWait("AccountPage", "VerifyPin", 60);
		Control.takeScreenshot();
		Control.objExists("AccountPage", "VerifyPin", true);
		Control.objExists("AccountPage", "NextButton", true);
		
		Constant.driver.findElement(By.xpath("//input[@name='input-1']")).click();
		Actions action = new Actions(Constant.driver);
		action.sendKeys(Generic.ReadFromExcel("PIN", "AccountCreation", 1)).build().perform();
		Control.takeScreenshot();
		Control.click("AccountPage","NextButton");
		Control.customWait("EditPage", "SaveChangesButton", 60);
		Control.takeScreenshot();
		Control.objExists("EditPage", "EditAccountTitle", true);
		Control.objExists("EditPage", "FirstName", true);
		Control.objExists("EditPage", "LastName", true);
		Control.objExists("EditPage", "NickName", true);
		Control.objExists("EditPage", "Gender", true);
		Control.objExists("EditPage", "EmailAddress", true);
		Control.objExists("EditPage", "GomoNumber", true);
		Control.objExists("EditPage", "Birthday", true);
		Control.objExists("EditPage", "SaveChangesButton", true);
		
		String fname = CreateNameString(7);
		System.out.println("First Name: "+fname);
		String lname = CreateNameString(7);	
		System.out.println("Last Name: "+lname);
		
		Control.findElement("EditPage", "FirstName").clear();
		Control.findElement("EditPage", "FirstName").sendKeys(fname);
		Thread.sleep(1000);
		Control.takeScreenshot();
		Control.findElement("EditPage", "LastName").clear();
		Control.findElement("EditPage", "LastName").sendKeys(lname);
		Thread.sleep(1000);
		Control.takeScreenshot();
		
		Control.click("EditPage", "IAgreeCheckbox");
		Thread.sleep(1000);
		Control.takeScreenshot();
		Control.click("EditPage", "SaveChangesButton");
		Control.customWait("EditPage", "SuccessMessage", 60);
		Control.takeScreenshot();
		Control.objExists("EditPage", "SuccessMessage", true);
		Control.customWait("EditPage", "SuccessMessage", 60);
		Control.takeScreenshot();
		
		String updatedName = Control.findElement("AccountPage", "Name").getText();
		System.out.println("Name: "+updatedName);
		
		if(!updatedName.contentEquals(initialName)) {
			if(updatedName.contentEquals(fname+" "+lname)) {
				Generic.WriteTestData("Update Name",""," ","Updated Name should display correctly","Updated Name is displayed correctly","Pass");
			}else {
				Generic.WriteTestData("Update Name",""," ","Updated Name should display correctly","Updated Name is not displayed correctly. Updated name: "+updatedName+" vs Displayed Name:"+fname+" "+lname,"Failed");
			}
		}else {
			Generic.WriteTestData("Update Name",""," ","Name should be updated","Name is not updated.","Failed");
		}
	}
	
	public static void AddNewAddress() throws Exception{
		int InitialNumberOfAddress = 0;
		int CurrentNumberOfAddress = 0;
		
		Control.takeScreenshot();
		Control.click("Dashboard","Account");
		Control.customWait("AccountPage", "AccounTitle", 60);
		Control.takeScreenshot();
		Control.objExists("AccountPage", "AccounTitle", true);
		Control.objExists("AccountPage", "NumberTitle", true);
		Control.objExists("AccountPage", "BasicInfo", true);
		Control.objExists("AccountPage", "SavedAddress", true);
		Control.click("AccountPage","SavedAddress");
		Control.customWait("AccountPage", "SavedAddressHeader", 60);
		InitialNumberOfAddress = Control.findElements("AccountPage", "AddressItem").size();
		System.out.println(InitialNumberOfAddress);	
		Control.click("AccountPage","AddNewAddress");
		Thread.sleep(3000);
		
		try
		{
			Control.customWait("ShippingDetailsPage", "ShippingDetailsTitle", 60);
			Control.objExists("ShippingDetailsPage", "ShippingDetailsTitle", true);
			Control.compareText("ShippingDetailsPage", "EnterDetails", Generic.ReadFromExcel("EnterDetails", "DawnSpiels", 1));
			Control.objExists("ShippingDetailsPage", "FirstNameField", true);
			Control.objExists("ShippingDetailsPage", "LastNameField", true);
			Control.objExists("ShippingDetailsPage", "UnitHouseBldgField", true);
			Control.objExists("ShippingDetailsPage", "StreetNameField", true);
			Control.objExists("ShippingDetailsPage", "Province", true);
			Control.objExists("ShippingDetailsPage", "City", true);
			Control.objExists("ShippingDetailsPage", "Barangay", true);
			Control.objExists("ShippingDetailsPage", "VillageSubdivision", true);
			Control.objExists("ShippingDetailsPage", "ZipCode", true);
			Control.objExists("ShippingDetailsPage", "ShippingDetailsSpiel4", true);
			Control.objExists("ShippingDetailsPage", "EmailAddress", true);
			Control.objExists("ShippingDetailsPage", "MobileNumber", true);
			Control.objExists("ShippingDetailsPage", "IAgreeCheckbox", true);
			Control.objExists("ShippingDetailsPage", "ShippingNote", true);
			Control.objExists("ShippingDetailsPage", "OKButton", true);
			Control.objExists("ShippingDetailsPage", "BackButton", true);
			Control.takeScreenshot();
		}
		catch(Exception e)
		{
			Control.takeScreenshot();
			e.printStackTrace();
		}
	
		EnterShippingDetails();
		SelectProvince("Metro Manila");
		
		Control.click("ShippingDetailsPage", "IAgreeCheckbox");
		Thread.sleep(1000);
		Control.takeScreenshot();
		Control.click("ShippingDetailsPage", "OKButton");
		Control.customWait("AccountPage", "AddNewAddress", 60);
		Thread.sleep(6000);
		
		CurrentNumberOfAddress = Control.findElements("AccountPage", "AddressItem").size();
		System.out.println(InitialNumberOfAddress);
		System.out.println(CurrentNumberOfAddress);
		if(CurrentNumberOfAddress==InitialNumberOfAddress+1) {
			Generic.WriteTestData("Check added address",""," ","Address has been added","Address has been added","Pass");
		}else {
			Generic.WriteTestData("Check added address",""," ","Address has been added","Address was not added","Failed");
		}
		
		
	}
	
	public static void ChangePIN() throws Exception{
		Control.takeScreenshot();
		Control.click("Dashboard","Settings");
		Control.customWait("Settings", "SettingsTitle", 60);
		Control.takeScreenshot();
		Control.objExists("Settings", "SettingsTitle", true);
		Control.objExists("Settings", "Update6DigitPin", true);
		Control.takeScreenshot();
		Control.click("Settings","Update6DigitPin");
		Control.customWait("Settings", "UpdatePinTitle", 60);
		Control.takeScreenshot();
		Constant.driver.findElement(By.xpath("//input[@name='input-1']")).click();
		Actions action = new Actions(Constant.driver);
		action.sendKeys(Generic.ReadFromExcel("PIN", "AccountCreation", 1)).build().perform();
		Control.takeScreenshot();
		Control.click("Settings","NextButton");
		Control.customWait("Settings", "SettingsTitle", 60);
		Control.takeScreenshot();
		Control.objExists("Settings", "SettingsTitle", true);
		Control.objExists("Settings", "NewPinLabel", true);
		Control.objExists("Settings", "NewPinField", true);
		Control.objExists("Settings", "ConfirmNewPinLabel", true);
		Control.objExists("Settings", "ConfirmPinField", true);
		Control.objExists("Settings", "ConfirmButton", true);
		action.sendKeys(Generic.ReadFromExcel("NewPIN", "AccountCreation", 1)).build().perform();
		Control.takeScreenshot();
		action.sendKeys(Generic.ReadFromExcel("NewPIN", "AccountCreation", 1)).build().perform();
		Control.takeScreenshot();
		Control.click("Settings","ConfirmButton");
		Control.customWait("Settings", "SuccessPopup", 60);
		Control.takeScreenshot();
		Control.objExists("Settings", "SuccessPopup", true);
		Control.objExists("Settings", "SuccessSpiel", true);
		Control.objExists("Settings", "Login", true);
		Control.click("Settings","Login");
		Control.customWait("Settings", "SuccessPopup", 60);
		Control.takeScreenshot();
		Control.customWait("Login", "VerifyNumberSpiel", 60);
		Control.compareText("Login", "VerifyNumberSpiel", Generic.ReadFromExcel("VerifyNumberSpiel", "DawnSpiels", 1));
		Control.objExists("Login", "EnterGomoNumber", true);
		Control.objExists("Login", "LetsGoButton", true);
		Control.takeScreenshot();
		Control.click("Login", "EnterGomoNumber");
		Control.findElement("Login", "EnterGomoNumber").sendKeys(Generic.ReadFromExcel("GomoNumber", "AI_TestData", 1));
		Thread.sleep(2000);
		Control.takeScreenshot();
		Control.click("Login", "LetsGoButton");
		Control.customWait("Login", "VerifyPin", 60);
		Thread.sleep(2000);
		Control.takeScreenshot();
		Control.objExists("Login", "VerifyPin", true);
		Control.objExists("Login", "MobileNumber", true);
		Control.objExists("Login", "NotMyNumber", true);
		Control.objExists("Login", "EnterPin", true);
		Control.objExists("Login", "ForgotPin", true);
		Control.takeScreenshot();
		Constant.driver.findElement(By.xpath("//input[@name='input-1']")).click();
		action.sendKeys(Generic.ReadFromExcel("NewPIN", "AccountCreation", 1)).build().perform();
		Control.takeScreenshot();
		Control.customWait("Dashboard", "Dashboard", 60);
		Control.takeScreenshot();
		Control.objExists("Dashboard", "Dashboard", true);
	}

	public static void WaitGomoLogo() throws Exception
	{
		Constant.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		WebDriverWait wait1 = new WebDriverWait(Constant.driver,1);
		WebDriverWait wait2 = new WebDriverWait(Constant.driver,60);
		try {			
			wait1.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//img [@src='/content/dam/globe/gomo/GomoLogo_web.png' and @class='dawn-header__symbol']")));
		} 
		catch (Exception e)
		{
			//e.printStackTrace();
		}
		try {			
			wait2.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//img [@src='/content/dam/globe/gomo/GomoLogo_web.png' and @class='dawn-header__symbol']")));
		} catch (Exception e) {
			//e.printStackTrace();
			}
		
		Constant.driver.manage().timeouts().implicitlyWait(Constant.defaultBrowserTimeOut, TimeUnit.SECONDS);
	}
	
	
	
	
	public static void WaitPromoLoading() throws Exception
	{
		Constant.driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
		WebDriverWait wait1 = new WebDriverWait(Constant.driver,1);
		WebDriverWait wait2 = new WebDriverWait(Constant.driver,60);
		try {			
			wait1.until(ExpectedConditions.visibilityOfElementLocated(By.className("lds-spinner")));
		} 
		catch (Exception e)
		{
			//e.printStackTrace();
		}
		try {			
			wait2.until(ExpectedConditions.invisibilityOfElementLocated(By.className("lds-spinner")));
		} catch (Exception e) {
			//e.printStackTrace();
			}
		
		Constant.driver.manage().timeouts().implicitlyWait(Constant.defaultBrowserTimeOut, TimeUnit.SECONDS);
	}
	
	public static String CreateNameString(int length) throws Exception{
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder();
		Random random = new Random();
		for(int i = 0; i < length; i++) {
			// generate random index number
			int index = random.nextInt(alphabet.length());
			// get character specified by index
			// from the string
			char randomChar = alphabet.charAt(index);
			// append the character to string builder
			sb.append(randomChar);
		}
		String randomString = sb.toString();
	    String name = randomString.substring(0, 1).toUpperCase() + randomString.substring(1);
		return name;	
	}
	
	public static List<WebElement> findElements(String PageName, String locatorName)   {
		
		String locator,locatorTag,objectLocator;
		if (locatorName != null) {
			locator = Constant.Map.get(PageName).get(locatorName);
			String[] arrLocator = locator.split("#");
			 locatorTag = arrLocator[0].trim();
			 objectLocator = arrLocator[1].trim();
			 System.out.println(locatorTag);
			 System.out.println(objectLocator);
			 			
			try {
				if (locatorTag.equalsIgnoreCase("id")) {
					Constant.webelements = Constant.driver.findElements(By.id(objectLocator));
				} else if (locatorTag.equalsIgnoreCase("name")) {
					Constant.webelements = Constant.driver.findElements(By.name(objectLocator));	
				} else if (locatorTag.equalsIgnoreCase("xpath")) {
					Constant.webelements = Constant.driver.findElements(By.xpath(objectLocator));					
				} else if (locatorTag.equalsIgnoreCase("linkText")) {
					Constant.webelements = Constant.driver.findElements(By.linkText(objectLocator));					
				} else if (locatorTag.equalsIgnoreCase("Text")) {
					Constant.webelements = Constant.driver.findElements(By.partialLinkText(objectLocator));					
				} else if (locatorTag.equalsIgnoreCase("class")) {
					Constant.webelements = Constant.driver.findElements(By.className(objectLocator));					
				} else if (locatorTag.equalsIgnoreCase("tagName")) {
					Constant.webelements = Constant.driver.findElements(By.tagName(objectLocator));
				} else if (locatorTag.equalsIgnoreCase("css")) {
					Constant.webelements = Constant.driver.findElements(By.cssSelector(objectLocator));				
				} else {
					String error = "Please Check the Given Locator Syntax :"+ locator;
					error = error.replaceAll("'", "\"");
					return null;
				}
			} catch (Exception exception) {
				/*String error = "Please Check the Given Locator Syntax :"
						+ locator;
				error = error.replaceAll("'", "\"");
								exception.printStackTrace();*/
				return null;
			}
		}
		return Constant.webelements;
	}
	
	public static String getOtp(String otp, Boolean isStg) throws Exception {
		String endPoint;
		if (isStg)
			endPoint = "https://c0fs27xwue.execute-api.ap-southeast-1.amazonaws.com/stg/webtool/v1/get-otp-bd";
		else
			endPoint = "https://brskc46xre.execute-api.ap-southeast-1.amazonaws.com/dev/webtool/v1/get-otp-bd";

		URL url = new URL(endPoint);
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setRequestProperty("Accept", "application/json");
		con.setDoOutput(true);

		String jsonInputString = "{\"serviceNumber\": \"" + otp + "\"}";

		try (OutputStream os = con.getOutputStream()) {
			byte[] input = jsonInputString.getBytes("utf-8");
			os.write(input, 0, input.length);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"))) {
			StringBuilder response = new StringBuilder();
			String responseLine = null;
			while ((responseLine = br.readLine()) != null) {
				response.append(responseLine.trim());
			}
			// System.out.println(response.toString());
			String getResponse = response.toString();
			otp = getResponse.split("\"otp\":", 2)[1].replace("\"", "").replace("}", "");
			;
		}
		System.out.println("Otp: " + otp);
		return otp;
	}
	
	public static String getOtp2(String msisdn) throws Exception {

		String result = "";
		HttpPost post = new HttpPost("https://brskc46xre.execute-api.ap-southeast-1.amazonaws.com/dev/webtool/v1/get-otp-bd");
		String block = "{\"serviceNumber\": \"" + msisdn + "\"}";
		post.setEntity(new StringEntity(block));
		try (CloseableHttpClient httpClient = HttpClients.createDefault();
			CloseableHttpResponse response = httpClient.execute(post)) {
			result = EntityUtils.toString(response.getEntity());
		}
		String otp = result.split("\"otp\":", 2)[1].replace("\"", "").replace("}", "");;
		System.out.println(otp);
		return otp;
	}
	 public static void DatePick(int year) throws Exception{
			Constant.driver.findElement(By.xpath("//font[text()='8']")).click();

	 }

	
	 public static void DatePicker(int year) throws Exception{
		String xpathMonth = "//div[@class='vdp-datepicker__calendar']//span[@class='day__month_btn up']";
		String xpathYear = "//div[@class='v-date-picker-header__value']//button[1]";
		String xpathYearNext = "//div[@class='vdp-datepicker__calendar']//span[@class='month__year_btn up']//preceding-sibling::span";
		String xpathYearPrev = "//div[@class='vdp-datepicker__calendar']//span[@class='month__year_btn up']//following-sibling::span";
		int currentYear = 0;
		//Control.findElement("AddMemberGroup", "ExpDate").click();
		Thread.sleep(1000);
		Constant.driver.findElement(By.xpath(xpathYear)).click();
		Thread.sleep(1000);
		while(year!=currentYear) {
			System.out.println("********************************");
			Control.click("AddMemberGroup", "ExpDate");
			System.out.println("********************************");
			//String s = Constant.driver.findElement(By.xpath("//div[@class='v-date-picker-header__value']//button[1]")).getText();
			//System.out.println(s);
			//currentYear=Integer.parseInt(s);
			//System.out.println("Current Year: "+currentYear);
			//System.out.println("Year: "+year);
			//if(currentYear==year) {
				Thread.sleep(1000);
				//Control.click("AddMemberGroup", "ExpDate");
				Constant.driver.findElement(By.xpath("//div[text()='Sept']")).click();
				Thread.sleep(1000);
				Control.click("AddMemberGroup", "ExpDate");
				Constant.driver.findElement(By.xpath("(//button[contains(@class,'v-btn v-btn--text')])[3]")).click();
				Thread.sleep(1000);
				break;
			/*}else if(currentYear<year) {
				try
				{
					Constant.driver.findElement(By.xpath(xpathYearPrev)).click();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}else if(currentYear>year) {
				try
				{
					Constant.driver.findElement(By.xpath(xpathYearNext)).click();
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
			}*/
		}
		
	 }
	 



	public static void LoggingIn(String email, String password) throws Exception {
		Control.customWait("LoginPage", "GlobeLogo", 60);
		Control.takeScreenshot();
		Control.customWait("LoginPage", "GlobeCbrmDataMart", 60);
		Control.customWait("LoginPage", "LoginWithGoogle", 60);
		Control.objExists("LoginPage", "GlobeLogo", true);
		Control.objExists("LoginPage", "GlobeCbrmDataMart", true);
		Control.objExists("LoginPage", "LoginWithGoogle", true);
		Control.click("LoginPage", "LoginWithGoogle");
		Control.findElement("LoginPage", "Email").sendKeys(email);
		Control.findElement("LoginPage", "Next1").click();
		Thread.sleep(5000);
		Control.findElement("LoginPage", "Password").sendKeys(password);
		Thread.sleep(5000);
		Control.findElement("LoginPage", "Next2").click();
		Thread.sleep(20000);
	//	Control.findElement("LoginPage", "Allow").click();
		Control.takeScreenshot();
		Thread.sleep(10000);
		Control.customWait("HomePage", "GlobeLogo", 60);
		//Thread.sleep(2000);
		Control.customWait("HomePage", "UserName", 60);
		Control.takeScreenshot();
		Control.customWait("HomePage", "UserProfilePic", 60);
		Thread.sleep(3000);
		Control.objExists("HomePage", "GlobeLogo", true);
		Control.objExists("HomePage", "UserName", true);
		Control.objExists("HomePage", "UserProfilePic", true);
		Control.findElements("HomePage", "4Tiles");
		Control.customWait("HomePage", "ReportandDataRepository", 60);
		Control.customWait("HomePage", "EtlScriptsandJobs", 60);
		Control.customWait("HomePage", "DatamartTables", 60);
		Control.customWait("HomePage", "SystemConfiguration", 60);
		Thread.sleep(3000);
		Control.objExists("HomePage", "ReportandDataRepository", true);
		Control.objExists("HomePage", "EtlScriptsandJobs", true);
		Control.objExists("HomePage", "DatamartTables", true);
		Control.objExists("HomePage", "SystemConfiguration", true);
		Control.compareText("HomePage", "ReportandDataRepository", Generic.ReadFromExcel("FirstTile", "Tiles", 1));
		Control.compareText("HomePage", "EtlScriptsandJobs", Generic.ReadFromExcel("SecTile", "Tiles", 1));
		Control.takeScreenshot();
		Control.compareText("HomePage", "DatamartTables", Generic.ReadFromExcel("ThirdTile", "Tiles", 1));
		Control.compareText("HomePage", "SystemConfiguration", Generic.ReadFromExcel("FourthTile", "Tiles", 1));

	}
	public static void LogIn(String email, String password) throws Exception 
	{
		Control.customWait("LoginPage", "GlobeLogo", 60);
		Control.takeScreenshot();
		Control.customWait("LoginPage", "GlobeCbrmDataMart", 60);
		Control.customWait("LoginPage", "LoginWithGoogle", 60);
		Control.click("LoginPage", "LoginWithGoogle");
		Control.objExists("LoginPage", "Select_account", true);
	}
	



	public static void SystemConfiguration() throws Exception {
		Control.click("HomePage", "SystemConfiguration");
		Control.customWait("SystemConfigPage", "UserAccessManagement", 60);
		Control.customWait("SystemConfigPage", "Environment", 60);
		Control.takeScreenshot();
		Control.customWait("SystemConfigPage", "DateRetentionPolicy", 60);
		Control.customWait("SystemConfigPage", "DateIngestion", 60);
		Control.customWait("SystemConfigPage", "SystemSettings", 60);
		Control.customWait("SystemConfigPage", "SystemLogs", 60);
		//customWait("SystemConfigPage", "TestTile1", 60);
		//Control.customWait("SystemConfigPage", "TestTile2", 60);
		Control.objExists("SystemConfigPage", "UserAccessManagement", true);
		Control.objExists("SystemConfigPage", "Environment", true);
		Control.objExists("SystemConfigPage", "DateRetentionPolicy", true);
		Control.objExists("SystemConfigPage", "DateIngestion", true);
		Control.objExists("SystemConfigPage", "SystemSettings", true);
		Control.objExists("SystemConfigPage", "SystemLogs", true);
		//Control.objExists("SystemConfigPage", "TestTile1", true);
		//Control.objExists("SystemConfigPage", "TestTile2", true);
		//Control.objExists("SystemConfigPage", "ScrollDown", true);
		Constant.driver.navigate().back();
		Control.takeScreenshot();
		Thread.sleep(3000);
		Control.objExists("HomePage", "ReportandDataRepository", true);
		Control.objExists("HomePage", "EtlScriptsandJobs", true);
		Control.objExists("HomePage", "DatamartTables", true);
		Control.objExists("HomePage", "SystemConfiguration", true);
		Control.click("HomePage", "SystemConfiguration");
		Thread.sleep(3000);
		/*JavascriptExecutor js = (JavascriptExecutor) Constant.driver;
		js.executeScript("window.scrollBy(0,100)", "");
		Control.takeScreenshot();
		Thread.sleep(3000);
		Control.click("SystemConfigPage", "ScrollDown");
		Thread.sleep(3000);
		Control.objExists("SystemConfigPage", "TestTile1", true);
		Control.objExists("SystemConfigPage", "TestTile2", true);
		Control.objExists("SystemConfigPage", "TestTile3", true);
		Control.objExists("SystemConfigPage", "TestTile4", true);
		Control.takeScreenshot();*/	
		
	}



	public static void UserManagementValidation() throws Exception {
		Control.click("HomePage", "SystemConfiguration");
		Control.takeScreenshot();
		Control.customWait("SystemConfigPage", "UserAccessManagement", 60);
		Control.objExists("SystemConfigPage", "UserAccessManagement", true);
		Control.click("SystemConfigPage", "UserAccessManagement");
		Control.customWait("UserManagementPage", "HamburgerIcon", 60);
		Control.takeScreenshot();
		Control.customWait("UserManagementPage", "GlobeLogo", 60);
		Control.customWait("UserManagementPage", "UserProfile", 60);
		Control.customWait("UserManagementPage", "Groups", 60);
		Control.customWait("UserManagementPage", "Roles", 60);
		Control.customWait("UserManagementPage", "Users", 60);
		Control.customWait("UserManagementPage", "SearchField", 60);
		Control.customWait("UserManagementPage", "Filter", 60);
		Control.customWait("UserManagementPage", "CreateGroup", 60);
		Control.customWait("UserManagementPage", "HamburgerIcon", 60);
		Control.objExists("UserManagementPage", "HamburgerIcon", true);
		Control.objExists("UserManagementPage", "GlobeLogo", true);
		Control.objExists("UserManagementPage", "UserProfile", true);
		Control.objExists("UserManagementPage", "Groups", true);
		Control.objExists("UserManagementPage", "Roles", true);
		Control.objExists("UserManagementPage", "Users", true);
		Control.objExists("UserManagementPage", "SearchField", true);
		Control.objExists("UserManagementPage", "Filter", true);
		Control.objExists("UserManagementPage", "CreateGroup", true);
		Control.takeScreenshot();
		Control.scroll("UserManagementPage","Pagination");
		Control.customWait("UserManagementPage", "Pagination", 60);
		Control.objExists("UserManagementPage", "Pagination", true);
		Thread.sleep(2000);
		Control.customWait("UserManagementPage","PreviousArrow",60);
		Control.objExists("UserManagementPage", "PreviousArrow", true);
		Control.takeScreenshot();
		Control.customWait("UserManagementPage","BackArrow",60);
		Control.objExists("UserManagementPage", "BackArrow", true);
		Control.customWait("UserManagementPage","Page1",60);
		Control.objExists("UserManagementPage", "Page1", true);
		Control.customWait("UserManagementPage","Page2",60);
		Control.objExists("UserManagementPage", "Page2", true);

		

			
	}



	public static void CreateGroup() throws Exception {
		Control.click("HomePage", "SystemConfiguration");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Control.objExists("SystemConfigPage", "UserAccessManagement", true);
		Control.click("SystemConfigPage", "UserAccessManagement");
		Control.takeScreenshot();
		//Thread.sleep(5000);
		Control.customWait("UserManagementPage", "CreateGroup", 60);
		Control.objExists("UserManagementPage", "CreateGroup", true);
		Thread.sleep(3000);
		Control.click("UserManagementPage", "CreateGroup");
		Control.takeScreenshot();
		Thread.sleep(3000);
		Control.customWait("UserManagementPage", "CreateGroupHeader", 60);
		Control.customWait("UserManagementPage", "Asterisk", 60);
		Control.customWait("UserManagementPage", "Xicon", 60);
		Control.customWait("UserManagementPage", "GroupNameField", 60);
		Control.customWait("UserManagementPage", "CharCounter", 60);
		Control.customWait("UserManagementPage", "GroupDescriptionField", 60);
		Control.customWait("UserManagementPage", "CharCounter1", 60);
		Control.customWait("UserManagementPage", "Cancel", 60);
		Control.customWait("UserManagementPage", "Create", 60);
		Control.objExists("UserManagementPage", "CreateGroupHeader", true);
		Control.objExists("UserManagementPage", "Asterisk", true);
		Control.objExists("UserManagementPage", "Xicon", true);
		Control.objExists("UserManagementPage", "GroupNameField", true);
		Control.takeScreenshot();
		Control.objExists("UserManagementPage", "CharCounter", true);
		Control.objExists("UserManagementPage", "GroupDescriptionField", true);
		Control.objExists("UserManagementPage", "CharCounter1", true);
		Control.objExists("UserManagementPage", "Cancel", true);
		Control.objExists("UserManagementPage", "Create", true);
		Control.click("UserManagementPage", "Xicon");
		Thread.sleep(2000);
		Control.click("UserManagementPage", "CreateGroup");
		Control.click("UserManagementPage", "Cancel");
		Thread.sleep(2000);
		Control.click("UserManagementPage", "CreateGroup");
		Thread.sleep(4000);
		Control.enterText("UserManagementPage", "GroupDescriptionTextField", Control.getAlphaNumericString(5));
		Control.disabled("UserManagementPage", "CreateButton");
		Thread.sleep(3000);
		Control.takeScreenshot();
		Control.click("UserManagementPage", "Cancel");
		Control.click("UserManagementPage", "CreateGroup");
		Thread.sleep(4000);
		Control.enterText("UserManagementPage", "GroupNameTextField", Control.getAlphaNumericString(55));
		Control.takeScreenshot();
		Control.enterText("UserManagementPage", "GroupDescriptionTextField", Control.getAlphaNumericString(260));
		Control.takeScreenshot();
		Control.click("UserManagementPage", "Xicon");
		Control.click("UserManagementPage", "CreateGroup");
		Control.enterText("UserManagementPage", "GroupNameTextField", Control.getAlphaNumericString(5));
		Control.enterText("UserManagementPage", "GroupDescriptionTextField",Control.getAlphaNumericString(10));
		Control.click("UserManagementPage", "CreateButton");
		Control.customWait("UserManagementPage", "Xicon1", 60);
		Control.customWait("UserManagementPage", "Avatar", 60);
		Control.customWait("UserManagementPage", "CreateGroupText",60);
		Control.customWait("UserManagementPage", "AreYouSureText", 60);
		Control.customWait("UserManagementPage", "Cancel1", 60);
		Control.customWait("UserManagementPage", "YesCreateButton", 60);
		Control.objExists("UserManagementPage", "Xicon1", true);
		Control.objExists("UserManagementPage", "Avatar", true);
		Control.objExists("UserManagementPage", "CreateGroupText", true);
		Control.objExists("UserManagementPage", "AreYouSureText", true);
		Control.objExists("UserManagementPage", "Cancel1", true);
		Control.objExists("UserManagementPage", "YesCreateButton", true);
		Control.takeScreenshot();
		Control.click("UserManagementPage", "Cancel1");
		Control.takeScreenshot();
		Control.enterText("UserManagementPage", "GroupNameTextField", Control.getAlphaNumericString(45));
		Control.enterText("UserManagementPage", "GroupDescriptionTextField", Control.getAlphaNumericString(245));
		Control.takeScreenshot();
		Control.click("UserManagementPage", "CreateButton");
		Control.click("UserManagementPage", "YesCreateButton");
		Control.takeScreenshot();
		Thread.sleep(10000);
		Control.customWait("UserManagementPage", "Xicon2", 60);
		Control.customWait("UserManagementPage", "CheckMark", 60);
		Control.customWait("UserManagementPage", "Success", 60);
		Control.customWait("UserManagementPage", "ANewGroupCreated", 60);
		Control.customWait("UserManagementPage", "OkButton", 60);
		Control.objExists("UserManagementPage", "Xicon2", true);
		Control.objExists("UserManagementPage", "CheckMark", true);
		Control.objExists("UserManagementPage", "Success", true);
		Control.objExists("UserManagementPage", "ANewGroupCreated", true);
		Control.objExists("UserManagementPage", "OkButton", true);
		Control.takeScreenshot();
		Control.click("UserManagementPage", "OkButton");
		Thread.sleep(10000);










		



		



		

        

		










		

		

		


		
	}



	public static void CreateGroupXicon() throws Exception {
		Control.click("HomePage", "SystemConfiguration");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Control.objExists("SystemConfigPage", "UserAccessManagement", true);
		Control.click("SystemConfigPage", "UserAccessManagement");
		Control.takeScreenshot();
		//Thread.sleep(5000);
		Control.customWait("UserManagementPage", "CreateGroup", 60);
		Control.objExists("UserManagementPage", "CreateGroup", true);
		Thread.sleep(3000);
		Control.click("UserManagementPage", "CreateGroup");
		Control.takeScreenshot();
		Thread.sleep(3000);
		Control.customWait("UserManagementPage", "CreateGroupHeader", 60);
		Control.customWait("UserManagementPage", "Asterisk", 60);
		Control.customWait("UserManagementPage", "Xicon", 60);
		Control.customWait("UserManagementPage", "GroupNameField", 60);
		Control.customWait("UserManagementPage", "CharCounter", 60);
		Control.customWait("UserManagementPage", "GroupDescriptionField", 60);
		Control.customWait("UserManagementPage", "CharCounter1", 60);
		Control.customWait("UserManagementPage", "Cancel", 60);
		Control.customWait("UserManagementPage", "Create", 60);
		Control.objExists("UserManagementPage", "CreateGroupHeader", true);
		Control.objExists("UserManagementPage", "Asterisk", true);
		Control.objExists("UserManagementPage", "Xicon", true);
		Control.objExists("UserManagementPage", "GroupNameField", true);
		Control.takeScreenshot();
		Control.objExists("UserManagementPage", "CharCounter", true);
		Control.objExists("UserManagementPage", "GroupDescriptionField", true);
		Control.objExists("UserManagementPage", "CharCounter1", true);
		Control.objExists("UserManagementPage", "Cancel", true);
		Control.objExists("UserManagementPage", "Create", true);
		Control.enterText("UserManagementPage", "GroupNameTextField", Control.getAlphaNumericString(50));
		Control.enterText("UserManagementPage", "GroupDescriptionTextField", Control.getAlphaNumericString(255));
		Control.takeScreenshot();
		Control.click("UserManagementPage", "CreateButton");
		Control.click("UserManagementPage", "YesCreateButton");
		Control.takeScreenshot();
		Thread.sleep(10000);
		Control.customWait("UserManagementPage", "Xicon2", 60);
		Control.customWait("UserManagementPage", "CheckMark", 60);
		Control.customWait("UserManagementPage", "Success", 60);
		Control.customWait("UserManagementPage", "ANewGroupCreated", 60);
		Control.customWait("UserManagementPage", "OkButton", 60);
		Control.objExists("UserManagementPage", "Xicon2", true);
		Control.objExists("UserManagementPage", "CheckMark", true);
		Control.objExists("UserManagementPage", "Success", true);
		Control.objExists("UserManagementPage", "ANewGroupCreated", true);
		Control.objExists("UserManagementPage", "OkButton", true);
		Control.takeScreenshot();
		Control.click("UserManagementPage", "Xicon2");
		Thread.sleep(20000);
		Control.takeScreenshot();


		
		
	}



	public static void GroupValidation() throws Exception {
		Control.click("HomePage", "SystemConfiguration");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Control.objExists("SystemConfigPage", "UserAccessManagement", true);
		Control.click("SystemConfigPage", "UserAccessManagement");
		Control.takeScreenshot();
		Control.customWait("GroupValidationPage", "AddMemberLink", 60);
		Control.customWait("GroupValidationPage", "GroupProfile", 60);
		Control.customWait("GroupValidationPage", "GroupName", 60);
		Control.customWait("GroupValidationPage", "MembersLink", 60);
		Control.customWait("GroupValidationPage", "GroupDescription", 60);
		Control.customWait("GroupValidationPage", "EditGroup", 60);
		Control.customWait("GroupValidationPage", "ReadMore", 60);
		Control.objExists("GroupValidationPage", "AddMemberLink", true);
		Control.objExists("GroupValidationPage", "GroupProfile", true);
		Control.objExists("GroupValidationPage", "GroupName", true);
		Control.objExists("GroupValidationPage", "MembersLink", true);
		Control.objExists("GroupValidationPage", "GroupDescription", true);
		Control.objExists("GroupValidationPage", "EditGroup", true);
		Control.objExists("GroupValidationPage", "ReadMore", true);
		Control.takeScreenshot();
		JavascriptExecutor js = (JavascriptExecutor) Constant.driver;
		js.executeScript("window.scrollBy(0,50)");
		Control.hover("GroupValidationPage", "GroupName");
		Control.takeScreenshot();
		//JavascriptExecutor js = (JavascriptExecutor) Constant.driver;
		//js.executeScript("window.scrollBy(0,50)");
		Control.scroll("GroupValidationPage", "ReadMore");
		Control.js_click("GroupValidationPage", "ReadMore");
		Control.takeScreenshot();
		Control.js_click("GroupValidationPage", "ReadLess");
		Control.takeScreenshot();



		

		





		
	}



	public static void EditGroup() throws Exception {
		Control.click("HomePage", "SystemConfiguration");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Control.objExists("SystemConfigPage", "UserAccessManagement", true);
		Control.click("SystemConfigPage", "UserAccessManagement");
		Control.takeScreenshot();
		//Control.customWait("GroupValidationPage", "GroupName", 60);
		//Control.objExists("GroupValidationPage", "GroupName", true);
		Control.customWait("GroupValidationPage", "EditGroup", 60);
		Control.objExists("GroupValidationPage", "EditGroup", true);
		Control.takeScreenshot();
		Control.js_click("GroupValidationPage", "EditGroup");
		Control.takeScreenshot();
		Control.customWait("GroupValidationPage", "EditGroupWindow", 60);
		Control.objExists("GroupValidationPage", "EditGroupWindow", true);
		Control.customWait("GroupValidationPage", "EditGroupHeader", 60);
		Control.objExists("GroupValidationPage", "EditGroupHeader", true);
		Control.customWait("GroupValidationPage", "Asterisk", 60);
		Control.objExists("GroupValidationPage", "Asterisk", true);
		Control.customWait("GroupValidationPage", "Xicon", 60);
		Control.objExists("GroupValidationPage", "Xicon", true);
		Control.customWait("GroupValidationPage", "GroupNameField", 60);
		Control.objExists("GroupValidationPage", "GroupNameField", true);
		//Control.enterText("GroupValidationPage", "GroupNameField", Control.getAlphaNumericString(10));
		Control.takeScreenshot();
		Control.customWait("GroupValidationPage", "GroupDescriptionField", 60);
		Control.objExists("GroupValidationPage", "GroupDescriptionField", true);
		//Control.enterText("GroupValidationPage", "GroupDescriptionField", Control.getAlphaNumericString(15));
		Control.takeScreenshot();
		Control.customWait("GroupValidationPage", "CancelButton", 60);
		Control.objExists("GroupValidationPage", "CancelButton", true);
		Control.customWait("GroupValidationPage", "UpdateButton", 60);
		Control.objExists("GroupValidationPage", "UpdateButton", true);
		Control.takeScreenshot();
		Thread.sleep(3000);
		Control.click("GroupValidationPage", "Xicon");
		Control.takeScreenshot();
		Control.js_click("GroupValidationPage", "EditGroup");
		Control.click("GroupValidationPage", "CancelButton");
		Control.takeScreenshot();
		Control.js_click("GroupValidationPage", "EditGroup");
		Control.enterText("GroupValidationPage", "GroupNameField", Control.getAlphaNumericString(10));
		Control.enterText("GroupValidationPage", "GroupDescriptionField", Control.getAlphaNumericString(15));
		Control.takeScreenshot();
		Control.click("GroupValidationPage", "UpdateButton");
		Control.customWait("UpdateGroupPage", "Xicon", 60);
		Control.objExists("UpdateGroupPage", "Xicon", true);
		Control.customWait("UpdateGroupPage", "Avatar", 60);
		Control.objExists("UpdateGroupPage", "Avatar", true);
		Control.customWait("UpdateGroupPage", "UpdateGroupHeader", 60);
		Control.objExists("UpdateGroupPage", "UpdateGroupHeader", true);
		Control.customWait("UpdateGroupPage", "AreYouSureText", 60);
		Control.objExists("UpdateGroupPage", "AreYouSureText", true);
		Control.customWait("UpdateGroupPage", "CancelButtonn", 60);
		Control.objExists("UpdateGroupPage", "CancelButtonn", true);
		Control.customWait("UpdateGroupPage", "YesUpdateButtonn", 60);
		Control.objExists("UpdateGroupPage", "YesUpdateButtonn", true);
		Control.takeScreenshot();
		Control.js_click("UpdateGroupPage", "CancelButtonn");
		Thread.sleep(4000);
		Control.takeScreenshot();
		Control.click("GroupValidationPage", "UpdateButton");
		Control.js_click("UpdateGroupPage", "YesUpdateButtonn");
		Control.customWait("UpdateGroupPage", "Xicon1", 60);
		Control.objExists("UpdateGroupPage", "Xicon1", true);
		Control.customWait("UpdateGroupPage", "CheckMark", 60);
		Control.objExists("UpdateGroupPage", "CheckMark", true);
		Control.customWait("UpdateGroupPage", "Success", 60);
		Control.objExists("UpdateGroupPage", "Success", true);
		Control.takeScreenshot();
		Control.customWait("UpdateGroupPage", "SuccessfullyUpdated", 60);
		Control.objExists("UpdateGroupPage", "SuccessfullyUpdated", true);
		Control.customWait("UpdateGroupPage", "OkButton", 60);
		Control.objExists("UpdateGroupPage", "OkButton", true);
		Control.takeScreenshot();
		Control.click("UpdateGroupPage", "OkButton");
		Control.customWait("UpdateGroupPage", "GroupName", 60);
		Control.takeScreenshot();
		Control.getMessageContent("UpdateGroupPage", "GroupName");
		
	}



	public static void EditGroupXicon() throws Exception {
		Control.click("HomePage", "SystemConfiguration");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Control.objExists("SystemConfigPage", "UserAccessManagement", true);
		Control.click("SystemConfigPage", "UserAccessManagement");
		Control.takeScreenshot();
		Control.customWait("GroupValidationPage", "EditGroup", 60);
		Control.objExists("GroupValidationPage", "EditGroup", true);
		Control.takeScreenshot();
		Control.js_click("GroupValidationPage", "EditGroup");
		Control.takeScreenshot();
		Control.customWait("GroupValidationPage", "EditGroupWindow", 60);
		Control.objExists("GroupValidationPage", "EditGroupWindow", true);
		Control.customWait("GroupValidationPage", "EditGroupHeader", 60);
		Control.objExists("GroupValidationPage", "EditGroupHeader", true);
		Control.customWait("GroupValidationPage", "Asterisk", 60);
		Control.objExists("GroupValidationPage", "Asterisk", true);
		Control.customWait("GroupValidationPage", "Xicon", 60);
		Control.objExists("GroupValidationPage", "Xicon", true);
		Control.customWait("GroupValidationPage", "GroupNameField", 60);
		Control.objExists("GroupValidationPage", "GroupNameField", true);
		Control.takeScreenshot();
		Control.customWait("GroupValidationPage", "GroupDescriptionField", 60);
		Control.objExists("GroupValidationPage", "GroupDescriptionField", true);
		Control.takeScreenshot();
		Control.customWait("GroupValidationPage", "CancelButton", 60);
		Control.objExists("GroupValidationPage", "CancelButton", true);
		Control.customWait("GroupValidationPage", "UpdateButton", 60);
		Control.objExists("GroupValidationPage", "UpdateButton", true);
		Control.enterText("GroupValidationPage", "GroupNameField", Control.getAlphaNumericString(10));
		Control.enterText("GroupValidationPage", "GroupDescriptionField", Control.getAlphaNumericString(15));
		Control.click("GroupValidationPage", "UpdateButton");
		Control.js_click("UpdateGroupPage", "YesUpdateButtonn");
		Control.customWait("UpdateGroupPage", "Xicon1", 60);
		Control.objExists("UpdateGroupPage", "Xicon1", true);
		Control.customWait("UpdateGroupPage", "CheckMark", 60);
		Control.objExists("UpdateGroupPage", "CheckMark", true);
		Control.customWait("UpdateGroupPage", "Success", 60);
		Control.objExists("UpdateGroupPage", "Success", true);
		Control.takeScreenshot();
		Control.customWait("UpdateGroupPage", "SuccessfullyUpdated", 60);
		Control.objExists("UpdateGroupPage", "SuccessfullyUpdated", true);
		Control.customWait("UpdateGroupPage", "OkButton", 60);
		Control.objExists("UpdateGroupPage", "OkButton", true);
		Control.takeScreenshot();
		Control.click("UpdateGroupPage", "Xicon1");
		Control.customWait("UpdateGroupPage", "GroupName", 60);
		Control.takeScreenshot();
		Control.getMessageContent("UpdateGroupPage", "GroupName");	
		
	}



	public static void AddGroupMember() throws Exception {
		Control.click("HomePage", "SystemConfiguration");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Control.objExists("SystemConfigPage", "UserAccessManagement", true);
		Control.click("SystemConfigPage", "UserAccessManagement");
		Control.takeScreenshot();
		Control.customWait("AddMemberGroup", "AddMemberLink", 60);
		Control.objExists("AddMemberGroup", "AddMemberLink", true);
		Control.click("AddMemberGroup", "AddMemberLink");
		Control.takeScreenshot();
		Thread.sleep(4000);
		Control.customWait("AddMemberGroup", "Xicon", 60);
		Control.objExists("AddMemberGroup", "Xicon", true);
		Control.customWait("AddMemberGroup", "AddMemberText", 60);
		Control.objExists("AddMemberGroup", "AddMemberText", true);
		Control.customWait("AddMemberGroup", "Asterisk", 60);
		Control.objExists("AddMemberGroup", "Asterisk", true);
		Control.customWait("AddMemberGroup", "EmailAddress", 60);
		Control.objExists("AddMemberGroup", "EmailAddress", true);
		Control.customWait("AddMemberGroup", "Group", 60);
		Control.objExists("AddMemberGroup", "Group", true);
		Control.customWait("AddMemberGroup", "Role", 60);
		Control.objExists("AddMemberGroup", "Role", true);
		Control.customWait("AddMemberGroup", "DateOfExpiry", 60);
		Control.objExists("AddMemberGroup", "DateOfExpiry", true);
		Control.takeScreenshot();
		Control.customWait("AddMemberGroup", "CheckBox", 60);
		Control.objExists("AddMemberGroup", "CheckBox", true);
		Control.customWait("AddMemberGroup", "EtlApprover", 60);
		Control.objExists("AddMemberGroup", "EtlApprover", true);
		Control.customWait("AddMemberGroup", "CancelButton", 60);
		Control.objExists("AddMemberGroup", "CancelButton", true);
		Control.customWait("AddMemberGroup", "AddMemberButton", 60);
		Control.objExists("AddMemberGroup", "AddMemberButton", true);
		Control.js_click("AddMemberGroup", "Xicon");
		Control.takeScreenshot();
		Control.click("AddMemberGroup", "AddMemberLink");
		Control.customWait("AddMemberGroup", "CancelButton", 60);
		Control.scroll("AddMemberGroup", "CancelButton");
		Control.click("AddMemberGroup", "CancelButton");
		Control.click("AddMemberGroup", "AddMemberLink");
		Control.customWait("AddMemberGroup", "EmailInput", 60);
		Control.findElement("AddMemberGroup", "EmailInput").click();
		Control.SelectDropDown("AddMemberGroup","DdList","alexis.reyes@globe.com.ph");
		Thread.sleep(5000);
		Control.click("AddMemberGroup", "Asterisk");
		Control.customWait("AddMemberGroup","ExpDate",60);
		Control.click("AddMemberGroup", "ExpDate");
		DatePicker(2022);
		Control.takeScreenshot();
		Control.customWait("AddMemberGroup", "RoleInput", 60);
		Control.findElement("AddMemberGroup", "RoleInput").click();
		Control.SelectDropDown("AddMemberGroup","DdList","Beta Tester Role");
		Control.Checkbox("AddMemberGroup", "CheckBox", "ON");
		Control.customWait("AddMemberGroup", "EtlInput", 60);
		Control.findElement("AddMemberGroup", "EtlInput").click();
		Control.SelectDropDown("AddMemberGroup","DdList","zmayncierto@globe.com.ph");
		Thread.sleep(5000);
		Control.click("AddMemberGroup", "AddMemberButton");
		Control.customWait("AddMemberGroup", "Xicon1", 60);
		Control.objExists("AddMemberGroup", "Xicon1", true);
		Control.customWait("AddMemberGroup", "Avatar", 60);
		Control.objExists("AddMemberGroup", "Avatar", true);
		Control.customWait("AddMemberGroup", "AddMembersText", 60);
		Control.objExists("AddMemberGroup", "AddMembersText", true);
		Control.takeScreenshot();
		Control.customWait("AddMemberGroup", "AreYouSureText", 60);
		Control.objExists("AddMemberGroup", "AreYouSureText", true);
		Control.customWait("AddMemberGroup", "CancelButton1", 60);
		Control.objExists("AddMemberGroup", "CancelButton1", true);
		Control.customWait("AddMemberGroup", "ProceedButton", 60);
		Control.objExists("AddMemberGroup", "ProceedButton", true);
		Control.click("AddMemberGroup", "ProceedButton");
		Control.takeScreenshot();
		Control.customWait("AddMemberGroup", "Xicon2", 60);
		Control.objExists("AddMemberGroup", "Xicon2", true);
		Control.customWait("AddMemberGroup", "CheckMark", 60);
		Control.objExists("AddMemberGroup", "CheckMark", true);
		Control.customWait("AddMemberGroup", "Success", 60);
		Control.objExists("AddMemberGroup", "Success", true);
		Control.customWait("AddMemberGroup", "SuccessfullyAddedText", 60);
		Control.objExists("AddMemberGroup", "SuccessfullyAddedText", true);
		Control.customWait("AddMemberGroup", "OkButton", 60);
		Control.objExists("AddMemberGroup", "OkButton", true);
		Control.takeScreenshot();
		Control.click("AddMemberGroup", "OkButton");
		int i=3;
		i++;
		Constant.driver.findElement(By.xpath("(//U[@data-v-7641c846=''][text()=' "+i+"Member(s)'])[1]")).click();
		//Control.customWait("AddMemberGroup", "MembersLink", 60);
		//Control.objExists("AddMemberGroup", "MembersLink", true);
		//Control.click("AddMemberGroup", "MembersLink");
		//Thread.sleep(10000);
		Control.takeScreenshot();
		ValidateGroupMembers();
		
	}



	public static void ValidateGroupMembers() throws Exception {
		Control.customWait("GroupMembersPage", "GroupName", 60);
		Control.objExists("GroupMembersPage", "GroupName", true);
		Control.customWait("GroupMembersPage", "GroupDescription", 60);
		Control.objExists("GroupMembersPage", "GroupDescription", true);
		Control.customWait("GroupMembersPage", "SearchMembers", 60);
		Control.objExists("GroupMembersPage", "SearchMembers", true);
		Control.customWait("GroupMembersPage", "DeActivateButton", 60);
		Control.objExists("GroupMembersPage", "DeActivateButton", true);
		Control.customWait("GroupMembersPage", "SelectGroup", 60);
		Control.objExists("GroupMembersPage", "SelectGroup", true);
		Control.customWait("GroupMembersPage", "TransferButton", 60);
		Control.objExists("GroupMembersPage", "TransferButton", true);
		Control.customWait("GroupMembersPage", "NetworkId", 60);
		Control.objExists("GroupMembersPage", "NetworkId", true);
		Control.customWait("GroupMembersPage", "FullName", 60);
		Control.objExists("GroupMembersPage", "FullName", true);
		Control.customWait("GroupMembersPage", "EmailAddress", 60);
		Control.objExists("GroupMembersPage", "EmailAddress", true);
		Control.customWait("GroupMembersPage", "Group", 60);
		Control.objExists("GroupMembersPage", "Group", true);
		Control.customWait("GroupMembersPage", "Role", 60);
		Control.objExists("GroupMembersPage", "Role", true);
		Control.customWait("GroupMembersPage", "AccountStatus", 60);
		Control.objExists("GroupMembersPage", "AccountStatus", true);

		
		
		
		
	}



	public static void AddGroupMemberAlreadyRegisterd() throws Exception {
		Control.click("HomePage", "SystemConfiguration");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Control.objExists("SystemConfigPage", "UserAccessManagement", true);
		Control.click("SystemConfigPage", "UserAccessManagement");
		Control.takeScreenshot();
		Control.customWait("AddMemberGroupPage1", "AddMemberLink", 60);
		Control.objExists("AddMemberGroupPage1", "AddMemberLink", true);
		Control.click("AddMemberGroupPage1", "AddMemberLink");
		Control.takeScreenshot();
		Thread.sleep(4000);
		Control.customWait("AddMemberGroupPage1", "Xicon", 60);
		Control.objExists("AddMemberGroupPage1", "Xicon", true);
		Control.customWait("AddMemberGroupPage1", "AddMemberText", 60);
		Control.objExists("AddMemberGroupPage1", "AddMemberText", true);
		Control.customWait("AddMemberGroupPage1", "Asterisk", 60);
		Control.objExists("AddMemberGroupPage1", "Asterisk", true);
		Control.customWait("AddMemberGroupPage1", "EmailAddress", 60);
		Control.objExists("AddMemberGroupPage1", "EmailAddress", true);
		Control.customWait("AddMemberGroupPage1", "Group", 60);
		Control.objExists("AddMemberGroupPage1", "Group", true);
		Control.customWait("AddMemberGroupPage1", "Role", 60);
		Control.objExists("AddMemberGroupPage1", "Role", true);
		Control.customWait("AddMemberGroupPage1", "DateOfExpiry", 60);
		Control.objExists("AddMemberGroupPage1", "DateOfExpiry", true);
		Control.takeScreenshot();
		Control.customWait("AddMemberGroupPage1", "CheckBox", 60);
		Control.objExists("AddMemberGroupPage1", "CheckBox", true);
		Control.customWait("AddMemberGroupPage1", "EtlApprover", 60);
		Control.objExists("AddMemberGroupPage1", "EtlApprover", true);
		Control.customWait("AddMemberGroupPage1", "CancelButton", 60);
		Control.objExists("AddMemberGroupPage1", "CancelButton", true);
		Control.customWait("AddMemberGroupPage1", "AddMemberButton", 60);
		Control.objExists("AddMemberGroupPage1", "AddMemberButton", true);
		Control.customWait("AddMemberGroupPage1", "EmailInput", 60);
		Control.findElement("AddMemberGroupPage1", "EmailInput").click();
		Control.SelectDropDown("AddMemberGroupPage1","DdList","alexis.reyes@globe.com.ph");
		Thread.sleep(5000);
		Control.click("AddMemberGroupPage1", "Asterisk");
		Control.customWait("AddMemberGroupPage1","ExpDate",60);
		Control.click("AddMemberGroupPage1", "ExpDate");
		DatePicker(2022);
		Control.takeScreenshot();
		Control.customWait("AddMemberGroupPage1", "RoleInput", 60);
		Control.findElement("AddMemberGroupPage1", "RoleInput").click();
		Control.SelectDropDown("AddMemberGroupPage1","DdList","Beta Tester Role");
		Control.Checkbox("AddMemberGroupPage1", "CheckBox", "ON");
		Control.customWait("AddMemberGroupPage1", "EtlInput", 60);
		Control.findElement("AddMemberGroupPage1", "EtlInput").click();
		Control.SelectDropDown("AddMemberGroupPage1","DdList","zmayncierto@globe.com.ph");
		Thread.sleep(5000);
		Control.scroll("AddMemberGroup", "AddMemberButton");
		Control.click("AddMemberGroupPage1", "AddMemberButton");
		Control.customWait("AddMemberGroupPage1", "Xicon1", 60);
		Control.objExists("AddMemberGroupPage1", "Xicon1", true);
		Control.customWait("AddMemberGroupPage1", "Avatar", 60);
		Control.objExists("AddMemberGroupPage1", "Avatar", true);
		Control.customWait("AddMemberGroupPage1", "AddMembersText", 60);
		Control.objExists("AddMemberGroupPage1", "AddMembersText", true);
		Control.takeScreenshot();
		Control.customWait("AddMemberGroupPage1", "AreYouSureText", 60);
		Control.objExists("AddMemberGroupPage1", "AreYouSureText", true);
		Control.customWait("AddMemberGroupPage1", "CancelButton1", 60);
		Control.objExists("AddMemberGroupPage1", "CancelButton1", true);
		Control.customWait("AddMemberGroupPage1", "ProceedButton", 60);
		Control.objExists("AddMemberGroupPage1", "ProceedButton", true);
		Control.click("AddMemberGroupPage1", "ProceedButton");
		Control.takeScreenshot();
		Control.customWait("AddMemberGroupPage1", "ErrorMsg", 60);
		Control.takeScreenshot();
		Control.objExists("AddMemberGroupPage1", "ErrorMsg", true);
		
		
	}



	public static void GroupSearchFunction() throws Exception {
		Control.click("HomePage", "SystemConfiguration");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Control.objExists("SystemConfigPage", "UserAccessManagement", true);
		Control.click("SystemConfigPage", "UserAccessManagement");
		Control.takeScreenshot();
		Control.ExplicitWait("GroupSearchPage", "SearchGroups","/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]");
		Control.enterText("GroupSearchPage", "SearchGroups","Group Name");
		Control.takeScreenshot();
		Control.customWait("GroupSearchPage", "SearchButton", 60);
		Control.click("GroupSearchPage", "SearchButton");
		Control.customWait("GroupSearchPage", "GroupName", 60);
		Control.takeScreenshot();
		Control.objExists("GroupSearchPage", "GroupName", true);
		Control.takeScreenshot();
		Constant.driver.navigate().refresh();
		Control.ExplicitWait("GroupSearchPage", "SearchGroups","/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]");
		Control.enterText("GroupSearchPage", "SearchGroups","G-Name Sample Updation");
		Control.takeScreenshot();
		Control.click("GroupSearchPage", "SearchButton");
		Control.takeScreenshot();
		Control.customWait("GroupSearchPage", "GroupName", 60);
		Control.objExists("GroupSearchPage", "GroupName", true);
		Control.takeScreenshot();
		Constant.driver.navigate().refresh();
		Control.ExplicitWait("GroupSearchPage", "SearchGroups","/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]");
		Control.enterText("GroupSearchPage", "SearchGroups","sakhamuri.lavanya@globe.com.ph");
		Control.click("GroupSearchPage", "SearchButton");
		Control.customWait("GroupSearchPage", "SorryMsg", 60);
		Control.objExists("GroupSearchPage", "SorryMsg", true);
		Constant.driver.navigate().refresh();
		Control.ExplicitWait("GroupSearchPage", "SearchGroups","/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]");
		Control.enterText("GroupSearchPage", "SearchGroups","Super Admin");
		Control.takeScreenshot();
		Control.click("GroupSearchPage", "SearchButton");
		Control.customWait("GroupSearchPage", "SorryMsg", 60);
		Control.objExists("GroupSearchPage", "SorryMsg", true);
		Control.takeScreenshot();
		Constant.driver.navigate().refresh();
		Control.ExplicitWait("GroupSearchPage", "SearchGroups","/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]");
		Control.enterText("GroupSearchPage", "SearchGroups", Control.getAlphaNumericString(255));
		Control.takeScreenshot();
		Constant.driver.navigate().refresh();
		Control.ExplicitWait("GroupSearchPage", "SearchGroups","/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]");
		Control.enterText("GroupSearchPage", "SearchGroups", Control.getAlphaNumericString(265));
		Control.takeScreenshot();	
	}



	public static void GroupFilterFunction() throws Exception {
		Control.click("HomePage", "SystemConfiguration");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Control.objExists("SystemConfigPage", "UserAccessManagement", true);
		Control.click("SystemConfigPage", "UserAccessManagement");
		Control.takeScreenshot();
		Control.customWait("GroupFilterPage", "FilterFunction", 60);
		Control.objExists("GroupFilterPage", "FilterFunction", true);
		Control.click("GroupFilterPage", "FilterFunction");
		Control.takeScreenshot();
		Control.customWait("GroupFilterPage", "FilterBy", 60);
		Control.objExists("GroupFilterPage", "FilterBy", true);
		Control.customWait("GroupFilterPage", "All", 60);
		Control.objExists("GroupFilterPage", "All", true);
		Control.customWait("GroupFilterPage", "ActiveGroup", 60);
		Control.objExists("GroupFilterPage", "ActiveGroup", true);
		Control.customWait("GroupFilterPage", "DeActivatedGroup", 60);
		Control.objExists("GroupFilterPage", "DeActivatedGroup", true);
		Control.takeScreenshot();
		Control.click("GroupFilterPage", "ActiveGroup");
		Control.customWait("GroupFilterPage", "FilterFunction", 60);
		Control.click("GroupFilterPage", "FilterFunction");
		Control.click("GroupFilterPage", "DeActivatedGroup");
		Control.customWait("GroupFilterPage", "FilterFunction", 60);
		Control.click("GroupFilterPage", "FilterFunction");
		Control.click("GroupFilterPage", "All");	
	}



	public static void AddUserNotRegistered(String Email,String Group,String Role,String Etl) throws Exception {
		Control.click("HomePage", "SystemConfiguration");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Control.objExists("SystemConfigPage", "UserAccessManagement", true);
		Control.click("SystemConfigPage", "UserAccessManagement");
		Control.takeScreenshot();
		Control.customWait("AddUserPage", "UserTab", 60);
		Control.objExists("AddUserPage", "UserTab", true);
		Control.takeScreenshot();
		Control.click("AddUserPage", "UserTab");
		Control.takeScreenshot();
		Control.customWait("AddUserPage", "CreateUserButton", 60);
		Control.objExists("AddUserPage", "CreateUserButton", true);
		Control.js_click("AddUserPage", "CreateUserButton");
		Control.customWait("AddUserPage", "Xicon", 60);
		Control.objExists("AddUserPage", "Xicon", true);
		Control.customWait("AddUserPage", "CreateDatamartUserText", 60);
		Control.objExists("AddUserPage", "CreateDatamartUserText", true);
		Control.customWait("AddUserPage", "Asterisk", 60);
		Control.objExists("AddUserPage", "Asterisk", true);
		Control.customWait("AddUserPage", "Email", 60);
		Control.objExists("AddUserPage", "Email", true);
		Control.takeScreenshot();
		Control.customWait("AddUserPage", "Group", 60);
		Control.objExists("AddUserPage", "Group", true);
		Control.customWait("AddUserPage", "Role", 60);
		Control.objExists("AddUserPage", "Role", true);
		Control.customWait("AddUserPage", "DateOfExp", 60);
		Control.objExists("AddUserPage", "DateOfExp", true);
		Control.customWait("AddUserPage", "CheckBox", 60);
		Control.objExists("AddUserPage", "CheckBox", true);
		Control.customWait("AddUserPage", "CreateButton", 60);
		Control.takeScreenshot();
		Control.objExists("AddUserPage", "CreateButton", true);
		Control.customWait("AddUserPage", "CancelButton", 60);
		Control.objExists("AddUserPage", "CancelButton", true);
		Control.customWait("AddUserPage", "EtlAproover", 60);
		Control.objExists("AddUserPage", "EtlAproover", true);
		Control.click("AddUserPage", "Xicon");
		Control.js_click("AddUserPage", "CreateUserButton");
		Control.scroll("AddUserPage", "CancelButton");
		Control.click("AddUserPage", "CancelButton");
		Control.takeScreenshot();
		Control.js_click("AddUserPage", "CreateUserButton");
		Control.click("AddUserPage", "Group");
		Thread.sleep(5000);
		Control.SelectDropDown("AddUserPage","DdList",Group);

		Control.click("AddUserPage", "Role");
		Control.SelectDropDown("AddUserPage","DdList",Role);
		Control.click("AddUserPage", "EtlAproover");
		Control.SelectDropDown("AddUserPage","DdList",Etl);
		Control.customWait("AddUserPage", "DateOfExp", 60);
		Control.js_click("AddUserPage", "DateOfExp");
		Thread.sleep(2000);
		DatePicker(2022);
		Control.click("AddUserPage", "CheckBox");
		Control.disabled("AddUserPage", "CreateButton");
		Control.click("AddUserPage", "CancelButton");
		Control.js_click("AddUserPage", "CreateUserButton");
		Control.click("AddUserPage", "Email");
		Thread.sleep(5000);
		Control.SelectDropDown("AddUserPage","DdList",Email);
		Control.click("AddUserPage","CreateDatamartUserText");
		Control.click("AddUserPage", "Role");
		Control.SelectDropDown("AddUserPage","DdList",Role);
		Control.js_click("AddUserPage", "DateOfExp");
		Thread.sleep(2000);
		DatePicker(2022);
		Control.click("AddUserPage", "EtlAproover");
		Control.SelectDropDown("AddUserPage","DdList",Etl);
		Control.click("AddUserPage", "CheckBox");
		Control.disabled("AddUserPage", "CreateButton");
		Control.click("AddUserPage", "CancelButton");
		Control.js_click("AddUserPage", "CreateUserButton");
		Control.click("AddUserPage", "Email");
		Thread.sleep(5000);
		Control.SelectDropDown("AddUserPage","DdList",Email);
		Control.click("AddUserPage","CreateDatamartUserText");
		Control.click("AddUserPage", "Group");
		Control.SelectDropDown("AddUserPage","DdList",Group);
		Control.js_click("AddUserPage", "DateOfExp");
		Thread.sleep(2000);
		DatePicker(2022);
		Control.click("AddUserPage", "EtlAproover");
		Control.SelectDropDown("AddUserPage","DdList",Etl);
		Control.click("AddUserPage", "CheckBox");
		Control.disabled("AddUserPage", "CreateButton");
		Control.click("AddUserPage", "CancelButton");
		Control.js_click("AddUserPage", "CreateUserButton");
		Control.click("AddUserPage", "Email");
		Thread.sleep(5000);
		Control.SelectDropDown("AddUserPage","DdList",Email);
		Control.click("AddUserPage","CreateDatamartUserText");
		Control.click("AddUserPage", "Group");
		Control.SelectDropDown("AddUserPage","DdList",Group);
		Control.click("AddUserPage", "Role");
		Control.SelectDropDown("AddUserPage","DdList",Role);
		//Control.js_click("AddUserPage", "DateOfExp");
		//Thread.sleep(2000);
		//DatePicker(2022);
		Control.click("AddUserPage", "EtlAproover");
		Control.SelectDropDown("AddUserPage","DdList",Etl);
		Control.click("AddUserPage", "CheckBox");
		//Control.disabled("AddUserPage", "CreateButton");
		Control.click("AddUserPage", "CancelButton");
		Control.js_click("AddUserPage", "CreateUserButton");
		Control.click("AddUserPage", "Email");
		Thread.sleep(5000);
		Control.SelectDropDown("AddUserPage","DdList",Email);
		Control.click("AddUserPage","CreateDatamartUserText");
		Control.click("AddUserPage", "Group");
		Control.SelectDropDown("AddUserPage","DdList",Group);
		Control.click("AddUserPage", "Role");
		Control.SelectDropDown("AddUserPage","DdList",Role);
		Control.js_click("AddUserPage", "DateOfExp");
		Thread.sleep(2000);
		DatePicker(2022);
		//Control.click("AddUserPage", "EtlAproover");
		//Control.SelectDropDown("AddUserPage","DdList",EtlName());
		Control.click("AddUserPage", "CheckBox");
		Control.disabled("AddUserPage", "CreateButton");
		Control.click("AddUserPage", "CancelButton");
		Control.js_click("AddUserPage", "CreateUserButton");
		Control.click("AddUserPage", "Email");
		Thread.sleep(5000);
		Control.SelectDropDown("AddUserPage","DdList",Email);
		Control.click("AddUserPage","CreateDatamartUserText");
		Control.click("AddUserPage", "Group");
		Control.SelectDropDown("AddUserPage","DdList",Group);
		Control.click("AddUserPage", "Role");
		Control.SelectDropDown("AddUserPage","DdList",Role);
		Control.js_click("AddUserPage", "DateOfExp");
		Thread.sleep(2000);
		DatePicker(2022);
		Thread.sleep(2000);
		Control.click("AddUserPage", "EtlAproover");
		Control.SelectDropDown("AddUserPage","DdList",Etl);
		Thread.sleep(2000);
		Control.click("AddUserPage", "CheckBox");
		Thread.sleep(2000);
		Control.scroll("AddUserPage", "CreateButton");
		Control.click("AddUserPage", "CreateButton");
		//Control.customWait("AddUserPage", "CreateButton", 60);
		//Control.objExists("AddUserPage", "CreateButton",true);
		Control.customWait("AddUserPage", "Xicon1", 60);
		Control.objExists("AddUserPage", "Xicon1",true);
		Control.customWait("AddUserPage", "Avatar", 60);
		Control.objExists("AddUserPage", "Avatar",true);
		Control.customWait("AddUserPage", "AreYouSureText", 60);
		Control.objExists("AddUserPage", "AreYouSureText",true);
		Control.customWait("AddUserPage", "CreateUserText", 60);
		Control.takeScreenshot();
		Control.objExists("AddUserPage", "CreateUserText",true);
		Control.customWait("AddUserPage", "CancelButton1", 60);
		Control.objExists("AddUserPage", "CancelButton1",true);
		Control.customWait("AddUserPage", "YesCreateButton", 60);
		Control.objExists("AddUserPage", "YesCreateButton",true);
		Control.click("AddUserPage", "CancelButton1");
		Control.scroll("AddUserPage", "CreateButton");
		Control.click("AddUserPage", "CreateButton");
		Control.click("AddUserPage", "YesCreateButton");
		Control.customWait("AddUserPage", "OkButton", 60);
		Control.click("AddUserPage", "OkButton");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Control.customWait("AddUserPage", "EditButton", 60);
		Control.objExists("AddUserPage", "EditButton",true);
		Control.click("AddUserPage", "EditButton");
		Control.takeScreenshot();
		Control.scroll("AddUserPage", "CancelButton2");
		Control.customWait("AddUserPage", "CancelButton2", 60);
		Control.objExists("AddUserPage", "CancelButton2",true);	
		Control.takeScreenshot();
		Control.click("AddUserPage", "CancelButton2");
		Thread.sleep(4000);
	}

	
	public static void AddUserAlreadyRegistered(String RegEmail, String Group, String Role,
			String Etl) throws Exception {
		Control.click("HomePage", "SystemConfiguration");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Control.objExists("SystemConfigPage", "UserAccessManagement", true);
		Control.click("SystemConfigPage", "UserAccessManagement");
		Control.takeScreenshot();
		Control.customWait("AddUserPage", "UserTab", 60);
		Control.objExists("AddUserPage", "UserTab", true);
		Control.takeScreenshot();
		Control.click("AddUserPage", "UserTab");
		Control.takeScreenshot();
		Control.customWait("AddUserPage", "CreateUserButton", 60);
		Control.objExists("AddUserPage", "CreateUserButton", true);
		Control.js_click("AddUserPage", "CreateUserButton");
		Control.customWait("AddUserPage", "Xicon", 60);
		Control.objExists("AddUserPage", "Xicon", true);
		Control.customWait("AddUserPage", "CreateDatamartUserText", 60);
		Control.objExists("AddUserPage", "CreateDatamartUserText", true);
		Control.customWait("AddUserPage", "Asterisk", 60);
		Control.objExists("AddUserPage", "Asterisk", true);
		Control.customWait("AddUserPage", "Email", 60);
		Control.objExists("AddUserPage", "Email", true);
		Control.takeScreenshot();
		Control.customWait("AddUserPage", "Group", 60);
		Control.objExists("AddUserPage", "Group", true);
		Control.customWait("AddUserPage", "Role", 60);
		Control.objExists("AddUserPage", "Role", true);
		Control.customWait("AddUserPage", "DateOfExp", 60);
		Control.objExists("AddUserPage", "DateOfExp", true);
		Control.customWait("AddUserPage", "CheckBox", 60);
		Control.objExists("AddUserPage", "CheckBox", true);
		Control.customWait("AddUserPage", "CreateButton", 60);
		Control.takeScreenshot();
		Control.objExists("AddUserPage", "CreateButton", true);
		Control.customWait("AddUserPage", "CancelButton", 60);
		Control.objExists("AddUserPage", "CancelButton", true);
		Control.customWait("AddUserPage", "EtlAproover", 60);
		Control.objExists("AddUserPage", "EtlAproover", true);
		/*Control.click("AddUserPage", "Email");
		Thread.sleep(5000);
		Control.SelectDropDownList("AddUserPage","DdList");
		Control.click("AddUserPage","CreateDatamartUserText");
		Thread.sleep(3000);*/
		Control.click("AddUserPage", "Email");
		Thread.sleep(5000);
		Control.SelectDropDown("AddUserPage","DdList",RegEmail);
		Control.click("AddUserPage","CreateDatamartUserText");
		Control.click("AddUserPage", "Group");
		Control.SelectDropDown("AddUserPage","DdList",Group);
		Control.click("AddUserPage", "Role");
		Control.SelectDropDown("AddUserPage","DdList",Role);
		Control.takeScreenshot();
		Control.js_click("AddUserPage", "DateOfExp");
		Thread.sleep(2000);
		DatePicker(2022);
		Thread.sleep(2000);
		Control.click("AddUserPage", "EtlAproover");
		Control.SelectDropDown("AddUserPage","DdList",Etl);
		Thread.sleep(2000);
		Control.click("AddUserPage", "CheckBox");
		Control.takeScreenshot();
		Thread.sleep(2000);
		Control.scroll("AddUserPage", "CreateButton");
		Control.click("AddUserPage", "CreateButton");
		Control.customWait("AddUserPage", "Xicon1", 60);
		Control.objExists("AddUserPage", "Xicon1",true);
		Control.customWait("AddUserPage", "Avatar", 60);
		Control.objExists("AddUserPage", "Avatar",true);
		Control.customWait("AddUserPage", "AreYouSureText", 60);
		Control.objExists("AddUserPage", "AreYouSureText",true);
		Control.customWait("AddUserPage", "CreateUserText", 60);
		Control.takeScreenshot();
		Control.objExists("AddUserPage", "CreateUserText",true);
		Control.customWait("AddUserPage", "CancelButton1", 60);
		Control.objExists("AddUserPage", "CancelButton1",true);
		Control.customWait("AddUserPage", "YesCreateButton", 60);
		Control.objExists("AddUserPage", "YesCreateButton",true);
		Control.click("AddUserPage", "YesCreateButton");
		Control.customWait("AddUserPage", "ErrorMsg", 60);
		Control.objExists("AddUserPage", "ErrorMsg",true);
		Control.takeScreenshot();
		Control.customWait("AddUserPage", "OkButton1", 60);
		Control.objExists("AddUserPage", "OkButton1",true);
		Control.takeScreenshot();




		
		
	}



	public static void CreateBulkUser() throws Exception {
		Control.click("HomePage", "SystemConfiguration");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Control.customWait("SystemConfigPage", "UserAccessManagement", 60);
		Control.customWait("SystemConfigPage", "Environment", 60);
		Control.takeScreenshot();
		Control.customWait("SystemConfigPage", "DateRetentionPolicy", 60);
		Control.customWait("SystemConfigPage", "DateIngestion", 60);
		Control.customWait("SystemConfigPage", "SystemSettings", 60);
		Control.customWait("SystemConfigPage", "SystemLogs", 60);
		Control.objExists("SystemConfigPage", "UserAccessManagement", true);
		Control.objExists("SystemConfigPage", "Environment", true);
		Control.objExists("SystemConfigPage", "DateRetentionPolicy", true);
		Control.objExists("SystemConfigPage", "DateIngestion", true);
		Control.objExists("SystemConfigPage", "SystemSettings", true);
		Control.objExists("SystemConfigPage", "SystemLogs", true);
		Control.takeScreenshot();
		Control.click("SystemConfigPage", "UserAccessManagement");
		Control.customWait("AddUserPage", "UserTab", 60);
		Control.objExists("AddUserPage", "UserTab", true);
		Control.takeScreenshot();
		Control.click("AddUserPage", "UserTab");
		Control.customWait("AddUserPage", "CreateBulkUser", 60);
		Control.objExists("AddUserPage", "CreateBulkUser", true);
		Control.takeScreenshot();
		Control.click("AddUserPage", "CreateBulkUser");
		Control.customWait("AddUserPage", "DownloadCsvFile", 60);
		Control.objExists("AddUserPage", "DownloadCsvFile", true);
		Control.takeScreenshot();
		Control.click("AddUserPage", "DownloadCsvFile");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Thread.sleep(3000);	
             		
	}



	public static void UserSearchFunction() throws Exception {
		Control.click("HomePage", "SystemConfiguration");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Control.objExists("SystemConfigPage", "UserAccessManagement", true);
		Control.click("SystemConfigPage", "UserAccessManagement");
		Control.takeScreenshot();
		Control.customWait("AddUserPage", "UserTab", 60);
		Control.objExists("AddUserPage", "UserTab", true);
		Control.takeScreenshot();
		Control.click("AddUserPage", "UserTab");
		Control.takeScreenshot();
		Control.ExplicitWait("UserSearchPage", "SearchUsers","/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]");
		Control.enterText("UserSearchPage", "SearchUsers", Generic.ReadFromExcel("ValidNetworkId", "UserCreation", 1));
		Control.takeScreenshot();
		Control.click("UserSearchPage", "SearchButton");
		Control.customWait("UserSearchPage", "NetworkId", 60);
		Control.objExists("UserSearchPage", "NetworkId", true);
		Control.takeScreenshot();
		Control.ExplicitWait("UserSearchPage", "SearchUsers","/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]");
		Control.enterText("UserSearchPage", "SearchUsers", Generic.ReadFromExcel("ValidFullName", "UserCreation", 1));
		Control.click("UserSearchPage", "SearchButton");
		Control.customWait("UserSearchPage", "FullName", 60);
		Control.objExists("UserSearchPage", "FullName", true);
		Control.takeScreenshot();
		Control.ExplicitWait("UserSearchPage", "SearchUsers","/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]");
		Control.enterText("UserSearchPage", "SearchUsers", Generic.ReadFromExcel("ValidEmail", "UserCreation", 1));
		Control.click("UserSearchPage", "SearchButton");
		Control.customWait("UserSearchPage", "Email", 60);
		Control.objExists("UserSearchPage", "Email", true);
		Control.takeScreenshot();
		Control.ExplicitWait("UserSearchPage", "SearchUsers","/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]");
		Control.enterText("UserSearchPage", "SearchUsers", Generic.ReadFromExcel("ValidGroup", "UserCreation", 1));
		Control.click("UserSearchPage", "SearchButton");
		Control.customWait("UserSearchPage", "Group", 60);
		Control.objExists("UserSearchPage", "Group", true);
		Control.takeScreenshot();
		Control.ExplicitWait("UserSearchPage", "SearchUsers","/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]");
		Control.enterText("UserSearchPage", "SearchUsers", Generic.ReadFromExcel("ValidRole", "UserCreation", 1));
		Control.click("UserSearchPage", "SearchButton");
		Control.customWait("UserSearchPage", "Role", 60);
		Control.objExists("UserSearchPage", "Role", true);
		Control.takeScreenshot();
		Control.ExplicitWait("UserSearchPage", "SearchUsers","/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]");
		Control.enterText("UserSearchPage", "SearchUsers", "Active");
		Control.click("UserSearchPage", "SearchButton");
		Control.customWait("UserSearchPage", "Active", 60);
		Control.objExists("UserSearchPage", "Active", true);
		Control.takeScreenshot();
		Control.ExplicitWait("UserSearchPage", "SearchUsers","/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]");
		Control.enterText("UserSearchPage", "SearchUsers", "InActive");
		Control.click("UserSearchPage", "SearchButton");
		Control.customWait("UserSearchPage", "InActive", 60);
		Control.objExists("UserSearchPage", "InActive", true);
		Control.takeScreenshot();
		Control.ExplicitWait("UserSearchPage", "SearchUsers","/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]");
		Control.enterText("UserSearchPage", "SearchUsers", "lavanyace929@gmail.com");
		Control.click("UserSearchPage", "SearchButton");
		Control.customWait("UserSearchPage", "SorryMsg", 60);
		Control.objExists("UserSearchPage", "SorryMsg", true);
		Control.takeScreenshot();
		Control.ExplicitWait("UserSearchPage", "SearchUsers","/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]");
		Control.enterText("UserSearchPage", "SearchUsers", "Xyz Group");
		Control.click("UserSearchPage", "SearchButton");
		Control.customWait("UserSearchPage", "SorryMsg", 60);
		Control.objExists("UserSearchPage", "SorryMsg", true);
		Control.takeScreenshot();
		Control.ExplicitWait("UserSearchPage", "SearchUsers","/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]");
		Control.enterText("UserSearchPage", "SearchUsers", "Manager");
		Control.click("UserSearchPage", "SearchButton");
		Control.customWait("UserSearchPage", "SorryMsg", 60);
		Control.objExists("UserSearchPage", "SorryMsg", true);
		Control.takeScreenshot();
		Control.ExplicitWait("UserSearchPage", "SearchUsers","/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]");
		Control.enterText("UserSearchPage", "SearchUsers", Control.getAlphaNumericString(255));
		Control.takeScreenshot();
		Control.ExplicitWait("UserSearchPage", "SearchUsers","/html[1]/body[1]/div[1]/div[1]/div[1]/div[3]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]");
		Control.enterText("UserSearchPage", "SearchUsers", Control.getAlphaNumericString(256));
		Control.takeScreenshot();	
	}



	public static void UserFilterFunction() throws Exception {
		Control.click("HomePage", "SystemConfiguration");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Control.objExists("SystemConfigPage", "UserAccessManagement", true);
		Control.click("SystemConfigPage", "UserAccessManagement");
		Control.takeScreenshot();
		//Control.customWait("AddUserPage", "UserTab", 60);
		//Control.objExists("AddUserPage", "UserTab", true);
		Control.takeScreenshot();
		//Control.click("AddUserPage", "UserTab");
		Control.takeScreenshot();
		Thread.sleep(5000);
		Control.customWait("UserSearchPage", "UserFilter", 60);
		Control.objExists("UserSearchPage", "UserFilter", true);
		Control.takeScreenshot();
		Control.click("UserSearchPage", "UserFilter");
		Thread.sleep(5000);
		Control.customWait("UserSearchPage", "Ascending", 60);
		Control.objExists("UserSearchPage", "Ascending", true);
		Control.customWait("UserSearchPage", "Descending", 60);
		Control.objExists("UserSearchPage", "Descending", true);
		Control.customWait("UserSearchPage", "Ascending", 60);
		Control.click("UserSearchPage", "Ascending");
		Thread.sleep(6000);
		Control.takeScreenshot();
		Control.click("UserSearchPage", "UserFilter");
		Control.customWait("UserSearchPage", "Descending", 60);
		Control.click("UserSearchPage", "Descending");
		Thread.sleep(5000);
		Control.takeScreenshot();	
	}



	public static void AddRoleFunction(String RoleName) throws Exception {
		Control.click("HomePage", "SystemConfiguration");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Control.objExists("SystemConfigPage", "UserAccessManagement", true);
		Control.click("SystemConfigPage", "UserAccessManagement");
		Control.takeScreenshot();
		Control.customWait("AddRolePage","RoleButton",60);
		Control.objExists("AddRolePage","RoleButton", true);
		Control.click("AddRolePage","RoleButton");
		//Control.scroll("AddRolePage","CreateRoleButton");
		Thread.sleep(4000);
		Control.customWait("AddRolePage","CreateRoleButton",60);
		Control.objExists("AddRolePage","CreateRoleButton", true);
		Control.click("AddRolePage","CreateRoleButton");
		Control.customWait("AddRolePage","Remainder",60);
		Control.objExists("AddRolePage","Remainder", true);
		Control.customWait("AddRolePage","CreateRoleText",60);
		Control.objExists("AddRolePage","CreateRoleText", true);
		Control.customWait("AddRolePage","Asterisk",60);
		Control.objExists("AddRolePage","Asterisk", true);
		Control.customWait("AddRolePage","RoleName",60);
		Control.objExists("AddRolePage","RoleName", true);
		Control.takeScreenshot();
		Control.customWait("AddRolePage","RoleDescription",60);
		Control.objExists("AddRolePage","RoleDescription", true);
		Control.scroll("AddRolePage","UAMTab");
		Control.customWait("AddRolePage","UAMTab",60);
		Control.objExists("AddRolePage","UAMTab", true);
		Control.customWait("AddRolePage","SystemConfigTab",60);
		Control.objExists("AddRolePage","SystemConfigTab", true);
		Control.customWait("AddRolePage","EtlManagementTab",60);
		Control.objExists("AddRolePage","EtlManagementTab", true);
		Control.customWait("AddRolePage","PermissionsText",60);
		Control.objExists("AddRolePage","PermissionsText", true);
		Control.scroll("AddRolePage","CancelButton");
		WebDriverWait wait= (new WebDriverWait(Constant.driver, 20));
		wait.until(ExpectedConditions. visibilityOfElementLocated(By.xpath("//div[@class='text-right mt-7 button-disabled col']//child::button[1]")));
		wait.until(ExpectedConditions . elementToBeClickable (By.xpath("//div[@class='text-right mt-7 button-disabled col']//child::button[1]")));
		Control.customWait("AddRolePage","CancelButton",60);
		Control.objExists("AddRolePage","CancelButton", true);
		Control.customWait("AddRolePage","SaveRoleButton",60);
		Control.objExists("AddRolePage","SaveRoleButton", true);
		//Control.takeScreenshot();
		Thread.sleep(3000);
		Control.js_click("AddRolePage","CancelButton");
		//Control.customWait("AddRolePage","CreateRoleButton",60);
		//Control.objExists("AddRolePage","CreateRoleButton", true);
		Thread.sleep(5000);
		wait.until(ExpectedConditions . visibilityOfElementLocated (By.xpath("//span[normalize-space()='Create Role']")));
		wait.until(ExpectedConditions . elementToBeClickable (By.xpath("//span[normalize-space()='Create Role']")));
		Control.click("AddRolePage","CreateRoleButton");
		Control.customWait("AddRolePage","RoleName",60);
		Control.enterText("AddRolePage","RoleName",RoleName);
		Control.customWait("AddRolePage","PermissionsBox",60);
		Control.scroll("AddRolePage","PermissionsBox");
		Control.objExists("AddRolePage","PermissionsBox", true);
		Control.js_click("AddRolePage","PermissionsBox");
		wait.until(ExpectedConditions. visibilityOfElementLocated(By.xpath("//div[@class='text-right mt-7 button-disabled col']//child::button[2]")));
		wait.until(ExpectedConditions . elementToBeClickable (By.xpath("//div[@class='text-right mt-7 button-disabled col']//child::button[2]")));
		Control.js_click("AddRolePage","SaveRoleButton");
		Control.customWait("AddRolePage","Xicon1",60);
		Control.objExists("AddRolePage","Xicon1", true);
		Control.customWait("AddRolePage","SaveRoleText",60);
		Control.objExists("AddRolePage","SaveRoleText", true);
		Control.customWait("AddRolePage","AreYouSureText",60);
		Control.objExists("AddRolePage","AreYouSureText", true);
		Control.customWait("AddRolePage","CancelButton1",60);
		Control.objExists("AddRolePage","CancelButton1", true);
		Control.customWait("AddRolePage","YesProceedButton",60);
		Control.objExists("AddRolePage","YesProceedButton", true);
		Control.click("AddRolePage","CancelButton1");
		wait.until(ExpectedConditions. visibilityOfElementLocated(By.xpath("//div[@class='text-right mt-7 button-disabled col']//child::button[2]")));
		wait.until(ExpectedConditions . elementToBeClickable (By.xpath("//div[@class='text-right mt-7 button-disabled col']//child::button[2]")));
		Control.js_click("AddRolePage","SaveRoleButton");
		Control.customWait("AddRolePage","YesProceedButton",60);
		Control.objExists("AddRolePage","YesProceedButton", true);
		Control.click("AddRolePage","YesProceedButton");
		Control.customWait("AddRolePage","Xicon2",60);
		Control.objExists("AddRolePage","Xicon2", true);
		Control.customWait("AddRolePage","Checkmark",60);
		Control.objExists("AddRolePage","Checkmark", true);
		Control.customWait("AddRolePage","Success",60);
		Control.objExists("AddRolePage","Success", true);
		Control.customWait("AddRolePage","NewRoleCreatedText",60);
		Control.objExists("AddRolePage","NewRoleCreatedText", true);
		Control.customWait("AddRolePage","OkButton",60);
		Control.objExists("AddRolePage","OkButton", true);
		Control.js_click("AddRolePage","OkButton");
		Thread.sleep(4000);
		Control.takeScreenshot();
	
	}

	public static void EditRoleFunction(String UpdateRole) throws Exception {
		Control.click("HomePage", "SystemConfiguration");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Control.objExists("SystemConfigPage", "UserAccessManagement", true);
		Control.click("SystemConfigPage", "UserAccessManagement");
		Control.takeScreenshot();
		Control.customWait("AddRolePage","RoleButton",60);
		Control.objExists("AddRolePage","RoleButton", true);
		Control.click("AddRolePage","RoleButton");
		Control.customWait("EditRolePage","EditButton",60);
		Control.objExists("EditRolePage","EditButton", true);
		Control.takeScreenshot();
		Control.click("EditRolePage","EditButton");
		Thread.sleep(4000);
		WebElement input= Constant.driver.findElement(By.xpath("(//label[text()='Role Name *']//following-sibling::input)"));
		input.sendKeys(Keys.chord(Keys.CONTROL,"a"));
		input.sendKeys(Keys.BACK_SPACE);
		Control.customWait("EditRolePage","RoleName",60);
		Control.objExists("EditRolePage","RoleName", true);
		Control.enterText("EditRolePage","RoleName",UpdateRole);
		WebDriverWait wait= (new WebDriverWait(Constant.driver, 20));
		Control.scroll("EditRolePage","UpdateRoleButton");
		wait.until(ExpectedConditions. visibilityOfElementLocated(By.xpath("//div[@class='text-right mt-7 button-disabled col']//button[2]")));
		wait.until(ExpectedConditions . elementToBeClickable (By.xpath("//div[@class='text-right mt-7 button-disabled col']//button[2]")));
		Control.customWait("EditRolePage","UpdateRoleButton",60);
		Control.objExists("EditRolePage","UpdateRoleButton", true);
		Control.js_click("EditRolePage","UpdateRoleButton");
		Control.customWait("EditRolePage","Xicon",60);
		Control.objExists("EditRolePage","Xicon", true);
		Control.customWait("EditRolePage","UpdateRoleText",60);
		Control.objExists("EditRolePage","UpdateRoleText", true);
		Control.customWait("EditRolePage","AreYouSureText",60);
		Control.objExists("EditRolePage","AreYouSureText", true);
		Control.customWait("EditRolePage","CancelButton",60);
		Control.objExists("EditRolePage","CancelButton", true);
		Control.customWait("EditRolePage","YesUpdateButton",60);
		Control.objExists("EditRolePage","YesUpdateButton", true);
		Control.takeScreenshot();
		Control.click("EditRolePage","YesUpdateButton");
		Control.customWait("EditRolePage","Xicon1",60);
		Control.objExists("EditRolePage","Xicon1", true);
		Control.customWait("EditRolePage","CheckMark",60);
		Control.objExists("EditRolePage","CheckMark", true);
		Control.customWait("EditRolePage","SuccessText",60);
		Control.objExists("EditRolePage","SuccessText", true);
		Control.customWait("EditRolePage","SuccessfullyUpdatedText",60);
		Control.objExists("EditRolePage","SuccessfullyUpdatedText", true);
		Control.customWait("EditRolePage","OkButton",60);
		Control.objExists("EditRolePage","OkButton", true);
		Control.takeScreenshot();;
		Control.click("EditRolePage","OkButton");
		Thread.sleep(4000);
		Control.takeScreenshot();
		
	}

	public static void RoleSearchFunction(String RoleName, String RoleDescription) throws Exception {
		Control.click("HomePage", "SystemConfiguration");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Control.objExists("SystemConfigPage", "UserAccessManagement", true);
		Control.click("SystemConfigPage", "UserAccessManagement");
		Control.takeScreenshot();
		Control.customWait("AddRolePage","RoleButton",60);
		Control.objExists("AddRolePage","RoleButton", true);
		Control.click("AddRolePage","RoleButton");
		Control.ExplicitWait("RoleSearchPage","SearchRoles","(//div[@class='v-text-field__slot']//input)[2]");
		Control.enterText("RoleSearchPage","SearchRoles", RoleName);
		Control.customWait("RoleSearchPage","SearchButton",60);
		Control.objExists("RoleSearchPage","SearchButton", true);
		Control.click("RoleSearchPage","SearchButton");
		Control.customWait("RoleSearchPage","DisplaySearchedRole",60);
		Control.objExists("RoleSearchPage","DisplaySearchedRole", true);
		Control.takeScreenshot();
		Control.Clear("(//div[@class='v-text-field__slot']//input)[2]");
		//Control.ExplicitWait("RoleSearchPage","SearchRoles","(//div[@class='v-text-field__slot']//input)[2]");
		Control.enterText("RoleSearchPage","SearchRoles", RoleDescription);
		Control.click("RoleSearchPage","SearchButton");
		Control.customWait("RoleSearchPage","DisplaySearchedRoleDescription",60);
		Control.objExists("RoleSearchPage","DisplaySearchedRoleDescription", true);
		Control.takeScreenshot();
		Control.Clear("(//div[@class='v-text-field__slot']//input)[2]");
		Control.enterText("RoleSearchPage","SearchRoles", "Abc");
		Control.click("RoleSearchPage","SearchButton");
		Control.customWait("RoleSearchPage","SorryMsg",60);
		Control.objExists("RoleSearchPage","SorryMsg", true);
		Control.takeScreenshot();
		Control.Clear("(//div[@class='v-text-field__slot']//input)[2]");
		Control.enterText("RoleSearchPage","SearchRoles", "This is Role Description");
		Control.click("RoleSearchPage","SearchButton");
		Control.customWait("RoleSearchPage","SorryMsg",60);
		Control.objExists("RoleSearchPage","SorryMsg", true);
		Control.takeScreenshot();
		Control.Clear("(//div[@class='v-text-field__slot']//input)[2]");
		Control.enterText("RoleSearchPage","SearchRoles", "sakhamuri.lavanya@globe.com.ph");
		Control.click("RoleSearchPage","SearchButton");
		Control.customWait("RoleSearchPage","SorryMsg",60);
		Control.objExists("RoleSearchPage","SorryMsg", true);
		Control.takeScreenshot();
		Control.Clear("(//div[@class='v-text-field__slot']//input)[2]");
		Control.enterText("RoleSearchPage","SearchRoles", "TCoE Group");
		Control.click("RoleSearchPage","SearchButton");
		Control.customWait("RoleSearchPage","SorryMsg",60);
		Control.objExists("RoleSearchPage","SorryMsg", true);
		Control.takeScreenshot();
		Control.Clear("(//div[@class='v-text-field__slot']//input)[2]");
		Control.enterText("RoleSearchPage","SearchRoles", Control.getAlphaNumericString(255));
		Control.click("RoleSearchPage","SearchButton");
		Control.customWait("RoleSearchPage","SorryMsg",60);
		Control.objExists("RoleSearchPage","SorryMsg", true);
		Control.Clear("(//div[@class='v-text-field__slot']//input)[2]");
		Control.enterText("RoleSearchPage","SearchRoles", Control.getAlphaNumericString(256));
		Control.click("RoleSearchPage","SearchButton");
		Control.customWait("RoleSearchPage","SorryMsg",60);
		Control.objExists("RoleSearchPage","SorryMsg", true);
		Control.Clear("(//div[@class='v-text-field__slot']//input)[2]");
	}
	
	public static void PaginationFunction() throws Exception
	{
		Control.click("HomePage","SystemConfiguration");
		Control.takeScreenshot();
		Control.click("SystemConfigPage","UserAccessManagement");
		Control.takeScreenshot();
		Control.click("UserManagementPage","Users");
		Control.takeScreenshot();
		Control.objExists("UserManagementPage","Pagination",true);
		Control.click("UserManagementPage","Page1");
		Control.takeScreenshot();
		Control.click("UserManagementPage","Page2");
		Control.takeScreenshot();
		Control.click("UserManagementPage","PreviousArrow");
		Control.objExists("UserManagementPage","Pagination",true);
		Control.takeScreenshot();
	}
	
	public static void CreateRole() throws Exception
	{
		Control.click("HomePage","SystemConfiguration");
		Control.objExists("SystemConfigPage","UserAccessManagement",true);
		Control.objExists("SystemConfigPage","Environment",true);
		Control.objExists("SystemConfigPage","DateRetentionPolicy",true);
		Control.objExists("SystemConfigPage","DateIngestion",true);
	//	Control.objExists("SystemConfigPage","SystemSettings",true);
		Control.takeScreenshot();
		Control.click("SystemConfigPage","UserAccessManagement");
		Control.objExists("UserManagementPage","Groups",true);
		Control.objExists("UserManagementPage","Roles",true);
		Control.objExists("UserManagementPage","Users",true);
		Control.takeScreenshot();
		Control.click("UserManagementPage","Roles");
		Control.takeScreenshot();
		Control.click("AddRolePage","CreateRoleButton");
		Control.objExists("AddRolePage","Remainder",true);
		Control.objExists("AddRolePage","CreateRoleText",true);
		Control.objExists("AddRolePage","RoleName",true);
		Control.objExists("AddRolePage","RoleDescription",true);
		Control.objExists("AddRolePage","UAMTab",true);
		Control.objExists("AddRolePage","SystemConfigTab",true);
		Control.objExists("AddRolePage","EtlManagementTab",true);
		Control.objExists("AddRolePage","PermissionsText",true);
		Control.objExists("AddRolePage","ManageUser",true);
		Control.objExists("AddRolePage","MU_CreateUser",true);
		Control.objExists("AddRolePage","MU_ModifyUser",true);
		Control.objExists("AddRolePage","MU_DeleteUser",true);
		Control.objExists("AddRolePage","ManageRole",true);
		Control.objExists("AddRolePage","MR_CreateUser",true);
		Control.objExists("AddRolePage","MR_ModifyUser",true);
		Control.objExists("AddRolePage","MR_DeactivateUser",true);
		Control.objExists("AddRolePage","ManageGroup",true);
		Control.objExists("AddRolePage","MG_CreateUser",true);
		Control.objExists("AddRolePage","MG_ModifyUser",true);
		Control.objExists("AddRolePage","MG_DeactivateUser",true);
		Control.click("AddRolePage","SystemConfigTab");
		Control.objExists("AddRolePage","ManageGenSettings",true);
		Control.objExists("AddRolePage","ModifySettings",true);
		Control.objExists("AddRolePage","ManageHouseKeeping",true);
		Control.objExists("AddRolePage","ModifyHouseKeeping",true);
		Control.objExists("AddRolePage","CancelButton",true);
		Control.objExists("AddRolePage","SaveRoleButton",true);
		Control.js_click("AddRolePage","CancelButton");
		
		Control.click("AddRolePage","CreateRoleButton");
		Control.objExists("AddRolePage","Remainder",true);
		Control.click("AddRolePage","X_Button");
		Control.objExists("AddRolePage","CreateRoleText",true);
		Control.objExists("AddRolePage","RoleName",true);
		Control.objExists("AddRolePage","RoleDescription",true);
		Control.objExists("AddRolePage","UAMTab",true);
		Control.objExists("AddRolePage","SystemConfigTab",true);
		Control.objExists("AddRolePage","EtlManagementTab",true);
		Control.objExists("AddRolePage","PermissionsText",true);
		Control.objExists("AddRolePage","ManageUser",true);
		Control.objExists("AddRolePage","MU_CreateUser",true);
		Control.objExists("AddRolePage","MU_ModifyUser",true);
		Control.objExists("AddRolePage","MU_DeleteUser",true);
		Control.objExists("AddRolePage","ManageRole",true);
		Control.objExists("AddRolePage","MR_CreateUser",true);
		Control.objExists("AddRolePage","MR_ModifyUser",true);
		Control.objExists("AddRolePage","MR_DeactivateUser",true);
		Control.objExists("AddRolePage","ManageGroup",true);
		Control.objExists("AddRolePage","MG_CreateUser",true);
		Control.objExists("AddRolePage","MG_ModifyUser",true);
		Control.objExists("AddRolePage","MG_DeactivateUser",true);
		Control.click("AddRolePage","SystemConfigTab");
		Control.objExists("AddRolePage","ManageGenSettings",true);
		Control.objExists("AddRolePage","ModifySettings",true);
		Control.objExists("AddRolePage","ManageHouseKeeping",true);
		Control.objExists("AddRolePage","ModifyHouseKeeping",true);
		Control.objExists("AddRolePage","CancelButton",true);
		Control.objExists("AddRolePage","SaveRoleButton",true);
		Control.js_click("AddRolePage","SaveRoleButton");
		System.out.println("SaveRole button is disabled");
		
		//==========================================Neg-02==================================================//
		Control.objExists("AddRolePage","RoleName",true);
		Control.enterText("AddRolePage", "RoleName", "datatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatat");
		Control.takeScreenshot();
		
		//==========================================Neg-03==================================================//
		Control.findElement("AddRolePage", "RoleName").clear();
		Control.objExists("AddRolePage","Error_msg",true);
		Control.takeScreenshot();
		Control.click("AddRolePage","SaveRoleButton");
		System.out.println("SaveRole button is disabled");
		
		//==========================================Neg-04==================================================//
		Control.enterText("AddRolePage", "RoleName", "MNC Role");
		Control.takeScreenshot();
		Control.click("AddRolePage","UAMTab");
		Control.click("AddRolePage","ManageUser");
		Control.click("AddRolePage","MU_CreateUser");
		Control.click("AddRolePage","SaveRoleButton");
		Control.takeScreenshot();
		Control.click("AddRolePage","YesProceedButton");
		Control.objExists("AddRolePage","Error_msg",true);
		Control.takeScreenshot();
		
		//==========================================Neg-05=================================================//
		Control.enterText("AddRolePage", "RoleName", "Kavya*&^%$#@!9087654321theyfn76^%$#dyet09876543214");
		Control.takeScreenshot();
		Control.enterText("AddRolePage", "RoleDescription", "Kavya*&^%$#@!9087654321theyfn76^%$#dyet09876543214");
		Control.click("AddRolePage","SystemConfigTab");
		System.out.println("not be able to select and click the Modify Settings checkbox");
		Control.click("AddRolePage","ManageGenSettings");
		System.out.println("not be able to select and click the Modify House Keeping checkbox");
		Control.click("AddRolePage","ManageHouseKeeping");
		Control.click("AddRolePage","SaveRoleButton");
		Control.objExists("AddRolePage","Pop_up",true);
		Control.click("AddRolePage","CancelButton1");
		Control.click("AddRolePage","SaveRoleButton");
		Control.click("AddRolePage","YesProceedButton");
		Control.objExists("AddRolePage","Success_popup",true);
		Control.click("AddRolePage","Ok_Button");
		
	}
	public static void EditRole() throws Exception
	{
		Control.click("HomePage","SystemConfiguration");
		Control.click("SystemConfigPage","UserAccessManagement");
		Control.click("UserManagementPage","Roles");
		Control.takeScreenshot();
		Control.click("EditRolePage", "EditButton");
		Control.objExists("AddRolePage","Remainder",true);
//		Control.objExists("AddRolePage","CreateRoleText",true);
		Control.objExists("AddRolePage","RoleName",true);
		Control.objExists("AddRolePage","RoleDescription",true);
		Custom_Functions.ScrollToView("AddRolePage", "Scroll1");
		Control.objExists("AddRolePage","UAMTab",true);
		Control.objExists("AddRolePage","SystemConfigTab",true);
		Control.objExists("AddRolePage","EtlManagementTab",true);
		Control.objExists("AddRolePage","PermissionsText",true);
		Control.objExists("AddRolePage","ManageUser",true);
		Control.objExists("AddRolePage","MU_CreateUser",true);
		Control.objExists("AddRolePage","MU_ModifyUser",true);
		Control.objExists("AddRolePage","MU_DeleteUser",true);
		Control.objExists("AddRolePage","ManageRole",true);
		Control.objExists("AddRolePage","MR_CreateUser",true);
		Control.objExists("AddRolePage","MR_ModifyUser",true);
		Control.objExists("AddRolePage","MR_DeactivateUser",true);
		Control.objExists("AddRolePage","ManageGroup",true);
		Control.objExists("AddRolePage","MG_CreateUser",true);
		Control.objExists("AddRolePage","MG_ModifyUser",true);
		Control.objExists("AddRolePage","MG_DeactivateUser",true);
		Control.click("AddRolePage","SystemConfigTab");
		Control.objExists("AddRolePage","ManageGenSettings",true);
		Control.objExists("AddRolePage","ModifySettings",true);
		Control.objExists("AddRolePage","ManageHouseKeeping",true);
		Control.objExists("AddRolePage","ModifyHouseKeeping",true);
		Control.objExists("AddRolePage","CancelButton",true);
		Control.objExists("EditRolePage","UpdateRoleButton",true);
		Control.js_click("AddRolePage","CancelButton");
		Control.click("EditRolePage", "EditButton");
		Control.click("AddRolePage","X_Button");
		
		//=========================================Neg-01===================================================//
		Control.findElement("AddRolePage", "RoleName").clear();
		Control.takeScreenshot();
		Control.click("AddRolePage","UAMTab");
		Control.click("AddRolePage","ManageUser");
		System.out.println("Update button is disabled");
		
		
		//========================================Neg-02===================================================//
		Control.objExists("AddRolePage","RoleName",true);
		Control.enterText("AddRolePage", "RoleName", "data253566436464*^&%&^%@%^atatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatatat");
		Control.takeScreenshot();
		
		//=======================================Neg-03===================================================//
		Control.findElement("AddRolePage", "RoleName").clear();
		Control.enterText("AddRolePage", "RoleName", "MNC Role");
		Control.takeScreenshot();
		Control.objExists("AddRolePage","Error_msg",true);
		
		//======================================Neg-04=====================================================//
		Control.enterText("AddRolePage", "RoleName", "Kavya*&^%$#@!9087654321theyfn76^%$#dyet09876543214");
		Control.takeScreenshot();
		Control.enterText("AddRolePage", "RoleDescription", "Kavya*&^%$#@!9087654321theyfn76^%$#dyet09876543214");
		Control.scroll("AddRolePage", "Scroll1");
		Control.click("AddRolePage","SystemConfigTab");
		System.out.println("not be able to select and click the Modify Settings checkbox");
		Control.click("AddRolePage","ManageGenSettings");
		System.out.println("not be able to select and click the Modify House Keeping checkbox");
		Control.click("EditRolePage","UpdateRoleButton");
		Control.objExists("EditRolePage","Xicon",true);
		Control.objExists("EditRolePage","UpdateRoleText",true);
		Control.objExists("EditRolePage","AreYouSureText",true);
		Control.objExists("EditRolePage","CancelButton",true);
		Control.objExists("EditRolePage","YesUpdateButton",true);
		Control.click("EditRolePage","YesUpdateButton");
		Control.objExists("EditRolePage","CheckMark",true);
		Control.objExists("EditRolePage","SuccessText",true);
		Control.objExists("EditRolePage","SuccessfullyUpdatedText",true);
		Control.objExists("EditRolePage","OkButton",true);

	}
	public static void Profile() throws Exception
	{
		Control.hover("Profile ","Icon");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("Profile ","PPM");
		Thread.sleep(10000);
		Control.takeScreenshot();
		
		Control.hover("Profile ","Icon");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("Profile ","UserManual");
		Thread.sleep(10000);
		Control.takeScreenshot();
	}
	
	
	
	public static void actualscript_newpublish() throws Exception
	{
		Control.click("HomePage","EtlScriptsandJobs");
		Control.takeScreenshot();
		Control.click("ETL","Requests");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","My_approval");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","Search");
		Control.enterText("ETL", "Search", "New Script Publish");
		Thread.sleep(10000);
		Control.click("ETL","Src_Icon");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","newPubrow");
		Thread.sleep(15000);
		Control.takeScreenshot();
		Control.objExists("ETL", "Page", true);
		Control.takeScreenshot();
		Control.click("ETL","Returntolist");
		Control.takeScreenshot();
	}
	
	public static void updatenewpublish() throws Exception
	{
		Control.click("HomePage","EtlScriptsandJobs");
		Control.takeScreenshot();
		Control.click("ETL","Requests");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","My_approval");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","Search");
		Control.enterText("ETL", "Search", "Update Script Publish");
		Thread.sleep(10000);
		Control.click("ETL","Src_Icon");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","newPubrow");
		Thread.sleep(20000);
		Control.takeScreenshot();
		Control.objExists("ETL", "Page", true);
		Control.takeScreenshot();
		Control.click("ETL","Returntolist");
		Control.takeScreenshot();
	}
	
	public static void actualscript_rollback() throws Exception
	{
		Control.takeScreenshot();
		Control.click("HomePage","EtlScriptsandJobs");
		Control.takeScreenshot();
		Control.click("ETL","Requests");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","My_approval");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","Search");
		Control.enterText("ETL", "Search", "RollBack");
		Thread.sleep(10000);
		Control.click("ETL","Src_Icon");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","newPubrow");
		Thread.sleep(20000);
		Control.takeScreenshot();
		Control.objExists("ETL", "Page", true);
		Control.takeScreenshot();
		Control.click("ETL","Returntolist");
		Control.takeScreenshot();
	}
	
	public static void actualscript_newscriptMultidownload() throws Exception
	{
		Control.click("HomePage","EtlScriptsandJobs");
		Control.takeScreenshot();
		Control.click("ETL","Requests");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","My_approval");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","Search");
		Control.enterText("ETL", "Search", "sample_script2.py");
		Thread.sleep(10000);
		Control.click("ETL","Src_Icon");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","Download");
		Control.takeScreenshot();
		Control.click("ETL","Dnld_Button");
		Control.takeScreenshot();
	}
	
	public static void actualscript_newscriptSingledownload() throws Exception
	{
		Control.click("HomePage","EtlScriptsandJobs");
		Control.takeScreenshot();
		Control.click("ETL","Requests");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","My_approval");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","Search");
		Control.enterText("ETL", "Search", "mayee.py");
		Thread.sleep(10000);
		Control.click("ETL","Src_Icon");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","Download");
		Control.takeScreenshot();
		Control.click("ETL","Dnld_Button");
		Control.takeScreenshot();
	}
	
	public static void UpdatecriptSingledownload() throws Exception
	{
		Control.click("HomePage","EtlScriptsandJobs");
		Control.takeScreenshot();
		Control.click("ETL","Requests");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","My_approval");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","Search");
		Control.enterText("ETL", "Search", "Hello_World.py");
		Thread.sleep(10000);
		Control.click("ETL","Src_Icon");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","Download");
		Control.takeScreenshot();
		Control.click("ETL","Dnld_Button");
		Control.takeScreenshot();
	}
	
	public static void UpdatecriptMultipledownload() throws Exception
	{
		Control.click("HomePage","EtlScriptsandJobs");
		Control.takeScreenshot();
		Control.click("ETL","Requests");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","My_approval");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","Search");
		Control.enterText("ETL", "Search", "PB9A19C1");
		Thread.sleep(10000);
		Control.click("ETL","Src_Icon");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","Download");
		Control.takeScreenshot();
		Control.click("ETL","Dnld_Button");
		Control.takeScreenshot();
	}
	
	public static void Mail_RejectNewScriptPublish () throws Exception
	{
		Constant.driver.navigate().to("https://mail.google.com/mail/u/0/?tab=rm&ogbl#inbox");
		Thread.sleep(5000);
		Control.click("Mail","Search_mail");
		Control.enterText("Mail", "Search_mail", "New Script Publish has been Rejected ");
		Control.takeScreenshot();
		Control.click("Mail","Srch_Icon");
		Control.takeScreenshot();
		Thread.sleep(5000);
		Control.click("Mail","First_Row");
		Control.takeScreenshot();
		Control.objExists("Mail", "Details", true);
		Control.takeScreenshot();
		Control.click("Mail","Request_link");
//		Constant.driver.close();
//		Constant.driver.switchTo();
		Control.takeScreenshot();
	}
	
	public static void Mail_RejectUpdateScriptPublish () throws Exception
	{
		Constant.driver.navigate().to("https://mail.google.com/mail/u/0/?tab=rm&ogbl#inbox");
		Thread.sleep(5000);
		Control.click("Mail","Search_mail");
		Control.enterText("Mail", "Search_mail", "Update Script has been Rejected");
		Control.takeScreenshot();
		Control.click("Mail","Srch_Icon");
		Control.takeScreenshot();
		Thread.sleep(5000);
		Control.click("Mail","First_Row");
		Control.takeScreenshot();
		Control.objExists("Mail", "Details", true);
		Control.takeScreenshot();
		Control.click("Mail","Request_link");
		Control.takeScreenshot();
	}
	
	public static void Mail_RejectRollbackScript () throws Exception
	{
		Constant.driver.navigate().to("https://mail.google.com/mail/u/0/?tab=rm&ogbl#inbox");
		Thread.sleep(5000);
		Control.click("Mail","Search_mail");
		Control.enterText("Mail", "Search_mail", "Script Rollback has been Rejected");
		Control.takeScreenshot();
		Control.click("Mail","Srch_Icon");
		Control.takeScreenshot();
		Thread.sleep(5000);
		Control.click("Mail","First_Row");
		Control.takeScreenshot();
		Control.objExists("Mail", "Details", true);
		Control.takeScreenshot();
		Control.click("Mail","Request_link");
		Control.takeScreenshot();
	}
	
	public static void DEFAULT_Status_filter () throws Exception
	{
		Control.click("HomePage","EtlScriptsandJobs");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","Requests");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","Publish_Scripts");
		Thread.sleep(10000);
		Control.takeScreenshot();
		Control.click("ETL","Filter_btn");
		Control.takeScreenshot();
	}
	
	public static void Filter_Function () throws Exception
	{
		Control.click("HomePage","EtlScriptsandJobs");
		Control.takeScreenshot();
		Control.click("ETL","Requests");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Control.click("ETL","Publish_Scripts");
		Thread.sleep(3000);
		Control.takeScreenshot();
		Control.click("ETL","Filter_btn");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Rollback_Fltr");
		Control.click("Filter_Fun","Approved_Filter");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Srch_Button");
		Thread.sleep(10000);
		Control.takeScreenshot();
		
		Control.click("ETL","Filter_btn");
		Control.click("Filter_Fun","Clear_fltr");
		
		
		//Publish Filter
		Control.click("ETL","Filter_btn");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Publish_Fltr");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Srch_Button");
		Thread.sleep(10000);
		Control.takeScreenshot();
		
		Control.click("ETL","Filter_btn");
		Control.click("Filter_Fun","Clear_fltr");
		
		
		//Approved Filter
		Control.click("ETL","Filter_btn");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Approved_Filter");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Srch_Button");
		Thread.sleep(10000);
		Control.takeScreenshot();
		
		Control.click("ETL","Filter_btn");
		Control.click("Filter_Fun","Clear_fltr");
		
		//Started Only 
		Control.click("ETL","Filter_btn");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Started_Filter");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Srch_Button");
		Thread.sleep(10000);
		Control.takeScreenshot();
		
		Control.click("ETL","Filter_btn");
		Control.click("Filter_Fun","Clear_fltr");
		
		
		//Completed Filter
		Control.click("ETL","Filter_btn");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Completed_Fltr");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Srch_Button");
		Thread.sleep(10000);
		Control.takeScreenshot();
		
		Control.click("ETL","Filter_btn");
		Control.click("Filter_Fun","Clear_fltr");
		
		//Cannot Publish Filter
		Control.click("ETL","Filter_btn");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Cannot_Publish_Fltr");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Srch_Button");
		Thread.sleep(10000);
		Control.takeScreenshot();
		
		Control.click("ETL","Filter_btn");
		Control.click("Filter_Fun","Clear_fltr");
		
		//Date Filter
		Control.click("ETL","Filter_btn");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Clear_fltr");
		Control.click("ETL","Filter_btn");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Date_Filter");
		Control.click("Filter_Fun","Date");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Srch_Button");
		Thread.sleep(10000);
		
		Control.click("ETL","Filter_btn");
		Control.click("Filter_Fun","Clear_fltr");
		
		//Rollback and Approved
		Control.click("ETL","Filter_btn");
		Control.click("Filter_Fun","Rollback_Fltr");
		Control.click("Filter_Fun","Approved_Filter");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Srch_Button");
		Thread.sleep(10000);
		Control.takeScreenshot();
		
		Control.click("ETL","Filter_btn");
		Control.click("Filter_Fun","Clear_fltr");
		
		//Rollback and Started
		Control.click("ETL","Filter_btn");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Rollback_Fltr");
		Control.click("Filter_Fun","Started_Filter");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Srch_Button");
		Thread.sleep(10000);
		Control.takeScreenshot();
		
		Control.click("ETL","Filter_btn");
		Control.click("Filter_Fun","Clear_fltr");
		
		
		//Rollback and completed
		Control.click("ETL","Filter_btn");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Rollback_Fltr");
		Control.click("Filter_Fun","Completed_Fltr");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Srch_Button");
		Thread.sleep(10000);
		Control.takeScreenshot();
		
		Control.click("ETL","Filter_btn");
		Control.click("Filter_Fun","Clear_fltr");
		
		
		//Rollback and Cannot publish
		Control.click("ETL","Filter_btn");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Rollback_Fltr");
		Control.click("Filter_Fun","Cannot_Publish_Fltr");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Srch_Button");
		Thread.sleep(10000);
		Control.takeScreenshot();
		
		Control.click("ETL","Filter_btn");
		Control.click("Filter_Fun","Clear_fltr");
		
		
		//Publish and approved
		Control.click("ETL","Filter_btn");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Publish_Fltr");
		Control.click("Filter_Fun","Approved_Filter");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Srch_Button");
		Thread.sleep(10000);
		Control.takeScreenshot();
		
		Control.click("ETL","Filter_btn");
		Control.click("Filter_Fun","Clear_fltr");

		
		//Publish and started
		Control.click("ETL","Filter_btn");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Publish_Fltr");
		Control.click("Filter_Fun","Started_Filter");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Srch_Button");
		Thread.sleep(10000);
		Control.takeScreenshot();
		
		Control.click("ETL","Filter_btn");
		Control.click("Filter_Fun","Clear_fltr");

		
		//Publish and Completed
		Control.click("ETL","Filter_btn");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Publish_Fltr");
		Control.click("Filter_Fun","Completed_Fltr");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Srch_Button");
		Thread.sleep(10000);
		Control.takeScreenshot();
		
		Control.click("ETL","Filter_btn");
		Control.click("Filter_Fun","Clear_fltr");

		
		//Publish and Cannot publish
		Control.click("ETL","Filter_btn");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Publish_Fltr");
		Control.click("Filter_Fun","Cannot_Publish_Fltr");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Srch_Button");
		Thread.sleep(10000);
		Control.takeScreenshot();
		
		Control.click("ETL","Filter_btn");
		Control.click("Filter_Fun","Clear_fltr");
		
		
		//rollback,approved and select date
		Control.click("ETL","Filter_btn");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Rollback_Fltr");
		Control.click("Filter_Fun","Approved_Filter");
		Control.click("Filter_Fun","Date_Filter");
		Control.click("Filter_Fun","Date");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Srch_Button");
		Thread.sleep(10000);
		Control.takeScreenshot();
		
		Control.click("ETL","Filter_btn");
		Control.click("Filter_Fun","Clear_fltr");
		
		
		//Publish, completed and select date 
		Control.click("ETL","Filter_btn");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Publish_Fltr");
		Control.click("Filter_Fun","Completed_Fltr");
		Control.click("Filter_Fun","Date_Filter");
		Control.click("Filter_Fun","Date");
		Control.takeScreenshot();
		Control.click("Filter_Fun","Srch_Button");
		Thread.sleep(10000);
		Control.takeScreenshot();
		
	
	}
	
	
	
	public static void ETL_Features() throws Exception
	{
		Control.click("HomePage","SystemConfiguration");
		Control.takeScreenshot();
		Control.click("SystemConfigPage","UserAccessManagement");
		Control.takeScreenshot();
		Control.click("UserManagementPage","Roles");
		Control.takeScreenshot();
		Control.click("AddRolePage","CreateRoleButton");
		Control.click("AddRolePage","RoleName");
		Control.enterText("AddRolePage", "RoleName", "Datamart Admin-ARN");
		Control.takeScreenshot();
		Control.click("AddRolePage","RoleDescription");
		Control.enterText("AddRolePage", "RoleDescription", "AutomationTesting");
		Control.takeScreenshot();
		Control.click("AddRolePage","EtlManagementTab");
		Control.click("AddRolePage","DatamartAdmin");
		Control.takeScreenshot();
		Control.click("AddRolePage","SaveRoleButton");
		Control.click("AddRolePage","CancelButton1");
	//	Control.takeScreenshot();
	//	Control.click("AddRolePage","Ok_Button");
	//	Thread.sleep(3000);
		Control.takeScreenshot();
		
		Control.click("HomePage", "UserName");
		Control.click("Profile", "Logout_Button");
		
		//==============================================Requestor================================================//
		Custom_Functions.LoggingIn(Generic.ReadFromExcel("DM_Admin", "AI_TestData", 1),Generic.ReadFromExcel("DM_Pwd", "AI_TestData", 1));
		Control.click("HomePage", "EtlScriptsandJobs");
		Control.click("ETL", "List");
		
		Control.click("ETL", "New_Script");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Control.click("ETL", "Browse_Btn");
		Thread.sleep(5000);
		Control.takeScreenshot();
		Control.click("ETL", "Scripts");
		Thread.sleep(5000);
		Control.click("ETL", "Checkbox");
		Control.takeScreenshot();
		Control.click("ETL", "Select_Btn");
		Control.takeScreenshot();
		Control.click("ETL", "Submit_Btn");
		Control.takeScreenshot();
		Control.click("ETL", "Inner_submit");
		Control.click("ETL", "Inner_ok");
		
		Control.click("Profile", "Icon");
		Control.click("Profile", "Logout_Button");
		
		//================================================Approver==============================================//
  		Constant.driver.navigate().to("https://mail.google.com/mail/u/0/?tab=rm&ogbl#inbox");              
  		Thread.sleep(5000);
		Constant.driver.findElement(By.xpath("//*[@name='noreply_datamart_no.']")) .click();
		Control.takeScreenshot();
		Constant.driver.findElement(By.xpath("//*[@class='a3s aiL ']"));
		Control.takeScreenshot();
		Constant.driver.findElement(By.xpath("//*[@href='https://datamartdev.globetel.com/etl-requests']")) .click();
		Control.takeScreenshot();
		
		Control.click("ETL", "My_approval");
		Control.takeScreenshot();
		Control.click("ETL", "Approve_Btn");
		Control.click("ETL", "Approve");
		
		//==============================================datamart admin========================================//
		Constant.driver.navigate().to("https://mail.google.com/mail/u/0/?tab=rm&ogbl#inbox"); 
		Thread.sleep(5000);
		Constant.driver.findElement(By.xpath("//*[@name='noreply_datamart_no.']")) .click();
		Control.takeScreenshot();
		Constant.driver.findElement(By.xpath("//*[@class='a3s aiL ']"));
		Control.takeScreenshot();
		Constant.driver.findElement(By.xpath("//*[@href='https://datamartdev.globetel.com/etl-requests']")) .click();
		Control.takeScreenshot();
		
		//=============================================datamart_ARN===========================================//
		Constant.driver.navigate().to("https://mail.google.com/mail/u/0/?tab=rm&ogbl#inbox"); 
		Thread.sleep(5000);
		Constant.driver.findElement(By.xpath("//*[@name='noreply_datamart_no.']")) .click();
		Control.takeScreenshot();
		Constant.driver.findElement(By.xpath("//*[@class='a3s aiL ']"));
		Control.takeScreenshot();
		Constant.driver.findElement(By.xpath("//*[@href='https://datamartdev.globetel.com/etl-requests']")) .click();
		Control.takeScreenshot();
		
	}
	public static void ChangeStatus() throws Exception
	{
		Control.click("HomePage", "EtlScriptsandJobs");
		Control.click("ETL", "Requests");
		Control.click("ETL", "Publish_Scripts");
		Control.takeScreenshot();
//		Control.click("ETL", "Start_Publish");
//		Control.takeScreenshot();
//		Control.click("ETL", "ProceedBtn");
//		Control.takeScreenshot();
//		Control.click("ETL", "okbtn");
		
		Constant.driver.navigate().to("https://mail.google.com/mail/u/0/?tab=rm&ogbl&zx=c76ncdc22cu6#inbox"); 
		Control.objExists("Mail", "In_Firstrow", true);
		Control.takeScreenshot();
		Control.click("Mail", "In_Firstrow");
		Control.objExists("Mail", "Details", true);
		Control.takeScreenshot();
	}
	
	public static void ChangeStatus_Completed() throws Exception
	{
		Control.click("HomePage", "EtlScriptsandJobs");
		Control.click("ETL", "Requests");
		Control.click("ETL", "Publish_Scripts");
		Control.takeScreenshot();
		Control.click("ETL", "Filter_btn");
		Control.takeScreenshot();
		Control.click("ETL", "Started_Ftr");
		Control.click("ETL", "Approved_Ftr");
		Control.takeScreenshot();
		Control.click("ETL", "Search_Icon");
		Thread.sleep(3000);
		Control.click("ETL", "Complete_Publish");
		Control.takeScreenshot();
		Control.click("ETL", "Publish_Button");
		Control.takeScreenshot();
		Control.click("ETL", "Publish_Okbtn");
		Thread.sleep(5000);
		
		Constant.driver.navigate().to("https://mail.google.com/mail/u/0/?tab=rm&ogbl&zx=c76ncdc22cu6#inbox"); 
		//No mail for completed status
	}
	
	public static void ChangeStatus_CannotPublish() throws Exception
	{
		Control.click("HomePage", "EtlScriptsandJobs");
		Control.click("ETL", "Requests");
		Control.click("ETL", "Publish_Scripts");
		Control.takeScreenshot();
		Control.click("ETL", "Filter_btn");
		Control.takeScreenshot();
		Control.click("ETL", "Started_Ftr");
		Control.click("ETL", "Approved_Ftr");
		Control.takeScreenshot();
		Control.click("ETL", "Search_Icon");
		Thread.sleep(3000);
		Control.click("ETL", "Cannot_Publish");
		Control.takeScreenshot();
		Control.click("ETL", "Stop");
		Control.takeScreenshot();
		Control.enterText("ETL", "Remarks", "Testing");
		Control.takeScreenshot();
		Control.click("ETL", "Stop");
		Thread.sleep(8000);
		Control.takeScreenshot();
		Control.click("ETL", "Publish_Okbtn");
		Thread.sleep(5000);
		
		Constant.driver.navigate().to("https://mail.google.com/mail/u/0/?tab=rm&ogbl&zx=c76ncdc22cu6#inbox"); 
		//No mail for cannot publish status
	}
	
	public static void Search_Function() throws Exception
	{
		Control.click("HomePage", "EtlScriptsandJobs");
		Control.click("ETL", "Requests");
		Thread.sleep(5000);
		Control.click("ETL", "My_approval");
		Thread.sleep(5000);
		Control.takeScreenshot();
		
		Control.hover("", "Download");
	}
	
	
	
	



	
		
			 
	
	
	
	


		
}



	
	

