package strongroom;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import clickUP.createTask;
import objects.AdjustmentimprestPage;
import objects.DestroyPatientPage;
import objects.DestroyimprestPage;
import objects.LoginPage;
import objects.NotificationPage;
import objects.OutgoingPatientPage;
import objects.OutgoingimprestPage;
import objects.SecondPage;
import objects.SignPage;
import objects.Stocktakepage;
import objects.Stocktakepages;
import objects.TransferInPatientPage;
import objects.TransferInimprestPage;
import objects.TransferoutPatientPage;
import objects.TransferoutimprestPage;

public class Agedcare extends createTask implements ITestListener {
	private WebDriver driver;
	private WebDriverWait wait;
	private LoginPage loginPage;
	private SecondPage secondPage;
	private NotificationPage notificationPage;
	private Stocktakepage stocktakepage;
	private TransferInimprestPage transferInPage;
	private TransferoutimprestPage transferoutimprestPage;
	private TransferInPatientPage transferInPatientPage;
	private TransferoutPatientPage transferoutPatientPage;
	private DestroyimprestPage destroyimprestPage;
	private DestroyPatientPage destroyPatientPage;
	private OutgoingimprestPage outgoingimprestPage;
	private OutgoingPatientPage outgoingPatientPage;
	private AdjustmentimprestPage adjustmentimprestPage;
	private SignPage signPage;
	private Stocktakepages stocktakepages;
	private static String formattedDateTime; // Class variable to store formatted date and time
	private static String loginTaskDescription;
	private static String inputdata;

	@BeforeClass
	public void setUp() {
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		wait = new WebDriverWait(driver, Duration.ofSeconds(40));
		loginPage = new LoginPage(driver, wait);
		secondPage = new SecondPage(driver, wait);
		notificationPage = new NotificationPage(driver, wait);
		stocktakepage = new Stocktakepage(driver, wait);
		transferInPage = new TransferInimprestPage(driver, wait);
		transferoutimprestPage = new TransferoutimprestPage(driver, wait);
		transferInPatientPage = new TransferInPatientPage(driver, wait);
		transferoutPatientPage = new TransferoutPatientPage(driver, wait);
		destroyimprestPage = new DestroyimprestPage(driver, wait);
		destroyPatientPage = new DestroyPatientPage(driver, wait);
		outgoingimprestPage = new OutgoingimprestPage(driver, wait);
		outgoingPatientPage = new OutgoingPatientPage(driver, wait);
		adjustmentimprestPage = new AdjustmentimprestPage(driver, wait);
		signPage = new SignPage(driver, wait);
		stocktakepages = new Stocktakepages(driver, wait);
		formattedDateTime = getCurrentDateTime();

		// Test Scenario
		loginPage.openLoginPage();
		LoginPage.login();
		loginPage.clickLoginButton();

		secondPage.selectLocationFromDropdown();
		secondPage.clickSecondPageButton();
		notificationPage.clickNotificationIcon();

		String loginPageURL = driver.getCurrentUrl();
		String selectedLocation = secondPage.getSelectedLocation(); // Assuming you have a method to get selected
																	// location
		String enteredLocation = LoginPage.getEnteredLocation();
		String enteredUsername = LoginPage.getEnteredUsername(); // Assuming you have a method to get entered username
		String enteredPassword = LoginPage.getEnteredPassword(); // Assuming you have a method to get entered password

		// Creating ClickUp task description for login
		loginTaskDescription = "Login Page URL: " + loginPageURL + "\n" + "Entred Location: " + enteredLocation + "\n"
				+ "Entered Username: " + enteredUsername + "\n" + "Entered Password: " + enteredPassword + "\n"
				+ "Selected Location: " + selectedLocation;

	}

	@Test(priority = 9, invocationCount = 1, enabled = false)
	public void Adjustmentimprest() throws InterruptedException { 

		adjustmentimprestPage.Adjustment();
		adjustmentimprestPage.Transectionid();
		adjustmentimprestPage.writenote();
		adjustmentimprestPage.imprest();

		Thread.sleep(3000);
		signPage.performSignature();
		// signPage.performSignature("sam", "1111");

		Thread.sleep(6000);

	}

