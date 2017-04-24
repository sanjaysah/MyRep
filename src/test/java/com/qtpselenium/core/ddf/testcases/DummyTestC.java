package com.qtpselenium.core.ddf.testcases;

import java.util.Hashtable;

import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qtpselenium.core.ddf.base.BaseTest;
import com.qtpselenium.core.ddf.util.DataUtil;
import com.qtpselenium.core.ddf.util.Xls_Reader;
import com.relevantcodes.extentreports.LogStatus;

public class DummyTestC extends BaseTest{
	SoftAssert softAssert;
	String testName="TestC";
	Xls_Reader xls;
	
	@BeforeTest
	public void init(){
		softAssert = new SoftAssert();
	}
	
	@Test(dataProvider="getData")
	public void testC(Hashtable<String,String> data){
		
		test=rep.startTest("DummyTestC");
		test.log(LogStatus.INFO, "Dummy Test C starting");
		
		if(!DataUtil.isRunnableTescase(xls, testName)||data.get("Runmode").equals("N")){
			test.log(LogStatus.SKIP, "Skipping the test as runmode is N");
			throw new SkipException("Skipping the test as runmode is N");
		}
		
		openBrowser("Chrome");
		test.log(LogStatus.INFO, "Open the Browser");
		//test.log(LogStatus.PASS, "Dummy Test C passed");
		test.log(LogStatus.FAIL, "Dummy Test C Failed");
		test.log(LogStatus.FAIL, "Screenshot"+test.addScreenCapture("D:\\Programming\\Screenshot1.png"));
	}
	
	@DataProvider
	public Object[][] getData(){
		initProp();
		xls = new Xls_Reader(prop.getProperty("xlspath"));
		return DataUtil.getTestData(xls, testName);
	}
	
	@AfterMethod
	public void quit() throws InterruptedException{
		try{
			softAssert.assertAll();
		}catch(Error e){
			test.log(LogStatus.FAIL, e.getMessage());
		}
		rep.endTest(test);
		rep.flush();
		Thread.sleep(5000);
		driver.quit();
	}
}
