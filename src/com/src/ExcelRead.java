package com.src;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.gargoylesoftware.htmlunit.html.HtmlTableRow.CellIterator;

public class ExcelRead {

	public static String path = null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ExcelRead ob = new ExcelRead(path);

	}
	
	ExcelRead(String path)
	{
		this.path = path;
	}
	
	public List<String> readExcel(String path) throws IOException
	{
		String value = null;
		
		List<String> list = new ArrayList<String>();
		
		FileInputStream read = new FileInputStream(System.getProperty("user.dir")+"\\Resources\\"+path);
		
		XSSFWorkbook book = new XSSFWorkbook(read);
		
		XSSFSheet sheet = book.getSheetAt(0);
		
		Iterator<Row> row = sheet.rowIterator();
		
		while(row.hasNext())
		{
			Row rowIterator = row.next();
			
			Iterator<Cell> cell = rowIterator.iterator();
			
			while(cell.hasNext())
			{
				Cell cellIterator = cell.next();
				
				value = cellIterator.getStringCellValue();
				
				list.add(value);
			
				
			}
		
		}
		read.close();
		return list;
	}
	
}