	@Test(priority = 8, invocationCount = 1, enabled = true)
	public void OutgoingPatient() throws InterruptedException {

		
		stocktakepage.clickStock();
		stocktakepage.clickStockTake();
		stocktakepage.enterMedication(0);
		stocktakepage.enterresidentname(0);
		Thread.sleep(3000);
		stocktakepage.Displayinstock();
		stocktakepage.searching();
		Thread.sleep(3000);

		int actualValue = stocktakepage.getExpectedValue();
		System.out.println("(Stock): " + actualValue);
		
		outgoingPatientPage.Outgoing();
		outgoingPatientPage.writenote();
		outgoingPatientPage.Resident();
		String selectdestroyqty = outgoingPatientPage.Getselectdestroyqty();
		String selectMedication = outgoingPatientPage.GetselectMedication();
		int selectQty = outgoingPatientPage.GetselectQty();

		Thread.sleep(3000);
		signPage.performSignature();
		Thread.sleep(6000);
		
		stocktakepages.clickStock();
		stocktakepages.clickStockTake();
		stocktakepages.enterMedication(0);
		stocktakepages.enterresidentname(0);
		Thread.sleep(3000);
		stocktakepages.Displayinstock();
		stocktakepages.searching();
		Thread.sleep(3000);
		
		

		int actualValue1 = stocktakepages.getExpectedValue();
		System.out.println("(Stock): " + actualValue1);
		
		
		int ExpectedQty = actualValue1 + selectQty ;
		System.out.print(ExpectedQty);
		
		if (ExpectedQty == actualValue) {
			inputdata = "OutGoing Quantity From Excel : "+ selectdestroyqty + "\n" + "Drug Name: " + 
					selectMedication +"\n" + "OutGoing in quantity:  " + selectQty +"\n" + "Current Stock: " 
					+ actualValue + "\n" + "After Destory Stock: " + actualValue1 +"\n"+ "Result: " + "Pass";
		}else {
			inputdata = "OutGoing Quantity From Excel : "+ selectdestroyqty + "\n" + "Drug Name: " + 
					selectMedication +"\n" + "OutGoing in quantity:  " + selectQty +"\n" + "Current Stock: " 
					+ actualValue + "\n" + "After Destory Stock: " + actualValue1 +"\n"+ "Result: " + "Fail";	
		}
	}

	@Test(priority = 7, invocationCount = 1, enabled = true)
	public void Outgoingimprest() throws InterruptedException {
		stocktakepage.clickStock();
		stocktakepage.clickStockTake();
		stocktakepage.enterMedication(0);
		stocktakepage.Displayimprest();
		Thread.sleep(3000);
		stocktakepage.searching();
		Thread.sleep(3000);
		int actualValue = stocktakepage.getExpectedValue();
		System.out.println("(Stock): " + actualValue);
		

		outgoingimprestPage.Outgoing();
		outgoingimprestPage.writenote();
		outgoingimprestPage.imprest();
		String selectdestroyqty = outgoingimprestPage.Getselectdestroyqty();
		String selectMedication = outgoingimprestPage.GetselectMedication();
		int selectQty = outgoingimprestPage.GetselectQty();

		Thread.sleep(3000);
		signPage.performSignature();
		Thread.sleep(6000);
		
		
		// Loop through the test execution
		stocktakepage.clickStock();
		stocktakepage.clickStockTake();
		stocktakepages.enterMedication(0);
		stocktakepages.Displayimprest();
		Thread.sleep(3000);
		stocktakepages.searching();
		Thread.sleep(3000);
		int actualValue1 = stocktakepages.getExpectedValue();
		System.out.println("(Stock): " + actualValue1);
		
		int ExpectedQty = actualValue1 + selectQty ;
		System.out.print(ExpectedQty);
		if (ExpectedQty == actualValue) {
			inputdata = "Destory Quantity From Excel : "+ selectdestroyqty + "\n" + "Drug Name: " + 
					selectMedication +"\n" + "Destory in quantity:  " + selectQty +"\n" + "Current Stock: " 
					+ actualValue + "\n" + "After Destory Stock: " + actualValue1 +"\n"+ "Result: " + "Pass";
		}else {
			inputdata = "Destory Quantity From Excel : "+ selectdestroyqty + "\n" + "Drug Name: " + 
					selectMedication +"\n" + "Destory in quantity:  " + selectQty +"\n" + "Current Stock: " 
					+ actualValue + "\n" + "After Destory Stock: " + actualValue1 +"\n"+ "Result: " + "Fail";	
		}
	}

