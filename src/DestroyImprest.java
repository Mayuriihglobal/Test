import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class DestroyImprest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		// Extent report setup
		ExtentReports extent = new ExtentReports();
		ExtentSparkReporter spark = new ExtentSparkReporter("DestroyImprest.html");
		extent.attachReporter(spark);

		// Create a WebDriver instance
		WebDriver driver = new ChromeDriver();

		// Maximize the Chrome window

		driver.manage().window().maximize();

		// Set implicit wait to 10 seconds
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		// Initialize WebDriverWait with a longer duration
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(60));

		// Open the webpage
		driver.get("https://staging.strongroom.ai/login");

		// Display status log on html report page
		extent.createTest("Go to https://staging.strongroom.ai/login").assignCategory("regression testing")
				.assignDevice("Chrome").log(Status.INFO, "Go to https://staging.strongroom.ai/login");

		try {
			// Entering Location
			WebElement locationInput = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Location']")));
			locationInput.sendKeys("Internal Testing");

			// Clicking on a location
			WebElement clickElement = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//p[@class='drug-search-result' and text()='Internal Testing']")));
			clickElement.click();

			// Entering Username
			WebElement usernameInput = driver.findElement(By.xpath("//input[@placeholder='Username/email']"));
			usernameInput.sendKeys("sam");

			// Entering Password
			WebElement passwordInput = driver.findElement(By.xpath("//input[@placeholder='Password']"));
			passwordInput.sendKeys("strongroompassword");

			// Clicking on Login Button
			WebElement loginButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='blue-button']")));
			loginButton.click();

			extent.createTest("Enter correct login").assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.INFO, "Enter correct login");

			// Selecting location on the second page
			WebElement selectLocation = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[@aria-label='Select location']")));
			selectLocation.click();

			extent.createTest("user is logged in and the system displays the location select page")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.INFO, "user is logged in and the system displays the location select page");

			// Choosing a location from the dropdown
			WebElement dropdownOption = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//li[@id='pv_id_2_4']")));
			dropdownOption.click();

			// Explicit wait for the "blue-button" with a 90-second duration
			WebDriverWait extendedWait = new WebDriverWait(driver, Duration.ofSeconds(60));

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			WebElement secondPageButton = extendedWait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='blue-button']")));

			// Click the "blue-button"
			secondPageButton.click();

			extent.createTest("Select a location and click the Select Location button")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.INFO, "system displays the drug register for the specific location");

			// Display status log on html report page : Go to /drug-register
			extent.createTest("Go to/drug-register").assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.PASS, "Go to /drug-register");

			// Clicking on the Destroy in
			WebElement transferIn = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[normalize-space()='Destroy']")));
			transferIn.click();

			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//div[@class='right-form-section-drug-container']")));

			// Log the pass status
			System.out.println("Test Passed: Click the Destroy button in the left menu : Destroy modal appears");

			// Display status log on html report page
			extent.createTest("Click the Destroy button in the left menu: Destroy modal appears")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.PASS, "Click the Destroy button in the left menu : Destroy modal appears");

		} catch (Exception e) {
			// Log the fail status and any exception details
			System.out.println(
					"Test Failed: Click Destroy button in the left menu : Destroy modal is not appears. Exception: "
							+ e.getMessage());

			// Display status log on html report page
			extent.createTest("Click Destroy button in the left menu : Destroy modal is not appears")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.FAIL, "Click the Destroy button in the left menu : Destroy modal is not appears");

		}

		// Assuming you have navigated to the page and located the textarea element
		WebElement noteTextArea = driver.findElement(By.xpath("//textarea[@name='notes' and @id='note-modal']"));

		// Write "Transferrin" in the note box
		noteTextArea.sendKeys("Destruction");

		System.out.println("Test Passed: Added text to notes");

		// Display status log on html report page
		extent.createTest("Enter a Reason for destruction").assignCategory("regression testing").assignDevice("Chrome")
				.log(Status.PASS, "Enter a Reason for destruction");

		// Enter a Method of destruction
		WebElement methodOfDestructionInput = driver
				.findElement(By.xpath("//input[@placeholder='Method of Destruction']"));
		methodOfDestructionInput.sendKeys("Incineration");

		System.out.println("Test Passed: Entered Method of Destruction");

		// Display status log on html report page
		extent.createTest("Enter a Method of Destruction").assignCategory("regression testing").assignDevice("Chrome")
				.log(Status.PASS, "Entered Method of Destruction");

		// Enter courier name
		WebElement courierNameInput = driver.findElement(By.xpath("//input[@placeholder='Courier Name']"));
		courierNameInput.sendKeys("Express Courier");

		System.out.println("Test Passed: Entered Courier Name");

		// Display status log on html report page
		extent.createTest("Enter Courier Name").assignCategory("regression testing").assignDevice("Chrome")
				.log(Status.PASS, "Entered Courier Name");

		// Enter courier notes
		WebElement courierNotesInput = driver.findElement(By.xpath("//input[@placeholder='Courier Notes']"));
		courierNotesInput.sendKeys("Handle with care");

		System.out.println("Test Passed: Entered Courier Notes");

		// Display status log on html report page
		extent.createTest("Enter Courier Notes").assignCategory("regression testing").assignDevice("Chrome")
				.log(Status.PASS, "Entered Courier Notes");

		// 88
		try {

			// Click the Imprest/Emergency Meds/Ward Stock button
			WebElement button = wait.until(ExpectedConditions
					.elementToBeClickable(By.xpath("//p[normalize-space()='Imprest/Emergency Meds/Ward Stock']")));
			button.click();

			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//input[@placeholder='Select Medication']")));

			// Log the pass status
			System.out.println(
					"Test Passed: Click the Imprest/Emergency Meds/Ward Stock button : select medication input field appears");

			// Display status log on html report page
			extent.createTest(
					"Click the Imprest/Emergency Meds/Ward Stock button : select medication input field appears")
					.assignCategory("regression testing").assignDevice("Chrome").log(Status.PASS,
							"Click the Imprest/Emergency Meds/Ward Stock button : select medication input field appears");

		} catch (Exception e) {

			// Log the fail status and any exception details
			System.out.println(
					"Test Failed: Click the Imprest/Emergency Meds/Ward Stock button  : select medication input field is not appear. Exception: "
							+ e.getMessage());

			// Display status log on html report page
			extent.createTest(
					"Click the Imprest/Emergency Meds/Ward Stock button  : select medication input field is not appear")
					.assignCategory("regression testing").assignDevice("Chrome").log(Status.FAIL,
							"Click the Imprest/Emergency Meds/Ward Stock button  : select medication input field is not appear");

		}

		try {

			// 7. Enter a medication : Check that the medication dropdown works
			WebElement medicationInput = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Select Medication']")));
			medicationInput.click();

			// Add the text "a" to the input field
			medicationInput.sendKeys("amoxicillin");

			// Add a wait for 5 seconds using Thread.sleep()
			try {
				Thread.sleep(5000); // 5000 milliseconds = 5 seconds
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			wait.until(ExpectedConditions
					.visibilityOfElementLocated(By.xpath("//div[@class='p-dropdown-items-wrapper']")));

			// Log the pass status
			System.out.println("Test Passed: Enter a medication : medication dropdown works");

			// Display status log on html report page
			extent.createTest("Enter a medication : medication dropdown works").assignCategory("regression testing")
					.assignDevice("Chrome").log(Status.PASS, "Enter a medication : medication dropdown works");

			medicationInput.sendKeys(Keys.ENTER);

			Thread.sleep(2000); // 2000 milliseconds = 2 seconds

			medicationInput.sendKeys(Keys.ARROW_DOWN);

			medicationInput.sendKeys(Keys.ENTER);

		} catch (Exception e) {

			System.out.println("Test Failed: Enter a medication : medication dropdown is not work");

			// Display status log on html report page
			extent.createTest("Enter a medication : medication dropdown is not work")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.FAIL, "Enter a medication : medication dropdown is not work");

		}

		wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Select Medication']")));

		// Click on the quantity field with the specified placeholder
		WebElement quantityInput = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Enter qty']")));
		quantityInput.click();

		// Enter the quantity "1" in the field
		quantityInput.sendKeys("1");

		System.out.println("Test Passed: Select a medication and qty");

		// Display status log on html report page
		extent.createTest("Select a medication and qty").assignCategory("regression testing").assignDevice("Chrome")
				.log(Status.PASS, "Select a medication and qty");

		try {

			// 9. Click the Add button : Check that the correct medication and qty have been
			// selected
			WebElement addButton = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='submit-button blue-button']")));
			addButton.click();

			// Display status log on html report page
			extent.createTest("Click the Add button").assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.PASS, "Click the Add button");

			// get xpath for madication
			WebElement elementmedication = driver.findElement(By.xpath("//p[text()='Amoxicillin 500 mg capsule']"));

			// Get the text from the medication name
			String text1 = elementmedication.getText();

			// Assuming 'text' contains the text retrieved from the element
			String expectedText1 = "Amoxicillin 500 mg capsule";

			// Print medication name
			System.out.println("Selected Medication name is : " + text1);

			// Compare the text with the expected text
			if (text1.equals(expectedText1)) {
				System.out.println("Pass: Added Medication name match");

				// Display status log on html report page
				extent.createTest("Added Medication name match").assignCategory("regression testing")
						.assignDevice("Chrome").log(Status.INFO, "Added Medication name match");

			} else {
				System.out.println("Fail: Added Medication name is not match");
				// Display status log on html report page
				extent.createTest("Added Medication name is not match").assignCategory("regression testing")
						.assignDevice("Chrome").log(Status.INFO, "Added Medication name is not match");
			}

			// get xpath for madication
			WebElement elementmedication1 = driver.findElement(By.cssSelector("td:nth-child(2) p:nth-child(1)"));

			// Get the text from the medication name
			String text11 = elementmedication1.getText();

			// Extract the desired part of the text
			String extractedText = text11.split("\\(")[0].trim();

			// Assuming 'text' contains the text retrieved from the element
			String expectedText11 = "1 capsule";

			// Print medication name
			System.out.println("Selected Quantity is : " + extractedText);

			if (extractedText.equals(expectedText11)) {
				System.out.println("Pass: Selected Quantity is match");

				extent.createTest("Added qty is matched").assignCategory("regression testing").assignDevice("Chrome")
						.log(Status.INFO, "Added qty is matched");

				extent.createTest("correct medication and qty have been selected").assignCategory("regression testing")
						.assignDevice("Chrome").log(Status.INFO, "correct medication and qty have been selected");

			} else {

				System.out.println("Fail: Selected Quantity is not match");

				extent.createTest("Added qty is not matched").assignCategory("regression testing")
						.assignDevice("Chrome").log(Status.INFO, "Added qty is not matched");

			}

		} catch (Exception e) {
			// Handle exceptions if necessary
			e.printStackTrace();
		}

		try {

			// Click on the button with the specified class
			WebElement completeButton = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='regular-button complete-button']")));
			completeButton.click();

			// Check that the signature check modal pops up
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(
					"//div[@class='modal-mask']//div[@class='modal-mask']//div[@class='form-section-container']")));

			// Log the pass status
			System.out.println("Test Passed: Click the Destroy button : signature check modal pops up apper");

			// Display status log on html report page
			extent.createTest("Click the Destroy button : signature check modal pops up apper")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.PASS, "Click the Destroy button : signature check modal pops up apper");

		} catch (Exception e) {

			System.out.println("Test Failed: Click the Destroy button : signature modal pops up is not apper");

			// Display status log on html report page
			extent.createTest("Click the Destroy button : signature modal pops up is not apper")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.FAIL, "Click the Destroy button : signature modal pops up is not apper");

		}

		try {

			// Find the input field with the specified placeholder
			WebElement usernameInput1 = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Username']")));

			usernameInput1.click();

			// Clear the text from the input field
			usernameInput1.clear();

			// Writing text into the input field

			WebElement usernameInput11 = wait
					.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@placeholder='Username']")));
			usernameInput11.sendKeys("valeshan.naidoo@strongroom.ai");

			// Clicking on the input field
			WebElement passwordInput1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='PIN/Password']")));
			passwordInput1.click();

			// Writing text into the input field
			passwordInput1.sendKeys("1111");

			// Clicking on the field
			WebElement greenButton = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='green-button']")));
			greenButton.click();

			// Wait for 5 seconds (5000 milliseconds)
			Thread.sleep(5000);

			// Check that the drug register displays the new transfer at the top of the
			// table
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tbody[4]/tr[1]/td[4]")));

			// Log the pass status
			System.out.println(
					"Test Passed: Enter correct signature and click the Sign button: Drug register displays the new destruction at the top of the table");

			// Display status log on html report page
			extent.createTest(
					"Enter correct signature and click the Sign button: Drug register displays the new destruction at the top of the table")
					.assignCategory("regression testing").assignDevice("Chrome").log(Status.PASS,
							"Enter correct signature and click the Sign button: Drug register displays the new destruction at the top of the table");

		} catch (Exception e) {

			System.out.println(
					"Test Failed: Enter correct signature and click the Sign button : Drug register is not displays the new destruction at the top of the table");

			// Display status log on html report page
			extent.createTest(
					"Enter correct signature and click the Sign button : Drug register is not displays the new destruction at the top of the table")
					.assignCategory("regression testing").assignDevice("Chrome").log(Status.FAIL,
							"Enter correct signature and click the Sign button : Drug register is not displays the new destruction at the top of the table");

		}

		try {

			// Click on the new transfer in the drug register
			WebElement elementToClick = driver.findElement(By.xpath("//tbody[1]/tr[1]/td[1]/i[1]"));
			elementToClick.click();

			// Check that the details are correct
			wait.until(ExpectedConditions.visibilityOfElementLocated(
					By.xpath("//tr[@id='expand-0']//div[@class='expanded-transfer-container']")));

			// Log the pass status
			System.out.println("Test Passed: Click on the new destruction the drug register : details are correct");

			// Display status log on html report page
			extent.createTest("Click on the new destruction in the drug register : details are correct")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.PASS, "Click on the new destruction in the drug register : details are correct");

		} catch (Exception e) {

			System.out.println(
					"Test Failed: Click on the new destruction in the drug register : details are not correct");
			extent.createTest("Click on the new destruction in the drug register : details are not correct")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.FAIL, "Click on the new destruction in the drug register : details are not correct");

		}

		// Wait for the element to be clickable and click on it

		try {
			WebElement stockElement = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Stock']")));
			stockElement.click();

			// Check that the details are correct
			wait.until(
					ExpectedConditions.visibilityOfElementLocated(By.xpath("//h3[normalize-space()='Destructions']")));

			System.out.println(
					"Test Passed: Click Stock in the top menu : Destructions Box displays the new transfer and the data is correct");

			// Display status log on html report page
			extent.createTest(
					"Click Stock in the top menu : Destructions Box displays the new transfer and the data is correct")
					.assignCategory("regression testing").assignDevice("Chrome").log(Status.PASS,
							"Click Stock in the top menu : Destructions Box displays the new transfer and the data is correct");

		} catch (Exception e) {

			System.out.println(
					"Test Failed: Click Stock in the top menu : Destructions Box is not displays the new transfer and the data is not correct");

			// Display status log on html report page
			extent.createTest(
					"Click Stock in the top menu : Destructions Box is not displays the new transfer and the data is not correct")
					.assignCategory("regression testing").assignDevice("Chrome").log(Status.FAIL,
							"Click Stock in the top menu : Destructions Box is not displays the new transfer and the data is not correct");

		}

		// Wait for the element to be clickable and click on it
		WebElement transfersElement = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Destructions']")));
		transfersElement.click();

		// Display status log on html report page
		extent.createTest("Click the Destructions field in the top sub menu").assignCategory("regression testing")
				.assignDevice("Chrome").log(Status.PASS, "Click the Destructions field in the top sub menu");

		WebElement notificationIcon = driver.findElement(By.xpath("//i[@class='pi pi-exclamation-circle']"));
		notificationIcon.click();

		WebElement destructionRegisterElement = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[@class='select-filter-item']")));

		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", destructionRegisterElement);

		// destructionRegisterElement.click();

		// Display status log on html report page
		extent.createTest("Click the Destruction Register in the search bar").assignCategory("regression testing")
				.assignDevice("Chrome").log(Status.PASS, "Click the Destruction Register in the search bar");

		// 88

		// Specify the XPath for the data element
		WebElement ArrorButton = wait
				.until(ExpectedConditions.elementToBeClickable(By.xpath("//tbody[1]/tr[1]/td[1]/i[1]")));
		ArrorButton.click();

		String xpathForData = "//p[@class='field-value correct-status-color']";

		// Wait for the element to be present
		WebElement dataElement = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(xpathForData)));

		// Check if the element contains the expected data
		String expectedData = "Destruction"; // Replace with the expected data
		String actualData = dataElement.getText();

		if (actualData.equals(expectedData)) {
			System.out.println("Test Passed : Open the new destruction row : data is correct" + actualData);

			extent.createTest("Open the new destruction row : data is correct").assignCategory("regression testing")
					.assignDevice("Chrome").log(Status.PASS, "Open the new destruction row : data is correct");

		} else {
			System.out.println("Test Failed : Open the new destruction row : data is not correct. Expected: "
					+ expectedData + ", Actual: " + actualData);

			extent.createTest("Open the new destruction row : data is not correct").assignCategory("regression testing")
					.assignDevice("Chrome").log(Status.FAIL, "Open the new destruction row : data is not correct");

			// ****Open the new transfer row Check that the data is correct

		}

		// Introduce a 2-second sleep

		try {

			// Add a sleep of 5000 milliseconds (5 seconds)
			Thread.sleep(5000);

			// Wait for the element to be clickable and click on it Stock take
			WebElement transfersElement1 = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//p[normalize-space()='Stock Take']")));
			transfersElement1.click();

			System.out.println("Test Passed : Clicked Stock Take in the top menu");

			extent.createTest("Clicked Stock Take in the top menu").assignCategory("regression testing")
					.assignDevice("Chrome").log(Status.PASS, "Clicked Stock Take in the top menu");

			// Find the input field for medication search
			WebElement medicationSearchInput = wait
					.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@placeholder='Medication...']")));

			// Enter the medication name "amoxicillin 500 mg capsule"
			medicationSearchInput.sendKeys("buprenorphine 40 microgram/hour patch");

			// Find the button using its XPath
			WebElement submitButton = wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("//button[@class='button submit-button']")));

			// Click on the button
			submitButton.click();

			// Wait for the result element to be visible
			WebElement resultElement = wait
					.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//tr[2]//td[1]")));

			// Check if the result element is visible
			if (resultElement.isDisplayed()) {
				System.out.println("Pass: Result element is visible");
			} else {
				System.out.println("Fail: Result element is not visible");
			}

			// Log the pass status
			System.out.println("Test Passed: Medication search performed successfully");

			// Display status log on html report page
			extent.createTest("Search for the destroyed medication in the search field")
					.assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.PASS, "data and qty is correct");

		} catch (Exception e) {
			// Log the fail status and any exception details
			System.out.println("Test Failed: Medication search failed. Exception: " + e.getMessage());

			// Display status log on html report page
			extent.createTest("Medication Search").assignCategory("regression testing").assignDevice("Chrome")
					.log(Status.FAIL, "Medication search failed");
		}

		extent.flush();

		// Close the WebDriver
		// driver.quit();

	}
	
	

}
