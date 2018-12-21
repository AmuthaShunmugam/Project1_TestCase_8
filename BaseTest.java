package com.ibm.test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.ibm.pages.AdminPage;
import com.ibm.pages.AdminPage1;
import com.ibm.pages.UserPage;
import com.ibm.utilities.ExcelUtil;
import com.ibm.utilities.PropertiesFileHandler;

public class BaseTest {
	WebDriver driver;
	WebDriverWait wait;
	PropertiesFileHandler propFIleHandler;
	HashMap<String, String> data;

	@BeforeSuite
	public void propertiesfile() throws IOException {
		String file = "./TestData/data.properties";
		PropertiesFileHandler propFileHandler = new PropertiesFileHandler();
		data = propFileHandler.getPropertiesAsMap(file);
	}

	@BeforeMethod
	public void BrowserInitialization() {
		System.setProperty("webdriver.chrome.driver", "./drivers/chromedriver.exe");
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 60);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

		
		
		
	@Test
	public void ChangeGSTDiscountandVerify() throws InterruptedException
	{
		String url = data.get("url");
		String userName = data.get("username");
		String password = data.get("password");
		String GST=data.get("Gst");
		String Discount=data.get("Disc");
		String srch=data.get("Search");
		//Login in the admin portal and change the GST vlaue of a product and save get the value from table and reduce it by 10%
		driver.get(url);
		AdminPage1 home = new AdminPage1(driver, wait);
		home.EnetrEmailAddress(userName);
		home.EnetrPassword(password);
		home.ClickonLoginButton();
		home.ClickonCatalogTabButton();Thread.sleep(2000);
		home.ClickonProduct();Thread.sleep(2000);
		home.ClickonProductAction();
		home.ClickonProductEdit();
		home.ClickonData();
		home.EntertheGSTValue(GST);
		home.ClickonTheSaveButton();
		WebElement amt1=driver.findElement(By.xpath("//table[@id='dataTableExample2']/tbody/tr[3]/td[5]"));
		String Amount1=amt1.getText().trim().replace(",","").replaceAll(".00", "").replace("121","").trim();
		System.out.println(Amount1);
		WebElement Success=driver.findElement(By.xpath("//div[@class='alert alert-success alert-dismissible']"));
		String Msg=Success.getText();
		System.out.println(Msg);
		Integer  value=12;
		Integer Calc= (value)*(Integer.parseInt(GST));
		Integer Amt= Integer.parseInt(Amount1)-Calc;
		System.out.println("The Amount from admin page:" +Amt);
		//Navigate to user portal and get the amount of modified product compare the value of admin and user
		driver.navigate().to("https://atozgroceries.com"); 
	    UserPage user= new UserPage(driver,wait);
		user.ClickonSearchbox();
		user.ClickonSearchElement();
		WebElement cost=driver.findElement(By.xpath("//p[@class='product-cost']"));
		String UserAmount=cost.getText().trim().replace("Discounted price","").replace("₹","").replace(",","").replace(":","").replace(".00","").trim().trim();
		Integer UserAmount1=Integer.parseInt(UserAmount);
		System.out.println("The Amount from user page:" +UserAmount1);
		Assert.assertEquals(UserAmount1,Amt);
	
	}


		
		
	}