	@Test(priority = 6, invocationCount = 1, enabled = true)
	public void DestroyPatient() throws InterruptedException {
		stocktakepage.clickStock();
		stocktakepage.clickStockTake();
		stocktakepage.enterMedication(0);
		stocktakepage.enterresidentname(0);
		Thread.sleep(3000);
		stocktakepage.Displayinstock();
		stocktakepage.searching();
		Thread.sleep(3000);

		int actualValue = stocktakepage.getExpectedValue();
		System.out.println("(Stock): " + actualValue);
		
		
		destroyPatientPage.Destroy();
		destroyPatientPage.writenote();
		destroyPatientPage.MethodOFDestruction();
		destroyPatientPage.CourierNameandNotes();
		destroyPatientPage.Resident();
		String selectdestroyqty = destroyPatientPage.Getselectdestroyqty();
		String selectMedication = destroyPatientPage.GetselectMedication();
		int selectQty = destroyPatientPage.GetselectQty();
		Thread.sleep(3000);
		signPage.performSignature();
		Thread.sleep(6000);
		
		stocktakepages.clickStock();
		stocktakepages.clickStockTake();
		stocktakepages.enterMedication(0);
		stocktakepages.enterresidentname(0);
		Thread.sleep(3000);
		stocktakepages.Displayinstock();
		stocktakepages.searching();
		Thread.sleep(3000);
		
		

		int actualValue1 = stocktakepages.getExpectedValue();
		System.out.println("(Stock): " + actualValue1);
		
		
		int ExpectedQty = actualValue1 + selectQty ;
		System.out.print(ExpectedQty);
		
		if (ExpectedQty == actualValue) {
			inputdata = "Destory Quantity From Excel : "+ selectdestroyqty + "\n" + "Drug Name: " + 
					selectMedication +"\n" + "Destory in quantity:  " + selectQty +"\n" + "Current Stock: " 
					+ actualValue + "\n" + "After Destory Stock: " + actualValue1 +"\n"+ "Result: " + "Pass";
		}else {
			inputdata = "Destory Quantity From Excel : "+ selectdestroyqty + "\n" + "Drug Name: " + 
					selectMedication +"\n" + "Destory in quantity:  " + selectQty +"\n" + "Current Stock: " 
					+ actualValue + "\n" + "After Destory Stock: " + actualValue1 +"\n"+ "Result: " + "Fail";	
		}
		
	}

	@Test(priority = 5, invocationCount = 1, enabled = true)
	public void Destroyimprest() throws InterruptedException {
		
		stocktakepage.clickStock();
		stocktakepage.clickStockTake();
		stocktakepage.enterMedication(0);
		stocktakepage.Displayimprest();
		Thread.sleep(3000);
		stocktakepage.searching();
		Thread.sleep(3000);
		int actualValue = stocktakepage.getExpectedValue();
		System.out.println("(Stock): " + actualValue);

		destroyimprestPage.Destroy();
		destroyimprestPage.writenote();
		destroyimprestPage.MethodOFDestruction();
		destroyimprestPage.CourierNameandNotes();
		destroyimprestPage.imprest();
		String selectdestroyqty = destroyimprestPage.Getselectdestroyqty();
		String selectMedication = destroyimprestPage.GetselectMedication();
		int selectQty = destroyimprestPage.GetselectQty();
		Thread.sleep(3000);
		signPage.performSignature();
		Thread.sleep(6000);
		
		// Loop through the test execution
		stocktakepage.clickStock();
		stocktakepage.clickStockTake();
		stocktakepages.enterMedication(0);
		stocktakepages.Displayimprest();
		Thread.sleep(3000);
		stocktakepages.searching();
		Thread.sleep(3000);
		int actualValue1 = stocktakepages.getExpectedValue();
		System.out.println("(Stock): " + actualValue1);
		
		int ExpectedQty = actualValue1 + selectQty ;
		System.out.print(ExpectedQty);
		if (ExpectedQty == actualValue) {
			inputdata = "Destory Quantity From Excel : "+ selectdestroyqty + "\n" + "Drug Name: " + 
					selectMedication +"\n" + "Destory in quantity:  " + selectQty +"\n" + "Current Stock: " 
					+ actualValue + "\n" + "After Destory Stock: " + actualValue1 +"\n"+ "Result: " + "Pass";
		}else {
			inputdata = "Destory Quantity From Excel : "+ selectdestroyqty + "\n" + "Drug Name: " + 
					selectMedication +"\n" + "Destory in quantity:  " + selectQty +"\n" + "Current Stock: " 
					+ actualValue + "\n" + "After Destory Stock: " + actualValue1 +"\n"+ "Result: " + "Fail";	
		}
	}

