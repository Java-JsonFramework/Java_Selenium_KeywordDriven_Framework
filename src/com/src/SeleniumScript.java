package com.src;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;

public class SeleniumScript {

	public static String testSet = "\\TestSet.xlsx";
	public static String Datasheet = "\\DataSheet.xlsx";
	public static String locator = "\\Locators.xlsx";
	public static String testCase = null;
	
	//*****************************************************
	
	public static List<String> testSetValue = new ArrayList<String>();
	public static List<String> steps = new ArrayList<String>();
	public static List<String> data = new ArrayList<String>();
	public static List<String> parameters = new ArrayList<String>();
	public static List<String> dataUse = new ArrayList<String>();
	public static List<String> locators = new ArrayList<String>();
	public static List<String> locatorsUse = new ArrayList<String>();
	public static HashMap<String, String> dataMap = new HashMap<>();
	public static HashMap<String, String> locatorsMap = new HashMap<>();
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	static ExtentTest test;
	static ExtentReports report;
	
	
	public void loadExtentReports()
	{
		report = new ExtentReports(System.getProperty("user.dir")+"\\Resources\\Results\\ExtentReportResults.html");
		report
		.addSystemInfo("Host Name", "Capgemini")
		.addSystemInfo("Environment", "QA")
		.addSystemInfo("User Name", "Sankhadeep");
		
		File configFile = new File((System.getProperty("user.dir")+"\\Resources\\extentConfig.xml"));
	}
	
	public void flushReports()
	{
		report.flush();
		report.close();
	}
	
	@Test
	public void getValues() throws IOException 
	{
		loadExtentReports();
		ExcelRead excel = new ExcelRead(testSet);
		
		testSetValue = excel.readExcel(testSet);
		
		for(int i =1; i<testSetValue.size();i=i+2)
		{
			if(testSetValue.get(i).equalsIgnoreCase("Y"))
			{
				testCase = testSetValue.get(i-1);
				
				System.out.println(testCase);
				
				test = report.startTest(testCase);
				
				steps = excel.readExcel("\\TestCases\\"+testCase+".xlsx");
				
				for(int s = 1;s<steps.size();s=s+2)
				{
					String step = steps.get(s);
					if(!step.equalsIgnoreCase("")&&!step.equalsIgnoreCase("no data"))
					{
						parameters.add(steps.get(s));
					}
				}
				
				data = excel.readExcel(Datasheet);
				int j = 0;
				
				for(int d =0;d<data.size();d++)
				{
					if(j==parameters.size())
						break;
					String datastep = data.get(d);
					String parameterStep = parameters.get(j);
					
					if(!parameters.get(j).equalsIgnoreCase(null))
					{
						if(parameters.get(j).equalsIgnoreCase(data.get(d)))
						{
							dataUse.add(data.get(d+1));
							dataMap.put(data.get(d), data.get(d+1));
							j++;
						}
					}
				}
				
				for(int p =2;p<steps.size();p=p+2)
				{
					String step = steps.get(p);
					if(!step.equalsIgnoreCase("")&&!step.equalsIgnoreCase("no data"))
					{
						locators.add(steps.get(p));
					}
				}
				
				locatorsUse = excel.readExcel(locator);
				
				int k = 0;
				
				for(int q = 0;q<locatorsUse.size();q=q+2)
				{
					String locatorUse = locatorsUse.get(q);
					String locatorxpath = locatorsUse.get(q+1);
					locatorsMap.put(locatorsUse.get(q),locatorsUse.get(q+1));
					
				}
				System.out.println();
				
				RunSeleniumScripts ob = new RunSeleniumScripts();
				ob.run();
				
				steps = new ArrayList<String>();
				data = new ArrayList<String>();
				parameters = new ArrayList<String>();
				dataUse = new ArrayList<String>();
				dataMap = new HashMap<>();
				locators = new ArrayList<String>();
				locatorsUse = new ArrayList<String>();
				locatorsMap = new HashMap<>();
				
				report.endTest(test);
				
				
			}
		
		
		}
		flushReports();
	}
}
