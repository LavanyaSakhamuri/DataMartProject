/*
 *Description: Control Functions library 
'Author :Sunanda Tirunagari & Ankit Kumar
 */

package utility;
//import java.awt.Desktop.Action;
import java.awt.Robot;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FileWriter;
import java.io.IOException;
//import java.net.URL;
import java.net.UnknownHostException;
//import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Map;
//import java.util.Set;
//import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import java.text.SimpleDateFormat;
import java.text.DateFormat;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.opera.OperaOptions;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.remote.CapabilityType;

//import GlobeEnd.Generic;

import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class Control {

	public static void delete(File file) throws IOException {
		if (file.isDirectory()) { 
			if (file.list().length == 0) {
				file.delete();
			} else {
				// list all the directory contents
				String files[] = file.list();
				for (String temp : files) {
					// construct the file structure
					File fileDelete = new File(file, temp);
					// recursive delete
					delete(fileDelete);
				}
				// check the directory again, if empty then delete it
				if (file.list().length == 0) {
					file.delete();
					//System.out.println("Directory is deleted : "
						//	+ file.getAbsolutePath());
				}
			}
		} else {
			// if file, then delete it
			file.delete();
		}
	}
	public static void deleteTempFile() throws UnknownHostException {
		String property = "java.io.tmpdir";
		String temp = System.getProperty(property);
		File directory = new File(temp);
		try {
			delete(directory);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	public static void OpenApplication(String browserName, String URL) throws UnknownHostException {
		//deleteTempFile();
		System.out.println(URL);
        if (browserName.equalsIgnoreCase("Firefox")) {
            System.setProperty("webdriver.gecko.driver", "D:\\Selenium\\Selenium Installation Material\\WebDriver\\geckodriver.exe");
            Constant.driver= new FirefoxDriver();
		} else if (browserName.equalsIgnoreCase("Safari")) {
		            System.setProperty("webdriver.SafariDriver.driver", "C:\\WebDrivers\\SafariDriver.exe");
		            Constant.driver = new SafariDriver();
		} else if (browserName.equalsIgnoreCase("Chrome")) {
			 System.setProperty("webdriver.chrome.driver", "D:\\Backup\\Documents\\Automation\\WebDriver\\Chrome\\chromedriver.exe");
	            ChromeOptions options = new ChromeOptions();
	            DesiredCapabilities desiredCapabilities = DesiredCapabilities.chrome();
	           //  options.addArguments("user-data-dir=C:\\ChromeData"); //e.g.: options.addArguments("user-data-dir=C:\\ChromeData");
	            options.addArguments("user-data-dir=C:\\ChromeData\\UserDetails\\");
	             desiredCapabilities.setCapability(ChromeOptions.CAPABILITY, options);
	             Constant.driver= new ChromeDriver(options);
		} else if (browserName.equalsIgnoreCase("IE")) {
		            System.setProperty("webdriver.ie.driver", "D:\\Selenium\\Selenium Installation Material\\IEWebDriver\\IEDriverServer.exe");
            Constant.driver =  new InternetExplorerDriver();
            
            /*try{
            	Runtime.getRuntime().exec("RunDll32.exe InetCpl.cpl,ClearMyTracksByProcess 255");
            }catch(IOException e)
            {
            	e.printStackTrace();
            } */
            
           /* DesiredCapabilities caps = DesiredCapabilities.internetExplorer();
            caps.setCapability(CapabilityType.ForSeleniumServer.ENSURING_CLEAN_SESSION,true);
            Constant.driver =  new InternetExplorerDriver(caps);
            Constant.driver.manage().deleteAllCookies();*/
            
            
           //Constant.driver.manage().deleteAllCookies();
		} else if (browserName.equalsIgnoreCase("Opera")) {
		OperaOptions options = new OperaOptions();
		options.setBinary("C:\\Users\\ab00363779\\AppData\\Local\\Programs\\Opera\\58.0.3135.132\\opera.exe");
		System.setProperty("webdriver.opera.driver", "D:\\Selenium\\Selenium Installation Material\\WebDriver\\operadriver.exe");
		Constant.driver = new OperaDriver(options);
		} else if (browserName.equalsIgnoreCase("Edge")) {
		            System.setProperty("webdriver.edge.driver", "D:\\Selenium\\WebDriver\\MicrosoftWebDriver.exe");
		            Constant.driver = new EdgeDriver();
		
		}
		Constant.driver.manage().timeouts().implicitlyWait(Constant.defaultBrowserTimeOut, TimeUnit.SECONDS);
        Constant.driver.manage().deleteAllCookies();
        Constant.driver.manage().deleteAllCookies();
        Constant.driver.manage().window().maximize();
        //return driver;
        //Added By Ankit
        try {
        	//Thread.sleep(10000);
            Constant.driver.get(URL);            
            //Generic.WriteTestData("Open the Browser and URL",browserName,URL,"Able to Open the URL '"+URL+"' in Browser '"+browserName+"'","Opened  the URL '"+URL+"' in the Browser '"+browserName+"' successfully","Passed");
        } catch (Exception e) {e.printStackTrace();    
        }
	}
	public static void highlightElement(WebDriver driver, WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', arguments[1]);",element, "border: 1px solid DeepPink;");
 	}
	
	
	public static WebElement findElement(String PageName, String locatorName)   {
		
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
					Constant.webelement = Constant.driver.findElement(By.id(objectLocator));
					highlightElement(Constant.driver, Constant.webelement);
				} else if (locatorTag.equalsIgnoreCase("name")) {
					Constant.webelement = Constant.driver.findElement(By.name(objectLocator));
					highlightElement(Constant.driver, Constant.webelement);
				} else if (locatorTag.equalsIgnoreCase("xpath")) {
					Constant.webelement = Constant.driver.findElement(By.xpath(objectLocator));
					highlightElement(Constant.driver, Constant.webelement);
				} else if (locatorTag.equalsIgnoreCase("linkText")) {
					Constant.webelement = Constant.driver.findElement(By.linkText(objectLocator));
					highlightElement(Constant.driver, Constant.webelement);
				} else if (locatorTag.equalsIgnoreCase("Text")) {
					Constant.webelement = Constant.driver.findElement(By.partialLinkText(objectLocator));
					highlightElement(Constant.driver, Constant.webelement);
				} else if (locatorTag.equalsIgnoreCase("class")) {
					Constant.webelement = Constant.driver.findElement(By.className(objectLocator));
					highlightElement(Constant.driver, Constant.webelement);
				} else if (locatorTag.equalsIgnoreCase("tagName")) {
					Constant.webelement = Constant.driver.findElement(By.tagName(objectLocator));
					highlightElement(Constant.driver, Constant.webelement);	
				} else if (locatorTag.equalsIgnoreCase("css")) {
					Constant.webelement = Constant.driver.findElement(By.cssSelector(objectLocator));
					highlightElement(Constant.driver, Constant.webelement);
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
		return Constant.webelement;
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
					//highlightElement(Constant.driver, Constant.webelements);
				} else if (locatorTag.equalsIgnoreCase("name")) {
					Constant.webelements = Constant.driver.findElements(By.name(objectLocator));
					//highlightElement(Constant.driver, Constant.webelements);
				} else if (locatorTag.equalsIgnoreCase("xpath")) {
					Constant.webelements = Constant.driver.findElements(By.xpath(objectLocator));
					highlightElement(Constant.driver, Constant.webelement);
				} else if (locatorTag.equalsIgnoreCase("linkText")) {
					Constant.webelements = Constant.driver.findElements(By.linkText(objectLocator));
					//highlightElement(Constant.driver, Constant.webelements);
				} else if (locatorTag.equalsIgnoreCase("Text")) {
					Constant.webelements = Constant.driver.findElements(By.partialLinkText(objectLocator));
					//highlightElement(Constant.driver, Constant.webelements);
				} else if (locatorTag.equalsIgnoreCase("class")) {
					Constant.webelements = Constant.driver.findElements(By.className(objectLocator));
					//highlightElement(Constant.driver, Constant.webelements);
				} else if (locatorTag.equalsIgnoreCase("tagName")) {
					Constant.webelements = Constant.driver.findElements(By.tagName(objectLocator));
					//highlightElement(Constant.driver, Constant.webelements);	
				} else if (locatorTag.equalsIgnoreCase("css")) {
					Constant.webelements = Constant.driver.findElements(By.cssSelector(objectLocator));
					//highlightElement(Constant.driver, Constant.webelements);
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
	public static String getAttribute(String PageName,String locator, String attributeName) {
		String attributeValue = null;
		try {

			WebElement element = findElement( PageName,locator);
			if (element != null)
				attributeValue = element.getAttribute(attributeName);
			element = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return attributeValue;
	}
	public static void enterText(String PageName, String locator, String value) throws Exception {
		Constant.PageName=PageName;
		Constant.locator=locator;
		try {
			WebElement element = findElement(PageName,locator);
			if (element != null) {
				Capabilities caps = ((RemoteWebDriver) Constant.driver).getCapabilities();
				  String browserName = caps.getBrowserName();			
				if (browserName.equalsIgnoreCase("internet explorer")) {	
					element.sendKeys(value);
					//JavascriptExecutor js = (JavascriptExecutor) Constant.driver;	
					//js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2]);", element, "value",value);
				}
				else
					element.clear();
					element.sendKeys(value);
				
					Generic.WriteTestData("Entering text '"+value+"' in '"+locator+"' text field",locator,value,"Should able to enter data'"+value+"' into  '"+locator+"' text field","Entered data'"+value+"' into  '"+locator+"' text field successfully","Passed");
			}else {
				Generic.WriteTestData("Entering text '"+value+"' in '"+locator+"' text field",locator,value,"Unable to identify the '"+locator+"' text field","Not able to identify the  '"+locator+"' text field","Failed");
			}
			
			element = null;
		} catch (Exception e) {
			Generic.WriteTestData("Entering text '"+value+"' in '"+locator+"' text field",locator,value,"Should able to enter data'"+value+"' into  '"+locator+"' text field","Not able to enter data'"+value+"' into  '"+locator+"' text field successfully","Failed");
			e.printStackTrace();
		}
		
	}
	public static void click(String PageName, String locator) throws Exception {
		Constant.PageName=PageName;
		Constant.locator=locator;
		
		try {
			WebElement element = findElement(PageName,locator);
			if (element != null) {
				element.click();
				/*JavascriptExecutor js = (JavascriptExecutor) Constant.driver;
                  js.executeScript("arguments[0].click();", element); */
				Generic.WriteTestData("Click on '"+locator+"'",locator,"","Able to click on '"+locator+"' button.","Clicked on '"+locator+"' button.","Passed");
			}
			else {
				Generic.WriteTestData("Click on '"+locator+"'",locator,"","Element should be available '"+locator+"' button.","Element was not found  '"+locator+"'","Failed");
			}
			element = null;		

		} catch (Exception e) {
			Generic.WriteTestData("Click on '"+locator+"'",locator,"","Able to click on '"+locator+"' button.","Clicking on '"+locator+"' button is unsuccessful.","Failed");
			System.out.println(" Error occured whlie click on the element "
					+ locator + " *** " + e.getMessage());
			
		}
		
	}
	
	public static void Clear(String locator) throws Exception{
		WebElement input= Constant.driver.findElement(By.xpath(locator));
			input.sendKeys(Keys.chord(Keys.CONTROL,"a"));
			input.sendKeys(Keys.BACK_SPACE);
		
	}
	public static void ExplicitWait(String PageName, String locator,String xpath) throws Exception {
		Constant.PageName=PageName;
		Constant.locator=locator;
		try {
			//WebElement element = findElement(PageName,locator);
			WebDriverWait wait= (new WebDriverWait(Constant.driver, 20));
			wait.until(ExpectedConditions. visibilityOfElementLocated(By.xpath(xpath)));
			wait.until(ExpectedConditions . elementToBeClickable (By.xpath(xpath)));
			WebElement element = findElement(PageName,locator);
			if (element != null) {
				//element.click();
				JavascriptExecutor js = (JavascriptExecutor) Constant.driver;
                  js.executeScript("arguments[0].click();", element); 
                  
				Generic.WriteTestData("Click on '"+locator+"'",locator,"","Able to click on '"+locator+"' button.","Clicked on '"+locator+"' button.","Passed");
			}
			else {
				Generic.WriteTestData("Click on '"+locator+"'",locator,"","Element should be available '"+locator+"' button.","Element was not found  '"+locator+"'","Failed");
			}
			element = null;		

		} catch (Exception e) {
			Generic.WriteTestData("Click on '"+locator+"'",locator,"","Able to click on '"+locator+"' button.","Clicking on '"+locator+"' button is unsuccessful.","Failed");
			System.out.println(" Error occured whlie click on the element "
					+ locator + " *** " + e.getMessage());
			
		}
		
	}

	
	public static void js_click(String PageName, String locator) throws Exception {
		Constant.PageName=PageName;
		Constant.locator=locator;
		
		try {
			WebElement element = findElement(PageName,locator);
			if (element != null) {
				  JavascriptExecutor js = (JavascriptExecutor) Constant.driver;
                  js.executeScript("arguments[0].click();", element);
				Generic.WriteTestData("Click on '"+locator+"'",locator,"","Able to click on '"+locator+"' button.","Clicked on '"+locator+"' button.","Passed");
			}
			else {
				Generic.WriteTestData("Click on '"+locator+"'",locator,"","Element should be available '"+locator+"' button.","Element was not found  '"+locator+"'","Failed");
			}
			element = null;		

		} catch (Exception e) {
			Generic.WriteTestData("Click on '"+locator+"'",locator,"","Able to click on '"+locator+"' button.","Clicking on '"+locator+"' button is unsuccessful.","Failed");
			System.out.println(" Error occured whlie click on the element "
					+ locator + " *** " + e.getMessage());
			
		}
		
	}
	
	public static void hover(String PageName, String locator) throws Exception {
		Constant.PageName=PageName;
		Constant.locator=locator;
		
		try {
			WebElement element = findElement(PageName,locator);
			Actions action = new Actions(Constant.driver);
			if (element != null) {
				Thread.sleep(1000);
				action.moveToElement(element).build().perform();
				Generic.WriteTestData("Hover on '"+locator+"'",locator,"","Able to Hover on '"+locator+"' button.","Hovered on '"+locator+"' .","Passed");
			}
			else {
				Generic.WriteTestData("Hover on '"+locator+"'",locator,"","Element should be available '"+locator+"' .","Element was not found  '"+locator+"'","Failed");
			}
			element = null;		

		} catch (Exception e) {
			Generic.WriteTestData("Hover on '"+locator+"'",locator,"","Able to Hover on '"+locator+"' .","Hover on '"+locator+"'  is unsuccessful.","Failed");
			System.out.println(" Error occured whlie Hover on the element "
					+ locator + " *** " + e.getMessage());
			
		}
		
	}
	
	public static void GoToUrl(String url) {
		try {
			Constant.driver.get(url);			
			} catch (Exception e) {

		}
	}
	public static void listSelect(String PageName, String locator, String listValue) {
		Constant.PageName=PageName;
		Constant.locator=locator;
		
		try {
			WebElement element = findElement(PageName,locator);
		
			if (element != null) {
				Select dropdown= new Select(element);
				dropdown.selectByVisibleText(listValue);
				element.click();			
				Generic.WriteTestData("select from '"+locator+"'",locator,"","Able to select '"+listValue+"' from '"+locator+ "'" ,"Able to select '"+listValue+"' from '"+locator ,"Passed");
			}
			else {
				
				Generic.WriteTestData("select from '"+locator+"'",locator,"","Able to select '"+listValue+"' from '"+locator+ "'" ,"Not able to select '"+listValue+"' from '"+locator ,"Failed");
			}
			element = null;		

		} catch (Exception e) {
			System.out.println(" Error occured whlie click on the element "
					+ locator + " *** " + e.getMessage());
			
		}
		
	}
	
	
	public static void SelectDropDown(String PageName, String locator, String listValue) {
		Constant.PageName=PageName;
		Constant.locator=locator;
		
		try {
			List<WebElement> element = findElements(PageName,locator);
		
			if (element != null) {
				List<WebElement> allOptions = findElements(PageName,locator);
			    System.out.println(allOptions.size());
		        for(int i = 0; i<=allOptions.size()-1; i++) {
		            if(allOptions.get(i).getText().contains(listValue)) {
		                 
		                allOptions.get(i).click();
		                break;
		                 
		            }
		        }
				//Select dropdown= new Select(element);
				//dropdown.selectByVisibleText(listValue);
				//element.click();			
				Generic.WriteTestData("select from '"+locator+"'",locator,"","Able to select '"+listValue+"' from '"+locator+ "'" ,"Able to select '"+listValue+"' from '"+locator ,"Passed");
			}
			else {
				
				Generic.WriteTestData("select from '"+locator+"'",locator,"","Able to select '"+listValue+"' from '"+locator+ "'" ,"Not able to select '"+listValue+"' from '"+locator ,"Failed");
			}
			element = null;		

		} catch (Exception e) {
			System.out.println(" Error occured whlie click on the element "
					+ locator + " *** " + e.getMessage());
			
		}
		
	}
	
	public static void SelectDropDownList(String PageName, String locator) {
		Constant.PageName=PageName;
		Constant.locator=locator;
		
		try {
			List<WebElement> element = findElements(PageName,locator);
		
			if (element != null) {
				List<WebElement> allOptions = findElements(PageName,locator);
			    System.out.println(allOptions.size());
		        for(int i = 0; i<=allOptions.size()-1; i++) {
		                allOptions.get(i).click();
		                if(allOptions.get(i)==Constant.driver.findElement(By.xpath("(//div[@class='v-list-item__title'])[12]"))) {
		                	break;
		                }
		        }
				//Select dropdown= new Select(element);
				//dropdown.selectByVisibleText(listValue);
				//element.click();			
				Generic.WriteTestData("select from '"+locator+"'",locator,"","Able to select '"+"' from '"+locator+ "'" ,"Able to select '"+"' from '"+locator ,"Passed");
			}
			else {
				
				Generic.WriteTestData("select from '"+locator+"'",locator,"","Able to select '"+"' from '"+locator+ "'" ,"Not able to select '"+"' from '"+locator ,"Failed");
			}
			element = null;		

		} catch (Exception e) {
			System.out.println(" Error occured whlie click on the element "
					+ locator + " *** " + e.getMessage());
			
		}
		
	}
	public static void Checkbox(String PageName, String locator, String CheckBoxVal) {
		Constant.PageName=PageName;
		Constant.locator=locator;
		boolean boxVal ;
		if (CheckBoxVal == "ON") {
			boxVal = true;
		}
		else {
				boxVal = false;
		}
		
		boolean bValue = false;
		try {
			WebElement element = findElement(PageName,locator);
			
			//WebElement element = findElement(PageName,locator);
			if (element != null) {
				bValue =  element.isSelected();
				if (bValue != boxVal) {
					element.click();	
				}
				Generic.WriteTestData("Click on '"+locator+"'",locator,"","Able to click on '"+locator+"' Checkbox/RadioButton.","Clicked on '"+locator+"' Checkbox/RadioButton.","Passed");
			}
			else {
				
				Generic.WriteTestData("Click on '"+locator+"'",locator,"","Able to click on '"+locator+"' Checkbox/RadioButton.","Clicking on '"+locator+"' Checkbox/ is unsuccessful.","Failed");
			}
			element = null;		

		} catch (Exception e) {
			System.out.println(" Error occured whlie click on the element "
					+ locator + " *** " + e.getMessage());
			
		}
		
	}	
	public static void RadioButton(String PageName, String locator) {
		Constant.PageName=PageName;
		Constant.locator=locator;
		//System.out.println("inside radio button Function");
		//System.out.println(locator);
		try {
			WebElement element = findElement(PageName,locator);
			System.out.println(element);
			

			if (element != null) {
				element.click();	
				Generic.WriteTestData("Click on '"+locator+"'",locator,"","Able to click on '"+locator+"' button.","Clicked on '"+locator+"' button.","Passed");
			}
			else {
				
				Generic.WriteTestData("Click on '"+locator+"'",locator,"","Able to click on '"+locator+"' button.","Clicking on '"+locator+"' button is unsuccessful.","Failed");
			}
			element = null;		

		} catch (Exception e) {
			System.out.println(" Error occured whlie click on the element "
					+ locator + " *** " + e.getMessage());
			
		}
	}

	public static void compareText(String PageName,String locator,String ExpectedText) throws Exception{
         //Thread.sleep(5000);
         Constant.PageName=PageName;
        Constant.locator=locator;
        String text = null;
        String value = null;
        
         WebElement element = findElement(PageName,locator);      
         if(element==null)
         {
        	 element = findElement(PageName,locator); 
         }
        try {
            if (element != null) {
                text = element.getText().trim();
            }
            if(ExpectedText.equals(text)) {
            	System.out.println("Actual Text: "+text);
            	System.out.println("Expected Text: "+ExpectedText);
                Generic.WriteTestData("Comparing text of field '"+locator+"' in Page '"+PageName,locator,ExpectedText,"The ExpectedText '"+ExpectedText+"' should be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is same as the Actual Text '"+text+"'","Passed");
            }
            else if(text.equals(""))
            {
                value = element.getAttribute("value");
                System.out.println("Actual Text: "+value);
            	System.out.println("Expected Text: "+ExpectedText);
                if(ExpectedText.equals(value))
                {
                	Generic.WriteTestData("Comparing text of field '"+locator+"' in Page '"+PageName,locator,ExpectedText,"The ExpectedText '"+ExpectedText+"' should be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is same as the Actual Text '"+text+"'","Passed");
                }
                else
                {
                	Generic.WriteTestData("Comparing text of field '"+locator+"' in Page '"+PageName,locator,ExpectedText,"The ExpectedText '"+ExpectedText+"' should be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is not same as the Actual Text '"+text+"'","Failed");
                }
            }
            else
            {
            	//Control.takeScreenshot();
            	Generic.WriteTestData("Comparing text of field '"+locator+"' in Page '"+PageName,locator,ExpectedText,"The ExpectedText '"+ExpectedText+"' should be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is not same as the Actual Text '"+text+"'","Failed");
            }
            
        }catch (Exception e) {
        	//Control.takeScreenshot();
        	Generic.WriteTestData("Comparing text of field '"+locator+"' in Page '"+PageName,locator,ExpectedText,"The ExpectedText '"+ExpectedText+"' should be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is not same as the Actual Text '"+text+"'","Failed");
            e.printStackTrace();
        }
        element = null; 
    }
	
	public static void compareText2(String PageName,String locator,String ExpectedText) throws Exception{
        //Thread.sleep(5000);
        Constant.PageName=PageName;
       Constant.locator=locator;
       String text = null;
       String value = null;
       
        WebElement element = findElement(PageName,locator);      
        if(element==null)
        {
       	 element = findElement(PageName,locator); 
        }
       try {
           if (element != null)                
               text = element.getText().replace("*","").trim();
           if(ExpectedText.equals(text)) {
               Generic.WriteTestData("Comparing text of field '"+locator+"' in Page '"+PageName,locator,ExpectedText,"The ExpectedText '"+ExpectedText+"' should be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is same as the Actual Text '"+text+"'","Passed");
           }
           else if(text.equals(""))
           {
               value = element.getAttribute("value");
               if(ExpectedText.equals(value))
               {
               	Generic.WriteTestData("Comparing text of field '"+locator+"' in Page '"+PageName,locator,ExpectedText,"The ExpectedText '"+ExpectedText+"' should be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is same as the Actual Text '"+text+"'","Passed");
               }
               else
               {
               	Generic.WriteTestData("Comparing text of field '"+locator+"' in Page '"+PageName,locator,ExpectedText,"The ExpectedText '"+ExpectedText+"' should be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is not same as the Actual Text '"+text+"'","Failed");
               }
           }
           else
           {
           	//Control.takeScreenshot();
           	Generic.WriteTestData("Comparing text of field '"+locator+"' in Page '"+PageName,locator,ExpectedText,"The ExpectedText '"+ExpectedText+"' should be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is not same as the Actual Text '"+text+"'","Failed");
           }
           
       }catch (Exception e) {
       	//Control.takeScreenshot();
       	Generic.WriteTestData("Comparing text of field '"+locator+"' in Page '"+PageName,locator,ExpectedText,"The ExpectedText '"+ExpectedText+"' should be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is not same as the Actual Text '"+text+"'","Failed");
           e.printStackTrace();
       }
       element = null; 
	}
	
	public static void compareText3(String ActualText,String ExpectedText) throws Exception{

       try {
           if(ExpectedText.equals(ActualText)) {
               Generic.WriteTestData("Comparing Actual vs Expected text","",ExpectedText,"The Expected Text '"+ExpectedText+"' should be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is same as the Actual Text '"+ActualText+"'","Passed");
           }
           else
           {
           	//Control.takeScreenshot();
        	   Generic.WriteTestData("Comparing Actual vs Expected text","",ExpectedText,"The Expected Text '"+ExpectedText+"' should be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is not the same as the Actual Text '"+ActualText+"'","Passed");
           }
           
       }catch (Exception e) {
       	//Control.takeScreenshot();
    	   Generic.WriteTestData("Comparing Actual vs Expected text","",ExpectedText,"The Expected Text '"+ExpectedText+"' should be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is not the same as the Actual Text '"+ActualText+"'","Passed");
           e.printStackTrace();
       }
   }
	
	public static void compareTextNotEqual(String PageName,String locator,String ExpectedText) throws Exception{
        //Thread.sleep(5000);
        Constant.PageName=PageName;
       Constant.locator=locator;
       String text = null;
       String value = null;
       
        WebElement element = findElement(PageName,locator);      
        if(element==null)
        {
       	 element = findElement(PageName,locator); 
        }
       try {
           if (element != null)                
               text = element.getText().trim();
           if(!ExpectedText.equals(text)) {
               Generic.WriteTestData("Comparing text of field '"+locator+"' in Page '"+PageName,locator,ExpectedText,"The ExpectedText '"+ExpectedText+"' should not be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is not same as the Actual Text '"+text+"'","Passed");
           }
           else if(text.equals(""))
           {
               value = element.getAttribute("value");
               if(!ExpectedText.equals(value))
               {
               	Generic.WriteTestData("Comparing text of field '"+locator+"' in Page '"+PageName,locator,ExpectedText,"The ExpectedText '"+ExpectedText+"' should not be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is not same as the Actual Text '"+text+"'","Passed");
               }
               else
               {
               	Generic.WriteTestData("Comparing text of field '"+locator+"' in Page '"+PageName,locator,ExpectedText,"The ExpectedText '"+ExpectedText+"' should not be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is  same as the Actual Text '"+text+"'","Failed");
               }
           }
           else if (ExpectedText.equals(text))
           {
           	//Control.takeScreenshot();
           	Generic.WriteTestData("Comparing text of field '"+locator+"' in Page '"+PageName,locator,ExpectedText,"The ExpectedText '"+ExpectedText+"' should not be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is same as the Actual Text '"+text+"'","Failed");
           }
           
       }catch (Exception e) {
       	//Control.takeScreenshot();
       	Generic.WriteTestData("Comparing text of field '"+locator+"' in Page '"+PageName,locator,ExpectedText,"The ExpectedText '"+ExpectedText+"' should not be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is same as the Actual Text '"+text+"'","Failed");
           e.printStackTrace();
       }
       element = null; 
	}
	
	public static void compareTextNotEqual2(String PageName,String locator,String ExpectedText) throws Exception{
        //Thread.sleep(5000);
        Constant.PageName=PageName;
       Constant.locator=locator;
       String text = null;
       String value = null;
       
        WebElement element = findElement(PageName,locator);      
        if(element==null)
        {
       	 element = findElement(PageName,locator); 
        }
       try {
           if (element != null)                
        	   text = element.getText().replace("*","").trim();
           if(!ExpectedText.equals(text)) {
               Generic.WriteTestData("Comparing text of field '"+locator+"' in Page '"+PageName,locator,ExpectedText,"The ExpectedText '"+ExpectedText+"' should not be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is not same as the Actual Text '"+text+"'","Passed");
           }
           else if(text.equals(""))
           {
               value = element.getAttribute("value");
               if(!ExpectedText.equals(value))
               {
               	Generic.WriteTestData("Comparing text of field '"+locator+"' in Page '"+PageName,locator,ExpectedText,"The ExpectedText '"+ExpectedText+"' should not be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is not same as the Actual Text '"+text+"'","Passed");
               }
               else
               {
               	Generic.WriteTestData("Comparing text of field '"+locator+"' in Page '"+PageName,locator,ExpectedText,"The ExpectedText '"+ExpectedText+"' should not be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is  same as the Actual Text '"+text+"'","Failed");
               }
           }
           else if (ExpectedText.equals(text))
           {
           	//Control.takeScreenshot();
           	Generic.WriteTestData("Comparing text of field '"+locator+"' in Page '"+PageName,locator,ExpectedText,"The ExpectedText '"+ExpectedText+"' should not be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is same as the Actual Text '"+text+"'","Failed");
           }
           
       }catch (Exception e) {
       	//Control.takeScreenshot();
       	Generic.WriteTestData("Comparing text of field '"+locator+"' in Page '"+PageName,locator,ExpectedText,"The ExpectedText '"+ExpectedText+"' should not be equal to Actual text from Application","The ExpectedText '"+ExpectedText+"' is same as the Actual Text '"+text+"'","Failed");
           e.printStackTrace();
       }
       element = null; 
	}
	
    public static void objProperty(String PageName,String locator,String ExpectedPropertyToBeVerified, String ExpectedPropertyValue) {
        Constant.PageName=PageName;
        Constant.locator=locator;
        String text = null;
    
        WebElement element = findElement(PageName,locator);
        try {
            if (element != null)    
            
                text = element.getAttribute(ExpectedPropertyToBeVerified);
            if(ExpectedPropertyValue.equalsIgnoreCase(text)) {
                Generic.WriteTestData("Comparing property of field '"+locator+"' in Page '"+PageName+"'",locator,ExpectedPropertyValue,"The Expected property '"+ExpectedPropertyValue+"' should be equal to Actual property from Application","Actual Property '"+text+"' equals to Expected Property '"+ExpectedPropertyValue+"'","Passed");
            }
            
            else
            {
                takeScreenshot();
                Generic.WriteTestData("Comparing property of field '"+locator+"' in Page '"+PageName+"'",locator,ExpectedPropertyValue,"The Expected property '"+ExpectedPropertyValue+"' should be equal to Actual property from Application","Actual property '"+text+"' is NOT equals to Expected property '"+ExpectedPropertyValue+"'","Failed");
            }            
        }catch (Exception e) {
            e.printStackTrace();
        }
        element = null;
    }
    public static void objExists(String PageName,String locator,boolean ExistsOnPage) throws Exception {
        
        Constant.PageName=PageName;
        Constant.locator=locator;
        boolean text;
        WebElement element = findElement(PageName,locator);
        try {
        	
            if (element != null)   
            {
            	//text = element.isDisplayed();
            	text = true;
            }
            else
            {
            	text = false;
            	System.out.println(text);
            }
            System.out.println(text);
            
                //text = element.getAttribute("aria-disabled");
            if(ExistsOnPage) 
            {
                if(text= true) 
                {
                    Generic.WriteTestData("Check visibility of locator '"+locator+"' in Page '"+PageName+"'",locator,"","'"+locator+"' should be visible on the Application","'"+locator+"' is visbile on the page '"+PageName+"'","Passed");    
                }
                else
                {
                    takeScreenshot();
                    Generic.WriteTestData("Check visibility of locator '"+locator+"' in Page '"+PageName+"'",locator,"","'"+locator+"' should be visible on the Application","'"+locator+"' is Not visbile on the page '"+PageName+"'","Failed");
                }                
            }            
            else
            {
            	System.out.println(text);
                if(text == true) 
                {
                    takeScreenshot();
                    Generic.WriteTestData("Check visibility of locator '"+locator+"' in Page '"+PageName+"'",locator,"","'"+locator+"' should not be visible on the Application","'"+locator+"' is visbile on the page '"+PageName+"'","Failed");
                }
                else
                {
                    Generic.WriteTestData("Check visibility of locator '"+locator+"' in Page '"+PageName+"'",locator,"","'"+locator+"' should not be visible on the Application","'"+locator+"' is not visbile on the page '"+PageName+"'","Passed");
                }
                
            }        
        }catch (Exception e) {
            e.printStackTrace();
            Generic.WriteTestData("Check visibility of locator '"+locator+"' in Page '"+PageName+"'",locator,"","'"+locator+"' should be visible on the Application","'"+locator+"' is Not visbile on the page '"+PageName+"'","Failed");
            
            
        }
        element = null;
    }
    public static void objExists1(String PageName,String locator,boolean ExistsOnPage) throws Exception {
    	Constant.driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        Constant.PageName=PageName;
        Constant.locator=locator;
        boolean text;
        WebElement element = findElement(PageName,locator);
        try {
        	
            if (element != null)   
            {
            	//text = element.isDisplayed();
            	text = true;
            }
            else
            {
            	text = false;
            	System.out.println(text);
            }
            System.out.println(text);
            
                //text = element.getAttribute("aria-disabled");
            if(ExistsOnPage) 
            {
                if(text= true) 
                {
                    Generic.WriteTestData("Check visibility of locator '"+locator+"' in Page '"+PageName+"'",locator,"","'"+locator+"' should be visible on the Application","'"+locator+"' is visbile on the page '"+PageName+"'","Passed");    
                }
                else
                {
                    takeScreenshot();
                    Generic.WriteTestData("Check visibility of locator '"+locator+"' in Page '"+PageName+"'",locator,"","'"+locator+"' should be visible on the Application","'"+locator+"' is Not visbile on the page '"+PageName+"'","Failed");
                }                
            }            
            else
            {
            	System.out.println(text);
                if(text == true) 
                {
                    takeScreenshot();
                    Generic.WriteTestData("Check visibility of locator '"+locator+"' in Page '"+PageName+"'",locator,"","'"+locator+"' should not be visible on the Application","'"+locator+"' is visbile on the page '"+PageName+"'","Failed");
                }
                else
                {
                    Generic.WriteTestData("Check visibility of locator '"+locator+"' in Page '"+PageName+"'",locator,"","'"+locator+"' should not be visible on the Application","'"+locator+"' is not visbile on the page '"+PageName+"'","Passed");
                }
                
            }
            Constant.driver.manage().timeouts().implicitlyWait(Constant.defaultBrowserTimeOut, TimeUnit.SECONDS);
        }catch (Exception e) {
            e.printStackTrace();
            Generic.WriteTestData("Check visibility of locator '"+locator+"' in Page '"+PageName+"'",locator,"","'"+locator+"' should be visible on the Application","'"+locator+"' is Not visbile on the page '"+PageName+"'","Failed");
            Constant.driver.manage().timeouts().implicitlyWait(Constant.defaultBrowserTimeOut, TimeUnit.SECONDS);
            
        }
        element = null;
    }
    public static String HandleWebAlert(boolean Accept) throws Exception{
    	Thread.sleep(20000);
        Alert alert = Constant.driver.switchTo().alert();
        String textonalert = alert.getText();
        if(Accept) { 
            alert.accept();
//            Generic.WriteTestData("Check visibility of locator '"+locator+"' in Page '"+PageName+"'",locator,"","'"+locator+"' should not be visible on the Application","'"+locator+"' is not visbile on the page '"+PageName+"'","Passed");
        }
        else
        {
            alert.dismiss();
        }
        return textonalert;
        
    }
    public static void takeScreenshot()throws Exception {
    	takeScreenshot(Constant.DefaultoptionalFlag);
    }
    public static void takeScreenshot(boolean optionalFlag)throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		Date date= new Date();
		String Date1 = dateFormat.format(date);
		DateFormat dateFormat1 = new SimpleDateFormat("HHMMSS");
		Date date2= new Date();
		String Date3 = dateFormat1.format(date2); 	
		String screenshotFilePath;
			
		 screenshotFilePath=Constant.ScreenshotFolderName+File.separator+Constant.PageName+"_" + Constant.locator + "_"+Date1+"_"+Date3+".png";
		Constant.RecentScreenshot = Constant.PageName+"_" + Constant.locator + "_"+Date1+"_"+Date3+".png";
		try{                                        
	        TakesScreenshot scrShot = ((TakesScreenshot)Constant.driver);
	        File srcFile = scrShot.getScreenshotAs(OutputType.FILE);
	        File DestFile = new File(screenshotFilePath);
	        FileHandler.copy(srcFile,DestFile);	        
			    if(optionalFlag) {
			        Generic.setScreenshothyperlink(screenshotFilePath);}
			    else {
			    	Constant.DefaultoptionalFlag = true;
			    	
			    }
	         }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

  public static void getMessageContent(String PageName, String locator) {
    	 Constant.PageName=PageName;
         Constant.locator=locator;
         String Messagecontent = null;
         WebElement element = findElement(PageName,locator);
         try {
 			if (element != null) {
 				Messagecontent = element.getText();	
 				Generic.WriteTestData("Able to get the text '"+locator+"'",locator,"","Able to fetch  '"+locator+"'","text value is : "+Messagecontent,"Passed");
 			}
 			else {
 				
 				Generic.WriteTestData("Not Able to get the text '"+locator+"'",locator,"","Not Able to fetch  '"+locator+"'","text value is not found :","Failed");
 			}
 			element = null;		

 		} catch (Exception e) {
 			System.out.println(" Error occured whlie click on the element "
 					+ locator + " *** " + e.getMessage());
 			
 		}
    	    	
    }
    
	public static void scroll(String PageName, String locator) {
		 Constant.PageName=PageName;
         Constant.locator=locator;
         WebElement element = findElement(PageName,locator);
         try {
 			if (element != null) {
 				JavascriptExecutor js = (JavascriptExecutor) Constant.driver; 
 				js.executeScript("arguments[0].scrollTop = arguments[1];",element, 250); 
 				Thread.sleep(1000); 
 				Generic.WriteTestData("Able to Scroll '"+locator+"'",locator,"","Able to Scroll  '"+locator+"'","Successfully scroll operation is performed","Passed");
 			}
 			else {
 				
 				Generic.WriteTestData("Not Able to Scroll '"+locator+"'",locator,"","Not Able to Scroll '"+locator+"'","Scroll operation is not performed","Failed");
 			}
 			element = null;		

 		} catch (Exception e) {
 			System.out.println(" Error occured whlie click on the element "
 					+ locator + " *** " + e.getMessage());
 			
 		}
	}

	public static void captureScreenshot()throws Exception 
	{
		captureScreenshot(Constant.DefaultoptionalFlag);
    }

	public static void captureScreenshot(boolean optionalFlag)throws Exception
    {
		DateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
		Date date= new Date();
		String Date1 = dateFormat.format(date);
		DateFormat dateFormat1 = new SimpleDateFormat("HHMMSS");
		Date date2= new Date();
		String Date3 = dateFormat1.format(date2); 	
		String screenshotFilePath;
			
		screenshotFilePath=Constant.ScreenshotFolderName+File.separator+Constant.PageName+"_" + Constant.locator + "_"+Date1+"_"+Date3+".png";
		Constant.RecentScreenshot = Constant.PageName+"_" + Constant.locator + "_"+Date1+"_"+Date3+".png";
		try{                                        
	       Robot robot = new Robot();
           BufferedImage image = robot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit().getScreenSize()));
           File DestFile = new File(screenshotFilePath);
	       ImageIO.write(image, "png", DestFile);
	        if(optionalFlag) {
			        Generic.setScreenshothyperlink(screenshotFilePath);}
			    else {
			    	Constant.DefaultoptionalFlag = true;
			    }
	         }catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
	
	public static WebElement customWait(String PageName, String locator, int timeout_exp) throws InterruptedException {
		double x = Math.ceil(timeout_exp / Constant.defaultBrowserTimeOut);
		System.out.println("x is: " + x);
		WebElement a = null;
		for (double i = 0; i < x; i++) {
			System.out.println("Waiting for element "+locator);
			a = Control.findElement(PageName, locator);
			if (a != null) {
				break;
			}
		}
		return a;
	}
	
	public static void DoAtEndOfEachTestCase()
	{
		Constant.testFailed = false;
		 Constant.driver.quit();
	}
	
	public static String TestData(String key) {
		return Constant.TestData1.get(key);		
	}
	public static void getmap(){
		Map<String, String> map = Constant.TestData1;
			for (Map.Entry<String, String> entry : map.entrySet()) {
			    System.out.println(entry.getKey() + "/" + entry.getValue());
			}
	}
	public static void DoAtStartOfEveryTestCase(int RowNo) throws Exception
	{
			System.out.println("************************** [ TestCase #:"+ RowNo+"] **************************");
			Generic.WriteTestCase(TestData("PromoeNo"), TestData("Name"), TestData("Expected_result"), TestData("Actual Result"));
	}

	
	public static void GeneratePDFReport()
	{
		String excel_path=Constant.ResultFilePath.substring(0,Constant.ResultFilePath.lastIndexOf("\\"))+"\\";
		System.out.println("Path to Result : "+excel_path);
        JavaReport jr=new JavaReport();
        jr.GenerateReport(Constant.path_to_python_scripts, Constant.ResultFilePath, excel_path, Constant.ResultFilePath, "Report_Test_Summary"+".pdf");
	}
	public static void disabled(String PageName, String locator) throws Exception {
		Constant.PageName=PageName;
		Constant.locator=locator;
		
		try {
			WebElement element = findElement(PageName,locator);
			if(!element.isEnabled()) {
				Generic.WriteTestData("Click on '"+locator+"'",locator,"","Unable to click on '"+locator+"' button."," Unable To Clicked on '"+locator+"' button.","Passed");
			}
			else
				Generic.WriteTestData("Click on '"+locator+"'",locator,"","Able to click on '"+locator+"' button.","Clicked on '"+locator+"' button.","Failed");
		}catch (Exception e) {
			Generic.WriteTestData("Click on '"+locator+"'",locator,"","Able to click on '"+locator+"' button.","Clicking on '"+locator+"' button is unsuccessful.","Failed");
			System.out.println(" Error occured whlie click on the element "
					+ locator + " *** " + e.getMessage());
			
		}
	}
	
	public static String getAlphaNumericString(int n)
    {
  
        // chose a Character random from this String
        String AlphaNumericString = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                                    + "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz";
  
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
  
        for (int i = 0; i < n; i++) {
  
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index
                = (int)(AlphaNumericString.length()
                        * Math.random());
  
            // add Character one by one in end of sb
            sb.append(AlphaNumericString
                          .charAt(index));
        }
  
        return sb.toString();
    }

 
				
				
		
	
	
	
	
	  
}   