	@Test(priority = 4, invocationCount = 1, enabled = true)
	public void TransferoutPatient() throws InterruptedException {
		stocktakepage.clickStock();
		stocktakepage.clickStockTake();
		stocktakepage.enterMedication(0);
		stocktakepage.enterresidentname(0);
		Thread.sleep(3000);
		stocktakepage.Displayinstock();
		stocktakepage.searching();
		Thread.sleep(3000);

		int actualValue = stocktakepage.getExpectedValue();
		System.out.println("(Stock): " + actualValue);
		

		transferoutPatientPage.transferout();
		transferoutPatientPage.enterLocation();
		transferoutPatientPage.writenote();
		transferoutPatientPage.Resident();
		String selectdestroyqty = transferoutPatientPage.Getselectdestroyqty();
		String selectMedication = transferoutPatientPage.GetselectMedication();
		int selectQty = transferoutPatientPage.GetselectQty();
		Thread.sleep(3000);
		signPage.performSignature();
		Thread.sleep(6000);
		
		
		stocktakepages.clickStock();
		stocktakepages.clickStockTake();
		stocktakepages.enterMedication(0);
		stocktakepages.enterresidentname(0);
		Thread.sleep(3000);
		stocktakepages.Displayinstock();
		stocktakepages.searching();
		Thread.sleep(3000);
		
		

		int actualValue1 = stocktakepages.getExpectedValue();
		System.out.println("(Stock): " + actualValue1);
		
		
		int ExpectedQty = actualValue1 + selectQty ;
		System.out.print(ExpectedQty);
		
		if (ExpectedQty == actualValue) {
			inputdata = "Destory Quantity From Excel : "+ selectdestroyqty + "\n" + "Drug Name: " + 
					selectMedication +"\n" + "Destory in quantity:  " + selectQty +"\n" + "Current Stock: " 
					+ actualValue + "\n" + "After Destory Stock: " + actualValue1 +"\n"+ "Result: " + "Pass";
		}else {
			inputdata = "Destory Quantity From Excel : "+ selectdestroyqty + "\n" + "Drug Name: " + 
					selectMedication +"\n" + "Destory in quantity:  " + selectQty +"\n" + "Current Stock: " 
					+ actualValue + "\n" + "After Destory Stock: " + actualValue1 +"\n"+ "Result: " + "Fail";	
		}
	}

	@Test(priority = 3, invocationCount = 1, enabled = true)
	public void TransferoutImprest() throws InterruptedException {
		
		stocktakepage.clickStock();
		stocktakepage.clickStockTake();
		stocktakepage.enterMedication(0);
		stocktakepage.Displayimprest();
		Thread.sleep(3000);
		stocktakepage.searching();
		Thread.sleep(3000);
		int actualValue = stocktakepage.getExpectedValue();
		System.out.println("(Stock): " + actualValue);

		transferoutimprestPage.transferout();
		transferoutimprestPage.enterLocation();
		transferoutimprestPage.writenote();
		transferoutimprestPage.imprest();
		String selectdestroyqty = transferoutimprestPage.Getselectdestroyqty();
		String selectMedication = transferoutimprestPage.GetselectMedication();
		int selectQty = transferoutimprestPage.GetselectQty();
		Thread.sleep(3000);
		signPage.performSignature();
		Thread.sleep(6000);
		
		// Loop through the test execution
		stocktakepage.clickStock();
		stocktakepage.clickStockTake();
		stocktakepages.enterMedication(0);
		stocktakepages.Displayimprest();
		Thread.sleep(3000);
		stocktakepages.searching();
		Thread.sleep(3000);
		int actualValue1 = stocktakepages.getExpectedValue();
		System.out.println("(Stock): " + actualValue1);
		
		int ExpectedQty = actualValue1 + selectQty ;
		System.out.print(ExpectedQty);
		if (ExpectedQty == actualValue) {
			inputdata = "Destory Quantity From Excel : "+ selectdestroyqty + "\n" + "Drug Name: " + 
					selectMedication +"\n" + "Destory in quantity:  " + selectQty +"\n" + "Current Stock: " 
					+ actualValue + "\n" + "After Destory Stock: " + actualValue1 +"\n"+ "Result: " + "Pass";
		}else {
			inputdata = "Destory Quantity From Excel : "+ selectdestroyqty + "\n" + "Drug Name: " + 
					selectMedication +"\n" + "Destory in quantity:  " + selectQty +"\n" + "Current Stock: " 
					+ actualValue + "\n" + "After Destory Stock: " + actualValue1 +"\n"+ "Result: " + "Fail";	
		}
	}

