package com.src;

import java.util.LinkedHashSet;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.Test;

public class RunSeleniumScripts {

	
	public static WebDriver driver;
	
	public static Set<String> keywordSet = new LinkedHashSet<String>();
	
	public static String keyword = null;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public void run()
	{
		keywordSet = new LinkedHashSet<String>();
		
		SeleniumScript scriptDriver = new SeleniumScript();
		
		SeleniumFunctionsLibrary webDriver = new SeleniumFunctionsLibrary();
		
		for(int i=0;i<scriptDriver.steps.size();i=i+2)
		{
			keywordSet.add(scriptDriver.steps.get(i));
		}
		
		for(String keyword:keywordSet)
		{
			switch(keyword)
			{
				case "Login":
				
					driver = webDriver.launchApplication(scriptDriver.dataMap.get("URL"), "CHROME");
					
					webDriver.checkElementPresent(scriptDriver.locatorsMap.get("username"), 20);
					
					webDriver.setValue(scriptDriver.locatorsMap.get("username"), scriptDriver.dataMap.get("username"));
					
					webDriver.setValue(scriptDriver.locatorsMap.get("password"), scriptDriver.dataMap.get("password"));
					
					webDriver.click(scriptDriver.locatorsMap.get("login"));
					
					break;
					
				case "Homepage":
					
					webDriver.checkElementPresent(scriptDriver.locatorsMap.get("ContinueButton"), 20);
					
					break;
				case "Admin":
					
					//webDriver.checkElementPresent(scriptDriver.locatorsMap.get("adminButton"), 20);
					webDriver.click(scriptDriver.locatorsMap.get("ContinueButton"));
					break;	
					
					
				case "Close":
					webDriver.closeApplication();
					break;
			}
		}
	}

}
