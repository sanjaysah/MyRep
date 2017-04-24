package com.qtpselenium.core.ddf.testcases;

import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import com.qtpselenium.core.ddf.base.BaseTest;
import com.relevantcodes.extentreports.LogStatus;

public class DummyTestA extends BaseTest{
	SoftAssert softAssert;
	
	@BeforeTest
	public void init(){
		softAssert = new SoftAssert();
	}
	
	@Test(priority=1)
	public void testA1(){
		test = rep.startTest("DummyTestA");
		test.log(LogStatus.INFO, "Starting the test");
		Assert.fail();
		test.log(LogStatus.FAIL, "Test Failed");
		}
	
	@Test(priority=2,dependsOnMethods={"testA1"})
	public void testA2(){
			
		}
	
	@Test(priority=3,dependsOnMethods={"testA1","testA2"})
	public void testA3(){
			
		}
}