	@Test(priority = 2, invocationCount = 1, enabled = true)
	public void TransferinPatient() throws InterruptedException {
		stocktakepage.clickStock();
		stocktakepage.clickStockTake();
		stocktakepage.enterMedication(0);
		stocktakepage.enterresidentname(0);
		Thread.sleep(3000);
		stocktakepage.Displayinstock();
		stocktakepage.searching();
		Thread.sleep(3000);
		int actualValue = stocktakepage.getExpectedValue();
		System.out.println("(Stock): " + actualValue);

		transferInPatientPage.transferIn();
		transferInPatientPage.enterLocation();
		transferInPatientPage.writenote();
		transferInPatientPage.Resident();
		String selectdestroyqty = transferInPatientPage.Getselectdestroyqty();
		String selectMedication = transferInPatientPage.GetselectMedication();
		int selectQty = transferInPatientPage.GetselectQty();
		Thread.sleep(3000);
		signPage.performSignature();
		Thread.sleep(6000);
		
		// Loop through the test execution
		stocktakepages.clickStock();
		stocktakepages.clickStockTake();
		stocktakepages.enterMedication(0);
		stocktakepages.enterresidentname(0);
		Thread.sleep(3000);
		stocktakepages.Displayinstock();
		stocktakepages.searching();
		Thread.sleep(3000);
		int actualValue1 = stocktakepages.getExpectedValue();
		System.out.println("(Stock): " + actualValue1);
		
		int ExpectedQty = actualValue + selectQty ;
		System.out.print(ExpectedQty);
		if (ExpectedQty == actualValue1) {
			inputdata = "Destory Quantity From Excel : "+ selectdestroyqty + "\n" + "Drug Name: " + 
					selectMedication +"\n" + "Destory in quantity:  " + selectQty +"\n" + "Current Stock: " 
					+ actualValue + "\n" + "After Destory Stock: " + actualValue1 +"\n"+ "Result: " + "Pass";
		}else {
			inputdata = "Destory Quantity From Excel : "+ selectdestroyqty + "\n" + "Drug Name: " + 
					selectMedication +"\n" + "Destory in quantity:  " + selectQty +"\n" + "Current Stock: " 
					+ actualValue + "\n" + "After Destory Stock: " + actualValue1 +"\n"+ "Result: " + "Fail";	
		}
	}

	@Test(priority = 10, invocationCount = 1, enabled = false)

	public void stocktakeOpen() throws InterruptedException {
		stocktakepage.clickStock();
		stocktakepage.clickStockTake();

		// Loop through the test execution
		for (int i = 0; i < 10; i++) {
			stocktakepage.enterMedication(i);
			Thread.sleep(3000);
			stocktakepage.Displayinstock();
			stocktakepage.searching();
			stocktakepage.Displayimprest();
			Thread.sleep(3000);

			int actualValue = stocktakepage.getExpectedValue();
			System.out.println("(Stock): " + actualValue);
		}
	}

