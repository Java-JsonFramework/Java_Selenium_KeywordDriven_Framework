package com.src;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.relevantcodes.extentreports.LogStatus;

public class SeleniumFunctionsLibrary {

	
	public static WebDriver driver;
	SeleniumScript obj = new SeleniumScript();
	
	public Boolean close = false;
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	
	public WebDriver launchApplication(String url, String browser)
	{
		RunSeleniumScripts ob = new RunSeleniumScripts();
		
		driver = ob.driver;
		
		PropertyConfigurator.configure(System.getProperty("user.dir")+"\\Resources\\log4j.properties");
		
		if(browser.equalsIgnoreCase("CHROME"))
		{
			System.out.println("launching chrome browser");
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir")+"\\browserDrivers\\chromedriver.exe");
			
			driver = new ChromeDriver();
			driver.get(url);
			driver.manage().window().maximize();
			obj.test.log(LogStatus.PASS, "Navigated to the URL: "+url);
		}
		if(browser.equalsIgnoreCase("IE"))
		{
			System.out.println("launching Internet Explorer browser");
			System.setProperty("webdriver.ie.driver", System.getProperty("user.dir")+"\\browserDrivers\\IEDriverServer.exe");
			
			driver = new InternetExplorerDriver();
			driver.get(url);
			driver.manage().window().maximize();
			obj.test.log(LogStatus.PASS, "Navigated to the URL: "+url);
		}
		return driver;
	}
	
	public void checkElementPresent(String element, int timeout)
	{
		if(close==false)
		{
			WebDriverWait wait = new WebDriverWait(driver, timeout);
			try
			{
				ExpectedCondition<Boolean> condition = new ExpectedCondition<Boolean>() {
					
					@SuppressWarnings("unused")
					public Boolean apply(WebDriver driver1)
					{
						WebElement search = driver.findElement(By.xpath(element));
						String name = search.getAttribute("value");
						if(name == null||name.equalsIgnoreCase(""))
							name = search.getText();
						if(search!=null)
						{
							obj.test.log(LogStatus.PASS, "Element: "+name+" is present");
							return true;
						}
						else
						{
							obj.test.log(LogStatus.FAIL, "Element: "+name+" is not present");
							return true;
						}
					}
					
				};
				
				try
				{
					wait.until(condition);
				}
				catch(Exception e)
				{
					obj.test.log(LogStatus.FAIL, "Element is not present");
					close();
				}
			}
			catch(Exception e)
			{
				obj.test.log(LogStatus.FAIL, "Element is not present");
				close();
			}
		}
	}
	
	public void setValue(String element, String value)
	{
		if(close == false)
		{
			try
			{
				WebElement search = driver.findElement(By.xpath(element));
				search.sendKeys(value);
				obj.test.log(LogStatus.PASS, "Value: "+ value+ " set successfully");
				
			}
			catch(Exception e)
			{
				obj.test.log(LogStatus.FAIL, "Value: "+ value+ " not set successfully");
				close();
			}
		}
	}
	public void click(String element)
	{
		if(close == false)
		{
			try
			{
				WebElement search = driver.findElement(By.xpath(element));
				search.click();
				obj.test.log(LogStatus.PASS, "Element clicked successfully");
				
			}
			catch(Exception e)
			{
				obj.test.log(LogStatus.FAIL, "Element not clicked successfully");
				close();
			}
		}
	}
	
	//for passed report
	public void closeApplication()
	{
		if(close == false)
		{
			driver.close();
			obj.test.log(LogStatus.PASS, "Application closed successfully");
		}
	}
	
	
	
	//for failed report
	public void close()
	{
		try
		{
			String screenshotPath = getScreenshot(driver,obj.testCase);
			obj.test.log(LogStatus.FAIL, obj.test.addScreenCapture(screenshotPath));
			
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		driver.close();
		obj.test.log(LogStatus.PASS, "Driver closed successfully");
		close = true;
	}
	
	public static String getScreenshot(WebDriver driver, String screenshotname) throws IOException
	{
		String dateName = new SimpleDateFormat("ddMMyyyyhhmmss").format(new Date());
		TakesScreenshot ts = (TakesScreenshot)driver;
		File source = ts.getScreenshotAs(OutputType.FILE);
		String destination = System.getProperty("user.dir")+"\\Resources\\Results\\FailedScreenshots\\"+screenshotname+dateName;
		File finalDestination = new File(destination);
		FileUtils.copyFile(source, finalDestination);
		return destination;
	}
	

}
