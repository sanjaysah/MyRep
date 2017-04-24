package com.qtpselenium.core.ddf.util;

import java.util.Hashtable;

public class DataUtil {
	
	public static Object[][] getTestData(Xls_Reader xls,String testName){

		String sheetName = "Data";
		int testNameStartRow=1;
		//Get Test case Name start row
		while(!xls.getCellData(sheetName, 0, testNameStartRow).equals(testName)){
			testNameStartRow++;
		}
		System.out.println(testName+" starts here -- "+testNameStartRow);
		
		//Get Row number in Test Case Data
		int colNameStartRow = testNameStartRow+1;
		int dataTestStartRow = testNameStartRow+2;
		int row=0;
		while(!xls.getCellData(sheetName, 0, dataTestStartRow+row).equals("")){
			row++;
		}
		System.out.println("Total Rows -- "+row);

		//Get Col number in Test Case Data
		int col=0;
		while(!xls.getCellData(sheetName, col, dataTestStartRow).equals("")){
			col++;
		}
		System.out.println("Total Cols -- "+col);
		
		//Read Data from TestData
		Object[][] data = new Object[row][1];
		Hashtable<String,String> table;
		int rowsObj=0;
		for(int rNum=dataTestStartRow;rNum<dataTestStartRow+row;rNum++){
			table = new Hashtable<String,String>();
			for(int cNum=0;cNum<col;cNum++){
				 String value = xls.getCellData(sheetName, cNum, rNum);
				 String key = xls.getCellData(sheetName, cNum, colNameStartRow);
				//System.out.println(data);
				 table.put(key, value);
			}
			data[rowsObj][0]=table;
			rowsObj++;
			//System.out.println("---------");
		}
		
		return data;
		
	}
	
	public static boolean isRunnableTescase(Xls_Reader xls,String testName){
		String sheetName = "TestCases";
		int rows = xls.getRowCount(sheetName);
		//int cols = xls.getColumnCount(sheetName);
		for(int rNum=2;rNum<=rows;rNum++){
		//	for(int cNum=0;cNum<cols;cNum++){
				String testID = xls.getCellData(sheetName, "TCID", rNum).trim();
				if(testID.equals(testName)){
					String runmode = xls.getCellData(sheetName, "Runmode", rNum).trim();
					if(runmode.equals("Y"))
						return true;
					else
						return false;
				}
		//	}
		}
		return false;
		
	}

}
