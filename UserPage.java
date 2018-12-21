package com.ibm.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class UserPage 
{

	//To click on 'Shop by Category'
	@FindBy(xpath="//*[@id='categories-menu']/ul/li/span")
	WebElement ShopCategoryEle;
	
	//To click on 'Search for product'
	@FindBy(xpath="//input[@id='search-box']")
	WebElement SearchBoxEle;
	
	//To enter value on 'Search for Product'
	@FindBy(xpath="//input[@type='text']")
	WebElement SearchBoxValueEle;
	
	//To select from dropdown 'NewBadhamProduct'
	@FindBy(xpath="//div[@class='modal-body']/div/a[3]")
	WebElement BadhamEle;
	
	WebDriverWait wait;
	WebDriver driver;
	
	public UserPage(WebDriver driver, WebDriverWait wait)  
	{
		this.driver = driver;
		this.wait = wait;
		PageFactory.initElements(driver, this);
	}
	
	public void ClickonShopByCategory()
	{
		ShopCategoryEle.click();
	}
	
	public void ClickonSearchbox()
	{
		SearchBoxEle.click();
	}
	
	public void ClickonSearchElement()
	{
		BadhamEle.click();
	}
	
	public void GetofferValue(String Offer)
	{
		WebElement offer=driver.findElement(By.xpath("//div[@class='pdp-product__offer']"));
		String Offer1=offer.getText().replaceAll("% OFF","").trim();
		System.out.println("The discount is :" +Offer);
		
	}
	
	
	
	
	
	

}
