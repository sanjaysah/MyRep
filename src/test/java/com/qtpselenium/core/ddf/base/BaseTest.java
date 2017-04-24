package com.qtpselenium.core.ddf.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.qtpselenium.core.ddf.util.ExtentManager;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;



public class BaseTest {
	public WebDriver driver = null;
	public Properties prop;
	public WebElement e = null;
	public ExtentReports rep = new ExtentManager().getInstance();
	public ExtentTest test= null;
	
	public void initProp(){
		//Intialize the Property file
		if(prop==null){
		prop = new Properties();
			try{
			FileInputStream fis = new FileInputStream(System.getProperty("user.dir")+"//src//test//resources//projectconfig.properties");
			prop.load(fis);
			}catch(Exception e){
				e.printStackTrace();
			}
			//System.out.println(prop.getProperty("firefoxdriver_exe"));
		}
	}
	
	public void openBrowser(String brows){
		
		if(brows.equals("Mozilla")){
			System.setProperty("webdriver.gecko.driver",prop.getProperty("firefoxdriver_exe"));
			driver = new FirefoxDriver();
		}else if(brows.equals("Chrome")){
			System.setProperty("webdriver.chrome.driver",prop.getProperty("chromedriver_exe"));
			driver = new ChromeDriver();
		}else if(brows.equals("IE")){
			System.setProperty("webdriver.ie.driver",prop.getProperty("iedriver_exe"));
			driver = new ChromeDriver();
		}
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}
	
	public void navigate(String urlKey){
		
		driver.navigate().to(prop.getProperty(urlKey));
	}
	
	public void type(String locator,String emailidKey){
		getElement(locator).sendKeys(prop.getProperty(emailidKey));
	}
	
	public void click(String locator){
		getElement(locator).click();
		}
	
	public WebElement getElement(String locator){   //finding element and returning it
		try{
		if(locator.endsWith("_id")){
			e=driver.findElement(By.id(prop.getProperty(locator)));
		}else if(locator.endsWith("_name")){
			e=driver.findElement(By.name(prop.getProperty(locator)));
		}else if(locator.endsWith("_xpath")){
			e=driver.findElement(By.xpath(prop.getProperty(locator)));
		}else if(locator.endsWith("_class")){
			e=driver.findElement(By.className(prop.getProperty(locator)));
		}else if(locator.endsWith("_css")){
			e=driver.findElement(By.cssSelector(prop.getProperty(locator)));
		}else if(locator.endsWith("_linktext")){
			e=driver.findElement(By.linkText(prop.getProperty(locator)));
		}else if(locator.endsWith("_plinktext")){
			e=driver.findElement(By.partialLinkText(prop.getProperty(locator)));
		}else if(locator.endsWith("_tagname")){
			e=driver.findElement(By.tagName(prop.getProperty(locator)));
		}else{
			reportFail("Locator Not matching"+locator);
			Assert.fail("Locator Not matching"+locator);
		}
		}catch(Exception e){
			//report the failure
			reportFail(e.getMessage());
			e.printStackTrace();
			Assert.fail("Locator Not matching"+e.getMessage());
		}
		return e;
		}
	
	public void select(){
		
	}
	
	public void closeBrowser(){
		
	}
	
	//********************************Validation Functions***************************************//
	
	public boolean verifyTitle(){
		return false;
	}
	
	public boolean isElementPresent(String locatorKey){
		List<WebElement> elementList=null;
		if(locatorKey.endsWith("_id")){
			elementList = driver.findElements(By.id(prop.getProperty(locatorKey)));
		}else if(locatorKey.endsWith("_name")){
			elementList=driver.findElements(By.name(prop.getProperty(locatorKey)));
		}else if(locatorKey.endsWith("_xpath")){
			elementList=driver.findElements(By.xpath(prop.getProperty(locatorKey)));
		}else if(locatorKey.endsWith("_class")){
			elementList=driver.findElements(By.className(prop.getProperty(locatorKey)));
		}else if(locatorKey.endsWith("_css")){
			elementList=driver.findElements(By.cssSelector(prop.getProperty(locatorKey)));
		}else if(locatorKey.endsWith("_linktext")){
			elementList=driver.findElements(By.linkText(prop.getProperty(locatorKey)));
		}else if(locatorKey.endsWith("_plinktext")){
			elementList=driver.findElements(By.partialLinkText(prop.getProperty(locatorKey)));
		}else if(locatorKey.endsWith("_tagname")){
			elementList=driver.findElements(By.tagName(prop.getProperty(locatorKey)));
		}else{
			reportFail("Locator Not matching"+locatorKey);
			Assert.fail("Locator Not matching"+locatorKey);
		}
		
		if(elementList.size()==0)
			return false;	
		else
			return true;
	}
	
	public boolean verifyText(String locatorKey,String msg){
		String actualText = getElement(locatorKey).getText().trim();
		String expText = prop.getProperty(msg);
		if(actualText.equals(expText))
			return true;
		else
			return false;
	}

	 //***********************************Reporting Functions************************************//
	
	public void reportPass(String msg){
		test.log(LogStatus.PASS, msg);
	}
	
	public void reportFail(String msg){
		test.log(LogStatus.FAIL, msg);
		takeScreenShot();
		Assert.fail(msg);
	}
	
	public void takeScreenShot(){
		// fileName of the screenshot
		Date d=new Date();
		String screenshotFile=d.toString().replace(":", "_").replace(" ", "_")+".png";
		// store screenshot in that file
		File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(scrFile, new File(System.getProperty("user.dir")+"//screenshots//"+screenshotFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//put screenshot file in reports
		test.log(LogStatus.INFO,"Screenshot-> "+ test.addScreenCapture(System.getProperty("user.dir")+"//screenshots//"+screenshotFile));
		
	}
	
}