	@Test(priority = 1, invocationCount = 1, enabled = true)
	public void TransferinImprest() throws InterruptedException {
		
		stocktakepage.clickStock();
		stocktakepage.clickStockTake();
		stocktakepage.enterMedication(0);
		stocktakepage.Displayimprest();
		Thread.sleep(3000);
		stocktakepage.searching();
		Thread.sleep(3000);
		int actualValue = stocktakepage.getExpectedValue();
		System.out.println("(Stock): " + actualValue);

		transferInPage.transferIn();
		transferInPage.enterLocation();
		transferInPage.writenote();
		transferInPage.imprest();
		String selectdestroyqty = transferInPage.Getselectdestroyqty();
		String selectMedication = transferInPage.GetselectMedication();
		int selectQty = transferInPage.GetselectQty();
		Thread.sleep(3000);
		signPage.performSignature();
		Thread.sleep(6000);

		
		// Loop through the test execution
		stocktakepage.clickStock();
		stocktakepage.clickStockTake();
		stocktakepages.enterMedication(0);
		stocktakepages.Displayimprest();
		Thread.sleep(3000);
		stocktakepages.searching();
		Thread.sleep(3000);
		int actualValue1 = stocktakepages.getExpectedValue();
		System.out.println("(Stock): " + actualValue1);
		
		int ExpectedQty = actualValue + selectQty ;
		System.out.print(ExpectedQty);
		if (ExpectedQty == actualValue1) {
			inputdata = "Destory Quantity From Excel : "+ selectdestroyqty + "\n" + "Drug Name: " + 
					selectMedication +"\n" + "Destory in quantity:  " + selectQty +"\n" + "Current Stock: " 
					+ actualValue + "\n" + "After Destory Stock: " + actualValue1 +"\n"+ "Result: " + "Pass";
		}else {
			inputdata = "Destory Quantity From Excel : "+ selectdestroyqty + "\n" + "Drug Name: " + 
					selectMedication +"\n" + "Destory in quantity:  " + selectQty +"\n" + "Current Stock: " 
					+ actualValue + "\n" + "After Destory Stock: " + actualValue1 +"\n"+ "Result: " + "Fail";	
		}
		

	}

	@Test(priority = 11, invocationCount = 1, enabled = false)

	public void stocktakeclose() throws InterruptedException {
		stocktakepages.clickStock();
		stocktakepages.clickStockTake();

		// Loop through the test execution
		for (int i = 0; i < 15; i++) {
			stocktakepages.enterMedication(i);
			Thread.sleep(3000);
			stocktakepages.Displayinstock();
			stocktakepages.searching();
			stocktakepages.Displayimprest();
			Thread.sleep(3000);

			int actualValue = stocktakepages.getExpectedValue();
			System.out.println("(Stock): " + actualValue);
		}
	}

	@AfterClass
	public void tearDown() {
		driver.quit();

	}

	@AfterMethod
	public void tearDown(ITestResult result) throws IOException {
		// Use the stored formattedDateTime in the method

		if (result.getStatus() == ITestResult.FAILURE) {
			String taskDescription = loginTaskDescription;
			String listId = "901600130678";
			String status = "FAIL";
			createClickUpTask(formattedDateTime, taskDescription, listId, status);

			String methodName = result.getMethod().getMethodName();
			String consoleError = extractConsoleError(result);
			String faildes = loginTaskDescription + "\n" + inputdata + consoleError;

			createClickUpTask(methodName, faildes, listId, status);

			String mainTaskId = getTaskId(formattedDateTime, listId);
			String subTaskId = getTaskId(methodName, listId);
			updateTask(subTaskId, mainTaskId);

		} else {
			String taskDescription = loginTaskDescription;
			String listId = "901600535467";
			String status = "PASS";

			createClickUpTask(formattedDateTime, taskDescription, listId, status);

			String methodName = result.getMethod().getMethodName();
			String consoleError = extractConsoleError(result);
			String faildes = loginTaskDescription + "\n" + inputdata + consoleError;
			createClickUpTask(methodName, faildes, listId, status);

			String mainTaskId = getTaskId(formattedDateTime, listId);
			String subTaskId = getTaskId(methodName, listId);
			updateTask(subTaskId, mainTaskId);
		}

	}

	private String getCurrentDateTime() {
		LocalDateTime now = LocalDateTime.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy H:m");
		return now.format(formatter);
	}

	private String extractConsoleError(ITestResult result) {
		String consoleOutput = "";
		Throwable throwable = result.getThrowable();
		if (throwable != null) {
			consoleOutput = throwable.getMessage();
		}
		return consoleOutput;
	}

}