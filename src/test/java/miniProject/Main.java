package miniProject;
 
import java.util.Scanner;
import java.io.IOException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
 
@Listeners(miniProject.Extentreport.class)
public class Main
{
	String link;
	String toSearch;
	String toSelect;
	boolean condition;
	String file;
	int rows;
	String Browser;
 
	@BeforeClass
	public void Beforeclass() throws IOException
	{
		boolean bol = true;
		while(bol) {
			System.out.println("Enter the browser to Automate (Chrome/Edge):");
			Scanner sc = new Scanner(System.in);
			Browser = sc.next();
			if(Browser.equalsIgnoreCase("Chrome") || (Browser.equalsIgnoreCase("Edge"))){
				SearchMobile.getWebDriver(Browser);
				bol =false;
				sc.close();
			}else {
				System.out.println("Invalid Browser name(Chrome or Edge)");
				
			}	
	    }
		file = System.getProperty("user.dir")+"\\TestData\\Details (1).xlsx";
		//Get the row count from the excel
		rows = ExcelUtils.getRowCount(file, "Amazon");
		
		for(int i=1; i<=rows;i++) {
		//read data from excel
		    link = ExcelUtils.getCellData(file, "Amazon", rows, 0);
		    toSearch = ExcelUtils.getCellData(file, "Amazon", rows, 1);
		  	toSelect = ExcelUtils.getCellData(file, "Amazon", rows, 2);
		}
	}
	
	@Test(priority=1)
	public void launchingApplication() throws IOException
	{	
		//pass data to the driver
		SearchMobile.LaunchUrl(link);
	}
	@Test(priority=2)
	public void maximizeApplication() throws IOException, InterruptedException
	{
		//Maximizing the window
	    SearchMobile.MaximizeWindow();
	}
	@Test(priority=3)
	public void searchItem() throws InterruptedException, IOException
	{
		//Search in the application
		SearchMobile.toSearch(toSearch);
	}
	@Test(priority=4)
	public void validation() throws IOException, InterruptedException
	{
		//Validate the search string
		condition=SearchMobile.Validation(toSearch);
	}
	@Test(priority=5)
	public void selectDropdown() throws IOException, InterruptedException
	{
		//Select option “Newest Arrivals"
		SearchMobile.dropSelect(toSelect);
 
	}
	@Test(priority=6)
	public void writingExcel() throws IOException, InterruptedException
	{
		//write output in excel
		for(int i=1; i<=rows;i++)
		{
		if(condition)
		 {
		 	System.out.println("\nTest Result is Printed in EXCEL");
		 	ExcelUtils.setCellData(file, "Amazon",i,4,"Passed");					
		 }
		else
		 {
			System.out.println("\nTest Result is Printed in EXCEL");
			ExcelUtils.setCellData(file, "Amazon",i,4,"Failed");
		 }
		}	
	}
	@AfterClass
    public void afterclass() throws InterruptedException {
		//Closing the browser
		SearchMobile.closeBrowser();
	}
}